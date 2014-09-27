package util;


/**
 * Created by laurencewelch on 9/27/14.
 */
import org.jsoup.Jsoup;
public class Util {
    public static void mustOverride() {
        throw new UnsupportedOperationException("Must implement");
    }

    public static String clean(String html) {
        return Jsoup.parse(html).text();
    }
}