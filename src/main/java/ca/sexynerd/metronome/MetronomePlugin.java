package ca.sexynerd.metronome;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Binder;
import com.google.inject.Provides;
import javax.inject.Inject;
import javax.sound.midi.*;

import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.ConfigChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.Overlay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

@PluginDescriptor(
	name = "Metronome"
)
public class MetronomePlugin extends Plugin implements MetaEventListener {

	private static final Logger logger = LoggerFactory.getLogger(MetronomePlugin.class);
    private Sequencer sequencer;

    @Inject
	Client client;

	@Inject
	MetronomeOverlay overlay;

	@Inject
	MetronomeConfiguration config;

	@Override
	protected void startUp() throws Exception {
        sequencer = MidiSystem.getSequencer();
        logger.info("loaded metronome plugin");
	}

	@Override
	protected void shutDown() throws Exception {
        logger.info("destroyed metronome plugin");
	}

	@Override
	public void configure(Binder binder) {
		binder.bind(MetronomeOverlay.class);
	}

	@Override
	public Overlay getOverlay() {
		return overlay;
	}

	@Provides
	MetronomeConfiguration provideConfig(ConfigManager configManager) {
		return configManager.getConfig(MetronomeConfiguration.class);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event) {
		if(event.getKey().equals("enabled")) {
            boolean enabled = Boolean.parseBoolean(event.getNewValue());
            if (enabled) {
                onEnabled();
            } else {
                onDisabled();
            }
        }
	}

	private void onEnabled() {
		logger.info("Metronome started!");
		start();
	}

    private void onDisabled() {
		logger.info("Metronome stopped!");
		stop();
	}

    @Override
    public void meta(MetaMessage meta) {
        if(client.getGameState() != GameState.LOGGED_IN || !config.enabled()) {
            stop();
            return;
        }
        //47 = END OF TRACK
        if (meta.getType() == 47) {
            if (sequencer != null && sequencer.isOpen()) {
                MetronomeOverlay.switchColour = !MetronomeOverlay.switchColour;
                sequencer.setTickPosition(0);
                sequencer.start();
            }
        }
    }

    public void start() {
        try {
            sequencer.setSequence(getClass().getClassLoader().getResourceAsStream(config.sound().getPath()));
            sequencer.open();
        } catch (IOException | InvalidMidiDataException | MidiUnavailableException e) {
            logger.error(e.getCause().getMessage());
        }
        sequencer.addMetaEventListener(this);
        sequencer.start();
    }

    public void stop() {
        if (sequencer != null && sequencer.isOpen()) {
            sequencer.stop();
            sequencer.close();
        }
    }
}
