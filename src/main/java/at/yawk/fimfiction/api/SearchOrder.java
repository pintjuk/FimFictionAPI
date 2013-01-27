package at.yawk.fimfiction.api;

public enum SearchOrder {
    FIRST_POSTED_DATE("latest"),
    HOT("heat"),
    UPDATE_DATE("updated"),
    RATING("top"),
    VIEWS("views"),
    WORD_COUNT("words"),
    COMMENTS("comments");
    
    private final String searchRequestIdentifier;
    
    SearchOrder(String searchRequestIdentifier) {
        this.searchRequestIdentifier = searchRequestIdentifier;
    }
    
    public String getSearchRequestIdentifier() {
        return searchRequestIdentifier;
    }
}
