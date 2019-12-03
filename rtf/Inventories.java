package fr.harmoglace.plugin.rtf;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

        ItemStack guerrier = new ItemStack(Material.STONE_SWORD, 1);
        ItemStack archer = new ItemStack(Material.BOW, 1);

        ItemMeta guerrierm = guerrier.getItemMeta();
        ItemMeta archerm = archer.getItemMeta();

        guerrierm.setDisplayName("§rKit Guerrier");
        guerrierm.addEnchant(Enchantment.KNOCKBACK, 1, true);
        guerrierm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        guerrierm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        guerrier.setItemMeta(guerrierm);

        archerm.setDisplayName("§rKit Archer");
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

        inventory.setItem(39, helmet);
        inventory.setItem(38, chestplate);
        inventory.setItem(37, leggings);
        inventory.setItem(36, boots);

        if (kit.equalsIgnoreCase("guerrier")) {
            ItemStack sword = new ItemStack(Material.IRON_SWORD);

            ItemMeta swordm = sword.getItemMeta();

            swordm.spigot().setUnbreakable(true);

            sword.setItemMeta(swordm);

            inventory.setItem(0, sword);

        } else if (kit.equalsIgnoreCase("archer")) {
            ItemStack bow = new ItemStack(Material.BOW);
            ItemStack arrow = new ItemStack(Material.ARROW);
            ItemStack sword = new ItemStack(Material.STONE_SWORD);

            ItemMeta bowm = bow.getItemMeta();
            ItemMeta swordm = sword.getItemMeta();

            swordm.spigot().setUnbreakable(true);

            sword.setItemMeta(swordm);

            bowm.addEnchant(Enchantment.ARROW_INFINITE, 1, true);

            bowm.spigot().setUnbreakable(true);

            bowm.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

            bowm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            bowm.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            bow.setItemMeta(bowm);


            inventory.setItem(0, bow);
            inventory.setItem(1, sword);
            inventory.setItem(17, arrow);
        }
    }
}
