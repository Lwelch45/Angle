package controllers;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import models.NewsArticle;
import play.Logger;
import play.api.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Result;

import util.Util;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 * Created by laurencewelch on 9/27/14.
 */
public class Test extends Controller {

    public static Result checkFeedFormat(){
        // http://rss.cnn.com/rss/cnn_topstories.rss
        try {
            URL u = new URL("http://www.nytimes.com/services/xml/rss/nyt/World.xml");
            HttpURLConnection httpcon = (HttpURLConnection) u.openConnection();

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(httpcon));
            List entries = feed.getEntries();
            Iterator itEntries = entries.iterator();
            while (itEntries.hasNext()) {
                SyndEntry entry = (SyndEntry) itEntries.next();
                System.out.println("Title: " + entry.getTitle());
                System.out.println("Link: " + entry.getLink());
                System.out.println("Author: " + entry.getAuthor());
                System.out.println("Publish Date: " + entry.getPublishedDate());
                System.out.println("Description: " + Util.clean(entry.getDescription().getValue()));
break;
            }
        }catch(Exception ex){}
            return ok();
    }

    public static Result checkParseRules(){
        try {

            URL u = new URL("http://rss.cnn.com/rss/cnn_topstories.rss");
            HttpURLConnection httpcon = (HttpURLConnection)u.openConnection();

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(httpcon));
            List entries = feed.getEntries();
            Iterator itEntries = entries.iterator();
            while (itEntries.hasNext()) {
                SyndEntry entry = (SyndEntry) itEntries.next();
                System.out.println("Title: " + entry.getTitle());
                System.out.println("Link: " + entry.getLink());
                System.out.println("Author: " + entry.getAuthor());
                System.out.println("Publish Date: " + entry.getPublishedDate());
                System.out.println("Description: " + Util.clean(entry.getDescription().getValue()));
                NewsArticle na = new NewsArticle(entry.getTitle(), Util.clean(entry.getDescription().getValue()), entry.getLink(), entry.getPublishedDate(),"6wuzfqug"); //,"38toxyju");

                na.hash = Crypto.encryptAES(na.title);
                na.getText();
                na.analyze();
                break;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
            return ok();
    }
}
