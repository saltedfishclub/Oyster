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

package cc.sfclub.game.module.game.desc;

import org.jetbrains.annotations.ApiStatus;

/**
 * The type of GameScope.
 * Also see {@link GameDescription}
 */
@ApiStatus.AvailableSince("0.1.0")
public enum ScopeType {
    /**
     * Chunk-based games. BedWars/Bridging Practices
     * Conflict with WORLD
     * Compatible to SCOPELESS
     */
    CHUNK,
    /**
     * Whole Server based game, like ManHunt.
     * Conflict with ANY CHUNK-Based Game and OTHER World-Based Game.
     * Compatible to SCOPLESS
     */
    WORLD,
    /**
     * The games doesn't need a scope, NG Words
     * Conflict with NOTHING
     * Compatible to ALL
     */
    SCOPELESS;

    public boolean isConflict(ScopeType another) {
        switch (this) {
            case CHUNK:
                return another == WORLD;
            case WORLD:
                return another == CHUNK;
            case SCOPELESS:
                return true;
            default:
                throw new AssertionError("Unknown Error");
        }
    }
}
