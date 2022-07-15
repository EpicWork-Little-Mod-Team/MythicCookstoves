package cc.mycraft.mythic_cookstoves.datagen

import cc.mycraft.mythic_cookstoves.MythicCookstoves
import cc.mycraft.mythic_cookstoves.blocks.BonfireBlock
import cc.mycraft.mythic_cookstoves.blocks.CookstoveBlock
import cc.mycraft.mythic_cookstoves.blocks.ModBlocks
import cc.mycraft.mythic_cookstoves.blocks.MythicTinderBlock
import cc.mycraft.mythic_cookstoves.blocks.mortar.AbstractMortarBlock
import cc.mycraft.mythic_cookstoves.blocks.pestle.AbstractPestleBlock
import cc.mycraft.mythic_cookstoves.blocks.plant.Age3BushBlock
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
        mythicFireModel(ModBlocks.MYTHIC_TINDER, modLoc("block/mythic_fire"))
        val mythicFireParticleModel = getParticleBlockModel(ModBlocks.MYTHIC_TINDER)
        val cookstoveModel = getObjBlockModelFromBooleanProperty(ModBlocks.COOKSTOVE, CookstoveBlock.LIT)
        bonfireModel(ModBlocks.BONFIRE, CookstoveBlock.LIT, modLoc("block/mythic_fire"))
        val bonfireParticleModel = getParticleBlockModelFromBooleanProperty(ModBlocks.BONFIRE, CookstoveBlock.LIT)
        val shallowPanModel = getObjBlockModel(ModBlocks.SHALLOW_PAN)
        val saucepanModel = getObjBlockModel(ModBlocks.SAUCEPAN)
        val stoneMortarModel = mortarModel(ModBlocks.STONE_MORTAR, mcLoc("block/stone"))
        val oakPestleModel = pestleModel(ModBlocks.OAK_PESTLE, mcLoc("block/oak_log"))
        val strawberryBushModel = getObjBlockModelFromProperty(ModBlocks.STRAWBERRY_BUSH, Age3BushBlock.AGE)
        val chiliModel = getObjBlockModelFromProperty(ModBlocks.CHILI, Age3BushBlock.AGE)
        // blockstates

        simpleBlock(ModBlocks.FURNITURE, models().getExistingFile(mcLoc("block/barrier")))

        simpleBlock(ModBlocks.MYTHIC_TINDER, mythicFireParticleModel)
        horizontalBlock(ModBlocks.COOKSTOVE) { cookstoveModel[it.getValue(CookstoveBlock.LIT)] }
        horizontalBlock(ModBlocks.BONFIRE) { bonfireParticleModel[it.getValue(CookstoveBlock.LIT)] }
        horizontalBlock(ModBlocks.SHALLOW_PAN, shallowPanModel)
        horizontalBlock(ModBlocks.SAUCEPAN, saucepanModel)
        horizontalBlock(ModBlocks.STONE_MORTAR, stoneMortarModel)
        simpleBlock(ModBlocks.OAK_PESTLE, oakPestleModel)
        getVariantBuilder(ModBlocks.STRAWBERRY_BUSH).forAllStates {
            arrayOf(ConfiguredModel(strawberryBushModel[it.getValue(Age3BushBlock.AGE).toString()]))
        }
        getVariantBuilder(ModBlocks.CHILI).forAllStates {
            arrayOf(ConfiguredModel(chiliModel[it.getValue(Age3BushBlock.AGE).toString()]))
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

    private fun getParticleBlockModelFromBooleanProperty(
        block: Block,
        property: Property<Boolean>
    ): Map<Boolean, BlockModelBuilder> {
        check(block.registryName?.namespace == MythicCookstoves.MOD_ID) { "must be mod block" }
        return property.possibleValues.associateWith {
            val modelName = checkNotNull(block.registryName?.path) + if (it) "_${property.name}" else ""
            getParticleBlockModel(modelName, modLoc(ModelProvider.BLOCK_FOLDER + "/" + modelName))
        }
    }

    private fun mortarModel(block: AbstractMortarBlock, texture: ResourceLocation): BlockModelBuilder {
        return getObjBlockModel(block, texture, arrayOf(texture), "mortar")
    }

    private fun pestleModel(block: AbstractPestleBlock, texture: ResourceLocation): BlockModelBuilder {
        return getObjBlockModel(block, texture, arrayOf(texture), "pestle").also {
            getObjBlockModel(
                checkNotNull(block.registryName?.path) + "_inside",
                texture,
                arrayOf(texture),
                "pestle_inside"
            )
        }
    }

    private fun bonfireModel(
        block: BonfireBlock,
        property: Property<Boolean>,
        fireTexture: ResourceLocation
    ): Map<Boolean, BlockModelBuilder> {
        return property.possibleValues.associateWith {
            val modelName = checkNotNull(block.registryName?.path) + if (it) "_${property.name}" else ""
            val texture = modLoc(ModelProvider.BLOCK_FOLDER + "/" + modelName)
            getObjBlockModel(
                modelName,
                if (it) arrayOf(texture, fireTexture) else arrayOf(texture)
            )
        }
    }

    private fun mythicFireModel(
        block: MythicTinderBlock,
        fireTexture: ResourceLocation
    ): BlockModelBuilder {
        val modelName = checkNotNull(block.registryName?.path)
        val texture = modLoc(ModelProvider.BLOCK_FOLDER + "/" + modelName)
        return getObjBlockModel(
            modelName,
            arrayOf(texture, fireTexture)
        )
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

    private fun getParticleBlockModel(block: Block): BlockModelBuilder {
        check(block.registryName?.namespace == MythicCookstoves.MOD_ID) { "must be mod block" }
        val modelName = checkNotNull(block.registryName?.path)
        return getParticleBlockModel(modelName, modLoc(ModelProvider.BLOCK_FOLDER + "/" + modelName))
    }

    private fun getParticleBlockModel(
        block: Block,
        particle: ResourceLocation
    ): BlockModelBuilder {
        check(block.registryName?.namespace == MythicCookstoves.MOD_ID) { "must be mod block" }
        return getParticleBlockModel(checkNotNull(block.registryName?.path), particle)
    }

    private fun getParticleBlockModel(
        modelName: String,
        particle: ResourceLocation
    ): BlockModelBuilder {
        return models().getBuilder(ModelProvider.BLOCK_FOLDER + "/" + modelName + "_particle")
            .texture("particle", particle)
    }
}