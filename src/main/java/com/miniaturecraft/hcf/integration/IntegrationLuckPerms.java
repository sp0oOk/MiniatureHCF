package com.miniaturecraft.hcf.integration;

import com.miniaturecraft.hcf.HCF;
import com.miniaturecraft.miniaturecore.interfaces.Integration;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

@Integration(name = "LuckPerms", className = "net.luckperms.api.LuckPerms")
@SuppressWarnings("unused")
public class IntegrationLuckPerms {

  private static LuckPerms luckPerms;

  public void onEnable() {
    final RegisteredServiceProvider<LuckPerms> provider =
        Bukkit.getServer().getServicesManager().getRegistration(LuckPerms.class);

    if (provider == null) {
      HCF.get().log("<red>Failed to hook into LuckPerms.");
      return;
    }

    luckPerms = provider.getProvider();
  }

  public void onDisable() {
    luckPerms = null;
  }
}
