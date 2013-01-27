package at.yawk.fimfiction.api;

import java.util.Date;

/**
 * All metadata that can currently be accessed about a chapter
 * 
 * @author Yawkat
 * 
 */
public interface ChapterMeta extends VisibleChapterMeta {
    public Date getModificationDate();
    
    public int getViews();
}
