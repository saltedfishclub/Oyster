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

package cc.sfclub.game.module.i18n;

import cc.sfclub.game.util.Log;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Properties;
import java.util.zip.ZipFile;

/**
 * 语言文件加载器。文件格式：https://github.com/saltedfishclub/Oyster/wiki/I18N-File-Format
 */
@ApiStatus.AvailableSince("0.1.0")
public class LocaleLoader {
    /**
     * 加载文件并且返回 {@link Locale}
     * 总不为 null，但可能跑出 RuntimeException (如果无法打开文件)
     *
     * @param zipFile 文件
     * @return locale
     */
    public static final Locale loadAsLocale(ZipFile zipFile) {
        var entries = zipFile.entries();
        Properties fallback = null;
        var locales = new HashMap<String, Properties>();

        while (entries.hasMoreElements()) {
            var entry = entries.nextElement();

            var property = new Properties();

            try {
                property.load(zipFile.getInputStream(entry));
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.warn("Can't load " + entry.getName() + " as a locale. " + entry.getName());
                throw new RuntimeException(ex);
            }

            var fileNames = entry.getName().split("\\.");
            if (fileNames.length != 2) {
                Log.warn("Invalid file: " + entry.getName());
                continue;
            }
            var fileName = fileNames[0];
            if (fileName.startsWith("default_")) {
                fallback = property;
            }

            locales.put(entry.getName(), property);
        }

        if (fallback == null) {
            throw new RuntimeException("There is not a default locale file.");
        }

        return new Locale(fallback, locales);
    }
}
