/**
 * @author mchyzer
 * $Id$
 */
package edu.internet2.middleware.grouperClient.jdbc.tableSync;

import java.sql.Timestamp;

import edu.internet2.middleware.grouperClient.jdbc.GcDbAccess;
import edu.internet2.middleware.grouperClient.jdbc.GcDbVersionable;
import edu.internet2.middleware.grouperClient.jdbc.GcPersist;
import edu.internet2.middleware.grouperClient.jdbc.GcPersistableClass;
import edu.internet2.middleware.grouperClient.jdbc.GcPersistableField;
import edu.internet2.middleware.grouperClient.jdbc.GcPersistableHelper;
import edu.internet2.middleware.grouperClient.jdbc.GcSqlAssignPrimaryKey;
import edu.internet2.middleware.grouperClient.util.GrouperClientUtils;
import edu.internet2.middleware.grouperClientExt.org.apache.commons.lang3.builder.EqualsBuilder;
import edu.internet2.middleware.grouperClientExt.org.apache.commons.lang3.builder.ToStringBuilder;
import edu.internet2.middleware.grouperClientExt.org.apache.commons.logging.Log;


/**
 * if doing user level syncs, this is the metadata
 */
@GcPersistableClass(tableName="grouper_sync_membership", defaultFieldPersist=GcPersist.doPersist)
public class GcGrouperSyncMembership implements GcSqlAssignPrimaryKey, GcDbVersionable {

  //########## START GENERATED BY GcDbVersionableGenerate.java ###########
  /** save the state when retrieving from DB */
  @GcPersistableField(persist = GcPersist.dontPersist)
  private GcGrouperSyncMembership dbVersion = null;

  /**
   * take a snapshot of the data since this is what is in the db
   */
  @Override
  public void dbVersionReset() {
    //lets get the state from the db so we know what has changed
    this.dbVersion = this.clone();
  }

  /**
   * if we need to update this object
   * @return if needs to update this object
   */
  @Override
  public boolean dbVersionDifferent() {
    return !this.equalsDeep(this.dbVersion);
  }

  /**
   * db version
   */
  @Override
  public void dbVersionDelete() {
    this.dbVersion = null;
  }

  /**
   * deep clone the fields in this object
   */
  @Override
  public GcGrouperSyncMembership clone() {

    GcGrouperSyncMembership gcGrouperSyncMembership = new GcGrouperSyncMembership();
    //connectionName  DONT CLONE

    gcGrouperSyncMembership.errorMessage = this.errorMessage;
    gcGrouperSyncMembership.errorTimestamp = this.errorTimestamp;
    //grouperSyncGroup  DONT CLONE

    gcGrouperSyncMembership.grouperSyncGroupId = this.grouperSyncGroupId;
    //grouperSyncMember  DONT CLONE

    gcGrouperSyncMembership.grouperSyncMemberId = this.grouperSyncMemberId;
    gcGrouperSyncMembership.id = this.id;
//    gcGrouperSyncMembership.inGrouperDb = this.inGrouperDb;
//    gcGrouperSyncMembership.inGrouperEnd = this.inGrouperEnd;
//    gcGrouperSyncMembership.inGrouperInsertOrExistsDb = this.inGrouperInsertOrExistsDb;
//    gcGrouperSyncMembership.inGrouperStart = this.inGrouperStart;
    gcGrouperSyncMembership.inTargetDb = this.inTargetDb;
    gcGrouperSyncMembership.inTargetEnd = this.inTargetEnd;
    gcGrouperSyncMembership.inTargetInsertOrExistsDb = this.inTargetInsertOrExistsDb;
    gcGrouperSyncMembership.inTargetStart = this.inTargetStart;
    //lastUpdated  DONT CLONE

    gcGrouperSyncMembership.membershipId = this.membershipId;
    gcGrouperSyncMembership.membershipId2 = this.membershipId2;
    gcGrouperSyncMembership.metadataUpdated = this.metadataUpdated;

    return gcGrouperSyncMembership;
  }

