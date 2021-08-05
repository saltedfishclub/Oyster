/*
 *
 *     Oyster - The universal minigame framework for spigot servers.
 *     Copyright (C) 2021 SaltedFish Club
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 *     USA
 */

package cc.sfclub.game.mechanic;

import cc.sfclub.game.mechanic.player.PlayerMechanic;
import cc.sfclub.game.mechanic.team.Team;
import cc.sfclub.game.module.i18n.Locale;
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
