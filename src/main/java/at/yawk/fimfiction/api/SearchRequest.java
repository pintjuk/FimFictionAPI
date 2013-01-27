package at.yawk.fimfiction.api;

import java.util.Map;

import at.yawk.fimfiction.api.util.SearchUtil;

/**
 * A GET search request
 * 
 * @see SearchUtil#getSearchGet(SearchRequest)
 * 
 * @author Yawkat
 * 
 */
public interface SearchRequest {
    /**
     * @return The search term
     */
    public String getSearchTerm();
    
    /**
     * @return The search order
     */
    public SearchOrder getSearchOrder();
    
    /**
     * Map with given categories and whether they should be used.
     * <code>null</code> or not in map means that it should be ignored,
     * <code>false</code> means that it may not appear, <code>true</code> means
     * it must appear.
     * 
     * @return The search categories
     */
    public Map<Category, Boolean> getCategories();
    
    /**
     * @return The content rating
     */
    public ContentRating getContentRating();
    
    /**
     * @return The mature category
     */
    public MatureCategory getMatureCategory();
    
    /**
     * @return Whether the story must be completed to be displayed.
     */
    public boolean getCompleted();
    
    /**
     * @return Minimum word amount or <code>null</code> if it should be ignored.
     */
    public Integer getMinimumWords();
    
    /**
     * @return Maximum word amount or <code>null</code> if it should be ignored.
     */
    public Integer getMaximumWords();
    
    /**
     * Map with given characters and whether they must appear in the story.
     * <code>null</code> or not in map means that it should be ignored,
     * <code>false</code> means that it may not appear, <code>true</code> means
     * 
     * @return The search characters
     */
    public Map<Character, Boolean> getCharacters();
    
    /**
     * @return Whether the story must have unread chapters (only useful when
     *         using a logged in connection)
     */
    public boolean getUnread();
    
    /**
     * @return Whether the story must be marked as favorite (only useful when
     *         using a logged in connection)
     */
    public boolean getFavorite();
    
    /**
     * @return Whether the story must be marked as read later (only useful when
     *         using a logged in connection)
     */
    public boolean getReadLater();
}
