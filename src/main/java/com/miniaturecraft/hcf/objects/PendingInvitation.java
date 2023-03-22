package com.miniaturecraft.hcf.objects;

import com.miniaturecraft.hcf.HCF;
import lombok.Data;

import java.util.UUID;

@Data(staticConstructor = "create")
public class PendingInvitation {

  private final UUID invitee;
  private final UUID inviter;
  private final long timestamp;

  public boolean isExpired() {
    final int seconds = HCF.get().getConf().factionExpireInvitationSeconds;
    return seconds > 0 && (System.currentTimeMillis() - timestamp) > (seconds * 1000L);
  }
}
