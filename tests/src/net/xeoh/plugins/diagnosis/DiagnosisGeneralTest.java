/*
 * PluginTest.java
 *
 * Copyright (c) 2008, Ralf Biedert All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer. Redistributions in binary form must reproduce the
 * above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * Neither the name of the author nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package net.xeoh.plugins.diagnosis;

import java.net.URI;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.JSPFProperties;
import net.xeoh.plugins.diagnosis.diagnosis.channels.LoggingChannel;
import net.xeoh.plugins.diagnosis.diagnosis.channels.TestChannel;
import net.xeoh.plugins.diagnosis.local.Diagnosis;
import net.xeoh.plugins.testplugins.testannotations.impl.TestAnnotationsImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author rb
 * 
 */
public class DiagnosisGeneralTest {

    private PluginManager pm;

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        final JSPFProperties props = new JSPFProperties();

        props.setProperty(Diagnosis.class, "recording.enabled", "true");
        props.setProperty(Diagnosis.class, "recording.file", "diagnosis.record");
        props.setProperty(Diagnosis.class, "recording.format", "xml/simple");
        props.setProperty(Diagnosis.class, "analysis.stacktraces.enabled", "true");
        props.setProperty(Diagnosis.class, "analysis.stacktraces.depth", "10000");

        
        // Enable and disable plugins like this:
        props.setProperty(TestAnnotationsImpl.class, "plugin.disabled", "false");
        
        this.pm = PluginManagerFactory.createPluginManager(props);
        this.pm.addPluginsFrom(URI.create("xxx:yyy"));
    }

    /**
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        this.pm.shutdown();
    }

    /**
     * 
     */
    @SuppressWarnings("boxing")
    @Test
    public void testGetPluginClassOfP() {
        Assert.assertNotNull(this.pm);
        final Diagnosis diagnosis = this.pm.getPlugin(Diagnosis.class);
        
        diagnosis.channel(LoggingChannel.class).status("Starting Test.");
        diagnosis.channel(TestChannel.class).status(100);

        diagnosis.channel(TestChannel.class).status(100);
        diagnosis.channel(TestChannel.class).status(100);
        diagnosis.channel(TestChannel.class).status(3000);
        diagnosis.channel(LoggingChannel.class).status("Initializing Status");
        

        //DiagnosisCondition condition = new TestCondition();
        //condition
        //diagnosis.registerCondition(condition);
        
        
        // Was ist mit Multi-Threading?
        // T1: start() -> state1 -> state2 ....        -> state4 
        // T2:                      start() -> state1
        // In dem Fall würde der Sensor sehen
        // s1 s2 s1 s4 s2 s5 ...
        // Und könnte nicht wirklich eine sinnvolle condition definieren.
        
        // Andererseits, wir haben den Thread-Status dabei, so dass jeder Thread seine eigene 
        // time-line bekommt, bzw. bekommen kann
        
        // Das würde bedeuten, dass es keinen einzigen Zustand s gibt, sondern eine unbestimmte menge davon. 
        
        // Wie macht man das nun mit conditions?
        
        // if(s == 1 && t == 3)?
        // if(s(threadA) == 1 && t(threadA) == 3)?
        
        
        
        // Frage, wie kann ich einzelne Kanäle deaktivieren, die z.b: eine Hohe Last oder Debugausgaben verursachen?
        // An die komme ich nämlich nicht einfach ran, wenn die in einem Plugin verborgen sind.
        // --> InformationBroker
                      
        
        // Soll es eine Replay-API geben?
        // --> Nicht notwendig, wenn es Tools zur Analyse in JSPF mit dazu gibt (z.B. Extraktor & Konverter)
        
        
        // Was ist mit den serialisierten Werten, die können vom Tool nicht zurückgelesen werden, sofern es spezielle 
        // Werte sind.
        // --> Müssen im Classpath mit aufgenommen werden, oder werden halt nicht wiede de-serialisiert 
        
        
        // Wird das nicht langsam sein?
        // --> Ist nicht gedacht für high-volume recording, sondern eher für sagen wir <100 Nachrichten pro Sekunde
        // auf Pluginebene. 
        
        
        // Was ist mit Erklärungen f. Messwerte, Conditions, usw. Die wären im Replay-Tool nicht verfügbar, insbesondere
        // nicht, wenn die zur Laufzeit zusammengeschuhstert werden müssen
        // --> Muss auf Klassenebene der Channels passieren, dort Konverter usw. reinzumachen
        

        // Würde ein Recording auf den Channels mit primitiven Typen nicht viel schneller sein?
        // z.B. diagnosis.channel(TestChannel.class).reportInt(30)
        // dann hätte man auch keine Probleme beim De-Serialisieren
        // --> Mmhm, ist was dran. Ist aber weniger elegant, außerdem können dann auf einem Channel 
        // Werte gemischt werden. Ich behaupte einfach mal dass in Richtung Java 7,8,9 Boxing-Typen
        // irgendwann gleichschnell wie primitive Typen behandelt werden können. Und das Problem 
        // beim entpacken kann der Entwickler einfach dadurch lösen, dass er halt nur eingebaute Typen
        // nimmt ...
        
        
        // Q1: How does the condition access diagnosis-internal functions/variables?
        // Q2: How do we associate conditions with messages / remedies / ...?
        // Q3: Who fires *when* and *how* and triggers *what* when a condition is met?
        diagnosis.channel(LoggingChannel.class).status("Ending Test.");     
    }

}
