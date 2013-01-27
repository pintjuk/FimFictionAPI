package at.yawk.fimfiction.api;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * An access interface to get the URLConnection for an URL. Used to apply
 * cookies or use proxies.
 * 
 * @author Yawkat
 */
public interface InternetAccess {
    /**
     * @param url
     *            The target URL
     * @return The connection
     * @throws IOException
     *             If connecting fails
     */
    public URLConnection connect(URL url) throws IOException;
}
