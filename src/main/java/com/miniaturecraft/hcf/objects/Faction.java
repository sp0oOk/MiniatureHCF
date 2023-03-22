package com.miniaturecraft.hcf.objects;

import com.miniaturecraft.hcf.HCF;
import com.miniaturecraft.hcf.enums.ClaimResult;
import com.miniaturecraft.hcf.enums.Relation;
import com.miniaturecraft.hcf.enums.Role;
import com.miniaturecraft.hcf.interfaces.IFaction;
import com.miniaturecraft.miniaturecore.objects.MiniatureList;
import com.miniaturecraft.miniaturecore.objects.MiniatureMap;
import com.miniaturecraft.miniaturecore.objects.Pair;
import com.miniaturecraft.miniaturecore.objects.QL;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class Faction implements IFaction {

  // ----------------------------------------- //
  // FIELDS
  // ----------------------------------------- //

  private int id;
  private UUID leader;
  private String name;
  private MiniatureList<QL> claims = MiniatureList.create();
  private QL home;
  private String description;
  private boolean systemFaction;
  private int balance;
  private int points;
  private int kothsCaptured;
  private double crystals;
  private MiniatureList<PendingInvitation> pendingInvitations = MiniatureList.create();
  private MiniatureList<Pair<UUID, Role>> members = MiniatureList.create();
  private MiniatureMap<Integer, Relation> relations = MiniatureMap.create();
  private double dtr;

  public Faction(int id, UUID leader, String name, String description, boolean system) {
    this.id = id;
    this.leader = leader;
    this.name = name;
    this.description = description;
    this.systemFaction = system;
    this.balance = 0;
    this.points = 0;
    this.kothsCaptured = 0;
    this.crystals = 0;
    this.dtr = 0;

    if (leader != null) {
      final FactionParticipator participator = HCF.get().getFactionsHandler().getPlayer(leader);
      participator.setFaction(id);
      members.add(Pair.of(leader, Role.LEADER));
    }
  }

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

  /** Returns the faction leader as participator. */
  @Override
  public Optional<FactionParticipator> getFLeader() {
    return getLeader().map(m -> HCF.get().getFactionsHandler().getPlayer(m.getUniqueId()));
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

  /** Returns the faction claims. */
  @Override
  public MiniatureList<QL> getClaims() {
    return claims;
  }

  /**
   * Checks if a location is claimed by the faction.
   *
   * @param location The location to check.
   * @return True if the location is claimed by the faction.
   */
  @Override
  public boolean isClaimed(QL location) {
    // TODO: implement
    return false;
  }

  /**
   * Attempt to claim a location for the faction.
   *
   * @param location The location to claim.
   * @param factionParticipator The faction participator claiming the location.
   * @return The result of the claim.
   */
  @Override
  public ClaimResult claim(QL location, FactionParticipator factionParticipator) {
    // TODO implement
    return null;
  }

  /** Returns the faction home. */
  @Override
  public Optional<QL> getHome() {
    return Optional.ofNullable(home);
  }

  @Override
  public void disband(String leader) {
    for (FactionParticipator participator : getFMembers()) {
      if (!removeMember(participator)) {
        HCF.get()
            .log(
                "<yellow>Failed to remove member <white>"
                    + participator.getName()
                    + "<yellow> from faction <white>"
                    + getName()
                    + "<yellow>.");
      }
    }
    HCF.get().getFactionsHandler().deleteFaction(this, true);
    HCF.get()
        .log(
            "<yellow>Faction <white>"
                + getName()
                + "<yellow> has been disbanded by <white>"
                + leader
                + "<yellow>.");
  }

  /** Returns the faction balance. */
  @Override
  public int getBalance() {
    return balance;
  }

  /** Adds the specified amount to the faction balance. */
  @Override
  public void addBalance(int balance) {
    this.balance += balance;
  }

  /** Removes balance from the faction. */
  @Override
  public void removeBalance(int balance) {
    this.balance -= balance;
  }

  /** Returns the faction points. */
  @Override
  public int getPoints() {
    return points;
  }

  /** Sets the faction points. */
  @Override
  public void setPoints(int points) {
    this.points = points;
  }

  /** Adds points to the faction. */
  @Override
  public void addPoints(int points) {
    this.points += points;
  }

  /** Removes points from the faction. */
  @Override
  public void removePoints(int points) {
    this.points -= points;
  }

  /** Returns the amount of KOTHs captured. */
  @Override
  public int getKothsCaptured() {
    return kothsCaptured;
  }

  /** Sets the koths captured. */
  @Override
  public void setKothsCaptured(int kothsCaptured) {
    this.kothsCaptured = kothsCaptured;
  }

  /** Adds koths captured. */
  @Override
  public void addKothsCaptured(int kothsCaptured) {
    this.kothsCaptured += kothsCaptured;
  }

  /** Removes X koths captured. */
  @Override
  public void removeKothsCaptured(int kothsCaptured) {
    this.kothsCaptured -= kothsCaptured;
  }

  /** Returns the faction crystals. */
  @Override
  public double getCrystals() {
    return crystals;
  }

  /** Sets the faction crystals. */
  @Override
  public void setCrystals(double crystals) {
    this.crystals = crystals;
  }

  /** Add crystals to the faction. */
  @Override
  public void addCrystals(double crystals) {
    this.crystals += crystals;
  }

  /** Remove crystals from the faction. */
  @Override
  public void removeCrystals(double crystals) {
    this.crystals -= crystals;
  }

  /** Returns the faction relations. */
  @Override
  public MiniatureMap<Integer, Relation> getRelations() {
    return relations;
  }

  /** Returns the faction pending invitations. */
  @Override
  public MiniatureList<PendingInvitation> getInvitations() {
    return pendingInvitations;
  }

  /** Returns the amount of expired invitations. */
  @Override
  public int removeExpiredInvitations() {
    int removed = 0;
    for (PendingInvitation pendingInvitation : pendingInvitations) {
      if (pendingInvitation.isExpired()) {
        pendingInvitations.remove(pendingInvitation);
        removed++;
      }
    }
    return removed;
  }

  /** Returns the faction members UUIDs. */
  @Override
  public List<UUID> getMembersUUID() {
    return members.stream().map(Pair::getFirst).collect(Collectors.toList());
  }

  /** Returns the faction members. */
  @Override
  public List<Pair<UUID, Role>> getMembers() {
    return members;
  }

  /** Returns the faction members as participators. */
  @Override
  public List<FactionParticipator> getFMembers() {
    return members.stream()
        .map(m -> HCF.get().getFactionsHandler().getPlayer(m.first))
        .collect(Collectors.toList());
  }

  /** Returns the faction online members. */
  @Override
  public List<FactionParticipator> getOnlineMembers() {
    return getFMembers().stream()
        .filter(FactionParticipator::isOnline)
        .collect(Collectors.toList());
  }

  /**
   * Adds a member to the faction.
   *
   * @param participator The participator to add.
   * @param role The role of the joining member.
   * @return True if the member was added.
   */
  @Override
  public boolean addMember(FactionParticipator participator, Role role) {
    if (getMembersUUID().contains(participator.getId())) {
      return false;
    }

    if (participator.getFaction().isPresent()
        && !participator.getFaction().get().removeMember(participator)) {
      return false;
    }

    participator.setFaction(id);

    return members.add(Pair.of(participator.getId(), role));
  }

  /**
   * Removes the specified member from the faction.
   *
   * @param participator The member to remove.
   * @return True if the member was removed.
   */
  @Override
  public boolean removeMember(FactionParticipator participator) {
    if (!getMembersUUID().contains(participator.getId())) {
      return false;
    }

    participator.setFaction(HCF.WILDERNESS_ID);

    final Pair<UUID, Role> pair =
        members.stream().filter(m -> m.first.equals(participator.getId())).findFirst().orElse(null);

    if (pair == null) {
      return false;
    }

    return members.remove(pair);
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
    return systemFaction;
  }

  /**
   * Attempt to leave the faction.
   *
   * @param participator The participator.
   * @return True if the participator left the faction.
   */
  @Override
  public boolean leave(FactionParticipator participator) {

    if (isSystemFaction() && !participator.isBypassing()
        || getFLeader().isPresent() && getFLeader().get().getId().equals(participator.getId())
        || getId() == HCF.WILDERNESS_ID) {
      return false;
    }

    final Faction wilderness = HCF.get().getFactionsHandler().getFaction(HCF.WILDERNESS_ID);
    participator.setFaction(wilderness.getId());
    wilderness.addMember(participator, Role.MEMBER);

    final Pair<UUID, Role> pair =
        members.stream().filter(m -> m.first.equals(participator.getId())).findFirst().orElse(null);

    if (pair == null) {
      return false;
    }

    return members.remove(pair);
  }
}
