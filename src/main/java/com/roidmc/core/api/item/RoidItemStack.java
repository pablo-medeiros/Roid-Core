package com.roidmc.core.api.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.roidmc.core.api.inventory.nbt.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RoidItemStack extends ItemStack{

    ItemMeta meta;

    public RoidItemStack() {
        super();
        this.meta=super.getItemMeta();
    }

    public RoidItemStack(int type) {
        this(type,1, (short) 0);
    }

    public RoidItemStack(Material type) {
        this(type,1, (short) 0);
    }

    public RoidItemStack(int type, int amount) {
        this(type, amount, (short) 0);
    }

    public RoidItemStack(Material type, int amount) {
        this(type, amount, (short) 0);
    }

    public RoidItemStack(int type, int amount, int damage) {
        this(type, amount, (short) damage);
    }
    public RoidItemStack(int type, int amount, short damage) {
        super(type, amount, damage);
        this.meta=super.getItemMeta();
    }

    public RoidItemStack(Material type, int amount, int damage) {
        this(type, amount, (short) damage);
    }

    public RoidItemStack(Material type, int amount, short damage) {
        super(type, amount, damage);
        this.meta=super.getItemMeta();
    }

    public RoidItemStack(int type, int amount, short damage, Byte data) {
        super(type, amount, damage, data);
        this.meta=super.getItemMeta();
    }

    public RoidItemStack(Material type, int amount, short damage, Byte data) {
        super(type, amount, damage, data);
        this.meta=super.getItemMeta();
    }

    public RoidItemStack(ItemStack stack) throws IllegalArgumentException {
        super(stack);
        this.meta=super.getItemMeta();
    }

    public ItemStack build(){
        setItemMeta(meta);
        return this;
    }

    public void replaceAllTexts(Function<String,String> fn) {
        displayName(fn.apply(getDisplayName()));
        lore(getLore().stream().map(fn).collect(Collectors.toList()));
        owner(fn.apply(getOwner()));
    }
    public void replaceAllNameAndLore(Function<String,String> fn) {
        displayName(fn.apply(getDisplayName()));
        lore(getLore().stream().map(fn).collect(Collectors.toList()));
    }

    public boolean hasDisplayName() {
        return meta.hasDisplayName();
    }

    public String getDisplayName() {
        return meta.getDisplayName() == null ? "" : meta.getDisplayName();
    }

    public RoidItemStack displayName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public boolean hasLore() {
        return meta.hasLore();
    }

    public List<String> getLore() {
        if(meta.getLore()==null)return new ArrayList<>();
        return meta.getLore();
    }

    public RoidItemStack lore(String... strings){
        this.lore(Arrays.asList(strings));
        return this;
    }

    public RoidItemStack lore(List<String> lore) {
        meta.setLore(lore);
        return this;
    }

    public RoidItemStack loreAutoBreak(String text, int maxLength){
        List<String> matchList = new ArrayList<>();
        Pattern regex = Pattern.compile("(.{1,"+maxLength+"}(?:\\s|$))|(.{0,"+maxLength+"})", Pattern.DOTALL);
        Matcher regexMatcher = regex.matcher(text);
        while (regexMatcher.find()) {
            matchList.add(regexMatcher.group());
        }
        String[] lines = matchList.toArray(new String[0]);
        for(int i = 1; i <lines.length; i++){
            String prevLine = lines[i-1];
            if(prevLine.charAt(prevLine.length()-1)=='§'){
                lines[i-1]=prevLine.substring(0,prevLine.length()-1);
                lines[i] = "§"+lines[i];
            }else {
                lines[i] = ChatColor.getLastColors(prevLine)+lines[i];
            }
        }
        return lore(lines);
    }

    public boolean hasEnchants() {
        return meta.hasEnchants();
    }

    public boolean hasEnchant(Enchantment ench) {
        return meta.hasEnchant(ench);
    }

    public int getEnchantLevel(Enchantment ench) {
        return meta.getEnchantLevel(ench);
    }

    public Map<Enchantment, Integer> getEnchants() {
        return meta.getEnchants();
    }

    public boolean enchant(Enchantment ench, int level, boolean ignoreLevelRestriction) {
        return meta.addEnchant(ench,level,ignoreLevelRestriction);
    }

    public boolean removeEnchant(Enchantment ench) {
        return meta.removeEnchant(ench);
    }

    public boolean hasConflictingEnchant(Enchantment ench) {
        return meta.hasConflictingEnchant(ench);
    }

    public RoidItemStack itemFlags(ItemFlag... itemFlags) {
        meta.addItemFlags(itemFlags);
        return this;
    }

    public RoidItemStack removeItemFlags(ItemFlag... itemFlags) {
        meta.removeItemFlags(itemFlags);
        return this;
    }

    public Set<ItemFlag> getItemFlags() {
        return meta.getItemFlags();
    }

    public boolean hasItemFlag(ItemFlag flag) {
        return meta.hasItemFlag(flag);
    }


    // Skull methods

    public RoidItemStack owner(String owner){
        if(!(meta instanceof SkullMeta))return this;
        SkullMeta sMeta = (SkullMeta) meta;
        sMeta.setOwner(owner!=null&&!owner.isEmpty()?owner:null);
        return this;
    }

    public boolean hasOwner(){
        if(!(meta instanceof SkullMeta))return false;
        return ((SkullMeta) meta).hasOwner();
    }

    public String getOwner(){
        if(!(meta instanceof SkullMeta))return "";
        String owner = ((SkullMeta) meta).getOwner();
        return owner == null ? "" : owner;
    }

    public RoidItemStack texture(String url){
        if(!(meta instanceof SkullMeta))return this;

        String name = url.replace("http:","").replace("https:","").replace("//textures.minecraft.net/texture/","");
        GameProfile profile = new GameProfile(UUID.randomUUID(),name);

        String data = String.format("{\"textures\":{\"SKIN\":{\"url\":\"%s\"}}}", url);
        String encodedData = Base64.getEncoder().encodeToString(data.getBytes());

        profile.getProperties().put("textures", new Property("textures",encodedData));
        try{
            SkullMeta skullMeta = (SkullMeta) meta;
            Field field = skullMeta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(skullMeta,profile);
        }catch (Exception e){
            e.printStackTrace();
        }

        return this;
    }

    public NBTItem toNBTItem(){
        ItemMeta meta = this.meta.clone();
        ItemStack item = new ItemStack(this);
        item.setItemMeta(meta);
        return new NBTItem(item);
    }

    @Override
    public ItemMeta getItemMeta() {
        return meta;
    }

    @Override
    public boolean setItemMeta(ItemMeta itemMeta) {
        this.meta=itemMeta;
        return super.setItemMeta(itemMeta);
    }

    @Override
    public RoidItemStack clone() {
        RoidItemStack builder = new RoidItemStack(this);
        builder.meta = meta.clone();
        return builder;
    }
}
