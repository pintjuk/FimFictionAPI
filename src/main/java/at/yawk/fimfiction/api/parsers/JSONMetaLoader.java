package at.yawk.fimfiction.api.parsers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import at.yawk.fimfiction.api.Category;
import at.yawk.fimfiction.api.Chapter;
import at.yawk.fimfiction.api.ContentRating;
import at.yawk.fimfiction.api.Identifier;
import at.yawk.fimfiction.api.InternetAccess;
import at.yawk.fimfiction.api.InvalidStoryException;
import at.yawk.fimfiction.api.JSONStoryMeta;
import at.yawk.fimfiction.api.StoryStatus;
import at.yawk.fimfiction.api.URLs;
import at.yawk.fimfiction.api.factories.StoryMetaFactory;
import at.yawk.fimfiction.api.immutable.SimpleAuthor;
import at.yawk.fimfiction.api.immutable.SimpleChapter;

public class JSONMetaLoader {
    public static JSONStoryMeta getStoryMeta(Identifier i, InternetAccess internet) throws IOException, ParseException, InvalidStoryException {
        final JSONObject story = (JSONObject)((JSONObject)new JSONParser().parse(new InputStreamReader(internet.connect(new URL(URLs.API_BASE + i.getId())).getInputStream()))).get("story");
        if(story == null)
            throw new InvalidStoryException("Story " + i.getId() + " does not exist");
        final StoryMetaFactory factory = new StoryMetaFactory();
        factory.setTitle(getWithDefault(story, "title", ""));
        factory.setShortDescription(getWithDefault(story, "short_description", ""));
        factory.setDescription(getWithDefault(story, "description", ""));
        factory.setModificationDate(new Date(getWithDefault(story, "date_modified", 0L)));
        factory.setImageLocation(getWithDefault(story, "image", ""));
        factory.setMaximumChapterViews(getWithDefault(story, "views", 0L).intValue());
        factory.setTotalViews(getWithDefault(story, "total_views", 0L).intValue());
        factory.setWords(getWithDefault(story, "words", 0L).intValue());
        factory.setCommentAmount(getWithDefault(story, "comments", 0L).intValue());
        final StoryStatus ss = StoryStatus.parse(getWithDefault(story, "status", ""));
        factory.setStoryStatus(ss == null ? StoryStatus.COMPLETE : ss);
        factory.setContentRating(ContentRating.parse(getWithDefault(story, "content_rating", -1L).intValue()));
        final List<Category> categories = new ArrayList<Category>();
        try {
            final JSONObject jc = (JSONObject)story.get("categories");
            for(Category c : Category.values()) {
                if((Boolean)jc.get(c.getName()))
                    categories.add(c);
            }
        } catch(Exception e) {}
        factory.setCategories(categories.toArray(new Category[categories.size()]));
        factory.setLikes(getWithDefault(story, "likes", 0L).intValue());
        factory.setDislikes(getWithDefault(story, "dislikes", 0L).intValue());
        final List<Chapter> chapters = new ArrayList<Chapter>(getWithDefault(story, "chapter_count", 16L).intValue());
        try {
            final JSONArray jc = (JSONArray)story.get("chapters");
            for(Object o : jc) {
                final JSONObject jo = (JSONObject)o;
                chapters.add(new SimpleChapter(getWithDefault(jo, "id", 0L).intValue(), getWithDefault(jo, "title", ""), getWithDefault(jo, "words", 0L).intValue(), getWithDefault(jo, "views", 0L).intValue(), new Date(getWithDefault(story, "date_modified", 0L))));
            }
        } catch(Exception e) {}
        try {
            final JSONObject ja = (JSONObject)story.get("author");
            factory.setAuthor(new SimpleAuthor(Integer.parseInt(getWithDefault(ja, "id", "0")), getWithDefault(ja, "name", "")));
        } catch(Exception e) {}
        factory.setChapters(chapters.toArray(new Chapter[chapters.size()]));
        return factory;
    }
    
    @SuppressWarnings("unchecked")
    private static <O> O getWithDefault(JSONObject source, Object key, O defaultV) {
        try {
            return (O)source.get(key);
        } catch(Exception e) {
            return defaultV;
        }
    }
}
