package models;


import play.db.ebean.Model;
import javax.persistence.*;
/**
 * Created by laurencewelch on 9/27/14.
 */
@Entity
public class DataSource extends Model{


    @Id
    Long id;

    String name;
    double score;


    public static Finder<Long, DataSource> find =new Finder<Long, DataSource>(Long.class, DataSource.class);

    public static DataSource findByName(String n){
        return find.all().get(1);
    }
    public static void init(){
        DataSource d = new DataSource();
        d.name = "Rhine";
        d.score = 1.0;
        d.save();

        d = new DataSource();
        d.name = "Semantria";
        d.score = 1.0;
        d.save();
    }
}
