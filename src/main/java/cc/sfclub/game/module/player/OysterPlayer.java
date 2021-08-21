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

package cc.sfclub.game.module.player;

import cc.sfclub.game.mechanic.GameEvent;
import cc.sfclub.game.mechanic.OysterEntity;
import cc.sfclub.game.module.flag.Flag;
import cc.sfclub.game.module.i18n.Locale;
import cc.sfclub.game.module.player.team.OysterTeam;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@ApiStatus.AvailableSince("0.1.0")
@Getter
public class OysterPlayer extends OysterEntity<OysterPlayer> {
    private final Locale locale;
    private final UUID bukkitPlayer;
    private final PlayerMechanic mechanic;
    @Nullable
    private final OysterTeam team;
    @Getter(AccessLevel.PRIVATE)
    private final Set<Flag<OysterPlayer>> sortedFlags;

    public OysterPlayer(Locale locale, UUID bukkitPlayer, PlayerMechanic mechanic, OysterTeam team, Collection<Flag> flags) {
        this.locale = locale;
        this.bukkitPlayer = bukkitPlayer;
        this.mechanic = mechanic;
        this.team = team;
        sortedFlags = new TreeSet<>((a, b) -> {
            int v = a.getPriority() - b.getPriority();
            if (v == 0) {
                return 1;
            }
            return v;
        });
    }

    public String translate(Object... args) {
        return locale.translate(getBukkitPlayer().getLocale(), args);
    }

    public void sendTranslated(Object... args) {
        if (isOnline()) {
            getBukkitPlayer().sendMessage(translate(args));
        }
    }

    @Override
    public void onUpdate(OysterPlayer player) {
        mechanic.onUpdate(player);
    }

    public boolean isOnline() {
        return getBukkitPlayer() != null;
    }

    @Override
    @Nullable
    public Flag<OysterPlayer> getFlagExact(@NonNull String name) {
        return sortedFlags.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(team.getFlags()
                        .stream()
                        .filter(e -> e.getName().equals(name))
                        .findFirst()
                        .orElse(null));
    }

    @Override
    public @NotNull List<Flag<OysterPlayer>> matchingFlags(@NonNull String prefixOrRegex, boolean regex) {
        return null;
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

    @Override
    public Set<Flag<OysterPlayer>> getFlags() {
        return sortedFlags;
    }

    @Override
    public void removeFlag(Flag flag) {
        sortedFlags.remove(flag);
    }

    @Override
    public boolean addFlag(Flag flag) {
        return sortedFlags.add(flag);
    }

    @Override
    public void onEvent(GameEvent event) {
        mechanic.onEvent(event);
    }

    @Nullable
    @Override
    public Entity getAsEntity() {
        return getBukkitPlayer();
    }
}
