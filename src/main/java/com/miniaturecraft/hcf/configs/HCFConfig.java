package com.miniaturecraft.hcf.configs;

import com.miniaturecraft.hcf.scoreboards.ScoreboardEntry;
import com.miniaturecraft.miniaturecore.interfaces.Config;
import com.miniaturecraft.miniaturecore.objects.MiniatureList;

@Config(name = "lang")
@SuppressWarnings("unused")
public class HCFConfig {

  // ----------------------------------------- //
  // GENERAL OPTIONS
  // ----------------------------------------- //

  public String teleportationMoveMessage = "&cYou have moved, teleportation cancelled.";

  public String factionHomeSet = "&aYou have set your faction home to your current location.";
  public String factionHomeNotSet = "&cYour faction does not have a home set.";
  public String factionHomeTeleporting =
      "&eTeleporting to your faction home in &6{teleport_time} &eseconds.";
  public String factionHomeTeleported = "&aYou have been teleported to your faction home.";
  public int factionHomeTeleportSeconds = 5;

  public String factionDoesNotExist = "&cThat faction doesn't exist!";
  public String factionExists = "&cA faction with that name already exists.";
  public String factionCreated = "&aYou have created the faction &e{faction_name}&a.";
  public String factionAlreadyInFaction = "&cYou are already in a faction.";
  public String fBypassMessage = "&eYou {is_bypassing} &eadamin bypassing.";

  // ----------------------------------------- //
  // INVITATION OPTIONS
  // ----------------------------------------- //
  public int factionExpireInvitationSeconds = 60 * 60 * 24 * 7;

  // ----------------------------------------- //
  // DEFAULT SYSTEM FACTIONS (DO NOT TOUCH)
  // ----------------------------------------- //
  public String warzoneName = "Warzone";
  public String warzoneDescription = "Not the safest place to be.";
  public String safezoneName = "Safezone";
  public String safezoneDescription = "The safest place to be.";
  public String wildernessName = "Wilderness";
  public String wildernessDescription = "A place for everyone.";
  public String roadName = "Road";
  public String roadDescription = "/road for more info.";

  // ----------------------------------------- //
  // FACTION NAME FEATURE
  // ----------------------------------------- //

  public String factionNameRegex = "[a-zA-Z0-9]{3,16}";
  public String factionNameRegexMessage =
      "&cFaction names must be between 3 and 16 characters long.";
  public boolean factionNameBlacklistEnabled = true;
  public String factionNameBlacklistMessage =
      "&cFaction names cannot contain some of the words you provided.";
  public MiniatureList<String> factionNameBlacklist =
      MiniatureList.create("bitch", "nigger", "nigga", "cunt");

  public int maxFactionMembers = 5;

  // ----------------------------------------- //
  // FACTION LEAVE OPTIONS
  // ----------------------------------------- //

  public String fLeave = "&eYou have left the team &6{faction_name}&e.";
  public String fLeaveOther = "&e{player_name} &ehas left the team &6{faction_name}&e.";
  public boolean fLeaveBroadcast = true;
  public MiniatureList<String> fLeaveCommandFailed =
      MiniatureList.create(
          "&cYou cannot leave your current team!",
          "&7Note: This is either because you are the leader, or you are the only member.");

  // ----------------------------------------- //
  // FACTION WHO OPTIONS
  // ----------------------------------------- //

  public MiniatureList<String> fWhoMessage =
      MiniatureList.create(
          "&7&m-------------------------------------",
          "&9{faction_name} &7[{faction_online_members}/{faction_max_members}] &3- &eHQ: &6{faction_home}",
          "&eLeader: {faction_rel_color}{faction_leader}&e[{faction_kdr_colored_leader}&e]",
          "&eBalance: &9${faction_balance}",
          "&eDeath until Raidable: {faction_dtr_colored_pretty}",
          "&ePoints: &c{faction_points}",
          "&eKOTH Captures: &c{faction_koth_captures}",
          "&eCrystals: &c{faction_crystals}",
          "&7&m-------------------------------------");

  // ----------------------------------------- //
  // SCOREBOARD OPTIONS
  // ----------------------------------------- //

  public ScoreboardEntry defaultScoreboard =
      ScoreboardEntry.of(
          "&6&lHCF",
          MiniatureList.create(
              "&7&m------------------",
              "&eOnline: &6?",
              "&eFactions: &6?",
              "&ePlayers: &6?",
              "&7&m------------------"));

  // ----------------------------------------- //
  // OTHER OPTIONS
  // ----------------------------------------- //
}
