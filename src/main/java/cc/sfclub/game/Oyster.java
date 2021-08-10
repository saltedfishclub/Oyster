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

package cc.sfclub.game;

import cc.sfclub.game.module.i18n.LocaleLoader;
import cc.sfclub.game.util.Log;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.zip.ZipFile;

public final class Oyster extends JavaPlugin {

    @SneakyThrows
    @Override
    public void onEnable() {
        Log.info(getDescription().getDescription());
        Log.info("Extracting Internal Locales...");
        extractLangs();
        Log.defaultLang = "en_US";
        Log.transInfo("oyster.loading", "");
        Log.transInfo("oyster.err", "");
        Log.transInfo("oyster.succ", "");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @SneakyThrows
    private void extractLangs() {
        saveResource("locale.zip", true);
        Log.defaultLocale = LocaleLoader.loadAsLocale(new ZipFile(new File(getDataFolder(), "locale.zip")));
    }
}
