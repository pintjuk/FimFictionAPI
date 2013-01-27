package at.yawk.fimfiction.api;

/**
 * Any chapter metadata that can be gotten using the display search
 * 
 * @author Yawkat
 * 
 */
public interface VisibleChapterMeta {
    /**
     * @return The chapter title
     */
    public String getTitle();
    
    /**
     * @return The amount of words in this chapter
     */
    public int getWords();
}
