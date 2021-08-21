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

import cc.sfclub.game.Oyster;
import cc.sfclub.game.module.scheduler.Scheduler;
import cc.sfclub.game.module.scheduler.SyncScheduler;
import cc.sfclub.game.module.scheduler.TickReceipt;
import cc.sfclub.game.task.SchedulerAdapter;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.ApiStatus;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * *ALL* things are synchronized.
 */
@ApiStatus.AvailableSince("0.1.0")
public final class TickManager {
    private final SchedulerAdapter adapter;
    private final ReferenceQueue<TickReceipt<?>> refTrashQueue = new ReferenceQueue<>();
    private final List<WeakReference<TickReceipt<?>>> receipts = new ArrayList<>();

    /**
     * Get the scheduler.
     */
    @Getter
    private final Scheduler scheduler;

    public TickManager() {
        var oyster = Oyster.getPlugin(Oyster.class);
        this.scheduler = new SyncScheduler();
        this.adapter = new SchedulerAdapter(scheduler);
        adapter.runTaskTimer(oyster, 0L, 1L);
        Bukkit.getScheduler().runTaskTimer(oyster, () -> {
            // Remove trashes.
            var ref = refTrashQueue.poll();
            while (ref != null) {
                java.lang.ref.Reference<? extends TickReceipt<?>> finalRef = ref;
                receipts.removeIf(i -> i.get() == finalRef.get());
                ref = refTrashQueue.poll();
            }
        }, 0L, 300 * 20L);
    }

    /**
     * Add a receipt. *Weak Reference*
     *
     * @param tickReceipt
     */
    public void addReceipt(TickReceipt<?> tickReceipt) {
        receipts.add(new WeakReference<>(tickReceipt));
    }

    /**
     * Stream for receipts.
     *
     * @return
     */
    public Stream<WeakReference<TickReceipt<?>>> receiptStream() {
        return receipts.stream().filter(e -> !e.get().isDropped());

    }

    /**
     * Might cause {@link ClassCastException}.
     *
     * @param name
     * @param typeOfT
     * @param <T>
     * @return
     */
    @SuppressWarnings("all")
    public <T> Optional<? extends TickReceipt<T>> getReceipt(String name, Class<T> typeOfT) {
        return (Optional<? extends TickReceipt<T>>) getReceipt(name);
    }

    /**
     * Find or {@link AssertionError}. Might cause {@link ClassCastException}
     * ONLY use it when you exactly knew your receipt.
     *
     * @param name
     * @param typeOfT
     * @param <T>
     * @return
     */
    @SuppressWarnings("all")
    public <T> TickReceipt<T> getReceiptExactly(String name, Class<T> typeOfT) {
        return getReceipt(name, typeOfT).orElseThrow(AssertionError::new);
    }

    /**
     * Find a receipt by name.
     *
     * @param name
     * @return
     */
    public Optional<? extends TickReceipt<?>> getReceipt(String name) {
        return receipts.stream().map(Reference::get).filter(e -> e.name().equals(name) && !e.isDropped()).findFirst();
    }
}
