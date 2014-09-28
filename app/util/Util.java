package util;


/**
 * Created by laurencewelch on 9/27/14.
 */
import models.Theme;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Util {
    public static void mustOverride() {
        throw new UnsupportedOperationException("Must implement");
    }

    public static String clean(String html) {
        return Jsoup.parse(html).text();
    }

    public static String ThemeToString(List<Theme> list){
        String s = "";
        for(Theme ss : list){
            s+= ss.name;
        }
        return s;
    }

    public static List<String> ThemeToList(List<Theme> list){
        ArrayList<String> s = new ArrayList<String>();
        for(Theme ss : list){
            s.add(ss.name);
        }
        return s;
    }
    public static String ListToString(List<String> list){
        String s = "";
        for(String ss : list){
            s+= ss;
        }
        return s;
    }
    public static String ListToString(List<String> list, String delimeter){
        String s = "";
        for(String ss : list){
            s+= ss + delimeter;
        }
        return s;
    }

    public static Double calcTimeDiffernce(Date A, Date B){
        Long res = A.getTime() - B.getTime();
        if (res < 8.64e+7) return 0.0;
        if (res < 6.048e+8) return .5;
        if (res > 6.048e+8) return 1.0;
      return 1.0;
    }
}