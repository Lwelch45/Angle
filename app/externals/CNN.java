package externals;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import play.Logger;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WSResponse;
import util.Http;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by laurencewelch on 9/27/14.
 */
public class CNN extends KimonoLabs {


    public CNN(){
        init("","");

    }

    @Override
    public void scrape(){
        Map map = new TreeMap<String,List<String>>();

        Logger.info(callUrl);
        this.caller = new Http(callUrl);
        F.Promise<WSResponse> response = this.caller.get(this.caller.holder);
        JsonNode node = response.get(this.caller.TIMEOUT).asJson();

        if (node.get("results").get("collection3") != null){
            ArrayList<String> ans = new ArrayList<String>();
            for(int i = 0; i < node.get("results").get("collection3").size(); i++){
                JsonNode n = node.get("results").get("collection3").get(i);
                if (n != null){
                        //Logger.info(n.get("property31").toString());
                        ans.add(n.get("property31").toString());
                }
            }
            map.put("Highlights", ans);
        }
        if (node.get("results").get("collection4") != null) {
            ArrayList<String> ans = new ArrayList<String>();
            for (int i = 0; i < node.get("results").get("collection4").size(); i++) {
                JsonNode n = node.get("results").get("collection4").get(i);
                if (n != null) {
                        //Logger.info(n.get("Body").get("text").toString());
                        ans.add(n.get("Body").get("text").toString());
                }
            }
            map.put("Bodys", ans);
        }
        if (node.get("results").get("collection5") != null) {
            ArrayList<String> ans = new ArrayList<String>();
            for (int i = 0; i < node.get("results").get("collection5").size(); i++) {
                JsonNode n = node.get("results").get("collection5").get(i);
                if (n != null) {

                    //Logger.info(n.get("property6").get("src").toString());
                    ans.add(n.get("property6").get("src").toString());

                }
            }
            map.put("Thumbnails", ans);
        }
        root = map;
    }
}
