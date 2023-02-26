package com.global.commtech.test.anagramfinder;

import com.global.commtech.test.anagramfinder.core.AnagramFinder;
import com.global.commtech.test.anagramfinder.core.FileLoader;
import com.global.commtech.test.anagramfinder.core.printer.Printer;
import com.global.commtech.test.anagramfinder.core.Word;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class AnagramCommandLineRunner implements CommandLineRunner {

   private final Printer printer;
   private final AnagramFinder anagramFinder;

    @Override
    public void run(final String... args) {
        Assert.isTrue(args.length == 1, "Please ensure that the input file is provided");

        final File file = new File(args[0]);
        Assert.isTrue(file.exists(), args[0] + " Does not exist");

        final FileLoader fileLoader = new FileLoader(file.getAbsolutePath());

        for(var sameSizedWords: fileLoader) {
            printAnagramsOf(sameSizedWords);
        }
    }

    private void printAnagramsOf(final List<String> sameSizedWords) {
        final Map<Word, LinkedHashSet<Word>> wordLinkedHashSetMap = anagramFinder.groupAnagrams(sameSizedWords);
        wordLinkedHashSetMap.forEach((key, group) -> {
            final List<String> printableGroup = group.stream().map(Word::value).collect(Collectors.toList());
            printer.printLine(printableGroup);
        });
    }
}
