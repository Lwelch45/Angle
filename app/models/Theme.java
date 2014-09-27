package models;

import play.db.ebean.Model;
import javax.persistence.*;
/**
 * Created by laurencewelch on 9/27/14.
 */
@Entity
public class Theme extends Model {
    @Id
    public Long id;

    public String name;
    public double score;
    public int evidence;
    public Boolean isAbout;
    public String polarity;

    public Theme(String n, double s, Integer e, Boolean iA, String p){
        name = n;
        score = s;
        evidence =e;
        isAbout = iA;
        polarity = p;
    }
}
