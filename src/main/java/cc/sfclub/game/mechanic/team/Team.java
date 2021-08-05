package cc.sfclub.game.mechanic.team;

import cc.sfclub.game.mechanic.EventReactor;
import cc.sfclub.game.mechanic.OysterPlayer;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.ApiStatus;

import java.util.Set;

@ApiStatus.AvailableSince("0.0.1")
public interface Team extends EventReactor {
    Set<OysterPlayer> getPlayers();

    Set<Flag> getFlags();

    String getName();

    ChatColor getColor();
}
