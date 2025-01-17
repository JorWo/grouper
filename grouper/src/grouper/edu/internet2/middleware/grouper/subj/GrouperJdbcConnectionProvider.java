/**
 * Copyright 2014 Internet2
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * @author mchyzer
 * $Id: GrouperJdbcConnectionProvider.java,v 1.3 2008-10-15 03:57:06 mchyzer Exp $
 */
package edu.internet2.middleware.grouper.subj;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.hibernate.internal.SessionImpl;

import edu.internet2.middleware.grouper.app.loader.db.GrouperLoaderDb;
import edu.internet2.middleware.grouper.hibernate.GrouperTransactionType;
import edu.internet2.middleware.grouper.hibernate.HibernateSession;
import edu.internet2.middleware.grouper.util.GrouperUtil;
import edu.internet2.middleware.subject.SourceUnavailableException;
import edu.internet2.middleware.subject.SubjectUtils;
import edu.internet2.middleware.subject.provider.JdbcConnectionBean;
import edu.internet2.middleware.subject.provider.JdbcConnectionProvider;


/**
 * provide connections to source api from the grouper hibernate settings
 */
public class GrouperJdbcConnectionProvider implements JdbcConnectionProvider {

  /** if this should be a readonly tx */
  private boolean readOnly;
  
  /**
   * TODO at the beginning of a release (e.g. 4.0?) get rid of this and just use grouper pool
   * bean to hold connection
   */
  public static class GrouperJdbcConnectionBean implements JdbcConnectionBean {
    
    /** reference to connection */
    private HibernateSession hibernateSession;
    
    /**
     * construct
     * @param theHibernateSession
     */
    public GrouperJdbcConnectionBean(HibernateSession theHibernateSession) {
      this.hibernateSession = theHibernateSession;
    }

    /**
     * @see edu.internet2.middleware.subject.provider.JdbcConnectionBean#connection()
     */
    public Connection connection() throws SQLException {
      return ((SessionImpl)this.hibernateSession.getSession()).connection();
    }
    /**
     * @see edu.internet2.middleware.subject.provider.JdbcConnectionBean#doneWithConnection()
     */
    public void doneWithConnection() throws SQLException {
      HibernateSession._internal_hibernateSessionEnd(this.hibernateSession);
    }

    /**
     * @see edu.internet2.middleware.subject.provider.JdbcConnectionBean#doneWithConnectionError(java.lang.Throwable)
     */
    public void doneWithConnectionError(Throwable t) {
      HibernateSession._internal_hibernateSessionCatch(this.hibernateSession, t);
    }

    /**
     * @see edu.internet2.middleware.subject.provider.JdbcConnectionBean#doneWithConnectionFinally()
     */
    public void doneWithConnectionFinally() {
      HibernateSession._internal_hibernateSessionFinally(this.hibernateSession);
    }
    
  }

  /**
   * bean to hold connection
   */
  public static class GrouperLoaderConnectionBean implements JdbcConnectionBean {
    
    /**
     * connection we gave out
     */
    private Connection connection;
    
    /**
     * connection id we are using
     */
    private String connectionId;
    
    /**
     * construct
     * @param theHibernateSession
     */
    public GrouperLoaderConnectionBean(String theConnectionId) {
      this.connectionId = theConnectionId;
    }

    /**
     * @see edu.internet2.middleware.subject.provider.JdbcConnectionBean#connection()
     */
    public Connection connection() throws SQLException {

      // give out the same one?  i dont think this is an issue...
      if (this.connection != null) {
        GrouperUtil.closeQuietly(this.connection);
        throw new RuntimeException("Already got a connection!");
      }
      this.connection = new GrouperLoaderDb(this.connectionId).connection();
      return this.connection;

    }

    /**
     * @see edu.internet2.middleware.subject.provider.JdbcConnectionBean#doneWithConnection()
     */
    public void doneWithConnection() throws SQLException {
      GrouperUtil.closeQuietly(this.connection);
    }

