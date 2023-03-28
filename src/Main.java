import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


public class Main {

    public static requests RQ;
    public static storage ST;
    public static readWrite RW;

    public static void main(String[] args) throws IOException, CsvValidationException {




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

        RW.storeStart(ST.getPowerFlowList, "getPowerFlowList" );

        RW.storeStart(ST.getStorageAkkList, "getPowerFlowList" );

    }

}

/*
RW = readWrite.getReadWriteClass();

        List<String>[] getPowerFlowList = new List[7];

        for (int i = 0; i< getPowerFlowList.length; i++)
        {
            getPowerFlowList[i] = new ArrayList<>();
        }

        // [0]P_Akku, [1]P_Grid , [2]P_Load, [3] P_PV, [4]rel_Autonomy, [5]rel_SelfConsumption, [6]Timestamp

        for(int i = 0; i< getPowerFlowList.length; i++){
            switch (i) {
                case 0:
                    getPowerFlowList[i].add("91");
                    getPowerFlowList[i].add("92");
                    getPowerFlowList[i].add("93");
                    getPowerFlowList[i].add("91");
                    getPowerFlowList[i].add("92");
                    getPowerFlowList[i].add("93");
                    break;
                case 1:
                    getPowerFlowList[i].add("120");
                    getPowerFlowList[i].add("130");
                    getPowerFlowList[i].add("140");
                    getPowerFlowList[i].add("120");
                    getPowerFlowList[i].add("130");
                    getPowerFlowList[i].add("140");
                    break;
                case 2:
                    getPowerFlowList[i].add("210");
                    getPowerFlowList[i].add("220");
                    getPowerFlowList[i].add("230");
                    getPowerFlowList[i].add("210");
                    getPowerFlowList[i].add("220");
                    getPowerFlowList[i].add("230");
                    break;
                case 3:
                    getPowerFlowList[i].add("310");
                    getPowerFlowList[i].add("320");
                    getPowerFlowList[i].add("330");
                    getPowerFlowList[i].add("310");
                    getPowerFlowList[i].add("320");
                    getPowerFlowList[i].add("330");
                    break;
                case 4:
                    getPowerFlowList[i].add("80");
                    getPowerFlowList[i].add("90");
                    getPowerFlowList[i].add("99");
                    getPowerFlowList[i].add("80");
                    getPowerFlowList[i].add("90");
                    getPowerFlowList[i].add("99");
                    break;
                case 5:
                    getPowerFlowList[i].add("10");
                    getPowerFlowList[i].add("20");
                    getPowerFlowList[i].add("30");
                    getPowerFlowList[i].add("10");
                    getPowerFlowList[i].add("20");
                    getPowerFlowList[i].add("30");
                    break;
                case 6:
                    getPowerFlowList[i].add("2023-03-27T16:14:17+00:00");
                    getPowerFlowList[i].add("2023-03-27T16:14:17+00:00");
                    getPowerFlowList[i].add("2023-03-27T16:14:17+00:00");
                    getPowerFlowList[i].add("2023-03-28T16:14:17+00:00");
                    getPowerFlowList[i].add("2023-03-28T16:14:17+00:00");
                    getPowerFlowList[i].add("2023-03-28T16:14:17+00:00");
                    break;
                default:

            }
        }

      //  getPowerFlowList[getPowerFlowList.length-1].add(3,"12023-03-28T16:14:17+00:00");
       // getPowerFlowList[getPowerFlowList.length-1].add(3,"12023-03-28T16:14:17+00:00");

        System.out.println(getPowerFlowList[0].get(0));
        RW.storeStart(getPowerFlowList, "getPowerFlowList" );
 */
