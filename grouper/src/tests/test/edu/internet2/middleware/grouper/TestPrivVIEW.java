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
import  org.apache.commons.logging.*;


/**
 * Test use of the VIEW {@link AccessPrivilege}.
 * <p />
 * @author  blair christensen.
 * @version $Id: TestPrivVIEW.java,v 1.3 2005-12-10 16:06:06 blair Exp $
 */
public class TestPrivVIEW extends TestCase {

  // Private Class Constants
  private static final Log LOG = LogFactory.getLog(TestPrivVIEW.class);

  // Private Class Variables
  private static Stem           edu;
  private static Group          i2;
  private static Member         m;
  private static GrouperSession nrs;
  private static Stem           root;
  private static GrouperSession s;
  private static Subject        subj0;
  private static Subject        subj1;
  private static Group          uofc;


  public TestPrivVIEW(String name) {
    super(name);
  }

  protected void setUp () {
    LOG.debug("setUp");
    Db.refreshDb();
    s       = SessionHelper.getRootSession();
    nrs     = SessionHelper.getSession(SubjectHelper.SUBJ0_ID);
    root    = StemHelper.findRootStem(s);
    edu     = StemHelper.addChildStem(root, "edu", "educational");
    i2      = StemHelper.addChildGroup(edu, "i2", "internet2");
    uofc    = StemHelper.addChildGroup(edu, "uofc", "uchicago");
    subj0   = SubjectHelper.SUBJ0;
    subj1   = SubjectHelper.SUBJ1;
    m       = Helper.getMemberBySubject(nrs, subj1);
  }

  protected void tearDown () {
    LOG.debug("tearDown");
    // Nothing 
  }

  // Tests

  public void testFindGroupWithoutADMIN() { 
    LOG.info("testFindGroupWithoutADMIN");
    // ALL has VIEW 
    GroupHelper.findByName(nrs, i2.getName());
    GroupHelper.findByUuid(nrs, i2.getUuid());
  } // public void testFindGroupWithoutADMIN()

  public void testFindGroupWithADMIN() {
    LOG.info("testFindGroupWithADMIN");
    PrivHelper.grantPriv(s, i2, nrs.getSubject(), AccessPrivilege.ADMIN);
    Group a = GroupHelper.findByName(nrs, i2.getName());
    Group b = GroupHelper.findByUuid(nrs, i2.getUuid());
  } // public void testFindGroupWithADMIN()

  public void testFindGroupWithAllADMIN() {
    LOG.info("testFindGroupWithAllADMIN");
    PrivHelper.grantPriv(s, i2, SubjectFinder.findAllSubject(), AccessPrivilege.ADMIN);
    Group a = GroupHelper.findByName(nrs, i2.getName());
    Group b = GroupHelper.findByUuid(nrs, i2.getUuid());
  } // public void testFindGroupWithAllADMIN()

  public void testFindGroupWithoutOPTIN() {
    LOG.info("testFindGroupWithoutOPTIN");
    // ALL has VIEW
    GroupHelper.findByName(nrs, i2.getName());
    GroupHelper.findByUuid(nrs, i2.getUuid());
  } // public void testFindGroupWithoutOPTIN()

  public void testFindGroupWithOPTIN() {
    LOG.info("testFindGroupWithOPTIN");
    PrivHelper.grantPriv(s, i2, nrs.getSubject(), AccessPrivilege.OPTIN);
    Group a = GroupHelper.findByName(nrs, i2.getName());
    Group b = GroupHelper.findByUuid(nrs, i2.getUuid());
  } // public void testFindGroupWithOPTIN()

  public void testFindGroupWithAllOPTIN() {
    LOG.info("testFindGroupWithAllOPTIN");
    PrivHelper.grantPriv(s, i2, SubjectFinder.findAllSubject(), AccessPrivilege.OPTIN);
    Group a = GroupHelper.findByName(nrs, i2.getName());
    Group b = GroupHelper.findByUuid(nrs, i2.getUuid());
  } // public void testFindGroupWithAllOPTIN()

  public void testFindGroupWithoutREAD() {
    LOG.info("testFindGroupWithoutREAD");
    // ALL has VIEW
    GroupHelper.findByName(nrs, i2.getName());
    GroupHelper.findByUuid(nrs, i2.getUuid());
  } // public void testFindGroupWithoutREAD()

  public void testFindGroupWithREAD() {
    LOG.info("testFindGroupWithREAD");
    PrivHelper.grantPriv(s, i2, nrs.getSubject(), AccessPrivilege.READ);
    Group a = GroupHelper.findByName(nrs, i2.getName());
    Group b = GroupHelper.findByUuid(nrs, i2.getUuid());
  } // public void testFindGroupWithREAD()

