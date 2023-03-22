package com.miniaturecraft.hcf;

import com.miniaturecraft.hcf.objects.Faction;
import com.miniaturecraft.hcf.objects.FactionParticipator;
import com.miniaturecraft.miniaturecore.objects.ParsedPlaceholders;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Utils {

  /**
   * Color the KDR based on the value.
   *
   * @param kdr the KDR to color.
   * @return the color of the KDR.
   */
  public static ChatColor colorKDR(double kdr) {
    if (kdr >= 2.0) {
      return ChatColor.GREEN;
    } else if (kdr >= 1.0) {
      return ChatColor.YELLOW;
    } else if (kdr >= 0.5) {
      return ChatColor.GOLD;
    } else if (kdr >= 0.0) {
      return ChatColor.RED;
    } else {
      return ChatColor.DARK_RED;
    }
  }

  /**
   * Default mapper for placeholders.
   *
   * @param participator the participator
   * @return the string
   */
  public static ParsedPlaceholders getSelfPlaceholders(@Nullable FactionParticipator participator) {
    final ParsedPlaceholders parsedPlaceholders = ParsedPlaceholders.of();
    final Optional<Faction> faction =
        Optional.ofNullable(participator).flatMap(FactionParticipator::getFaction);

    parsedPlaceholders.map(
        s -> {
          switch (s) {
            case "player_name":
              return participator != null ? participator.getDisplayName() : "?";
            case "player_balance":
              return "0"; // TODO: Economy Provider, Vault or internal.
            case "player_kdr":
              return "0.00"; // TODO: KDR
            case "faction_name":
              return faction.map(Faction::getName).orElse("None");
            case "faction_online_members":
              return faction.map(m -> m.getOnlineMembers().size()).orElse(0) + "";
            case "faction_max_members":
              return faction.map(Faction::getMaxMembers).orElse(0) + "";
            case "faction_home":
              return "None"; // TODO: I want to complete custom location wrapper in core.
            case "faction_rel_color":
              return faction
                      .map(f -> f.getRelationTo(participator).getColour())
                      .orElse(ChatColor.WHITE)
                  + "";
            case "faction_leader":
              return faction
                  .map(f -> f.getLeader().map(Player::getName).orElse("Console"))
                  .orElse("Console");
            case "faction_kdr_colored_leader":
              return faction
                      .map(
                          f ->
                              f.getFLeader()
                                  .map(fp -> colorKDR(fp.getKDR()).toString() + fp.getKDR())
                                  .orElse("?"))
                      .orElse("?")
                  + "";
            case "faction_balance":
              return faction.map(Faction::getBalance).orElse(0) + ""; // TODO: Format
            case "faction_dtr_colored_pretty":
              return faction.map(Faction::getDTR).orElse(0.0D) + ""; // TODO: Format
            case "faction_points":
              return faction.map(Faction::getPoints).orElse(0) + "";
            case "faction_koth_captures":
              return faction.map(Faction::getKothsCaptured).orElse(0) + "";
            case "faction_crystals":
              return faction.map(Faction::getCrystals).orElse(0D) + "";
            case "is_bypassing":
              return participator != null && participator.isBypassing()
                  ? ChatColor.GREEN + "are now"
                  : ChatColor.RED + "are no longer";
            default:
              return null;
          }
        });

    return parsedPlaceholders;
  }
}
