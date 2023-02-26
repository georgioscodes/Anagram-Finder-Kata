package com.global.commtech.test.anagramfinder.validator;

import com.global.commtech.test.anagramfinder.exception.AnagramFinderException;
import java.util.List;

public final class ThrowIf {

    public static final class ListContain {

        public static void wordsWithDifferentLength(final List<String> strings) {
            if (strings.isEmpty()) {
                return;
            }

            final int length = strings.get(0).length();
            final long sameSizedElements = strings.parallelStream()
                .filter(str -> str.length() == length)
                .count();

            if (sameSizedElements != strings.size()) {
                throw new AnagramFinderException("All words in the list must be of the same size for the list: " + strings);
            }
        }
    }

    public static final class Word {

        public static void isNullOrEmpty(final String word) {
            if (word == null || "".equals(word)) {
                throw new AnagramFinderException("Word must not be null or empty");
            }
        }
    }
}
