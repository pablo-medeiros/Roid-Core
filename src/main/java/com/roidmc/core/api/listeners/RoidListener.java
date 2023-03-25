package com.roidmc.core.api.listeners;

import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.event.server.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.world.*;
import org.bukkit.event.block.*;
import org.bukkit.event.enchantment.*;
import org.bukkit.event.hanging.*;
import org.bukkit.event.vehicle.*;
import org.bukkit.event.weather.*;

public class RoidListener implements Listener {


    public void onVehicleExit(VehicleExitEvent e){}

    public void onVehicleEntityCollision(VehicleEntityCollisionEvent e){}

    public void onVehicleEnter(VehicleEnterEvent e){}

    public void onVehicleMove(VehicleMoveEvent e){}

    public void onVehicleUpdate(VehicleUpdateEvent e){}

    public void onVehicleBlockCollision(VehicleBlockCollisionEvent e){}

    public void onVehicleDamage(VehicleDamageEvent e){}

    public void onVehicleCreate(VehicleCreateEvent e){}

    public void onVehicleDestroy(VehicleDestroyEvent e){}

    public void onPlayerItemConsume(PlayerItemConsumeEvent e){}

    public void onPlayerTeleport(PlayerTeleportEvent e){}

    public void onPlayerChangedWorld(PlayerChangedWorldEvent e){}

    public void onPlayerEggThrow(PlayerEggThrowEvent e){}

    public void onPlayerToggleSneak(PlayerToggleSneakEvent e){}

    public void onPlayerPickupItem(PlayerPickupItemEvent e){}

    public void onPlayerAchievementAwarded(PlayerAchievementAwardedEvent e){}

    public void onPlayerItemHeld(PlayerItemHeldEvent e){}

    public void onPlayerFish(PlayerFishEvent e){}

    public void onPlayerBucketFill(PlayerBucketFillEvent e){}

    public void onPlayerJoin(PlayerJoinEvent e){}

    public void onPlayerToggleSprint(PlayerToggleSprintEvent e){}

    public void onPlayerAnimation(PlayerAnimationEvent e){}

    public void onPlayerToggleFlight(PlayerToggleFlightEvent e){}

    public void onPlayerInteract(PlayerInteractEvent e){}

    public void onPlayerLogin(PlayerLoginEvent e){}

    public void onPlayerRegisterChannel(PlayerRegisterChannelEvent e){}

    public void onPlayerItemBreak(PlayerItemBreakEvent e){}

