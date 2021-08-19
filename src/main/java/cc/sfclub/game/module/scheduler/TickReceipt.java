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
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A receipt that used to specific actions for a tick.
 *
 * @param <T> Tick Target
 */
@ApiStatus.AvailableSince("0.1.0")
public class TickReceipt<T> {
    private final List<AwaitingTickable<T>> syncs = new ArrayList<>();
    private Function<T, Boolean> requirement;
    private String name;

    /**
     * Wrapper method for scheduler.strategies
     * Also see {@link cc.sfclub.game.module.scheduler.strategies.PeriodicTicks} and {@link cc.sfclub.game.module.scheduler.strategies.RequirementComposer}
     *
     * @param consumer
     * @return
     */
    public TickReceipt<T> requires(Supplier<Function<T, Boolean>> consumer) {
        return requires(consumer.get());
    }

    /**
     * *Set* a function that controls tick happening or not.
     * ALL receipts only have ONE requirement , see {@link cc.sfclub.game.module.scheduler.strategies.RequirementComposer} for more conditions.
     * For more complexly periodic conditions: {@link cc.sfclub.game.module.scheduler.strategies.PeriodicTicks}
     *
     * @param func
     * @return
     */
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

    /**
     * Set name for receipt.
     * Also see
     *
     * @param name receipt name
     * @return it
     */
    public TickReceipt<T> name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get name for receipt.
     *
     * @return
     */
    public String name() {
        return name;
    }

    @SuppressWarnings("all")
    protected boolean tick(Object Ot) {
        T t = (T) Ot;
        if (requirement == null || requirement.apply(t)) {
            for (AwaitingTickable<T> sync : syncs) {
                sync.tick(sync);
            }
            return true;
        } else {
            return false;
        }
    }
}
