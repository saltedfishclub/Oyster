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

package cc.sfclub.game.module.flag;

import cc.sfclub.game.mechanic.Tickable;
import cc.sfclub.game.module.player.OysterPlayer;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.AvailableSince("0.0.1")
public abstract class FlagType<T extends Tickable<?>> implements Tickable<T> {
    private final Flag.Strategy trigStrategy;

    public FlagType(Flag.Strategy strategy) {
        trigStrategy = strategy;
    }

    public abstract String getName();

    public void tick(T target) {

    }

    /**
     * Only call it like player/team.getType().canRun
     *
     * @param target
     * @return
     */
    public boolean canRun(OysterPlayer target) {
        //todo specators
        if (trigStrategy == Flag.Strategy.ALWAYS) return true;
        Flag flag = target.getFlag(getName());
        if (flag == null) {
            return trigStrategy == Flag.Strategy.NOT_CONTAINS;
        } else {
            return trigStrategy == Flag.Strategy.CONTAINS;
        }
    }
}
