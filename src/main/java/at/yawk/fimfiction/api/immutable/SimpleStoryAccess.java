package at.yawk.fimfiction.api.immutable;

import at.yawk.fimfiction.api.GeneralStoryMeta;
import at.yawk.fimfiction.api.Identifier;
import at.yawk.fimfiction.api.StoryAccess;

/**
 * A simple, immutable story access
 * 
 * @author Yawkat
 * 
 * @param <M>
 *            The type of story metadata
 */
public class SimpleStoryAccess<M extends GeneralStoryMeta> implements StoryAccess<M> {
    private final Identifier id;
    private final M          meta;
    
    public SimpleStoryAccess(Identifier id, M meta) {
        this.id = id;
        this.meta = meta;
    }
    
    @Override
    public Identifier getIdentifier() {
        return id;
    }
    
    @Override
    public M getMeta() {
        return meta;
    }
    
    @Override
    public int compareTo(Identifier o) {
        return id.compareTo(o);
    }
    
    @Override
    public int getId() {
        return id.getId();
    }
}
