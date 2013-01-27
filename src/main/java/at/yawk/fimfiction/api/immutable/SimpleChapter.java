package at.yawk.fimfiction.api.immutable;

import java.util.Date;

import at.yawk.fimfiction.api.Chapter;

/**
 * A simple, immutable chapter
 * 
 * @author Yawkat
 * 
 */
public class SimpleChapter extends SimpleIdentifier implements Chapter {
    private final String title;
    private final int    words;
    private final int    views;
    private final Date   modificationDate;
    
    public SimpleChapter(int id, String title, int words, int views, Date modificationDate) {
        super(id);
        this.title = title;
        this.words = words;
        this.views = views;
        this.modificationDate = modificationDate;
    }
    
    @Override
    public String getTitle() {
        return title;
    }
    
    @Override
    public int getWords() {
        return words;
    }
    
    @Override
    public int getViews() {
        return views;
    }
    
    @Override
    public Date getModificationDate() {
        return modificationDate;
    }
    
}
