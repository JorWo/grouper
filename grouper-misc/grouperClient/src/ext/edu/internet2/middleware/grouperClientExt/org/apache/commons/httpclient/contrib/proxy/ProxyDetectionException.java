/*
 * $HeadURL: https://svn.apache.org/repos/asf/jakarta/httpcomponents/oac.hc3x/tags/HTTPCLIENT_3_1/src/contrib/org/apache/commons/httpclient/contrib/proxy/ProxyDetectionException.java $
 * $Revision: 480424 $
 * $Date: 2006-11-29 06:56:49 +0100 (Wed, 29 Nov 2006) $
 *
 * ====================================================================
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package edu.internet2.middleware.grouperClientExt.org.apache.commons.httpclient.contrib.proxy;

import edu.internet2.middleware.grouperClientExt.org.apache.commons.httpclient.util.ExceptionUtil;

/**
 * Signals a problem with auto-detecting the proxy information using the java
 * plugin.
 * 
 * <p>
 * DISCLAIMER: HttpClient developers DO NOT actively support this component.
 * The component is provided as a reference material, which may be inappropriate
 * for use without additional customization.
 * </p>
 */
public class ProxyDetectionException extends Exception {

    /**
     * Creates a new ProxyDetectionException with a <tt>null</tt> detail message. 
     */
    public ProxyDetectionException() {
        super();
    }

    /**
     * Creates a new ProxyDetectionException with the specified detail message.
     * 
     * @param message The exception detail message
     */
    public ProxyDetectionException(String message) {
        super(message);
    }

    /**
     * Creates a new ProxyDetectionException with the specified detail message 
     * and cause.
     * 
     * @param message the exception detail message
     * @param cause the <tt>Throwable</tt> that caused this exception, or 
     *              <tt>null</tt> if the cause is unavailable, unknown, or not 
     *              a <tt>Throwable</tt>
     */
    public ProxyDetectionException(String message, Throwable cause) {
        super(message);
        ExceptionUtil.initCause(this, cause);
    }

}
