/*
  Copyright (C) 2004-2007 University Corporation for Advanced Internet Development, Inc.
  Copyright (C) 2004-2007 The University Of Chicago

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

package edu.internet2.middleware.grouper;
import  edu.internet2.middleware.grouper.Privilege;
import  edu.internet2.middleware.grouper.Stem;
import  edu.internet2.middleware.grouper.UnableToPerformException;
import  edu.internet2.middleware.grouper.privs.NamingResolver;
import  edu.internet2.middleware.subject.Subject;
import  java.util.Set;


/**
 * Mock {@link NamingResolver}.
 * @author  blair christensen.
 * @version $Id: MockNamingResolver.java,v 1.3 2007-08-27 15:53:53 blair Exp $
 * @since   1.2.1
 */
public class MockNamingResolver implements NamingResolver {


  private static final GrouperRuntimeException E = new GrouperRuntimeException("not implemented");



  /**
   * @return  New <code>MockNamingResolver</code>.
   * @since   1.2.1
   */
  public MockNamingResolver() {
    super();
  }



  /**
   * Not implemented.
   * @throws  GrouperRuntimeException
   * @since   1.2.1
   */
  public String getConfig(String property) 
    throws  IllegalArgumentException
  {
    throw E;
  }

  /**
   * Not implemented.
   * @throws  GrouperRuntimeException
   * @since   1.2.1
   */
  public Set<Stem> getStemsWhereSubjectHasPrivilege(Subject subject, Privilege privilege)
    throws  IllegalArgumentException
  {
    throw E;
  }

  /**
   * Not implemented.
   * @throws  GrouperRuntimeException
   * @since   1.2.1
   */
  public Set<Privilege> getPrivileges(Stem stem, Subject subject)
    throws  IllegalArgumentException
  {
    throw E;
  }

  /**
   * Not implemented.
   * @throws  GrouperRuntimeException
   * @since   1.2.1
   */
  public Set<Subject> getSubjectsWithPrivilege(Stem stem, Privilege privilege)
    throws  IllegalArgumentException
  {
    throw E;
  }

  /**
   * Not implemented.
   * @throws  GrouperRuntimeException
   * @since   1.2.1
   */
  public void grantPrivilege(Stem stem, Subject subject, Privilege privilege)
    throws  IllegalArgumentException,
            UnableToPerformException
  {
    throw E;
  }

  /**
   * Not implemented.
   * @throws  GrouperRuntimeException
   * @since   1.2.1
   */
  public boolean hasPrivilege(Stem stem, Subject subject, Privilege privilege)
    throws  IllegalArgumentException
  {
    throw E;
  }

  /**
   * Not implemented.
   * @throws  GrouperRuntimeException
   * @since   1.2.1
   */
  public void revokePrivilege(Stem stem, Privilege privilege)
    throws  IllegalArgumentException,
            UnableToPerformException
  {
    throw E;
  }
            

  /**
   * Not implemented.
   * @throws  GrouperRuntimeException
   * @since   1.2.1
   */
  public void revokePrivilege(Stem stem, Subject subject, Privilege privilege)
    throws  IllegalArgumentException,
            UnableToPerformException
  {
    throw E;
  }            

}

