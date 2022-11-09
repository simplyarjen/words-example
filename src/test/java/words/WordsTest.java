package words;

import java.util.*;
import org.junit.*;
import static org.assertj.core.api.Assertions.*;

public class WordsTest {
  private final List<String> testWords = new ArrayList<>();
  private Collection<Words.Pair> theResult;

  @Test
  public void pairsAreUnordered() {
    assertThat(thePair("A", "B")).isEqualTo(thePair("A", "B"));
    assertThat(thePair("A", "B")).isNotEqualTo(thePair("A", "C"));
    assertThat(thePair("A", "B")).isEqualTo(thePair("B", "A"));

  }

  @Ignore("This one should work when the algorithm is implemented.")
  @Test
  public void itShouldFindPairsWithNoLettersInCommon() {
    givenWords("A", "B", "C");
    whenTheAlgorithmIsRun();
    assertThat(theResult).hasSize(3);
    assertThat(theResult).contains(thePair("A", "B"));
    assertThat(theResult).contains(thePair("B", "C"));
    assertThat(theResult).contains(thePair("A", "C"));
  }

  private void givenWords(String... words) {
    testWords.addAll(Arrays.asList(words));
  }

  private void whenTheAlgorithmIsRun() {
    theResult = new Words(testWords).findPairsWithNoLettersInCommon();
  }

  private Words.Pair thePair(String a, String b) {
    return new Words.Pair(a, b);
  }
}
