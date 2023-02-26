package com.global.commtech.test.anagramfinder.core;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.global.commtech.test.anagramfinder.exception.AnagramFinderException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WordTest {

    @ParameterizedTest
    @MethodSource("anagrams")
    void wordCanIdentifyWhenAnotherWordIsAnagram(final String word, final String other, final boolean isAnagram) {
        assertThat(new Word(word).isAnagramOf(new Word(other))).isEqualTo(isAnagram);
    }

    @Test
    void shouldNotBeNullOrEmpty() {
        assertThatThrownBy(() -> new Word(""))
            .isInstanceOf(AnagramFinderException.class);
    }

    static Stream<Arguments> anagrams() {
        return Stream.of(
            Arguments.of("word", "rdow", true),
            Arguments.of("aaa", "aaa", true),
            Arguments.of("av", "va", true),
            Arguments.of("av", "av", true),
            Arguments.of("abc", "acb", true),
            Arguments.of("abc", "acb", true),
            Arguments.of("cba", "acb", true),
            Arguments.of("abcdef", "abdcfe", true),
            Arguments.of("a", "abdcfe", false),
            Arguments.of("abc", "cbb", false),
            Arguments.of("aaaa", "aaaaa", false),
            Arguments.of("aaaab", "aaaaa", false)
        );
    }
}