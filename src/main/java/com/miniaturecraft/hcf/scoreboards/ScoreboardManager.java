package com.miniaturecraft.hcf.scoreboards;

import com.miniaturecraft.miniaturecore.objects.MiniatureMap;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {
  private final MiniatureMap<Player, AScoreboard> scoreboards;

  public ScoreboardManager() {
    scoreboards = MiniatureMap.create();
  }

  /**
   * Shows a scoreboard to a player.
   *
   * @param player the player to show the scoreboard to
   * @param entry the entry to show(scoreboard)
   */
  public void showScoreboard(Player player, ScoreboardEntry entry) {
    Scoreboard scoreboard = player.getServer().getScoreboardManager().getNewScoreboard();
    AScoreboard aScoreboard = new AScoreboard(player, scoreboard, entry);
    scoreboards.put(player, aScoreboard);
    aScoreboard.loadScoreboard();
  }

  /**
   * Hides/Removes the scoreboard from the player.
   *
   * @param player the player to remove the scoreboard from
   */
  public void hideScoreboard(Player player) {
    AScoreboard aScoreboard = scoreboards.get(player);
    if (aScoreboard != null) {
      aScoreboard.destroyScoreboard();
      scoreboards.remove(player);
    }
  }
}
