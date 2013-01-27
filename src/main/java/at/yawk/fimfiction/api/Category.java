package at.yawk.fimfiction.api;

/**
 * A story category
 * 
 * @author Yawkat
 * 
 */
public enum Category {
    ROMANCE("Romance"),
    TRAGEDY("Tragedy"),
    SAD("Sad"),
    DARK("Dark"),
    COMEDY("Comedy"),
    RANDOM("Random"),
    CROSSOVER("Crossover"),
    ADVENTURE("Adventure"),
    SLICE_OF_LIFE("Slice of Life"),
    ALTERNATE_UNIVERSE("Alternate Universe"),
    HUMAN("Human");
    
    private final String searchRequestIdentifier, name;
    
    private Category(String name) {
        this.searchRequestIdentifier = "category_" + toString().toLowerCase();
        this.name = name;
    }
    
    /**
     * @return The request identifier (used in search request GET)
     */
    public String getSearchRequestIdentifier() {
        return searchRequestIdentifier;
    }
    
    /**
     * @return The display name (correct casing, spaces etc)
     */
    public String getName() {
        return name;
    }
}
