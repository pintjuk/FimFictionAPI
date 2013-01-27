package at.yawk.fimfiction.api.immutable;

import at.yawk.fimfiction.api.Author;
import at.yawk.fimfiction.api.Category;
import at.yawk.fimfiction.api.Chapter;
import at.yawk.fimfiction.api.Character;
import at.yawk.fimfiction.api.ContentRating;
import at.yawk.fimfiction.api.MatureCategory;
import at.yawk.fimfiction.api.StoryStatus;
import at.yawk.fimfiction.api.VisibleStoryMeta;

/**
 * A simple, immutable visible story meta
 * 
 * @author Yawkat
 * 
 */
public class SimpleVisibleStoryMeta implements VisibleStoryMeta {
    private final String           title;
    private final Chapter[]        chapters;
    private final String           imageLocation;
    private final String           fullImageLocation;
    private final int              maximumChapterViews;
    private final int              totalViews;
    private final int              commentAmount;
    private final Author           author;
    private final StoryStatus      storyStatus;
    private final ContentRating    contentRating;
    private final int              likes;
    private final int              dislikes;
    private final int              words;
    private final Category[]       categories;
    private final Character[]      characters;
    private final MatureCategory[] matureCategories;
    private final String           description;
    
    public SimpleVisibleStoryMeta(String description, String title, Chapter[] chapters, String imageLocation, String fullImageLocation, int maximumChapterViews, int totalViews, int commentAmount, Author author, StoryStatus storyStatus, ContentRating contentRating, int likes, int dislikes, int words, Category[] categories, Character[] characters, MatureCategory[] matureCategories) {
        this.description = description;
        this.title = title;
        this.chapters = chapters;
        this.imageLocation = imageLocation;
        this.fullImageLocation = fullImageLocation;
        this.maximumChapterViews = maximumChapterViews;
        this.totalViews = totalViews;
        this.commentAmount = commentAmount;
        this.author = author;
        this.storyStatus = storyStatus;
        this.contentRating = contentRating;
        this.likes = likes;
        this.dislikes = dislikes;
        this.words = words;
        this.categories = categories;
        this.characters = characters;
        this.matureCategories = matureCategories;
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public String getTitle() {
        return title;
    }
    
    @Override
    public Chapter[] getChapters() {
        return chapters;
    }
    
    @Override
    public String getImageLocation() {
        return imageLocation;
    }
    
    @Override
    public String getFullImageLocation() {
        return fullImageLocation;
    }
    
    @Override
    public int getMaximumChapterViews() {
        return maximumChapterViews;
    }
    
    @Override
    public int getTotalViews() {
        return totalViews;
    }
    
    @Override
    public int getCommentAmount() {
        return commentAmount;
    }
    
    @Override
    public Author getAuthor() {
        return author;
    }
    
    @Override
    public StoryStatus getStoryStatus() {
        return storyStatus;
    }
    
    @Override
    public ContentRating getContentRating() {
        return contentRating;
    }
    
    @Override
    public int getLikes() {
        return likes;
    }
    
    @Override
    public int getDislikes() {
        return dislikes;
    }
    
    @Override
    public int getWords() {
        return words;
    }
    
    @Override
    public Category[] getCategories() {
        return categories;
    }
    
    @Override
    public Character[] getCharacters() {
        return characters;
    }
    
    @Override
    public MatureCategory[] getMatureCategories() {
        return matureCategories;
    }
}
