package at.yawk.fimfiction.api.immutable;

import at.yawk.fimfiction.api.Identifier;

/**
 * A simple, immutable ID
 * 
 * @author Yawkat
 * 
 */
public class SimpleIdentifier implements Identifier {
    private final int id;
    
    public SimpleIdentifier(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    @Override
    public boolean equals(Object o) {
        return o != null && o.getClass() == this.getClass() && ((Identifier)o).getId() == this.getId();
    }
    
    @Override
    public int hashCode() {
        return getId();
    }
    
    @Override
    public int compareTo(Identifier o) {
        return Integer.compare(this.getId(), o.getId());
    }
}
