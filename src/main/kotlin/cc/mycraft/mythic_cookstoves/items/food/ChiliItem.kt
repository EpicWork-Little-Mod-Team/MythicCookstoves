package cc.mycraft.mythic_cookstoves.items.food

import cc.mycraft.mythic_cookstoves.blocks.ModBlocks
import cc.mycraft.mythic_cookstoves.items.ModTab
import net.minecraft.world.item.ItemNameBlockItem

class ChiliItem :
    ItemNameBlockItem(ModBlocks.CHILI, Properties().tab(ModTab).food(ModFoods.CHILI))