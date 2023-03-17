package com.github.spook.hcf;

import com.github.spook.hcf.nms.NMS;
import com.github.spook.hcf.nms.NMSVersionHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class HCF extends JavaPlugin {

  private static HCF instance;
  private NMSVersionHandler nmsVersionHandler;
  private NMS nms;

  @Override
  public void onEnable() {
    instance = this;
    nmsVersionHandler = new NMSVersionHandler();
    nms = nmsVersionHandler.getNMSInstance();
  }

  @Override
  public void onDisable() {
    nms = null;
    nmsVersionHandler = null;
    instance = null;
  }

  public static HCF get() {
    return instance;
  }

  public NMS getNms() {
    return nms;
  }
}
