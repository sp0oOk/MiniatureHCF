package com.miniaturecraft.hcf.objects;

import com.miniaturecraft.hcf.enums.Role;
import com.miniaturecraft.hcf.interfaces.IFactionParticipator;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

@Data(staticConstructor = "create")
public class FactionParticipator implements IFactionParticipator {

  // ----------------------------------------- //
  // FIELDS
  // ----------------------------------------- //

  private final UUID id;
  private final String name;
  private final Faction faction;
  private final Role role;

  /** Returns the UUID of the participator. */
  @Override
  public UUID getId() {
    return id;
  }

  /** Returns the name of the participator. */
  @Override
  public String getName() {
    return name;
  }

  /** Returns the faction of the participator. */
  @Override
  public Optional<Faction> getFaction() {
    return Optional.ofNullable(faction);
  }

  /** Returns the role of the participator. */
  @Override
  public Role getRole() {
    return role;
  }

  /** Returns whether the participator is online. */
  @Override
  public boolean isOnline() {
    final Player player = Bukkit.getPlayer(id);
    return player != null && player.isOnline();
  }
}
