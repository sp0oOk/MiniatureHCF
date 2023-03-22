package com.miniaturecraft.hcf.interfaces;

import com.miniaturecraft.hcf.enums.Role;
import com.miniaturecraft.hcf.objects.Faction;
import com.miniaturecraft.hcf.objects.FactionParticipator;
import com.miniaturecraft.hcf.enums.Relation;
import com.miniaturecraft.miniaturecore.objects.MiniatureMap;
import com.miniaturecraft.miniaturecore.objects.Pair;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IFaction {

  int getId();

  double getDTR();

  Optional<Player> getLeader();

  Optional<FactionParticipator> getFLeader();

  String getName();

  String description();

  void disband(String leader);

  int getBalance();

  void setBalance(int balance);

  void addBalance(int balance);

  void removeBalance(int balance);

  int getPoints();

  void setPoints(int points);

  void addPoints(int points);

  void removePoints(int points);

  int getKothsCaptured();

  void setKothsCaptured(int kothsCaptured);

  void addKothsCaptured(int kothsCaptured);

  void removeKothsCaptured(int kothsCaptured);

  double getCrystals();

  void setCrystals(double crystals);

  void addCrystals(double crystals);

  void removeCrystals(double crystals);

  MiniatureMap<Integer, Relation> getRelations();

  List<UUID> getMembersUUID();

  List<Pair<UUID, Role>> getMembers();

  List<FactionParticipator> getFMembers();

  List<FactionParticipator> getOnlineMembers();

  boolean addMember(FactionParticipator participator, Role role);

  boolean removeMember(FactionParticipator participator);

  int getMaxMembers();

  boolean isRaidable();

  Relation getRelationTo(Faction faction);

  Relation getRelationTo(FactionParticipator participator);

  boolean isSystemFaction();

  boolean leave(FactionParticipator participator);
}
