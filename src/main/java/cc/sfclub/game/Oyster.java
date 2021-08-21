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

import cc.sfclub.game.api.OysterAPI;
import cc.sfclub.game.config.OysterConfig;
import cc.sfclub.game.module.i18n.LocaleLoader;
import cc.sfclub.game.task.UpdateChecker;
import cc.sfclub.game.util.Log;
import cc.sfclub.game.util.SimpleConfig;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;

import java.io.File;
import java.util.zip.ZipFile;

/**
 * Low level API, avoid using it's method directly.
 */
@ApiStatus.AvailableSince("0.1.0")
public final class Oyster extends JavaPlugin {
    private SimpleConfig<OysterConfig> wrappedConfig;
    private volatile boolean loadLock = false;
    @Getter
    private OysterAPI api;

    @SneakyThrows
    @Override
    @SuppressWarnings("all")
    public void onEnable() {
        if (loadLock) {
            Log.transInfo("oyster.error.dont_reload");
            setEnabled(false);
            return;
        }
        loadLock = true;
        Log.info(getDescription().getDescription());
        Log.info("Extracting Internal Locales...");
        extractLangs();
        wrappedConfig = new SimpleConfig<>(getDataFolder(), OysterConfig.class);
        wrappedConfig.saveDefault();
        wrappedConfig.reloadConfig();
        Log.defaultLang = getOysterConfig().getLanguage();
        Log.transInfo("oyster.config.loaded", Log.defaultLang);
        initiateAPI();
        if (getOysterConfig().isUpdateCheck()) {
            new UpdateChecker().runTaskTimerAsynchronously(this, 0L, 300 * 20L);
        }
    }

    private void initiateAPI() {
        api = new OysterAPI();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public OysterConfig getOysterConfig() {
        return wrappedConfig.get();
    }

    @SneakyThrows
    private void extractLangs() {
        saveResource("locale.zip", true);
        Log.defaultLocale = LocaleLoader.loadAsLocale(new ZipFile(new File(getDataFolder(), "locale.zip")));
    }
}
