package ca.sexynerd.metronome;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.annotation.Nullable;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;

public class MetronomeOverlay extends Overlay {

	private final Client client;
	private final MetronomeConfiguration config;
	public static boolean switchColour = false;
	private final BasicStroke stroke;
	private final int DIAMETER = 28;

	@Inject
	public MetronomeOverlay(@Nullable Client client, MetronomeConfiguration config) {
		setPosition(OverlayPosition.DYNAMIC);
		this.client = client;
		this.config = config;
		this.stroke = new BasicStroke(3);
	}

	@Override
	public Dimension render(Graphics2D graphics, java.awt.Point parent) {
		if(config.enabled() && config.overlayEnabled() && client.getGameState() == GameState.LOGGED_IN) {
			Widget widget = client.getWidget(WidgetInfo.QUICK_PRAYER_ORB);
			if(widget != null) {
				graphics.setStroke(stroke);
				graphics.setColor(switchColour ? config.getFirstColor() : config.getSecondColor());
				graphics.drawOval((int) widget.getBounds().getX() + DIAMETER - 5, (int) widget.getBounds().getY() - 4, DIAMETER, DIAMETER + 3);
			}
		}
		return null;
	}
}
