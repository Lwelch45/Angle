package externals;

import com.fasterxml.jackson.databind.JsonNode;
import play.Play;
import play.libs.Json;
import util.Util;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laurencewelch on 9/28/14.
 */
public class Rhine {

    public static final String baseUrl = "api.rhine.io/"+ Play.application().configuration().getString("rhineAPI") + "/";
    public static final String distance = "distance/";
    public static final String key_entity = "entity_extraction";
    public static final String closest_entity = "closest_entities";

    public static double fetchDistance(List<String> a, List<String> b){
        String itemA = Util.ListToString(a, ",");
        String itemB = Util.ListToString(b, ",");
        return fetchDistance(itemA, itemB);
    }
    public static double fetchDistance(String a, String b){
        String url = baseUrl + distance + a + "/" + b;
        String res = GET(url);
        JsonNode jNode = Json.parse(res);
        Double result = jNode.get("Result").asDouble();
        return convert(result);
    }

    public static List<String> fetchEntities(List<String> a){
        String itemA = Util.ListToString(a, ",");
        return fetchEntities(itemA);
    }

    public static List<String> fetchEntities(String a){
        String url = baseUrl + key_entity+ a;

        return new ArrayList<String>();
    }

    public static String GET(String url){
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
            return inputLine;
        }catch (Exception e){}
        return "";
    }
    public static Double convert(Double conv){
        if(Double.isNaN(conv)) return 1.0;
        if (0.0<= conv && conv>= 15) return 0.0;
        if (15.0< conv && conv>= 40) return .25;
        if (40.0< conv && conv>= 100.0) return 50.0;
        if (100< conv) return .750;
        return .25;
    }

}
