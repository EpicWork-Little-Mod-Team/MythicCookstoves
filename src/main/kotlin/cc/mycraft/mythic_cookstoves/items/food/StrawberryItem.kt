package cc.mycraft.mythic_cookstoves.items.food

import cc.mycraft.mythic_cookstoves.blocks.ModBlocks
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemNameBlockItem

class StrawberryItem :
    ItemNameBlockItem(ModBlocks.STRAWBERRY_BUSH, Properties().tab(CreativeModeTab.TAB_FOOD).food(Foods.STRAWBERRY))