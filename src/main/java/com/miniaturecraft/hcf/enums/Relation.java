package com.miniaturecraft.hcf.enums;

import org.bukkit.ChatColor;

public enum Relation {
  NEUTRAL(4, "Neutral", ChatColor.WHITE),
  ALLY(3, "Ally", ChatColor.LIGHT_PURPLE),
  TRUCE(2, "Truce", ChatColor.AQUA),
  ENEMY(1, "Enemy", ChatColor.RED),
  FACTION(0, "Faction", ChatColor.GREEN);

  // ----------------------------------------- //
  // FIELDS
  // ----------------------------------------- //

  private final int value;
  private final String name;
  private final ChatColor colour;

  Relation(int value, String name, ChatColor colour) {
    this.value = value;
    this.name = name;
    this.colour = colour;
  }

  /**
   * Is a relation more than another.
   *
   * @param relation the relation to compare to
   * @return true if this relation is more than the other
   */
  public boolean isAtLeast(Relation relation) {
    return value >= relation.value;
  }

  /**
   * Is a relation less than another.
   *
   * @param relation the relation to compare to
   * @return true if this relation is less than the other
   */
  public boolean isAtMost(Relation relation) {
    return value <= relation.value;
  }

  /**
   * Is a relation above another.
   *
   * @param relation the relation to compare to
   * @return true if this relation is above the other
   */
  public boolean isAbove(Relation relation) {
    return value > relation.value;
  }

  /**
   * Is a relation below another.
   *
   * @param relation the relation to compare to
   * @return true if this relation is below the other
   */
  public boolean isBelow(Relation relation) {
    return value < relation.value;
  }

  /**
   * Are we neutral to the other relation
   *
   * @return true if we are neutral
   */
  public boolean isNeutral() {
    return this == NEUTRAL;
  }

  /** Returns the value of the relation. */
  public int getValue() {
    return value;
  }

  /** Returns the "pretty" name of the relation. */
  public String getName() {
    return name;
  }

  /** Returns the colour of the relation. */
  public ChatColor getColour() {
    return colour;
  }
}
