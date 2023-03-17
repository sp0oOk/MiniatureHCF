package com.github.spook.hcf.factions;

import lombok.Data;

import java.util.Optional;
import java.util.UUID;

@Data
public class FactionParticipator implements IFactionParticipator {

  private UUID id;
  private String name;
  private IFaction faction;
  private Role role;

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Optional<IFaction> getFaction() {
    return Optional.ofNullable(faction);
  }

  @Override
  public Role getRole() {
    return role;
  }
}
