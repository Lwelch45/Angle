package models;

import javax.persistence.*;

import externals.CNN;
import externals.KimonoLabs;
import play.db.ebean.Model;

import java.util.Date;

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
    public String body; // body of article
    public Date pubDate;

    public static Finder<Long, NewsArticle> find =new Finder<Long, NewsArticle>(Long.class, NewsArticle.class);
    public static NewsArticle findByHash(String hash){
        return find.where().eq("hash", hash).findList().get(0);
    }
    public NewsArticle(){}

    public NewsArticle(String t, String d, String l, Date pubDate){}

    @ManyToOne
    public NewsSource source;

    public void getText(){
        CNN scraper = new CNN();
        String[] split = link.split("/?");
        System.out.println(split[0]);

        int t = 0;
        t++;
        //scraper.scrape(, api);
    }

    @Override
    public String toString(){
        return "Article {title=" + title +  ", link=" + link + "}";
    }


}
