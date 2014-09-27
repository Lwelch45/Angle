package externals;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.util.concurrent.RateLimiter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by laurencewelch on 9/27/14.
 */
public class KimonoLabs extends Scraper {

    public String baseUrl = "https://www.kimonolabs.com/api/";
    public Map root = new TreeMap<String,List<String>>();


    public void init(String key, String api){
        if(init) return;
        KimonoLabs.apiKey = "?apikey=nkgN5fDjGPZ9QbdJMGiZuZYARoviv058";
        KimonoLabs.limiter = RateLimiter.create(.2);
    }

    public void scrape(){}

    public Map<String,List<String>> scrape(HashMap<Integer, String> params, String api){
        this.callUrl = baseUrl + api + apiKey;
        for(int i = 0; i < params.size(); i++){
            callUrl+= params.get(i);
        }
        scrape();

        return root;
    }
}

