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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        new BMS();
        new GUI().setVisible(true);
    }
}
