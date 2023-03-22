package com.miniaturecraft.hcf.listeners;

import com.miniaturecraft.hcf.HCF;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJQListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    // Initialize Faction Participant
    HCF.get().getFactionsHandler().initializePlayer(event.getPlayer());
    // Show scoreboard
    HCF.get()
        .getScoreboardManager()
        .showScoreboard(event.getPlayer(), HCF.get().getConf().defaultScoreboard);
  }

  @EventHandler
  public void onLeave(PlayerQuitEvent event) {
    HCF.get().getScoreboardManager().hideScoreboard(event.getPlayer());
  }
}
