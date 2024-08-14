package com.thenotifier;

import com.google.inject.Provides;
import javax.inject.Inject;

import com.thenotifier.util.SpeechPlayer;
import lombok.extern.slf4j.Slf4j;

import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Skill;

import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.Notifier;

import java.awt.*;

@Slf4j
@PluginDescriptor(
	name = "The Notifier",
	description = "Different notification possibilities",
	tags = {"health", "hitpoints", "hp", "notification", "notifications", "visual", "sound", "idle", "skilling"}
)
public class TheNotifierPlugin extends Plugin
{
	private boolean shouldNotifyHitpoints = true;
	private boolean shouldNotifyPrayer = true;

	@Inject
	private Client client;

	@Inject
	private TheNotifierConfig config;

	@Inject
	private TheNotifierOverlay hitpointOverlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private SpeechPlayer speechPlayer;

	@Inject
	private Notifier notifier;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(hitpointOverlay);
		speechPlayer.createSynthesizer();
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(hitpointOverlay);
	}

	@Subscribe
	public void onGameTick(GameTick event) {
		if (!isClientReady()) {
			return;
		}

		if (!config.disableHitpointNotifications()) {
			if (shouldNotifyHitpoints && hitpointTotalBelowThreshold()) {
				speechPlayer.speak(config, config.getHealthSoundAlertText());
				notifier.notify("Your hitpoints are below " + config.getHitpointThreshold());
				shouldNotifyHitpoints = false;
			} else if (!hitpointTotalBelowThreshold()) {
				shouldNotifyHitpoints = true;
			}
		}
	}

	@Provides
	TheNotifierConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(TheNotifierConfig.class);
	}

	public boolean shouldRenderOverlay() {
		if (!isClientReady()) {
			return false;
		}

		/* Render for hitpoints */
		if (!config.disableHitpointOverlay() && hitpointTotalBelowThreshold()) {
			return true;
		}

		return false;
	}

	public Color getOverlayColor() {

		/* We'll just default to alerting on Health via the overlay
		 * if combo is off and both are breaching thresholds
		 * Maybe this could be configurable?
		 */
		if (!config.disableHitpointOverlay() && hitpointTotalBelowThreshold()) {
			return config.getHitpointOverlayColor();
		}

		/* Return transparent overlay if we somehow get here */
		return new Color(0.0f, 0.0f, 0.0f, 0.0f);
	}

	public boolean hitpointTotalBelowThreshold()  {
		return isClientReady() && client.getBoostedSkillLevel(Skill.HITPOINTS) < config.getHitpointThreshold();
	}

	public boolean isClientReady() {
		return client.getGameState() == GameState.LOGGED_IN && client.getLocalPlayer() != null;
	}
}
