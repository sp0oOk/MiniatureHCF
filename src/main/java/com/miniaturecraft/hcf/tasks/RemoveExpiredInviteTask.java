package com.miniaturecraft.hcf.tasks;

import com.miniaturecraft.hcf.HCF;
import org.bukkit.scheduler.BukkitRunnable;

public class RemoveExpiredInviteTask extends BukkitRunnable {

  private final boolean save;
  private int deletedCount;

  public RemoveExpiredInviteTask(boolean save) {
    this.save = save;
    this.deletedCount = 0;
  }

  @Override
  public void run() {
    HCF.get()
        .getFactionsHandler()
        .getAllFactions()
        .forEach(faction -> deletedCount += faction.removeExpiredInvitations());
    if (deletedCount > 0 && save) {
      HCF.get().save(HCF.get().getConf());
      HCF.get().log("<yellow>Removed <red>" + deletedCount + " <yellow>expired invitations.");
    }
  }
}
