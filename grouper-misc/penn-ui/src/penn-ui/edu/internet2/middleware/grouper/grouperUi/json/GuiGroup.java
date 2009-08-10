/**
 * 
 */
package edu.internet2.middleware.grouper.grouperUi.json;

import edu.internet2.middleware.grouper.Group;
import edu.internet2.middleware.grouper.grouperUi.util.GuiUtils;


/**
 * Result of one group being retrieved since a user is a member of it.  The number of
 * groups will equal the number of groups the user is a member of (provided the filter matches)
 * 
 * @author mchyzer
 */
public class GuiGroup {

  /** group */
  private Group group;
  
  /**
   * return the group
   * @return the group
   */
  public Group getGroup() {
    return this.group;
  }

  /**
   * 
   */
  public GuiGroup() {
    
  }
  
  /**
   * 
   * @param theGroup
   */
  public GuiGroup(Group theGroup) {
    this.group = theGroup;
  }
  
  /**
   * the export subject ids file name
   * @return the export subject ids file name
   */
  public String getExportSubjectIdsFileName() {
    return getExportSubjectIdsFileNameStatic(this.group);
  }
  
  /**
   * static logic
   * @param group
   * @return the file name
   */
  public static String getExportSubjectIdsFileNameStatic(Group group) {
    String groupExtensionFileName = group.getDisplayExtension();
    
    groupExtensionFileName = GuiUtils.stripNonFilenameChars(groupExtensionFileName);
    
    return "groupExportSubjectIds_" + groupExtensionFileName + ".csv";
 
  }
  
}
