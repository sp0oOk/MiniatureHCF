package com.github.spook.hcf.nms;

import org.bukkit.entity.Player;

public interface NMS {

  void sendActionBar(Player player, String message);

  void sendTitle(Player player, String header, String footer, int fadeIn, int stay, int fadeOut);
}
