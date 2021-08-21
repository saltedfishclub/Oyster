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

import cc.sfclub.game.module.flag.Flag;
import lombok.NonNull;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * Also see {@link org.bukkit.scoreboard.Team} and {@link Flag} and {@link cc.sfclub.game.module.flag.FlagType} and {@link cc.sfclub.game.managers.FlagManager}
 * 这个接口代表了一个可以被贴上标签并被计算的对象
 *
 * @param <T>
 */
@ApiStatus.AvailableSince("0.1.0")
public interface Flaggable<T extends Tickable<?>> {

    /**
     * 获取所有标签
     */
    Set<Flag<T>> getFlags();

    /**
     * 删除标签
     *
     * @param flag flag
     */
    void removeFlag(Flag<T> flag);

    /**
     * 添加标签
     *
     * @param flag flag
     * @return success?always true
     */
    boolean addFlag(Flag<T> flag);

    /**
     * 精确匹配标签名字，可能返回null
     *
     * @param name flagName
     * @return flag or null
     */
    @Nullable
    Flag<T> getFlagExact(@NonNull String name);

    /**
     * 根据前缀或者正则获取
     *
     * @param prefixOrRegex prefix or regex
     * @param regex         useRegex
     * @return flagResult or emptyList
     */
    @NotNull
    List<Flag<T>> matchingFlags(@NonNull String prefixOrRegex, boolean regex);
}
