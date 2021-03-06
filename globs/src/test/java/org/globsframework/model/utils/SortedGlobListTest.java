package org.globsframework.model.utils;

import junit.framework.TestCase;
import org.globsframework.metamodel.DummyObject;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobChecker;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.Key;
import org.globsframework.utils.TestUtils;

import java.util.Arrays;
import java.util.List;

public class SortedGlobListTest extends TestCase {

  private SortedGlobList list;

  protected Glob a;
  protected Glob b;
  protected Glob c;
  protected Glob d;
  protected Glob unknown;
  protected GlobRepository repository;

  public void setUp() throws Exception {
    GlobChecker checker = new GlobChecker();
    repository = checker.parse(
      "<dummyObject id='0' name='d'/>" +
      "<dummyObject id='1' name='c'/>" +
      "<dummyObject id='2' name='b'/>" +
      "<dummyObject id='3' name='a'/>" +
      "<dummyObject id='4' name='unknown'/>"
    );

    a = repository.get(Key.create(DummyObject.TYPE, 3));
    b = repository.get(Key.create(DummyObject.TYPE, 2));
    c = repository.get(Key.create(DummyObject.TYPE, 1));
    d = repository.get(Key.create(DummyObject.TYPE, 0));
    unknown = repository.get(Key.create(DummyObject.TYPE, 4));

    list = new SortedGlobList(new GlobFieldComparator(DummyObject.NAME));
  }

  public void test() throws Exception {

    assertEquals(0, list.size());
    assertTrue(list.isEmpty());

    assertEquals(0, list.add(b));
    assertEquals(0, list.add(a));
    assertEquals(2, list.add(d));
    assertEquals(2, list.add(c));
    TestUtils.assertIteratorContains(list.iterator(), a, b, c, d);
    assertEquals(4, list.size());
    assertFalse(list.isEmpty());

    assertEquals(0, list.indexOf(a));
    assertEquals(2, list.indexOf(c));

    assertEquals(b, list.get(1));

    assertEquals(1, list.remove(b));
    assertEquals(2, list.remove(d));
    assertTrue(list.remove(unknown) < 0);
    list.remove(1);
    TestUtils.assertIteratorContains(list.iterator(), a);
    assertEquals(1, list.size());

    list.addAll(Arrays.asList(b, d, c));
    TestUtils.assertIteratorContains(list.iterator(), a, b, c, d);

    list.removeAll(Arrays.asList(b, d));
    TestUtils.assertIteratorContains(list.iterator(), a, c);
  }

  public void testIndexOf() throws Exception {
    list.addAll(Arrays.asList(a, b, c));
    assertEquals(1, list.firstIndexOf(createMatcher(b), repository));
    assertEquals(-1, list.firstIndexOf(createMatcher(unknown), repository));
  }

  public void testClear() throws Exception {
    list.addAll(Arrays.asList(b, d, c));
    list.clear();
    assertEquals(0, list.size());
    assertTrue(list.isEmpty());
  }

  public void testAsList() throws Exception {
    list.add(a);
    list.add(c);
    List newList = list.asList();
    TestUtils.assertIteratorContains(newList.iterator(), a, c);
    newList.add(b);
    TestUtils.assertIteratorContains(newList.iterator(), a, c, b);
    TestUtils.assertIteratorContains(list.iterator(), a, c);
  }

  private GlobMatcher createMatcher(final Glob expected) {
    return new GlobMatcher() {
      public boolean matches(Glob item, GlobRepository repository) {
        return item.equals(expected);
      }

    };
  }
}
