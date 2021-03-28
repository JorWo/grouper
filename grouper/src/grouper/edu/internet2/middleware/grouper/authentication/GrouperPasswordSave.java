package edu.internet2.middleware.grouper.authentication;

import edu.internet2.middleware.grouper.authentication.GrouperPassword.Application;
import edu.internet2.middleware.grouper.authentication.GrouperPassword.EncryptionType;
import edu.internet2.middleware.grouper.j2ee.Authentication;

/**
 * <p>Use this class to add username and password in grouper registry</p>
 * <p>Sample call to create a username password for grouper ui
 * 
 * <blockquote>
 * <pre>
 * new GrouperPasswordSave().assignUsername("GrouperSystem").assignPassword("admin123").assignEntityType("username")
 *  .assignApplication(GrouperPassword.Application.UI).save();
 * </pre>
 * </blockquote>
 * 
 * </p>
 * 
 * <p> Sample call to create a username password for grouper webservices
 * <blockquote>
 * <pre>
 * new GrouperPasswordSave().assignUsername("GrouperSystem").assignPassword("admin123").assignEntityType("username")
 *  .assignApplication(GrouperPassword.Application.WS).save();
 * </pre>
 * </blockquote>
 * </p>
 *
 */
public class GrouperPasswordSave {
  
  /**
   * username to be assigned
   */
  private String username;
  
  /**
   * entity type to be assigned
   */
  private String entityType;
  
  /**
   * 
   */
  private EncryptionType encryptionType;
  
  private String thePassword;
  
  private Application application;
  
  public void save() {
    new Authentication().assignUserPassword(this);
  }
  
  public GrouperPasswordSave assignUsername(String username) {
    this.username = username;
    return this;
  }
  
  
  public GrouperPasswordSave assignEntityType(String entityType) {
    this.entityType = entityType;
    return this;
  }
  
  public GrouperPasswordSave assignEncryptionType(EncryptionType encryptionType) {
    this.encryptionType = encryptionType;
    return this;
  }
  
  
  public GrouperPasswordSave assignPassword(String password) {
    this.thePassword = password;
    return this;
  }
  
  public GrouperPasswordSave assignApplication(GrouperPassword.Application application) {
    this.application = application;
    return this;
  }


  
  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }


  
  /**
   * @return the entityType
   */
  public String getEntityType() {
    return entityType;
  }

  
  /**
   * @return the encryptionType
   */
  public EncryptionType getEncryptionType() {
    return encryptionType;
  }


  
  /**
   * @return the thePassword
   */
  public String getThePassword() {
    return thePassword;
  }


  
  /**
   * @return the application
   */
  public Application getApplication() {
    return application;
  }
  
  
}
