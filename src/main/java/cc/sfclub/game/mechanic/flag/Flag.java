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

package cc.sfclub.game.mechanic.flag;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.AvailableSince("0.0.1")
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Flag {
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 50;
    public static final int PRIORITY_LOW = 100;

    private final String name;
    /**
     * 优先级。越低越高
     */
    private final int priority;
    /**
     * 触发策略。如 NOT_CONTAINS 则是正在交互/或者由 Mechanic 正在处理的人不包含这个 tag 的时候触发。
     */
    private final Strategy trigStrategy;
    /**
     * 通常用于取消掉团队中的共享flag
     */
    private final boolean disable;
    /**
     * Flag 类型。可能会携带额外数据
     */
    private final FlagType type;

    public enum Strategy {
        NOT_CONTAINS, CONTAINS
    }
}
