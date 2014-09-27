package controllers;

import models.NewsSource;
import play.*;
import play.mvc.*;

import scala.concurrent.duration.Duration;
import views.html.*;
import play.libs.Akka;

import java.util.concurrent.TimeUnit;

public class Application extends Controller {

    public static Result index() {
        return ok();
    }

    public static Result initalizeDatabase(){
        NewsSource.init();
        return ok("Added All sources to database");
    }

    public static Result startscrapeJob(){
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
