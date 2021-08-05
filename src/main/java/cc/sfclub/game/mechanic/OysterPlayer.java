package cc.sfclub.game.mechanic;

import cc.sfclub.game.config.Locale;
import cc.sfclub.game.mechanic.player.PlayerMechanic;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.AvailableSince("0.0.1")
@RequiredArgsConstructor
public class OysterPlayer implements Tickable {
    private final Locale locale;
    private final Player bukkitPlayer;
    private final PlayerMechanic mechanic;

    public String translate(String key, Object... args) {
        return String.format(locale.getLocale(bukkitPlayer.getLocale()).getOrDefault(key, ChatColor.RED + key + ChatColor.RESET), args);
    }

    @Override
    public void onUpdate(long time) {
        mechanic.onUpdate(time);
    }
}
