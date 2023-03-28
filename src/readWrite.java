import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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



    public void storeStart(List<String>[] list, String listName) throws IOException, CsvValidationException {  //useless method maybe for later
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

    public void writeCVSFile(List<String>[] list, String listName) throws IOException, CsvValidationException {

        String date = list[list.length-1].get(0);
        date = date.substring(0,10);
        String path = getFilePath(date, listName);

        File f = isFileExist(path);
        if (f != null) {
            System.out.println("file already exists");
            list = readCVSFile(list, path, listName, f);

        }

        //create file
        File file = new File(path);


        // create FileWriter object with file as parameter
        FileWriter outputfile = new FileWriter(file);

        // create CSVWriter object filewriter object as parameter
        CSVWriter writer = new CSVWriter(outputfile);

        //writes header
        writer.writeNext(getHeaderFN(listName));


        while(list[0].size() > 0) {

            if(!date.equals(list[list.length-1].get(0).substring(0,10))) { //falls Datensatz von neuem Tag
                writer.close();
                writeCVSFile(list, listName);
                return;
            }

            // add data to csv
            String[] data = new String[list.length];
            for (int i = 0; i < list.length; i++) {
                data [i] = list[i].get(0);
                list[i].remove(0);
            }
            System.out.println(data[data.length-1]);
            writer.writeNext(data);
        }

        // closing writer connection
        writer.close();
    }



    public List<String>[] readCVSFile( List<String>[] list, String path, String listname, File file) throws IOException, CsvValidationException {

        //File file = new File(path);
        FileReader inputfile = new FileReader(path);
        BufferedReader br = new BufferedReader(inputfile);


        br.readLine(); // header

        String row = null;
        String [] line = new String [list.length];


        while ((row = br.readLine()) != null) {
            int counter = 0;

            line = row.split(",");

            for (int i = 0; i< line.length; i++){
                list[i].add(counter, line[i].replaceAll("\"", ""));
                System.out.println(line[i]);
            }
            counter = counter +1;
        }

        file.delete();
        br.close();

        return list;
    }

    public String getFilePath(String date, String listName){
        String fp = "D:\\solarAPIOutput\\" + listName + date + ".csv";
        return fp;
    }

    public File isFileExist(String path) {
        File f = new File(path);
       if (f.isFile())
       {
           return f;
       }
        else{

           return null;
       }
    }

    private String[] getHeaderFN(String listName) {
        // adding header to csv
        switch (listName) {
            case "getStorageAkkList":
                String[] headergs = {"StateOfCharge_Relative", "Temperature_Cell", "Timestamp"};
                return headergs;
            case "getPowerFlowList":
                String[] headergf = {"P_Akku", "P_Grid", "P_Load", "P_PV", "rel_Autonomy", "rel_SelfConsumption", "Timestamp"};
                return headergf;
            default:
                return null;
        }
    }

}
