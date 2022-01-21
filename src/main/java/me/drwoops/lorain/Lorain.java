/*
 Copyright (c) 2021 by drwoops <thedrwoops@gmail.com>

 This file is part of Lorain.

 Lorain is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Lorain is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with Foobar.  If not, see <https://www.gnu.org/licenses/>.
*/
package me.drwoops.lorain;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ThreadLocalRandom;

public final class Lorain extends JavaPlugin implements Listener {

    double cancellation_probability;

    @Override
    public void onEnable() {
        // get config defaults
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        cancellation_probability = getConfig().getDouble("cancellation-probability");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = true)
    public void onWeatherChange(WeatherChangeEvent event) {
        // only step in if rain is about to start naturally
        if (event.getCause() == WeatherChangeEvent.Cause.NATURAL && event.toWeatherState()) {
            if (ThreadLocalRandom.current().nextDouble() <= cancellation_probability) {
                event.setCancelled(true);
                getLogger().info("rain was cancelled");
            } else {
                getLogger().info("rain was not cancelled");
            }
        }
    }
}
