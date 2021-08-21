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

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.jetbrains.annotations.ApiStatus;

/**
 * 游戏事件
 * 可以用于广播**具有针对性的**事件或者传输数据。
 */
@Getter
@AllArgsConstructor
@ApiStatus.AvailableSince("0.1.0")
public abstract class GameEvent extends Event {
    /**
     * 事件发生的范围
     */
    private final Scope scope;

    public enum Scope {
        GAME, FLAG, TEAM, PLAYER
    }
}
