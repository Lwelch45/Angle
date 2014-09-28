package externals;

import com.semantria.Session;
import com.semantria.interfaces.ICallbackHandler;
import com.semantria.mapping.Document;
import com.semantria.mapping.output.*;
import com.semantria.serializer.JsonSerializer;
import com.semantria.utils.RequestArgs;
import com.semantria.utils.ResponseArgs;
import models.*;
import play.Play;
import util.Util;
import java.util.List;
import java.util.UUID;

/**
 * Created by laurencewelch on 9/27/14.
 */
public class Semantria extends Scraper {
    public static final String key = Play.application().configuration().getString("semantriaKEY");
    public static final String secret = Play.application().configuration().getString("semantriaSECRET");
    public static DataSource ds = DataSource.findByName("Semantria");
    public Semantria(){

    }

    public void scrape(){}

    @Override
    public void init(String n, String m){
        //no purpose
    }

    public EntityAnalysis analyze(NewsArticle na){
        EntityAnalysis ea = new EntityAnalysis();
        Session session = Session.createSession(key, secret, new JsonSerializer());
        session.setCallbackHandler(new ICallbackHandler() {
            @Override
            public void onResponse(Object sender, ResponseArgs responseArgs) {
            }

            @Override
            public void onRequest(Object sender, RequestArgs requestArgs) {
            }

            @Override
            public void onError(Object sender, ResponseArgs errorArgs) {
                System.out.println(String.format("%d: %s", (int) errorArgs.getStatus(), errorArgs.getMessage()));
            }

            @Override
            public void onDocsAutoResponse(Object sender, List<DocAnalyticData> processedData) {
            }

            @Override
            public void onCollsAutoResponse(Object sender, List<CollAnalyticData> processedData) {
            }
        });
        Document d = new Document(UUID.randomUUID().toString(),Util.ListToString(na.body));
        session.queueDocument(d);
        DocAnalyticData result;
        do {
            try
            {
                Thread.currentThread().sleep(1000);
            }
            catch (InterruptedException e) {
                System.err.println("Error: " + e.getMessage());
            }
            //Retreives analysis results for queued collection
             result = session.getDocument(d.getId());
        } while (result.getStatus() == TaskStatus.QUEUED);
        ea.summary = result.getSummary();
        ea.sentimentScore = result.getSentimentScore();

        for(DocTheme theme : result.getThemes()){
            ea.themes.add(new Theme(theme.getTitle(), theme.getSentimentScore(), theme.getEvidence(), theme.getIsAbout(), theme.getSentimentPolarity()));
        }
        if (result.getAutoCategories() != null)
            ea.category = result.getAutoCategories().get(0).getTitle();
        ea.sentiment = result.getSentimentPolarity();
        for(DocEntity de : result.getEntities()){
            ea.addEntity(new KeyEntity(de.getTitle(), de.getSentimentPolarity(), de.getSentimentScore(), de.getEvidence(), de.getConfident(), ds));
        }
        ea.save();
        return ea;
    }
}
