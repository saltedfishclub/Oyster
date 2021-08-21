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

package cc.sfclub.game.mechanic;

import org.jetbrains.annotations.ApiStatus;

/**
 * Mechanic，也就是机能，实际上处理或者是作出了「对应对象的动作」的控制者。
 * 对于一个 Mechanic，他将具有以下条件...
 * <p>
 * - 感知到事件的存在
 * - 感知到 tick 的发生
 * <p>
 * 他总是在 tick 发生时作出动作，像 Minecraft 的大多数逻辑一样，像是...
 * <p>
 * - 对某个`标签`对应的所有对象作出动作
 * - 根据游戏时间进行动作
 * - 让你家土豆长快点
 * <p>
 * 不光光是游戏。玩家，或者任何游戏要素都可以拥有 Mechanic 来比较灵活的控制游戏内容。
 *
 * @param <ProcessTarget> Target. Such as OysterPlayer, OysterGame
 */
@ApiStatus.AvailableSince("0.1.0")
public interface Mechanic<ProcessTarget> extends Tickable<ProcessTarget>, EventReactor {
}
