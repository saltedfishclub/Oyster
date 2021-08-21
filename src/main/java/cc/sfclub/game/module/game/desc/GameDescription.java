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

import cc.sfclub.game.module.game.OysterGame;
import cc.sfclub.game.module.game.State;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

/**
 * 插件对一个游戏的描述，也就是原型
 */
@ApiStatus.AvailableSince("0.1.0")
@Getter
@Builder
public class GameDescription {
    /**
     * 游戏原型的名字，比如起床战争
     * 关于生成的游戏对象的名字，详见 {@link OysterGame#getName()}
     */
    private final String name;

    /**
     * 游戏原型的版本号
     */
    private final String version;

    /**
     * 游戏初始化 State 的提供着
     */
    private final Supplier<State> stateSupplier;

    /**
     * 游戏占用的场地类型
     */
    private final ScopeType scopeType;
}
