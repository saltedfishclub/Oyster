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

import org.jetbrains.annotations.ApiStatus;

/**
 * 代表可以被Tick. 实现 Tickable 并不一定被 Tick，关于 Tick 策略，详见 {@link cc.sfclub.game.module.scheduler.Scheduler} and {@link cc.sfclub.game.module.scheduler.TickReceipt}
 *
 * @param <T>
 */
@ApiStatus.AvailableSince("0.1.0")
@FunctionalInterface
public interface Tickable<T> {
    void onUpdate(T object);
}
