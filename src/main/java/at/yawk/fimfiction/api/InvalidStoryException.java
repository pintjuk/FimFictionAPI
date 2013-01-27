package at.yawk.fimfiction.api;

/**
 * Thrown whenever an invalid story ID is being used
 * 
 * @author Yawkat
 */
public class InvalidStoryException extends NullPointerException {
    private static final long serialVersionUID = 1L;
    
    public InvalidStoryException() {
        super();
    }
    
    public InvalidStoryException(String s) {
        super(s);
    }
}
