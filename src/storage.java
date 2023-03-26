import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class storage {

    public List<String>[] getStorageAkkList = new List[2];



                //ab hier alles für singleton
    public static storage instance = null;

    public static storage getStorageClass()
    {
        if (instance != null){
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

    }
                //bis hier alles für singleton





    public void choseStorage (String requestContent, String [][] data){
        switch (requestContent) {
            case "getStorage":
                    store(data, getStorageAkkList);
                break;
            case "two":
                //
                break;
            case "three":
                //
                break;
            default:

        }
    }

    public void store (String [][] data, List<String>[] list){
        for (int i = 0; i < list.length; i++){
            list[i].add(data[1][i]);
        }
    }

}
