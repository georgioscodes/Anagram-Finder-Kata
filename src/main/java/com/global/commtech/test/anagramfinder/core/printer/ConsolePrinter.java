package com.global.commtech.test.anagramfinder.core.printer;

import java.util.Collection;
import org.springframework.stereotype.Component;

@Component
public class ConsolePrinter implements Printer {

    @Override
    public void printLine(final Collection<String> list) {
        System.out.println(String.join(",", list));
    }
}
