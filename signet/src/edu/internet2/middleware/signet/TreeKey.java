/*--
$Id: TreeKey.java,v 1.2 2004-12-24 04:15:46 acohen Exp $
$Date: 2004-12-24 04:15:46 $

Copyright 2004 Internet2 and Stanford University.  All Rights Reserved.
Licensed under the Signet License, Version 1,
see doc/license.txt in this distribution.
*/
package edu.internet2.middleware.signet;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import edu.internet2.middleware.signet.tree.Tree;
import edu.internet2.middleware.signet.tree.TreeType;
import edu.internet2.middleware.signet.tree.TreeTypeAdapter;

class TreeKey
implements Serializable
{
private Signet					signet;
private String 					treeId;
private TreeTypeAdapter	adapter;

/**
 * 
 */
TreeKey()
{
  super();
  // TODO Auto-generated constructor stub
}

TreeKey(Signet signet, String treeId, TreeTypeAdapter adapter)
{
  this.signet = signet;
  this.treeId = treeId;
  this.adapter = adapter;
}

TreeKey(Tree tree)
{
  this.treeId = tree.getId();
  this.adapter = tree.getAdapter();
}

/**
 * @return Returns the id.
 */
String getTreeId()
{
  return this.treeId;
}
/**
 * @param id The id to set.
 */
void setTreeId(String treeId)
{
  this.treeId = treeId;
}

/**
 * @return Returns the treeType.
 */
TreeTypeAdapter getAdapter()
{
  return this.adapter;
}

/**
 * @param treeType The treeType to set.
 */
void setAdapter(TreeTypeAdapter adapter)
{
  this.adapter = adapter;
}

String getAdapterName()
{
  return this.adapter.getClass().getName();
}

void setAdapterName(String adapterName)
throws ObjectNotFoundException
{
  this.adapter = this.signet.getTreeTypeAdapter(adapterName);
}

boolean isComplete()
{
  if ((this.treeId != null) && (this.adapter != null))
  {
    return true;
  }
  else
  {
    return false;
  }
}


/* (non-Javadoc)
 * @see java.lang.Object#equals(java.lang.Object)
 */
public boolean equals(Object obj)
{
  if ( !(obj instanceof TreeKey) )
  {
    return false;
  }
  
  TreeKey rhs = (TreeKey) obj;
  return new EqualsBuilder()
                  .append(this.getTreeId(), rhs.getTreeId())
                  .append(this.getAdapter(), rhs.getAdapter())
                  .isEquals();
}

/* (non-Javadoc)
 * @see java.lang.Object#hashCode()
 */
public int hashCode()
{
  // you pick a hard-coded, randomly chosen, non-zero, odd number
  // ideally different for each class
  return new HashCodeBuilder(17, 37).   
     append(this.getTreeId())
     .append(this.getAdapter())
     .toHashCode();
}

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
public String toString()
{
  String outStr
  	= "id='"
  	  + this.treeId 
  	  + "', treeType='" 
  	  + this.adapter
  	  + "'";
  
  return outStr;
}
}
