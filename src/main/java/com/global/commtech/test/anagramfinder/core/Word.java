package com.global.commtech.test.anagramfinder.core;

import com.global.commtech.test.anagramfinder.validator.ThrowIf;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Word {

    private final String word;
    private final Map<Character, Integer> analysedWord;

    public Word(final String word) {
        ThrowIf.Word.isNullOrEmpty(word);
        this.word = word;
        this.analysedWord = analyse(word);
    }

    public static List<Word> fromStrings(final List<String> strings) {
        return strings.parallelStream().map(Word::new)
            .collect(Collectors.toList());
    }

    public boolean isAnagramOf(final Word other) {
        return analysedWord.equals(other.analysedWord);
    }

    public String value() {
        return word;
    }

    private Map<Character, Integer> analyse(final String word) {
        final Map<Character, Integer> analysedWord = new HashMap<>();
        final var chars = word.toCharArray();
        for (var c : chars) {
            analysedWord.compute(c, (k, v) -> v == null ? 1 : ++v);
        }
        return analysedWord;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Word word1 = (Word) o;
        return word.equals(word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    @Override
    public String toString() {
        return word;
    }
}
