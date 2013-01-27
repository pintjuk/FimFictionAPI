package at.yawk.fimfiction.api.immutable;

import java.util.Date;

import at.yawk.fimfiction.api.Author;
import at.yawk.fimfiction.api.Category;
import at.yawk.fimfiction.api.Chapter;
import at.yawk.fimfiction.api.ContentRating;
import at.yawk.fimfiction.api.JSONStoryMeta;
import at.yawk.fimfiction.api.StoryStatus;

/**
 * A simple, immutable JSON story metadata
 * 
 * @author Yawkat
 * 
 */
public class SimpleJSONStoryMeta implements JSONStoryMeta {
    private final String        description;
    private final String        shortDescription;
    private final String        title;
    private final Chapter[]     chapters;
    private final Date          modificationDate;
    private final String        imageLocation;
    private final String        fullImageLocation;
    private final int           maximumChapterViews;
    private final int           totalViews;
    private final int           commentAmount;
    private final Author        author;
    private final StoryStatus   storyStatus;
    private final ContentRating contentRating;
    private final int           likes;
    private final int           dislikes;
    private final int           words;
    private final Category[]    categories;
    
    public SimpleJSONStoryMeta(String description, String shortDescription, String title, Chapter[] chapters, Date modificationDate, String imageLocation, String fullImageLocation, int maximumChapterViews, int totalViews, int commentAmount, Author author, StoryStatus storyStatus, ContentRating contentRating, int likes, int dislikes, int words, Category[] categories) {
        this.description = description;
        this.shortDescription = shortDescription;
        this.title = title;
        this.chapters = chapters;
        this.modificationDate = modificationDate;
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
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public String getShortDescription() {
        return shortDescription;
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
    public Date getModificationDate() {
        return modificationDate;
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
}
