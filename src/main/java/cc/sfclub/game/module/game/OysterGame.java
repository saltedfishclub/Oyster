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
import cc.sfclub.game.managers.TickManager;
import cc.sfclub.game.mechanic.Flaggable;
import cc.sfclub.game.mechanic.GameEvent;
import cc.sfclub.game.module.event.Channel;
import cc.sfclub.game.module.flag.Flag;
import cc.sfclub.game.module.game.desc.GameDescription;
import cc.sfclub.game.module.game.region.AnywhereScope;
import cc.sfclub.game.module.game.region.GameScope;
import cc.sfclub.game.module.i18n.Locale;
import cc.sfclub.game.module.player.OysterPlayer;
import cc.sfclub.game.module.player.team.OysterTeam;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Oyster 实际的游戏对象。
 * <p>
 * # Game
 * <p>
 * 一个游戏，由参与者，阶段和状态组成。
 * 状态是由游戏提供的(实现 {@link State})，可以用来储存数据或者别的什么东西。
 * <p>
 * - NG 词
 * - ...
 * <p>
 * # Complex Game
 * <p>
 * ~~对于复杂的游戏，自然不能只有这么点东西~~
 * 对于参与者和更加复杂的游戏规则，可以由阵营，队伍，规则与场地组成，不过他们都是可选的。
 * 对于所有的`机能`，我们将抽象为 ` {@link cc.sfclub.game.mechanic.Mechanic} `
 */
@ApiStatus.AvailableSince("0.1.0")
@Builder
public class OysterGame extends GameMechanic implements Flaggable<OysterGame> {
    /**
     * 获取游戏的名字，总是随机的字符串。
     */
    @Getter
    private final String name;
    /**
     * 游戏的原型
     */
    @Getter
    private final GameDescription protoType;

    @Getter
    private final Locale locale;
    /**
     * 活动范围
     */
    @Builder.Default
    @Getter
    private final GameScope scope = new AnywhereScope();
    /**
     * 游玩的参与者
     */
    @Builder.Default
    @Getter
    private final List<OysterPlayer> players = new ArrayList<>();
    /**
     * 队伍（可选）
     */
    @Builder.Default
    @Getter
    private final Map<String, OysterTeam> teams = new HashMap<>();
    /**
     * 规则，实质上是 Flag
     * (可选)
     */
    @Getter
    @Builder.Default
    private final Set<Flag<OysterGame>> rules = new TreeSet<>((a, b) -> {
        int v = a.getPriority() - b.getPriority();
        if (v == 0) {
            return 1;
        }
        return v;
    });
    /**
     * 游戏的机能。
     */
    @Builder.Default
    private final GameMechanic mechanic = new EmptyGameMechanic();
    /**
     * 游戏的 tick 调度管理器
     * Also see {@link cc.sfclub.game.module.scheduler.TickReceipt}
     */
    @Builder.Default
    @Getter
    private final TickManager tickManager = new TickManager();
    /**
     * 游戏的事件管道。
     */
    @Builder.Default
    @Getter
    private final Channel<GameEvent> eventBus = new Channel<>();
    /**
     * GameState
     */
    @Getter
    private State state;

    /**
     * 提供了自动强转获取State的方法，可能CCE
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T> T getStateAs(Class<T> t) {
        Validate.notNull(t);
        return t.cast(state);
    }

    @Override
    public void onData(GameEvent event) {
        mechanic.onData(event);
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
        Validate.notNull(flag);
        rules.remove(flag);
    }

    /**
     * 切换状态
     *
     * @param newState
     */
    public void switchState(State newState) {
        Validate.notNull(newState);
        Bukkit.getPluginManager().callEvent(new GameStateSwitched(this, this.state, newState));
        newState.init(this);
        this.state.onSwitch(newState);
        this.state = newState;
    }

    @Override
    public boolean addFlag(Flag<OysterGame> flag) {
        Validate.notNull(flag);
        return rules.add(flag);
    }

    @Override
    public @Nullable Flag<OysterGame> getFlagExact(@NonNull String name) {
        Validate.notNull(name);
        return rules.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst().orElse(null);
    }

    /**
     * 广播信息，只能使用翻译键。
     * Also see {@link cc.sfclub.game.module.i18n.Locale}
     *
     * @param args
     */
    public void broadcastMessage(Object args) {
        players.forEach(e -> e.sendTranslated(args));
    }

    @Override
    public @NotNull List<Flag<OysterGame>> matchingFlags(@NonNull String prefixOrRegex, boolean regex) {
        return regex ? rules.stream().filter(e -> e.getName().matches(prefixOrRegex)).collect(Collectors.toList()) : rules.stream().filter(e -> e.getName().startsWith(prefixOrRegex)).collect(Collectors.toList());
    }
}
