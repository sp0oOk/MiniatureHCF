package com.github.spook.hcf.nms.v1_8;

import com.github.spook.hcf.nms.NMS;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class v1_8 implements NMS {

  @Override
  public void sendActionBar(Player player, String message) {
    PacketPlayOutChat chat =
        new PacketPlayOutChat(
            IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"), (byte) 2);
    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(chat);
  }

  @Override
  public void sendTitle(
      Player player, String header, String footer, int fadeIn, int stay, int fadeOut) {
    PacketPlayOutTitle packetOutTitle =
        new PacketPlayOutTitle(
            PacketPlayOutTitle.EnumTitleAction.TITLE,
            IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}"),
            fadeIn,
            stay,
            fadeOut);
    PacketPlayOutTitle packetOutSubtitle =
        new PacketPlayOutTitle(
            PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
            IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}"),
            fadeIn,
            stay,
            fadeOut);
    PacketPlayOutTitle packetOutTimes = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetOutTitle);
    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetOutSubtitle);
    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetOutTimes);
  }
}
