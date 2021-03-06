package models;

import  play.Logger;
import javax.persistence.*;
import play.api.libs.Crypto;
import play.Play;
import play.db.ebean.Model;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import util.Util;

import java.util.Date;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by laurencewelch on 9/26/14.
 */
@Entity
public class NewsSource extends Model {

    @Id
    Long id;

    String name;
    String description;
    String feed; // feed url
    Date lastUpdated = new Date(); // keep track of when the system was updated.

    @OneToMany(mappedBy = "source")
    List<NewsArticle> articles;

    public static Finder<Long, NewsSource> find =new Finder<Long, NewsSource>(Long.class, NewsSource.class);


    public NewsSource(){}
    public NewsSource(String n, String desc, String feed){
        this.name = n;
        this.description = desc;
        this.feed = feed;
    }
    public NewsSource(String n, String feed){
        this.name = n;
        this.feed = feed;
    }

    public void fetch(){
        lastUpdated =  new Date();
        try {
            URL u = new URL(feed);
            HttpURLConnection httpcon = (HttpURLConnection)u.openConnection();

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(httpcon));
            List entries = feed.getEntries();
            Iterator itEntries = entries.iterator();
            while (itEntries.hasNext()) {
                SyndEntry entry = (SyndEntry)itEntries.next();
                System.out.println("Title: " + entry.getTitle());
                System.out.println("Link: " + entry.getLink());
                System.out.println("Author: " + entry.getAuthor());
                System.out.println("Publish Date: " + entry.getPublishedDate());
                System.out.println("Description: " + entry.getDescription().getValue());
                NewsArticle na = new NewsArticle(entry.getTitle(), Util.clean(entry.getDescription().getValue()),entry.getLink(),entry.getPublishedDate());
                na.hash = Crypto.encryptAES(na.title);
                if (NewsArticle.findByHash(na.hash) == null){
                    na.save();
                    na.getText();
                    na.analyze();
                }
            }
        }catch (Exception ex){
            Logger.debug(ex.toString());
        }
    }

    public void updateSet(Boolean force){
        Date currentDate = new Date();
        if(currentDate.getTime() - lastUpdated.getTime() > Play.application().configuration().getLong("updateInterval") || force)
            fetch();
    }
    public static void updateEntireSet(Boolean force){
        Date currentDate = new Date();
        for (NewsSource s : NewsSource.find.all())
           s.updateSet(force);
    }

    public static void init(){

        //CNN
        NewsSource n = new NewsSource("CNN-Top Stories","http://rss.cnn.com/rss/cnn_topstories.rss", "6wuzfqug" );
        n.save();
        n = new NewsSource("CNN-World","http://rss.cnn.com/rss/cnn_world.rss", "6wuzfqug" );
        n.save();
        n = new NewsSource("CNN-U.S","http://rss.cnn.com/rss/cnn_us.rss", "6wuzfqug" );
        n.save();
        n = new NewsSource("CNN-Business","http://rss.cnn.com/rss/money_latest.rss", "6wuzfqug" );
        n.save();
        n = new NewsSource("CNN-Politics","http://rss.cnn.com/rss/cnn_allpolitics.rss", "6wuzfqug" );
        n.save();
        n = new NewsSource("CNN-Crime","http://rss.cnn.com/rss/cnn_crime.rss", "6wuzfqug" );
        n.save();
        n = new NewsSource("CNN-Technology","http://rss.cnn.com/rss/cnn_tech.rss", "6wuzfqug" );
        n.save();
        n = new NewsSource("CNN-Health","http://rss.cnn.com/rss/cnn_health.rss", "6wuzfqug" );
        n.save();
        n = new NewsSource("CNN-Travel","http://rss.cnn.com/rss/cnn_travel.rss", "6wuzfqug" );
        n.save();
        n = new NewsSource("CNN-Living","http://rss.cnn.com/rss/cnn_living.rss","6wuzfqug" );
        n.save();
        n = new NewsSource("CNN-Video","http://rss.cnn.com/rss/cnn_freevideo.rss", "6wuzfqug" );
        n.save();
        n = new NewsSource("CNN-Most Popular","http://rss.cnn.com/rss/cnn_mostpopular.rss", "6wuzfqug" );
        n.save();
        n = new NewsSource("CNN-Most Recent","http://rss.cnn.com/rss/cnn_latest.rss", "6wuzfqug" );
        n.save();

        //NYTIMES
        //n = new NewsSource("NYT-World","http://www.nytimes.com/services/xml/rss/nyt/World.xml","38toxyju" );
        //n.save();
        //n = new NewsSource("NYT-U.S","http://www.nytimes.com/services/xml/rss/nyt/US.xml", "38toxyju" );
        //n.save();
        //n = new NewsSource("NYT-Technology","hhttp://rss.nytimes.com/services/xml/rss/nyt/Technology.xml", "38toxyju" );
        //n.save();
        //n = new NewsSource("NYT-Business","http://rss.nytimes.com/services/xml/rss/nyt/Business.xml", "38toxyju" );
        //n.save();
        //n = new NewsSource("NYT-Politics","http://www.nytimes.com/services/xml/rss/nyt/Politics.xml", "38toxyju" );
        //n.save();

        //n = new NewsSource("","" );
        //n.save();

    }

}
