package com.miniaturecraft.hcf;

import com.miniaturecraft.hcf.configs.FactionsConfig;
import com.miniaturecraft.hcf.configs.HCFConfig;
import com.miniaturecraft.hcf.listeners.PlayerJoiningListener;
import com.miniaturecraft.miniaturecore.MiniaturePlugin;

public class HCF extends MiniaturePlugin {

  // ----------------------------------------- //
  // FIELDS
  // ----------------------------------------- //

  public static int WILDERNESS_ID = 0;
  public static int WARZONE_ID = 1;
  public static int SAFEZONE_ID = 2;
  public static int ROAD_ID = 3;

  private static HCF i;
  private FactionsConfig factionsConfig;
  private HCFConfig hcfConfig;

  /**
   * Our cute plugin instance
   *
   * @return The instance
   */
  public static HCF get() {
    return i;
  }

  /** Called when the plugin is enabled stage 2/3 */
  @Override
  public void onEnableInner() {
    i = this;
    factionsConfig = getConfig(FactionsConfig.class);
    factionsConfig.createSystemFactions();
    hcfConfig = getConfig(HCFConfig.class);
    registerListener(new PlayerJoiningListener());
  }

  /** Called when the plugin is disabled */
  @Override
  public void onDisable() {
    save(factionsConfig, hcfConfig);
    super.onDisable();
    i = null;
  }

  /**
   * Get the factions' config/handler
   *
   * @return The factions' config/handler
   */
  public FactionsConfig getFactionsHandler() {
    return factionsConfig;
  }

  /**
   * Get the HCF config
   *
   * @return The HCF config
   */
  public HCFConfig getConf() {
    return hcfConfig;
  }
}
