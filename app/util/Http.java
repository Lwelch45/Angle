package util;


import com.fasterxml.jackson.databind.JsonNode;
import play.libs.F;
import play.libs.ws.WS;
import play.libs.ws.WSRequestHolder;
import play.libs.ws.WSResponse;
/**
 * Created by laurence on 9/24/2014.
 */
public class Http {
    public static final Integer TIMEOUT = 120000; //120 seconds
    public WSRequestHolder holder;

    public Http(String url){
        holder = url(url);
    }

    public static WSRequestHolder url(String url){ return WS.url(url);}

    public static F.Promise<WSResponse> get(WSRequestHolder holder){
        return holder.get();
    }

    public static F.Promise<WSResponse> post(WSRequestHolder holder, JsonNode data){
        return holder.post(data);
    }

    public static F.Promise<WSResponse> post(WSRequestHolder holder, String data){
        return holder.post(data);
    }

    public static F.Promise<WSResponse> put(WSRequestHolder holder, JsonNode data){
        return holder.put(data);
    }

    public static F.Promise<WSResponse> put(WSRequestHolder holder, String data){
        return holder.put(data);
    }
}