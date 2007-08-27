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
import  edu.internet2.middleware.grouper.privs.AccessResolverFactory;


/**
 * Test {@link AccessResolverFactory}.
 * @author  blair christensen.
 * @version $Id: Test_privs_AccessResolverFactory.java,v 1.2 2007-08-27 15:53:53 blair Exp $
 * @since   1.2.1
 */
public class Test_privs_AccessResolverFactory extends GrouperTest {


  private GrouperSession s;



  public void setUp() {
    super.setUp();
    try {
      this.s = GrouperSession.start( SubjectFinder.findAllSubject() );
    }
    catch (Exception e) {
      throw new GrouperRuntimeException( "test setUp() error: " + e.getMessage(), e );  
    }
  }

  public void tearDown() {
    super.tearDown();
  }


  public void test_getInstance_twoInstancesNotEqual() {
    assertFalse( 
      "two instance resolvers not equal", 
      AccessResolverFactory.getInstance( this.s ).equals( AccessResolverFactory.getInstance( this.s ) )
    );
  }

  public void test_getResolver_twoResolversEqual() {
    assertEquals( 
      "two singleton resolvers equal", 
      AccessResolverFactory.getResolver(this.s),
      AccessResolverFactory.getResolver(this.s)
    );
  }

}

