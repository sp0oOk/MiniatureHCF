package com.miniaturecraft.hcf.scoreboards;

import com.miniaturecraft.miniaturecore.objects.MiniatureList;
import com.miniaturecraft.miniaturecore.objects.MiniatureMap;
import org.bukkit.ChatColor;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ScoreboardEntry {
  public String displayName;
  public MiniatureList<String> entries;

  public ScoreboardEntry(String displayName, MiniatureList<String> entries) {
    this.displayName = displayName;
    this.entries = removeDuplicates(entries);
    Collections.reverse(this.entries);
  }

  private String makeUniqueEntry(String entry) {
    ChatColor[] colors = ChatColor.values();
    final Random random = new Random();
    final int index = random.nextInt(colors.length);
    final ChatColor color = colors[index];
    if (random.nextBoolean()) {
      return color + entry;
    } else {
      return entry + color;
    }
  }

  // https://www.spigotmc.org/threads/scoreboard-doesnt-display-some-lines.76746/
  private MiniatureList<String> removeDuplicates(List<String> entries) {
    MiniatureMap<String, Integer> countMap = MiniatureMap.create();
    for (String entry : entries) {
      countMap.put(entry, countMap.getOrDefault(entry, 0) + 1);
    }
    MiniatureList<String> uniqueEntries = MiniatureList.create();
    for (String entry : entries) {
      if (countMap.get(entry) > 1) {
        uniqueEntries.add(makeUniqueEntry(entry));
        countMap.put(entry, countMap.get(entry) - 1);
      } else {
        uniqueEntries.add(entry);
      }
    }
    return uniqueEntries;
  }

  public static ScoreboardEntry of(String displayName, MiniatureList<String> entries) {
    return new ScoreboardEntry(displayName, entries);
  }
}
