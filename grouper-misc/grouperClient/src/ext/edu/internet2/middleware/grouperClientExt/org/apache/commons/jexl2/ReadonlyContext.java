/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.internet2.middleware.grouperClientExt.org.apache.commons.jexl2;

/**
 * A readonly context wrapper.
 * @since 2.1
 */
public final class ReadonlyContext implements JexlContext {
    /** The wrapped context. */
    private final JexlContext wrapped;

    /**
     * Creates a new readonly context.
     * @param context the wrapped context
     */
    public ReadonlyContext(JexlContext context) {
        wrapped = context;
    }

    /** {@inheritDoc} */
    public Object get(String name) {
        return wrapped.get(name);
    }

    /** 
     * Will throw an UnsupportedOperationException when called; the JexlEngine deals with it appropriately.
     * @param name the unused variable name
     * @param value the unused variable value
     */
    public void set(String name, Object value) {
        throw new UnsupportedOperationException("Not supported.");
    }

    /** {@inheritDoc} */
    public boolean has(String name) {
        return wrapped.has(name);
    }
}
