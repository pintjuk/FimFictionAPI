package at.yawk.fimfiction.api;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Basic support for cookies and logging into FIMFiction accounts
 * 
 * @author Yawkat
 * 
 */
public class AccountInternetAccess extends SimpleInternetAccess implements MatureInternetAccess, AccountInterface {
    private Map<String, String> cookies    = new HashMap<String, String>();
    private boolean             isLoggedIn = false;
    
    public AccountInternetAccess(InternetAccess parentAccess) {
        super(parentAccess);
    }
    
    public AccountInternetAccess() {
        super();
    }
    
    @Override
    protected String getCookies() {
        final StringBuilder sb = new StringBuilder(super.getCookies());
        for(final Entry<String, String> e : cookies.entrySet()) {
            sb.append(e.getKey());
            sb.append('=');
            sb.append(e.getValue());
            sb.append("; ");
        }
        return sb.toString();
    }
    
    @Override
    public boolean login(String username, String password) {
        try {
            final URLConnection urlc = connect(new URL(URLs.LOGIN));
            urlc.connect();
            urlc.getOutputStream().write(("username=" + username + "&password=" + password).getBytes());
            urlc.getOutputStream().flush();
            final char c = (char)(urlc.getInputStream().read());
            if(c == '0') {
                String headerName;
                for(int i = 1; (headerName = urlc.getHeaderFieldKey(i)) != null; i++) {
                    if(headerName.equals("Set-Cookie")) {
                        final String s = urlc.getHeaderField(i);
                        cookies.put(s.substring(0, s.indexOf('=')), s.substring(s.indexOf('=') + 1, s.indexOf(';')));
                    }
                }
                return isLoggedIn = true;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return isLoggedIn = false;
    }
    
    @Override
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}
