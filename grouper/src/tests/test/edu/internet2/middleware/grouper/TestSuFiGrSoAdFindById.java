/*
  Copyright 2004-2005 University Corporation for Advanced Internet Development, Inc.
  Copyright 2004-2005 The University Of Chicago

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package test.edu.internet2.middleware.grouper;

import  edu.internet2.middleware.grouper.*;
import  edu.internet2.middleware.subject.*;
import  edu.internet2.middleware.subject.provider.*;
import  java.util.*;
import  junit.framework.*;

/**
 * Test {@link SubjectFinder.findById()} with {@link GrouperSourceAdapter}.
 * <p />
 * @author  blair christensen.
 * @version $Id: TestSuFiGrSoAdFindById.java,v 1.5 2005-11-29 17:25:39 blair Exp $
 */
public class TestSuFiGrSoAdFindById extends TestCase {

  protected GrouperSession  s;
  protected Stem            edu, root;
  protected Group           i2;

  public TestSuFiGrSoAdFindById(String name) {
    super(name);
  }

  protected void setUp () {
    Db.refreshDb();
    s     = SessionHelper.getRootSession();
    root  = StemHelper.findRootStem(s);
    edu   = StemHelper.addChildStem(root, "edu", "educational");
    i2    = StemHelper.addChildGroup(edu, "i2", "internet2");
  }

  protected void tearDown () {
    // Nothing 
  }

  // Tests

  public void testFindByIdBadId() {
    SubjectHelper.getSubjectByBadId(Helper.BAD_SUBJ_ID);
    Assert.assertTrue("failed to find bad subject", true);
  } // public void testFindByIdBadId()

  public void testFindByIdGoodIdBadType() {
    SubjectHelper.getSubjectByBadIdType(i2.getUuid(), "person");
    Assert.assertTrue("failed to find bad subject", true);
  } // public void testFindByIdGoodIdBadType()

  public void testFindByIdGoodId() {
    Subject subj = SubjectHelper.getSubjectById(i2.getUuid());
    Assert.assertTrue("found subject", true);
  } // public void testFindByIdGoodId()

  public void testFindByIdGoodIdGoodType() {
    Subject subj = SubjectHelper.getSubjectByIdType(i2.getUuid(), "group");
    Assert.assertTrue("found subject", true);
    Map attrs = subj.getAttributes();
    Assert.assertTrue("7 attributes", attrs.size() == 7);
    // createSubjectId
    String attr = "createSubjectId";
    String val  = "GrouperSystem";
    Assert.assertTrue(
      "attr => " + attr, subj.getAttributeValue(attr).equals(val)
    );
    Assert.assertTrue(
      "attrs => " + attr, attrs.get(attr).equals(val)
    );
    // createSubjectType
    attr = "createSubjectType";
    val  = "application";
    Assert.assertTrue(
      "attr => " + attr, subj.getAttributeValue(attr).equals(val)
    );
    Assert.assertTrue(
      "attrs => " + attr, attrs.get(attr).equals(val)
    );
    // createTime
    attr = "createTime";
    Assert.assertNotNull(
      "attr => " + attr, subj.getAttributeValue(attr)
    );
    Assert.assertNotNull(
      "attrs => " + attr, attrs.get(attr)
    );
    // displayExtension
    attr = "displayExtension";
    val  = "internet2";
    Assert.assertTrue(
      "attr => " + attr, subj.getAttributeValue(attr).equals(val)
    );
    Assert.assertTrue(
      "attrs => " + attr, attrs.get(attr).equals(val)
    );
    // displayName
    attr = "displayName";
    val  = "educational:internet2";
    Assert.assertTrue(
      "attr => " + attr, subj.getAttributeValue(attr).equals(val)
    );
    Assert.assertTrue(
      "attrs => " + attr, attrs.get(attr).equals(val)
    );
    // extension
    attr = "extension";
    val  = "i2";
    Assert.assertTrue(
      "attr => " + attr, subj.getAttributeValue(attr).equals(val)
    );
    Assert.assertTrue(
      "attrs => " + attr, attrs.get(attr).equals(val)
    );
    // name
    attr = "name";
    val  = "edu:i2";
    Assert.assertTrue(
      "attr => " + attr, subj.getAttributeValue(attr).equals(val)
    );
    Assert.assertTrue(
      "attrs => " + attr, attrs.get(attr).equals(val)
    );
  } // public void testFindByIdGoodIdGoodType()

}

