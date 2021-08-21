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

import cc.sfclub.game.mechanic.EventReactor;
import cc.sfclub.game.mechanic.GameEvent;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple event bus
 */
@ApiStatus.AvailableSince("0.1.0")
public class EventManager {
    private final List<EventReactor> subscribers = new ArrayList<>();

    /**
     * Post an event to all reactors. Not a bukkit interaction.
     *
     * @param event event to post
     * @param <T>   type of event
     * @return param
     */
    public <T extends GameEvent> T post(T event) {
        for (EventReactor subscriber : subscribers) {
            subscriber.onEvent(event);
        }
        return event;
    }

    /**
     * Register a reactor.
     *
     * @param reactor
     */
    public void register(EventReactor reactor) {
        subscribers.add(reactor);
    }

    /**
     * Unregister a reactor.
     *
     * @param reactor
     */
    public void unregister(EventReactor reactor) {
        subscribers.remove(reactor);
    }
}