  /**
   *
   */
  public boolean equalsDeep(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof GcGrouperSyncMembership)) {
      return false;
    }
    GcGrouperSyncMembership other = (GcGrouperSyncMembership) obj;

    return new EqualsBuilder()

        //connectionName  DONT EQUALS
        .append(this.errorMessage, other.errorMessage)
        .append(this.errorTimestamp, other.errorTimestamp)

        //grouperSyncGroup  DONT EQUALS

        .append(this.grouperSyncGroupId, other.grouperSyncGroupId)
        //grouperSyncMember  DONT EQUALS

        .append(this.grouperSyncMemberId, other.grouperSyncMemberId)
        .append(this.id, other.id)
//        .append(this.inGrouperDb, other.inGrouperDb)
//        .append(this.inGrouperEnd, other.inGrouperEnd)
//        .append(this.inGrouperInsertOrExistsDb, other.inGrouperInsertOrExistsDb)
//        .append(this.inGrouperStart, other.inGrouperStart)
        .append(this.inTargetDb, other.inTargetDb)
        .append(this.inTargetEnd, other.inTargetEnd)
        .append(this.inTargetInsertOrExistsDb, other.inTargetInsertOrExistsDb)
        .append(this.inTargetStart, other.inTargetStart)
        //lastUpdated  DONT EQUALS

        .append(this.membershipId, other.membershipId)
        .append(this.membershipId2, other.membershipId2)
        .append(this.metadataUpdated, other.metadataUpdated)
        .isEquals();

  }
  //########## END GENERATED BY GcDbVersionableGenerate.java ###########

  
