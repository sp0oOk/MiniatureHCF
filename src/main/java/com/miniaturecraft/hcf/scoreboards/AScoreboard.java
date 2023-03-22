package com.miniaturecraft.hcf.scoreboards;

import com.miniaturecraft.miniaturecore.objects.MiniatureList;
import com.miniaturecraft.miniaturecore.utils.Txt;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class AScoreboard {
  private final Player player;
  private final Scoreboard scoreboard;
  private Objective objective;
  private boolean isStarted;
  private final ScoreboardEntry scoreboardEntry;

  public AScoreboard(Player player, Scoreboard scoreboard, ScoreboardEntry scoreboardEntry) {
    this.player = player;
    this.scoreboard = scoreboard;
    this.scoreboardEntry = scoreboardEntry;
  }

  /** Get the player. */
  public Player getPlayer() {
    return player;
  }

  /**
   * Set scoreboard title.
   *
   * @param displayName The title to set.
   * @return The current scoreboard.
   */
  public AScoreboard setDisplayName(String displayName) {
    scoreboardEntry.displayName = displayName;
    return this;
  }

  /** Get scoreboard title. */
  public String getDisplayName() {
    return scoreboardEntry.displayName;
  }

  /**
   * Insert a line at X position in the scoreboard.
   *
   * @param string The line to insert.
   * @param i The position to insert.
   * @return The current scoreboard.
   */
  public AScoreboard insertLine(String string, int i) {
    scoreboardEntry.entries.add(i, string);
    return this;
  }

  /**
   * Adds a line to the scoreboard.
   *
   * @param string The line to add.
   * @return The current scoreboard.
   */
  public AScoreboard addLine(String string) {
    scoreboardEntry.entries.add(0, string);
    return this;
  }

  /** Get Entries */
  public MiniatureList<String> getEntries() {
    return scoreboardEntry.entries;
  }

  /** Load the scoreboard. */
  public void loadScoreboard() {
    if (isStarted) {
      updateScoreboard();
      return;
    }
    this.objective = scoreboard.registerNewObjective("Scoreboard", "dummy");
    this.objective.setDisplayName(Txt.parse(scoreboardEntry.displayName));
    for (int pos = 0; pos < scoreboardEntry.entries.size(); pos++) {
      this.objective.getScore(Txt.parse(scoreboardEntry.entries.get(pos))).setScore(pos);
    }
    this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    player.setScoreboard(scoreboard);
    isStarted = true;
  }

  /**
   * Updates Scoreboard (Will be changed to be line by line updating in the future for performance
   * reasons)
   */
  public void updateScoreboard() {
    for (String entry : scoreboard.getEntries()) {
      scoreboard.resetScores(entry);
    }
    this.objective.setDisplayName(Txt.parse(scoreboardEntry.displayName));
    for (int pos = 0; pos < scoreboardEntry.entries.size(); pos++)
      this.objective.getScore(Txt.parse(scoreboardEntry.entries.get(pos))).setScore(pos);
  }

  /** Destroy scoreboard */
  public void destroyScoreboard() {
    isStarted = false;
  }
}
