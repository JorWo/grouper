/*--
$Id: Signet.java,v 1.2 2004-12-24 04:15:46 acohen Exp $
$Date: 2004-12-24 04:15:46 $

Copyright 2004 Internet2 and Stanford University.  All Rights Reserved.
Licensed under the Signet License, Version 1,
see doc/license.txt in this distribution.
*/
package edu.internet2.middleware.signet;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.naming.OperationNotSupportedException;

import org.apache.commons.collections.list.UnmodifiableList;
import org.apache.commons.collections.set.UnmodifiableSet;
import org.apache.commons.logging.Log;

import edu.internet2.middleware.signet.tree.Tree;
import edu.internet2.middleware.signet.tree.TreeNotFoundException;
import edu.internet2.middleware.signet.tree.TreeType;
import edu.internet2.middleware.signet.tree.TreeNode;
import edu.internet2.middleware.signet.tree.TreeTypeAdapter;
import edu.internet2.middleware.subject.Subject;
import edu.internet2.middleware.subject.SubjectNotFoundException;
import edu.internet2.middleware.subject.SubjectTypeAdapter;
import edu.internet2.middleware.subject.SubjectType;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.cfg.Configuration;

/**
* This is the factory class for all Signet entities.
* 
*/
public final class Signet
{
/**
 * This constant denotes the default subject-type ID, as it is defined
 * and used by Signet.
 * <p />
 * Perhaps this constant should be moved to the PrivilegedSubject interface,
 * or even hidden within the implementation of that interface.
 */
public static final String DEFAULT_SUBJECT_TYPE_ID
	= "signet";

static final String SCOPE_PART_DELIMITER 
	= ":";

public static final String DEFAULT_TREE_TYPE_ADAPTER_NAME
	= "edu.internet2.middleware.signet.TreeTypeAdapterImpl";

/**
 * This constant denotes the "first name" attribute of a Subject, as it is
 * defined and used by Signet.
 * <p />
 * Perhaps this constant should be moved to the PrivilegedSubject interface,
 * or even hidden within the implementation of that interface.
 */
public static final String ATTR_FIRSTNAME
	= "~firstname";

/**
 * This constant denotes the "middle name" attribute of a Subject, as it is
 * defined and used by Signet.
 * <p />
 * Perhaps this constant should be moved to the PrivilegedSubject interface,
 * or even hidden within the implementation of that interface.
 */
public static final String ATTR_MIDDLENAME
	= "~middlename";

/**
 * This constant denotes the "last name" attribute of a Subject, as it is
 * defined and used by Signet.
 * <p />
 * Perhaps this constant should be moved to the PrivilegedSubject interface,
 * or even hidden within the implementation of that interface.
 */
public static final String ATTR_LASTNAME
	= "~lastname";

/**
 * This constant denotes the "display ID" attribute of a Subject, as it is
 * defined and used by Signet.
 * <p />
 * Perhaps this constant should be moved to the PrivilegedSubject interface,
 * or even hidden within the implementation of that interface.
 */
public static final String ATTR_DISPLAYID
  = "~displayid";


private static final String DEFAULT_SUBJECT_TYPE_NAME
	= "Signet native Subject type";
private static final String SUPERSUBJECT_ID
  = "SignetSuperSubject";   
private static final String SUPERSUBJECT_NAME
	= "The Signet Super-Subject";
private static final String SUPERSUBJECT_DESCRIPTION 
	= "Can grant any permission to any Signet subject.";
private static final String SUPERSUBJECT_DISPLAYID 
	= "SignetSuperSubject";  

private static Configuration 		cfg;
private static SessionFactory		sessionFactory;

private Session						session;
private Transaction 			tx;
private SubjectType				nativeSubjectType;
private PrivilegedSubject	superPSubject;
private int								xactNestingLevel = 0;
private Log								log;

/**
 * This constructor builds the fundamental Signet factory object. It opens
 * a database connection, and stores some Signet-specific metadata in that
 * database if it is not already present.
 *
 */
public Signet()
{
  super();
  
  try
  {
    session = sessionFactory.openSession();
  }
  catch (HibernateException he)
  {
    throw new SignetRuntimeException(he);
  }

  // The native Signet subject-type must be stored in the database.
  // Let's just make sure that it is.
  initNativeSubjectType();
}

/**
 * Creates a new Category.
 * 
 * @param subsystem
 * 			The {@link Subsystem} which contains this {@link Category}.
 * @param id
 *          A short mnemonic code which will appear in XML documents and
 *          other documents used by analysts.
 * @param name
 *          A descriptive name which will appear in UIs and documents
 *          exposed to users.
 * @param status
 * 			The {@link Status} that should be initially assigned to
 * 			this {@link Category}.
 */
public final Category newCategory
(Subsystem 	subsystem,
    String 	id,
    String		name,
    Status		status)
{
  Category category = new CategoryImpl(subsystem, id, name, status);
  subsystem.add(category);
  
  return category;
}

/**
 * Sets the Log associated with this Signet session.
 * @param log
 */
public final void setLog(Log log)
{
  this.log = log;
}

/**
 * Gets the Log associated with this Signet session.
 * @return
 */
public final Log getLog()
{
  return this.log;
}


/**
 * Creates a new Function.
 * 
 * @param category
 * 			The {@link Category} which contains this {@link Function}.
 * @param id
 *          A short mnemonic code which will appear in XML documents and
 *          other documents used by analysts.
 * @param name
 *          A descriptive name which will appear in UIs and documents
 *          exposed to users.
 * @param status
 * 			The {@link Status} that should be initially assigned to
 * 			this {@link Category}.
 * @param helpText A prose description which will appear in help-text and
 * 			other explanatory materials.
 * @param permissions
 *          The {@link Permission}s which should be associated with this
 * 			{@link Function}.
 */
public Function newFunction
	(Category		category,
   String 		id,
   String 		name,
   Status			status,
   String			helpText)
{
  Function newFunction
  	= new FunctionImpl
  		(this, category, id, name, helpText, status);
  
  ((SubsystemImpl)(category.getSubsystem())).add(newFunction);
  
  ((CategoryImpl)category).add(newFunction);
  
  return newFunction;
}

/**
 * Creates a new Subsystem.
 * 
 * @param code
 *            A short mnemonic code which will appear in XML documents and
 *            other documents used by analysts.
 * @param name
 *            A descriptive name which will appear in UIs and documents
 *            exposed to users.
 * @param helpText
 *            A prose description which will appear in help-text and other
 *            explanatory materials.
 * @param status
 * 			The {@link Status} that should be initially assigned to
 * 			this {@link Subsystem}.
 */

public final Subsystem newSubsystem
	(String id,
   String	name,
   String helpText,
   Status	status)
{
  return new SubsystemImpl(this, id, name, helpText, status);
}

/**
 * 
 */
static /* runs at class load time */
{
  cfg = new Configuration();
    
  try
  {
    // Read the "hibernate.cfg.xml" file. It is expected to be in a root
    // directory of the classpath.
    cfg.configure();
    String dbAccount = cfg.getProperty("hibernate.connection.username");
    cfg.setInterceptor(new HousekeepingInterceptor(dbAccount));

    sessionFactory = cfg.buildSessionFactory();
  }
  catch (HibernateException he)
  {
    throw new SignetRuntimeException(he);
  }
  
}

private void initNativeSubjectType()
{
  // The native Signet subject-type must be stored in the database.
  // Let's just make sure that it is.
  
  try
  {
    nativeSubjectType
    	= this.getSubjectType(Signet.DEFAULT_SUBJECT_TYPE_ID);
  }
  catch (edu.internet2.middleware.signet.ObjectNotFoundException onfe)
  {
    nativeSubjectType
  		= new SubjectTypeImpl
  				(this,
				   Signet.DEFAULT_SUBJECT_TYPE_ID,
				   Signet.DEFAULT_SUBJECT_TYPE_NAME,
				   new SubjectTypeAdapterImpl(this));
      
    this.beginTransaction();
    this.save(nativeSubjectType);
    this.commit();
  }
}


/**
 * Begins a Signet transaction.
 *
 */
public final void beginTransaction()
{
  if (xactNestingLevel == 0)
  {
    try
    {
      tx = session.beginTransaction();
    }
    catch (HibernateException e)
    {
      throw new SignetRuntimeException(e);
    }
  }
  
  xactNestingLevel++;
}

/**
 * Saves a new Signet object, and any Signet objects it refers to.
 * 
 * @param o
 */
public final void save(Object o)
{
  try
  {
    session.save(o);
  }
  catch (HibernateException e)
  {
    throw new SignetRuntimeException(e);
  }
}

/**
 * @param treeId
 * @param treeName
 * @return
 */
public final Tree newTree
  (TreeTypeAdapter	adapter,
   String    				treeId,
   String    				treeName)
{
  Tree newTree = new TreeImpl(this, adapter, treeId, treeName);
  return newTree;
}

/**
 * Creates a new TreeNode.
 * 
 * @param tree
 * @param id
 * @param name
 * @return
 */
public final TreeNode newTreeNode
(Tree tree, String id, String name)
{
  TreeNode newTreeNode
    = tree.getAdapter().newTreeNode(tree, id, name);
  
  return newTreeNode;
}

/**
 * Gets a single Tree by ID.
 * 
 * @param id
 * @return
 * @throws ObjectNotFoundException
 */
public final Tree getTree
	(String	id)
throws ObjectNotFoundException
{
  try
  {
    return (Tree)(session.load(TreeImpl.class, id));
  }
  catch (net.sf.hibernate.ObjectNotFoundException onfe)
  {
    throw new edu.internet2.middleware.signet.ObjectNotFoundException(onfe);
  }
  catch (HibernateException e)
  {
    throw new SignetRuntimeException(e);
  }
}  

/**
 * Gets all of the Subsystems in the Signet database.
 * 
 * @return an unmodifiable Set of all of the {@link Subsystem}s in the Signet
 * 			database. Never returns null: in the case of zero
 * 			{@link Subsystem}s, this method will return an empty Set.
 */
public Set getSubsystems()
{
  List resultList;
  Set resultSet = new HashSet();
  
  try
  {
    resultList
    = session.find
    ("from edu.internet2.middleware.signet.SubsystemImpl as subsystem");
  }
  catch (HibernateException e)
  {
    throw new SignetRuntimeException(e);
  }
  
  resultSet.addAll(resultList);
  Iterator resultSetIterator = resultSet.iterator();
  while (resultSetIterator.hasNext())
  {
    SubsystemImpl subsystemImpl = (SubsystemImpl)(resultSetIterator.next());
    subsystemImpl.setSignet(this);
  }
  return UnmodifiableSet.decorate(resultSet);
}



/**
 * Gets all of the Trees in the Signet database. Should probably be changed
 * to return a type-safe Collection.
 * 
 * @return an array of all of the {@link Tree}s in the Signet
 * 			database. Never returns null: in the case of zero
 * 			{@link Tree}s, this method will return a zero-length array.
 */
public Tree[] getTrees()
{
  List resultList;
  
  try
  {
    resultList
    = session.find
    ("from edu.internet2.middleware.signet.TreeImpl as tree");
  }
  catch (HibernateException e)
  {
    throw new SignetRuntimeException(e);
  }
  
  Object[] objectArray = resultList.toArray();
  
  TreeImpl[] treeImplArray
  = new TreeImpl[objectArray.length];
  
  for (int i = 0; i < objectArray.length; i++)
  {
    treeImplArray[i] = (TreeImpl)(objectArray[i]);
  }
  
  return treeImplArray;
}


/**
 * Gets all PrivilegedSubjects. Should probably be changed to return a
 * type-safe Collection.
 * 
 * @return a Set of all of the {@link PrivilegedSubjects}s accessible to
 *      Signet, including those who have no privileges. Never returns null:
 *      in the case of zero {@link PrivilegedSubject}s, this method will
 *      return an empty Set.
 */
public Set getPrivilegedSubjects()
{
  List subjectTypeList;
  Set privilegedSubjects = new HashSet();
  
  try
  {
    subjectTypeList
    	= session.find
    	    ("from edu.internet2.middleware.signet.SubjectTypeImpl"
    	     + " as subjectType");
  }
  catch (HibernateException e)
  {
    throw new SignetRuntimeException
    	("Error while attempting to retrieve all SubjectTypes from"
    	 + " the database",
    	 e);
  }
  
  Iterator subjectTypesIterator = subjectTypeList.iterator();
  while (subjectTypesIterator.hasNext())
  {
    SubjectTypeImpl subjectTypeImpl = (SubjectTypeImpl)(subjectTypesIterator.next());
    subjectTypeImpl.setSignet(this);
    SubjectTypeAdapter adapter = subjectTypeImpl.getAdapter();
    Subject[] subjectsArray = adapter.getSubjects(subjectTypeImpl);
    
    for (int i = 0; i < subjectsArray.length; i++)
    {
      PrivilegedSubjectImpl privilegedSubjectImpl
      	= (PrivilegedSubjectImpl)
      			(this.getPrivilegedSubject(subjectsArray[i]));
      privilegedSubjectImpl.setSignet(this);
      privilegedSubjectImpl.setSubjectType(subjectTypeImpl);
      privilegedSubjects.add(privilegedSubjectImpl);
    }
  }
  
  return UnmodifiableSet.decorate(privilegedSubjects);
}

// I really want to do away with this method, having the PrivilegedSubject
// pick up its granted Assignments via Hibernate object-mapping. I just
// haven't figured out how to do that yet.
//
// I do, however, like this notion of returning an UnmodifiableSet instead of
// an Array.
Set getAssignmentsByGrantor(SubjectKey grantor)
{
  Query query;
  List 	resultList;
  
  try
  {
    query
  		= session.createQuery
  			("from edu.internet2.middleware.signet.AssignmentImpl"
		     + " as assignment"
		     + " where grantorID = :id"
		     + " and grantorTypeID = :type");
    
    query.setString("id", grantor.getSubjectId());
    query.setString("type", grantor.getSubjectType(this).getId());
  
    resultList = query.list();
  }
  catch (HibernateException e)
  {
    throw new SignetRuntimeException(e);
  }
  
  Set resultSet = new HashSet(resultList);
  
  Iterator resultSetIterator = resultSet.iterator();
  while (resultSetIterator.hasNext())
  {
    Assignment assignment = (Assignment)(resultSetIterator.next());
    ((AssignmentImpl)assignment).setSignet(this);
  }
  
  return resultSet;
}



// I really want to do away with this method, having the
// Tree pick up its parent-child relationships via Hibernate
// object-mapping. I just haven't figured out how to do that yet.
Set getParents(TreeNode childNode)
throws TreeNotFoundException
{
  Query query;
  List 	resultList;
  Tree	tree = childNode.getTree();
  
  try
  {
    query
  		= session.createQuery
  			("from edu.internet2.middleware.signet.TreeNodeRelationship"
		     + " as treeNodeRelationship"
		     + " where treeID = :treeId"
		     + " and nodeID = :childNodeId");
    
    query.setString("treeId", tree.getId());
    query.setString("childNodeId", childNode.getId());
  
    resultList = query.list();
  }
  catch (HibernateException e)
  {
    throw new SignetRuntimeException(e);
  }
  
  Set resultSet = new HashSet(resultList);
  Set parents = new HashSet();
  
  Iterator resultSetIterator = resultSet.iterator();
  while (resultSetIterator.hasNext())
  {
    TreeNodeRelationship tnr
    	= (TreeNodeRelationship)(resultSetIterator.next());
    parents.add(tree.getNode(tnr.getParentNodeId()));
  }
  
  return parents;
}



// I really want to do away with this method, having the
// Tree pick up its parent-child relationships via Hibernate
// object-mapping. I just haven't figured out how to do that yet.
Set getChildren(TreeNode parentNode)
throws TreeNotFoundException
{
  Query query;
  List 	resultList;
  Tree	tree = parentNode.getTree();
  
  try
  {
    query
  		= session.createQuery
  			("from edu.internet2.middleware.signet.TreeNodeRelationship"
		     + " as treeNodeRelationship"
		     + " where treeID = :treeId"
		     + " and parentNodeID = :parentNodeId");
    
    query.setString("treeId", tree.getId());
    query.setString("parentNodeId", parentNode.getId());
  
    resultList = query.list();
  }
  catch (HibernateException e)
  {
    throw new SignetRuntimeException(e);
  }
  
  Set resultSet = new HashSet(resultList);
  Set children = new HashSet();
  
  Iterator resultSetIterator = resultSet.iterator();
  while (resultSetIterator.hasNext())
  {
    TreeNodeRelationship tnr
    	= (TreeNodeRelationship)(resultSetIterator.next());
    children.add(tree.getNode(tnr.getChildNodeId()));
  }
  
  return children;
}

// I really want to do away with this method, having the PrivilegedSubject
// pick up its granted Assignments via Hibernate object-mapping. I just
// haven't figured out how to do that yet.
//
// I do, however, like this notion of returning an UnmodifiableSet instead of
// an Array.
Set getAssignmentsByGrantee(SubjectKey grantee)
{
  Query query;
  List 	resultList;
  
  try
  {
    query
  		= session.createQuery
  			("from edu.internet2.middleware.signet.AssignmentImpl"
		     + " as assignment"
		     + " where granteeID = :id"
		     + " and granteeTypeID = :type");
    
    query.setString("id", grantee.getSubjectId());
    query.setString("type", grantee.getSubjectType(this).getId());
  
    resultList = query.list();
  }
  catch (HibernateException e)
  {
    throw new SignetRuntimeException(e);
  }
  
  Set resultSet = new HashSet(resultList);
  Iterator resultSetIterator = resultSet.iterator();
  while (resultSetIterator.hasNext())
  {
    AssignmentImpl assignment
    	= (AssignmentImpl)(resultSetIterator.next());
    assignment.setSignet(this);
  }
  
  return resultSet;
}

// I really want to do away with this method, having the Subsystem
// pick up its associated Functions via Hibernate object-mapping. I just
// haven't figured out how to do that yet.
Set getFunctionsBySubsystem(Subsystem subsystem)
{
  Query query;
  List 	resultList;
  
  try
  {
    query
  		= session.createQuery
  			("from edu.internet2.middleware.signet.FunctionImpl"
		     + " as function"
		     + " where subsystemID = :id");
    
    query.setString("id", subsystem.getId());
  
    resultList = query.list();
  }
  catch (HibernateException e)
  {
    throw new SignetRuntimeException(e);
  }
  
  Set resultSet = new HashSet(resultList);
  
  Iterator resultSetIterator = resultSet.iterator();
  while (resultSetIterator.hasNext())
  {
    Function function = (Function)(resultSetIterator.next());
    ((FunctionImpl)function).setSignet(this);
  }
  
  return resultSet;
}


/**
 * Gets all Assignments in the Signet database. Should probably be changed
 * to return a type-safe Collection.
 * 
 * @return an array of all of the {@link Assignment}s in the
 * 			Signet database. Never returns null: in the case of zero
 * 			{@link Assignment}s, this method will return a zero-length array.
 */
public Assignment[] getAssignments()
{
  List resultList;
  
  try
  {
    resultList
    = session.find
    ("from edu.internet2.middleware.signet.AssignmentImpl"
        + " as assignment");
  }
  catch (HibernateException e)
  {
    throw new SignetRuntimeException(e);
  }
  
  Iterator assignmentsIterator = resultList.iterator();
  while (assignmentsIterator.hasNext())
  {
    AssignmentImpl assignmentImpl
    	= (AssignmentImpl)(assignmentsIterator.next());
    assignmentImpl.setSignet(this);
  }
  
  AssignmentImpl[] assignmentImplArray
  = new AssignmentImpl[resultList.size()];
  
  collection2array(resultList, assignmentImplArray);
  
  return assignmentImplArray;
}  


/**
 * Gets all of the SubjectTypes in the Signet database.
 * 
 * @return a List of all of the {@link SubjectType}s in the
 * 			Signet database. Never returns null: in the case of zero
 * 			{@link SubjectType}s, this method will return an empty List.
 */
public List getSubjectTypes()
{
  List resultList;
  
  try
  {
    resultList
    = session.find
    ("from edu.internet2.middleware.signet.SubjectTypeImpl"
        + " as SubjectType");
  }
  catch (HibernateException e)
  {
    throw new SignetRuntimeException(e);
  }
  
  Iterator resultListIterator = resultList.iterator();
  while (resultListIterator.hasNext())
  {
    ((SubjectTypeImpl)(resultListIterator.next())).setSignet(this);
  }
  
  return UnmodifiableList.decorate(resultList);
}


/**
 * Creates a new Subsystem.
 * 
 * @return
 */
public Subsystem newSubsystem
(String id,
    String name,
    String helpText)
{
  return new SubsystemImpl(this, id, name, helpText, Status.PENDING);
}


/**
 * commitd a Signet database transaction.
 */
public void commit()
{
  if (tx == null)
  {
    throw new IllegalStateException
    	("It is illegal to attempt to commit a Signet transaction that has" +
    	 " not yet begun.");
  }
  
  if (xactNestingLevel < 1)
  {
    throw new SignetRuntimeException
    	("A Signet transaction is open, but Signet's transaction-nesting"
    	 + " level is not greater than zero. This is an internal error.");
  }
  
  xactNestingLevel--;
  
  if (xactNestingLevel == 0)
  {
    try
    {
      tx.commit();
      tx = null;
    }
    catch (HibernateException e)
    {
      throw new SignetRuntimeException(e);
    }
  }
}


/**
 * Closes a Signet session.
 */
public void close()
{
  try
  {
    session.close();
    session = null;
    sessionFactory.close();
    sessionFactory = null;
  }
  catch (HibernateException e)
  {
    throw new SignetRuntimeException(e);
  }
}


/**
 * Creates a new Permission.
 * 
 * @param id
 * @param subsystem
 * @return
 */
public Permission newPermission
	(String			id,
	 Subsystem	subsystem,
	 Status			status)
{
  Permission newPermission = new PermissionImpl(id, subsystem, status);
  
  return newPermission;
}


/**
 * @deprecated This function is no longer needed, and should never have
 * been public in the first place.
 * 
 * @param set
 * @param parentsArray
 */
public Object[] collection2array
(Collection srcCollection, Object[] destArray)
{
  Iterator srcIterator = srcCollection.iterator();
  int i = 0;
  while (srcIterator.hasNext())
  {
    destArray[i] = srcIterator.next();
    i++;
  }
  
  return destArray;
}

/**
 * Gets a single PrivilegedSubject by ID.
 * 
 * @param subjectId
 * @return
 * @throws ObjectNotFoundException
 */
public PrivilegedSubject getPrivilegedSubject(String subjectId)
throws ObjectNotFoundException
{
  return getPrivilegedSubject(Signet.DEFAULT_SUBJECT_TYPE_ID, subjectId);
}

/**
 * This method is for use with extremely simple authentication, which can
 * yield  a subject's display-ID, but not the user's subjectType.
 * We will assume that the subject is one of the subject-types stored
 * by the native Signet-subject adaptor.
 * 
 * Of course, since a single adaptor can serve up multiple subject-types,
 * this method may find more than one subject for this display-ID.
 * 
 * @param displayId
 * @return
 * @throws 
 */
public Set getPrivilegedSubjectsByDisplayId
	(String displayId)
{
  Set pSubjects =new HashSet();
  
  List subjectTypes = this.getSubjectTypes();
  Iterator subjectTypesIterator = subjectTypes.iterator();
  while (subjectTypesIterator.hasNext())
  {
    SubjectType type = (SubjectType)(subjectTypesIterator.next());
    SubjectTypeAdapter adapter = type.getAdapter();
    
    try
    {
      Subject subject = adapter.getSubjectByDisplayId(type, displayId);
      PrivilegedSubject pSubject = this.getPrivilegedSubject(subject);
      pSubjects.add(pSubject);
    }
    catch (SubjectNotFoundException snfe)
    {
      ; // In this context, it's not an error to fail to find a Subject.
    }
  }
  
  return UnmodifiableSet.decorate(pSubjects);
}

/**
 * Gets a single PrivilegedSubject by type and ID.
 * 
 * @param subjectTypeId
 * @param subjectId
 * @return
 * @throws ObjectNotFoundException
 */
public PrivilegedSubject getPrivilegedSubject
	(String	subjectTypeId,
	 String	subjectId)
throws ObjectNotFoundException
{
  Subject subject = getSubject(subjectTypeId, subjectId);
  PrivilegedSubject pSubject = getPrivilegedSubject(subject);
  
  return pSubject;
}

/**
 * Gets a single PrivilegedSubject by its type and displayID.
 * 
 * @param subjectTypeId
 * @param displayId
 * @return
 * @throws ObjectNotFoundException
 */
public PrivilegedSubject getPrivilegedSubjectByDisplayId
	(String	subjectTypeId,
	 String	displayId)
throws ObjectNotFoundException
{
  Subject subject = getSubjectByDisplayId(subjectTypeId, displayId);
  PrivilegedSubject pSubject = getPrivilegedSubject(subject);
  
  return pSubject;
}

/**
 * Gets a single PrivilegedSubject by its underlying Subject.
 * 
 * @param subject
 * @return
 */
public PrivilegedSubject getPrivilegedSubject(Subject subject)
{
  PrivilegedSubjectImpl privilegedSubjectImpl;
  
  // First, let's see if we already have a PrivilegedSubject for this
  // Subject in the database.
  
  privilegedSubjectImpl = fetchExistingPrivilegedSubject(subject);
  
  if (privilegedSubjectImpl == null)
  {
    // There's no existing PrivilegedSubject in the database, so we'll
    // create one now.
    privilegedSubjectImpl = new PrivilegedSubjectImpl(this, subject);
  }
  else
  {
    privilegedSubjectImpl.setSignet(this);
  }
  
  return privilegedSubjectImpl;
}

/**
 * 
 * @param subject
 * @return NULL if no PrivilegedSubject is found.
 */
private PrivilegedSubjectImpl fetchExistingPrivilegedSubject
	(Subject subject)
{
  Query query;
  List 	resultList;
    
  try
  {
    query
    	= session.createQuery
    		("from edu.internet2.middleware.signet.PrivilegedSubjectImpl"
  		   + " as privilegedSubject"
  		   + " where subjectID = :id"
  		   + " and subjectTypeID = :type");
  
    query.setString("id", subject.getId());
    query.setString("type", subject.getSubjectType().getId());
  
    resultList = query.list();
  }
  catch (HibernateException e)
  {
    throw new SignetRuntimeException(e);
  }

	if (resultList.size() < 1)
	{
	  return null;
	}

	if (resultList.size() > 1)
	{
	  throw new SignetRuntimeException
	  	("Each record in the PrivilegedSubjectImpl table is supposed to have a"
       + " unique combination of id and subjectTypeId. Signet found " 
       + resultList.size() 
       + " records with the id '" 
       + subject.getId() 
       + "' and the subjectTypeId '"
       + subject.getSubjectType().getId()
       + "'.");
	}

	return (PrivilegedSubjectImpl)(resultList.get(0));
}

/**
 * Creates a new PrivilegedSubject.
 * 
 * @param subject
 * @return
 */
public PrivilegedSubject newPrivilegedSubject
	(Subject subject)
{
  return new PrivilegedSubjectImpl(this, subject);
}

/**
 * Creates a new Subject.
 * 
 * @param id
 * @param name
 * @return
 * @throws OperationNotSupportedException
 * @throws ObjectNotFoundException
 */
public Subject newSubject(String id, String name)
throws
	ObjectNotFoundException
{
  return this.newSubject(id, name, null, null);
}

/**
 * Creates a new Subject.
 * 
 * @param id
 * @param name
 * @param description
 * @param displayId
 * @return
 * @throws ObjectNotFoundException
 */
public Subject newSubject
	(String id,
	 String	name,
	 String description,
	 String displayId)
throws ObjectNotFoundException
{
  Subject newSubject;
  
  try
  {
    newSubject
    	= this.newSubject
    			(Signet.DEFAULT_SUBJECT_TYPE_ID,
    			 id,
    			 name,
    			 description,
    			 displayId);
  }
  catch (OperationNotSupportedException onse)
  {
    throw new SignetRuntimeException
    	("An attempt to create a new native Signet subject has failed,"
    	 + " because the Signet subject-adapter has reported that its"
    	 + " collection cannot be modified. Although other subject-adapters"
    	 + " may prevent subject-creation, this one should always allow it.",
    	 onse);
  }
  
  return newSubject;
}

/**
 * Creates a new Subject.
 * 
 * @param subjectTypeId
 * @param subjectId
 * @param subjectName
 * @param subjectDescription
 * @param subjectDisplayId
 * @return
 * @throws ObjectNotFoundException
 * @throws OperationNotSupportedException
 */
Subject newSubject
	(String subjectTypeId,
	 String subjectId,
	 String subjectName,
	 String subjectDescription,
	 String subjectDisplayId)
throws
	ObjectNotFoundException,
	OperationNotSupportedException
{
  SubjectType subjectType
  	= this.getSubjectType(subjectTypeId);
  Subject subject
  	= newSubject
  			(subjectType,
  			 subjectId, 
  			 subjectName, 
  			 subjectDescription, 
  			 subjectDisplayId);
  
  return subject;
}

/**
 * Creates a new Subject.
 * 
 * @param subjectType
 * @param subjectId
 * @param subjectName
 * @param subjectDescription
 * @param subjectDisplayId
 * @return
 * @throws OperationNotSupportedException
 */
public Subject newSubject
	(SubjectType  subjectType,
	 String 			subjectId,
	 String 			subjectName,
	 String 			subjectDescription,
	 String 			subjectDisplayId)
throws OperationNotSupportedException
{
  SubjectTypeAdapter adapter = subjectType.getAdapter();
  Subject subject
  	= adapter.newSubject
  			(subjectType,
  			 subjectId, 
  			 subjectName, 
  			 subjectDescription, 
  			 subjectDisplayId);

  if (subjectDisplayId != null)
  {
    subject.addAttribute(Signet.ATTR_DISPLAYID, subjectDisplayId);
  }
  
  return subject;
}

/**
 * This method is only for use by the native Signet SubjectTypeAdapter.
 * @return null if the Subject is not found.
 * @throws SignetRuntimeException if more than one Subject is found.
 */
SubjectImpl getNativeSignetSubject(SubjectType type, String id)
throws ObjectNotFoundException
{
  SubjectImpl subjectImpl;
  
  try
  {
    subjectImpl = (SubjectImpl)(session.load(SubjectImpl.class, id));
  }
  catch (net.sf.hibernate.ObjectNotFoundException onfe)
  {
    throw new edu.internet2.middleware.signet.ObjectNotFoundException(onfe);
  }
  catch (HibernateException he)
  {
    throw new SignetRuntimeException(he);
  }

  subjectImpl.setSubjectType(type);
  
  return subjectImpl;
}

/**
 * This method is only for use by the native Signet TreeTypeAdapter.
 * @return null if the Tree is not found.
 * @throws SignetRuntimeException if more than one Tree is found.
 */
Tree getNativeSignetTree(String id)
throws ObjectNotFoundException
{
  TreeImpl treeImpl;
  
  try
  {
    treeImpl = (TreeImpl)(session.load(TreeImpl.class, id));
  }
  catch (net.sf.hibernate.ObjectNotFoundException onfe)
  {
    throw new edu.internet2.middleware.signet.ObjectNotFoundException(onfe);
  }
  catch (HibernateException he)
  {
    throw new SignetRuntimeException(he);
  }

  treeImpl.setAdapter(new TreeTypeAdapterImpl(this));
  
  return treeImpl;
}

/**
 * This method is only for use by the native Signet SubjectTypeAdapter.
 * @return A list of all native Signet subjects. Never returns null.
 */
List getNativeSignetSubjects(SubjectType type)
{
  Query query;
  List 	resultList;
  
  try
  {
    query
    	= session.createQuery
    		("from edu.internet2.middleware.signet.SubjectImpl"
    		 + " as subject"
    		 + " where subject.subjectType = :type");
      
      query.setParameter("type", type);
    
    resultList = query.list();
  }
  catch (HibernateException e)
  {
    throw new SignetRuntimeException(e);
  }
  
  Iterator subjectIterator = resultList.iterator();
  while (subjectIterator.hasNext())
  {
    Subject subject = (Subject)(subjectIterator.next());
    ((SubjectImpl)subject).setSubjectType(type);
  }
  
  return UnmodifiableList.decorate(resultList);
}

Subject getNativeSignetSubjectByDisplayId
	(SubjectType type, String displayId)
{
  Query query;
  List 	resultList;
  
  try
  {
    query
    	= session.createQuery
    		("from edu.internet2.middleware.signet.SubjectImpl"
    		 + " as subject"
    		 + " where subject.displayId = :id"
    		 + " and subject.subjectType = :type");
    
    query.setString("id", displayId);
    query.setString("type", type.getId());
    
    resultList = query.list();
  }
  catch (HibernateException e)
  {
    throw new SignetRuntimeException(e);
  }
  
  if (resultList.size() < 1)
  {
    return null;
  }
  
  if (resultList.size() > 1)
  {    
    reportMultipleRecordError
    	("SubjectImpl", "displayID", displayId, resultList.size());
  }
  
  // If we got this far, we must have just one result object.
  Subject subject = (Subject)(resultList.get(0));
  ((SubjectImpl)subject).setSubjectType(type);
  
  return subject;
}

/**
 * Gets a single SubjectType by ID.
 * 
 * @param subjectTypeId
 * @return
 * @throws ObjectNotFoundException
 */
public SubjectType getSubjectType(String subjectTypeId)
throws ObjectNotFoundException
{
  SubjectTypeImpl subjectTypeImpl;
  
  try
  {
    subjectTypeImpl
    	= (SubjectTypeImpl)
    			(session.load(SubjectTypeImpl.class, subjectTypeId));
  }
  catch (net.sf.hibernate.ObjectNotFoundException onfe)
  {
    throw new edu.internet2.middleware.signet.ObjectNotFoundException(onfe);
  }
  catch (HibernateException he)
  {
    throw new SignetRuntimeException(he);
  }
  
  subjectTypeImpl.setSignet(this);
  return subjectTypeImpl;
}

///**
// * Gets a single TreeNode by treeID and nodeID.
// * 
// * @param treeId
// * @param treeNodeId
// * @return
// * @throws ObjectNotFoundException
// */
//public TreeNode getTreeNode
//  (String treeId,
//   String treeNodeId)
//throws
//	ObjectNotFoundException
//{
//  return getTreeNode(treeId, treeNodeId);
//}

/**
 * @deprecated the whole notion of tree-type will soon be removed from
 * Signet.
 * 
 * @param treeTypeId
 * @param treeId
 * @param treeNodeId
 * @return
 * @throws ObjectNotFoundException
 */
public TreeNode getTreeNode
  (TreeTypeAdapter 	adapter,
   String 					treeId,
   String						treeNodeId)
throws
	ObjectNotFoundException
{
  Tree tree = null;
  
  try
  {
    tree = adapter.getTree(treeId);
  }
  catch (TreeNotFoundException tnfe)
  {
    throw new ObjectNotFoundException(tnfe);
  }
  
  TreeNode treeNode = tree.getNode(treeNodeId);
  
  return treeNode;
}

/**
 * Gets a single TreNode identified by a scope-string. 
 * The format of that scopeString is currently subject to change. and will
 * be documented after it is finalized.
 * 
 * @param scopeString
 * @return
 * @throws ObjectNotFoundException
 */
public TreeNode getTreeNode(String scopeString)
throws
  ObjectNotFoundException
{
  int firstDelimIndex
  	= scopeString.indexOf(SCOPE_PART_DELIMITER);
  int	secondDelimIndex
  	= scopeString.indexOf
  			(SCOPE_PART_DELIMITER,
  			 firstDelimIndex + SCOPE_PART_DELIMITER.length());
  
  String treeTypeAdapterName
  	= scopeString.substring(0, firstDelimIndex);
  String treeId
  	= scopeString.substring
  			(firstDelimIndex + SCOPE_PART_DELIMITER.length(),
  			 secondDelimIndex);
  String treeNodeId
  	= (scopeString.substring
  	    (secondDelimIndex + SCOPE_PART_DELIMITER.length()));
  
  TreeTypeAdapter adapter
  	= getTreeTypeAdapter(treeTypeAdapterName);
  return getTreeNode(adapter, treeId, treeNodeId);
}

/**
 * 
 * @param treeTypeId
 * @return
 */
public TreeTypeAdapter getTreeTypeAdapter(String adapterName)
{
  TreeTypeAdapter adapter;
  Class clazz = null;
  
  try
  {
    clazz = Class.forName(adapterName);
  }
  catch (ClassNotFoundException cnfe)
  {
    throw new SignetRuntimeException
    	("A Tree in the Signet database uses a TreeTypeAdapter which"
    	 + " is implemented by the class named '"
    	 + adapterName
    	 + "'. This class cannot be found in Signet's classpath.",
    	 cnfe);
  }
  
  Class[] noParams = new Class[0];
  Constructor constructor;
  
  try
  {
    constructor = clazz.getConstructor(noParams);
  }
  catch (NoSuchMethodException nsme)
  {
    throw new SignetRuntimeException
  	("A Tree in the Signet database uses a TreeTypeAdapter which"
     	 + " is implemented by the class named '"
  	 + adapterName
  	 + "'. This class is in Signet's classpath, but it does not provide"
  	 + " a default, parameterless constructor.",
  	 nsme);
  }
  
  try
  {
    adapter
    	= (TreeTypeAdapter)(constructor.newInstance(noParams));
  }
  catch (Exception e)
  {
    throw new SignetRuntimeException
  	("A Tree in the Signet database uses a TreeTypeAdapter which"
      	 + " is implemented by the class named '"
  	 + adapterName
  	 + "'. This class is in Signet's classpath, and it does provide"
  	 + " a default, parameterless constructor, but Signet did not succeed"
  	 + " in invoking that constructor.",
  	 e);
  }
  
  if (adapter instanceof TreeTypeAdapterImpl)
  {
    ((TreeTypeAdapterImpl)(adapter)).setSignet(this);
  }
  
  return adapter;
}

/**
 * Formats a scope-tree for display. This method should probably be moved
 * to some new, display-oriented class.
 * 
 * @param treeNode
 * @param childSeparatorPrefix
 * @param levelPrefix
 * @param levelSuffix
 * @param childSeparatorSuffix
 * @return
 * @throws ObjectNotFoundException
 */
public String displayAncestry
	(TreeNode treeNode,
	 String childSeparatorPrefix,
   String levelPrefix,
   String levelSuffix,
   String childSeparatorSuffix)
throws ObjectNotFoundException
{
  StringBuffer 	display = new StringBuffer();
  int						depth 	= 0;
  
  buildAncestry
  	(display,
  	 treeNode,
  	 childSeparatorPrefix + levelPrefix,
  	 levelSuffix + childSeparatorSuffix);
  
  try
  {
    display.insert(0, treeNode.getTree().getName());
  }
  catch (TreeNotFoundException tnfe)
  {
    throw new ObjectNotFoundException(tnfe);
  }
  
  display.insert(0, levelPrefix);
  display.append(levelSuffix);
  
  return display.toString();
}

private void buildAncestry
	(StringBuffer display,
	 TreeNode			node,
	 String				prefix,
	 String				suffix)
{
  if (node == null)
  {
    return;
  }
  
  display.insert(0, node.getName());
  display.insert(0, prefix);
  display.append(suffix);
  
  Set parents = node.getParents();
  Iterator parentsIterator = parents.iterator();
  while (parentsIterator.hasNext())
  {
    buildAncestry
    	(display, (TreeNode)(parentsIterator.next()), prefix, suffix);
  }
}


/**
 * Gets a single Subsystem by ID.
 * 
 * @param string
 * @return
 */
public Subsystem getSubsystem(String id)
throws ObjectNotFoundException
{
  SubsystemImpl subsystemImpl;
  
  try
  {
    subsystemImpl = (SubsystemImpl)(session.load(SubsystemImpl.class, id));
  }
  catch (net.sf.hibernate.ObjectNotFoundException onfe)
  {
    throw new edu.internet2.middleware.signet.ObjectNotFoundException(onfe);
  }
  catch (HibernateException he)
  {
    throw new SignetRuntimeException(he);
  }
  
  subsystemImpl.setSignet(this);
  return subsystemImpl;
}


///**
// * Gets a single Function by ID.
// * 
// * @param string
// * @return
// */
//public Function getFunction(String id)
//throws ObjectNotFoundException
//{    
//  FunctionImpl function;
//  
//  try
//  {
//    function = (FunctionImpl)(session.load(FunctionImpl.class, id));
//  }
//  catch (net.sf.hibernate.ObjectNotFoundException onfe)
//  {
//    throw new edu.internet2.middleware.signet.ObjectNotFoundException(onfe);
//  }
//  catch (HibernateException he)
//  {
//    throw new SignetRuntimeException(he);
//  }
//  
//  function.setSignet(this);
//  return function;
//}

/**
 * Gets the Signet super-privileged Subject, creating it if it does not
 * already exist in the database.
 * 
 * @return
 * @throws ObjectNotFoundException
 */
public PrivilegedSubject getSuperPrivilegedSubject()
throws ObjectNotFoundException
{
  // If we've already fetched the SuperPrivilegedSubject from the
  // database, we'll just return it and be done.
  if (superPSubject != null)
  {
    return superPSubject;
  }
  
  // Let's fetch the SuperPrivilegedSubject from the database, starting
  // with its underlying Subject. If that Subject does not yet exist,
  // then we'll create it.
  
  Subject superSubject;
  
  try
  {
    superSubject
  		= this.getSubject
  				(Signet.DEFAULT_SUBJECT_TYPE_ID, Signet.SUPERSUBJECT_ID);
  }
  catch (ObjectNotFoundException snfe)
  {
    // The superSubject was not found. Let's put it into the database.
    // TODO: This code will need to be protected by a critical section.
    
    try
    {
      superSubject
      	= this.newSubject
      			(this.nativeSubjectType,
      			 Signet.SUPERSUBJECT_ID,
      	  	 Signet.SUPERSUBJECT_NAME,
      	  	 Signet.SUPERSUBJECT_DESCRIPTION,
      	  	 Signet.SUPERSUBJECT_DISPLAYID);
    }
    catch (OperationNotSupportedException onse)
    {
      throw new SignetRuntimeException
      	("An attempt to store the Signet superSubject in the database"
      	 + " failed.", onse);
    }
  }
  
  superPSubject = this.getPrivilegedSubject(superSubject);
  
  return superPSubject;
}

/**
 * Gets a single Subject by ID.
 * 
 * @param subjectId
 * @return
 * @throws ObjectNotFoundException
 */
public Subject getSubject(String subjectId)
throws ObjectNotFoundException
{
  return this.getSubject(Signet.DEFAULT_SUBJECT_TYPE_ID, subjectId);
}

/**
 * Gets a single Subject by type and ID.
 * 
 * @param subjectTypeId
 * @param subjectId
 * @return
 * @throws ObjectNotFoundException
 */
public Subject getSubject(String subjectTypeId, String subjectId)
throws
	ObjectNotFoundException
{
  SubjectType type = this.getSubjectType(subjectTypeId);
  SubjectTypeAdapter adapter = type.getAdapter();
  Subject subject = null;
  
  if (adapter instanceof SubjectTypeAdapterImpl)
  {
    ((SubjectTypeAdapterImpl)adapter).setSignet(this);
  }
  
  try
  {
    subject = adapter.getSubject(type, subjectId);
  }
  catch (SubjectNotFoundException snfe)
  {
    throw new ObjectNotFoundException(snfe);
  }
  
  return subject;
}

/**
 * Gets a single Subject by type and display ID.
 * 
 * @param subjectTypeId
 * @param displayId
 * @return
 * @throws ObjectNotFoundException
 */
public Subject getSubjectByDisplayId
	(String subjectTypeId,
	 String displayId)
throws
	ObjectNotFoundException
{
  SubjectType type = this.getSubjectType(subjectTypeId);
  SubjectTypeAdapter adapter = type.getAdapter();
  Subject subject = null;
  
  try
  {
    subject = adapter.getSubjectByDisplayId(type, displayId);
  }
  catch (SubjectNotFoundException snfe)
  {
    throw new ObjectNotFoundException(snfe);
  }
  
  return subject;
}

private void reportMultipleRecordError
	(String tableName,
	 String keyName,
	 String keyVal,
	 int 		recordCount)
{
  throw new SignetRuntimeException
  	("Each record in the '"
  	 + tableName
  	 + "'table is supposed to have a"
  	 + " unique "
  	 + keyName
  	 + ". Signet found " 
  	 + recordCount 
  	 + " records with the "
  	 + keyName
  	 + "'" 
  	 + keyVal 
  	 + "'.");
}


/**
 * Creates a new Subject.
 * 
 * @param person_subject_type_id
 * @param person_subject_type_name
 * @param adapter
 */
public SubjectType newSubjectType
	(String 						subjectTypeId,
	 String 						subjectTypeName,
	 SubjectTypeAdapter adapter)
{
  SubjectType newSubjectType;
  
  newSubjectType
		= new SubjectTypeImpl
				(this,
				 subjectTypeId,
			   subjectTypeName,
		     adapter);
  
  return newSubjectType;
}


/**
 * Normalizes a Subject attribute-value. Should this method be public?
 * 
 * @param value The Value of a Signet Subject attribute.
 * 
 * @return the normalized version of the attribute value.
 * 		This is the original value shifted to all lower-case,
 * 		and with all punctuation marks removed.
 */
public String normalizeSubjectAttributeValue(String value)
{
  int valueLen = value.length();
  StringBuffer normalized = new StringBuffer(valueLen);
  
  for (int i = 0; i < valueLen; i++)
  {
    char currentChar = value.charAt(i);
    if (Character.isLetterOrDigit(currentChar))
    {
      normalized.append(currentChar);
    }
    else if (Character.isWhitespace(currentChar))
    {
      normalized.append(currentChar);
    }
  }

  return new String(normalized);
}

private Set getRootsOfContainingTrees(Set treeNodes)
throws TreeNotFoundException
{
  Set roots = new HashSet();
  
  Iterator treeNodesIterator = treeNodes.iterator();
  while (treeNodesIterator.hasNext())
  {
    TreeNode treeNode = (TreeNode)(treeNodesIterator.next());
    Tree tree = treeNode.getTree();
    roots.addAll(tree.getRoots());
  }
  
  return roots;
}

/**
 * Formats a Tree for display, with special handling of specified nodes,
 * the ancestors of those noes, and the descendants of those nodes.
 * 
 * @param ancestorPrefix
 * @param selfPrefix
 * @param descendantPrefix
 * @param prefixIncrement
 * @param infix
 * @param infixIncrement
 * @param suffix
 * @param treeNodesOfInterest
 * @return
 * @throws TreeNotFoundException
 */
public String printTreeNodesInContext
  (String ancestorPrefix,
   String selfPrefix,
   String descendantPrefix,
   String prefixIncrement,
   String infix,
   String infixIncrement,
   String suffix,
   Set    treeNodesOfInterest)
throws TreeNotFoundException
{
  StringBuffer scopesDisplay = new StringBuffer();
  Set roots = getRootsOfContainingTrees(treeNodesOfInterest);

  Iterator rootsIterator = roots.iterator();
  while (rootsIterator.hasNext())
  {
    TreeNode root = (TreeNode)(rootsIterator.next());
    printTreeNode
      (scopesDisplay,
       ancestorPrefix,
       selfPrefix,
       descendantPrefix,
       prefixIncrement,
       infix,
       infixIncrement,
       suffix,
       treeNodesOfInterest,
       root);
  }
  
  return scopesDisplay.toString();
}

/**
 * Formats a Tree for display. This method should probably be moved to some
 * new, display-oriented class.
 * 
 * @param ancestorPrefix
 * @param selfPrefix
 * @param descendantPrefix
 * @param prefixIncrement
 * @param infix
 * @param infixIncrement
 * @param followingLine
 * @param tree
 * @return
 * @throws TreeNotFoundException
 */
public String printTree
  (String ancestorPrefix,
   String selfPrefix,
   String descendantPrefix,
   String prefixIncrement,	// gets PREpended to prefix
   String infix,
   String infixIncrement,		// gets APpended to infix
   String followingLine, 
   Tree  	tree)
throws TreeNotFoundException
{
  StringBuffer treeDisplay = new StringBuffer();
  
  if (tree != null)
  {
    Set roots = tree.getRoots();
    Iterator rootsIterator = roots.iterator();
    while (rootsIterator.hasNext())
    {
      TreeNode root = (TreeNode)(rootsIterator.next());
      Set allTreeNodes = root.getTree().getTreeNodes();
    
      printTreeNode
      	(treeDisplay,
         ancestorPrefix,
         selfPrefix,
         descendantPrefix,
         prefixIncrement,
         infix,
         infixIncrement,
         followingLine,
         allTreeNodes,
         root);
    }
  }
  
  return treeDisplay.toString();
}

private void printTreeNode
  (StringBuffer scopesDisplay,
   String				prefix,
   String       prefixIncrement,
   String				infix,
   String				infixIncrement,
   String				suffix,
   Set					allGrantableScopes,
   TreeType			treeType,
   Tree					tree,
   TreeNode			treeNode)
{
  printTreeNode
  	(scopesDisplay,
  	 prefix,
  	 prefix,
  	 prefix,
  	 prefixIncrement,
  	 infix,
  	 infixIncrement,
  	 suffix,
  	 allGrantableScopes,
  	 treeNode);
}

private void printTreeNode
  (StringBuffer scopesDisplay,
   String 			ancestorPrefix,
   String 			selfPrefix,
   String 			descendantPrefix,
   String       prefixIncrement,
   String				infix,
   String				infixIncrement,
   String				suffix,
   Set					allGrantableScopes,
   TreeNode			treeNode)
{
  if (treeNode == null)
  {
    return;
  }
  if (allGrantableScopes.contains(treeNode))
  {
    scopesDisplay.append(selfPrefix);
  }
  else if (treeNode.isAncestorOfAll(allGrantableScopes))
  {
    scopesDisplay.append(ancestorPrefix);
  }
  else
  {
    scopesDisplay.append(descendantPrefix);
  }
  
  scopesDisplay.append(treeNode);
  scopesDisplay.append(infix);
  scopesDisplay.append(treeNode.getName());
  scopesDisplay.append(suffix);

  Set children = treeNode.getChildren();
  SortedSet sortedChildren = new TreeSet(children);
  Iterator sortedChildrenIterator = sortedChildren.iterator();
  while (sortedChildrenIterator.hasNext())
  {
    printTreeNode
      (scopesDisplay,
       prefixIncrement + ancestorPrefix,
       prefixIncrement + selfPrefix,
       prefixIncrement + descendantPrefix,
       prefixIncrement,
       infix + infixIncrement,
       infixIncrement,
       suffix,
       allGrantableScopes,
       (TreeNode)(sortedChildrenIterator.next()));
  }
}

/**
 * Gets a single Assignment by ID.
 * 
 * @param assignmentId
 * @return
 * @throws ObjectNotFoundException
 */
public Assignment getAssignment(int id)
throws ObjectNotFoundException
{    
  Assignment assignment;
  
  try
  {
    assignment
      = (Assignment)
          (session.load(AssignmentImpl.class, new Integer(id)));
  }
  catch (net.sf.hibernate.ObjectNotFoundException onfe)
  {
    throw new edu.internet2.middleware.signet.ObjectNotFoundException(onfe);
  }
  catch (HibernateException he)
  {
    throw new SignetRuntimeException(he);
  }
  
  return assignment;
}
}