  public void testFindGroupWithAllREAD() {
    LOG.info("testFindGroupWithAllREAD");
    // Already exists
    // PrivHelper.grantPriv(s, i2, SubjectFinder.findAllSubject(), AccessPrivilege.READ);
    Group a = GroupHelper.findByName(nrs, i2.getName());
    Group b = GroupHelper.findByUuid(nrs, i2.getUuid());
  } // public void testFindGroupWithAllREAD()

  public void testFindGroupWithoutUPDATE() {
    LOG.info("testFindGroupWithoutUPDATE");
    // ALL has VIEW
    GroupHelper.findByName(nrs, i2.getName());
    GroupHelper.findByUuid(nrs, i2.getUuid());
  } // public void testFindGroupWithoutUPDATE()

  public void testFindGroupWithUPDATE() {
    LOG.info("testFindGroupWithUPDATE");
    PrivHelper.grantPriv(s, i2, nrs.getSubject(), AccessPrivilege.UPDATE);
    Group a = GroupHelper.findByName(nrs, i2.getName());
    Group b = GroupHelper.findByUuid(nrs, i2.getUuid());
  } // public void testFindGroupWithUPDATE()

  public void testFindGroupWithAllUPDATE() {
    LOG.info("testFindGroupWithAllUPDATE");
    PrivHelper.grantPriv(s, i2, SubjectFinder.findAllSubject(), AccessPrivilege.UPDATE);
    Group a = GroupHelper.findByName(nrs, i2.getName());
    Group b = GroupHelper.findByUuid(nrs, i2.getUuid());
  } // public void testFindGroupWithAllUPDATE()

  public void testFindGroupWithoutVIEW() {
    LOG.info("testFindGroupWithoutVIEW");
    // ALL has VIEW
    GroupHelper.findByName(nrs, i2.getName());
    GroupHelper.findByUuid(nrs, i2.getUuid());
  } // public void testFindGroupWithoutVIEW()

  public void testFindGroupWithVIEW() {
    LOG.info("testFindGroupWithVIEW");
    PrivHelper.grantPriv(s, i2, nrs.getSubject(), AccessPrivilege.VIEW);
    Group a = GroupHelper.findByName(nrs, i2.getName());
    Group b = GroupHelper.findByUuid(nrs, i2.getUuid());
  } // public void testFindGroupWithVIEW()

  public void testFindGroupWithAllVIEW() {
    LOG.info("testFindGroupWithAllVIEW");
    // Already exists
    // PrivHelper.grantPriv(s, i2, SubjectFinder.findAllSubject(), AccessPrivilege.VIEW);
    Group a = GroupHelper.findByName(nrs, i2.getName());
    Group b = GroupHelper.findByUuid(nrs, i2.getUuid());
  } // public void testFindGroupWithAllVIEW()

  public void testAddGroupAsMemberWithADMIN() {
    LOG.info("testAddGroupAsMemberWithADMIN");
    PrivHelper.grantPriv(s, uofc, subj0, AccessPrivilege.ADMIN);
    PrivHelper.grantPriv(s, i2,   subj0, AccessPrivilege.ADMIN);
    GroupHelper.addMember(uofc, subj1, m);
    Group a = GroupHelper.findByName(nrs, uofc.getName());
    Group b = GroupHelper.findByName(nrs, i2.getName());
    // add uofc (a) to i2 (b)
    GroupHelper.addMember(b, a);
    MembershipHelper.testNumMship(b, Group.getDefaultList(), 2, 1, 1);
    MembershipHelper.testImmMship(nrs, b, a, Group.getDefaultList());
    MembershipHelper.testEffMship(nrs, b, subj1, Group.getDefaultList(), a, 1);
  } // public void testAddGroupAsMemberWithADMIN()

  public void testAddGroupAsMemberWithAllADMIN() {
    LOG.info("testAddGroupAsMemberWithAllADMIN");
    PrivHelper.grantPriv(s, uofc, SubjectFinder.findAllSubject(), AccessPrivilege.ADMIN);
    PrivHelper.grantPriv(s, i2,   subj0, AccessPrivilege.ADMIN);
    GroupHelper.addMember(uofc, subj1, m);
    Group a = GroupHelper.findByName(nrs, uofc.getName());
    Group b = GroupHelper.findByName(nrs, i2.getName());
    // add uofc (a) to i2 (b)
    GroupHelper.addMember(b, a);
    MembershipHelper.testNumMship(b, Group.getDefaultList(), 2, 1, 1);
    MembershipHelper.testImmMship(nrs, b, a, Group.getDefaultList());
    MembershipHelper.testEffMship(nrs, b, subj1, Group.getDefaultList(), a, 1);
  } // public void testAddGroupAsMemberWithAllADMIN()

