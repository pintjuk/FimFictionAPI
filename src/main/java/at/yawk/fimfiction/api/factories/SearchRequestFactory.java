package at.yawk.fimfiction.api.factories;

import java.util.HashMap;
import java.util.Map;

import at.yawk.fimfiction.api.Category;
import at.yawk.fimfiction.api.Character;
import at.yawk.fimfiction.api.ContentRating;
import at.yawk.fimfiction.api.MatureCategory;
import at.yawk.fimfiction.api.SearchOrder;
import at.yawk.fimfiction.api.SearchRequest;
import at.yawk.fimfiction.api.immutable.ImmutableHelper;

/**
 * A mutable search request.
 * 
 * @author Yawkat
 */
public class SearchRequestFactory implements SearchRequest {
    private String                  searchTerm     = "";
    private SearchOrder             searchOrder    = SearchOrder.FIRST_POSTED_DATE;
    private Map<Category, Boolean>  categories     = new HashMap<Category, Boolean>();
    private ContentRating           contentRating  = ContentRating.ALL;
    private MatureCategory          matureCategory = MatureCategory.ALL;
    private boolean                 completed      = false;
    private Integer                 minimumWords   = null;
    private Integer                 maximumWords   = null;
    private Map<Character, Boolean> characters     = new HashMap<Character, Boolean>();
    private boolean                 unread         = false;
    private boolean                 favorite       = false;
    private boolean                 readLater      = false;
    
    public String getSearchTerm() {
        return searchTerm;
    }
    
    /**
     * @param searchTerm
     *            The new search term
     * @return This object
     */
    public SearchRequestFactory setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
        return this;
    }
    
    public SearchOrder getSearchOrder() {
        return searchOrder;
    }
    
    /**
     * @param searchOrder
     *            The new search order
     * @return This object
     */
    public SearchRequestFactory setSearchOrder(SearchOrder searchOrder) {
        this.searchOrder = searchOrder;
        return this;
    }
    
    public Map<Category, Boolean> getCategories() {
        return categories;
    }
    
    /**
     * @param categories
     *            The new categories
     * @return This object
     */
    public SearchRequestFactory setCategories(Map<Category, Boolean> categories) {
        this.categories = categories;
        return this;
    }
    
    public ContentRating getContentRating() {
        return contentRating;
    }
    
    /**
     * @param contentRating
     *            The new content rating
     * @return This object
     */
    public SearchRequestFactory setContentRating(ContentRating contentRating) {
        this.contentRating = contentRating;
        return this;
    }
    
    public MatureCategory getMatureCategory() {
        return matureCategory;
    }
    
    /**
     * @param matureCategory
     *            The new mature category
     * @return This object
     */
    public SearchRequestFactory setMatureCategory(MatureCategory matureCategory) {
        this.matureCategory = matureCategory;
        return this;
    }
    
    public boolean getCompleted() {
        return completed;
    }
    
    /**
     * @param completed
     *            The new completed flag
     * @return This object
     */
    public SearchRequestFactory setCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }
    
    public Integer getMinimumWords() {
        return minimumWords;
    }
    
    /**
     * @param minimumWords
     *            The new minimum word count or <code>null</code> to ignore
     * @return This object
     */
    public SearchRequestFactory setMinimumWords(Integer minimumWords) {
        this.minimumWords = minimumWords;
        return this;
    }
    
    public Integer getMaximumWords() {
        return maximumWords;
    }
    
    /**
     * @param maximumWords
     *            The new maximum word count or <code>null</code> to ignore
     * @return This object
     */
    public SearchRequestFactory setMaximumWords(Integer maximumWords) {
        this.maximumWords = maximumWords;
        return this;
    }
    
    public Map<Character, Boolean> getCharacters() {
        return characters;
    }
    
    /**
     * @param characters
     *            The new characters
     * @return This object
     */
    public SearchRequestFactory setCharacters(Map<Character, Boolean> characters) {
        this.characters = characters;
        return this;
    }
    
    public boolean getUnread() {
        return unread;
    }
    
    /**
     * @param unread
     *            The new unread flag
     * @return This object
     */
    public SearchRequestFactory setUnread(boolean unread) {
        this.unread = unread;
        return this;
    }
    
    public boolean getFavorite() {
        return favorite;
    }
    
    /**
     * @param favorite
     *            The new favorite flag
     * @return This object
     */
    public SearchRequestFactory setFavorite(boolean favorite) {
        this.favorite = favorite;
        return this;
    }
    
    public boolean getReadLater() {
        return readLater;
    }
    
    /**
     * @param readLater
     *            The new read later flag
     * @return This object
     */
    public SearchRequestFactory setReadLater(boolean readLater) {
        this.readLater = readLater;
        return this;
    }
    
    /**
     * @return An immutable copy of the current data stored in this object
     */
    public SearchRequest getCurrentData() {
        return ImmutableHelper.getCurrentData(this);
    }
}