//  /**
//   * T if inserted on the in_grouper_start date, or F if it existed then and not sure when inserted
//   * @return true or false
//   */
//  public boolean isInGrouperInsertOrExists() {
//    return GrouperClientUtils.booleanValue(this.inGrouperInsertOrExistsDb, false);
//  }
//
//  /**
//   * if this group exists in grouper
//   */
//  @GcPersistableField(columnName="in_grouper")
//  private String inGrouperDb;
//
//  /**
//   * when this group was removed from grouper
//   */
//  private Timestamp inGrouperEnd;
//
//  /**
//   * when this group was added to grouper
//   */
//  private Timestamp inGrouperStart;
//
//  /**
//   * T if inserted on the in_grouper_start date, or F if it existed then and not sure when inserted
//   */
//  @GcPersistableField(columnName="in_grouper_insert_or_exists")
//  private String inGrouperInsertOrExistsDb;
//  
//  /**
//   * if this group exists in grouper
//   * @return if is target
//   */
//  public Boolean getInGrouper() {
//    return GrouperClientUtils.booleanObjectValue(this.inGrouperDb);
//  }
//
//  /**
//   * if this group exists in grouper T/F
//   * @return
//   */
//  public String getInGrouperDb() {
//    return inGrouperDb;
//  }
//
//  /**
//   * if this group exists in grouper T/F
//   * @param inGrouperDb
//   */
//  public void setInGrouperDb(String inGrouperDb) {
//    this.inGrouperDb = inGrouperDb;
//  }
//
//  /**
//   * when this group was removed from grouper
//   * @return
//   */
//  public Timestamp getInGrouperEnd() {
//    return inGrouperEnd;
//  }
//
//  /**
//   * when this group was removed from grouper
//   * @param inGrouperEnd
//   */
//  public void setInGrouperEnd(Timestamp inGrouperEnd) {
//    this.inGrouperEnd = inGrouperEnd;
//  }
//
//  /**
//   * when this group was added to grouper
//   * @return
//   */
//  public Timestamp getInGrouperStart() {
//    return inGrouperStart;
//  }
//
//  /**
//   * when this group was added to grouper
//   * @param inGrouperStart
//   */
//  public void setInGrouperStart(Timestamp inGrouperStart) {
//    this.inGrouperStart = inGrouperStart;
//  }
//
//  /**
//   * if the provisioner added to grouper or if it already existed
//   * @return
//   */
//  public String getInGrouperInsertOrExistsDb() {
//    return inGrouperInsertOrExistsDb;
//  }
//
//  /**
//   * if the provisioner added to grouper or if it already existed
//   * @param inGrouperInsertOrExistsDb
//   */
//  public void setInGrouperInsertOrExistsDb(String inGrouperInsertOrExistsDb) {
//    this.inGrouperInsertOrExistsDb = inGrouperInsertOrExistsDb;
//  }
//
//  /**
//   * if in grouper
//   * @return if in grouper
//   */
//  public boolean isInGrouper() {
//    return GrouperClientUtils.booleanValue(this.inGrouperDb, false);
//  }
// 
//  /**
//   * if in grouper
//   * @param in grouper
//   */
//  public void setInGrouper(boolean inGrouper) {
//    this.inGrouperDb = inGrouper ? "T" : "F";
//  }
//
//  /**
//   * T if inserted on the in_grouper_start date, or F if it existed then and not sure when inserted
//   * @param inGrouperInsertOrExists
//   */
//  public void setInGrouperInsertOrExists(boolean inGrouperInsertOrExists) {
//    this.inGrouperInsertOrExistsDb = inGrouperInsertOrExists ? "T" : "F";
//  }


  /**
   * 
   */
  @GcPersistableField(persist=GcPersist.dontPersist)
  private GcGrouperSync grouperSync;
  
  /**
   * delete all data if table is here
   */
  public static void reset() {
    
    try {
      // if its not there forget about it... TODO remove this in 2.5+
      new GcDbAccess().connectionName("grouper").sql("select * from " + GcPersistableHelper.tableName(GcGrouperSyncMembership.class) + " where 1 != 1").select(Integer.class);
    } catch (Exception e) {
      return;
    }

    new GcDbAccess().connectionName("grouper").sql("delete from " + GcPersistableHelper.tableName(GcGrouperSyncMembership.class)).executeSql();
  }


  /**
   * other metadata on membership
   */
  private String membershipId;
  
  /**
   * if the last sync had an error, this is the error message
   */
  private String errorMessage; 

  /**
   * this the last sync had an error, this was the error timestamp
   */
  private Timestamp errorTimestamp;
  
  /**
   * if the last sync had an error, this is the error message
   * @return error message
   */
  public String getErrorMessage() {
    return this.errorMessage;
  }

  /**
   * if the last sync had an error, this is the error message
   * @param errorMessage1
   */
  public void setErrorMessage(String errorMessage1) {
    this.errorMessage = errorMessage1;
  }

  /**
   * this the last sync had an error, this was the error timestamp
   * @return error timestamp
   */
  public Timestamp getErrorTimestamp() {
    return this.errorTimestamp;
  }

  /**
   * this the last sync had an error, this was the error timestamp
   * @param errorTimestamp1
   */
  public void setErrorTimestamp(Timestamp errorTimestamp1) {
    this.errorTimestamp = errorTimestamp1;
  }

  /**
   * other metadata on membership
   * @return metadata
   */
  public String getMembershipId() {
    return this.membershipId;
  }

  /**
   * other metadata on membership
   * @param membershipId1_1
   */
  public void setMembershipId(String membershipId1_1) {
    this.membershipId = membershipId1_1;
  }

  /**
   * when metadata was last updated
   */
  private Timestamp metadataUpdated;
  
  
  
  /**
   * when metadata was last updated
   * @return
   */
  public Timestamp getMetadataUpdated() {
    return this.metadataUpdated;
  }

  /**
   * when metadata was last updated
   * @param metadataUpdated1
   */
  public void setMetadataUpdated(Timestamp metadataUpdated1) {
    this.metadataUpdated = metadataUpdated1;
  }

  /**
   * other metadata on membership
   */
  private String membershipId2;
  
  
  
  /**
   * other metadata on membership
   * @return metadata
   */
  public String getMembershipId2() {
    return this.membershipId2;
  }


  /**
   * other metadata on membership
   * @param membershipId2_1
   */
  public void setMembershipId2(String membershipId2_1) {
    this.membershipId2 = membershipId2_1;
  }

  /**
   * link back to sync group
   */
  @GcPersistableField(persist=GcPersist.dontPersist)
  private GcGrouperSyncGroup grouperSyncGroup = null;
  
  /**
   * link back to sync group
   * @return group
   */
  public GcGrouperSyncGroup getGrouperSyncGroup() {
    return this.grouperSyncGroup;
  }

  /**
   * link back to sync group
   * @param gcGrouperSyncGroup
   */
  public void setGrouperSyncGroup(GcGrouperSyncGroup gcGrouperSyncGroup) {
    this.grouperSyncGroup = gcGrouperSyncGroup;
    this.grouperSyncGroupId = gcGrouperSyncGroup == null ? null : gcGrouperSyncGroup.getId();
  }

  /**
   * link back to sync member
   */
  @GcPersistableField(persist=GcPersist.dontPersist)
  private GcGrouperSyncMember grouperSyncMember = null;

  /**
   * link back to sync member
   * @return member
   */
  public GcGrouperSyncMember getGrouperSyncMember() {
    return this.grouperSyncMember;
  }

  /**
   * link back to sync member
   * @param gcGrouperSyncMember1
   */
  public void setGrouperSyncMember(GcGrouperSyncMember gcGrouperSyncMember1) {
    
    this.grouperSyncMember = gcGrouperSyncMember1;
    this.grouperSyncMemberId = gcGrouperSyncMember1 == null ? null : gcGrouperSyncMember1.getId();
  }

  /**
   * foreign key back to group table
   */
  private String grouperSyncGroupId;
  
  /**
   * foreign key back to group table
   * @return group id
   */
  public String getGrouperSyncGroupId() {
    return this.grouperSyncGroupId;
  }

  /**
   * foreign key back to group table
   * @param grouperSyncGroupId1
   */
  public void setGrouperSyncGroupId(String grouperSyncGroupId1) {
    this.grouperSyncGroupId = grouperSyncGroupId1;
    if (this.grouperSyncGroup == null || !GrouperClientUtils.equals(grouperSyncGroupId1, this.grouperSyncGroup.getId())) {
      this.grouperSyncGroup = null;
    }

  }

  /**
   * foreign key to the members sync table
   */
  private String grouperSyncMemberId;
  
  /**
   * foreign key to the members sync table
   * @return member id
   */
  public String getGrouperSyncMemberId() {
    return this.grouperSyncMemberId;
  }

  /**
   * foreign key to the members sync table
   * @param memberId1
   */
  public void setGrouperSyncMemberId(String memberId1) {
    this.grouperSyncMemberId = memberId1;
    if (this.grouperSyncMember == null || !GrouperClientUtils.equals(memberId1, this.grouperSyncMember.getId())) {
      this.grouperSyncMember = null;
    }
  }

  /**
   * 
   */
  private static Log LOG = GrouperClientUtils.retrieveLog(GcGrouperSyncMembership.class);

  public void storePrepare() {
    this.lastUpdated = new Timestamp(System.currentTimeMillis());
    this.connectionName = GcGrouperSync.defaultConnectionName(this.connectionName);
    this.errorMessage = GrouperClientUtils.abbreviate(this.errorMessage, 3700);
  }

  /**
   * connection name or null for default
   */
  @GcPersistableField(persist=GcPersist.dontPersist)
  private String connectionName;

  /**
   * connection name or null for default
   * @return connection name
   */
  public String getConnectionName() {
    return this.connectionName;
  }

  /**
   * connection name or null for default
   * @param connectionName1
   */
  public void setConnectionName(String connectionName1) {
    this.connectionName = connectionName1;
  }

  /**
   * 
   * @param args
   */
  public static void main(String[] args) {
    
    System.out.println("none");
    
    for (GcGrouperSyncMembership theGcGrouperSyncMembership : new GcDbAccess().connectionName("grouper").selectList(GcGrouperSyncMembership.class)) {
      System.out.println(theGcGrouperSyncMembership.toString());
    }
    
    // foreign key
    GcGrouperSync gcGrouperSync = new GcGrouperSync();
    gcGrouperSync.setSyncEngine("temp");
    gcGrouperSync.setProvisionerName("myJob");
    gcGrouperSync.getGcGrouperSyncDao().store();
    
    GcGrouperSyncGroup gcGrouperSyncGroup = gcGrouperSync.getGcGrouperSyncGroupDao().groupRetrieveOrCreateByGroupId("myId");
    gcGrouperSyncGroup.setLastTimeWorkWasDone(new Timestamp(System.currentTimeMillis() + 2000));
    gcGrouperSync.getGcGrouperSyncGroupDao().internal_groupStore(gcGrouperSyncGroup);

    GcGrouperSyncMember gcGrouperSyncMember = new GcGrouperSyncMember();
    gcGrouperSyncMember.setGrouperSync(gcGrouperSync);
    gcGrouperSyncMember.setLastTimeWorkWasDone(new Timestamp(System.currentTimeMillis() + 2000));
    gcGrouperSyncMember.setMemberId("someId");
    gcGrouperSync.getGcGrouperSyncMemberDao().internal_memberStore(gcGrouperSyncMember);

    GcGrouperSyncMembership gcGrouperSyncMembership = new GcGrouperSyncMembership();
    gcGrouperSyncMembership.setGrouperSyncGroup(gcGrouperSyncGroup);
    gcGrouperSyncMembership.setGrouperSyncMember(gcGrouperSyncMember);
    gcGrouperSyncMembership.inTargetDb = "T";
    gcGrouperSyncMembership.inTargetInsertOrExistsDb = "T";
    gcGrouperSyncMembership.inTargetEnd = new Timestamp(123L);
    gcGrouperSyncMembership.inTargetStart = new Timestamp(234L);
    gcGrouperSyncMembership.membershipId = "memId";
    gcGrouperSyncMembership.membershipId2 = "memId2";
    gcGrouperSync.getGcGrouperSyncMembershipDao().internal_membershipStore(gcGrouperSyncMembership);

    System.out.println("stored");

    gcGrouperSyncMembership = gcGrouperSync.getGcGrouperSyncMembershipDao().membershipRetrieveBySyncGroupIdAndSyncMemberId(
        gcGrouperSyncGroup.getId(), gcGrouperSyncMember.getId());
    System.out.println(gcGrouperSyncMembership);
    
    gcGrouperSyncMembership.setMembershipId("memId1");
    gcGrouperSync.getGcGrouperSyncMembershipDao().internal_membershipStore(gcGrouperSyncMembership);

    System.out.println("updated");

    for (GcGrouperSyncMembership theGcGrouperSyncMembership : new GcDbAccess().connectionName("grouper").selectList(GcGrouperSyncMembership.class)) {
      System.out.println(theGcGrouperSyncMembership.toString());
    }

    gcGrouperSync.getGcGrouperSyncMembershipDao().membershipDelete(gcGrouperSyncMembership, false);
    gcGrouperSync.getGcGrouperSyncGroupDao().groupDelete(gcGrouperSyncGroup, false, false);
    gcGrouperSync.getGcGrouperSyncMemberDao().memberDelete(gcGrouperSyncMember, false, false);
    gcGrouperSync.getGcGrouperSyncDao().delete();
    
    System.out.println("deleted");

    for (GcGrouperSyncGroup theGcGrouperSyncStatus : new GcDbAccess().connectionName("grouper").selectList(GcGrouperSyncGroup.class)) {
      System.out.println(theGcGrouperSyncStatus.toString());
    }
  }
  
  
  /**
   * 
   */
  @Override
  public String toString() {
    return GrouperClientUtils.toStringReflection(this);
  }

  /**
   * 
   */
  public GcGrouperSyncMembership() {
  }
  
  /**
   * uuid of this record in this table
   */
  @GcPersistableField(primaryKey=true, primaryKeyManuallyAssigned=false)
  private String id;

  
  /**
   * uuid of this record in this table
   * @return the id
   */
  public String getId() {
    return this.id;
  }

  
  /**
   * uuid of this record in this table
   * @param id1 the id to set
   */
  public void setId(String id1) {
    this.id = id1;
  }

  /**
   * T if inserted on the in_target_start date, or F if it existed then and not sure when inserted
   */
  @GcPersistableField(columnName="in_target_insert_or_exists")
  private String inTargetInsertOrExistsDb;

  /**
   * T if inserted on the in_target_start date, or F if it existed then and not sure when inserted
   * @return true or false
   */
  public String getInTargetInsertOrExistsDb() {
    return this.inTargetInsertOrExistsDb;
  }

  /**
   * T if inserted on the in_target_start date, or F if it existed then and not sure when inserted
   * @param inTargetInsertOrExistsDb1
   */
  public void setInTargetInsertOrExistsDb(String inTargetInsertOrExistsDb1) {
    this.inTargetInsertOrExistsDb = inTargetInsertOrExistsDb1;
  }

  /**
   * T if inserted on the in_target_start date, or F if it existed then and not sure when inserted
   * @return true or false
   */
  public boolean isInTargetInsertOrExists() {
    return GrouperClientUtils.booleanValue(this.inTargetInsertOrExistsDb, false);
  }
  
  /**
   * T if inserted on the in_target_start date, or F if it existed then and not sure when inserted
   * @param inTargetInsertOrExists
   */
  public void setInTargetInsertOrExists(boolean inTargetInsertOrExists) {
    this.inTargetInsertOrExistsDb = inTargetInsertOrExists ? "T" : "F";
  }
  
  /**
   * if this group exists in the target/destination
   */
  @GcPersistableField(columnName="in_target")
  private String inTargetDb;
  
  /**
   * if this group exists in the target/destination
   * @return if in target
   */
  public String getInTargetDb() {
    return this.inTargetDb;
  }

  /**
   * if this group exists in the target/destination
   * @param inTargetDb1
   */
  public void setInTargetDb(String inTargetDb1) {
    this.inTargetDb = inTargetDb1;
  }

  /**
   * if this group exists in the target/destination
   * @return if is target
   */
  public Boolean getInTarget() {
    return GrouperClientUtils.booleanObjectValue(this.inTargetDb);
  }
  
  /**
   * if this group exists in the target/destination
   * @param inTarget
   */
  public void setInTarget(Boolean inTarget) {
    this.inTargetDb = inTarget ? "T" : "F";
  }
  
  /**
   * when this record was last updated
   */
  private Timestamp lastUpdated;
  
  /**
   * when this record was last updated
   * @return the lastUpdated
   */
  public Timestamp getLastUpdated() {
    return this.lastUpdated;
  }

  /**
   * when this record was last updated
   * @param lastUpdated1
   */
  public void setLastUpdated(Timestamp lastUpdated1) {
    this.lastUpdated = lastUpdated1;
  }

  /**
   * when this group was removed from target
   */
  private Timestamp inTargetEnd;
  /**
   * when this group was provisioned to target
   */
  private Timestamp inTargetStart;

  /**
   * uuid of the job in grouper_sync
   */
  private String grouperSyncId;

  /**
   * 
   */
  @Override
  public boolean gcSqlAssignNewPrimaryKeyForInsert() {
    if (this.id != null) {
      return false;
    }
    this.id = GrouperClientUtils.uuid();
    return true;
  }

  /**
   * when this group was provisioned to target
   * @return when
   */
  public Timestamp getInTargetEnd() {
    return this.inTargetEnd;
  }

  /**
   * when this group was provisioned to target
   * @return when
   */
  public Timestamp getInTargetStart() {
    return this.inTargetStart;
  }

  /**
   * when this group was provisioned to target
   * @param inTargetEnd1
   */
  public void setInTargetEnd(Timestamp inTargetEnd1) {
    this.inTargetEnd = inTargetEnd1;
  }

  /**
   * when this group was provisioned to target
   * @param inTargetStart1
   */
  public void setInTargetStart(Timestamp inTargetStart1) {
    this.inTargetStart = inTargetStart1;
  }

  /**
   * if in target
   * @return if in target
   */
  public boolean isInTarget() {
    return GrouperClientUtils.booleanValue(this.inTargetDb, false);
  }

  /**
   * if in target
   * @param in target
   */
  public void setInTarget(boolean inTarget) {
    this.inTargetDb = inTarget ? "T" : "F";
  }

  /**
   * 
   * @return gc grouper sync
   */
  public GcGrouperSync getGrouperSync() {
    return this.grouperSync;
  }

  /**
   * uuid of the job in grouper_sync
   * @return uuid of the job in grouper_sync
   */ 
  public String getGrouperSyncId() {
    return this.grouperSyncId;
  }

  /**
   * 
   * @param gcGrouperSync
   */
  public void setGrouperSync(GcGrouperSync gcGrouperSync) {
    this.grouperSync = gcGrouperSync;
    this.grouperSyncId = gcGrouperSync == null ? null : gcGrouperSync.getId();
    this.connectionName = gcGrouperSync == null ? this.connectionName : gcGrouperSync.getConnectionName();
  
  }

  /**
   * uuid of the job in grouper_sync
   * @param grouperSyncId1
   */
  public void setGrouperSyncId(String grouperSyncId1) {
    this.grouperSyncId = grouperSyncId1;
    if (this.grouperSync == null || !GrouperClientUtils.equals(this.grouperSync.getId(), grouperSyncId1)) {
      this.grouperSync = null;
    }
  }

}