  public void testAddGroupAsMemberWithOPTIN() {
    LOG.info("testAddGroupAsMemberWithOPTIN");
    PrivHelper.grantPriv(s, uofc, subj0, AccessPrivilege.OPTIN);
    PrivHelper.grantPriv(s, i2,   subj0, AccessPrivilege.ADMIN);
    GroupHelper.addMember(uofc, subj1, m);
    Group a = GroupHelper.findByName(nrs, uofc.getName());
    Group b = GroupHelper.findByName(nrs, i2.getName());
    // add uofc (a) to i2 (b)
    GroupHelper.addMember(b, a);
    MembershipHelper.testNumMship(b, Group.getDefaultList(), 2, 1, 1);
    MembershipHelper.testImmMship(nrs, b, a, Group.getDefaultList());
    MembershipHelper.testEffMship(nrs, b, subj1, Group.getDefaultList(), a, 1);
  } // public void testAddGroupAsMemberWithOPTIN()

  public void testAddGroupAsMemberWithAllOPTIN() {
    LOG.info("testAddGroupAsMemberWithAllOPTIN");
    PrivHelper.grantPriv(s, uofc, SubjectFinder.findAllSubject(), AccessPrivilege.OPTIN);
    PrivHelper.grantPriv(s, i2,   subj0, AccessPrivilege.ADMIN);
    GroupHelper.addMember(uofc, subj1, m);
    Group a = GroupHelper.findByName(nrs, uofc.getName());
    Group b = GroupHelper.findByName(nrs, i2.getName());
    // add uofc (a) to i2 (b)
    GroupHelper.addMember(b, a);
    MembershipHelper.testNumMship(b, Group.getDefaultList(), 2, 1, 1);
    MembershipHelper.testImmMship(nrs, b, a, Group.getDefaultList());
    MembershipHelper.testEffMship(nrs, b, subj1, Group.getDefaultList(), a, 1);
  } // public void testAddGroupAsMemberWithAllOPTIN()

  public void testAddGroupAsMemberWithOPTOUT() {
    LOG.info("testAddGroupAsMemberWithOPTOUT");
    PrivHelper.grantPriv(s, uofc, subj0, AccessPrivilege.OPTOUT);
    PrivHelper.grantPriv(s, i2,   subj0, AccessPrivilege.ADMIN);
    GroupHelper.addMember(uofc, subj1, m);
    Group a = GroupHelper.findByName(nrs, uofc.getName());
    Group b = GroupHelper.findByName(nrs, i2.getName());
    // add uofc (a) to i2 (b)
    GroupHelper.addMember(b, a);
    MembershipHelper.testNumMship(b, Group.getDefaultList(), 2, 1, 1);
    MembershipHelper.testImmMship(nrs, b, a, Group.getDefaultList());
    MembershipHelper.testEffMship(nrs, b, subj1, Group.getDefaultList(), a, 1);
  } // public void testAddGroupAsMemberWithOPTOUT()

  public void testAddGroupAsMemberWithAllOPTOUT() {
    LOG.info("testAddGroupAsMemberWithAllOPTOUT");
    PrivHelper.grantPriv(s, uofc, SubjectFinder.findAllSubject(), AccessPrivilege.OPTOUT);
    PrivHelper.grantPriv(s, i2,   subj0, AccessPrivilege.ADMIN);
    GroupHelper.addMember(uofc, subj1, m);
    Group a = GroupHelper.findByName(nrs, uofc.getName());
    Group b = GroupHelper.findByName(nrs, i2.getName());
    // add uofc (a) to i2 (b)
    GroupHelper.addMember(b, a);
    MembershipHelper.testNumMship(b, Group.getDefaultList(), 2, 1, 1);
    MembershipHelper.testImmMship(nrs, b, a, Group.getDefaultList());
    MembershipHelper.testEffMship(nrs, b, subj1, Group.getDefaultList(), a, 1);
  } // public void testAddGroupAsMemberWithAllOPTOUT()

  public void testAddGroupAsMemberWithREAD() {
    LOG.info("testAddGroupAsMemberWithREAD");
    PrivHelper.grantPriv(s, uofc, subj0, AccessPrivilege.READ);
    PrivHelper.grantPriv(s, i2,   subj0, AccessPrivilege.ADMIN);
    GroupHelper.addMember(uofc, subj1, m);
    Group a = GroupHelper.findByName(nrs, uofc.getName());
    Group b = GroupHelper.findByName(nrs, i2.getName());
    // add uofc (a) to i2 (b)
    GroupHelper.addMember(b, a);
    MembershipHelper.testNumMship(b, Group.getDefaultList(), 2, 1, 1);
    MembershipHelper.testImmMship(nrs, b, a, Group.getDefaultList());
    MembershipHelper.testEffMship(nrs, b, subj1, Group.getDefaultList(), a, 1);
  } // public void testAddGroupAsMemberWithREAD()

