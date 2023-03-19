package com.miniaturecraft.hcf.interfaces;

import com.miniaturecraft.hcf.objects.Faction;
import com.miniaturecraft.hcf.objects.FactionParticipator;
import com.miniaturecraft.hcf.enums.Relation;
import com.miniaturecraft.miniaturecore.objects.MiniatureMap;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public interface IFaction {

  int getId();

  double getDTR();

  Optional<Player> getLeader();

  String getName();

  String description();

  MiniatureMap<Integer, Relation> getRelations();

  List<FactionParticipator> getMembers();

  List<FactionParticipator> getOnlineMembers();

  int getMaxMembers();

  boolean isRaidable();

  Relation getRelationTo(Faction faction);

  Relation getRelationTo(FactionParticipator participator);

  boolean isSystemFaction();
}
