package com.github.spook.hcf.nms;

import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;

public class NMSVersionHandler {

  private final String versionString;

  public NMSVersionHandler() {
    String packageName = Bukkit.getServer().getClass().getPackage().getName();
    this.versionString =
        Bukkit.getServer()
            .getClass()
            .getPackage()
            .getName()
            .substring(packageName.lastIndexOf('.') + 1);
  }

  public boolean isVersion(String version) {
    return versionString.contains(version);
  }

  public boolean isLegacy() {
    return !versionString.startsWith("v");
  }

  public String getVersionString() {
    return versionString;
  }

  public NMS getNMSInstance() {
    try {
      return (NMS)
          Class.forName("com.github.spook.hcf.nms." + versionString + "." + versionString)
              .getDeclaredConstructor()
              .newInstance();
    } catch (InstantiationException
        | IllegalAccessException
        | ClassNotFoundException
        | NoSuchMethodException
        | InvocationTargetException e) {
      e.printStackTrace();
      return null;
    }
  }
}
