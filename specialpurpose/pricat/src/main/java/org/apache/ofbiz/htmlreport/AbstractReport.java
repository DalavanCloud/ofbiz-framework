/*******************************************************************************
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
 *******************************************************************************/
package org.apache.ofbiz.htmlreport;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Base report class.
 * 
 */
public abstract class AbstractReport implements InterfaceReport {

    /** Contains all error messages generated by the report. */
    private List<Object> errors;

    /** The locale this report is written in. */
    private Locale locale;

    /** Runtime of the report. */
    private long startTime;

    /** Contains all warning messages generated by the report. */
    private List<Object> warnings = new ArrayList<Object>();

    /** Day constant. */
    private static final long DAYS = 1000 * 60 * 60 * 24;

    /** Hour constant. */
    private static final long HOURS = 1000 * 60 * 60;

    /** Minute constant. */
    private static final long MINUTES = 1000 * 60;

    /** Second constant. */
    private static final long SECONDS = 1000;
    
    public static final String SESSION_REPORT_CLASS = "OFBIZ_HTML_REPORT";

    public void addError(Object obj) {

        errors.add(obj);
    }

    public void addWarning(Object obj) {

        warnings.add(obj);
    }

    public String formatRuntime() {

        long runtime = getRuntime();
        long seconds = (runtime / SECONDS) % 60;
        long minutes = (runtime / MINUTES) % 60;
        long hours = (runtime / HOURS) % 24;
        long days = runtime / DAYS;
        StringBuffer strBuf = new StringBuffer();

        if (days > 0) {
            if (days < 10) {
                strBuf.append('0');
            }
            strBuf.append(days);
            strBuf.append(':');
        }

        if (hours < 10) {
            strBuf.append('0');
        }
        strBuf.append(hours);
        strBuf.append(':');

        if (minutes < 10) {
            strBuf.append('0');
        }
        strBuf.append(minutes);
        strBuf.append(':');

        if (seconds < 10) {
            strBuf.append('0');
        }
        strBuf.append(seconds);

        return strBuf.toString();
    }

    public List<Object> getErrors() {
        return errors;
    }

    public Locale getLocale() {
        return locale;
    }

    public long getRuntime() {
        return System.currentTimeMillis() - startTime;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public boolean hasError() {
        return (errors.size() > 0);
    }
    
    public boolean hasWarning() {
        return (warnings.size() > 0);
    }

    public void resetRuntime() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Initializes some member variables for this report.<p>
     * 
     * @param locale the locale for this report
     */
    protected void init(Locale locale) {
        startTime = System.currentTimeMillis();
        this.locale = locale;
        errors = new ArrayList<Object>();
    }

    /**
     * Prints a String to the report.<p>
     *
     * @param value the String to add
     */
    public void print(String value) {
        print(value, FORMAT_DEFAULT);
    }

    /**
     * Prints a String to the report, using the indicated formatting.<p>
     * 
     * Use the contants starting with <code>FORMAT</code> from this interface
     * to indicate which formatting to use.<p>
     *
     * @param value the message container to add
     * @param format the formatting to use for the output
     */
    public abstract void print(String value, int format);

    /**
     * Prints a String with line break to the report.<p>
     * 
     * @param value the message container to add
     */
    public void println(String value) {

        println(value, FORMAT_DEFAULT);
    }

    /**
     * Prints a String with line break to the report, using the indicated formatting.<p>
     * 
     * Use the contants starting with <code>FORMAT</code> from this interface
     * to indicate which formatting to use.<p>
     *
     * @param value the String to add
     * @param format the formatting to use for the output
     */
    public void println(String value, int format) {
        print(value, format);
        println();
    }

}