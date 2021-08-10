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

package cc.sfclub.game.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class SimpleConfig<C> {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String root;
    private final Class<C> clazz;
    private C configObj;
    @Setter
    @Getter
    private String configFileName = "config.json";

    @SneakyThrows
    public SimpleConfig(File rootDir, Class<C> configClass) {
        rootDir.mkdirs();
        this.root = rootDir.getAbsolutePath();
        this.clazz = configClass;
        this.configObj = configClass.getDeclaredConstructor().newInstance();
        saveDefault();
        reloadConfig();
    }

    /**
     * Save Config to config.json
     */
    @SneakyThrows
    public void saveConfig() {
        Files.write(new File(root + "/" + getConfigFileName()).toPath(), gson.toJson(configObj).getBytes());
    }

    /**
     * 若文件不存在则保存默认
     */
    @SneakyThrows
    public void saveDefault() {
        Path a = new File(root + "/" + getConfigFileName()).toPath();
        a.getParent().toFile().mkdirs();
        if (!Files.exists(a)) {
            Files.write(a, gson.toJson(configObj).getBytes());
        }
    }

    /**
     * 获取包装对象
     *
     * @return
     */
    public C get() {
        return configObj;
    }

    public void set(C c) {
        this.configObj = c;
    }

    /**
     * Reload MainConfig
     */
    @SneakyThrows
    public void reloadConfig() {
        configObj = gson.fromJson(new BufferedReader(new FileReader(root + "/" + getConfigFileName())), clazz);
    }
}