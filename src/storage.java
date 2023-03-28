import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class storage {

    public List<String>[] getStorageAkkList = new List[3];  // [0]StateOfCharge_Relative, [1]Temperature_Cell ,[2]Timestamp
    public List<String>[] getPowerFlowList = new List[7];  // [0]P_Akku, [1]P_Grid , [2]P_Load, [3] P_PV, [4]rel_Autonomy, [5]rel_SelfConsumption, [6]Timestamp


                //ab hier alles für singleton
    public static storage instance = null;

    public static storage getStorageClass()
    {
        if (instance == null){
            instance = new storage();
        }
        return instance;

    }
    public storage ()
    {
        for (int i = 0; i< getStorageAkkList.length; i++)
        {
            getStorageAkkList[i] = new ArrayList<>();
        }
        for (int i = 0; i< getPowerFlowList.length; i++)
        {
            getPowerFlowList[i] = new ArrayList<>();
        }

    }
                //bis hier alles für singleton





    public void choseStorage (String requestContent, String [][] data){
        switch (requestContent) {
            case "getStorage":
                    store(data, getStorageAkkList);
                break;
            case "PowerFlow":
                    store(data, getPowerFlowList);
                break;
            case "three":
                //
                break;
            default:

        }
    }

    public void store (String [][] data, List<String>[] list){
        for (int i = 0; i < list.length; i++){
            list[i].add(data[1][i].replaceAll("\"", ""));
        }
    }

    public void printStorage()
    {
        for(int i = 0; i < getStorageAkkList.length; i++)
        {
            for(int z = 0; z < getStorageAkkList[i].size(); z++) {
                System.out.println(getStorageAkkList[i].get(z));
            }
        }
        System.out.println("-------------------------------------------------");
        for(int i = 0; i < getPowerFlowList.length; i++)
        {
            for(int z = 0; z < getPowerFlowList[i].size(); z++) {
                System.out.println(getPowerFlowList[i].get(z));
            }
        }

    }

}
