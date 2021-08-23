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
import cc.sfclub.game.util.Log;
import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * 游戏管理器。负责组装游戏
 * Also see {@link OysterGame}
 */
public class GameManager {
    private static final Map<String, GameDescription> SHARED_DESCRIPTIONS = new HashMap<>();
    private final Map<String, OysterGame> runningGames = new HashMap<>();
    @Getter
    private final Oyster oysterPlugin = Oyster.getPlugin(Oyster.class);

    /**
     * 注册一个游戏的原型，将会根据原型新建一个GameState注入游戏。
     * Also see {@link cc.sfclub.game.module.game.State} and {@link GameDescription}
     *
     * @param description
     */
    public void registerGamePrototype(GameDescription description) {
        Validate.notNull(description);
        SHARED_DESCRIPTIONS.put(description.getName(), description);
    }

    /**
     * 启动一个游戏到准备阶段。
     * 获取游戏的名字: {@link OysterGame#getName()}
     * 在哪初始化这个游戏: {@link cc.sfclub.game.module.game.State#init(OysterGame)}
     *
     * @param gamePrototype prototype name
     * @return the game.
     */
    @Nullable
    public OysterGame launchGame(String gamePrototype) {
        var desc = SHARED_DESCRIPTIONS.get(gamePrototype);
        Validate.notNull(desc, "Can't find specified game prototype!");
        if (!canRun(desc)) {
            // Can't run that game for scope conflicts.
            Log.transWarn("game.loading.conflict.scope", gamePrototype);
            return null;
        }
        var og = OysterGame.builder().name(gamePrototype + "-" + UUID.randomUUID()).state(desc.getStateSupplier().get()).protoType(desc).build();
        runningGames.put(gamePrototype + "-" + UUID.randomUUID(), og);
        return og;
    }

    /**
     * 获取所有游戏作为流
     *
     * @return stream
     */
    public Stream<OysterGame> getGames() {
        return runningGames.values().stream();
    }

    /**
     * 通过名字获取原型
     *
     * @param name
     * @return
     */
    public GameDescription getPrototypeByName(String name) {
        Validate.notNull(name);
        return SHARED_DESCRIPTIONS.get(name);
    }

    private boolean canRun(GameDescription description) {
        return runningGames.values().stream().noneMatch(e -> e.getProtoType().getScopeType().isConflict(description.getScopeType()));
    }
}
