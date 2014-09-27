package models;

import play.db.ebean.Model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laurencewelch on 9/27/14.
 */
@Entity
public class EntityAnalysis extends Model {
    @Id
    Long id;

    public String category;

    public NewsArticle article;

    @ManyToOne
    public List<Theme> themes;
    public double sentimentScore; // -1 negative, 0 neutral, 1 positive
    public String sentiment;
    public String summary;

    @ManyToOne
    List<KeyEntity> entities;

    public EntityAnalysis(){
        themes = new ArrayList<Theme>();
        entities = new ArrayList<KeyEntity>();
    }

    public EntityAnalysis addEntity(KeyEntity key){
        entities.add(key);
        return this;
    }

}
