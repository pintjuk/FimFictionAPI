package at.yawk.fimfiction.api;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * An internet access that only supports a mature flag, but no accounts.
 * 
 * @author Yawkat
 */
public class SimpleInternetAccess implements MatureInternetAccess {
    private boolean              mature = true;
    private final InternetAccess parentAccess;
    
    public SimpleInternetAccess(InternetAccess parentAccess) {
        this.parentAccess = parentAccess;
    }
    
    public SimpleInternetAccess() {
        this(null);
    }
    
    private final URLConnection getConnection(URL url) throws IOException {
        return parentAccess == null ? url.openConnection() : parentAccess.connect(url);
    }
    
    @Override
    public URLConnection connect(URL url) throws IOException {
        final URLConnection c = getConnection(url);
        c.setDoInput(true);
        c.setDoOutput(true);
        c.setRequestProperty("Cookie", getCookies());
        return c;
    }
    
    protected String getCookies() {
        return isMature() ? "view_mature=true; " : "";
    }
    
    @Override
    public boolean isMature() {
        return mature;
    }
    
    @Override
    public MatureInternetAccess setMature(boolean flag) {
        mature = flag;
        return this;
    }
}
