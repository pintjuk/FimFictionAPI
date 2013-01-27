package at.yawk.fimfiction.api;

/**
 * A unique Identifier, used by Chapters, Stories and Authors.
 * 
 * @author Yawkat
 */
public interface Identifier extends Comparable<Identifier> {
    public int getId();
}
