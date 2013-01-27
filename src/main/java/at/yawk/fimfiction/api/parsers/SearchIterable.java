package at.yawk.fimfiction.api.parsers;

import java.util.Map;

import at.yawk.fimfiction.api.Category;
import at.yawk.fimfiction.api.Character;
import at.yawk.fimfiction.api.ContentRating;
import at.yawk.fimfiction.api.Identifier;
import at.yawk.fimfiction.api.InternetAccess;
import at.yawk.fimfiction.api.MatureCategory;
import at.yawk.fimfiction.api.SearchOrder;
import at.yawk.fimfiction.api.SearchRequest;
import at.yawk.fimfiction.api.immutable.ImmutableHelper;

/**
 * An iterable over a search request
 * 
 * @author Yawkat
 * 
 * @param <S>
 *            The type of data that is read
 */
public abstract class SearchIterable<S extends Identifier> implements SearchRequest, Iterable<S> {
    private final SearchRequest  request;
    private final InternetAccess internet;
    
    public SearchIterable(SearchRequest request, InternetAccess internet) {
        this.request = ImmutableHelper.getCurrentData(request);
        this.internet = internet;
    }
    
    @Override
    public String getSearchTerm() {
        return request.getSearchTerm();
    }
    
    @Override
    public SearchOrder getSearchOrder() {
        return request.getSearchOrder();
    }
    
    @Override
    public Map<Category, Boolean> getCategories() {
        return request.getCategories();
    }
    
    @Override
    public ContentRating getContentRating() {
        return request.getContentRating();
    }
    
    @Override
    public MatureCategory getMatureCategory() {
        return request.getMatureCategory();
    }
    
    @Override
    public boolean getCompleted() {
        return request.getCompleted();
    }
    
    @Override
    public Integer getMinimumWords() {
        return request.getMinimumWords();
    }
    
    @Override
    public Integer getMaximumWords() {
        return request.getMaximumWords();
    }
    
    @Override
    public Map<Character, Boolean> getCharacters() {
        return request.getCharacters();
    }
    
    @Override
    public boolean getUnread() {
        return request.getUnread();
    }
    
    @Override
    public boolean getFavorite() {
        return request.getFavorite();
    }
    
    @Override
    public boolean getReadLater() {
        return request.getReadLater();
    }
    
    protected SearchRequest getSearchRequest() {
        return request;
    }
    
    protected InternetAccess getInternet() {
        return internet;
    }
}
