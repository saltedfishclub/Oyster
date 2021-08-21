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

package cc.sfclub.game.module.game;

import cc.sfclub.game.api.event.GameStateSwitched;
import cc.sfclub.game.managers.EventManager;
import cc.sfclub.game.managers.TickManager;
import cc.sfclub.game.mechanic.Flaggable;
import cc.sfclub.game.mechanic.GameEvent;
import cc.sfclub.game.module.flag.Flag;
import cc.sfclub.game.module.game.region.AnywhereScope;
import cc.sfclub.game.module.game.region.GameScope;
import cc.sfclub.game.module.player.OysterPlayer;
import cc.sfclub.game.module.player.team.OysterTeam;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

@ApiStatus.AvailableSince("0.1.0")
@Builder
public class OysterGame extends GameMechanic implements Flaggable<OysterGame> {
    @Getter
    private final String name;

    @Builder.Default
    @Getter
    private final GameScope scope = new AnywhereScope();
    @Builder.Default
    @Getter
    private final List<OysterPlayer> players = new ArrayList<>();
    @Getter
    private State state;

    @Builder.Default
    @Getter
    private final Map<String, OysterTeam> teams = new HashMap<>();
    @Getter
    @Builder.Default
    private final Set<Flag<OysterGame>> rules = new TreeSet<>((a, b) -> {
        int v = a.getPriority() - b.getPriority();
        if (v == 0) {
            return 1;
        }
        return v;
    });
    @Builder.Default
    private final GameMechanic mechanic = new EmptyGameMechanic();
    @Builder.Default
    @Getter
    private final TickManager tickManager = new TickManager();
    @Builder.Default
    @Getter
    private final EventManager eventBus = new EventManager();

    public <T> T getStateAs(Class<T> t) {
        return t.cast(state);
    }

    @Override
    public void onEvent(GameEvent event) {
        mechanic.onEvent(event);
    }

    @Override
    public void tick(OysterGame game) {
        mechanic.onUpdate(game);
    }

    @Override
    public Set<Flag<OysterGame>> getFlags() {
        return rules;
    }

    @Override
    public void removeFlag(Flag<OysterGame> flag) {
        rules.remove(flag);
    }

    public void switchState(State newState) {
        Bukkit.getPluginManager().callEvent(new GameStateSwitched(this, this.state, newState));
        this.state = newState;
    }

    @Override
    public boolean addFlag(Flag<OysterGame> flag) {
        return rules.add(flag);
    }

    @Override
    public @Nullable Flag<OysterGame> getFlagExact(@NonNull String name) {
        return rules.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst().orElse(null);
    }

    public void broadcastMessage(Object args) {
        players.forEach(e -> e.sendTranslated(args));
    }

    @Override
    public List<Flag<OysterGame>> matchingFlags(@NonNull String prefixOrRegex, boolean regex) {
        return regex ? rules.stream().filter(e -> e.getName().matches(prefixOrRegex)).collect(Collectors.toList()) : rules.stream().filter(e -> e.getName().startsWith(prefixOrRegex)).collect(Collectors.toList());
    }
}
