package com.miniaturecraft.hcf.tasks;

import com.miniaturecraft.hcf.HCF;
import com.miniaturecraft.miniaturecore.objects.QL;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportationTask extends BukkitRunnable {

  private final Player player;
  private final QL location;
  private final int waitTimeSeconds;
  private final String message;
  private int currentWaitTimeSeconds;

  public TeleportationTask(Player player, QL location, int waitTimeSeconds, String message) {
    this.player = player;
    this.location = location;
    this.waitTimeSeconds = waitTimeSeconds;
    this.message = message;
    this.currentWaitTimeSeconds = 0;
  }

  @Override
  public void run() {

    if (!player.isOnline() || player.isDead()) {
      cancel();
      return;
    }

    if (currentWaitTimeSeconds == 0) {
      player.setMetadata("teleporting_queue", new FixedMetadataValue(HCF.get(), getTaskId()));
    }

    currentWaitTimeSeconds++;

    if (currentWaitTimeSeconds >= waitTimeSeconds) {
      player.teleport(location.asBukkitLocation());
      if (message != null) {
        player.sendMessage(message);
      }
      cancel();
    }
  }
}
