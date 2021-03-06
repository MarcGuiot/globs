package org.globsframework.model.utils;

import junit.framework.TestCase;
import org.globsframework.metamodel.DummyObject;
import org.globsframework.metamodel.DummyObject2;
import static org.globsframework.model.FieldValue.value;

import org.globsframework.model.*;
import org.globsframework.model.repository.ReplicationGlobRepository;

public class ReplicationGlobRepositoryTest extends TestCase {
  private GlobChecker checker = new GlobChecker();
  private GlobRepository source;
  private ReplicationGlobRepository repository;

  public void setUp() throws Exception {
    source = checker.parse(
      "<dummyObject id='0' name='name'/>" +
      "<dummyObject id='1' name='name' value='1.1'/>" +
      "<dummyObject id='2' name='name' value='2.2'/>");

    repository = new ReplicationGlobRepository(source, DummyObject2.TYPE);
  }

  public void testStandardCase() throws Exception {
    checker.assertEquals(repository,
                         "<dummyObject id='0' name='name'/>" +
                         "<dummyObject id='1' name='name' value='1.1'/>" +
                         "<dummyObject id='2' name='name' value='2.2'/>");

    assertNull(repository.find(null));

    repository.create(DummyObject.TYPE,
                      value(DummyObject.ID, 3),
                      value(DummyObject.NAME, "obj3"));

    repository.update(Key.create(DummyObject.TYPE, 1), DummyObject.NAME, "newName");

    repository.delete(Key.create(DummyObject.TYPE, 2));

    repository.create(DummyObject2.TYPE,
                      value(DummyObject2.ID, 1),
                      value(DummyObject2.LABEL, "label 1"));

    repository.create(DummyObject2.TYPE,
                      value(DummyObject2.ID, 2),
                      value(DummyObject2.LABEL, "label 2"));


    checker.assertEquals(source,
                         "<dummyObject id='0' name='name'/>" +
                         "<dummyObject id='1' name='newName' value='1.1'/>" +
                         "<dummyObject id='3' name='obj3'/>");

    new GlobChecker()
      .assertEquals(repository,
                    "<dummyObject id='0' name='name'/>" +
                    "<dummyObject id='1' name='newName' value='1.1'/>" +
                    "<dummyObject id='3' name='obj3'/>" +
                    "<dummyObject2 id='1' label='label 1'/>" +
                    "<dummyObject2 id='2' label='label 2'/>");

    repository.update(Key.create(DummyObject2.TYPE, 1), DummyObject2.LABEL, "new label 1");
    repository.delete(Key.create(DummyObject2.TYPE, 2));
    new GlobChecker()
      .assertEquals(repository,
                    "<dummyObject id='0' name='name'/>" +
                    "<dummyObject id='1' name='newName' value='1.1'/>" +
                    "<dummyObject id='3' name='obj3'/>" +
                    "<dummyObject2 id='1' label='new label 1'/>");

  }

  public void testFindWithNullKeyReturnsNull() throws Exception {
    assertNull(repository.find(null));
    assertFalse(repository.contains((Key)null));
  }

}
