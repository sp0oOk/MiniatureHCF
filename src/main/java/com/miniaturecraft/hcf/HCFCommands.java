package com.miniaturecraft.hcf;

import com.miniaturecraft.hcf.enums.Role;
import com.miniaturecraft.hcf.integration.IntegrationProtocolLib;
import com.miniaturecraft.hcf.objects.Faction;
import com.miniaturecraft.hcf.objects.FactionParticipator;
import com.miniaturecraft.miniaturecore.commands.CommandArgs;
import com.miniaturecraft.miniaturecore.enums.Requirement;
import com.miniaturecraft.miniaturecore.interfaces.commands.Arg;
import com.miniaturecraft.miniaturecore.interfaces.commands.Command;
import com.miniaturecraft.miniaturecore.interfaces.commands.SubCommand;
import com.miniaturecraft.miniaturecore.objects.ParsedPlaceholders;
import com.miniaturecraft.miniaturecore.requirements.TypeRequirement;
import com.miniaturecraft.miniaturecore.utils.Txt;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(
    name = "f",
    description = "Root command for factions.",
    alias = {"faction", "factions", "fac"},
    versionCommand = true)
@SuppressWarnings("unused")
public class HCFCommands {

  /**
   * Command /f create <name> - Creates a faction.
   *
   * @param sender The sender of the command.
   * @param args The arguments of the command.
   */
  @SubCommand(
      name = "create",
      description = "Creates a new faction.",
      permission = "hcf.command.faction.create",
      args = {@Arg(name = "name", type = TypeRequirement.STRING)})
  public void onCommandCreate(CommandSender sender, CommandArgs args) {
    final String name = args.read();

    // Does name match the regex?
    if (!name.matches(HCF.get().getConf().factionNameRegex)) {
      sender.sendMessage(Txt.parse(HCF.get().getConf().factionNameRegexMessage));
      return;
    }

    // If the blacklist is enabled and the name is blacklisted, send a message.
    if (HCF.get().getConf().factionNameBlacklistEnabled
        && HCF.get().getConf().factionNameBlacklist.contains(name.toLowerCase())) {
      sender.sendMessage(Txt.parse(HCF.get().getConf().factionNameBlacklistMessage));
      return;
    }

    // Check if a faction with the name already exists.
    if (HCF.get().getFactionsHandler().getFaction(name) != null) {
      sender.sendMessage(Txt.parse(HCF.get().getConf().factionExists));
      return;
    }

    // Is sender a player?
    final boolean isPlayer = sender instanceof Player;
    // Participator of the sender.
    FactionParticipator participator = null;

    // If they are a player, and they are in a faction that isn't wilderness, send a message. (They
    // are already in a faction)
    if (isPlayer
        && (participator = HCF.get().getFactionsHandler().getPlayer((Player) sender)) != null
        && participator.getFaction().isPresent()
        && participator.getFaction().get().getId() != HCF.WILDERNESS_ID) {
      sender.sendMessage(Txt.parse(HCF.get().getConf().factionAlreadyInFaction));
      return;
    }

    // Create the faction.
    final Faction created =
        HCF.get()
            .getFactionsHandler()
            .createFaction(
                isPlayer ? ((Player) sender).getUniqueId() : null,
                name,
                "No description",
                isPlayer);

    // Get parsed placeholders.
    final ParsedPlaceholders placeholders = Utils.getSelfPlaceholders(participator);

    // If they are a player and participator exists, add them to the faction and set their role to
    // leader.
    if (isPlayer && participator != null) {
      created.addMember(participator, Role.LEADER);
    }

    // Send a message to the sender. (Faction created)
    sender.sendMessage(Txt.parse(placeholders.parse(HCF.get().getConf().factionCreated)));
  }

  /**
   * Command /f leave - Leaves the faction.
   *
   * @param sender The sender of the command.
   * @param args The arguments of the command.
   */
  @SubCommand(
      name = "leave",
      description = "Leave your current faction.",
      permission = "hcf.command.faction.leave",
      requirements = {Requirement.IS_PLAYER})
  public void onCommandLeave(CommandSender sender, CommandArgs args) {
    // Get the participator of the sender.
    final FactionParticipator participator =
        HCF.get().getFactionsHandler().getPlayer((Player) sender);
    // Get the parsed placeholders.
    final ParsedPlaceholders placeholders = Utils.getSelfPlaceholders(participator);

    // If they have a faction and leave was successful, send a message OR ELSE false (They are in a
    // faction)
    if (participator.getFaction().map(f -> f.leave(participator)).orElse(false)) {
      sender.sendMessage(placeholders.parse(HCF.get().getConf().fLeave));
      return;
    }

    // Send a message to the sender. (They are not in a faction)
    placeholders.parse(HCF.get().getConf().fLeaveCommandFailed).send(sender);
  }

  /**
   * Command /f who [faction] - Shows information about a faction.
   *
   * @param sender The sender of the command.
   * @param args The arguments of the command.
   */
  @SubCommand(
      name = "who",
      description = "View information about a faction.",
      alias = {"show"},
      permission = "hcf.command.faction.who",
      requirements = {Requirement.IS_PLAYER})
  public void onCommandWho(CommandSender sender, CommandArgs args) {
    // TODO: re-add placeholders
    // Get the sender as a player.
    final Player player = (Player) sender;
    // Get the participator of the sender.
    final FactionParticipator participator = HCF.get().getFactionsHandler().getPlayer(player);
    // Get the parsed placeholders.
    final ParsedPlaceholders placeholders = Utils.getSelfPlaceholders(participator);
    // Send the faction information to the sender. (Faction information)
    placeholders.parse(HCF.get().getConf().fWhoMessage).send(sender);
  }

  @SubCommand(
      name = "test",
      description = "Test command",
      permission = "hcf.command.faction.test",
      requirements = {Requirement.IS_PLAYER})
  public void onCommandTest(CommandSender sender, CommandArgs args) {
    final Player player = (Player) sender;
    final IntegrationProtocolLib protocolLib =
        HCF.get().getIntegration(IntegrationProtocolLib.class);
    protocolLib.spawnFakeBlocks(player, player.getLocation(), 5, Material.DIAMOND, (byte) 0);
    player.sendMessage("Spawned fake blocks.");
  }
}
