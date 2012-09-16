/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bms;

import bms.DBConnection;
import bms.Regula;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

/**
 *
 * @author Michal
 */
public class SD extends TimerTask {

    static int N = 3;
    static float[] t;

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

    /**
     * odczytuje reguly dotyczace danego czujnika
     *
     * @param czujnik
     * @return
     */
    private static Regula[] odczyt_z_bazy(int czujnik) {
        return DBConnection.Select_All_From_Reguly("id_czuj=" + String.valueOf(czujnik));
    }

    private static float oblicz(Regula[] r, float f) {
        float zz, z, i, c, zc, val[];
        float value = 0;
        Regula rr = r[0];
        r = new Regula[1];
        r[0] = rr;
        val = new float[2];//0-grzej, 1-wietrz
        for (int j = 0; j < r.length; ++j) {
            if (GUI.isAdvanced()) {
                switch (GUI.cold_fun) {
                    case 0:
                        zz = fuzzy_S(f, GUI.cold[0], GUI.cold[1]);
                        break;
                    case 1:
                        zz = fuzzy_trojkat(f, GUI.cold[0], GUI.cold[1], GUI.cold[2]);
                        break;
                    case 2:
                        zz = fuzzy_trapez(f, GUI.cold[0], GUI.cold[1], GUI.cold[2], GUI.cold[3]);
                        break;
                    case 3:
                        zz = fuzzy_Z(f, GUI.cold[0], GUI.cold[1]);
                        break;
                    default:
                        zz = gaussian(GUI.cold[0], GUI.cold[1], f);
                        break;
                }
                switch (GUI.calm_fun) {
                    case 0:
                        z = fuzzy_S(f, GUI.calm[0], GUI.calm[1]);
                        break;
                    case 1:
                        z = fuzzy_trojkat(f, GUI.calm[0], GUI.calm[1], GUI.calm[2]);
                        break;
                    case 2:
                        z = fuzzy_trapez(f, GUI.calm[0], GUI.calm[1], GUI.calm[2], GUI.calm[3]);
                        break;
                    case 3:
                        z = fuzzy_Z(f, GUI.calm[0], GUI.calm[1]);
                        break;
                    default:
                        z = gaussian(GUI.calm[0], GUI.calm[1], f);
                        break;
                }
                switch (GUI.warm_fun) {
                    case 0:
                        c = fuzzy_S(f, GUI.warm[0], GUI.warm[1]);
                        break;
                    case 1:
                        c = fuzzy_trojkat(f, GUI.warm[0], GUI.warm[1], GUI.warm[2]);
                        break;
                    case 2:
                        c = fuzzy_trapez(f, GUI.warm[0], GUI.warm[1], GUI.warm[2], GUI.warm[3]);
                        break;
                    case 3:
                        c = fuzzy_Z(f, GUI.warm[0], GUI.warm[1]);
                        break;
                    default:
                        c = gaussian(GUI.warm[0], GUI.warm[1], f);
                        break;
                }
                switch (GUI.hot_fun) {
                    case 0:
                        zc = fuzzy_S(f, GUI.hot[0], GUI.hot[1]);
                        break;
                    case 1:
                        zc = fuzzy_trojkat(f, GUI.hot[0], GUI.hot[1], GUI.hot[2]);
                        break;
                    case 2:
                        zc = fuzzy_trapez(f, GUI.hot[0], GUI.hot[1], GUI.hot[2], GUI.hot[3]);
                        break;
                    case 3:
                        zc = fuzzy_Z(f, GUI.hot[0], GUI.hot[1]);
                        break;
                    default:
                        zc = gaussian(GUI.hot[0], GUI.hot[1], f);
                        break;
                }
//                System.out.println(String.valueOf("Fun: " + GUI.cold_fun) + " " + String.valueOf(GUI.hot_fun));
                val[0] = fuzzyOR(zz, z);//za zimno OR zimno-> grzej
                val[1] = fuzzyOR(c, zc);//cieplo OR za cieplo-> wietrz
//                System.out.println("Temp: " + String.valueOf(f) + " \nzz: " + String.valueOf(zz) + " z:" + String.valueOf(z) + " \nc: " + String.valueOf(c) + " zc: " + String.valueOf(zc) + "\ngrzej: " + String.valueOf(val[0]) + " wietrz: " + String.valueOf(val[1]) + " \nroznica: " + String.valueOf(val[0] - val[1]));
                value += val[0] - val[1];
            } else {
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
                    default:
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
//            System.out.println("Temp: " + String.valueOf(f) + " \nzz: " + String.valueOf(zz) + " z:" + String.valueOf(z) + " \nc: " + String.valueOf(c) + " zc: " + String.valueOf(zc) + "\ngrzej: " + String.valueOf(val[0]) + " wietrz: " + String.valueOf(val[1]) + " \nroznica: " + String.valueOf(val[0] - val[1]));
        }
        value /= r.length;
        return value;
    }

    private static boolean ustaw(int obj, float val) {
        boolean b;
        if (val > 0) {
            b = ModbusTCPConnection.heating(1);
            b = ModbusTCPConnection.cooling(0);

        } else {
            b = ModbusTCPConnection.cooling(1);
            b = ModbusTCPConnection.heating(0);
        }


        return b;
    }

    @Override
    public void run() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf;
        Regula[] tmp;
        float tmp_f;
        int czuj;

        GUI.log("<-------START------->");

        sdf = new SimpleDateFormat("yyyy-MM-dd");
        GUI.log("Date: " + sdf.format(cal.getTime()));
        sdf = new SimpleDateFormat("HH:mm:ss");
        GUI.log("Time: " + sdf.format(cal.getTime()));

        czuj = DBConnection.Select_All_From_Czujniki();
        GUI.log("Sensors count: " + czuj);
        t = ModbusTCPConnection.read();
        
        DBConnection.update_Temp_database(t[0], t[1]);
//        t=new float[2];
//        t[0]=15.5f;
//        t[1]=25.3f;
        for (int i = 0; i < czuj - 1; i++) {
            if (GUI.isAdvanced()) {
                tmp = GUI.p;
                GUI.setSliders(tmp[0].getWartosc(), i);
            } else if (GUI.isLocal()) {
                tmp = GUI.getSliders(i);
            } else {
                tmp = odczyt_z_bazy(i + 1);
                GUI.setSliders(tmp[0].getWartosc(), i);
            }
            tmp_f = oblicz(tmp, t[i]);
            GUI.setDecisionProgressBar(tmp_f, i);
//            System.out.println(String.valueOf(t[i]) + ": " + String.valueOf(tmp_f));
            GUI.log("Region:" + String.valueOf(i) + "\nIdeal temp:" + String.valueOf(tmp[0].getWartosc()) + "\nActual temp: " + String.valueOf(t[i]) + "\nDecision: " + String.valueOf(tmp_f));
            boolean b = ustaw(i + 1, tmp_f);
            GUI.log("Setting accomplished:" + String.valueOf(b));
        }
        GUI.log("<-------STOP------->");
    }
}
