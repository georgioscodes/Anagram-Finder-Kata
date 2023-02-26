package com.global.commtech.test.anagramfinder.core;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FileLoaderTest {

    private FileLoader underTest;

    @Nested
    class SanitisedFile {

        private static final URL INPUT_FILE =
            FileLoaderTest.class.getClassLoader().getResource("file_loader_tests/test1.txt");

        @BeforeEach
        void setUp() {
            underTest = new FileLoader(INPUT_FILE.getPath());
        }

        @Test
        void loadsFileInChunks() {
            final var firstSize = underTest.getNextSameSizedWordsFromFile();
            assertThat(firstSize).containsExactly("aa", "bb", "cc");

            final var secondSize = underTest.getNextSameSizedWordsFromFile();
            assertThat(secondSize).containsExactly("vvv", "asd", "dsa");

            final var thirdSize = underTest.getNextSameSizedWordsFromFile();
            assertThat(thirdSize).containsExactly("cccc", "aaaa", "asas", "aaaa");

            final var fourthSize = underTest.getNextSameSizedWordsFromFile();
            assertThat(fourthSize).containsExactly("qweewq");

            final var fifthSize = underTest.getNextSameSizedWordsFromFile();
            assertThat(fifthSize).containsExactly("qweewqa");

            final var eof = underTest.getNextSameSizedWordsFromFile();
            assertThat(eof).isEmpty();
        }

        @Test
        void iteratorWorksAsExpected() {
            final List<List<String>> results = new ArrayList<>();

            for (var words : underTest) {
                results.add(words);
            }

            assertThat(results).containsExactly(List.of("aa", "bb", "cc"),
                                                List.of("vvv", "asd", "dsa"),
                                                List.of("cccc", "aaaa", "asas", "aaaa"),
                                                List.of("qweewq"),
                                                List.of("qweewqa"));
        }
    }

    @Nested
    class NonSanitisedFile {

        private static final URL INPUT_FILE =
            FileLoaderTest.class.getClassLoader().getResource("file_loader_tests/example_with_empty_lines.txt");

        @BeforeEach
        void setUp() {
            underTest = new FileLoader(INPUT_FILE.getPath());
        }

        @Test
        void shouldIgnoreEmptyLines() {
            final var firstSize = underTest.getNextSameSizedWordsFromFile();
            assertThat(firstSize).containsExactly("aa", "bb", "cc");

            final var secondSize = underTest.getNextSameSizedWordsFromFile();
            assertThat(secondSize).containsExactly("vvv", "asd", "dsa");

            final var thirdSize = underTest.getNextSameSizedWordsFromFile();
            assertThat(thirdSize).containsExactly("cccc", "aaaa", "asas", "aaaa");

            final var fourthSize = underTest.getNextSameSizedWordsFromFile();
            assertThat(fourthSize).containsExactly("qweewq");

            final var fifthSize = underTest.getNextSameSizedWordsFromFile();
            assertThat(fifthSize).containsExactly("qweewqa");

            final var eof = underTest.getNextSameSizedWordsFromFile();
            assertThat(eof).isEmpty();
        }
    }
}