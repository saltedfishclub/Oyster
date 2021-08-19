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

package cc.sfclub.game.task;

import cc.sfclub.game.util.Log;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class UpdateChecker extends BukkitRunnable {
    private static final JsonParser jsonParser = new JsonParser();
    private final HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(6)).build();
    private static final String URL_UPDATE_CHECK = "https://api.github.com/repos/saltedfishclub/Oyster/releases";
    private int retries = 0;
    @Override
    public void run() {
        Log.transInfo("oyster.update.checking");
        try {
            var request = HttpRequest.newBuilder().GET().uri(new URI(URL_UPDATE_CHECK)).build();
            var body = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
            var ja = jsonParser.parse(body == null ? "[]" : body);
            for (JsonElement jsonElement : ja.getAsJsonArray()) {
                var jo = jsonElement.getAsJsonObject();
                var branch = jo.get("target_commitish").toString();
                var name = jo.get("name").toString();
                var tag = jo.get("tag_name").toString();
                var isDraftOrPreRelease = jo.get("draft").getAsBoolean() || jo.get("prerelease").getAsBoolean();
                if (isDraftOrPreRelease || !"release".equals(branch)) {
                    return;
                }
                Log.transInfo("oyster.update.hint", tag, name);
                return;
            }
        } catch (Throwable t) {
            retries++;
            Log.transInfo("oyster.update.error", t.getMessage(), retries);
        }
    }
}
