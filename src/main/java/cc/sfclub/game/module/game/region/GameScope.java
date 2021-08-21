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

package cc.sfclub.game.module.game.region;

import org.bukkit.Location;
import org.jetbrains.annotations.ApiStatus;

/**
 * GameScope 是一块抽象的区域，`对于一个房间`，所有的游戏逻辑都将会在 GameScope 中发生。
 * 这通常适用于一些小游戏，例如`搭路练习`，但并不适用于**需要较多场地和独立条件**的游戏，例如`ManiHunt`
 * 为了使 Oyster 能够更为灵活，我们将选择的权利交给 `游戏` 而不是框架本身。Oyster 允许游戏设置对于一个服务器的 GameScope
 * 最大承载量，并且将允许小游戏如何对场地进行划分。（区块组合，单个区块，区段，三个世界为一组，...）
 * <p>
 * **WIP** // TODO: 21/08/2021
 */
@ApiStatus.AvailableSince("0.1.0")
public interface GameScope {
    boolean isInScope(Location location);

    Location getWarp(String warpName);

    void setWarp(String warpName, Location location);

    boolean hasWarp(String warp);
}
