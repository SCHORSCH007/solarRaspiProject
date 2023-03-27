import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
public class readWrite {


                        //ab hier alles für singleton
    public static readWrite instance = null;

    public static readWrite getReadWriteClass()
    {
        if (instance == null){
            instance = new readWrite();
        }
        return instance;

    }
    public readWrite ()
    {}      //bis hier alles für singleton

    // getStorageAkkList    [0]StateOfCharge_Relative, [1]Temperature_Cell ,[2]Timestamp
    // getPowerFlowList     [0]P_Akku, [1]P_Grid , [2]P_Load, [3] P_PV, [4]rel_Autonomy, [5]rel_SelfConsumption, [6]Timestamp



    public void storeStart(List<String>[] list, String listName) throws IOException {  //useless method maybe for later
        switch (listName) {
            case "getStorageAkkList":
                    writeCVSFile(list, listName);
                break;
            case "getPowerFlowList":
                    writeCVSFile(list, listName);
                break;
            default:
        }

    }

    public void writeCVSFile(List<String>[] list, String listName) throws IOException {

        String date = list[list.length-1].get(0);
        date = date.substring(1,10);
        String path = getFilePath(date, listName);
        boolean datenDavorEinfügen = false;
        List<String>[] listBeforeData = null;

        if (false) { //isFileExist(path)
            listBeforeData = list.clone();
            for(int i = 0; i < list.length; i++){
                list[i].clear();
            }
            datenDavorEinfügen = true;
            listBeforeData = readCVSFile(listBeforeData, path, listName);

            File fileBefore = new File(path);
            fileBefore.delete();

        }

        //create file
        File file = new File(path);


        // create FileWriter object with file as parameter
        FileWriter outputfile = new FileWriter(file);

        // create CSVWriter object filewriter object as parameter
        CSVWriter writer = new CSVWriter(outputfile);

        // adding header to csv
        switch (listName) {
            case "getStorageAkkList":
                String[] headergs = {"StateOfCharge_Relative", "Temperature_Cell", "Timestamp"};
                writer.writeNext(headergs);
                break;
            case "getPowerFlowList":
                String[] headergf = {"P_Akku", "P_Grid", "P_Load", "P_PV", "rel_Autonomy", "rel_SelfConsumption", "Timestamp"};
                writer.writeNext(headergf);
                break;
            default:
        }

        if (datenDavorEinfügen && listBeforeData != null){
            while(listBeforeData[0].size() > 0) {

                // add data to csv
                String[] data = new String[listBeforeData.length];
                for (int i = 0; i < listBeforeData.length; i++) {
                    data [i] = listBeforeData[i].get(0);
                    listBeforeData[i].remove(0);
                }
                writer.writeNext(data);
            }
        }

        System.out.println(list[0].get(0));
        System.out.println(date);
        System.out.println(list[list.length-1].get(0).substring(1,10));

        while(list[0].size() > 0) {

            if(!date.equals(list[list.length-1].get(0).substring(1,10))) { //falls Datensatz von neuem Tag
                writeCVSFile(list, listName);
                return;
            }

            // add data to csv
            String[] data = new String[list.length];
            for (int i = 0; i < list.length; i++) {
                data [i] = list[i].get(0);
                list[i].remove(0);
            }
            writer.writeNext(data);
        }

        // closing writer connection
        writer.close();
    }

    public List<String>[] readCVSFile( List<String>[] listBeforeData, String path, String listname) throws IOException {

        if (true){
        return null;}

        Path filepath = Paths.get(path);

        List<String[]> list = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(filepath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    list.add(line);
                }
            } catch (CsvValidationException e) {
                throw new RuntimeException(e);
            }
        }

        for(int i = 0; i< list.size(); i++){
            for (int f = 0; f < list.get(i).length; f++){
                listBeforeData[i].add(list.get(i)[f]);
            }
        }


        return listBeforeData;
    }

    public String getFilePath(String date, String listName){
        String fp = "D:\\solarAPIOutput\\" + listName + date;
        return fp;
    }

    public boolean isFileExist(String path) {
        File f = new File(path);
        if (f.exists()) {
            return true;
        }
        else {
            return false;
        }

    }

}
