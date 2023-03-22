package com.miniaturecraft.hcf.configs;

import com.miniaturecraft.hcf.HCF;
import com.miniaturecraft.hcf.enums.Role;
import com.miniaturecraft.hcf.objects.Faction;
import com.miniaturecraft.hcf.objects.FactionParticipator;
import com.miniaturecraft.miniaturecore.interfaces.Config;
import com.miniaturecraft.miniaturecore.objects.MiniatureList;
import org.bukkit.entity.Player;

import java.util.UUID;

@Config(name = "factions")
public class FactionsConfig {

  // ----------------------------------------- //
  // FIELDS
  // ----------------------------------------- //

  public MiniatureList<Faction> factions = MiniatureList.create();
  public MiniatureList<FactionParticipator> factionParticipators = MiniatureList.create();

  /**
   * Get a faction by its name("tag")
   *
   * @param name The name of the faction
   * @return The faction or null if not found
   */
  public Faction getFaction(String name) {
    return factions.stream()
        .filter(faction -> faction.getName().equalsIgnoreCase(name))
        .findFirst()
        .orElse(null);
  }

  /**
   * Get a faction by its id
   *
   * @param id The id of the faction
   * @return The faction or null if not found
   */
  public Faction getFaction(int id) {
    return factions.stream().filter(faction -> faction.getId() == id).findFirst().orElse(null);
  }

  /**
   * Get a faction by a player in it
   *
   * @param uuid The uuid of the player
   * @return The faction or null if not found
   */
  public Faction getFactionByMember(UUID uuid) {
    return factions.stream()
        .filter(
            faction ->
                faction.getFMembers().stream().anyMatch(member -> member.getId().equals(uuid)))
        .findFirst()
        .orElse(null);
  }

  /** Creates system factions */
  public void createSystemFactions() {
    int addedCount = 0;
    final HCFConfig config = HCF.get().getConf();
    for (int systemFaction :
        MiniatureList.create(HCF.WILDERNESS_ID, HCF.SAFEZONE_ID, HCF.ROAD_ID, HCF.WARZONE_ID)) {
      Faction faction = getFaction(systemFaction);
      if (faction == null) {
        if (systemFaction == HCF.WILDERNESS_ID) {
          faction =
              new Faction(
                  systemFaction, null, config.wildernessName, config.wildernessDescription, true);
        } else if (systemFaction == HCF.SAFEZONE_ID) {
          faction =
              new Faction(
                  systemFaction, null, config.safezoneName, config.safezoneDescription, true);
        } else if (systemFaction == HCF.ROAD_ID) {
          faction = new Faction(systemFaction, null, config.roadName, config.roadDescription, true);
        } else if (systemFaction == HCF.WARZONE_ID) {
          faction =
              new Faction(systemFaction, null, config.warzoneName, config.warzoneDescription, true);
        }
        factions.add(faction);
        addedCount++;
      }
    }

    if (addedCount > 0) {
      HCF.get().save(this);
      HCF.get().log("<i>Added <h>" + addedCount + " <i>missing system factions.");
    }
  }

  /**
   * Creates a faction
   *
   * @param leader The leader of the faction (null if system faction)
   * @param name The name of the faction
   * @param description The description of the faction
   */
  public Faction createFaction(UUID leader, String name, String description, boolean system) {
    final Faction faction = new Faction(factions.size() + 1, leader, name, description, system);
    factions.add(faction);
    return faction;
  }

  /**
   * Deletes a faction
   *
   * @param faction The faction to delete
   * @param save Whether to save the config (recommended as if server crashes, the faction will be
   *     back if not saved)
   */
  public void deleteFaction(Faction faction, boolean save) {
    factions.remove(faction);
  }

  /**
   * Get a faction participator by its uuid
   *
   * @param uuid The uuid of the participator
   * @return The participator or null if not found
   */
  public FactionParticipator getPlayer(UUID uuid) {
    return factionParticipators.stream()
        .filter(participator -> participator.getId().equals(uuid))
        .findFirst()
        .orElse(null);
  }

  /**
   * Get a faction participator by player
   *
   * @param player The player
   * @return The participator or null if not found
   */
  public FactionParticipator getPlayer(Player player) {
    return getPlayer(player.getUniqueId());
  }

  /**
   * Initializes a faction participator, called when a player joins
   *
   * @param player The player
   */
  public void initializePlayer(Player player) {
    final Faction wilderness = getFaction(HCF.WILDERNESS_ID);
    UUID playerId = player.getUniqueId();

    if (factionParticipators.stream()
        .anyMatch(participator -> participator.getId().equals(playerId))) {
      return;
    }

    FactionParticipator newParticipator =
        new FactionParticipator(playerId, player.getName(), HCF.WILDERNESS_ID);
    if (factionParticipators.add(newParticipator)) {
      wilderness.addMember(newParticipator, Role.MEMBER);
    }
  }
}
