package cc.mycraft.mythic_cookstoves.block_entities

import cc.mycraft.mythic_cookstoves.MythicCookstoves
import cc.mycraft.mythic_cookstoves.blocks.ModBlocks
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

object ModBlockEntities {
    val REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MythicCookstoves.MOD_ID)

    val MORTAR by REGISTRY.registerObject("mortar") {
        BlockEntityType.Builder.of(::MortarBlockEntity, ModBlocks.STONE_MORTAR).build(null)
    }
    val BONFIRE by REGISTRY.registerObject("bonfire") {
        BlockEntityType.Builder.of(::BonfireBlockEntity, ModBlocks.BONFIRE).build(null)
    }
    val MYTHIC_FIRE by REGISTRY.registerObject("mythic_fire") {
        BlockEntityType.Builder.of(::MythicFireBlockEntity, ModBlocks.MYTHIC_FIRE).build(null)
    }
}