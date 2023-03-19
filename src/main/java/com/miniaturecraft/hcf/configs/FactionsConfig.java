package com.miniaturecraft.hcf.configs;

import com.miniaturecraft.hcf.HCF;
import com.miniaturecraft.hcf.objects.Faction;
import com.miniaturecraft.hcf.objects.FactionParticipator;
import com.miniaturecraft.hcf.enums.Role;
import com.miniaturecraft.miniaturecore.interfaces.Config;
import com.miniaturecraft.miniaturecore.objects.UniqueList;
import org.bukkit.entity.Player;

import java.util.UUID;

@Config(name = "factions")
public class FactionsConfig {

  // ----------------------------------------- //
  // FIELDS
  // ----------------------------------------- //

  public UniqueList<Faction> factions = UniqueList.create();
  public UniqueList<FactionParticipator> factionParticipators = UniqueList.create();

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
                faction.getMembers().stream().anyMatch(member -> member.getId().equals(uuid)))
        .findFirst()
        .orElse(null);
  }

  /** Creates system factions */
  public void createSystemFactions() {
    if (factions.add(Faction.create(HCF.WILDERNESS_ID, null, "Wilderness", "The Wilderness"))
        || factions.add(Faction.create(HCF.WARZONE_ID, null, "Warzone", "Not a safe place to be."))
        || factions.add(Faction.create(HCF.SAFEZONE_ID, null, "Safezone", "A safe place to be."))
        || factions.add(Faction.create(HCF.ROAD_ID, null, "Road", "A place to walk."))) {
      HCF.get().save(this);
    }
  }

  /**
   * Creates a faction
   *
   * @param leader The leader of the faction (null if system faction)
   * @param name The name of the faction
   * @param description The description of the faction
   */
  public Faction createFaction(UUID leader, String name, String description) {
    final Faction faction = Faction.create(factions.size() + 1, leader, name, description);

    if (leader == null) {
      faction.setSystemFaction(true);
    }

    factions.add(faction);

    return faction;
  }

  /**
   * Deletes a faction
   *
   * @param faction The faction to delete
   */
  public void deleteFaction(Faction faction) {
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
        FactionParticipator.create(playerId, player.getName(), wilderness, Role.MEMBER);
    if (factionParticipators.add(newParticipator)) {
      wilderness.getMembers().add(newParticipator);
    }
  }
}
