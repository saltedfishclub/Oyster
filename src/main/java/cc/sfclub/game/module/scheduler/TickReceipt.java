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

import cc.sfclub.game.managers.TickManager;
import cc.sfclub.game.mechanic.Tickable;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 注册 tick 后产生的回执
 * Also see {@link Scheduler#add(Tickable)}
 *
 * @param <T> Tick Target
 */
@ApiStatus.AvailableSince("0.1.0")
public class TickReceipt<T> {
    private final List<AwaitingTickable<T>> syncs = new ArrayList<>(); //todo Flattening
    private final List<AwaitingTickable<T>> always = new ArrayList<>();
    private Function<T, Boolean> requirement;
    private boolean dropped = false;
    private String name = null;

    /**
     * 包装。
     * Also see {@link cc.sfclub.game.module.scheduler.strategies.PeriodicTicks} and {@link cc.sfclub.game.module.scheduler.strategies.RequirementComposer}
     *
     * @param consumer
     * @return
     */
    public TickReceipt<T> requires(Supplier<Function<T, Boolean>> consumer) {
        Validate.notNull(consumer);
        return requires(consumer.get());
    }

    /**
     * 触发 tick 的先决条件。
     * 每个回执只能设置有一个条件，多个以最后插入的为准 , 可以通过 {@link cc.sfclub.game.module.scheduler.strategies.RequirementComposer} 或者 {@link Function#andThen(Function)} 合并多个条件
     * 设置触发更新的间隔: {@link cc.sfclub.game.module.scheduler.strategies.PeriodicTicks}
     *
     * @param func
     * @return
     */
    public TickReceipt<T> requires(Function<T, Boolean> func) {
        Validate.notNull(func);
        this.requirement = func;
        return this;
    }

    /**
     * 在回执目标运行的时候顺便运行新的 tickable，可以用于同步多个实体之间的动作
     * 与 {@link this#alwaysTicks(Tickable)} 不同，（如果有的话）他必须在 {@link this#requires(Function)} 通过后才运行
     * 这意味着可能会受到 {@link cc.sfclub.game.module.scheduler.strategies.PeriodicTicks} 一类的影响
     *
     * @param tickable
     * @return 新 tickable 的回执，用于更方便的链式调用
     */
    public TickReceipt<T> alsoTicks(Tickable<T> tickable) {
        Validate.notNull(tickable);
        var receipt = new TickReceipt<T>();
        syncs.add(new AwaitingTickable<>(tickable, receipt));
        return receipt;
    }

    /**
     * 在回执目标运行的时候顺便运行新的 tickable，可以用于同步多个实体之间的动作
     * 和 {@link this#alsoTicks(Tickable)} 不同，它无视条件触发。
     *
     * @param tickable
     * @return 新的回执，用于更方便的链式调用
     */
    public TickReceipt<T> alwaysTicks(Tickable<T> tickable) {
        Validate.notNull(tickable);
        var receipt = new TickReceipt<T>();
        always.add(new AwaitingTickable<>(tickable, receipt));
        return receipt;
    }

    /**
     * 在回执目标运行的时候顺便运行新的 tickable，可以用于同步多个实体之间的动作
     * 与 {@link this#alsoTicks(Tickable)} 和 {@link this#alwaysTicks(Tickable)} 不同，他不会返回新的回执，但是会受到同一个条件 ({@link this#requires(Function)}) 的影响。
     *
     * @param tickable
     * @return 自身，用于更方便的链式调用
     */
    public TickReceipt<T> syncWith(Tickable<T> tickable) {
        Validate.notNull(tickable);
        alsoTicks(tickable);
        return this;
    }

    /**
     * 设置回执的名字
     * Also see {@link cc.sfclub.game.managers.TickManager#getReceipt(String)}
     *
     * @param name receipt name
     * @return 自身
     */
    public TickReceipt<T> name(String name) {
        Validate.notNull(name);
        this.name = name;
        return this;
    }

    /**
     * 标记回执已经被抛弃，将会被垃圾处理器回收。
     * Also see {@link this#isDropped()}
     */
    public void drop() {
        this.dropped = true;
    }

    /**
     * 是否已经被抛弃，通常不应该继续保留被抛弃的回执的引用，其次你将无法通过 {@link TickManager#receiptStream()} 等方式获取到他
     *
     * @return
     */
    public boolean isDropped() {
        return this.dropped;
    }

    /**
     * 获取回执名字
     *
     * @return
     */
    @Nullable
    public String name() {
        return name;
    }

    @SuppressWarnings("all")
    protected boolean tick(Object Ot) {
        T t = (T) Ot;
        if (always.size() != 0) {
            for (AwaitingTickable<T> alway : always) {
                alway.tick(t);
            }
        }
        if (requirement == null || requirement.apply(t)) {
            if (syncs.size() != 0) {
                for (AwaitingTickable<T> sync : syncs) {
                    sync.tick(sync);
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
