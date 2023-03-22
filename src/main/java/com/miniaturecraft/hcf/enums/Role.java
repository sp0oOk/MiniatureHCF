package com.miniaturecraft.hcf.enums;

import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
@SuppressWarnings("unused")
public enum Role {
  LEADER(0, "Leader", ChatColor.RED),
  CAPTAIN(1, "Captain", ChatColor.DARK_RED),
  OFFICER(2, "Officer", ChatColor.GOLD),
  MEMBER(3, "Member", ChatColor.YELLOW),
  RECRUIT(4, "Recruit", ChatColor.GREEN);

  // ----------------------------------------- //
  // FIELDS
  // ----------------------------------------- //

  private final int value;
  private final String name;
  private final ChatColor colour;

  Role(int value, String name, ChatColor colour) {
    this.value = value;
    this.name = name;
    this.colour = colour;
  }

  /**
   * Is a role at least higher than another.
   *
   * @param role the role to compare to
   * @return true if this role is at least higher than the other
   */
  public boolean isAtLeast(Role role) {
    return value >= role.value;
  }

  /**
   * Is a role at lower than another.
   *
   * @param role the role to compare to
   * @return true if this role is at lower than the other
   */
  public boolean isAtMost(Role role) {
    return value <= role.value;
  }

  /**
   * Is a role above another.
   *
   * @param role the role to compare to
   * @return true if this role is above the other
   */
  public boolean isAbove(Role role) {
    return value > role.value;
  }

  /**
   * Is a role below another.
   *
   * @param role the role to compare to
   * @return true if this role is below the other
   */
  public boolean isBelow(Role role) {
    return value < role.value;
  }

  /** Is the role leader? */
  public boolean isLeader() {
    return this == LEADER;
  }

  /**
   * Is a role higher than another.
   *
   * @param role the role to compare to
   * @param other the other role to compare to
   * @return true if this role is higher than the other
   */
  public static boolean isHigher(Role role, Role other) {
    return role.getValue() < other.getValue();
  }
}
