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

import cc.sfclub.game.mechanic.Flaggable;
import cc.sfclub.game.mechanic.GameEvent;
import cc.sfclub.game.module.flag.Flag;
import cc.sfclub.game.module.game.region.AnywhereScope;
import cc.sfclub.game.module.game.region.GameScope;
import cc.sfclub.game.module.i18n.Locale;
import cc.sfclub.game.module.player.OysterPlayer;
import cc.sfclub.game.module.player.team.OysterTeam;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Builder
public class OysterGame extends GameMechanic implements Flaggable<OysterGame> {
    private final State state;
    @Builder.Default
    private final GameScope scope = new AnywhereScope();
    @Builder.Default
    private final List<OysterPlayer> players = new ArrayList<>();
    @Builder.Default
    private final Locale locale = new Locale();

    @Builder.Default
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

    @Override
    public boolean addFlag(Flag<OysterGame> flag) {
        return rules.add(flag);
    }

    @Override
    public @Nullable Flag<OysterGame> getFlag(@NonNull String name) {
        return rules.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst().orElse(null);
    }
}