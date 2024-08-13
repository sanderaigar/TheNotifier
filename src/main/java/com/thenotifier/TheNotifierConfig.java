package com.thenotifier;

import net.runelite.client.config.*;
import net.runelite.client.config.ConfigSection;

import java.awt.*;

@ConfigGroup("health-notifications")
public interface TheNotifierConfig extends Config
{
	/* Hitpoint Settings */
	@ConfigSection(
			name = "Hitpoint Settings",
			description = "Hitpoint settings",
			position = 0,
			closedByDefault = false
	)
	String hitpointSettings = "hitpointSettings";

	@ConfigItem(
			keyName = "getHitpointThreshold",
			name = "Hitpoint Threshold",
			description = "Set hitpoint threshold",
			position = 1,
			section = hitpointSettings
	)
	default int getHitpointThreshold() {
		return 1;
	}

	@Alpha
	@ConfigItem(
			keyName = "overlayColor",
			name = "Overlay Color",
			description = "Set the notification overlay color",
			position = 2,
			section = hitpointSettings
	)
	default Color getHitpointOverlayColor() {
		return new Color(1.0f, 0.0f, 0.0f, 0.25f);
	}

	@ConfigItem(
			keyName = "disableOverlay",
			name = "Disable Overlay",
			description = "Disable overlay notifications",
			position = 3,
			section = hitpointSettings
	)
	default boolean disableHitpointOverlay() {
		return false;
	}

	@ConfigItem(
			keyName = "healthSoundAlertText",
			name = "Text-to-speech for sound alert",
			description = "Text that gets played as speech when threshold is reached"
	)
	default String getHealthSoundAlertText()
	{
		return "HEALTH!";
	}

	@ConfigItem(
			keyName = "disableHealthSoundAlert",
			name = "Disable (TTS) Sound",
			description = "Disable text-to-speech notification",
			position = 3,
			section = hitpointSettings
	)
	default boolean isHealthSoundAlertEnabled() {
		return false;
	}

	@Range(
			min = 0,
			max = 100
	)
	@ConfigItem(
			keyName = "healthAletvolume",
			name = "Health (TTS)Audio volume",
			description = "Speech volume (0-100)%.",
			position = 1,
			section = hitpointSettings
	)
	default int audioVolume()
	{
		return  100;
	}

	@ConfigItem(
			keyName = "disableNotification",
			name = "Disable Notification",
			description = "Disable tray notifications",
			position = 4,
			section = hitpointSettings
	)
	default boolean disableHitpointNotifications() { return true; }

}
