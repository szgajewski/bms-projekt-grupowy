package bms;

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

    public BMS() {
        DBConnection c = new DBConnection();
        c.Connect(); //test połączenia

        //c.Insert_Into_Czujniki(nazwa_cz);
        //c.Insert_Into_Funkcje_P(nazwa_f);
        //c.Insert_Into_Pomiary(id_pom, czas, wartosc);
        //c.Insert_Into_Reguly(id_czuj, funkcja, wartosc, czas);

        c.Select_All_From_Czujniki();
        c.Select_All_From_Funkcje_P();
        c.Select_All_From_Pomiary();   //-problem z timestamp()
        c.Select_All_From_Reguly();

        //c.Select_kolumna_From_tabela(kolumna,tabela);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new BMS();
        SD sd = new SD();
        sd.run();

    }
}
