import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;


public class Main {



    public static requests RQ;
    public static storage ST;
    public static readWrite RW;

    public static String path;

    public static void main(String[] args) throws IOException, CsvValidationException {

        path = "D:\\solarAPIOutput\\";
        //path = "\\home\\schorsch007\\DATENSOLAR\\"
        RQ = requests.getRequestsClass();
        ST = storage.getStorageClass();
        RW = readWrite.getReadWriteClass();

        normalExe();


    }

    public static void normalExe() throws IOException, CsvValidationException {

        long startTime = System.currentTimeMillis();
        int counterStore = 0;

        while (true){


            if ((System.currentTimeMillis()-startTime) > 10000){
                RQ.getAbfrage("http://192.168.179.7/solar_api/v1/GetStorageRealtimeData.cgi", "getStorage");
                RQ.getAbfrage("http://192.168.179.7/solar_api/v1/GetPowerFlowRealtimeData.fcgi","PowerFlow");
                startTime = System.currentTimeMillis();
                counterStore ++;
            }


            if (counterStore >  30) {
                RW.storeStart(ST.getPowerFlowList, "getPowerFlowList");
                RW.storeStart(ST.getStorageAkkList, "getStorageAkkList");
                counterStore = 0;
            }



        }

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