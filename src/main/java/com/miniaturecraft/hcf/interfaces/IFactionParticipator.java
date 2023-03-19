package com.miniaturecraft.hcf.interfaces;

import com.miniaturecraft.hcf.enums.Role;
import com.miniaturecraft.hcf.objects.Faction;

import java.util.Optional;
import java.util.UUID;

public interface IFactionParticipator {

  UUID getId();

  String getName();

  Optional<Faction> getFaction();

  Role getRole();

  boolean isOnline();
}
