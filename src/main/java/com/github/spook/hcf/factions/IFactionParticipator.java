package com.github.spook.hcf.factions;

import java.util.Optional;
import java.util.UUID;

public interface IFactionParticipator {

    UUID getId();
    String getName();
    Optional<IFaction> getFaction();
    Role getRole();

}
