package com.miniaturecraft.hcf.listeners;

import com.miniaturecraft.hcf.HCF;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMovingListener implements Listener {

  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onPlayerMove(PlayerMoveEvent event) {
    Player player = event.getPlayer();
    if (player.hasMetadata("teleporting_queue")
        && Bukkit.getScheduler()
            .isCurrentlyRunning(player.getMetadata("teleporting_queue").get(0).asInt())) {
      player.removeMetadata("teleporting_queue", HCF.get());
      player.sendMessage(HCF.get().getConf().teleportationMoveMessage);
    }
  }
}
