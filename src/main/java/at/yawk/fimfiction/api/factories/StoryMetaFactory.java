package at.yawk.fimfiction.api.factories;

import java.util.Date;

import at.yawk.fimfiction.api.Author;
import at.yawk.fimfiction.api.Category;
import at.yawk.fimfiction.api.Chapter;
import at.yawk.fimfiction.api.Character;
import at.yawk.fimfiction.api.ContentRating;
import at.yawk.fimfiction.api.MatureCategory;
import at.yawk.fimfiction.api.StoryMeta;
import at.yawk.fimfiction.api.StoryStatus;
import at.yawk.fimfiction.api.immutable.ImmutableHelper;

public class StoryMetaFactory implements StoryMeta {
    private String           description;
    private String           shortDescription;
    private String           title;
    private Chapter[]        chapters;
    private Date             modificationDate;
    private String           imageLocation;
    private String           fullImageLocation;
    private int              maximumChapterViews;
    private int              totalViews;
    private int              commentAmount;
    private Author           author;
    private StoryStatus      storyStatus;
    private ContentRating    contentRating;
    private int              likes;
    private int              dislikes;
    private int              words;
    private Character[]      characters;
    private Category[]       categories;
    private MatureCategory[] matureCategories;
    
    @Override
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String getShortDescription() {
        return shortDescription;
    }
    
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
    
    @Override
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public Chapter[] getChapters() {
        return chapters;
    }
    
    public void setChapters(Chapter[] chapters) {
        this.chapters = chapters;
    }
    
    @Override
    public Date getModificationDate() {
        return modificationDate;
    }
    
    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
    
    @Override
    public String getImageLocation() {
        return imageLocation;
    }
    
    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }
    
    @Override
    public String getFullImageLocation() {
        return fullImageLocation;
    }
    
    public void setFullImageLocation(String fullImageLocation) {
        this.fullImageLocation = fullImageLocation;
    }
    
    @Override
    public int getMaximumChapterViews() {
        return maximumChapterViews;
    }
    
    public void setMaximumChapterViews(int maximumChapterViews) {
        this.maximumChapterViews = maximumChapterViews;
    }
    
    @Override
    public int getTotalViews() {
        return totalViews;
    }
    
    public void setTotalViews(int totalViews) {
        this.totalViews = totalViews;
    }
    
    @Override
    public int getCommentAmount() {
        return commentAmount;
    }
    
    public void setCommentAmount(int commentAmount) {
        this.commentAmount = commentAmount;
    }
    
    @Override
    public Author getAuthor() {
        return author;
    }
    
    public void setAuthor(Author author) {
        this.author = author;
    }
    
    @Override
    public StoryStatus getStoryStatus() {
        return storyStatus;
    }
    
    public void setStoryStatus(StoryStatus storyStatus) {
        this.storyStatus = storyStatus;
    }
    
    @Override
    public ContentRating getContentRating() {
        return contentRating;
    }
    
    public void setContentRating(ContentRating contentRating) {
        this.contentRating = contentRating;
    }
    
    @Override
    public int getLikes() {
        return likes;
    }
    
    public void setLikes(int likes) {
        this.likes = likes;
    }
    
    @Override
    public int getDislikes() {
        return dislikes;
    }
    
    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
    
    @Override
    public int getWords() {
        return words;
    }
    
    public void setWords(int words) {
        this.words = words;
    }
    
    @Override
    public Character[] getCharacters() {
        return characters;
    }
    
    public void setCharacters(Character[] characters) {
        this.characters = characters;
    }
    
    public StoryMeta getCurrentData() {
        return ImmutableHelper.getCurrentData(this);
    }
    
    @Override
    public Category[] getCategories() {
        return categories;
    }
    
    public void setCategories(Category[] categories) {
        this.categories = categories;
    }
    
    @Override
    public MatureCategory[] getMatureCategories() {
        return matureCategories;
    }
    
    public void setMatureCategories(MatureCategory[] matureCategories) {
        this.matureCategories = matureCategories;
    }
}
