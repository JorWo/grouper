/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package edu.internet2.middleware.grouperInstallerExt.org.apache.commons.compress.archivers;

/**
 * Exception thrown by ArchiveStreamFactory if a format is requested/detected that doesn't support streaming.
 * 
 * @since 1.8
 */
public class StreamingNotSupportedException extends ArchiveException {
    
    private static final long serialVersionUID = 1L;
    
    private final String format;

    /**
     * Creates a new StreamingNotSupportedException.
     * 
     * @param format the format that has been requested/detected.
     */
    public StreamingNotSupportedException(String format) {
        super("The " + format + " doesn't support streaming.");
        this.format = format;
    }

    /**
     * Returns the format that has been requested/detected.
     * 
     * @return the format that has been requested/detected.
     */
    public String getFormat() {
        return format;
    }
    
}