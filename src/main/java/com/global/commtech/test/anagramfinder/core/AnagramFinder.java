package com.global.commtech.test.anagramfinder.core;

import com.global.commtech.test.anagramfinder.validator.ThrowIf;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.springframework.stereotype.Component;

@Component
public class AnagramFinder {

    public Map<Word, LinkedHashSet<Word>> groupAnagrams(final List<String> sameSizedWords) {
        ThrowIf.ListContains.wordsWithDifferentLength(sameSizedWords);
        final var listOfWords = Word.fromStrings(sameSizedWords);
        final LinkedHashMap<Word, LinkedHashSet<Word>> grouped = new LinkedHashMap<>();
        for (var currentWord : listOfWords) {
            grouped.keySet().parallelStream()
                .filter(key -> key.isAnagramOf(currentWord))
                .findFirst()
                .ifPresentOrElse(addCurrentWordToExistingGroup(grouped, currentWord),
                                 createANewGroupWithCurrentWordAsKey(grouped, currentWord));
        }
        return grouped;
    }

    private Runnable createANewGroupWithCurrentWordAsKey(final LinkedHashMap<Word, LinkedHashSet<Word>> grouped,
                                                         final Word currentWord) {
        return () -> {
            final LinkedHashSet<Word> anagramsOfCurrent = new LinkedHashSet<>();
            anagramsOfCurrent.add(currentWord);
            grouped.put(currentWord, anagramsOfCurrent);
        };
    }

    private Consumer<Word> addCurrentWordToExistingGroup(final LinkedHashMap<Word, LinkedHashSet<Word>> grouped, final Word currentWord) {
        return key -> grouped.get(key).add(currentWord);
    }


}
