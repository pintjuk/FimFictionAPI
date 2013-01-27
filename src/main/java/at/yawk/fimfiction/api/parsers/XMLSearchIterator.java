package at.yawk.fimfiction.api.parsers;

import java.net.URLConnection;

import at.yawk.fimfiction.api.Identifier;
import at.yawk.fimfiction.api.InternetAccess;
import at.yawk.yxml.Lexer;

abstract class XMLSearchIterator<S extends Identifier> extends SearchIterator<S> {
    public XMLSearchIterator(String request, InternetAccess internet) {
        super(request, internet);
    }
    
    @Override
    protected S[] getNextBlock(URLConnection url) {
        try {
            return getNextBlock(new Lexer(url));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    protected abstract S[] getNextBlock(Lexer reader) throws Exception;
}
