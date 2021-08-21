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
 * Tick 管理器，所有对于他的操作都应该是同步的。
 * 每一个 Tick Manager 均会运行一个新的 Scheduler，请避免创建 Tick Manager。
 */
@ApiStatus.AvailableSince("0.1.0")
public final class TickManager {
    private final SchedulerAdapter adapter;
    private final ReferenceQueue<TickReceipt<?>> refTrashQueue = new ReferenceQueue<>();
    private final List<WeakReference<TickReceipt<?>>> receipts = new ArrayList<>();

    /**
     * 获取到Scheduler/调度器对象
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
     * 添加一个 Tick 回执，弱引用储存，请自行注意GC
     * Also see {@link TickReceipt}
     *
     * @param tickReceipt
     */
    public void addReceipt(TickReceipt<?> tickReceipt) {
        receipts.add(new WeakReference<>(tickReceipt));
    }

    /**
     * 回执流
     * Also see {@link TickReceipt}
     *
     * @return
     */
    public Stream<WeakReference<TickReceipt<?>>> receiptStream() {
        return receipts.stream().filter(e -> !e.get().isDropped());

    }

    /**
     * 可能导致 {@link ClassCastException}.
     * 适用于你知道名字但是不确定他是否被回收的情况。
     * Also see {@link TickReceipt}
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
     * 精确查找并直接返回结果，可能导致 {@link AssertionError} 或 {@link ClassCastException}
     * **只在你完全清楚情况的情况下使用他**
     * Also see {@link TickReceipt}
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
     * 通过名字查找回执，没有类型转型也不确保能找到。
     * Also see {@link TickReceipt}
     *
     * @param name
     * @return
     */
    public Optional<? extends TickReceipt<?>> getReceipt(String name) {
        return receipts.stream().map(Reference::get).filter(e -> name.equals(e.name()) && !e.isDropped()).findFirst();
    }

    //todo matchesReceipt
}
