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
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private final List<EventReactor> subscribers = new ArrayList<>();

    public <T extends GameEvent> T post(T event) {
        for (EventReactor subscriber : subscribers) {
            subscriber.onEvent(event);
        }
        return event;
    }

    public void post(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }

    public void register(EventReactor reactor) {
        subscribers.add(reactor);
    }

    public void unregister(EventReactor reactor) {
        subscribers.remove(reactor);
    }
}
