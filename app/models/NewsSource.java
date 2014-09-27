package models;


import javax.persistence.*;
import play.db.ebean.Model;

import java.util.Date;

/**
 * Created by laurencewelch on 9/26/14.
 */
public class NewsSource extends Model {

    @Id
    Long id;

    String name;
    String description;
    String feed;
    Date lastUpdated;


}
