package ca.sexynerd.metronome;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup(
	keyName = "metronome",
	name = "Metronome",
	description = "Configuration for the metronome plugin"
)
public interface MetronomeConfiguration extends Config {

	@ConfigItem(
			keyName = "color1",
			name = "Primary color",
			description = "",
			position = 1
	)
	default Color getFirstColor() {
		return Color.RED;
	}

	@ConfigItem(
			keyName = "color2",
			name = "Secondary color",
			description = "",
			position = 2
	)
	default Color getSecondColor() {
		return Color.BLUE;
	}


	@ConfigItem(
			keyName = "sound",
			name = "Sound",
			description = "What kind of sound the midi player makes",
			position = 3
	)
	default Sounds sound() {
		return Sounds.JINGLE_BELLS;
	}
	@ConfigItem(
			keyName = "overlay",
			name = "Enable Overlay",
			description = "Enable Overlay",
			position = 4
	)
	default boolean overlayEnabled() {
		return true;
	}


	@ConfigItem(
		keyName = "enabled",
		name = "Enable",
		description = "Configures whether the overlay is enabled",
		position = 5
	)
	default boolean enabled() {
		return false;
	}
}

