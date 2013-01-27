package at.yawk.fimfiction.api.parsers;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import at.yawk.fimfiction.api.Identifier;
import at.yawk.fimfiction.api.InternetAccess;
import at.yawk.fimfiction.api.URLs;

abstract class SearchIterator<S extends Identifier> implements Iterator<S> {
    private final String         request;
    private final InternetAccess internet;
    
    private int                  currentIndex   = 0;
    private S[]                  currentBlock   = null;
    private int                  currentPage    = 1;
    private int                  lastPageLength = 0;
    
    public SearchIterator(String request, InternetAccess internet) {
        this.request = URLs.SEARCH_BASE + request + "&page=";
        this.internet = internet;
    }
    
    @Override
    public boolean hasNext() {
        if(currentBlock == null || currentIndex >= currentBlock.length)
            return readNextBlock();
        else
            return true;
    }
    
    @Override
    public S next() {
        if(hasNext())
            return currentBlock[currentIndex++];
        else
            throw new NoSuchElementException();
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    private boolean readNextBlock() {
        if(currentBlock == null || currentBlock.length >= lastPageLength) {
            try {
                currentBlock = getNextBlock(internet.connect(new URL(request + currentPage)));
                currentPage++;
                currentIndex = 0;
                lastPageLength = currentBlock.length;
                return lastPageLength > 0;
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    protected abstract S[] getNextBlock(URLConnection url);
}
