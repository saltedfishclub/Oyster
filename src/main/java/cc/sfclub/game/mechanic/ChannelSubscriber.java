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
import cc.sfclub.game.module.event.Channel;
import cc.sfclub.game.module.event.Listenable;
import org.jetbrains.annotations.ApiStatus;

/**
 * 事件处理器
 */
@ApiStatus.AvailableSince("0.1.0")
public interface ChannelSubscriber<T extends Listenable> {
    /**
     * Also see {@link Channel} and {@link GameEvent}
     *
     * @param event
     */
    void onData(T event);
}
