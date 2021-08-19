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

package cc.sfclub.game.module.scheduler;

import cc.sfclub.game.mechanic.Tickable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class TickReceipt<T> {
    private final List<AwaitingTickable<T>> syncs = new ArrayList<>();
    private Function<T, Boolean> requirement;

    public TickReceipt<T> requires(Supplier<Function<T, Boolean>> consumer) {
        return requires(consumer.get());
    }

    public TickReceipt<T> requires(Function<T, Boolean> func) {
        this.requirement = func;
        return this;
    }

    /**
     * Syncs and returning new receipt.
     *
     * @param tickable
     * @return
     */
    public TickReceipt<T> alsoTicks(Tickable<T> tickable) {
        var receipt = new TickReceipt<T>();
        syncs.add(new AwaitingTickable<>(tickable, receipt));
        return receipt;
    }

    /**
     * Only syncs, returning itself.
     *
     * @param tickable
     * @return
     */
    public TickReceipt<T> syncWith(Tickable<T> tickable) {
        alsoTicks(tickable);
        return this;
    }

    protected boolean tick(T t) {
        if (requirement == null) {
            syncs.forEach(e -> e.tick(t));
            return true;
        }
        if (requirement.apply(t)) {
            syncs.forEach(e -> e.tick(t));
            return true;
        } else {
            return false;
        }
    }
}
