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

package cc.sfclub.game.module.event;

import cc.sfclub.game.mechanic.ChannelSubscriber;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据管道，可传输事件或者别的什么，对象可能在传输过程中被更改。
 */
@ApiStatus.AvailableSince("0.1.0")
public class Channel<T extends Listenable> {
    private final List<ChannelSubscriber<T>> subscribers = new ArrayList<>();

    /**
     * Post an event to all reactors. Not a bukkit interaction.
     *
     * @param event event to post
     * @return param
     */
    public T post(T event) {
        for (ChannelSubscriber<T> subscriber : subscribers) {
            subscriber.onData(event);
        }
        return event;
    }

    /**
     * Register a reactor.
     *
     * @param reactor
     */
    public void register(ChannelSubscriber<T> reactor) {
        subscribers.add(reactor);
    }

    /**
     * Unregister a reactor.
     *
     * @param reactor
     */
    public void unregister(ChannelSubscriber<T> reactor) {
        subscribers.remove(reactor);
    }
}
