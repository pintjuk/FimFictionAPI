package at.yawk.fimfiction.api.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import at.yawk.fimfiction.api.DownloadType;
import at.yawk.fimfiction.api.Identifier;
import at.yawk.fimfiction.api.InternetAccess;
import at.yawk.fimfiction.api.URLs;

/**
 * Download helper for a story
 * 
 * @author Yawkat
 * 
 */
public final class Downloader {
    private Downloader() {
        
    }
    
    /**
     * Download a story
     * 
     * @param story
     *            The story ID
     * @param output
     *            The output stream
     * @param dlType
     *            The download type
     * @param internet
     *            The internet access
     * @throws IOException
     *             If a connection exception occurs
     */
    public static void downloadStory(Identifier story, OutputStream output, DownloadType dlType, InternetAccess internet) throws IOException {
        final String s;
        switch(dlType) {
        case EPUB:
            s = URLs.DOWNLOAD_EPUB;
            break;
        case HTML:
            s = URLs.DOWNLOAD_HTML;
            break;
        case TXT:
            s = URLs.DOWNLOAD_TXT;
            break;
        default:
            throw new IllegalArgumentException("Unknown download type " + dlType);
        }
        final URLConnection c = internet.connect(new URL(s + story.getId()));
        c.connect();
        copyStream(c.getInputStream(), output);
    }
    
    /**
     * Convenience method, uses {@link FileOutputStream} to download a story
     * 
     * @param story
     *            The story ID
     * @param output
     *            The output file
     * @param dlType
     *            The download type
     * @param internet
     *            The internet access
     * @throws IOException
     *             If a connection exception occurs
     * @see Downloader#downloadStory(Identifier, OutputStream, DownloadType,
     *      InternetAccess)
     */
    public static void downloadStory(Identifier story, File output, DownloadType dlType, InternetAccess internet) throws IOException {
        final OutputStream os = new FileOutputStream(output);
        downloadStory(story, os, dlType, internet);
        os.close();
    }
    
    /**
     * Download a Chapter
     * 
     * @param story
     *            The story ID
     * @param output
     *            The output stream
     * @param dlType
     *            The download type, EPUB is not supported
     * @param internet
     *            The internet access
     * @throws IOException
     *             If a connection exception occurs
     */
    public static void downloadChapter(Identifier chapter, OutputStream output, DownloadType dlType, InternetAccess internet) throws IOException {
        final String s;
        switch(dlType) {
        case EPUB:
            throw new IllegalArgumentException("EPUB is not supported for chapters");
        case HTML:
            s = URLs.DOWNLOAD_CHAPTER_HTML;
            break;
        case TXT:
            s = URLs.DOWNLOAD_CHAPTER_TXT;
            break;
        default:
            throw new IllegalArgumentException("Unknown download type " + dlType);
        }
        final URLConnection c = internet.connect(new URL(s + chapter.getId()));
        c.connect();
        copyStream(c.getInputStream(), output);
    }
    
    /**
     * Convenience method
     * 
     * @param story
     *            The story ID
     * @param output
     *            The output file
     * @param dlType
     *            The download type
     * @param internet
     *            The internet access
     * @throws IOException
     *             If a connection exception occurs
     * @see Downloader#downloadStory(Identifier, OutputStream, DownloadType,
     *      InternetAccess)
     */
    public static void downloadChapter(Identifier chapter, File output, DownloadType dlType, InternetAccess internet) throws IOException {
        final OutputStream os = new FileOutputStream(output);
        downloadChapter(chapter, os, dlType, internet);
        os.close();
    }
    
    private static void copyStream(InputStream in, OutputStream out) throws IOException {
        final byte[] tmp = new byte[1024];
        int length;
        while((length = in.read(tmp)) > 0)
            out.write(tmp, 0, length);
    }
}
