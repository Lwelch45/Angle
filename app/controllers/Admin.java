package controllers;

import models.DataSource;
import models.NewsSource;
import play.libs.Akka;
import play.mvc.Controller;
import play.mvc.Result;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by laurencewelch on 9/27/14.
 */
public class Admin extends Controller {

    public static Result initalizeDatabase(){
        NewsSource.init();
        DataSource.init();
        return ok("Added All sources to database");
    }

    public static Result startKimonoJob(){
        //NewsSource.updateEntireSet()
        Akka.system().scheduler().schedule(
                Duration.create(0, TimeUnit.MILLISECONDS), //Initial delay 0 milliseconds
                Duration.create(30, TimeUnit.MINUTES),     //Frequency 30 minutes
                new Runnable(){
                    public void run(){
                        NewsSource.updateEntireSet(true);
                    }
                },
                Akka.system().dispatcher()
        );
        return ok("scraping scheduled");
    }
}
