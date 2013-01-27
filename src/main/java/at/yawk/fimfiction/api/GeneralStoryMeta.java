package at.yawk.fimfiction.api;

/**
 * General story metadata that can be accessed from both JSON and visible
 * searches.
 * 
 * @author Yawkat
 * 
 */
public interface GeneralStoryMeta {
    /**
     * @return The story description
     */
    public String getDescription();
    
    /**
     * @return The story author
     */
    public AuthorMeta getAuthor();
    
    /**
     * @return The chapters
     */
    public VisibleChapter[] getChapters();
    
    /**
     * @return The title
     */
    public String getTitle();
    
    /**
     * @return The image location (src-attribute in display searches)
     */
    public String getImageLocation();
    
    /**
     * @return The "full" image location (href-attribute in display searches,
     *         called full location because of JSON)
     */
    public String getFullImageLocation();
    
    /**
     * @return The views of the most viewed chapter of this story
     */
    public int getMaximumChapterViews();
    
    /**
     * @return Total story views
     */
    public int getTotalViews();
    
    /**
     * @return All categories
     */
    public Category[] getCategories();
    
    /**
     * @return The amount of likes
     */
    public int getLikes();
    
    /**
     * @return The amount of dislikes
     */
    public int getDislikes();
    
    /**
     * @return The total amount of words
     */
    public int getWords();
    
    /**
     * @return The content rating
     */
    public ContentRating getContentRating();
    
    /**
     * @return The current story status
     */
    public StoryStatus getStoryStatus();
    
    /**
     * @return The amount of comments
     */
    public int getCommentAmount();
}
