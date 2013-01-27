package at.yawk.fimfiction.api.immutable;

import java.util.Date;

import at.yawk.fimfiction.api.Author;
import at.yawk.fimfiction.api.Category;
import at.yawk.fimfiction.api.Chapter;
import at.yawk.fimfiction.api.Character;
import at.yawk.fimfiction.api.ContentRating;
import at.yawk.fimfiction.api.Identifier;
import at.yawk.fimfiction.api.MatureCategory;
import at.yawk.fimfiction.api.Story;
import at.yawk.fimfiction.api.StoryMeta;
import at.yawk.fimfiction.api.StoryStatus;

/**
 * An empty story with default not-null data
 * 
 * @author Yawkat
 * 
 */
public final class EmptyStory extends SimpleIdentifier implements Story, Identifier, StoryMeta, Comparable<Identifier>, Cloneable {
    private static final Chapter[]        EMPTY_CHAPTER_ARRAY   = new Chapter[0];
    private static final Date             EMPTY_DATE            = new Date(0);
    private static final Character[]      EMPTY_CHARACTER_ARRAY = new Character[0];
    private static final Category[]       EMPTY_CATEGORY_ARRAY  = new Category[0];
    private static final MatureCategory[] EMPTY_MATURE_CATEGORY = new MatureCategory[0];
    
    private static final EmptyStory       EMPTY_STORY           = new EmptyStory();
    
    private EmptyStory() {
        super(0);
    }
    
    public static EmptyStory instance() {
        return EMPTY_STORY;
    }
    
    @Override
    public String getDescription() {
        return "";
    }
    
    @Override
    public String getShortDescription() {
        return "";
    }
    
    @Override
    public String getTitle() {
        return "";
    }
    
    @Override
    public Chapter[] getChapters() {
        return EMPTY_CHAPTER_ARRAY;
    }
    
    @Override
    public Date getModificationDate() {
        return EMPTY_DATE;
    }
    
    @Override
    public String getImageLocation() {
        return "";
    }
    
    @Override
    public String getFullImageLocation() {
        return "";
    }
    
    @Override
    public int getMaximumChapterViews() {
        return 0;
    }
    
    @Override
    public int getTotalViews() {
        return 0;
    }
    
    @Override
    public int getCommentAmount() {
        return 0;
    }
    
    @Override
    public Author getAuthor() {
        return EmptyAuthor.instance();
    }
    
    @Override
    public StoryStatus getStoryStatus() {
        return StoryStatus.COMPLETE;
    }
    
    @Override
    public ContentRating getContentRating() {
        return ContentRating.ALL;
    }
    
    @Override
    public int getLikes() {
        return 0;
    }
    
    @Override
    public int getDislikes() {
        return 0;
    }
    
    @Override
    public Character[] getCharacters() {
        return EMPTY_CHARACTER_ARRAY;
    }
    
    @Override
    public int getWords() {
        return 0;
    }
    
    @Override
    public Category[] getCategories() {
        return EMPTY_CATEGORY_ARRAY;
    }
    
    @Override
    public EmptyStory clone() {
        return EMPTY_STORY;
    }
    
    @Override
    public MatureCategory[] getMatureCategories() {
        return EMPTY_MATURE_CATEGORY;
    }
}
