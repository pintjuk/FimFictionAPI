package at.yawk.fimfiction.api;

/**
 * Any metadata that can be gotten using the normal search function
 * 
 * @author Yawkat
 * 
 */
public interface VisibleStoryMeta extends GeneralStoryMeta {
    /**
     * @return The main characters that appear in the story
     */
    public Character[] getCharacters();
    
    /**
     * Can be either <code>[]</code>, <code>[SEX]</code>, <code>[GORE]</code>,
     * <code>[SEX, GORE]</code> or <code>[GORE, SEX]</code>
     * 
     * @return The mature categories that have been assigned to this story
     */
    public MatureCategory[] getMatureCategories();
}
