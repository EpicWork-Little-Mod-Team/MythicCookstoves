package cc.mycraft.mythic_cookstoves.datagen

import cc.mycraft.mythic_cookstoves.MythicCookstoves
import cc.mycraft.mythic_cookstoves.items.ModItems
import net.minecraft.data.DataGenerator
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemNameBlockItem
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.registries.ForgeRegistries


class ItemModelsGen(pGenerator: DataGenerator, modId: String, existingFileHelper: ExistingFileHelper?) :
    ItemModelProvider(pGenerator, modId, existingFileHelper) {
    val GENERATED = mcLoc("item/generated")

    override fun registerModels() {
        val items =
            ForgeRegistries.ITEMS.values.filter { it.registryName?.namespace == MythicCookstoves.MOD_ID }.toMutableSet()
        // skip item
        items.remove(ModItems.MYTHIC_TINDER)
        items.remove(ModItems.BONFIRE)
        items.remove(ModItems.SHALLOW_PAN)
        items.remove(ModItems.SAUCEPAN)
        // generate
        items.forEach {
            val name = checkNotNull(it.registryName?.path)
            if (it is BlockItem && it !is ItemNameBlockItem) {
                withExistingParent(name, modLoc("$BLOCK_FOLDER/" + checkNotNull(it.block.registryName).path))
            } else {
                withExistingParent(name, GENERATED).texture("layer0", modLoc("$ITEM_FOLDER/$name"))
            }
        }
    }
}