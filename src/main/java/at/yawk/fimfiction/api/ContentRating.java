package at.yawk.fimfiction.api;

/**
 * Content rating. Mature content rating is not shown in searches if the mature
 * flag is disabled.
 * 
 * @author Yawkat
 * 
 */
public enum ContentRating {
    ALL(-1),
    EVERYONE(0),
    TEEN(1),
    MATURE(2);
    
    private static final int MINIMUM_ID = -1;
    
    private final byte       searchRequestIdentifier;
    
    private ContentRating(int searchRequestIdentifier) {
        this.searchRequestIdentifier = (byte)searchRequestIdentifier;
    }
    
    public byte getSearchRequestIdentifier() {
        return searchRequestIdentifier;
    }
    
    private static ContentRating[] ids = new ContentRating[values().length];
    
    static {
        for(ContentRating cr : values())
            ids[cr.getSearchRequestIdentifier() - MINIMUM_ID] = cr;
    }
    
    /**
     * Get a content rating for a given ID
     * 
     * @param id
     * @return the content rating
     * @throws ArrayIndexOutOfBoundsException
     *             if the id is invalid
     */
    public static ContentRating parse(int id) {
        return ids[id - MINIMUM_ID];
    }
}
