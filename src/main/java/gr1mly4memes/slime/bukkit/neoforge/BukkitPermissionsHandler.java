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

package gr1mly4memes.slime.bukkit.neoforge;

import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.server.permission.handler.DefaultPermissionHandler;
import net.neoforged.neoforge.server.permission.handler.IPermissionHandler;
import net.neoforged.neoforge.server.permission.nodes.PermissionDynamicContext;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import net.neoforged.neoforge.server.permission.nodes.PermissionTypes;
import org.bukkit.Bukkit;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

/**
 * Bridges NeoForge's permission API onto Bukkit's, so NeoForge mod permission nodes resolve through
 * whatever permission plugin the server runs (LuckPerms, etc.) instead of each node's default
 * resolver.
 *
 * <p>Non-boolean permission nodes still fall through to the wrapped handler, because Bukkit
 * permissions are inherently boolean.
 *
 * <p>This handler is <em>opt-in</em>: it is registered under {@link #IDENTIFIER} but NeoForge's
 * {@code permissionHandler} server config still defaults to {@code neoforge:default_handler}.
 * Set {@code permissionHandler="slime:permission"} in the NeoForge server config to use it.
 */
public record BukkitPermissionsHandler(IPermissionHandler delegate) implements IPermissionHandler {

    public static final Identifier IDENTIFIER = Identifier.fromNamespaceAndPath("slime", "permission");

    /** Wraps the vanilla default handler so unhandled cases keep working. */
    public static BukkitPermissionsHandler wrapping(Collection<PermissionNode<?>> nodes) {
        return new BukkitPermissionsHandler(new DefaultPermissionHandler(nodes));
    }

    @Override
    public Identifier getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public Set<PermissionNode<?>> getRegisteredNodes() {
        return delegate.getRegisteredNodes();
    }

    @Override
    public <T> T getPermission(ServerPlayer player, PermissionNode<T> node, PermissionDynamicContext<?>... context) {
        if (node.getType() == PermissionTypes.BOOLEAN) {
            return node.getType().typeToken().cast(player.getBukkitEntity().hasPermission(node.getNodeName()));
        } else {
            return delegate.getPermission(player, node, context);
        }
    }

    @Override
    public <T> T getOfflinePermission(UUID uuid, PermissionNode<T> node, PermissionDynamicContext<?>... context) {
        var player = Bukkit.getPlayer(uuid);
        if (player != null && node.getType() == PermissionTypes.BOOLEAN) {
            return node.getType().typeToken().cast(player.hasPermission(node.getNodeName()));
        } else {
            return delegate.getOfflinePermission(uuid, node, context);
        }
    }
}
