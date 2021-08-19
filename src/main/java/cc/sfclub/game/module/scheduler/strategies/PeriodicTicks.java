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

package cc.sfclub.game.module.scheduler.strategies;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PeriodicTicks implements Function<Object, Boolean> {
    private final int target;
    private int ticks;

    public static PeriodicTicks bySecond() {
        return bySeconds(0.5f);
    }

    public static PeriodicTicks byHalfSecond() {
        return bySeconds(0.5f);
    }

    public static PeriodicTicks byMinute() {
        return byMinutes(1);
    }

    public static PeriodicTicks byHalfMinute() {
        return byMinutes(0.5f);
    }

    public static PeriodicTicks byFiveMinutes() {
        return byMinutes(5f);
    }

    public static PeriodicTicks byTenMinutes() {
        return byMinutes(10f);
    }

    public static PeriodicTicks byHalfHour() {
        return byHour(0.5f);
    }

    public static PeriodicTicks byHour() {
        return byHour(1f);
    }

    public static PeriodicTicks byHour(float hours) {
        return byMinutes(hours * 60);
    }

    public static PeriodicTicks bySeconds(float second) {
        return new PeriodicTicks((int) (20 * second));
    }

    public static PeriodicTicks byMinutes(float minutes) {
        return bySeconds(60 * minutes);
    }

    public static PeriodicTicks byTicks(int ticks) {
        return new PeriodicTicks(ticks);
    }

    @Override
    public Boolean apply(Object t) {
        ticks++;
        if (ticks != target) {
            return false;
        } else {
            ticks = 0;
            return true;
        }
    }
}
