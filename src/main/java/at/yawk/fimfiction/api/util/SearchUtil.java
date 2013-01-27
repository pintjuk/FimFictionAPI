package at.yawk.fimfiction.api.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import at.yawk.fimfiction.api.Category;
import at.yawk.fimfiction.api.Character;
import at.yawk.fimfiction.api.SearchRequest;

public final class SearchUtil {
    private SearchUtil() {
        
    }
    
    /**
     * Convert a request to a GET string
     * 
     * @param request
     *            The request
     * @return The GET string
     */
    public static String getSearchGet(SearchRequest request) {
        try {
            final StringBuilder sb = new StringBuilder();
            
            sb.append("view=category");
            
            sb.append("&search=");
            sb.append(URLEncoder.encode(request.getSearchTerm(), "UTF-8"));
            
            sb.append("&order=");
            sb.append(request.getSearchOrder().getSearchRequestIdentifier());
            
            for(final Category ec : Category.values()) {
                final Boolean b = request.getCategories().get(ec);
                sb.append('&');
                sb.append(ec.getSearchRequestIdentifier());
                sb.append('=');
                sb.append(b == null ? "" : b.booleanValue() ? "1" : "2");
            }
            
            sb.append("&content_rating=");
            sb.append(request.getContentRating().getSearchRequestIdentifier());
            
            sb.append("&mature_categories=");
            sb.append(request.getMatureCategory().getSearchRequestIdentifier());
            
            if(request.getCompleted())
                sb.append("&completed=1");
            
            if(request.getFavorite())
                sb.append("&tracking");
            
            if(request.getUnread())
                sb.append("&unread");
            
            if(request.getReadLater())
                sb.append("&read_it_later");
            
            sb.append("&minimum_words=");
            sb.append(request.getMinimumWords() == null ? "" : request.getMinimumWords());
            
            sb.append("&maximum_words=");
            sb.append(request.getMaximumWords() == null ? "" : request.getMaximumWords());
            
            for(final Character ec : request.getCharacters().keySet()) {
                final Boolean b = request.getCharacters().get(ec);
                if(b != null) {
                    sb.append(b.booleanValue() ? "&characters[]=" : "&characters_execluded[]=");
                    sb.append(ec.getId());
                }
            }
            
            return sb.toString();
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
