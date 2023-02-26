package com.global.commtech.test.anagramfinder.core;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.global.commtech.test.anagramfinder.exception.AnagramFinderException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnagramFinderTest {

    private AnagramFinder underTest;

    @BeforeEach
    void setUp() {
        underTest = new AnagramFinder();
    }

    @Test
    void orderIsMaintained() {
        final var expected = new LinkedHashMap<>();
        expected.put(new Word("aaa"), new LinkedHashSet<>(List.of(new Word("aaa"))));
        expected.put(new Word("aba"), new LinkedHashSet<>(List.of(new Word("aba"), new Word("baa"), new Word("aab"))));
        expected.put(new Word("wer"), new LinkedHashSet<>(List.of(new Word("wer"), new Word("rew"))));

        final Map<Word, LinkedHashSet<Word>> grouped =
            underTest.groupAnagrams(List.of("aaa", "aba", "baa", "aaa", "aab", "aaa","wer","rew"));

        assertThat(grouped).isEqualTo(expected);
        System.out.println(grouped);
    }

    @Test
    void canHaveGroupsWithSingleValue() {
        final var expected = new LinkedHashMap<>();
        expected.put(new Word("aaa"), new LinkedHashSet<>(List.of(new Word("aaa"))));
        expected.put(new Word("bbb"), new LinkedHashSet<>(List.of(new Word("bbb"))));
        expected.put(new Word("ccc"), new LinkedHashSet<>(List.of(new Word("ccc"))));
        expected.put(new Word("ddd"), new LinkedHashSet<>(List.of(new Word("ddd"))));

        final Map<Word, LinkedHashSet<Word>> grouped =
            underTest.groupAnagrams(List.of("aaa", "bbb", "ccc", "ddd"));

        assertThat(grouped).isEqualTo(expected);
        System.out.println(grouped);
    }

    @Test
    void throwsWhenListContainsWordWithDifferentLength() {
        assertThatThrownBy(() -> underTest.groupAnagrams(List.of("aa", "aaa")))
            .isInstanceOf(AnagramFinderException.class);

    }

}