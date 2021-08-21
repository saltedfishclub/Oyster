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
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * Oyster 国际化模块
 */
@ApiStatus.AvailableSince("0.1.0")
@AllArgsConstructor
public class Locale {
    @NonNull
    private Properties fallback;
    private final Map<String, Properties> locales;

    private Properties getLocale(String locale) {
        return locales.getOrDefault(locale, fallback);
    }

    /**
     * 翻译`翻译键`并且填充参数。
     * 关于参数如何填充进入翻译文本：https://segmentfault.com/a/1190000013654676
     *
     * @param locale     语言
     * @param keyAndArgs 翻译键和参数，可以只需要翻译键
     * @return 翻译后的文本，如果只写了语言就会返回null，其他情况下永远不是 null
     */
    @Nullable
    public String translate(String locale, Object... keyAndArgs) {
        if (keyAndArgs.length < 1) {
            Log.warn("Misuse of transInfo(...)!");
            return null;
        }
        Object msg = keyAndArgs[0];
        if (msg instanceof String) {
            String key = (String) msg;
            if (keyAndArgs.length == 1) {
                ChatColor.translateAlternateColorCodes('&', getLocale(locale).getProperty(key, fallback.getProperty(key, ChatColor.RED + "Invalid: " + key + ChatColor.RESET)));
            }
            Object[] args = Arrays.copyOfRange(keyAndArgs, 1, keyAndArgs.length);
            return ChatColor.translateAlternateColorCodes('&', String.format(getLocale(locale).getProperty(key, fallback.getProperty(key, ChatColor.RED + "Invalid: " + key + ChatColor.RESET)), args));
        } else {
            return msg.toString();
        }
    }

    /**
     * 注册语言，覆盖时有警告
     *
     * @param lang   语言
     * @param locale 语言文本
     */
    public void registerLocale(String lang, Properties locale) {
        if (locales.containsKey(lang)) {
            Log.warn("Duplicate locale was found! " + lang);
        }
        locales.put(lang, locale);
    }

    /**
     * 设置 fallback 文本，如果是null则从这里取。
     *
     * @param fallback
     */
    public void setFallback(Properties fallback) {
        this.fallback = fallback;
    }
}
