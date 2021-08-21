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

package cc.sfclub.game.managers;

import cc.sfclub.game.Oyster;
import cc.sfclub.game.module.game.OysterGame;
import cc.sfclub.game.module.game.desc.GameDescription;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class GameManager {
    private static final Map<String, GameDescription> SHARED_DESCRIPTIONS = new HashMap<>();
    private final Map<String, OysterGame> runningGames = new HashMap<>();
    @Getter
    private final Oyster oysterPlugin;

    /**
     * Register a prototype
     *
     * @param description
     */
    public void registerGamePrototype(GameDescription description) {
        SHARED_DESCRIPTIONS.put(description.getName(), description);
    }

    /**
     * Launch a game into preparing state.
     * For game's name: {@link OysterGame#getName()}
     * For game's initialization: {@link cc.sfclub.game.module.game.State#init(OysterGame)}
     *
     * @param gamePrototype prototype name
     * @return the game.
     */
    public OysterGame launchGame(String gamePrototype) {
        var desc = SHARED_DESCRIPTIONS.get(gamePrototype);
        var og = OysterGame.builder().name(gamePrototype + "-" + UUID.randomUUID()).state(desc.getStateSupplier().get()).build();
        runningGames.put(gamePrototype + "-" + UUID.randomUUID(), og);
        return og;
    }
}
