package com.miniaturecraft.hcf.objects;

import com.miniaturecraft.hcf.HCF;
import com.miniaturecraft.hcf.interfaces.IFactionParticipator;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class FactionParticipator implements IFactionParticipator {

  // ----------------------------------------- //
  // FIELDS
  // ----------------------------------------- //

  private UUID id;
  private String name;
  private int faction;
  private int kills;
  private int deaths;
  private int assists;
  private boolean bypassing;

  public FactionParticipator(UUID uuid, String name, int faction) {
    this.id = uuid;
    this.name = name;
    this.faction = faction;
    this.kills = 0;
    this.deaths = 0;
    this.assists = 0;
    this.bypassing = false;
  }

  /** Returns the UUID of the participator. */
  @Override
  public UUID getId() {
    return id;
  }

  /** Returns the name of the participator. */
  @Override
  public String getName() {
    return name;
  }

  /** Returns the faction of the participator. */
  @Override
  public Optional<Faction> getFaction() {
    return Optional.ofNullable(HCF.get().getFactionsHandler().getFaction(faction));
  }

  /** Returns whether the participator is online. */
  @Override
  public boolean isOnline() {
    final Player player = Bukkit.getPlayer(id);
    return player != null && player.isOnline();
  }

  /** Returns whether the participator is bypassing. */
  @Override
  public boolean isBypassing() {
    return bypassing;
  }

  /** Changes the bypassing state of the participator and returns. */
  @Override
  public boolean changeBypassing() {
    bypassing = !bypassing;
    return bypassing;
  }

  /** Returns the kills of the participator. */
  @Override
  public int getKills() {
    return kills;
  }

  /** Returns the deaths of the participator. */
  @Override
  public int getDeaths() {
    return deaths;
  }

  /** Returns the assists of the participator. */
  @Override
  public int getAssists() {
    return assists;
  }

  /** Returns the KDR of the participator. */
  @Override
  public float getKDR() {
    if (deaths == 0) {
      return kills;
    }

    final double KDR = (double) kills / deaths;

    return Math.round(KDR * 100.0) / 100.0F;
  }
}
