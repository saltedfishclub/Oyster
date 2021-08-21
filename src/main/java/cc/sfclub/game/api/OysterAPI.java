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

package cc.sfclub.game.api;

import cc.sfclub.game.Oyster;
import cc.sfclub.game.managers.FlagManager;
import cc.sfclub.game.managers.GameManager;
import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;

/**
 * Oyster APIs
 */
@ApiStatus.AvailableSince("0.1.0")
public class OysterAPI {
    @Getter
    private final FlagManager flagManager = new FlagManager();
    private final GameManager gameManager = new GameManager();

    public static OysterAPI getInstance() {
        return Oyster.getPlugin(Oyster.class).getApi();
    }
}
