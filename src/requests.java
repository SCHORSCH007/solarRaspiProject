import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class requests {

                //ab hier alles für singleton
    public static requests instance = null;

    public static requests getRequestsClass()
    {
        if (instance == null){
           instance = new requests();
        }
        return instance;

    }
    public requests ()
    {}
                //bis hier alles für singleton


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

        //System.out.println(ueberstezungJson);
        whichData(ueberstezungJson, requestContent);
    }


    //beim Hinzufügen hier keywords einfügen und storage anpassen
    public static void whichData(String Json, String requestContent) {
        switch (requestContent) {
            case "getStorage":
                datenAnalysieren(Json, requestContent, "StateOfCharge_Relative", "Temperature_Cell", "Timestamp");
                break;
            case "PowerFlow":
                datenAnalysieren(Json, requestContent, "P_Akku", "P_Grid", "P_Load", "P_PV", "rel_Autonomy", "rel_SelfConsumption", "Timestamp" );
                break;
            case "three":
                //
                break;
            default:

        }

    }

    public static void datenAnalysieren(String Json, String requestContent, String... args) {
        List<String> keywords = new ArrayList<>();
        List<String> information = new ArrayList<>();
        String [][] data;


        for (String arg : args) {
            keywords.add(arg);
            //System.out.println(arg);
        }

        data = new String[2][keywords.size()];  //new String[keywords.size()][keywords.size()];

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
                            if (splitv2[z+1].contains("\n"))
                            {
                                String[] splitv3 = splitv2[z+1].split("\n");
                                information.add(splitv3[0]);

                            }
                            else {
                                information.add(splitv2[z + 1]);
                            }
                        }
                    }
                }
            }
        }

        //die keywords/informationen werden in Arrays eingefügt   [0;x] -> keywords | [1;x] informationen
        for (int i = 0; i<keywords.size(); i++) {
            data [0][i] = keywords.get(i);
            data [1][i] = information.get(i);
            //System.out.println("-------");
            //System.out.println(i);
            //System.out.println(keywords.get(i));
            //System.out.println(information.get(i));

        }

        Main.ST.choseStorage(requestContent, data);
    }
}
