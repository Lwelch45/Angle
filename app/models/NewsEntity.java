package models;

import javax.persistence.*;
import play.db.ebean.Model;

/**
 * Created by laurencewelch on 9/26/14.
 */
public class NewsEntity extends Model{
    @Id
    Long id;

    public String title; //
    public String desription; //blurb about article
    public String link; //link to article
    public String hash; // MD5 hash of the title used for comparison.
    public String body; // body of article

    @Override
    public String toString(){
        return "Article {title=" + title +  ", link=" + link + "}";
    }


}
