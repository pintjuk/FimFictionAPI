package at.yawk.fimfiction.api.parsers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import at.yawk.fimfiction.api.Identifier;
import at.yawk.fimfiction.api.InternetAccess;
import at.yawk.fimfiction.api.SimpleInternetAccess;
import at.yawk.fimfiction.api.URLs;
import at.yawk.fimfiction.api.immutable.SimpleIdentifier;

public class RSSIdentifierFavoriteIterable implements Iterable<Identifier> {
    private final String         url;
    private final InternetAccess internet;
    
    public RSSIdentifierFavoriteIterable(Identifier userId, InternetAccess internet) {
        this.url = URLs.RSS_FAVORITE + userId.getId();
        this.internet = internet;
    }
    
    @Override
    public Iterator<Identifier> iterator() {
        try {
            final XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(internet.connect(new URL(url)).getInputStream());
            final List<Identifier> elements = new ArrayList<Identifier>();
            while(reader.hasNext()) {
                final int event = reader.next();
                if(event == XMLStreamReader.START_ELEMENT && reader.getName().getLocalPart().equals("link")) {
                    if(reader.next() == XMLStreamReader.CHARACTERS) {
                        final String s = reader.getText().trim().substring(32);
                        elements.add(new SimpleIdentifier(Integer.parseInt(s.substring(0, s.indexOf('/')))));
                    }
                }
            }
            return elements.iterator();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(XMLStreamException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
        for(Identifier i : new RSSIdentifierFavoriteIterable(new SimpleIdentifier(30603), new SimpleInternetAccess()))
            System.out.println(i.getId());
    }
}
