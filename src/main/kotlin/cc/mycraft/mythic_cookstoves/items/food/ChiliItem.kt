package cc.mycraft.mythic_cookstoves.items.food

import cc.mycraft.mythic_cookstoves.blocks.ModBlocks
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemNameBlockItem

class ChiliItem :
    ItemNameBlockItem(ModBlocks.CHILI, Properties().tab(CreativeModeTab.TAB_FOOD).food(ModFoods.CHILI))