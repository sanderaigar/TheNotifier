package com.thenotifier.util; /**
 * Copyright 2003 Sun Microsystems, Inc.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL 
 * WARRANTIES.
 */
import com.sun.speech.freetts.jsapi.FreeTTSEngineCentral;
import com.thenotifier.TheNotifierConfig;

import java.util.Locale;

import javax.speech.EngineCreate;
import javax.speech.EngineList;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

/**
 * Simple program showing how to use FreeTTS using only the Java
 * Speech API (JSAPI).
 */
public class SpeechPlayer {

    protected Synthesizer synthesizer;

    public void createSynthesizer() {

        try {
            System.setProperty("freetts.voices","com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            SynthesizerModeDesc desc =
                    new SynthesizerModeDesc(null,
                            "general",
                            Locale.US,
                            Boolean.FALSE,
                            null);

            FreeTTSEngineCentral central = new FreeTTSEngineCentral();
            EngineList list = central.createEngineList(desc);

            if (list.size() > 0) {
                EngineCreate creator = (EngineCreate) list.get(0);
                synthesizer = (Synthesizer) creator.createEngine();
            }
            if (synthesizer == null) {
                System.err.println("Cannot create synthesizer");
                System.exit(1);
            }
            synthesizer.allocate();
            synthesizer.resume();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void speak(TheNotifierConfig config, String speech) {
        try {
            synthesizer.getSynthesizerProperties().setVolume((float) config.audioVolume() / 100);
            synthesizer.speakPlainText(speech, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}