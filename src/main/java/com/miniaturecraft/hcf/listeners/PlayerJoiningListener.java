package com.miniaturecraft.hcf.listeners;

import com.miniaturecraft.hcf.HCF;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoiningListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    HCF.get().getFactionsHandler().initializePlayer(event.getPlayer());
  }
}
