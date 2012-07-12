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
            strefa;          //wybor strefy
    private float wartosc, //
            czas;        //dodatkowy parametr

    public Regula(int funkcja, int strefa, float wartosc, float czas) {
        this.funkcja = funkcja;
        this.strefa = strefa;
        this.wartosc = wartosc;
        this.czas = czas;
    }

    public Regula() {
        this.funkcja = 0;
        this.strefa = 0;
        this.wartosc = 0;
        this.czas = 0;
    }

    public int getstrefa() {
        return strefa;
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