    /**
     * @see edu.internet2.middleware.subject.provider.JdbcConnectionBean#doneWithConnectionError(java.lang.Throwable)
     */
    public void doneWithConnectionError(Throwable t) {
      GrouperUtil.closeQuietly(this.connection);
      if (t instanceof RuntimeException) {
        throw (RuntimeException)t;
      }
      throw new RuntimeException("error", t);
    }

    /**
     * @see edu.internet2.middleware.subject.provider.JdbcConnectionBean#doneWithConnectionFinally()
     */
    public void doneWithConnectionFinally() {
      GrouperUtil.closeQuietly(this.connection);
    }
    
  }

  /** logger */
  private static Log log = GrouperUtil.getLog(GrouperJdbcConnectionProvider.class);

  /**
   * "grouper" or blank for grouper database, otherwise its the grouper-loader.properties database entry
   */
  private String jdbcConfigId = null;
  
  /**
   * @see edu.internet2.middleware.subject.provider.JdbcConnectionProvider#init(Properties, java.lang.String, java.lang.String, java.lang.Integer, int, java.lang.Integer, int, java.lang.Integer, int, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, boolean, String)
   */
  public void init(Properties properties, String sourceId, String driver, Integer maxActive, int defaultMaxActive,
      Integer maxIdle, int defaultMaxIdle, Integer maxWaitSeconds,
      int defaultMaxWaitSeconds, String dbUrl, String dbUser, String dbPassword,
      Boolean readOnly, boolean readOnlyDefault, String theJdbcConfigId) throws SourceUnavailableException {
    
    if (!StringUtils.isBlank(driver)) {
      log.warn("shouldnt set driver for GrouperJdbcConnectionProvider: " + sourceId + ", " + driver);
    }
    
    if (maxActive != null) {
      log.warn("shouldnt set maxActive for GrouperJdbcConnectionProvider: " + sourceId + ", " + maxActive);
    }
    
    if (maxIdle != null) {
      log.warn("shouldnt set maxIdle for GrouperJdbcConnectionProvider: " + sourceId + ", " + maxIdle);
    }
    
    if (maxWaitSeconds != null) {
      log.warn("shouldnt set maxWaitSeconds for GrouperJdbcConnectionProvider: " + sourceId + ", " + maxWaitSeconds);
    }

    if (!StringUtils.isBlank(dbUrl)) {
      log.warn("shouldnt set dbUrl for GrouperJdbcConnectionProvider: " + sourceId + ", " + dbUrl);
    }

    if (!StringUtils.isBlank(dbUser)) {
      log.warn("shouldnt set dbUser for GrouperJdbcConnectionProvider: " + sourceId + ", " + dbUser);
    }

    if (!StringUtils.isBlank(dbPassword)) {
      log.warn("shouldnt set dbPassword for GrouperJdbcConnectionProvider: " + sourceId);
    }

    this.readOnly = SubjectUtils.defaultIfNull(readOnly, readOnlyDefault);
    
    this.jdbcConfigId = theJdbcConfigId;
    //nothing to do...
  }

  /**
   * @see edu.internet2.middleware.subject.provider.JdbcConnectionProvider#connectionBean()
   */
  public JdbcConnectionBean connectionBean() throws SQLException {
    
    if (StringUtils.isBlank(this.jdbcConfigId) || StringUtils.equalsIgnoreCase("grouper", this.jdbcConfigId)) {
      GrouperTransactionType grouperTransactionType = this.readOnly ? GrouperTransactionType.READONLY_OR_USE_EXISTING 
          : GrouperTransactionType.READ_WRITE_OR_USE_EXISTING;
  
      HibernateSession hibernateSession = null;
      hibernateSession = HibernateSession._internal_hibernateSession(grouperTransactionType);
      return new GrouperJdbcConnectionBean(hibernateSession);
    }
    
    return new GrouperLoaderConnectionBean(this.jdbcConfigId);
  }

  /**
   * @see edu.internet2.middleware.subject.provider.JdbcConnectionProvider#requiresJdbcConfigInSourcesXml()
   */
  public boolean requiresJdbcConfigInSourcesXml() {
    return false;
  }

}