    public void onPlayerPortal(PlayerPortalEvent e){}

    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent e){}

    public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent e){}

    public void onPlayerShearEntity(PlayerShearEntityEvent e){}

    public void onPlayerGameModeChange(PlayerGameModeChangeEvent e){}

    public void onPlayerUnleashEntity(PlayerUnleashEntityEvent e){}

    public void onPlayerBedEnter(PlayerBedEnterEvent e){}

    public void onPlayerRespawn(PlayerRespawnEvent e){}

    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e){}

    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent e){}

    public void onPlayerDropItem(PlayerDropItemEvent e){}

    public void onPlayerUnregisterChannel(PlayerUnregisterChannelEvent e){}

    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e){}

    public void onPlayerBedLeave(PlayerBedLeaveEvent e){}

    public void onPlayerExpChange(PlayerExpChangeEvent e){}

    public void onPlayerStatisticIncrement(PlayerStatisticIncrementEvent e){}

    public void onPlayerMove(PlayerMoveEvent e){}

    public void onPlayerItemDamage(PlayerItemDamageEvent e){}

    public void onPlayerLevelChange(PlayerLevelChangeEvent e){}

    public void onPlayerVelocity(PlayerVelocityEvent e){}

    public void onPlayerEditBook(PlayerEditBookEvent e){}

    public void onPlayerKick(PlayerKickEvent e){}

    public void onPlayerQuit(PlayerQuitEvent e){}

    public void onPlayerResourcePackStatus(PlayerResourcePackStatusEvent e){}

    public void onPlayerInteractEntity(PlayerInteractEntityEvent e){}

    public void onAsyncPlayerChat(AsyncPlayerChatEvent e){}

    public void onPlayerChatTabComplete(PlayerChatTabCompleteEvent e){}

    public void onEnchantItem(EnchantItemEvent e){}

    public void onPrepareItemEnchant(PrepareItemEnchantEvent e){}

    public void onThunderChange(ThunderChangeEvent e){}

    public void onLightningStrike(LightningStrikeEvent e){}

    public void onWeatherChange(WeatherChangeEvent e){}

    public void onHangingPlace(HangingPlaceEvent e){}

    public void onHangingBreak(HangingBreakEvent e){}

    public void onHangingBreakByEntity(HangingBreakByEntityEvent e){}

    public void onMapInitialize(MapInitializeEvent e){}

    public void onServiceRegister(ServiceRegisterEvent e){}

    public void onServiceUnregister(ServiceUnregisterEvent e){}

    public void onPluginDisable(PluginDisableEvent e){}

    public void onRemoteServerCommand(RemoteServerCommandEvent e){}

    public void onPluginEnable(PluginEnableEvent e){}

    public void onServerListPing(ServerListPingEvent e){}

    public void onServerCommand(ServerCommandEvent e){}

    public void onSignChange(SignChangeEvent e){}

    public void onEntityBlockForm(EntityBlockFormEvent e){}

    public void onBlockPistonRetract(BlockPistonRetractEvent e){}

    public void onBlockFromTo(BlockFromToEvent e){}

    public void onBlockExplode(BlockExplodeEvent e){}

    public void onBlockBreak(BlockBreakEvent e){}

    public void onBlockDamage(BlockDamageEvent e){}

    public void onBlockCanBuild(BlockCanBuildEvent e){}

    public void onBlockForm(BlockFormEvent e){}

    public void onBlockGrow(BlockGrowEvent e){}

    public void onBlockMultiPlace(BlockMultiPlaceEvent e){}

    public void onBlockFade(BlockFadeEvent e){}

    public void onBlockRedstone(BlockRedstoneEvent e){}

    public void onBlockSpread(BlockSpreadEvent e){}

    public void onBlockDispense(BlockDispenseEvent e){}

    public void onBlockIgnite(BlockIgniteEvent e){}

    public void onBlockPlace(BlockPlaceEvent e){}

    public void onBlockPhysics(BlockPhysicsEvent e){}

    public void onBlockBurn(BlockBurnEvent e){}

    public void onBlockPistonExtend(BlockPistonExtendEvent e){}

    public void onBlockExp(BlockExpEvent e){}

    public void onLeavesDecay(LeavesDecayEvent e){}

    public void onNotePlay(NotePlayEvent e){}

    public void onFurnaceBurn(FurnaceBurnEvent e){}

    public void onInventoryClose(InventoryCloseEvent e){}

    public void onInventoryCreative(InventoryCreativeEvent e){}

    public void onInventoryMoveItem(InventoryMoveItemEvent e){}

    public void onCraftItem(CraftItemEvent e){}

    public void onBrew(BrewEvent e){}

    public void onFurnaceSmelt(FurnaceSmeltEvent e){}

    public void onInventory(InventoryEvent e){}

    public void onInventoryOpen(InventoryOpenEvent e){}

    public void onPrepareItemCraft(PrepareItemCraftEvent e){}

    public void onInventoryDrag(InventoryDragEvent e){}

    public void onInventoryPickupItem(InventoryPickupItemEvent e){}

    public void onFurnaceExtract(FurnaceExtractEvent e){}

    public void onInventoryClick(InventoryClickEvent e){}

    public void onExpBottle(ExpBottleEvent e){}

    public void onSheepRegrowWool(SheepRegrowWoolEvent e){}

    public void onEntityBreakDoor(EntityBreakDoorEvent e){}

    public void onEntityTarget(EntityTargetEvent e){}

    public void onEntityCombustByBlock(EntityCombustByBlockEvent e){}

    public void onEntityCombust(EntityCombustEvent e){}

    public void onItemMerge(ItemMergeEvent e){}

    public void onFireworkExplode(FireworkExplodeEvent e){}

    public void onEntityUnleash(EntityUnleashEvent e){}

    public void onEntityDamageByEntity(EntityDamageByEntityEvent e){}

    public void onEntityInteract(EntityInteractEvent e){}

    public void onPlayerDeath(PlayerDeathEvent e){}

    public void onItemSpawn(ItemSpawnEvent e){}

    public void onEntityTame(EntityTameEvent e){}

    public void onPotionSplash(PotionSplashEvent e){}

    public void onEntityPortalExit(EntityPortalExitEvent e){}

    public void onItemDespawn(ItemDespawnEvent e){}

    public void onProjectileLaunch(ProjectileLaunchEvent e){}

    public void onEntityRegainHealth(EntityRegainHealthEvent e){}

    public void onEntityPortalEnter(EntityPortalEnterEvent e){}

    public void onExplosionPrime(ExplosionPrimeEvent e){}

    public void onEntityDamage(EntityDamageEvent e){}

    public void onEntityChangeBlock(EntityChangeBlockEvent e){}

    public void onEntityDeath(EntityDeathEvent e){}

    public void onPlayerLeashEntity(PlayerLeashEntityEvent e){}

    public void onSlimeSplit(SlimeSplitEvent e){}

    public void onEntityDamageByBlock(EntityDamageByBlockEvent e){}

    public void onPigZap(PigZapEvent e){}

    public void onProjectileHit(ProjectileHitEvent e){}

    public void onEntityCombustByEntity(EntityCombustByEntityEvent e){}

    public void onEntityTeleport(EntityTeleportEvent e){}

    public void onEntityCreatePortal(EntityCreatePortalEvent e){}

    public void onCreatureSpawn(CreatureSpawnEvent e){}

    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent e){}

    public void onEntityPortal(EntityPortalEvent e){}

    public void onHorseJump(HorseJumpEvent e){}

    public void onSpawnerSpawn(SpawnerSpawnEvent e){}

    public void onCreeperPower(CreeperPowerEvent e){}

    public void onEntityExplode(EntityExplodeEvent e){}

    public void onEntitySpawn(EntitySpawnEvent e){}

    public void onEntityShootBow(EntityShootBowEvent e){}

    public void onSheepDyeWool(SheepDyeWoolEvent e){}

    public void onFoodLevelChange(FoodLevelChangeEvent e){}

    public void onSpawnChange(SpawnChangeEvent e){}

    public void onWorldSave(WorldSaveEvent e){}

    public void onStructureGrow(StructureGrowEvent e){}

    public void onWorldLoad(WorldLoadEvent e){}

    public void onChunkUnload(ChunkUnloadEvent e){}

    public void onChunkLoad(ChunkLoadEvent e){}

    public void onPortalCreate(PortalCreateEvent e){}

    public void onWorldInit(WorldInitEvent e){}

    public void onWorldUnload(WorldUnloadEvent e){}

    public void onChunkPopulate(ChunkPopulateEvent e){}
}
