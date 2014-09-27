package util;


/**
 * Created by laurencewelch on 9/27/14.
 */
import org.jsoup.Jsoup;

import java.util.List;

public class Util {
    public static void mustOverride() {
        throw new UnsupportedOperationException("Must implement");
    }

    public static String clean(String html) {
        return Jsoup.parse(html).text();
    }

    public static String ListToString(List<String> list){
        String s = "";
        for(String ss : list){
            s+= ss;
        }
        return s;
    }

}