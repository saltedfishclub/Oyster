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

package cc.sfclub.game.mechanic.team;

import cc.sfclub.game.mechanic.EventReactor;
import cc.sfclub.game.mechanic.Flaggable;
import cc.sfclub.game.mechanic.GameEvent;
import cc.sfclub.game.mechanic.flag.Flag;
import cc.sfclub.game.mechanic.player.OysterPlayer;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

@ApiStatus.AvailableSince("0.0.1")
@Getter
@RequiredArgsConstructor
public class OysterTeam implements EventReactor, Flaggable {
    private final Set<OysterPlayer> players;
    private final Set<Flag> flags;
    private final String name;
    private final ChatColor color;
    private final TeamMechanic mechanic;

    @Override
    public void removeFlag(Flag flag) {
        flags.remove(flag);
    }

    @Override
    public boolean addFlag(Flag flag) {
        return flags.add(flag);
    }

    @Override
    public @Nullable Flag getFlag(@NonNull String name) {
        return null; //todo
    }

    @Override
    public void onEvent(GameEvent event) {

    }
}