/**
 * @author mchyzer
 * $Id: HooksMembershipChangeBean.java,v 1.1 2008-07-08 06:51:34 mchyzer Exp $
 */
package edu.internet2.middleware.grouper.hooks.beans;

import java.util.HashSet;
import java.util.Set;

import edu.internet2.middleware.grouper.Composite;
import edu.internet2.middleware.grouper.DefaultMemberOf;
import edu.internet2.middleware.grouper.Field;
import edu.internet2.middleware.grouper.Group;
import edu.internet2.middleware.grouper.GrouperAPI;
import edu.internet2.middleware.grouper.Member;
import edu.internet2.middleware.grouper.Membership;
import edu.internet2.middleware.grouper.Stem;
import edu.internet2.middleware.grouper.util.GrouperUtil;


/**
 * pre/post update bean for high level membership change (the main change, not
 * the side effects like adding the member to the groups where the group
 * to be added to is a member)
 */
public class HooksMembershipChangeBean extends HooksBean {

  /** object being inserted */
  private DefaultMemberOf defaultMemberOf = null;
  /**
   * @param theDefaultMemberOf 
   */
  public HooksMembershipChangeBean(
      DefaultMemberOf theDefaultMemberOf) {
    this.defaultMemberOf = theDefaultMemberOf;
  }
  
  /**
   * @return the defaultMemberOf
   */
  public DefaultMemberOf getDefaultMemberOf() {
    return this.defaultMemberOf;
  }

  /**
   * composite if applicable
   * @return the composite
   */
  public Composite getComposite() {
    return this.defaultMemberOf.getComposite();
  }

  /**
   * field for membership
   * @return the field
   */
  public Field getField() {
    return this.defaultMemberOf.getField();
  }

  /**
   * group for membership
   * @return the group
   */
  public Group getGroup() {
    return this.defaultMemberOf.getGroup();
  }

  /**
   * member being assigned
   * @return the member
   */
  public Member getMember() {
    return this.defaultMemberOf.getMember();
  }

  /**
   * membership dto
   * @return the membership
   */
  public Membership getMembership() {
    return this.defaultMemberOf.getMembership();
  }

  /**
   * stem
   * @return the stem
   */
  public Stem getStem() {
    return this.defaultMemberOf.getStem();
  }
  
  /**
   * get the effective membership saves (inserts)
   * @return the effective membership saves (inserts) or the empty set
   */
  public Set<Membership> getMembershipEffectiveSaves() {
    Set<Membership> result = new HashSet<Membership>();
    
    for (GrouperAPI grouperAPI : GrouperUtil.nonNull(this.defaultMemberOf.getSaves())) {
      if (grouperAPI instanceof Membership) {
        Membership membership = (Membership)grouperAPI;
        
        //make sure a membership and effective
        if (membership.isEffective()) {
          result.add(membership);
        }
      }
    }
    for (GrouperAPI grouperAPI : GrouperUtil.nonNull(this.defaultMemberOf.getEffectiveSaves())) {
      if (grouperAPI instanceof Membership) {
        Membership membership = (Membership)grouperAPI;
        
        //make sure a membership and effective
        if (membership.isEffective()) {
          result.add(membership);
        }
      }
    }
    return result;
  }
  
  /**
   * get the immediate membership saves (inserts)
   * @return the immediate membership saves (inserts) or the empty set
   */
  public Set<Membership> getMembershipImmediateSaves() {
    Set<Membership> result = new HashSet<Membership>();
    
    for (GrouperAPI grouperAPI : GrouperUtil.nonNull(this.defaultMemberOf.getSaves())) {
      if (grouperAPI instanceof Membership) {
        Membership membership = (Membership)grouperAPI;
        
        //make sure a membership and immediate
        if (membership.isImmediate()) {
          result.add(membership);
        }
      }
    }
    return result;
  }

  /**
   * get the effective membership deletes
   * @return the effective membership saves or the empty set
   */
  public Set<Membership> getMembershipEffectiveDeletes() {
    Set<Membership> result = new HashSet<Membership>();
    
    for (GrouperAPI grouperAPI : GrouperUtil.nonNull(this.defaultMemberOf.getDeletes())) {
      if (grouperAPI instanceof Membership) {
        Membership membership = (Membership)grouperAPI;
        
        //make sure a membership and effective
        if (membership.isEffective()) {
          result.add(membership);
        }
      }
    }
    //try the other property about effective deletes
    for (GrouperAPI grouperAPI : GrouperUtil.nonNull(this.defaultMemberOf.getEffectiveDeletes())) {
      if (grouperAPI instanceof Membership) {
        Membership membership = (Membership)grouperAPI;
        
        //make sure a membership and effective
        if (membership.isEffective()) {
          result.add(membership);
        }
      }
    }
    return result;
  }

  /**
   * get the immediate membership deletes
   * @return the immediate membership deletes or the empty set
   */
  public Set<Membership> getMembershipImmediateDeletes() {
    Set<Membership> result = new HashSet<Membership>();
    
    for (GrouperAPI grouperAPI : GrouperUtil.nonNull(this.defaultMemberOf.getDeletes())) {
      if (grouperAPI instanceof Membership) {
        Membership membership = (Membership)grouperAPI;
        
        //make sure a membership and immediate
        if (membership.isImmediate()) {
          result.add(membership);
        }
      }
    }
    return result;
  }

}
