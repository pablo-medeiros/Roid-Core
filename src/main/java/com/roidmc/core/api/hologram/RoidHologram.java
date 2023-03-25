package com.roidmc.core.api.hologram;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class RoidHologram {

    private ArmorStand stand;
    private String text;
    private ItemStack head;

    public void setLocation(World world, double x, double y, double z,float yaw, float pitch){
        if(!exists())summon(new Location(world,x,y,z));
        else {
            if(!stand.getWorld().equals(world)){
                stand.remove();
                stand=null;
                setLocation(world, x, y, z,yaw,pitch);
            }else {
                EntityArmorStand entityArmorStand = ((CraftArmorStand)stand).getHandle();
//                entityArmorStand.setPosition(x,y,z);
                entityArmorStand.setLocation(x,y,z,yaw,pitch);
                PacketPlayOutEntityTeleport teleportPacket = new PacketPlayOutEntityTeleport(entityArmorStand);
                for (Player player : Bukkit.getOnlinePlayers()) {
                    EntityPlayer nmsPlayer = ((CraftPlayer)player).getHandle();
                    nmsPlayer.playerConnection.sendPacket(teleportPacket);
                }
            }
        }
    }

    private synchronized void summon(Location location){
        stand = location.getWorld().spawn(location,ArmorStand.class);
        stand.setSmall(true);
        stand.setGravity(false);
        stand.setBasePlate(true);
        stand.setVisible(false);
        if(text!=null&&!text.isEmpty()){
            stand.setCustomName(text);
            stand.setCustomNameVisible(true);
        }
        if(head!=null){
            stand.setHelmet(head);
        }
    }

    public double getHeight(){
        return head==null?0.3:0.7;
    }

    public void remove(){
        if(exists()){
            stand.remove();
        }
    }

    public void setText(String text) {
        this.text = text;
        if(exists()){
            stand.setCustomName(text==null||text.isEmpty()?"§f":text);
            stand.setCustomNameVisible(text!=null&&!text.isEmpty());
        }
    }

    public void setHead(ItemStack head) {
        this.head = head;
        if(exists())stand.setHelmet(head);
    }

    public String getText() {
        return text;
    }

    public ItemStack getHead() {
        return head;
    }

    public boolean exists(){
        return stand!=null&&stand.isValid()&&!stand.isDead();
    }
}
