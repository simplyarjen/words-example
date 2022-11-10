package words;

import java.util.*;
import java.util.stream.*;

public class Words {
  private final Collection<String> words;

  public Words(Collection<String> words) { this.words = words; }

  public Collection<Pair> findPairsWithNoLettersInCommon() {
    List<Word> wrappedWords = words.stream().map(Word::of).collect(Collectors.toList());
    return allPairs(wrappedWords).stream()
        .filter(Pair::haveNoLettersInCommon)
        .collect(Collectors.toSet());
  }

  private static Collection<Pair> allPairs(Collection<Word> words) {
    return new AbstractCollection<Pair>() {
      @Override
      public int size() {
        int wordsSize = words.size();
        return (wordsSize * (wordsSize - 1)) / 2;
      }

      @Override
      public Iterator<Pair> iterator() {
        return new Iterator<Pair>() {
          private Iterator<Word> firstIterator = words.iterator();
          private Iterator<Word> secondIterator = words.iterator();
          private int firstIndex = -1, secondIndex = -1;
          private Word first, second;
          private Pair next;

          @Override
          public boolean hasNext() {
            if (next == null) next = findNext();
            return (next != null);
          }

          @Override
          public Pair next() {
            if (next == null) next = findNext();
            if (next == null) throw new NoSuchElementException();
            Pair result = next;
            next = null;
            return result;
          }

          private Pair findNext() {
            if (!advanceFirst()) return null;
            while (firstIndex >= secondIndex) {
              if (!advanceSecond()) return null;
              if (!advanceFirst()) return null;
            }
            return new Pair(first, second);
          }

          private boolean advanceFirst() {
            if (! firstIterator.hasNext()) return false;
            first = firstIterator.next();
            firstIndex += 1;
            return true;
          }

          private boolean advanceSecond() {
            if (! secondIterator.hasNext()) return false;
            second = secondIterator.next();
            secondIndex += 1;
            firstIterator = words.iterator();
            firstIndex = -1;
            return true;
          }
        };
      }
    };
  }

  public static class Word {
    private final String word;
    private final char[] orderedChars;

    private Word(String word, char[] orderedChars) {
      this.word = word;
      this.orderedChars = orderedChars;
    }

    static Word of(String word) {
      TreeSet<Character> sortedCharacters = word.chars()
          .mapToObj(c -> new Character((char) c))
          .collect(Collectors.toCollection( TreeSet<Character>::new ));
      char[] orderedChars = new char[sortedCharacters.size()];
      int i = 0;
      for (Character c : sortedCharacters) {
        orderedChars[i++] = c;
      }
      return new Word(word, orderedChars);
    }

    @Override
    public String toString() {
      return word;
    }

    @Override
    public int hashCode() {
      return word.hashCode();
    }

    @Override
    public boolean equals(Object other) {
      if (! (other instanceof Word)) return false;
      return word.equals( ((Word) other).word );
    }

    boolean hasLettersInCommonWith(Word other) {
      for (int i = 0, j = 0; i < orderedChars.length && j < other.orderedChars.length; ) {
        if (orderedChars[i] == other.orderedChars[j]) return true;
        if (orderedChars[i] < other.orderedChars[j]) {
          i += 1;
        }
        else {
          j += 1;
        }
      }
      return false;
    }
  }
  
  public static class Pair {
    private final Word first, second;

    public Pair(Word first, Word second) {
      this.first = first;
      this.second = second;
    }

    boolean haveNoLettersInCommon() {
      return ! first.hasLettersInCommonWith(second);
    }

    @Override
    public String toString() { 
      return String.format("(%s, %s)", first, second);
    }

    @Override
    public int hashCode() {
      return first.hashCode() + second.hashCode();
    }

    @Override
    public boolean equals(Object other) {
      if (!(other instanceof Pair)) return false;
      Pair that = (Pair) other;
      return 
        (first.equals(that.first) && second.equals(that.second)) ||
        (first.equals(that.second) && second.equals(that.first));
    }
  }
}
