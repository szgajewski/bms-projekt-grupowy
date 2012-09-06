package bms;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    static Connection conn;
    static Statement stat;
    static ResultSet rs;
    static final String serverName = "sql.parakletos.nazwa.pl";
    static final String mydatabase = "parakletos_5";
    static final String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
    static final String username = "parakletos_5";
    static final String password = "ProjektGrupowy314151618";

    public static boolean Connect() {

        try {

// Load the JDBC driver?
// String driverName = "org.gjt.mm.mysql.Driver"; // MySQL MM JDBC driver
// Class.forName(driverName);

            conn = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Start");
            conn.close();
            System.out.println("Success");
            System.out.println("End");
            return true;

        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.toString() + "\n ");
            return false;
        }


    }

    public void Insert_Into_Czujniki(String nazwa_cz) {

        try {
            conn = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Start of DBConnection");

            stat = (Statement) conn.createStatement();
            stat.executeUpdate("INSERT INTO  `czujniki` (`nazwa_cz`)VALUES ('" + nazwa_cz + "')");
            System.out.println("Inserted to table: `czujniki` value: " + nazwa_cz + " to column: `nazwa_cz`");
            stat.close();
            conn.close();
            System.out.println("End of DBConnection");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Insert_Into_Funkcje_P(String nazwa_f) {

        try {
            conn = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Start of DBConnection");

            stat = (Statement) conn.createStatement();
            stat.executeUpdate("INSERT INTO  `funkcje_p` (`nazwa_f`)VALUES ('" + nazwa_f + "')");
            System.out.println("Inserted to table: `funkcje_p` value: " + nazwa_f + " to column: `nazwa_f`");
            stat.close();
            conn.close();
            System.out.println("End of DBConnection");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Insert_Into_Pomiary(Integer id_pom, Float czas, Float wartosc) {

        try {
            conn = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Start of DBConnection");

            stat = (Statement) conn.createStatement();
            stat.executeUpdate("INSERT INTO  `pomiary` (`id_pom`,`czas`,`wartosc`)VALUES ('" + id_pom + "','" + czas + "','" + wartosc + "')");
            System.out.println("Inserted to table: `pomiary` value: " + id_pom + " to column: `id_pom` and value: " + czas + " to column: `czas` and value: " + wartosc + " to column: `wartosc`");
            stat.close();
            conn.close();
            System.out.println("End of DBConnection");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Insert_Into_Reguly(Integer id_czuj, Integer funkcja, Float wartosc, Float czas) {

        try {
            conn = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Start of DBConnection");

            stat = (Statement) conn.createStatement();
            stat.executeUpdate("INSERT INTO  `reguly` (`id_czuj`,`funkcja`,`wartosc`,`czas`)VALUES ('" + id_czuj + "','" + funkcja + "','" + wartosc + "','" + czas + "')");
            System.out.println("Inserted to table: `reguly` value: " + id_czuj + " to column: `id_czuj` and value: " + funkcja + " to column: `funkcja` and value: " + wartosc + " to column: `wartosc` and value: " + czas + " to column: `czas`");
            stat.close();
            conn.close();
            System.out.println("End of DBConnection");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static int Select_All_From_Czujniki() {
        int i = 0;
        try {
            conn = (Connection) DriverManager.getConnection(url, username, password);
//            System.out.println("Start of DBConnection");
            stat = (Statement) conn.createStatement();
            rs = stat.executeQuery("select * from czujniki;");
            while (rs.next()) {
//                System.out.println(rs.getString("id_cz") + " " + rs.getString("nazwa_cz"));
                i++;
            }
            rs.close();
            stat.close();
            conn.close();
//            System.out.println("End of DBConnection");

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    public void Select_All_From_Funkcje_P() {

        try {
            conn = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Start of DBConnection");
            stat = (Statement) conn.createStatement();
            rs = stat.executeQuery("select * from funkcje_p;");
            while (rs.next()) {
                System.out.println(rs.getString("id_f") + " " + rs.getString("nazwa_f"));
            }
            rs.close();
            stat.close();
            conn.close();
            System.out.println("End of DBConnection");

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Select_All_From_Pomiary() {

        try {
            conn = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Start of DBConnection");
            stat = (Statement) conn.createStatement();
            rs = stat.executeQuery("select * from pomiary;");
            while (rs.next()) {
                System.out.println(rs.getString("id_p") + " " + rs.getString("id_pom") + " " + rs.getTimestamp("czas").toString() + " " + rs.getString("wartosc"));
            }
            rs.close();
            stat.close();
            conn.close();
            System.out.println("End of DBConnection");

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Regula[] Select_All_From_Reguly(String where) {
        ArrayList<Regula> reg = new ArrayList<>();

        try {
            conn = (Connection) DriverManager.getConnection(url, username, password);
//            System.out.println("Start of DBConnection");
            stat = (Statement) conn.createStatement();
            rs = stat.executeQuery("select * from reguly where " + where + ";");
            while (rs.next()) {
//                System.out.println(rs.getString("id_r") + " " + rs.getString("funkcja") + " " + rs.getString("id_czuj") + " " + rs.getString("wartosc") + " " + rs.getString("czas"));
                reg.add(new Regula(rs.getInt("funkcja"), rs.getInt("id_czuj"), rs.getFloat("wartosc"), rs.getFloat("czas")));
            }
            rs.close();
            stat.close();
            conn.close();
//            System.out.println("End of DBConnection");

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        Regula[] r = new Regula[reg.size()];
        reg.toArray(r);
        return r;
    }

    public void Select_kolumna_From_tabela(String kolumna, String tabela) {

        try {
            conn = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Start of DBConnection");
            stat = (Statement) conn.createStatement();
            rs = stat.executeQuery("select " + kolumna + " from " + tabela + ";");
            while (rs.next()) {
                System.out.println(rs.getString(kolumna));
            }
            rs.close();
            stat.close();
            conn.close();
            System.out.println("End of DBConnection");

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
