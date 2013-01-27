package at.yawk.fimfiction.api;

/**
 * Mature category
 * 
 * @author Yawkat
 * 
 */
public enum MatureCategory {
    ALL(0, 1),
    SEX(1, 2),
    GORE(2, 4);
    
    private final byte searchRequestIdentifier;
    private final byte bit;
    
    private MatureCategory(int searchRequestIdentifier, int bit) {
        this.searchRequestIdentifier = (byte)searchRequestIdentifier;
        this.bit = (byte)bit;
    }
    
    /**
     * @return The ID (used by GET search)
     */
    public byte getSearchRequestIdentifier() {
        return searchRequestIdentifier;
    }
    
    /**
     * @return A unique bit used to identify this category
     */
    public byte getBit() {
        return bit;
    }
}
