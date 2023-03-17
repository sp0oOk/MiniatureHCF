package com.github.spook.hcf.factions;

import lombok.Data;

import java.util.List;

@Data
public class Faction implements IFaction {

  private int id;
  private String name;
  private String description;
  private List<IFactionParticipator> members;

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String description() {
    return null;
  }

  @Override
  public List<IFactionParticipator> getMembers() {
    return null;
  }
}
