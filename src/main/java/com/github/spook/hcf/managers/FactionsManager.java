package com.github.spook.hcf.managers;

import com.github.spook.hcf.HCF;
import com.github.spook.hcf.factions.Faction;
import com.github.spook.hcf.interfaces.IManager;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FactionsManager implements IManager {

  private static final Type type = new TypeToken<List<Faction>>() {}.getType();

  private HCF plugin;

  private Map<Integer, Faction> factions;
  private Gson gson;

  @Override
  public void onEnable(JavaPlugin hcf) {
    this.plugin = (HCF) hcf;
    this.gson = new Gson();
    loadAllFactions();
    createSystemFactions();
  }

  @Override
  public void onDisable() {}

  @SneakyThrows
  private void loadAllFactions() {

    final File factionsJson = new File(plugin.getDataFolder(), "factions.json");

    if (factionsJson.exists()) {
      final List<Faction> factions = gson.fromJson(new FileReader(factionsJson), type);
      factions.forEach(faction -> this.factions.put(faction.getId(), faction));
      return;
    }

    this.factions = Maps.newConcurrentMap();
  }

  private void createSystemFactions() {}

  public Faction getFaction(int id) {
    return factions.get(id);
  }

  public Faction getFaction(String name) {
    return factions.values().stream()
        .filter(faction -> faction.getName().equalsIgnoreCase(name))
        .findFirst()
        .orElse(null);
  }

  public Map<Integer, Faction> getAllFactions() {
    return factions;
  }

  public void setFactions(Map<Integer, Faction> factions) {
    this.factions = factions;
  }
}
