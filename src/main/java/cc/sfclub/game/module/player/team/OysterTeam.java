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

package cc.sfclub.game.module.player.team;

import cc.sfclub.game.mechanic.Flaggable;
import cc.sfclub.game.mechanic.GameEvent;
import cc.sfclub.game.mechanic.Mechanic;
import cc.sfclub.game.module.flag.Flag;
import cc.sfclub.game.module.player.OysterPlayer;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 一个 Team 代表了一组人。他们具有共享的 Flags ，但也可以有独立的个体产生额外的 Flags。
 * 多人游戏中，Team 总是要互相协作工作，他们之间的许多资源将会是共享的（当然，这个也由 Flag 来控制。）
 * 通常来说，个人拥有的 Flag 在判断时总会覆盖团队的通用 Flag。
 * <p>
 * Also see {@link Flag} and {@link OysterPlayer}
 */
@ApiStatus.AvailableSince("0.1.0")
@Getter
@RequiredArgsConstructor
public class OysterTeam implements Flaggable<OysterPlayer>, Mechanic<OysterTeam> {
    /**
     * 队伍内的玩家列表
     */
    private final Set<OysterPlayer> players;
    /**
     * 队伍所持有的标签
     */
    private final Set<Flag<OysterPlayer>> flags;
    /**
     * 队伍名称
     */
    private final String name;
    /**
     * 队伍在可以被染色的地方出现时候的颜色
     */
    private final ChatColor color;
    /**
     * 队伍的机能
     * Also see {@link Mechanic}
     */
    private final TeamMechanic mechanic;

    /**
     * 广播信息
     *
     * @param args 翻译键和参数
     */
    public void broadcastMessages(Object... args) {
        players.forEach(e -> e.sendTranslated(args));
    }

    @Override
    public void removeFlag(Flag<OysterPlayer> flag) {
        flags.remove(flag);
    }

    @Override
    public boolean addFlag(Flag<OysterPlayer> flag) {
        return flags.add(flag);
    }

    @Override
    public @Nullable Flag<OysterPlayer> getFlagExact(@NonNull String name) {
        return flags.stream().filter(e -> e.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public @NotNull List<Flag<OysterPlayer>> matchingFlags(@NonNull String prefixOrRegex, boolean regex) {
        return regex ? flags.stream().filter(e -> e.getName().matches(prefixOrRegex)).collect(Collectors.toList()) : flags.stream().filter(e -> e.getName().startsWith(prefixOrRegex)).collect(Collectors.toList());
    }

    @Override
    public void onData(GameEvent event) {
        mechanic.onData(event);
    }

    @Override
    public void onUpdate(OysterTeam object) {
        mechanic.onUpdate(object);
    }
}
