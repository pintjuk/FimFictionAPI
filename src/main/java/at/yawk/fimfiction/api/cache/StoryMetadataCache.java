package at.yawk.fimfiction.api.cache;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.json.simple.parser.ParseException;

import at.yawk.fimfiction.api.Identifier;
import at.yawk.fimfiction.api.InternetAccess;
import at.yawk.fimfiction.api.JSONStoryMeta;
import at.yawk.fimfiction.api.immutable.EmptyStory;
import at.yawk.fimfiction.api.parsers.JSONMetaLoader;

/**
 * A cache for story metadata
 * 
 * @author Yawkat
 * 
 */
public class StoryMetadataCache {
    private static final boolean      DEFAULT_ANONYMOUS       = false;
    private static final int          DEFAULT_MAXIMUM_THREADS = 5;
    
    private static StoryMetadataCache currentInstance         = null;
    
    /**
     * @return The current instance or <code>null</code> if none is set yet.
     */
    public static StoryMetadataCache getCurrentInstance() {
        return currentInstance;
    }
    
    /**
     * @param defaultAccess
     *            The default internet access
     * @param defaultMaximumThreads
     *            The default maximum threads
     * @return The current instence or, if it is not set yet, a new
     *         non-anonymous instance with the given arguments.
     */
    public static StoryMetadataCache getCurrentInstance(InternetAccess defaultAccess, int defaultMaximumThreads) {
        return currentInstance == null ? currentInstance = new StoryMetadataCache(defaultAccess, defaultMaximumThreads) : currentInstance;
    }
    
    /**
     * @param defaultAccess
     *            The default internet access
     * @return The current instence or, if it is not set yet, a new
     *         non-anonymous instance with the given arguments.
     */
    public static StoryMetadataCache getCurrentInstance(InternetAccess defaultAccess) {
        return getCurrentInstance(defaultAccess, DEFAULT_MAXIMUM_THREADS);
    }
    
    private final Map<Identifier, JSONStoryMeta> metadata = new HashMap<Identifier, JSONStoryMeta>();
    private final InternetAccess                 internet;
    
    private final Executor                       downloader;
    
    /**
     * @param internet
     *            The internet access
     * @param maximumThreads
     *            The maximum thread amount
     * @param anonymous
     *            <code>false</code> if the current instance should be set to
     *            the new object
     */
    public StoryMetadataCache(InternetAccess internet, int maximumThreads, boolean anonymous) {
        this.internet = internet;
        downloader = Executors.newFixedThreadPool(maximumThreads);
    }
    
    /**
     * @param internet
     *            The internet access
     * @param maximumThreads
     *            The maximum thread amount
     */
    public StoryMetadataCache(InternetAccess internet, int maximumThreads) {
        this(internet, maximumThreads, DEFAULT_ANONYMOUS);
    }
    
    /**
     * @param internet
     *            The internet access
     * @param anonymous
     *            <code>false</code> if the current instance should be set to
     *            the new object
     */
    public StoryMetadataCache(InternetAccess internet, boolean anonymous) {
        this(internet, DEFAULT_MAXIMUM_THREADS, anonymous);
    }
    
    /**
     * @param internet
     *            The internet access
     */
    public StoryMetadataCache(InternetAccess internet) {
        this(internet, DEFAULT_MAXIMUM_THREADS);
    }
    
    /**
     * Get the current story meta for the given ID. If it is not loaded yet, it
     * will be updated synchronously.
     * 
     * @param id
     *            The ID
     * @return The metadata
     */
    public JSONStoryMeta getFinalStoryMeta(Identifier id) {
        if(!metadata.containsKey(id))
            updateStoryMeta(id);
        return metadata.get(id);
    }
    
    /**
     * Get the current story meta for the given ID. If it is not loaded yet, it
     * will be updated asynchronously.
     * 
     * @param id
     *            The ID
     * @return The metadata or <code>null</code> if it has not been loaded yet.
     */
    public JSONStoryMeta getStoryMetaAsync(Identifier id) {
        if(metadata.get(id) == null) {
            if(!metadata.containsKey(id))
                updateStoryMetaAsync(id);
            return EmptyStory.instance();
        } else
            return metadata.get(id);
    }
    
    /**
     * Update a story metadata asynchronously.
     * 
     * @param id
     *            The story ID
     */
    public void updateStoryMetaAsync(final Identifier id) {
        metadata.put(id, null);
        downloader.execute(new Runnable() {
            @Override
            public void run() {
                updateStoryMeta(id);
            }
        });
    }
    
    /**
     * Update a story metadata synchronously.
     * 
     * @param id
     *            The story ID
     */
    public void updateStoryMeta(Identifier id) {
        try {
            metadata.put(id, JSONMetaLoader.getStoryMeta(id, internet));
        } catch(IOException e) {
            e.printStackTrace();
        } catch(ParseException e) {
            e.printStackTrace();
        }
    }
}
