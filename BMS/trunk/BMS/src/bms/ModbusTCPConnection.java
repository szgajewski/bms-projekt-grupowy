package bms;

import java.net.InetAddress;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.net.TCPMasterConnection;

public class ModbusTCPConnection {

    static ReadInputRegistersRequest req1 = null; //the request
    static ReadInputRegistersRequest req2 = null; //the request
    static ReadInputRegistersResponse res1 = null; //the response
    static ReadInputRegistersResponse res2 = null; //the response
    //static int port = 502;
    static int port = 2502;
    static int ref = 11000; //the reference; offset where to start reading from
    static int count = 2; //the number of DI's to read

    public static float[] read() {

        float[] results = new float[count];
        try {
            //String astr = "192.168.0.121";
            //String astr = "89.73.234.127";
            String astr = "tsunet.dyndns.org";

            InetAddress addr = InetAddress.getByName(astr);
            TCPMasterConnection con = new TCPMasterConnection(addr); //the connection
            ModbusTCPTransaction trans = null; //the transaction

            con.setPort(port);
            con.connect();
            System.out.println("Connected... " + port + " " + ref);

            req1 = new ReadInputRegistersRequest(ref, count);
            req2 = new ReadInputRegistersRequest(ref + 2, count);

            trans = new ModbusTCPTransaction(con);
            trans.setRequest(req1);

            trans.execute();
            res1 = (ReadInputRegistersResponse) trans.getResponse();

            int temp1 = res1.getRegisterValue(1);
            float myFloat = Float.intBitsToFloat(temp1 << 16);


            System.out.println("Temp1=" + myFloat);
            results[0] = myFloat;

            //------------------------------------------------------

            trans.setRequest(req2);;

            trans.execute();
            res2 = (ReadInputRegistersResponse) trans.getResponse();

            int temp2 = res2.getRegisterValue(1);
            float myFloat2 = Float.intBitsToFloat(temp2 << 16);

            System.out.println("Temp2=" + myFloat2);
            results[1] = myFloat2;

            con.close();


        } catch (Exception ex) {
            ex.printStackTrace();
        }


        System.out.println("Zczytywanie zakonczone");
        return results;
    }
    
    
}
