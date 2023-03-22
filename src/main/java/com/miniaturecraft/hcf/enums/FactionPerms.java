package com.miniaturecraft.hcf.enums;

import com.miniaturecraft.miniaturecore.objects.MiniatureList;

public enum FactionPerms {

  // ----------------------------------------- //
  // LEADER PERMS
  // ----------------------------------------- //

  SET_HOME(Role.LEADER),
  DISBAND(Role.LEADER),

  // ----------------------------------------- //
  // CO-LEADER PERMS
  // ----------------------------------------- //

  INVITE(Role.CAPTAIN),
  KICK(Role.CAPTAIN),
  PROMOTE(Role.CAPTAIN),
  DEMOTE(Role.CAPTAIN),

  // ----------------------------------------- //
  // ALL PERMS
  // ----------------------------------------- //
  ACCESS(Role.MEMBER, Role.LEADER, Role.CAPTAIN, Role.OFFICER);

  private final Role[] roles;

  FactionPerms(Role... roles) {
    this.roles = roles;
  }

  public MiniatureList<Role> getRoles() {
    return MiniatureList.create(roles);
  }

  public boolean hasRole(Role role) {
    return getRoles().contains(role);
  }

  public boolean hasRole(Role... roles) {
    for (Role role : roles) {
      if (hasRole(role)) {
        return true;
      }
    }
    return false;
  }
}
