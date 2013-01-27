package at.yawk.fimfiction.api.parsers;

import java.util.ArrayList;
import java.util.List;

import at.yawk.fimfiction.api.Identifier;
import at.yawk.fimfiction.api.InternetAccess;
import at.yawk.fimfiction.api.immutable.SimpleIdentifier;
import at.yawk.yxml.Lexer;

public class IDSearchIterator extends XMLSearchIterator<Identifier> {
    public IDSearchIterator(String request, InternetAccess internet) {
        super(request, internet);
    }
    
    @Override
    protected Identifier[] getNextBlock(Lexer lexer) throws Exception {
        final List<Identifier> identifiers = new ArrayList<Identifier>(10);
        boolean isInH2 = false;
        while(lexer.getNext()) {
            if(lexer.isTag() && !lexer.isEndTagOnly()) {
                if(isInH2) {
                    if(lexer.getLowercaseTagName().equals("a") && !lexer.getAttributes().containsKey("id")) {
                        final String partHref = lexer.getAttributes().get("href").substring(7);
                        identifiers.add(new SimpleIdentifier(Integer.parseInt(partHref.substring(0, partHref.indexOf('/')))));
                        isInH2 = false;
                    }
                } else if(lexer.getLowercaseTagName().equals("h2")) {
                    isInH2 = true;
                }
            }
        }
        return identifiers.toArray(new Identifier[identifiers.size()]);
    }
}
