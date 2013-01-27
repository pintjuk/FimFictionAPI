package at.yawk.fimfiction.api.examples.wordcounter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.htmlparser.util.ParserException;

import at.yawk.fimfiction.api.AccountInternetAccess;
import at.yawk.fimfiction.api.Identifier;
import at.yawk.fimfiction.api.StoryAccess;
import at.yawk.fimfiction.api.VisibleChapter;
import at.yawk.fimfiction.api.VisibleStoryMeta;
import at.yawk.fimfiction.api.actions.AccountActions;
import at.yawk.fimfiction.api.factories.SearchRequestFactory;
import at.yawk.fimfiction.api.parsers.IDSearchIterable;
import at.yawk.fimfiction.api.parsers.MetaSearchIterable;

public class WordCounter {
	public static void main(String[] args) throws ParserException, IOException {
		System.out.println("Logging in");
		final AccountInternetAccess internet = new AccountInternetAccess();
		internet.login(args[0], args[1]);
		int ind;
		
		final Set<Identifier> unreadFavorites = new HashSet<>();
		ind = 0;
		System.out.print("Stage 1.1: Downloading unread favorites: " + ind++);
		for(Identifier i : new IDSearchIterable(new SearchRequestFactory().setUnread(true).setFavorite(true), internet)) {
			unreadFavorites.add(i);
			System.out.print("\rStage 1.1: Downloading unread favorites: " + ind++);
		}
		System.out.println("\rStage 1.1: Downloading unread favorites: done!");
		
		final Set<StoryAccess<VisibleStoryMeta>> favorites = new HashSet<>();
		ind = 0;
		System.out.print("Stage 1.2: Downloading favorites: " + ind++);
		for(StoryAccess<VisibleStoryMeta> i : new MetaSearchIterable(new SearchRequestFactory().setFavorite(true), internet)) {
			favorites.add(i);
			System.out.print("\rStage 1.2: Downloading favorites: " + ind++);
		}
		System.out.println("\rStage 1.2: Downloading favorites: done!");
		
		final Set<StoryAccess<VisibleStoryMeta>> readlater = new HashSet<>();
		ind = 0;
		System.out.print("Stage 1.3: Downloading readlater: " + ind++);
		for(StoryAccess<VisibleStoryMeta> i : new MetaSearchIterable(new SearchRequestFactory().setReadLater(true), internet)) {
			readlater.add(i);
			System.out.print("\rStage 1.3: Downloading readlater: " + ind++);
		}
		System.out.println("\rStage 1.3: Downloading readlater: done!");
		
		int totalWords = 0;
		final Set<Identifier> checkedStories = new HashSet<>();
		for(StoryAccess<VisibleStoryMeta> access : favorites) {
			int words;
			// Check if it contains unread chapters, if not take a shortcut
			if(unreadFavorites.contains(access.getIdentifier())) {
				words = 0;
				final boolean[] read = AccountActions.getHasRead(access.getIdentifier(), internet);
				int i = 0;
				for(VisibleChapter c : access.getMeta().getChapters())
					if(read[i++])
						words += c.getWords();
			} else {
				words = access.getMeta().getWords();
			}
			totalWords += words;
			checkedStories.add(access.getIdentifier());
			System.out.print("\rStage 2.1: Read words for story " + access.getId() + ": " + words + " (" + totalWords + " total)                ");
		}
		System.out.println();
		for(StoryAccess<VisibleStoryMeta> access : readlater) {
			if(!checkedStories.contains(access.getIdentifier())) {
				int words;
				words = 0;
				final boolean[] read = AccountActions.getHasRead(access.getIdentifier(), internet);
				int i = 0;
				for(VisibleChapter c : access.getMeta().getChapters())
					if(read[i++])
						words += c.getWords();
				
				totalWords += words;
				System.out.print("\rStage 2.2: Read words for story " + access.getId() + ": " + words + " (" + totalWords + " total)                ");
			}
		}
		System.out.println();
		System.out.println("Done! Total read words: " + totalWords);
	}
}
