package com.global.commtech.test.anagramfinder.validator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.global.commtech.test.anagramfinder.exception.AnagramFinderException;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ThrowIfTest {

    @Nested
    class ListContains {

        @Test
        void throwsWhenListContainsWordWithDifferentLength() {
            assertThatThrownBy(() -> ThrowIf.ListContains.wordsWithDifferentLength(List.of("aa", "aaa")))
                .isInstanceOf(AnagramFinderException.class)
                .hasMessage("All words in the list must be of the same size for the list: [aa, aaa]");

        }

        @Test
        void doesNotThrowWhenListIsEmpty() {
            ThrowIf.ListContains.wordsWithDifferentLength(List.of());
        }
    }

    @Nested
    class Word {

        @Test
        void doesNotThrowWhenWordIsNotEmpty() {
            ThrowIf.Word.isNullOrEmpty("a-word");
        }

        @Test
        void throwsWhenWordIsEmpty() {
            assertThatThrownBy(() -> ThrowIf.Word.isNullOrEmpty(""))
                .isInstanceOf(AnagramFinderException.class)
                .hasMessage("Word must not be null or empty");
        }

        @Test
        void throwsWhenWordIsNull() {
            assertThatThrownBy(() -> ThrowIf.Word.isNullOrEmpty(null))
                .isInstanceOf(AnagramFinderException.class)
                .hasMessage("Word must not be null or empty");
        }
    }
}