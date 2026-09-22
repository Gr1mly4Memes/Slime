/*
 * Copyright (C) Gr1mly4Memes.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package gr1mly4memes.slime.neoforge;

import gr1mly4memes.slime.SlimeLogger;
import gr1mly4memes.slime.bukkit.neoforge.BukkitPermissionsHandler;
import net.neoforged.neoforge.server.permission.events.PermissionGatherEvent;

/**
 * Registers Slime's Bukkit-backed permission handler with NeoForge's permission API.
 *
 * <p>Previously {@link BukkitPermissionsHandler} was written but never registered, so NeoForge mod
 * permission nodes always fell back to their own default resolver and never saw the server's
 * permission plugin. Registration is deliberate but <em>opt-in</em>: the handler merely becomes
 * selectable, and NeoForge's {@code permissionHandler} config still defaults to
 * {@code neoforge:default_handler}. Set {@code permissionHandler="slime:permission"} to use it.
 */
public final class SlimePermissionBridge {

    private SlimePermissionBridge() {
    }

    /**
     * Must run before {@code PermissionAPI.initializePermissionAPI()}, which happens in
     * {@code ServerLifecycleHooks.handleServerStarting} — i.e. after the Bukkit server (and with it
     * the CraftServer constructor) has been created.
     */
    public static void register() {
        try {
            net.neoforged.neoforge.common.NeoForge.EVENT_BUS.addListener(
                    PermissionGatherEvent.Handler.class,
                    event -> event.addPermissionHandler(BukkitPermissionsHandler.IDENTIFIER, BukkitPermissionsHandler::wrapping));
        } catch (RuntimeException e) {
            // Never fail server startup over an optional permission handler.
            SlimeLogger.LOGGER.warning("Could not register the Slime permission handler, mod permissions will use NeoForge's default resolver", e);
        }
    }
}
