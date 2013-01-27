package at.yawk.fimfiction.api.parsers;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import at.yawk.fimfiction.api.Category;
import at.yawk.fimfiction.api.Chapter;
import at.yawk.fimfiction.api.Character;
import at.yawk.fimfiction.api.ContentRating;
import at.yawk.fimfiction.api.Identifier;
import at.yawk.fimfiction.api.InternetAccess;
import at.yawk.fimfiction.api.MatureCategory;
import at.yawk.fimfiction.api.StoryAccess;
import at.yawk.fimfiction.api.StoryStatus;
import at.yawk.fimfiction.api.VisibleStoryMeta;
import at.yawk.fimfiction.api.factories.StoryMetaFactory;
import at.yawk.fimfiction.api.immutable.SimpleAuthor;
import at.yawk.fimfiction.api.immutable.SimpleChapter;
import at.yawk.fimfiction.api.immutable.SimpleIdentifier;
import at.yawk.fimfiction.api.immutable.SimpleStoryAccess;

public class MetaSearchIterator extends SearchIterator<StoryAccess<VisibleStoryMeta>> {
    public MetaSearchIterator(String request, InternetAccess internet) {
        super(request, internet);
    }
    
    @Override
    protected StoryAccess<VisibleStoryMeta>[] getNextBlock(URLConnection url) {
        try {
            return getNextBlock(new Lexer(url));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    protected StoryAccess<VisibleStoryMeta>[] getNextBlock(Lexer reader) throws Exception {
        final List<StoryAccess<VisibleStoryMeta>> stories = new ArrayList<StoryAccess<VisibleStoryMeta>>();
        StoryMetaFactory currentStory = null;
        Identifier currentStoryId = null;
        Node n;
        boolean awaitingTitle = false;
        boolean awaitingAuthor = false;
        while((n = reader.nextNode()) != null) {
            if(currentStory == null) {
                if(n instanceof TagNode && !((TagNode)n).isEndTag() && ((TagNode)n).getTagName().equals("DIV") && "content_box post_content_box".equals(((TagNode)n).getAttribute("class"))) {
                    currentStory = new StoryMetaFactory();
                    currentStory.setCommentAmount(-1);
                }
            } else {
                if(n instanceof TagNode) {
                    final TagNode tn = (TagNode)n;
                    if(!((TagNode)n).isEndTag()) {
                        if(tn.getTagName().equals("DIV") && "story_stats".equals(tn.getAttribute("class"))) {
                            stories.add(new SimpleStoryAccess<VisibleStoryMeta>(currentStoryId, currentStory));
                            currentStory = null;
                            currentStoryId = null;
                        } else {
                            if(awaitingTitle) {
                                if(tn.getTagName().equals("A") && tn.getAttribute("id") == null) {
                                    final String partHref = ((TagNode)n).getAttribute("href").substring(7);
                                    currentStoryId = new SimpleIdentifier(Integer.parseInt(partHref.substring(0, partHref.indexOf('/'))));
                                    currentStory.setTitle(StringEscapeUtils.unescapeHtml(reader.nextNode().getText()));
                                    awaitingTitle = false;
                                    awaitingAuthor = true;
                                }
                            } else if(tn.getTagName().equals("H2")) {
                                awaitingTitle = true;
                            } else if(awaitingAuthor) {
                                if(tn.getTagName().equals("A")) {
                                    currentStory.setAuthor(new SimpleAuthor(0, reader.nextNode().getText()));
                                    awaitingAuthor = false;
                                }
                            } else if(tn.getTagName().equals("DIV") && "description".equals(tn.getAttribute("class"))) {
                                currentStory.setDescription("");
                                while(!((n = reader.nextNode()) instanceof TagNode) || !((TagNode)n).isEndTag() || !"DIV".equals(((TagNode)n).getTagName())) {
                                    final String s;
                                    if(n instanceof TagNode) {
                                        if(((TagNode)n).getTagName().equals("P")) {
                                            if(((TagNode)n).isEndTag() || "double".equals(((TagNode)n).getAttribute("class")))
                                                s = "\n";
                                            else
                                                s = "";
                                        } else if(((TagNode)n).getTagName().equals("B")) {
                                            if(((TagNode)n).isEndTag())
                                                s = "[/b]";
                                            else
                                                s = "[b]";
                                        } else if(((TagNode)n).getTagName().equals("I")) {
                                            if(((TagNode)n).isEndTag())
                                                s = "[/i]";
                                            else
                                                s = "[i]";
                                        } else {
                                            s = "";
                                        }
                                    } else {
                                        s = n.getText();
                                    }
                                    currentStory.setDescription(currentStory.getDescription() + s);
                                }
                                currentStory.setDescription(StringEscapeUtils.unescapeHtml(currentStory.getDescription().trim()));
                            } else if(tn.getTagName().equals("SPAN") && "likes".equals(tn.getAttribute("class"))) {
                                currentStory.setLikes(Integer.parseInt(reader.nextNode().getText().replace(",", "")));
                            } else if(tn.getTagName().equals("SPAN") && "dislikes".equals(tn.getAttribute("class"))) {
                                currentStory.setDislikes(Integer.parseInt(reader.nextNode().getText().replace(",", "")));
                            } else if(tn.getTagName().equals("DIV") && "comment_container".equals(tn.getAttribute("class"))) {
                                while(!((n = reader.nextNode()) instanceof TextNode) || n.getText().trim().length() == 0)
                                    ;
                                currentStory.setCommentAmount(Integer.parseInt(n.getText().trim().replace(",", "")));
                            } else if(tn.getTagName().equals("DIV") && "left".equals(tn.getAttribute("class"))) {
                                while(!((n = reader.nextNode()) instanceof TagNode))
                                    ;
                                currentStory.setFullImageLocation(((TagNode)n).getAttribute("href"));
                                while(!((n = reader.nextNode()) instanceof TagNode))
                                    ;
                                currentStory.setImageLocation(((TagNode)n).getAttribute("src"));
                            } else if(tn.getTagName().equals("DIV") && "categories".equals(tn.getAttribute("class"))) {
                                final List<Category> categories = new ArrayList<Category>(5);
                                while(true) {
                                    n = reader.nextNode();
                                    if(n instanceof TagNode)
                                        if(((TagNode)n).getTagName().equals("DIV"))
                                            break;
                                        else {
                                            final String c = reader.nextNode().getText();
                                            if(c.trim().length() > 0) {
                                                for(Category ca : Category.values())
                                                    if(ca.getName().equalsIgnoreCase(c)) {
                                                        categories.add(ca);
                                                        break;
                                                    }
                                            }
                                        }
                                }
                                currentStory.setCategories(categories.toArray(new Category[categories.size()]));
                                while(!((n = reader.nextNode()) instanceof TextNode) || n.getText().trim().isEmpty())
                                    ;
                                currentStory.setMaximumChapterViews(Integer.parseInt(n.getText().substring(0, n.getText().indexOf('(')).trim().replace(",", "")));
                                currentStory.setTotalViews(Integer.parseInt(n.getText().substring(n.getText().indexOf('(') + 1, n.getText().indexOf(')')).trim().replace(",", "")));
                            } else if(tn.getTagName().equals("UL") && "chapters".equals(tn.getAttribute("class"))) {
                                String title = null;
                                int words = -1;
                                int id = -1;
                                final List<Chapter> chapters = new ArrayList<Chapter>();
                                while(true) {
                                    n = reader.nextNode();
                                    if(n instanceof TagNode && "save_ordering".equals(((TagNode)n).getAttribute("class")))
                                        break;
                                    else if(n instanceof TagNode && ((TagNode)n).getTagName().equals("LI") && ((TagNode)n).isEndTag())
                                        chapters.add(new SimpleChapter(id, title, words, -1, null));
                                    else if(n instanceof TagNode && ((TagNode)n).getTagName().equals("DIV") && "word_count".equals(((TagNode)n).getAttribute("class")))
                                        words = Integer.parseInt(reader.nextNode().getText().replaceAll("[^0-9]", ""));
                                    else if(n instanceof TagNode && ((TagNode)n).getTagName().equals("DIV") && "download_container".equals(((TagNode)n).getAttribute("class"))) {
                                        while(!((n = reader.nextNode()) instanceof TagNode) || !((TagNode)n).getTagName().equals("A"))
                                            ;
                                        id = Integer.parseInt(((TagNode)n).getAttribute("href").substring(30));
                                    } else if(n instanceof TagNode && ((TagNode)n).getTagName().equals("A") && "chapter_link".equals(((TagNode)n).getAttribute("class")))
                                        title = StringEscapeUtils.unescapeHtml(reader.nextNode().getText());
                                }
                                currentStory.setChapters(chapters.toArray(new Chapter[chapters.size()]));
                                while(!((n = reader.nextNode()) instanceof TagNode) || !((TagNode)n).getTagName().equals("IMG") || !"//www.fimfiction-static.net/images/icons/epub.png".equals(((TagNode)n).getAttribute("src")))
                                    ;
                                while(!((n = reader.nextNode()) instanceof TextNode) || n.getText().trim().isEmpty())
                                    ;
                                for(StoryStatus ss : StoryStatus.values())
                                    if(ss.getName().equalsIgnoreCase(n.getText().trim())) {
                                        currentStory.setStoryStatus(ss);
                                        break;
                                    }
                                while(!((n = reader.nextNode()) instanceof TagNode) || !((TagNode)n).getTagName().equals("SPAN"))
                                    ;
                                n = reader.nextNode();
                                for(ContentRating cr : ContentRating.values())
                                    if(cr.toString().equalsIgnoreCase(n.getText().trim())) {
                                        currentStory.setContentRating(cr);
                                        break;
                                    }
                                while(true) {
                                    while(!((n = reader.nextNode()) instanceof TagNode) || !((TagNode)n).getTagName().equals("B"))
                                        ;
                                    n = reader.nextNode();
                                    if(n.getText().trim().equals("&#xb7;")) {
                                        while(!((n = reader.nextNode()) instanceof TextNode) || n.getText().trim().isEmpty())
                                            ;
                                        for(MatureCategory mc : MatureCategory.values()) {
                                            if(mc.toString().equalsIgnoreCase(n.getText().trim())) {
                                                final MatureCategory[] ac = currentStory.getMatureCategories() == null ? new MatureCategory[1] : Arrays.copyOf(currentStory.getMatureCategories(), currentStory.getMatureCategories().length + 1);
                                                ac[ac.length - 1] = mc;
                                                currentStory.setMatureCategories(ac);
                                                break;
                                            }
                                        }
                                    } else {
                                        currentStory.setWords(Integer.parseInt(n.getText().trim().replace(",", "")));
                                        break;
                                    }
                                }
                            } else if(tn.getTagName().equals("A") && "character_icon pill_button pill_button_img_only".equals(tn.getAttribute("class"))) {
                                while(!((n = reader.nextNode()) instanceof TagNode) || !((TagNode)n).getTagName().equals("IMG"))
                                    ;
                                for(Character c : Character.values())
                                    if(c.getImageLocation().equals("http:" + ((TagNode)n).getAttribute("src"))) {
                                        final Character[] ac = currentStory.getCharacters() == null ? new Character[1] : Arrays.copyOf(currentStory.getCharacters(), currentStory.getCharacters().length + 1);
                                        ac[ac.length - 1] = c;
                                        currentStory.setCharacters(ac);
                                        break;
                                    }
                            }
                        }
                    }
                }
            }
        }
        return stories.toArray(new StoryAccess[stories.size()]);
    }
}
