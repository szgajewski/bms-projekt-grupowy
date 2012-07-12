/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bms;

import java.util.TimerTask;

/**
 *
 * @author Michal
 */
public class SD extends TimerTask {

    static int N = 3;
    static float t = 15;

    private static float gaussian(double b, double c, double x) {
        double a;
        float value;
        a = 1 / (c * Math.sqrt(2 * Math.PI));
        //  ,b=mean value ,c=standard deviation
        value = (float) (Math.exp(-1 * ((x - b) * (x - b)) / (2 * c * c)));
        return value;
    }

    private static float fuzzy_S(float wartosc, float p1, float p2) { //S
        float wynik;
        if (wartosc <= p1) {
            wynik = 0;
        } else if (wartosc >= p2) {
            wynik = 1;
        } else {
            wynik = 0 + (wartosc - p1) / (p2 - p1);
        }
        return wynik;
    }

    private static float fuzzy_Z(float wartosc, float p1, float p2) { //Z
        float wynik;
        if (wartosc <= p1) {
            wynik = 1;
        } else if (wartosc >= p2) {
            wynik = 0;
        } else {
            wynik = 1 - (wartosc - p1) / (p2 - p1);
        }
        return wynik;
    }

    private static float fuzzy_trojkat(float wartosc, float p1, float p2, float p3) { //trojkatna funkcja
        float wynik;
        if (wartosc <= p1 || wartosc >= p3) {
            wynik = 0;
        } else if (wartosc == p2) {
            wynik = 1;
        } else if (wartosc > p1 && wartosc < p2) {
            wynik = 0 + (wartosc - p1) / (p2 - p1);
        } else {
            wynik = 1 - (wartosc - p2) / (p3 - p2);
        }
        return wynik;
    }

    private static float fuzzy_trapez(float wartosc, float p1, float p2, float p3, float p4) { //trapezowa funkcja
        float wynik;
        if (wartosc <= p1 || wartosc >= p4) {
            wynik = 0;
        } else if (wartosc >= p2 && wartosc <= p3) {
            wynik = 1;
        } else if (wartosc > p1 && wartosc < p2) {
            wynik = 0 + (wartosc - p1) / (p2 - p1);
        } else {
            wynik = 1 - (wartosc - p3) / (p4 - p3);
        }
        return wynik;
    }

    private static float fuzzyAND(float f1, float f2) {
        if (f1 > f2) {
            return f2;
        }
        return f1;
    }

    private static float fuzzyOR(float f1, float f2) {
        if (f1 > f2) {
            return f1;
        }
        return f2;
    }

    private static float fuzzyNOT(float f1) {
        return 1 - f1;
    }

    private static Regula[] odczyt_z_bazy(int czujnik) {
        return DBConnection.Select_All_From_Reguly("id_czuj=" + String.valueOf(czujnik));
    }

    private static float oblicz(Regula[] r, float f) {
        float zz, z, i, c, zc, val[];
        float value = 0;

        val = new float[2];//0-grzej, 1-wietrz
        for (int j = 0; j < r.length; ++j) {
            switch (r[j].getFunkcja()) {
                case 0:
                    zz = gaussian(0, 10, f);
                    z = gaussian(r[j].getWartosc() - 5, 5, f);
                    i = gaussian(r[j].getWartosc(), 1, f);
                    c = gaussian(r[j].getWartosc() + 5, 5, f);
                    zc = gaussian(40, 10, f);
                    val[0] = fuzzyOR(zz, z);//za zimno OR zimno-> grzej
                    val[1] = fuzzyOR(c, zc);//cieplo OR za cieplo-> wietrz
                    //printf("G:\n\tTemp: %2f \nzz: %f z: %f i: %f \nc: %f zc: %f\ngrzej: %f wietrz: %f \nroznica: %f\n",f,zz,z,i,c,zc,val[0],val[1],val[0]-val[1]);
                    value += val[0] - val[1];
                    break;
                case 1:
                    zz = fuzzy_Z(f, 10, 15);
                    z = fuzzy_trapez(f, 10, 15, r[j].getWartosc() - 5, r[j].getWartosc() + 3);
                    i = fuzzy_trojkat(f, r[j].getWartosc() - 5, r[j].getWartosc(), r[j].getWartosc() + 5);
                    c = fuzzy_trapez(f, r[j].getWartosc() - 3, r[j].getWartosc() + 5, 25, 30);
                    zc = fuzzy_S(f, 25, 30);
                    val[0] = fuzzyOR(zz, z);
                    val[1] = fuzzyOR(c, zc);
                    value += val[0] - val[1];
                    //printf("T:\n\tTemp: %2f \nzz: %f z: %f i: %f \nc: %f zc: %f\ngrzej: %f wietrz: %f \nroznica: %f\n",f,zz,z,i,c,zc,val[0],val[1],val[0]-val[1]);
                    break;
            }
        }
        value /= r.length;
        return value;
    }

    private static boolean ustaw(int obj, float val) {


        return false;
    }

    @Override
    public void run() {
        Regula[] tmp;
        float tmp_f;
        int czuj = DBConnection.Select_All_From_Czujniki();
        for (int i = 1; i <= czuj; i++) {
            tmp = odczyt_z_bazy(i);
            tmp_f = oblicz(tmp, t);
            System.out.println(String.valueOf(t) + ": " + String.valueOf(tmp_f));
            ustaw(i, tmp_f);
        }
        t += 0.5f;
        if (t > 30) {
            t = 10;
        }
    }
}
