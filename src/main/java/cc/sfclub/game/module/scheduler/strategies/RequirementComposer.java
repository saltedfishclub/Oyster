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

package cc.sfclub.game.module.scheduler.strategies;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RequirementComposer<T> implements Function<T, Boolean> {
    private final List<Function<T, Boolean>> funcs;

    public static <T> RequirementComposer<T> of(Class<T> classOf) {
        return new RequirementComposer<>(new ArrayList<>());
    }

    public static <T> RequirementComposer<T> of(Class<T> classOf, Function<T, Boolean>... func) {
        return new RequirementComposer<>(Lists.newArrayList(func));
    }

    public RequirementComposer<T> and(Function<T, Boolean> function) {
        funcs.add(function);
        return this;
    }

    @Override
    public Boolean apply(T t) {
        return funcs.stream().allMatch(e -> e.apply(t));
    }
}
