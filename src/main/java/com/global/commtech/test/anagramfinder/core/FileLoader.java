package com.global.commtech.test.anagramfinder.core;

import com.global.commtech.test.anagramfinder.exception.AnagramFinderException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  This class loads words from file in batches to avoid
 *  out of memory error.
 *  <br><br>
 *  It is thread safe, which has a performance impact due to synchronisation
 *  and blocking.
 *  <br><br>
 *  It is a performance bottleneck of the anagram finder app algorithm but
 *  it is due to the given requirements of the problem
 *  <em>The files may not fit into memory all at once (but all the words of the same size would)</em>
 */
public class FileLoader implements Iterable<List<String>> {

    private final long wordsCount;
    private final Path file;
    private final AtomicLong wordsRead;
    private final AtomicInteger currentWordSize;

    public FileLoader(final String file) {
        this.file = Path.of(file);
        this.currentWordSize = new AtomicInteger(findWordsSizeInPosition(0));
        this.wordsRead = new AtomicLong(0);
        this.wordsCount = countWords();
    }

    public synchronized List<String> getNextSameSizedWordsFromFile() {
        try (final Stream<String> lines = streamOfLines()) {
            final List<String> words = lines.skip(wordsRead.get())
                .filter(word -> word.length() == currentWordSize.get())
                .collect(Collectors.toList());
            wordsRead.addAndGet(words.size());
            currentWordSize.set(findWordsSizeInPosition(wordsRead.get()));
            return words;
        } catch (IOException e) {
            throw new AnagramFinderException("Could get the next batch of words with size" + currentWordSize, e);
        }
    }

    @Override
    public Iterator<List<String>> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return wordsRead.get() < wordsCount;
            }

            @Override
            public List<String> next() {
                return getNextSameSizedWordsFromFile();
            }
        };
    }

    private int findWordsSizeInPosition(final long position) {
        try (final Stream<String> lines = streamOfLines()) {
            return lines.skip(position).findFirst().orElse("").length();
        } catch (IOException e) {
            throw new AnagramFinderException("Could not finds word size in position " + position, e);
        }
    }

    private long countWords() {
        try (final Stream<String> lines = streamOfLines()) {
            return lines.count();
        } catch (IOException e) {
            throw new AnagramFinderException("Could not count the words in the file", e);
        }
    }

    private Stream<String> streamOfLines() throws IOException {
        return Files.lines(file).filter(line -> !"".equals(line));
    }
}
