/*--
$Id: AdapterUnavailableException.java,v 1.2 2004-12-24 04:15:46 acohen Exp $
$Date: 2004-12-24 04:15:46 $

Copyright 2004 Internet2 and Stanford University.  All Rights Reserved.
Licensed under the Signet License, Version 1,
see doc/license.txt in this distribution.
*/
package edu.internet2.middleware.signet.tree;

/**
Used to indicate that the adapter is not available.
*/
public class AdapterUnavailableException extends Exception
{
public AdapterUnavailableException(String msg)
{
	super(msg);
}

public AdapterUnavailableException(String msg, Throwable cause)
{
	super(msg, cause);
}
}
