package gr1mly4memes.slime.commands.subcommands;

import gr1mly4memes.slime.SlimeConfig;
import gr1mly4memes.slime.commands.SlimeSubcommand;
import net.minecraft.server.MinecraftServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.io.File;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;

public class ReloadCommand implements SlimeSubcommand {
    @Override
    public boolean execute(CommandSender sender, String subCommand, String[] args) {
        MinecraftServer server = MinecraftServer.getServer();
        SlimeConfig.init((File) server.options.valueOf("slime-settings"));
        Command.broadcastCommandMessage(sender, text("Slime config reload complete.", GREEN));
        return false;
    }
}
