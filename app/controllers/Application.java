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


}
