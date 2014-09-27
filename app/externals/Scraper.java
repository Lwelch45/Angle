package externals;

import com.google.common.util.concurrent.RateLimiter;
import util.Http;
import util.Util;

/**
 * Created by laurencewelch on 9/27/14.
 */

public abstract class Scraper {

    public static String apiKey = "";
    public static String serviceUrl = "";
    public String callUrl = "";
    public Http caller;
    static boolean init = false;
    public static RateLimiter limiter;

    public void init(String key, String url){
        Util.mustOverride();
    }
    public abstract void scrape();

}