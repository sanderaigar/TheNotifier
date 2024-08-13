package com.thenotifier.config;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.thenotifier.TheNotifierConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SoundPlayer
{
    public void tryLoadSpeech(TheNotifierConfig config, String dialect)
    {
        if (!config.isHealthSoundAlertEnabled())
            return;

        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice voice = voiceManager.getVoice("kevin16");
        if (voice == null) {
            System.err.println("Cannot find voice: kevin16");
            System.exit(1);
        }

        voice.setVolume(Float.parseFloat(String.valueOf(config.audioVolume() / 100)));
        voice.allocate();
        voice.speak(dialect);
        voice.deallocate();
    }
}