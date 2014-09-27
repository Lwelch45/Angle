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

    String category;
    String theme;
    Integer Sentiment; // -1 negative, 0 neutral, 1 positive

    List<KeyEntity> entities;
    public EntityAnalysis(){
        entities = new ArrayList<KeyEntity>();
    }

    public EntityAnalysis addEntity(KeyEntity key){
        entities.add(key);
        return this;
    }

}
