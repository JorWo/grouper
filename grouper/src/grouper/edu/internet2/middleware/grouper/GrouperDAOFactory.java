/*
  Copyright (C) 2007 University Corporation for Advanced Internet Development, Inc.
  Copyright (C) 2007 The University Of Chicago

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
import  edu.internet2.middleware.grouper.cfg.ApiConfig;
import  edu.internet2.middleware.grouper.internal.dao.CompositeDAO;
import  edu.internet2.middleware.grouper.internal.dao.FieldDAO;
import  edu.internet2.middleware.grouper.internal.dao.GroupDAO;
import  edu.internet2.middleware.grouper.internal.dao.GrouperSessionDAO;
import  edu.internet2.middleware.grouper.internal.dao.GroupTypeDAO;
import  edu.internet2.middleware.grouper.internal.dao.MemberDAO;
import  edu.internet2.middleware.grouper.internal.dao.MembershipDAO;
import  edu.internet2.middleware.grouper.internal.dao.RegistryDAO;
import  edu.internet2.middleware.grouper.internal.dao.RegistrySubjectDAO;
import  edu.internet2.middleware.grouper.internal.dao.StemDAO;
import  edu.internet2.middleware.grouper.internal.util.Realize;

/** 
 * Factory for returning <code>GrouperDAO</code> objects.
 * <p/>
 * @author  blair christensen.
 * @version $Id: GrouperDAOFactory.java,v 1.9 2007-08-27 15:53:52 blair Exp $
 * @since   1.2.0
 */
public abstract class GrouperDAOFactory {


  private static GrouperDAOFactory gdf;


  /**
   * Return singleton {@link GrouperDAOFactory} implementation.
   * <p/>
   * @since   1.2.0
   */
  public static GrouperDAOFactory getFactory() {
    if (gdf == null) {
      gdf = getFactory( new ApiConfig() );
    }
    return gdf;
  } 

  /**
   * Return singleton {@link GrouperDAOFactory} implementation using the specified
   * configuration.
   * <p/>
   * @throws  IllegalArgumentException if <i>cfg</i> is null.
   * @since   1.2.1
   */
  public static GrouperDAOFactory getFactory(ApiConfig cfg) 
    throws  IllegalArgumentException
  {
    if (cfg == null) {
      throw new IllegalArgumentException("null configuration");
    }
    String            klass = cfg.getProperty(GrouperConfig.PROP_DAO_FACTORY);
    GrouperValidator  v     = NotNullOrEmptyValidator.validate(klass);
    if ( v.isInvalid() ) {
      klass = GrouperConfig.DEFAULT_DAO_FACTORY;
    }
    return (GrouperDAOFactory) Realize.instantiate(klass);
  }


  // PUBLIC ABSTRACT INSTANCE METHODS //

  /**
   * @since   1.2.0
   */
  public abstract CompositeDAO getComposite();

  /**
   * @since   1.2.0
   */
  public abstract FieldDAO getField();

  /**
   * @since   1.2.0
   */
  public abstract GroupDAO getGroup();

  /**
   * @since   1.2.0
   */
  public abstract GrouperSessionDAO getGrouperSession();

  /**
   * @since   1.2.0
   */
  public abstract GroupTypeDAO getGroupType();

  /**
   * @since   1.2.0
   */
  public abstract MemberDAO getMember();

  /**
   * @since   1.2.0
   */
  public abstract MembershipDAO getMembership();

  /**
   * @since   1.2.0
   */
  public abstract RegistryDAO getRegistry();

  /**
   * @since   1.2.0
   */
  public abstract RegistrySubjectDAO getRegistrySubject();

  /**
   * @since   1.2.0
   */
  public abstract StemDAO getStem();


  // PROTECTED CLASS METHODS //

  // @since   1.2.0
  protected static void internal_resetFactory() {
    gdf = null;
  }

} 

