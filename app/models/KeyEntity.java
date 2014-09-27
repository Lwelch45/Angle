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

    String name;
    Integer Sentiment; // -1 negative, 0 neutral, 1 positive
    Integer evidence;
    Double confidence;

    DataSource source;
}
