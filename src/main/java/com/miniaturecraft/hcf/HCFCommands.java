package com.miniaturecraft.hcf;

import com.miniaturecraft.hcf.enums.Role;
import com.miniaturecraft.hcf.objects.Faction;
import com.miniaturecraft.hcf.objects.FactionParticipator;
import com.miniaturecraft.hcf.tasks.TeleportationTask;
import com.miniaturecraft.miniaturecore.commands.CommandArgs;
import com.miniaturecraft.miniaturecore.enums.Requirement;
import com.miniaturecraft.miniaturecore.interfaces.commands.Arg;
import com.miniaturecraft.miniaturecore.interfaces.commands.Command;
import com.miniaturecraft.miniaturecore.interfaces.commands.SubCommand;
import com.miniaturecraft.miniaturecore.objects.ParsedPlaceholders;
import com.miniaturecraft.miniaturecore.objects.QL;
import com.miniaturecraft.miniaturecore.requirements.TypeRequirement;
import com.miniaturecraft.miniaturecore.utils.Txt;
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
                !isPlayer);

    // Get parsed placeholders.
    final ParsedPlaceholders placeholders = Utils.getSelfPlaceholders(participator);

    // If they are a player and participator exists, add them to the faction and set their role to
    // leader.
    if (isPlayer && participator != null) {
      created.addMember(participator, Role.LEADER);
    }

    // Send a message to the sender. (Faction created)
    sender.sendMessage(placeholders.parse(HCF.get().getConf().factionCreated));
  }

  @SubCommand(
      name = "home",
      description = "Teleports you to your faction's home.",
      permission = "hcf.command.faction.home",
      requirements = {Requirement.IS_PLAYER})
  public void onCommandHome(CommandSender sender, CommandArgs args) {
    final Player player = (Player) sender;
    final FactionParticipator participator = HCF.get().getFactionsHandler().getPlayer(player);

    if (!participator.getFaction().isPresent()) {
      sender.sendMessage(Txt.parse(HCF.get().getConf().factionDoesNotExist));
      return;
    }

    final Faction faction = participator.getFaction().get();

    if (!faction.getHome().isPresent()) {
      sender.sendMessage(Txt.parse(HCF.get().getConf().factionHomeNotSet));
      return;
    }

    new TeleportationTask(
            player,
            faction.getHome().get(),
            HCF.get().getConf().factionHomeTeleportSeconds,
            Txt.parse(HCF.get().getConf().factionHomeTeleported))
        .runTaskTimer(HCF.get(), 0L, 20L);

    player.sendMessage(Txt.parse(HCF.get().getConf().factionHomeTeleporting));
  }

  @SubCommand(
      name = "sethome",
      description = "Sets your faction's home.",
      permission = "hcf.command.faction.sethome",
      requirements = {Requirement.IS_PLAYER})
  public void onCommandSetHome(CommandSender sender, CommandArgs args) {
    final Player player = (Player) sender;
    final FactionParticipator participator = HCF.get().getFactionsHandler().getPlayer(player);
    final Faction faction;

    if ((faction = participator.getFaction().orElse(null)) == null) {
      sender.sendMessage(Txt.parse(HCF.get().getConf().factionDoesNotExist));
      return;
    }

    // TODO: Reminder, do role checks/permission system and land check!

    faction.setHome(QL.valueOf(player.getLocation()));
    player.sendMessage(Txt.parse(HCF.get().getConf().factionHomeSet));
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

  @SubCommand(
      name = "disband",
      description = "Disbands your current faction.",
      permission = "hcf.command.faction.disband",
      args = {@Arg(name = "faction", req = false, type = TypeRequirement.STRING)})
  public void onDisband(CommandSender sender, CommandArgs args) {
    // Get the participator of the sender.
    final FactionParticipator participator =
        sender instanceof Player ? HCF.get().getFactionsHandler().getPlayer((Player) sender) : null;

    // Name or null if not set.
    final String name = args.read(null);

    // If name is null and participator isn't null set the faction to the participators faction or
    // else get the faction by name.
    final Faction faction =
        name == null && participator != null
            ? participator.getFaction().orElse(null)
            : HCF.get().getFactionsHandler().getFaction(name);

    // If the faction still doesn't exist, send a message.
    if (faction == null) {
      sender.sendMessage(Txt.parse("&cFaction not found."));
      return;
    }

    // If the faction is a system faction, send a message.
    if (faction.isSystemFaction()) {
      sender.sendMessage(Txt.parse("&cYou cannot disband system factions."));
      return;
    }

    // Get the faction leader of X faction.
    final FactionParticipator leader = faction.getFLeader().orElse(null);

    // If the leader is null or the leader is null (should never reach, but some safety checks)
    if (leader == null || participator == null) {
      return; // This should never happen.
    }

    // If the leader isn't the sender, and they aren't bypassing, send a message.
    if (leader.getId() != participator.getId() && !participator.isBypassing()) {
      sender.sendMessage(Txt.parse("&cYou must be the leader of the faction to disband it."));
      return;
    }

    // Disband the faction.
    faction.disband(leader.getDisplayName());

    // Send a message to the sender. (Faction disbanded)
    sender.sendMessage(Txt.parse("&aSuccessfully disbanded the faction."));
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
    // TODO: re-add args, when rethink of placeholers is done.
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
      name = "bypass",
      description = "Administrative bypass command.",
      permission = "hcf.command.faction.bypass",
      requirements = {Requirement.IS_PLAYER})
  public void onCommandBypass(CommandSender sender, CommandArgs args) {
    final FactionParticipator participator =
        HCF.get().getFactionsHandler().getPlayer((Player) sender);
    participator.changeBypassing();
    final String parsedStr =
        Utils.getSelfPlaceholders(participator).parse(HCF.get().getConf().fBypassMessage);
    sender.sendMessage(parsedStr);
  }

  @SubCommand(
      name = "scoreboard",
      alias = {"sb"},
      description = "Toggle scoreboard state.",
      permission = "hcf.command.faction.scoreboard",
      requirements = {Requirement.IS_PLAYER})
  public void onScoreboard(CommandSender sender, CommandArgs args) {
    // TODO Implement this.
  }

  @SubCommand(
      name = "test",
      description = "Test command",
      permission = "hcf.command.faction.test",
      requirements = {Requirement.IS_PLAYER})
  public void onCommandTest(CommandSender sender, CommandArgs args) {
    final Player player = (Player) sender;
  }
}
