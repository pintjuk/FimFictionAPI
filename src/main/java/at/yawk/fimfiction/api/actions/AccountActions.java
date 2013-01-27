package at.yawk.fimfiction.api.actions;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import at.yawk.fimfiction.api.Identifier;
import at.yawk.fimfiction.api.InternetAccess;
import at.yawk.fimfiction.api.URLs;
import at.yawk.yxml.Lexer;

/**
 * Any actions that can be performed using an account
 * 
 * @author Yawkat
 * 
 */
public final class AccountActions {
    private AccountActions() {
        
    }
    
    /**
     * Mark a story as read later
     * 
     * @param story
     *            The story ID
     * @param readlater
     *            The flag
     * @param access
     *            The internet access
     * @throws IOException
     *             If anything goes wrong with the connection
     */
    public static void setReadLater(Identifier story, boolean readlater, InternetAccess access) throws IOException {
        final URLConnection c = access.connect(new URL(URLs.READLATER));
        c.connect();
        c.getOutputStream().write(("story=" + story.getId() + "&selected=" + (readlater ? 1 : 0)).getBytes());
        clear(c.getInputStream());
    }
    
    /**
     * Mark a story as favorite
     * 
     * @param story
     *            The story ID
     * @param favorite
     *            The favorite flag
     * @param emailNotification
     *            Whether an e-mail notification should be sent for any updates
     * @param access
     *            The internet access
     * @throws IOException
     *             If anything goes wrong
     */
    public static void setFavorite(Identifier story, boolean favorite, boolean emailNotification, InternetAccess access) throws IOException {
        final URLConnection c = access.connect(new URL(URLs.FAVORITE));
        c.connect();
        c.getOutputStream().write(("story=" + story.getId() + "&selected=" + (favorite ? 1 : 0) + "&email=" + (emailNotification ? 1 : 0)).getBytes());
        clear(c.getInputStream());
    }
    
    /**
     * FIMFiction does not support setting the read status but provides a toggle
     * only.
     * 
     * @param chapter
     *            The chapter ID
     * @param access
     *            The internet access
     * @return <code>true</code> if the chapter is now marked as read
     * @throws IOException
     *             if anything goes wrong
     */
    public static boolean toggleUnread(Identifier chapter, InternetAccess access) throws IOException {
        final URLConnection c = access.connect(new URL(URLs.READLATER));
        c.connect();
        c.getOutputStream().write(("chapter=" + chapter.getId()).getBytes());
        final StringBuilder sb = new StringBuilder();
        int i;
        while((i = c.getInputStream().read()) >= 0)
            sb.append((char)i);
        return sb.toString().trim().endsWith("tick.png");
    }
    
    /**
     * @param story
     *            The story ID
     * @param access
     *            The internet access
     * @return A boolean array containing the read status of all chapters
     *         individually
     * @throws IOException
     *             If any connection problems occur
     * @throws ParserException
     *             If any parse problems occur (probably to outdated API
     *             version)
     */
    public static boolean[] getHasRead(final Identifier story, final InternetAccess access) throws IOException {
        final List<Boolean> l = new ArrayList<Boolean>();
        final Lexer parser = new Lexer(access.connect(new URL(URLs.STORY + story.getId())));
        boolean isInChapter = false;
        while(parser.getNext()) {
            if(parser.isTag() && !parser.isEndTagOnly()) {
                if(isInChapter) {
                    if(parser.getLowercaseTagName().equals("img") && parser.getAttributes().containsKey("id")) {
                        l.add(parser.getAttributes().get("src").endsWith("tick.png"));
                        isInChapter = false;
                    }
                } else if(parser.getLowercaseTagName().equals("div") && "chapter_container".equals(parser.getAttributes().get("class")))
                    isInChapter = true;
            }
        }
        final boolean[] ab = new boolean[l.size()];
        int i = 0;
        for(Boolean b : l)
            ab[i++] = b;
        return ab;
    }
    
    /**
     * FIMFiction does not support un-liking a story again, as soon as you like
     * or unlike it you can only set it back to the opposite.
     * 
     * @param story
     *            The story ID
     * @param isLike
     *            <code>true</code> for like, <code>false</code> for dislike
     * @param token
     *            The like token
     * @param access
     *            The internet access
     * @throws IOException
     *             If any connection errors occur
     * @see AccountActions#getLikeToken(Identifier, InternetAccess)
     * @see AccountActions#setLike(Identifier, boolean, InternetAccess)
     */
    public static void setLike(Identifier story, boolean isLike, String token, InternetAccess access) throws IOException {
        final URLConnection c = access.connect(new URL(URLs.FAVORITE));
        c.connect();
        c.getOutputStream().write(("story=" + story.getId() + "&rating=" + (isLike ? 100 : 0) + "&ip=" + token).getBytes());
        clear(c.getInputStream());
    }
    
    /**
     * Load a like token for a given story. It is unknown when this token gets
     * changed.
     * 
     * @param story
     *            The story ID
     * @param access
     *            The internet access
     * @return The token
     * @throws ParserException
     *             If any parsing errors occur (due to an outdated API)
     * @throws IOException
     *             If any connection errors occur
     */
    public static String getLikeToken(Identifier story, InternetAccess access) throws IOException {
        final Lexer parser = new Lexer(access.connect(new URL(URLs.STORY + story.getId())));
        while(parser.getNext()) {
            if(parser.isTag() && !parser.isEndTagOnly() && parser.getLowercaseTagName().equals("a")) {
                final String clazz = parser.getAttributes().get("class");
                if(clazz != null && clazz.equals("like_button ")) {
                    final String s = parser.getAttributes().get("onclick").substring(21);
                    return s.substring(0, s.indexOf('\''));
                }
            }
        }
        return null;
    }
    
    /**
     * Convenience method for setting the like flag without token.
     * 
     * @param story
     *            The story ID
     * @param isLike
     *            <code>true</code> for like, <code>false</code> for dislike
     * @param access
     *            The internet access
     * @throws ParserException
     *             If any parsing errors occur (due to an outdated API)
     * @throws IOException
     *             If any connection errors occur
     * @see AccountActions#getLikeToken(Identifier, InternetAccess)
     * @see AccountActions#setLike(Identifier, boolean, String, InternetAccess)
     */
    public static void setLike(Identifier story, boolean isLike, InternetAccess access) throws IOException {
        setLike(story, isLike, getLikeToken(story, access), access);
    }
    
    private static void clear(InputStream is) throws IOException {
        while(is.read() >= 0)
            ;
    }
}
