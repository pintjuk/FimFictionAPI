package at.yawk.fimfiction.api;

public class URLs {
	public static final String	FIMFICTION			= "http://www.fimfiction.net/";
	public static final String	FIMFICTION_STATIC	= "http://www.fimfiction-static.net/";
	public static final String	FIMFICTION_AJAX		= FIMFICTION + "ajax/";
	public static final String	FIMFICTION_DOWNLOAD	= FIMFICTION + "download_";
	public static final String	SEARCH_BASE			= FIMFICTION + "index.php?";
	public static final String	API_BASE			= FIMFICTION + "api/story.php?story=";
	public static final String	LOGIN				= FIMFICTION_AJAX + "login.php";
	public static final String	READLATER			= FIMFICTION_AJAX + "add_read_it_later.php";
	public static final String	FAVORITE			= FIMFICTION_AJAX + "add_favourite.php";
	public static final String	UNREAD				= FIMFICTION_AJAX + "toggle_read.php";
	public static final String	RATE				= FIMFICTION_AJAX + "rate.php";
	public static final String	STORY				= FIMFICTION + "story/";
	public static final String	DOWNLOAD_TXT		= FIMFICTION_DOWNLOAD + "story.php?story=";
	public static final String	DOWNLOAD_HTML		= FIMFICTION_DOWNLOAD + "story.php?html&story=";
	public static final String	DOWNLOAD_EPUB		= FIMFICTION_DOWNLOAD + "epub.php?story=";
}