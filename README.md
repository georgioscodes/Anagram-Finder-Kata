# Anagram Finder

A simple command line utility for finding anagrams in a specified file

#### The Task

Write a program that takes as an argument the path to a file containing one word per line, groups the words that are
anagrams to each other, and writes to the standard output of each of these groups.
The groups should be separated by newlines and the words inside each group by commas.

#### The Data

You can make the following assumptions about the data in the files:

* The words in the input file are ordered by size
* The files may not fit into memory all at once (but all the words of the same size would)
* The words are not necessarily actual English words. For example, "ABC" and "CBA" are both considered words for the
  sake of this exercise.
* The files in the `Data` folder are just sample input data to help you reason about the problem. Production files will
  be much more significant. If you make other assumptions, write them down in a readme in your submission.

## Software required to run this

* Java 17

## Building and Running the tests

```
./gradlew clean build
```

## Running the program

```
./gradlew bootRun --args="example2.txt" 
```

where example2.txt is the text file that we want to search for anagrams

