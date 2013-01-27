package at.yawk.fimfiction.api;

/**
 * Used for "merging" an ID and metadata into one object without having to
 * implement all other methods
 * 
 * @author Yawkat
 * 
 * @param <M>
 *            The type of story meta to be accessed
 */
public interface StoryAccess<M extends GeneralStoryMeta> extends Identifier {
    public Identifier getIdentifier();
    
    public M getMeta();
}
