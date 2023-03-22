package com.miniaturecraft.hcf.listeners;

import com.miniaturecraft.hcf.objects.MEnderPearl;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractingListener implements Listener {

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {

    if (!event.getAction().name().contains("RIGHT_CLICK")) {
      return;
    }

    if (!event.isCancelled()
        && event.getItem() != null
        && event.getItem().getType().equals(Material.ENDER_PEARL)) {
      final MEnderPearl pearl = new MEnderPearl(event.getPlayer());
      ((CraftWorld) event.getPlayer().getWorld()).getHandle().addEntity(pearl);
      event.setCancelled(true);
    }
  }
}
