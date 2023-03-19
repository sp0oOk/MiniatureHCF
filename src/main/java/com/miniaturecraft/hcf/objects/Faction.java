package com.miniaturecraft.hcf.objects;

import com.miniaturecraft.hcf.HCF;
import com.miniaturecraft.hcf.enums.Relation;
import com.miniaturecraft.hcf.interfaces.IFaction;
import com.miniaturecraft.hcf.interfaces.IFactionParticipator;
import com.miniaturecraft.miniaturecore.objects.MiniatureMap;
import com.miniaturecraft.miniaturecore.objects.UniqueList;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Data(staticConstructor = "create")
public class Faction implements IFaction {

  // ----------------------------------------- //
  // FIELDS
  // ----------------------------------------- //

  private final int id;
  private final UUID leader;
  private final String name;
  private final String description;
  private List<FactionParticipator> members = UniqueList.create();
  private MiniatureMap<Integer, Relation> relations = MiniatureMap.create();
  private double dtr;
  private boolean systemFaction;

  /** Returns the faction ID. */
  @Override
  public int getId() {
    return id;
  }

  /** Returns the faction DTR. */
  @Override
  public double getDTR() {
    return dtr;
  }

  /** Returns the faction leader. */
  @Override
  public Optional<Player> getLeader() {
    return Optional.ofNullable(Bukkit.getPlayer(leader));
  }

  /** Returns the faction name. */
  @Override
  public String getName() {
    return name;
  }

  /** Returns the faction description. */
  @Override
  public String description() {
    return description == null ? "No description" : description;
  }

  /** Returns the faction relations. */
  @Override
  public MiniatureMap<Integer, Relation> getRelations() {
    return relations;
  }

  /** Returns the faction members. */
  @Override
  public List<FactionParticipator> getMembers() {
    return members;
  }

  /** Returns the faction online members. */
  @Override
  public List<FactionParticipator> getOnlineMembers() {
    return members.stream().filter(IFactionParticipator::isOnline).collect(Collectors.toList());
  }

  /** Returns the faction max members. */
  @Override
  public int getMaxMembers() {
    return HCF.get().getConf().maxFactionMembers;
  }

  /** Returns true if the faction is raidable. */
  @Override
  public boolean isRaidable() {
    return getDTR() <= 0.0D && !isSystemFaction();
  }

  /**
   * Returns the relation to the faction.
   *
   * @param faction the faction to check
   * @return the relation
   */
  @Override
  public Relation getRelationTo(Faction faction) {
    return getRelations().getOrDefault(faction.getId(), Relation.NEUTRAL);
  }

  /**
   * Returns the relation to a faction participator.
   *
   * @param participator The faction participator.
   * @return The relation.
   */
  @Override
  public Relation getRelationTo(FactionParticipator participator) {
    return participator.getFaction().map(i -> i.getRelationTo(this)).orElse(Relation.NEUTRAL);
  }

  /** Returns true if the faction is a system faction. */
  @Override
  public boolean isSystemFaction() {
    return id == HCF.WARZONE_ID || id == HCF.SAFEZONE_ID || id == HCF.ROAD_ID || systemFaction;
  }
}
