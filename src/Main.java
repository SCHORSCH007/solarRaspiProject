import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static requests RQ;
    public static storage ST;
    public static readWrite RW;

    public static void main(String[] args) throws IOException {

        RQ = requests.getRequestsClass();
        ST = storage.getStorageClass();
        RW = readWrite.getReadWriteClass();

        RQ.getAbfrage("http://192.168.179.7/solar_api/v1/GetStorageRealtimeData.cgi", "getStorage");


    }




}

