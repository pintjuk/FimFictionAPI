package at.yawk.fimfiction.api;

/**
 * An internet access with a mature flag
 * 
 * @author Yawkat
 */
public interface MatureInternetAccess extends InternetAccess {
    /**
     * @return Whether mature stories should be shown
     */
    public boolean isMature();
    
    /**
     * Set the mature flag
     * 
     * @param flag
     *            the new state
     * @return this access.
     */
    public MatureInternetAccess setMature(boolean flag);
}
