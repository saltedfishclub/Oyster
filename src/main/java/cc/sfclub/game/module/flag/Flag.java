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

import cc.sfclub.game.api.OysterAPI;
import cc.sfclub.game.mechanic.Flaggable;
import cc.sfclub.game.mechanic.Tickable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;

/**
 * 实际上 Oyster 里并没有阵营~~因为我们有比阵营更加灵活的东西~~
 * 首先，你不应该把在 Oyster 中的队伍理解为*总是对立的*队伍，他们没有那么死板。在 Oyster 中，队伍只能代表一群人。
 * 而真正起到区分队伍的实际上是 `Flag`，也就是`标签` 。通过配置标签策略，我们可以做到很多事情:
 * <p>
 * - 女生宿舍 4 个人 N 个群
 * - 使用标签组合出更灵活的阵营，并且标签随时可变
 * - 让队伍内的成员条件不对等
 * - .....
 * - 让有小红帽 flag 的人名字前缀加一个小绿帽
 *
 * @param <T>
 */
@ApiStatus.AvailableSince("0.1.0")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Flag<T extends Tickable<?>> {
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
    private final FlagType<T> type;

    public boolean canRun(Flaggable<T> t) {
        return type.canRun(t, trigStrategy);
    }

    public static <T extends Tickable<?>> Flag<T> of(String name, int priority, Strategy strategy, boolean disable, Class<FlagType<T>> flagTypeClass) {
        return new Flag<T>(name, priority, strategy, disable, OysterAPI.getInstance().getFlagManager().ofType(flagTypeClass));
    }

    public enum Strategy {
        NOT_CONTAINS, CONTAINS, ALWAYS
    }


}
