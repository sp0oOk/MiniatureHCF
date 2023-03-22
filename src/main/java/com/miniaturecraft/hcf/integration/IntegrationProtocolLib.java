package com.miniaturecraft.hcf.integration;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.miniaturecraft.hcf.HCF;
import com.miniaturecraft.miniaturecore.interfaces.Integration;
import com.miniaturecraft.miniaturecore.objects.MiniatureList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;

@Integration(name = "ProtocolLib", className = "com.comphenix.protocol.ProtocolLibrary")
@SuppressWarnings("unused")
public class IntegrationProtocolLib implements Listener {

  public void onEnable() {}

  public void onDisable() {}

  public void spawnFakeBlocks(
      Player player, Location center, int radius, Material material, byte data) {
    // Create packet for spawning fake block
    PacketContainer fakeBlockPacket =
        ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SPAWN_ENTITY);

    // Set entity type to falling block
    fakeBlockPacket.getIntegers().write(0, 70);

    // Set block location and velocity for each side
    for (BlockFace face : BlockFace.values()) {
      Location blockLocation =
          center.clone().add(face.getModX() * radius, 0, face.getModZ() * radius);
      fakeBlockPacket.getIntegers().write(1, (int) (blockLocation.getX() * 32)); // X position
      fakeBlockPacket.getIntegers().write(2, (int) (blockLocation.getY() * 32)); // Y position
      fakeBlockPacket.getIntegers().write(3, (int) (blockLocation.getZ() * 32)); // Z position

      fakeBlockPacket.getIntegers().write(4, 0); // no velocity

      // Send packet to player
      try {
        ProtocolLibrary.getProtocolManager().sendServerPacket(player, fakeBlockPacket);
      } catch (Exception e) {
        e.printStackTrace();
      }

      // Spawn a fake block entity
      FallingBlock fakeBlock = player.getWorld().spawnFallingBlock(blockLocation, material, data);

      // Set a metadata value on the fake block entity to identify it as a fake block for this
      // player
      fakeBlock.setMetadata(
          "fake_block_player", new FixedMetadataValue(HCF.get(), player.getUniqueId().toString()));
    }
  }

  public void removeFakeBlocks(Player player) {
    // Create packet for destroying entities
    PacketContainer destroyEntitiesPacket =
        ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_DESTROY);

    // Set the list of entity IDs to destroy to all fake block entities for this player
    MiniatureList<Integer> entityIds = MiniatureList.create();
    for (Entity entity : player.getWorld().getEntitiesByClass(FallingBlock.class)) {
      if (entity.getMetadata("fake_block_player").stream()
          .anyMatch(
              metadataValue -> metadataValue.asString().equals(player.getUniqueId().toString()))) {
        entityIds.add(entity.getEntityId());
      }
    }
    destroyEntitiesPacket
        .getIntegerArrays()
        .write(0, entityIds.stream().mapToInt(i -> i).toArray());

    // Send packet to player
    try {
      ProtocolLibrary.getProtocolManager().sendServerPacket(player, destroyEntitiesPacket);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
