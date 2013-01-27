package at.yawk.fimfiction.api.immutable;

import java.util.Collections;
import java.util.Map;

import at.yawk.fimfiction.api.Category;
import at.yawk.fimfiction.api.Character;
import at.yawk.fimfiction.api.ContentRating;
import at.yawk.fimfiction.api.MatureCategory;
import at.yawk.fimfiction.api.SearchOrder;
import at.yawk.fimfiction.api.SearchRequest;

/**
 * A simple, immutable search request
 * 
 * @author Yawkat
 * 
 */
public class SimpleSearchRequest implements SearchRequest {
    private final String                  searchTerm;
    private final SearchOrder             searchOrder;
    private final Map<Category, Boolean>  categories;
    private final ContentRating           contentRating;
    private final MatureCategory          matureCategory;
    private final boolean                 completed;
    private final Integer                 minimumWords;
    private final Integer                 maximumWords;
    private final Map<Character, Boolean> characters;
    private final boolean                 unread;
    private final boolean                 favorite;
    private final boolean                 readLater;
    
    public SimpleSearchRequest(String searchTerm, SearchOrder searchOrder, Map<Category, Boolean> categories, ContentRating contentRating, MatureCategory matureCategory, boolean completed, Integer minimumWords, Integer maximumWords, Map<Character, Boolean> characters, boolean unread, boolean favorite, boolean readLater) {
        this.searchTerm = searchTerm;
        this.searchOrder = searchOrder;
        this.categories = Collections.unmodifiableMap(categories);
        this.contentRating = contentRating;
        this.matureCategory = matureCategory;
        this.completed = completed;
        this.minimumWords = minimumWords;
        this.maximumWords = maximumWords;
        this.characters = Collections.unmodifiableMap(characters);
        this.unread = unread;
        this.favorite = favorite;
        this.readLater = readLater;
    }
    
    public String getSearchTerm() {
        return searchTerm;
    }
    
    public SearchOrder getSearchOrder() {
        return searchOrder;
    }
    
    public Map<Category, Boolean> getCategories() {
        return categories;
    }
    
    public ContentRating getContentRating() {
        return contentRating;
    }
    
    public MatureCategory getMatureCategory() {
        return matureCategory;
    }
    
    public boolean getCompleted() {
        return completed;
    }
    
    public Integer getMinimumWords() {
        return minimumWords;
    }
    
    public Integer getMaximumWords() {
        return maximumWords;
    }
    
    public Map<Character, Boolean> getCharacters() {
        return characters;
    }
    
    public boolean getUnread() {
        return unread;
    }
    
    public boolean getFavorite() {
        return favorite;
    }
    
    public boolean getReadLater() {
        return readLater;
    }
}
