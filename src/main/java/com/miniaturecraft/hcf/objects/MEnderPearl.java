package com.miniaturecraft.hcf.objects;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class MEnderPearl extends EntityEnderPearl {

  public MEnderPearl(Player player) {
    super(((CraftPlayer) player).getHandle().world, ((CraftPlayer) player).getHandle());
  }

  protected void a(MovingObjectPosition position) {

    final IBlockData block = this.world.getType(position.a());

    if (block.getBlock() instanceof BlockFenceGate && block.getBlock().b(block.getBlock())
        || block.getBlock() == Blocks.TRIPWIRE) {
      return;
    }

    if (getBukkitEntity().getLocation().getBlock().getType().isSolid()) {
      die();
      return;
    }

    super.a(position);
  }
}
