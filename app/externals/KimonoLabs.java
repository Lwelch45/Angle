package externals;

import com.google.common.util.concurrent.RateLimiter;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by laurencewelch on 9/27/14.
 */
public class KimonoLabs extends Scraper {

    public String baseUrl = "https://www.kimonolabs.com/api/";

    public void init(String key, String api){
        if(init) return;
        KimonoLabs.apiKey = "?apikey=nkgN5fDjGPZ9QbdJMGiZuZYARoviv058";
        KimonoLabs.limiter = RateLimiter.create(.2);
    }

    public void scrape(){}

    public void scrape(HashMap<String, String> params, String api){
        this.callUrl = baseUrl + api + apiKey;

    }
}

