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

package gr1mly4memes.slime.eventhandler.dispatcher;

import com.mojang.datafixers.util.Either;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerEventDispatcher {

    /**
     * Guards against re-entrancy: returning a dropped stack to the inventory calls
     * {@code player.drop(...)}, which fires another {@link ItemTossEvent} and would recurse
     * until the stack overflows if a plugin cancels every drop.
     */
    private static final ThreadLocal<Boolean> RESTORING_DROP = ThreadLocal.withInitial(() -> Boolean.FALSE);

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onEnterSleepEvent(CanPlayerSleepEvent event) {
        var serverPlayer = event.getEntity();
        var blockposition = event.getPos();
        Either<net.minecraft.world.entity.player.Player.BedSleepingProblem, Unit> nmsBedResult = event.getProblem() != null ? Either.left(event.getProblem()) : Either.right(Unit.INSTANCE);
        var cbedResult = org.bukkit.craftbukkit.event.CraftEventFactory.callPlayerBedEnterEvent(serverPlayer, blockposition, nmsBedResult);
        if (cbedResult.left().isPresent()) {
            event.setProblem(cbedResult.left().get());
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void onItemTossEvent(ItemTossEvent event) {
        // Guard the cast: a modded FakePlayer (or any non-ServerPlayer implementation) used to
        // throw a ClassCastException from inside the NeoForge event bus, which kills the toss
        // and often the whole tick.
        if (!(event.getPlayer() instanceof ServerPlayer player)) {
            return;
        }
        ItemEntity itemEntity = event.getEntity();

        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.captureDrops = null;
        }

        org.bukkit.entity.Player bPlayer = player.getBukkitEntity();
        org.bukkit.entity.Item bItem = (org.bukkit.entity.Item) itemEntity.getBukkitEntity();

        PlayerDropItemEvent bukkitEvent = new PlayerDropItemEvent(bPlayer, bItem);
        Bukkit.getPluginManager().callEvent(bukkitEvent);

        if (!bukkitEvent.isCancelled()) {
            return;
        }

        event.setCanceled(true);
        ItemStack stack = itemEntity.getItem();
        if (RESTORING_DROP.get()) {
            // Already restoring a cancelled drop: give the stack back without re-entering the event.
            player.getInventory().add(stack);
            return;
        }
        RESTORING_DROP.set(Boolean.TRUE);
        try {
            if (!player.getInventory().add(stack)) {
                player.drop(stack, false, false, false, null);
            }
        } finally {
            RESTORING_DROP.remove();
        }
        player.containerMenu.broadcastChanges();
    }
}