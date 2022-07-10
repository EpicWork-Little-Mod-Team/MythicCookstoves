package cc.mycraft.mythic_cookstoves.datagen

import cc.mycraft.mythic_cookstoves.blocks.ModBlocks
import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.BlockTagsProvider
import net.minecraft.tags.BlockTags
import net.minecraftforge.common.data.ExistingFileHelper

class BlockTagsGen(pGenerator: DataGenerator, modId: String, existingFileHelper: ExistingFileHelper?) :
    BlockTagsProvider(pGenerator, modId, existingFileHelper) {
    override fun addTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(ModBlocks.COOKSTOVE)
            .add(ModBlocks.SHALLOW_PAN)
            .add(ModBlocks.SAUCEPAN)
            .add(ModBlocks.STONE_MORTAR)
    }
}