package cc.mycraft.mythic_cookstoves.blocks

import cc.mycraft.mythic_cookstoves.MythicCookstoves
import cc.mycraft.mythic_cookstoves.blocks.mortar.StoneMortarBlock
import cc.mycraft.mythic_cookstoves.blocks.pestle.OakPestleBlock
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

object ModBlocks {
    val REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, MythicCookstoves.MOD_ID)

    val COOKSTOVE by REGISTRY.registerObject("cookstove") { CookstoveBlock() }
    val SAUCEPAN by REGISTRY.registerObject("saucepan") { SaucepanBlock() }
    val STONE_MORTAR by REGISTRY.registerObject("stone_mortar") { StoneMortarBlock() }
    val OAK_PESTLE by REGISTRY.registerObject("oak_pestle") { OakPestleBlock() }
    val STRAWBERRY_BUSH by REGISTRY.registerObject("strawberry_bush") { StrawberryBushBlock() }
}