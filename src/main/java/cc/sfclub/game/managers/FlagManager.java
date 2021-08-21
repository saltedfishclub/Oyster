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

import cc.sfclub.game.module.flag.FlagType;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 标签，标签类型管理器，全端统一
 */
@ApiStatus.AvailableSince("0.1.0")
public class FlagManager {
    private final Map<Class<? extends FlagType<?>>, Supplier<? extends FlagType<?>>> suppliers = new HashMap<>(64);

    /**
     * 注册一个 FlagType Supplier
     *
     * @param clazz
     * @param supplier
     * @param <T>
     * @return
     */
    public <T extends FlagType<?>> FlagManager registerSupplier(Class<T> clazz, Supplier<T> supplier) {
        suppliers.put(clazz, supplier);
        return this;
    }

    /**
     * 获取一个 FlagType
     *
     * @param type
     * @param <T>
     * @return
     */
    public <T extends FlagType<?>> T ofType(Class<T> type) {
        return (T) suppliers.getOrDefault(type, () -> null).get();
    }
}
