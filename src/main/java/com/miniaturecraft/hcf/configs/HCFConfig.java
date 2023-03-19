package com.miniaturecraft.hcf.configs;

import com.google.common.collect.Lists;
import com.miniaturecraft.hcf.objects.Faction;
import com.miniaturecraft.hcf.objects.FactionParticipator;
import com.miniaturecraft.miniaturecore.interfaces.Config;
import com.miniaturecraft.miniaturecore.objects.MiniatureList;
import com.miniaturecraft.miniaturecore.objects.MiniatureMap;
import com.miniaturecraft.miniaturecore.objects.ParsedPlaceholders;
import org.bukkit.entity.Player;

import java.util.List;

@Config(name = "lang")
@SuppressWarnings("unused")
public class HCFConfig {

  // ----------------------------------------- //
  // FIELDS
  // ----------------------------------------- //

  public int maxFactionMembers = 5;

  public MiniatureMap<String, String> messages =
      MiniatureMap.create(
          "noFactionPresent", "&7You are not on a team!",
          "factionCreated", "&eTeam &6{faction_name} &ehas been &acreated &eby {player_name}&e");

  public List<String> fWhoMessage =
      Lists.newArrayList(
          "&7&m-------------------------------------",
          "&9{faction_name} &7[{online_members}/{max_members}] &3- &eHQ: &6{faction_home}",
          "&eLeader: {rel_color}{faction_leader}&e[{kdr_colored_leader}&e]",
          "&eBalance: &9${faction_balance}",
          "&eDeath until Raidable: {dtr_colored_pretty}",
          "&ePoints: &c{faction_points}",
          "&eKOTH Captures: &c{faction_koth_captures}",
          "&eCrystals: &c{faction_crystals}",
          "&7&m-------------------------------------");

  public MiniatureList<String> parseFWho(Faction faction, FactionParticipator viewer) {
    return ParsedPlaceholders.parse(
            fWhoMessage,
            k -> {
              switch (k) {
                case "faction_name":
                  return faction.getName();
                case "online_members":
                  return faction.getOnlineMembers().size() + "";
                case "max_members":
                  return faction.getMaxMembers() + "";
                case "rel_color":
                  return faction.getRelationTo(viewer).getColour() + "";
                case "faction_leader":
                  return faction.getLeader().map(Player::getName).orElse("Console");
                default:
                  return null;
              }
            })
        .unwrapAll();
  }
}
