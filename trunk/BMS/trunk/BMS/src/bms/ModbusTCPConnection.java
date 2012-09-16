package bms;

import java.net.InetAddress;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.net.TCPMasterConnection;
import net.wimpi.modbus.procimg.SimpleRegister;

public class ModbusTCPConnection {

    static ReadInputRegistersRequest req1 = null; //the request
    static ReadInputRegistersRequest req2 = null; //the request
    static ReadInputDiscretesRequest req3 = null; //the request
    static ReadInputDiscretesRequest req4 = null; //the request
    static ReadInputRegistersResponse res1 = null; //the response
    static ReadInputRegistersResponse res2 = null; //the response
    static ReadInputDiscretesResponse res3 = null; //the response
    static ReadInputDiscretesResponse res4 = null; //the response
    static WriteSingleRegisterRequest wreq = null; //the request
    static WriteSingleRegisterResponse wres = null; //the response
    static int port = 2502;
//    static int port = 502;
    static int ref = 11000; //the reference; offset where to start reading from
    static int count = 2; //the number of DI's to read
    static int ref2 = 402; //the reference; offset where to start reading from
    static int count2 = 1; //the number of DI's to read
    static int ref3 = 101; //the reference; offset where to start reading from

    public static float[] read() {

        float[] results = new float[count];
        try {
            //String astr = "192.168.0.121";
//            String astr = "89.73.234.127";
            String astr = "tsunet.dyndns.org";

            InetAddress addr = InetAddress.getByName(astr);
            TCPMasterConnection con = new TCPMasterConnection(addr); //the connection
            ModbusTCPTransaction trans = null; //the transaction

            con.setPort(port);
            con.connect();
            System.out.println("Connected... " + port + " " + ref);
            GUI.log("Connected... " + port + " " + ref);
            req1 = new ReadInputRegistersRequest(ref, count);
            req2 = new ReadInputRegistersRequest(ref + 2, count);

            trans = new ModbusTCPTransaction(con);
            trans.setRequest(req1);

            trans.execute();
            res1 = (ReadInputRegistersResponse) trans.getResponse();

            int temp1 = res1.getRegisterValue(1);
            float myFloat = Float.intBitsToFloat(temp1 << 16);


            System.out.println("Temp1=" + myFloat);
            GUI.log("Temp1=" + myFloat);
            results[0] = myFloat;

            //------------------------------------------------------

            trans.setRequest(req2);

            trans.execute();
            res2 = (ReadInputRegistersResponse) trans.getResponse();

            int temp2 = res2.getRegisterValue(1);
            float myFloat2 = Float.intBitsToFloat(temp2 << 16);

            System.out.println("Temp2=" + myFloat2);
            GUI.log("Temp2=" + myFloat2);
            results[1] = myFloat2;
            GUI.setTemp(myFloat, myFloat2);
            con.close();
            GUI.log("Disconnected");

        } catch (Exception ex) {
            System.out.println("Zczytywanie zakonczone");
            GUI.log("Connection Error");
        }


        return results;
    }

    public static boolean heating(int i) {
        boolean b = false;
        try {

            String astr = "tsunet.dyndns.org";
            InetAddress addr = InetAddress.getByName(astr);
            TCPMasterConnection con = new TCPMasterConnection(addr); //the connection
            ModbusTCPTransaction trans = null; //the transaction


            con.setPort(port);
            con.connect();
            System.out.println("Connected... " + port + " " + ref);

            req3 = new ReadInputDiscretesRequest(ref2, count2);

            trans = new ModbusTCPTransaction(con);

            SimpleRegister reg = new SimpleRegister(0);
            wreq = new WriteSingleRegisterRequest(ref2, reg);

            reg.setValue(i);
            trans.setRequest(wreq);
            trans.execute();

            trans.setRequest(req3);
            trans.execute();
            res3 = (ReadInputDiscretesResponse) trans.getResponse();
            System.out.println("Grzanie =" + res3.getDiscretes().toString());
            GUI.log("Heating =" + res3.getDiscretes().toString());

            con.close();

            b = true;
        } catch (Exception ex) {
//            ex.printStackTrace();
        }


        System.out.println("Zczytywanie zakonczone");
        return b;
    }

    public static boolean cooling(int i) {
        boolean b = false;
        try {

            String astr = "tsunet.dyndns.org";
            InetAddress addr = InetAddress.getByName(astr);
            TCPMasterConnection con = new TCPMasterConnection(addr); //the connection
            ModbusTCPTransaction trans = null; //the transaction


            con.setPort(port);
            con.connect();
            System.out.println("Connected... " + port + " " + ref);

            req4 = new ReadInputDiscretesRequest(ref3, count2);

            trans = new ModbusTCPTransaction(con);

            SimpleRegister reg = new SimpleRegister(0);
            wreq = new WriteSingleRegisterRequest(ref3, reg);

            reg.setValue(i);
            trans.setRequest(wreq);
            trans.execute();

            trans.setRequest(req4);
            trans.execute();
            res4 = (ReadInputDiscretesResponse) trans.getResponse();
            System.out.println("Wietrzenie =" + res4.getDiscretes().toString());
            GUI.log("Cooling =" + res4.getDiscretes().toString());

            con.close();

            b = true;
        } catch (Exception ex) {
//                ex.printStackTrace();
        }


        System.out.println("Zczytywanie zakonczone");
        return b;
    }
}
