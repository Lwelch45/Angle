package models;

import javax.persistence.*;

import com.fasterxml.jackson.databind.JsonNode;
import externals.CNN;
import externals.KimonoLabs;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Results;

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

    @OneToMany(mappedBy = "article")
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
        }
    }

    @Override
    public String toString(){
        return "Article {title=" + title +  ", link=" + link + "}";
    }


}
