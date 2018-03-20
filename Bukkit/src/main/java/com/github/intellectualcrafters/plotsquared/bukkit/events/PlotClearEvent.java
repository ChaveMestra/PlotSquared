package com.github.intellectualcrafters.plotsquared.bukkit.events;

import com.github.intellectualcrafters.plotsquared.plot.object.Plot;
import com.github.intellectualcrafters.plotsquared.plot.object.PlotId;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Called when a plot is cleared
 */
public class PlotClearEvent extends PlotEvent implements Cancellable {

  private static final HandlerList handlers = new HandlerList();
  private boolean cancelled;

  public PlotClearEvent(Plot plot) {
    super(plot);
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  /**
   * Get the PlotId.
   *
   * @return PlotId
   */
  public PlotId getPlotId() {
    return getPlot().getId();
  }

  /**
   * Get the world name.
   *
   * @return String
   */
  public String getWorld() {
    return getPlot().getWorldName();
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  @Override
  public boolean isCancelled() {
    return this.cancelled;
  }

  @Override
  public void setCancelled(boolean b) {
    this.cancelled = b;
  }
}
