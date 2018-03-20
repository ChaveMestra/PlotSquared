package com.github.intellectualcrafters.plotsquared.bukkit.object;

import com.github.intellectualcrafters.plotsquared.plot.object.OfflinePlotPlayer;
import java.util.UUID;
import org.bukkit.OfflinePlayer;

public class BukkitOfflinePlayer implements OfflinePlotPlayer {

  public final OfflinePlayer player;

  /**
   * Please do not use this method. Instead use BukkitUtil.getPlayer(Player), as it caches player
   * objects.
   */
  public BukkitOfflinePlayer(OfflinePlayer player) {
    this.player = player;
  }

  @Override
  public UUID getUUID() {
    return this.player.getUniqueId();
  }

  @Override
  public long getLastPlayed() {
    return this.player.getLastPlayed();
  }

  @Override
  public boolean isOnline() {
    return this.player.isOnline();
  }

  @Override
  public String getName() {
    return this.player.getName();
  }
}
