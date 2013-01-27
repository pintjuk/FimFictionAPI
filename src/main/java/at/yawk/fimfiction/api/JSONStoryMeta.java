package at.yawk.fimfiction.api;

import java.util.Date;

/**
 * Additional metadata visible via JSON request
 * 
 * @author Yawkat
 */
public interface JSONStoryMeta extends GeneralStoryMeta {
    /**
     * The short description is not used much, but is still available for many
     * stories.
     * 
     * @return The short description
     */
    public String getShortDescription();
    
    /**
     * @return The last modification date and time
     */
    public Date getModificationDate();
    
    /**
     * @return The author (including ID)
     */
    public Author getAuthor();
    
    /**
     * @return The chapters (including JSON data such as view count etc)
     */
    public Chapter[] getChapters();
}
