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

package cc.sfclub.game.module.game.desc;

import cc.sfclub.game.module.game.State;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

@ApiStatus.AvailableSince("0.1.0")
@Getter
@Builder
public class GameDescription {
    private final String name;
    private final String version;
    private final Supplier<State> stateSupplier;
    private final ScopeType scopeType;
}
