package com.github.spook.hcf.factions;

import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
@SuppressWarnings("unused")
public enum Role {
  LEADER(0, "Leader", ChatColor.RED),
  OFFICER(1, "Officer", ChatColor.GOLD),
  MEMBER(2, "Member", ChatColor.YELLOW);

  private final int value;
  private final String name;
  private final ChatColor colour;

  Role(int value, String name, ChatColor colour) {
    this.value = value;
    this.name = name;
    this.colour = colour;
  }

  public boolean isAtLeast(Role role) {
    return value >= role.value;
  }

  public boolean isAtMost(Role role) {
    return value <= role.value;
  }

  public boolean isAbove(Role role) {
    return value > role.value;
  }

  public boolean isBelow(Role role) {
    return value < role.value;
  }

  public boolean isLeader() {
    return this == LEADER;
  }

  public static boolean isHigher(Role role, Role other) {
    return role.getValue() < other.getValue();
  }

  public static Role getRole(IFactionParticipator participator) {
    return participator.getRole();
  }
}
