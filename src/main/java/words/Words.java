package words;

import java.util.*;

public class Words {
  private final Collection<String> words;

  public Words(Collection<String> words) { this.words = words; }

  public Collection<Pair> findPairsWithNoLettersInCommon() {
    List<Pair> result = new ArrayList<>();
    allPairs().forEach(result::add);
    return result;
  }

  private Iterable<Pair> allPairs() {
    return () -> new Iterator<Pair>() {
      private Iterator<String> firstIterator = words.iterator();
      private Iterator<String> secondIterator = words.iterator();
      private int firstIndex = -1, secondIndex = -1;
      private String first, second;
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
  
  public static class Pair {
    private final String first, second;

    public Pair(String first, String second) {
      this.first = first;
      this.second = second;
    }

    @Override
    public String toString() { 
      return String.format("(%s, %s)", first, second);
    }

    @Override
    public int hashCode() {
      return first.hashCode() * 31 + second.hashCode();
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
