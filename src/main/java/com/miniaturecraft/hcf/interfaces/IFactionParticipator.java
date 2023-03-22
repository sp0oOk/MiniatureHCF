package com.miniaturecraft.hcf.interfaces;

import com.miniaturecraft.hcf.objects.Faction;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public interface IFactionParticipator {

  UUID getId();

  String getName();

  Optional<Faction> getFaction();

  boolean isOnline();

  Optional<Player> getBukkitPlayer();

  String getDisplayName();

  boolean isBypassing();

  boolean changeBypassing();

  int getKills();

  int getDeaths();

  int getAssists();

  float getKDR();
}
