package at.yawk.fimfiction.api.cache;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import at.yawk.fimfiction.api.Character;

import at.yawk.fimfiction.api.InternetAccess;

/**
 * Class for storing character images so they don't need to be redownloaded all
 * the time.
 * 
 * @author Yawkat
 * 
 */
public class CharacterImageCache {
    private static final boolean       DEFAULT_ANONYMOUS       = false;
    private static final int           DEFAULT_MAXIMUM_THREADS = 5;
    
    private static CharacterImageCache currentInstance         = null;
    
    /**
     * Get the current instance
     * 
     * @return The current instance or <code>null</code> if none has been
     *         created yet
     */
    public static CharacterImageCache getCurrentInstance() {
        return currentInstance;
    }
    
    /**
     * Get the current instance
     * 
     * @param defaultAccess
     *            The internet access used by the cache should the instance not
     *            be set yet
     * @param defaultMaximumThreads
     *            The maximum thread count used by the cache should the instance
     *            not be set yet
     * @return The current instance, or, if not set, a new non-anonymous
     *         instance with the given arguments
     */
    public static CharacterImageCache getCurrentInstance(InternetAccess defaultAccess, int defaultMaximumThreads) {
        return currentInstance == null ? currentInstance = new CharacterImageCache(defaultAccess, defaultMaximumThreads) : currentInstance;
    }
    
    /**
     * Get the current instance
     * 
     * @param defaultAccess
     *            The internet access used by the cache should the instance not
     *            be set yet
     * @return The current instance, or, if not set, a new non-anonymous
     *         instance with the given arguments
     */
    public static CharacterImageCache getCurrentInstance(InternetAccess defaultAccess) {
        return getCurrentInstance(defaultAccess, DEFAULT_MAXIMUM_THREADS);
    }
    
    private final Map<Character, BufferedImage> images = new EnumMap<Character, BufferedImage>(Character.class);
    private final InternetAccess                internet;
    
    private final Executor                      downloader;
    
    /**
     * @param internet
     *            The internet access
     * @param maximumThreads
     *            The maximum thread amount for downloading
     * @param anonymous
     *            <code>false</code> if the new object should be set as the
     *            public current instance
     */
    public CharacterImageCache(InternetAccess internet, int maximumThreads, boolean anonymous) {
        this.internet = internet;
        downloader = Executors.newFixedThreadPool(maximumThreads);
        if(!anonymous)
            currentInstance = this;
    }
    
    /**
     * @param internet
     *            The internet access
     * @param maximumThreads
     *            The maximum thread amount for downloading
     */
    public CharacterImageCache(InternetAccess internet, int maximumThreads) {
        this(internet, maximumThreads, DEFAULT_ANONYMOUS);
    }
    
    /**
     * @param internet
     *            The internet access
     * @param anonymous
     *            <code>false</code> if the new object should be set as the
     *            public current instance
     */
    public CharacterImageCache(InternetAccess internet, boolean anonymous) {
        this(internet, DEFAULT_MAXIMUM_THREADS, anonymous);
    }
    
    /**
     * @param internet
     *            The internet access
     */
    public CharacterImageCache(InternetAccess internet) {
        this(internet, DEFAULT_MAXIMUM_THREADS);
    }
    
    /**
     * @param character
     *            The character
     * @return The character image or <code>null</code> if it is not downloaded
     *         yet.
     */
    public BufferedImage getImage(Character character) {
        if(!images.containsKey(character))
            updateCharacter(character);
        return images.get(character);
    }
    
    /**
     * Update a character image.
     * 
     * @param character
     *            The character
     */
    public void updateCharacter(final Character character) {
        images.put(character, null);
        downloader.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    images.put(character, ImageIO.read(internet.connect(new URL(character.getImageLocation())).getInputStream()));
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
