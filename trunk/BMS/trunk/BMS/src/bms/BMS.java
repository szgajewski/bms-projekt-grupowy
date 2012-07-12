package bms;

import java.util.Timer;

/**
 *
 * @author Michal
 */
public class BMS {

    //zmienne do insertow
    public String nazwa_cz = "nowy_czujnik";
    public String nazwa_f = "nowa_funkcja";
    public int id_pom = 0;
    public float czas = 1;
    public float wartosc = 0;
    public int id_czuj = 1;
    public int funkcja = 2;
    //zmienne do selectow
    public String tabela = "nazwa_tabeli";
    public String kolumna = "nazwa_kolumny";
    private Timer t = new Timer();

    public BMS() {
        t.scheduleAtFixedRate(new SD(), 1000, 60 * 1000);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new BMS();
    }
}
