package cc.mycraft.mythic_cookstoves.datagen

import cc.mycraft.mythic_cookstoves.MythicCookstoves
import cc.mycraft.mythic_cookstoves.blocks.CookstoveBlock
import cc.mycraft.mythic_cookstoves.blocks.ModBlocks
import cc.mycraft.mythic_cookstoves.blocks.StrawberryBushBlock
import net.minecraft.data.DataGenerator
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.Property
import net.minecraftforge.client.model.generators.BlockModelBuilder
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.client.model.generators.ConfiguredModel
import net.minecraftforge.client.model.generators.ModelProvider
import net.minecraftforge.client.model.generators.loaders.OBJLoaderBuilder
import net.minecraftforge.common.data.ExistingFileHelper

class BlockStatesGen(gen: DataGenerator, modid: String, exFileHelper: ExistingFileHelper) :
    BlockStateProvider(gen, modid, exFileHelper) {
    override fun registerStatesAndModels() {
        // models
        val cookstoveModel = getObjBlockModelFromBooleanProperty(ModBlocks.COOKSTOVE, CookstoveBlock.LIT)
        val saucepanModel = getObjBlockModel(ModBlocks.SAUCEPAN)
        val stoneMortarModel = mortarModel(ModBlocks.STONE_MORTAR, mcLoc("block/stone"))
        val oakPestleModel = pestleModel(ModBlocks.OAK_PESTLE, mcLoc("block/oak_log"))
        val strawberryBushModel = getObjBlockModelFromProperty(ModBlocks.STRAWBERRY_BUSH, StrawberryBushBlock.AGE)
        // blockstates
        horizontalBlock(ModBlocks.COOKSTOVE) { cookstoveModel[it.getValue(CookstoveBlock.LIT)] }
        horizontalBlock(ModBlocks.SAUCEPAN, saucepanModel)
        horizontalBlock(ModBlocks.STONE_MORTAR, stoneMortarModel)
        simpleBlock(ModBlocks.OAK_PESTLE, oakPestleModel)
        getVariantBuilder(ModBlocks.STRAWBERRY_BUSH).forAllStates {
            arrayOf(ConfiguredModel(strawberryBushModel[it.getValue(StrawberryBushBlock.AGE).toString()]))
        }
    }

    private fun getObjBlockModelFromProperty(block: Block, property: Property<*>): Map<String, BlockModelBuilder> {
        check(block.registryName?.namespace == MythicCookstoves.MOD_ID) { "must be mod block" }
        return property.possibleValues.associate {
            it.toString() to getObjBlockModel(checkNotNull(block.registryName?.path) + "_${property.name}$it")
        }
    }

    private fun getObjBlockModelFromBooleanProperty(
        block: Block,
        property: Property<Boolean>
    ): Map<Boolean, BlockModelBuilder> {
        check(block.registryName?.namespace == MythicCookstoves.MOD_ID) { "must be mod block" }
        return property.possibleValues.associateWith {
            getObjBlockModel(checkNotNull(block.registryName?.path) + if (it) "_${property.name}" else "")
        }
    }

    private fun mortarModel(block: Block, texture: ResourceLocation): BlockModelBuilder {
        return getObjBlockModel(block, texture, arrayOf(texture), "mortar")
    }

    private fun pestleModel(block: Block, texture: ResourceLocation): BlockModelBuilder {
        return getObjBlockModel(block, texture, arrayOf(texture), "pestle").also {
            getObjBlockModel(
                checkNotNull(block.registryName?.path) + "_inside",
                texture,
                arrayOf(texture),
                "pestle_inside"
            )
        }
    }

    private fun getObjBlockModel(block: Block): BlockModelBuilder {
        check(block.registryName?.namespace == MythicCookstoves.MOD_ID) { "must be mod block" }
        return getObjBlockModel(checkNotNull(block.registryName?.path))
    }

    private fun getObjBlockModel(block: Block, textures: Array<ResourceLocation>): BlockModelBuilder {
        check(block.registryName?.namespace == MythicCookstoves.MOD_ID) { "must be mod block" }
        return getObjBlockModel(checkNotNull(block.registryName?.path), textures)
    }

    private fun getObjBlockModel(
        block: Block,
        particle: ResourceLocation,
        textures: Array<ResourceLocation>
    ): BlockModelBuilder {
        check(block.registryName?.namespace == MythicCookstoves.MOD_ID) { "must be mod block" }
        return getObjBlockModel(
            checkNotNull(block.registryName?.path),
            particle,
            textures
        )
    }

    private fun getObjBlockModel(
        block: Block,
        particle: ResourceLocation,
        textures: Array<ResourceLocation>,
        obj: String
    ): BlockModelBuilder {
        check(block.registryName?.namespace == MythicCookstoves.MOD_ID) { "must be mod block" }
        return getObjBlockModel(checkNotNull(block.registryName?.path), particle, textures, obj)
    }

    private fun getObjBlockModel(modelName: String): BlockModelBuilder {
        return getObjBlockModel(modelName, arrayOf(modLoc(ModelProvider.BLOCK_FOLDER + "/" + modelName)))
    }

    private fun getObjBlockModel(modelName: String, textures: Array<ResourceLocation>): BlockModelBuilder {
        return getObjBlockModel(
            modelName,
            if (textures.isNotEmpty()) textures[0] else throw NullPointerException(),
            textures
        )
    }

    private fun getObjBlockModel(
        modelName: String,
        particle: ResourceLocation,
        textures: Array<ResourceLocation>
    ): BlockModelBuilder {
        return getObjBlockModel(
            modelName,
            particle,
            textures,
            modelName
        )
    }

    private fun getObjBlockModel(
        modelName: String,
        particle: ResourceLocation,
        textures: Array<ResourceLocation>,
        obj: String
    ): BlockModelBuilder {
        return models().getBuilder(ModelProvider.BLOCK_FOLDER + "/" + modelName)
            .parent(models().getExistingFile(mcLoc("block/block")))
            .customLoader { parent, exFileHelper ->
                OBJLoaderBuilder.begin(parent, exFileHelper)
                    .modelLocation(modLoc("models/${ModelProvider.BLOCK_FOLDER}/$obj.obj")).flipV(true)
            }.end()
            .texture("particle", particle)
            .apply { textures.forEachIndexed { i, texture -> texture("texture$i", texture) } }
    }
}