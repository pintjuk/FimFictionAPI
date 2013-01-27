package at.yawk.fimfiction.api;

import java.util.HashMap;
import java.util.Map;

/**
 * The current status of a story
 * 
 * @author Yawkat
 * 
 */
public enum StoryStatus {
    COMPLETE("Complete"),
    INCOMPLETE("Incomplete"),
    ON_HIATUS("On Hiatus"),
    CANCELLED("Cancelled");
    
    private final String name;
    
    private StoryStatus(String name) {
        this.name = name;
    }
    
    /**
     * @return The display name of this status (correct casing, spaces)
     */
    public String getName() {
        return name;
    }
    
    private static Map<String, StoryStatus> names = new HashMap<String, StoryStatus>(values().length);
    
    static {
        for(StoryStatus ss : values())
            names.put(ss.getName(), ss);
    }
    
    /**
     * Get the StoryStatus for a given display String (used by JSON meta parser)
     * 
     * @param s
     *            The name
     * @return The StoryStatus or <code>null</code> if no status matches the
     *         string
     */
    public static StoryStatus parse(String s) {
        return names.get(s);
    }
}
