package at.yawk.fimfiction.api.immutable;

import at.yawk.fimfiction.api.Author;
import at.yawk.fimfiction.api.Identifier;

/**
 * An empty author with default not-null data
 * 
 * @author Yawkat
 */
public final class EmptyAuthor extends SimpleIdentifier implements Author, Identifier, Comparable<Identifier>, Cloneable {
    private static EmptyAuthor EMPTY_AUTHOR = new EmptyAuthor();
    
    public static EmptyAuthor instance() {
        return EMPTY_AUTHOR;
    }
    
    private EmptyAuthor() {
        super(0);
    }
    
    @Override
    public String getName() {
        return "";
    }
    
    @Override
    public EmptyAuthor clone() {
        return EMPTY_AUTHOR;
    }
}
