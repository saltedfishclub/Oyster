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
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncScheduler implements Scheduler {
    private final Map<Class<?>, List<AwaitingTickable<?>>> tickTargets = new HashMap<>();

    @Override
    public void tick() {
        for (List<AwaitingTickable<?>> value : tickTargets.values()) {
            for (AwaitingTickable<?> awaitingTickable : value) {
                awaitingTickable.tick(awaitingTickable.tickable);
            }
        }
    }

    @Override
    public <T> TickReceipt<T> add(Tickable<T> tickable) {
        var receipt = new TickReceipt<T>();
        var awaitTickable = new AwaitingTickable<>(tickable, receipt);

        if (!tickTargets.containsKey(tickable.getClass())) {
            tickTargets.put(tickable.getClass(), Lists.newArrayList(awaitTickable));
        } else {
            tickTargets.get(tickable.getClass()).add(awaitTickable);
        }
        return receipt;
    }

    @Override
    public void remove(Tickable<?> tickable) {
        if (tickTargets.containsKey(tickable.getClass())) {
            var list = tickTargets.get(tickable.getClass());
            list.remove(tickable);
            if (list.size() == 0) {
                tickTargets.remove(tickable.getClass());
            }
        }
    }
}
