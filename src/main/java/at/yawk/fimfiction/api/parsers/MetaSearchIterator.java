package at.yawk.fimfiction.api.parsers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
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
import at.yawk.yxml.Lexer;

public class MetaSearchIterator extends XMLSearchIterator<StoryAccess<VisibleStoryMeta>> {
    public MetaSearchIterator(String request, InternetAccess internet) {
        super(request, internet);
    }
    
    @SuppressWarnings("unchecked")
    protected StoryAccess<VisibleStoryMeta>[] getNextBlock(Lexer reader) throws Exception {
        final List<StoryAccess<VisibleStoryMeta>> stories = new ArrayList<StoryAccess<VisibleStoryMeta>>();
        StoryMetaFactory currentStory = null;
        Identifier currentStoryId = null;
        boolean awaitingTitle = false;
        boolean awaitingAuthor = false;
        while(reader.getNext()) {
            if(currentStory == null) {
                if(reader.isTag() && !reader.isEndTagOnly() && reader.getLowercaseTagName().equals("div") && "content_box post_content_box".equals(reader.getAttribute("class"))) {
                    currentStory = new StoryMetaFactory();
                    currentStory.setCommentAmount(-1);
                }
            } else {
                if(reader.isTag()) {
                    if(!reader.isEndTagOnly()) {
                        if(reader.getLowercaseTagName().equals("div"))
                            System.out.println(reader.getAttribute("class"));
                        if(reader.getLowercaseTagName().equals("div") && "story_stats".equals(reader.getAttribute("class"))) {
                            stories.add(new SimpleStoryAccess<VisibleStoryMeta>(currentStoryId, currentStory));
                            currentStory = null;
                            currentStoryId = null;
                        } else {
                            if(awaitingTitle) {
                                if(reader.getLowercaseTagName().equals("a") && reader.getAttribute("id") == null) {
                                    final String partHref = reader.getAttribute("href").substring(7);
                                    currentStoryId = new SimpleIdentifier(Integer.parseInt(partHref.substring(0, partHref.indexOf('/'))));
                                    reader.getNext();
                                    currentStory.setTitle(StringEscapeUtils.unescapeHtml(reader.getCurrentElementContent().trim()));
                                    awaitingTitle = false;
                                    awaitingAuthor = true;
                                }
                            } else if(reader.getLowercaseTagName().equals("h2")) {
                                awaitingTitle = true;
                            } else if(awaitingAuthor) {
                                if(reader.getLowercaseTagName().equals("a")) {
                                    reader.getNext();
                                    currentStory.setAuthor(new SimpleAuthor(0, reader.getCurrentElementContent()));
                                    awaitingAuthor = false;
                                }
                            } else if(reader.getLowercaseTagName().equals("div") && "description".equals(reader.getAttribute("class"))) {
                                currentStory.setDescription("");
                                while(reader.getNext() && (!reader.isTag() || !reader.isEndTagOnly() || !"div".equals(reader.getLowercaseTagName()))) {
                                    final String s;
                                    if(reader.isTag()) {
                                        if(reader.getLowercaseTagName().equals("p")) {
                                            if(reader.isEndTagOnly() || "double".equals(reader.getAttribute("class")))
                                                s = "\n";
                                            else
                                                s = "";
                                        } else if(reader.getLowercaseTagName().equals("b")) {
                                            if(reader.isEndTagOnly())
                                                s = "[/b]";
                                            else
                                                s = "[b]";
                                        } else if(reader.getLowercaseTagName().equals("i")) {
                                            if(reader.isEndTagOnly())
                                                s = "[/i]";
                                            else
                                                s = "[i]";
                                        } else {
                                            s = "";
                                        }
                                    } else {
                                        s = reader.getCurrentElementContent();
                                    }
                                    currentStory.setDescription(currentStory.getDescription() + s);
                                }
                                currentStory.setDescription(StringEscapeUtils.unescapeHtml(currentStory.getDescription().trim()));
                            } else if(reader.getLowercaseTagName().equals("span") && "likes".equals(reader.getAttribute("class"))) {
                                reader.getNext();
                                currentStory.setLikes(Integer.parseInt(reader.getCurrentElementContent().replace(",", "")));
                            } else if(reader.getLowercaseTagName().equals("span") && "dislikes".equals(reader.getAttribute("class"))) {
                                reader.getNext();
                                currentStory.setDislikes(Integer.parseInt(reader.getCurrentElementContent().replace(",", "")));
                            } else if(reader.getLowercaseTagName().equals("div") && "comment_container".equals(reader.getAttribute("class"))) {
                                while(reader.getNext() && (reader.isTag() || reader.getCurrentElementContent().trim().length() == 0))
                                    ;
                                currentStory.setCommentAmount(Integer.parseInt(reader.getCurrentElementContent().trim().replace(",", "")));
                            } else if(reader.getLowercaseTagName().equals("div") && "left".equals(reader.getAttribute("class"))) {
                                while(reader.getNext() && !reader.isTag())
                                    ;
                                currentStory.setFullImageLocation(reader.getAttribute("href"));
                                while(reader.getNext() && !reader.isTag())
                                    ;
                                currentStory.setImageLocation(reader.getAttribute("src"));
                            } else if(reader.getLowercaseTagName().equals("div") && "categories".equals(reader.getAttribute("class"))) {
                                final List<Category> categories = new ArrayList<Category>(5);
                                while(true) {
                                    reader.getNext();
                                    if(reader.isTag())
                                        if(reader.getLowercaseTagName().equals("div"))
                                            break;
                                        else {
                                            reader.getNext();
                                            final String c = reader.getCurrentElementContent().trim();
                                            if(c.length() > 0) {
                                                for(Category ca : Category.values())
                                                    if(ca.getName().equalsIgnoreCase(c)) {
                                                        categories.add(ca);
                                                        break;
                                                    }
                                            }
                                        }
                                }
                                currentStory.setCategories(categories.toArray(new Category[categories.size()]));
                                while(reader.getNext() && (reader.isTag() || reader.getCurrentElementContent().trim().isEmpty()))
                                    ;
                                currentStory.setMaximumChapterViews(Integer.parseInt(reader.getCurrentElementContent().substring(0, reader.getCurrentElementContent().indexOf('(')).trim().replace(",", "")));
                                currentStory.setTotalViews(Integer.parseInt(reader.getCurrentElementContent().substring(reader.getCurrentElementContent().indexOf('(') + 1, reader.getCurrentElementContent().indexOf(')')).trim().replace(",", "")));
                            } else if(reader.getLowercaseTagName().equals("ul") && "chapters".equals(reader.getAttribute("class"))) {
                                String title = null;
                                int words = -1;
                                int id = -1;
                                final List<Chapter> chapters = new ArrayList<Chapter>();
                                while(true) {
                                    reader.getNext();
                                    if(reader.isTag() && "save_ordering".equals(reader.getAttribute("class")))
                                        break;
                                    else if(reader.isTag() && reader.getLowercaseTagName().equals("li") && reader.isEndTagOnly())
                                        chapters.add(new SimpleChapter(id, title, words, -1, null));
                                    else if(reader.isTag() && reader.getLowercaseTagName().equals("div") && "word_count".equals(reader.getAttribute("class"))) {
                                        reader.getNext();
                                        words = Integer.parseInt(reader.getCurrentElementContent().replaceAll("[^0-9]", ""));
                                    } else if(reader.isTag() && reader.getLowercaseTagName().equals("div") && "download_container".equals(reader.getAttribute("class"))) {
                                        while(reader.getNext() && (!reader.isTag() || !reader.getLowercaseTagName().equals("a")))
                                            ;
                                        id = Integer.parseInt(reader.getAttribute("href").substring(30));
                                    } else if(reader.isTag() && reader.getLowercaseTagName().equals("a") && "chapter_link".equals(reader.getAttribute("class"))) {
                                        reader.getNext();
                                        title = StringEscapeUtils.unescapeHtml(reader.getCurrentElementContent().trim());
                                    }
                                }
                                currentStory.setChapters(chapters.toArray(new Chapter[chapters.size()]));
                                while(reader.getNext() && (!reader.isTag() || !reader.getLowercaseTagName().equals("img") || !"//www.fimfiction-static.net/images/icons/epub.png".equals(reader.getAttribute("src"))))
                                    ;
                                while(reader.getNext() && (reader.isTag() || reader.getCurrentElementContent().trim().isEmpty()))
                                    ;
                                for(StoryStatus ss : StoryStatus.values())
                                    if(ss.getName().equalsIgnoreCase(reader.getCurrentElementContent().trim())) {
                                        currentStory.setStoryStatus(ss);
                                        break;
                                    }
                                while(reader.getNext() && (!reader.isTag() || !reader.getLowercaseTagName().equals("span")))
                                    ;
                                reader.getNext();
                                for(ContentRating cr : ContentRating.values())
                                    if(cr.toString().equalsIgnoreCase(reader.getLowercaseTagName().trim())) {
                                        currentStory.setContentRating(cr);
                                        break;
                                    }
                                while(true) {
                                    while(reader.getNext() && (!reader.isTag() || !reader.getLowercaseTagName().equals("b")))
                                        ;
                                    reader.getNext();
                                    if(reader.getCurrentElementContent().trim().equals("&#xb7;")) {
                                        while(reader.getNext() && (reader.isTag() || reader.getCurrentElementContent().trim().isEmpty()))
                                            ;
                                        for(MatureCategory mc : MatureCategory.values()) {
                                            if(mc.toString().equalsIgnoreCase(reader.getCurrentElementContent().trim())) {
                                                final MatureCategory[] ac = currentStory.getMatureCategories() == null ? new MatureCategory[1] : Arrays.copyOf(currentStory.getMatureCategories(), currentStory.getMatureCategories().length + 1);
                                                ac[ac.length - 1] = mc;
                                                currentStory.setMatureCategories(ac);
                                                break;
                                            }
                                        }
                                    } else {
                                        currentStory.setWords(Integer.parseInt(reader.getCurrentElementContent().trim().replace(",", "")));
                                        break;
                                    }
                                }
                            } else if(reader.getLowercaseTagName().equals("a") && "character_icon pill_button pill_button_img_only".equals(reader.getAttribute("class"))) {
                                while(reader.getNext() && (!reader.isTag() || !reader.getLowercaseTagName().equals("img")))
                                    ;
                                for(Character c : Character.values())
                                    if(c.getImageLocation().equals("http:" + reader.getAttribute("src"))) {
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
