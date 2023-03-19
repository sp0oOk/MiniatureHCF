package com.miniaturecraft.hcf;

import com.miniaturecraft.hcf.objects.Faction;
import com.miniaturecraft.hcf.objects.FactionParticipator;
import com.miniaturecraft.miniaturecore.commands.CommandArgs;
import com.miniaturecraft.miniaturecore.enums.Requirement;
import com.miniaturecraft.miniaturecore.interfaces.commands.Arg;
import com.miniaturecraft.miniaturecore.interfaces.commands.Command;
import com.miniaturecraft.miniaturecore.interfaces.commands.SubCommand;
import com.miniaturecraft.miniaturecore.requirements.TypeRequirement;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(
    name = "f",
    description = "Root command for factions.",
    alias = {"faction", "factions", "fac"},
    versionCommand = true)
@SuppressWarnings("unused")
public class HCFCommands {

  @SubCommand(
      name = "who",
      description = "View information about a faction.",
      alias = {"show"},
      permission = "hcf.command.faction.who",
      requirements = {Requirement.IS_PLAYER},
      args = {@Arg(name = "faction", req = false, type = TypeRequirement.STRING)})
  public void onCommandWho(CommandSender sender, CommandArgs args) {
    final Player player = (Player) sender;
    final Faction faction =
        args.read(HCF.get().getFactionsHandler().getFactionByMember(player.getUniqueId()));
    final FactionParticipator participator = HCF.get().getFactionsHandler().getPlayer(player);
    HCF.get().getConf().parseFWho(faction, participator).send(player);
  }
}
