package at.yawk.fimfiction.api;

/**
 * An interface for any account classes
 * 
 * @author Yawkat
 * 
 */
public interface AccountInterface {
    /**
     * Login
     * 
     * @param username
     * @param password
     * @return <code>true</code> if logging in was successful, otherwise
     *         <code>false</code>
     */
    public boolean login(String username, String password);
    
    /**
     * @return <code>true</code> if the user is logged in
     */
    public boolean isLoggedIn();
}