  public void testAddGroupAsMemberWithAllREAD() {
    LOG.info("testAddGroupAsMemberWithAllREAD");
    // Already exists
    // PrivHelper.grantPriv(s, uofc, SubjectFinder.findAllSubject(), AccessPrivilege.READ);
    PrivHelper.grantPriv(s, i2,   subj0, AccessPrivilege.ADMIN);
    GroupHelper.addMember(uofc, subj1, m);
    Group a = GroupHelper.findByName(nrs, uofc.getName());
    Group b = GroupHelper.findByName(nrs, i2.getName());
    // add uofc (a) to i2 (b)
    GroupHelper.addMember(b, a);
    MembershipHelper.testNumMship(b, Group.getDefaultList(), 2, 1, 1);
    MembershipHelper.testImmMship(nrs, b, a, Group.getDefaultList());
    MembershipHelper.testEffMship(nrs, b, subj1, Group.getDefaultList(), a, 1);
  } // public void testAddGroupAsMemberWithAllREAD()

  public void testAddGroupAsMemberWithUPDATE() {
    LOG.info("testAddGroupAsMemberWithUPDATE");
    PrivHelper.grantPriv(s, uofc, subj0, AccessPrivilege.UPDATE);
    PrivHelper.grantPriv(s, i2,   subj0, AccessPrivilege.ADMIN);
    GroupHelper.addMember(uofc, subj1, m);
    Group a = GroupHelper.findByName(nrs, uofc.getName());
    Group b = GroupHelper.findByName(nrs, i2.getName());
    // add uofc (a) to i2 (b)
    GroupHelper.addMember(b, a);
    MembershipHelper.testNumMship(b, Group.getDefaultList(), 2, 1, 1);
    MembershipHelper.testImmMship(nrs, b, a, Group.getDefaultList());
    MembershipHelper.testEffMship(nrs, b, subj1, Group.getDefaultList(), a, 1);
  } // public void testAddGroupAsMemberWithUPDATE()

  public void testAddGroupAsMemberWithAllUPDATE() {
    LOG.info("testAddGroupAsMemberWithAllUPDATE");
    PrivHelper.grantPriv(s, uofc, SubjectFinder.findAllSubject(), AccessPrivilege.UPDATE);
    PrivHelper.grantPriv(s, i2,   subj0, AccessPrivilege.ADMIN);
    GroupHelper.addMember(uofc, subj1, m);
    Group a = GroupHelper.findByName(nrs, uofc.getName());
    Group b = GroupHelper.findByName(nrs, i2.getName());
    // add uofc (a) to i2 (b)
    GroupHelper.addMember(b, a);
    MembershipHelper.testNumMship(b, Group.getDefaultList(), 2, 1, 1);
    MembershipHelper.testImmMship(nrs, b, a, Group.getDefaultList());
    MembershipHelper.testEffMship(nrs, b, subj1, Group.getDefaultList(), a, 1);
  } // public void testAddGroupAsMemberWithAllUPDATE()

  public void testAddGroupAsMemberWithVIEW() {
    LOG.info("testAddGroupAsMemberWithVIEW");
    PrivHelper.grantPriv(s, uofc, subj0, AccessPrivilege.VIEW);
    PrivHelper.grantPriv(s, i2,   subj0, AccessPrivilege.ADMIN);
    GroupHelper.addMember(uofc, subj1, m);
    Group a = GroupHelper.findByName(nrs, uofc.getName());
    Group b = GroupHelper.findByName(nrs, i2.getName());
    // add uofc (a) to i2 (b)
    GroupHelper.addMember(b, a);
    MembershipHelper.testNumMship(b, Group.getDefaultList(), 2, 1, 1);
    MembershipHelper.testImmMship(nrs, b, a, Group.getDefaultList());
    MembershipHelper.testEffMship(nrs, b, subj1, Group.getDefaultList(), a, 1);
  } // public void testAddGroupAsMemberWithVIEW()

  public void testAddGroupAsMemberWithAllVIEW() {
    LOG.info("testAddGroupAsMemberWithAllVIEW");
    // Already exists
    // PrivHelper.grantPriv(s, uofc, SubjectFinder.findAllSubject(), AccessPrivilege.VIEW);
    PrivHelper.grantPriv(s, i2,   subj0, AccessPrivilege.ADMIN);
    // Already exists
    GroupHelper.addMember(uofc, subj1, m);
    Group a = GroupHelper.findByName(nrs, uofc.getName());
    Group b = GroupHelper.findByName(nrs, i2.getName());
    // add uofc (a) to i2 (b)
    GroupHelper.addMember(b, a);
    MembershipHelper.testNumMship(b, Group.getDefaultList(), 2, 1, 1);
    MembershipHelper.testImmMship(nrs, b, a, Group.getDefaultList());
    MembershipHelper.testEffMship(nrs, b, subj1, Group.getDefaultList(), a, 1);
  } // public void testAddGroupAsMemberWithAllVIEW()

}

