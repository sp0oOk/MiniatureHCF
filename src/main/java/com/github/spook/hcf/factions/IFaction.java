package com.github.spook.hcf.factions;

import java.util.List;

public interface IFaction {

  int getId();

  String getName();

  String description();

  List<IFactionParticipator> getMembers();
}
