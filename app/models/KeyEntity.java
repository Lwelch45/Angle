package models;

import play.db.ebean.Model;
import javax.persistence.*;
/**
 * Created by laurencewelch on 9/27/14.
 */
@Entity
public class KeyEntity extends Model {

    @Id
    Long id;

    public String name;
    public String sentiment; // -1 negative, 0 neutral, 1 positive
    public float sentimentScore;

    public Integer evidence;
    public Boolean confidence;

    public DataSource source;

    public KeyEntity(String n, String s, float sS, Integer e, Boolean c, DataSource ds){
        this.name = n;
        this.sentiment = s;
        this.sentimentScore = sS;
        this.evidence = e;
        this.confidence = c;
        this.source = ds;
    }
}
