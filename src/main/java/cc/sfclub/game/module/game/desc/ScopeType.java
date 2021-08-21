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
 * {@link cc.sfclub.game.module.game.region.GameScope} 的场地类型
 * Also see {@link GameDescription}
 */
@ApiStatus.AvailableSince("0.1.0")
public enum ScopeType {
    /**
     * 基于区块组合的地图，例如搭路练习
     * 和 WORLD 类型冲突
     * 和 SCOPELESS 类型兼容
     */
    CHUNK,
    /**
     * 基于整个服务器做游玩空间的游戏，例如 ManiHunt
     * 于其他 WORLD 类型的游戏或任何 CHUNK 类型的游戏冲突
     * 和 SCOPELESS 类型兼容
     */
    WORLD,
    /**
     * 不需要场地的游戏，比如 NG 词
     * 和任何类型兼容
     */
    SCOPELESS;

    /**
     * 对比是否冲突
     *
     * @param another
     * @return
     */
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
