package fr.harmoglace.plugin.rtf;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inventories {

    public Inventory inventaire() {
        Inventory inv = Bukkit.createInventory(null, 27, "§2Choisis ton équipe");

        ItemStack bleu = new ItemStack(Material.WOOL, 1, (byte) 11);



        ItemStack rouge = new ItemStack(Material.WOOL, 1, (byte) 14);

        ItemMeta bleum = bleu.getItemMeta();
        ItemMeta rougem = rouge.getItemMeta();

        bleum.setDisplayName("§9Equipe bleue");
        bleu.setItemMeta(bleum);

        rougem.setDisplayName("§4Equipe rouge");
        rouge.setItemMeta(rougem);


        inv.setItem(11, bleu);
        inv.setItem(15, rouge);
        return inv;
    }

    public Inventory kitChoose() {
        Inventory inv = Bukkit.createInventory(null, 27, "§rChoisis ton kit");

        ItemStack guerrier = new ItemStack(Material.IRON_SWORD, 1);
        ItemStack archer = new ItemStack(Material.BOW, 1);

        ItemMeta guerrierm = guerrier.getItemMeta();
        ItemMeta archerm = archer.getItemMeta();

        guerrierm.setDisplayName("§aKit Guerrier");
        ArrayList<String> loreg = new ArrayList<>();
        loreg.add("");
        loreg.add("§rKit fait pour le corps à corps");
        guerrierm.setLore(loreg);
        guerrierm.addEnchant(Enchantment.KNOCKBACK, 1, true);
        guerrierm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        guerrierm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        guerrier.setItemMeta(guerrierm);

        archerm.setDisplayName("§aKit Archer");
        ArrayList<String> lorea = new ArrayList<>();
        lorea.add("");
        lorea.add("§rKit fait pour le combat à distance");
        archerm.setLore(lorea);
        archerm.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
        archerm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        archer.setItemMeta(archerm);

        inv.setItem(11, guerrier);
        inv.setItem(15, archer);

        return inv;
    }

    public void setKit(Player player, String kit) {
        Inventory inventory = player.getInventory();
        inventory.clear();


        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        ItemStack blocks = new ItemStack(Material.SANDSTONE, 64);
        ItemStack pickaxe = new ItemStack(Material.IRON_PICKAXE);

        ItemMeta helmetm = helmet.getItemMeta();
        ItemMeta chestplatem = chestplate.getItemMeta();
        ItemMeta leggingsm = leggings.getItemMeta();
        ItemMeta bootsm = boots.getItemMeta();

        helmetm.spigot().setUnbreakable(true);
        helmetm.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        chestplatem.spigot().setUnbreakable(true);
        chestplatem.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        leggingsm.spigot().setUnbreakable(true);
        leggingsm.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        bootsm.spigot().setUnbreakable(true);
        bootsm.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);


        helmet.setItemMeta(helmetm);
        chestplate.setItemMeta(chestplatem);
        leggings.setItemMeta(leggingsm);
        boots.setItemMeta(bootsm);

        ItemMeta pickaxem = pickaxe.getItemMeta();

        pickaxem.spigot().setUnbreakable(true);

        pickaxem.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        pickaxe.setItemMeta(pickaxem);

        inventory.setItem(39, helmet);
        inventory.setItem(38, chestplate);
        inventory.setItem(37, leggings);
        inventory.setItem(36, boots);
        inventory.setItem(1, pickaxe);
        inventory.setItem(8, blocks);

        if (kit.equalsIgnoreCase("guerrier")) {
            ItemStack sword = new ItemStack(Material.IRON_SWORD);

            ItemMeta swordm = sword.getItemMeta();

            swordm.addEnchant(Enchantment.DAMAGE_ALL, 2, true);


            swordm.spigot().setUnbreakable(true);

            swordm.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            swordm.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

            swordm.setDisplayName("§rKit Guerrier");

            sword.setItemMeta(swordm);

            inventory.setItem(0, sword);

        } else if (kit.equalsIgnoreCase("archer")) {
            ItemStack bow = new ItemStack(Material.BOW);
            ItemStack arrow = new ItemStack(Material.ARROW);
            ItemStack sword = new ItemStack(Material.WOOD_SWORD);

            ItemMeta bowm = bow.getItemMeta();
            ItemMeta swordm = sword.getItemMeta();

            swordm.spigot().setUnbreakable(true);

            swordm.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

            sword.setItemMeta(swordm);

            bowm.addEnchant(Enchantment.ARROW_INFINITE, 1, true);

            bowm.spigot().setUnbreakable(true);

            bowm.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

            bowm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            bowm.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            bowm.setDisplayName("§rKit archer");

            bow.setItemMeta(bowm);


            inventory.setItem(0, bow);
            inventory.setItem(1, sword);
            inventory.setItem(17, arrow);
        }
    }
}
