package bms;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    static Connection conn;
    static Statement stat;
    static ResultSet rs;
    String serverName = "sql.parakletos.nazwa.pl";
    String mydatabase = "parakletos_5";
    String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
    String username = "parakletos_5";
    String password = "ProjektGrupowy314151618";

    public void Connect() {

        try {

// Load the JDBC driver?
// String driverName = "org.gjt.mm.mysql.Driver"; // MySQL MM JDBC driver
// Class.forName(driverName);

            conn = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Start");
            conn.close();
            System.out.println("Success");
            System.out.println("End");

        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.toString() + "\n ");
        }


    }

    public void Insert_Into_Czujniki(String nazwa_cz) {

        try {
            conn = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Start of DBConnection");

            stat = (Statement) conn.createStatement();
            stat.executeUpdate("INSERT INTO  `czujniki` (`nazwa_cz`)VALUES ('" + nazwa_cz + "')");
            System.out.println("Inserted to table: `czujniki` value: " + nazwa_cz + " to column: `nazwa_cz`");

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

            conn.close();
            System.out.println("End of DBConnection");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Select_All_From_Czujniki() {

        try {
            conn = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Start of DBConnection");
            stat = (Statement) conn.createStatement();
            rs = stat.executeQuery("select * from czujniki;");
            while (rs.next()) {
                System.out.println(rs.getString("id_cz") + " " + rs.getString("nazwa_cz"));
            }
            rs.close();

            conn.close();
            System.out.println("End of DBConnection");

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

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

            conn.close();
            System.out.println("End of DBConnection");

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Select_All_From_Reguly() {

        try {
            conn = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Start of DBConnection");
            stat = (Statement) conn.createStatement();
            rs = stat.executeQuery("select * from reguly;");
            while (rs.next()) {
                System.out.println(rs.getString("id_r") + " " + rs.getString("id_czuj") + " " + rs.getString("funkcja") + " " + rs.getString("wartosc") + " " + rs.getString("czas"));
            }
            rs.close();

            conn.close();
            System.out.println("End of DBConnection");

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

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

            conn.close();
            System.out.println("End of DBConnection");

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
