package cc.sfclub.game.mechanic;

import cc.sfclub.game.config.Locale;
import cc.sfclub.game.mechanic.player.PlayerMechanic;
import cc.sfclub.game.mechanic.team.Team;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@ApiStatus.AvailableSince("0.0.1")
@RequiredArgsConstructor
@Getter
public class OysterPlayer implements Tickable {
    private final Locale locale;
    private final UUID bukkitPlayer;
    private final PlayerMechanic mechanic;
    private final Team team;

    public String translate(String key, Object... args) {
        return String.format(locale.getLocale(getBukkitPlayer().getLocale()).getOrDefault(key, ChatColor.RED + key + ChatColor.RESET), args);
    }

    public void sendTranslated(String key, Object... args) {
        if (isOnline()) {
            getBukkitPlayer().sendMessage(translate(key, args)); //maybe we con... records.
        }
    }

    @Override
    public void onUpdate(long time) {
        mechanic.onUpdate(time);
    }

    public boolean isOnline() {
        return getBukkitPlayer() != null;
    }

    /**
     * Nullable when player isn't online.
     *
     * @return
     */
    @Nullable
    public Player getBukkitPlayer() {
        return Bukkit.getPlayer(bukkitPlayer);
    }
}
