/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bms;

/**
 *
 * @author Michal
 */
public class Regula {

    private int funkcja, //wybor funkcji
            co;          //wybor strefy
    private float wartosc, //
            czas;        //dodatkowy parametr

    public Regula(int funkcja, int co, float wartosc, float czas) {
        this.funkcja = funkcja;
        this.co = co;
        this.wartosc = wartosc;
        this.czas = czas;
    }

    public Regula() {
        this.funkcja = 0;
        this.co = 0;
        this.wartosc = 0;
        this.czas = 0;
    }

    public int getCo() {
        return co;
    }

    public float getCzas() {
        return czas;
    }

    public int getFunkcja() {
        return funkcja;
    }

    public float getWartosc() {
        return wartosc;
    }
}
