package models;

import javax.persistence.*;
import javax.xml.crypto.Data;

import com.fasterxml.jackson.databind.JsonNode;
import externals.*;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Results;
import util.Util;

import java.util.*;

/**
 * Created by laurencewelch on 9/26/14.
 */
@Entity
public class NewsArticle extends Model{
    @Id
    Long id;
    public String api;
    public String title; //
    public String desription; //blurb about article
    public String link; //link to article
    public String hash; // MD5 hash of the title used for comparison.
    public List<String> body; // body of article
    public Date pubDate;
    public List<String> highlights;
    public List<String> thumbnails;
    public List<String> images;

    @ManyToOne
    public List<EntityAnalysis> features;
    public static Finder<Long, NewsArticle> find =new Finder<Long, NewsArticle>(Long.class, NewsArticle.class);
    public static NewsArticle findByHash(String hash){
        return find.where().eq("hash", hash).findList().get(0);
    }
    public NewsArticle(){}

    public NewsArticle(String t, String d, String l, Date pubDate){
        title = t;
        desription = d;
        link = l;
        this.pubDate = pubDate;
        features  = new ArrayList<EntityAnalysis>();
        body = new ArrayList<String>();
    }

    public NewsArticle(String t, String d, String l, Date pubDate, String api){
        title = t;
        desription = d;
        link = l;
        this.pubDate = pubDate;
        this.api = api;
        features  = new ArrayList<EntityAnalysis>();
        body = new ArrayList<String>();
    }

    @ManyToOne
    public NewsSource source;

    public EntityAnalysis analyze(){
        Semantria s = new Semantria();
        EntityAnalysis ea = s.analyze(this);
        return new EntityAnalysis();
    }


    public void getText(){

        if (api.matches("6wuzfqug")){
            CNN scraper = new CNN();
            String[] split = link.split("/");
            //System.out.println(split[7]);
            HashMap<Integer, String> map = new HashMap<Integer, String>();
            map.put(0,"&kimpath3=" +  split[5]);
            map.put(1,"&kimpath5="+ split[7]);
            scraper.scrape(map, api);
            Map<String, List<String>> details = scraper.root;
            if(details.containsKey("Highlights"))
                highlights = details.get("Highlights");
            if(details.containsKey("Bodys"))
                body = details.get("Bodys");
            if(details.containsKey("Thumbnails"))
                thumbnails = details.get("Thumbnails");
            System.out.println(body.size());
        }
        if (api.matches("38toxyju")){
            NYT scraper = new NYT();
            String[] split = link.split("/");
            //System.out.println(split[7]);
            HashMap<Integer, String> map = new HashMap<Integer, String>();
            map.put(0,"&kimpath2="+ split[4]);
            map.put(1,"&kimpath4="+ split[6]);
            map.put(0,"&kimpath6="+ split[8]);
            map.put(1,"&kimpath10="+ split[10]);
            map.put(1,"&kimpath12="+ split[12]);
            scraper.scrape(map, api);
            Map<String, List<String>> details = scraper.root;
            if(details.containsKey("Highlights"))
                highlights = details.get("Highlights");
            if(details.containsKey("Bodys"))
                body = details.get("Bodys");
            if(details.containsKey("Thumbnails"))
                thumbnails = details.get("Thumbnails");
            System.out.println(body.size());
        }
    }

    public static Double calcDistance(NewsArticle A, NewsArticle B){
        Double timeDiff = Util.calcTimeDiffernce(A.pubDate,B.pubDate);
        Double themeDiff = 0.5;
        if(A.features != null && A.features.get(1).themes != null && A.features.get(1).themes.size() > 3 && B.features != null && B.features.get(1).themes != null && B.features.get(1).themes.size() > 3){
            double e = Rhine.fetchDistance(Util.ThemeToList(A.features.get(1).themes),Util.ThemeToList (B.features.get(1).themes));
            for(int x = 0; x <3;x++) {
                e +=Rhine.fetchDistance(A.features.get(1).themes.get(x).name,B.features.get(1).themes.get(x).name);
            }
            themeDiff = e/4;
       }
        Double topicDiff = 0.5;
        if(A.features != null && A.features.get(1).category != null && B.features != null && B.features.get(1).category != null){
            double e = Rhine.fetchDistance(A.features.get(1).category,B.features.get(1).category);

            topicDiff = e;
        }
        Double EntityDiff = 0.5;
        if(A.features != null && A.features.get(0).entities != null && A.features.get(1).entities.size() > 3 && B.features != null && B.features.get(1).entities != null && B.features.get(1).entities.size() > 3){
            double e = Rhine.fetchDistance(A.features.get(0).entities, B.features.get(1).entities);

            EntityDiff = e;
        }
        try{
           Thread.sleep(2000);
        }catch(Exception ex){}
        return 0.0;
    }

    @Override
    public String toString(){
        return "Article {title=" + title +  ", link=" + link + "}";
    }


}
