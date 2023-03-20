import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {

        getAbfrage("http://192.168.179.7/solar_api/v1/GetStorageRealtimeData.cgi", "getStorage");
    }


    //Abfrage wird gestartet mit der übergebenen URL und der Kontextinformation(Was wird abgefragt)
    public static void getAbfrage(String urlEingabe, String requestContent) throws IOException {

        String urlString = urlEingabe;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();

        readInputStream(is, requestContent); //inputstream wird in anderer Methode analysiert
    }

    //Json input stream wird in ein String eingefügt
    public static void readInputStream(InputStream is, String requestContent) throws IOException {

        String ueberstezungJson = null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;

        while ((line = reader.readLine()) != null) {
            ueberstezungJson = ueberstezungJson + "\n" + line;
        }
        reader.close();

        System.out.println(ueberstezungJson);
        whichData(ueberstezungJson, requestContent);
    }


    public static void whichData(String Json, String requestContent) {
        switch (requestContent) {
            case "getStorage":
                datenAnalysieren(Json, "StateOfCharge_Relative", "Capacity_Maximum");
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

    public static void datenAnalysieren(String Json, String... args) {
        List<String> keywords = new ArrayList<>();
        List<String> information = new ArrayList<>();
        String [][] data;


        for (String arg : args) {
            keywords.add(arg);
        }

        data = new String[keywords.size()][keywords.size()];

        /*
        Dieses wilde Konstrukt sollte alle Informationen zu allen Keywords liefern,
          sofern es der Formatierung ---  "[keyword]" : [information], --- unterliegt

          - die Informationen und keywords sollten sogar in den gleichen indezees der Listen stehen
        */

        String[] split = Json.split(",");
        for (int f = 0; f < keywords.size(); f++){
            for (int i = 0; i < split.length; i++) {
                if (split[i].contains((CharSequence) keywords.get(f))) {
                    String[] splitv2 = split[i].split(" ");

                    for (int z = 0; z < splitv2.length; z++) {
                        if (splitv2[z].contains(":")) {
                            information.add(splitv2[z + 1]);
                        }
                    }
                }
            }
        }

        //die keywords/informationen werden in Arrays eingefügt   [0;x] -> keywords | [1;x] informationen
        for (int i = 0; i<keywords.size(); i++) {
            data [0][i] = keywords.get(i);
            data [1][i] = information.get(i);
        }
    }
}


