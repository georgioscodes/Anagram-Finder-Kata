package com.global.commtech.test.anagramfinder.exception;

public class AnagramFinderException extends RuntimeException {

    public AnagramFinderException(final String message, final Exception e) {
        super(message, e);
    }

    public AnagramFinderException(final String message) {
        super(message);
    }
}
