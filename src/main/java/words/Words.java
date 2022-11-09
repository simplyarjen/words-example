package words;

import java.util.*;

public class Words {
  private final List<String> words;

  public Words(List<String> words) { this.words = words; }

  public Collection<Pair> findPairsWithNoLettersInCommon() {
    return Collections.emptyList();
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
