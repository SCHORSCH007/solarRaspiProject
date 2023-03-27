import java.io.IOException;

import static java.lang.Thread.sleep;


public class Main {

    public static requests RQ;
    public static storage ST;
    public static readWrite RW;

    public static void main(String[] args) throws IOException {

        RQ = requests.getRequestsClass();
        ST = storage.getStorageClass();
        RW = readWrite.getReadWriteClass();

        RQ.getAbfrage("http://192.168.179.7/solar_api/v1/GetStorageRealtimeData.cgi", "getStorage");
        RQ.getAbfrage("http://192.168.179.7/solar_api/v1/GetPowerFlowRealtimeData.fcgi","PowerFlow");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        RQ.getAbfrage("http://192.168.179.7/solar_api/v1/GetStorageRealtimeData.cgi", "getStorage");
        RQ.getAbfrage("http://192.168.179.7/solar_api/v1/GetPowerFlowRealtimeData.fcgi","PowerFlow");

        ST.printStorage();

        System.out.println(ST.getPowerFlowList[0].get(0));
        RW.storeStart(ST.getPowerFlowList, "getPowerFlowList" );
    }





}


