package cc.mycraft.mythic_cookstoves.blocks

import cc.mycraft.mythic_cookstoves.MythicCookstoves
import cc.mycraft.mythic_cookstoves.blocks.mortar.StoneMortarBlock
import cc.mycraft.mythic_cookstoves.blocks.pestle.OakPestleBlock
import cc.mycraft.mythic_cookstoves.blocks.plant.ChiliBlock
import cc.mycraft.mythic_cookstoves.blocks.plant.StrawberryBushBlock
import cc.mycraft.mythic_cookstoves.blocks.pot.SaucepanBlock
import cc.mycraft.mythic_cookstoves.blocks.pot.ShallowPanBlock
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

object ModBlocks {
    val REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, MythicCookstoves.MOD_ID)

    val COOKSTOVE by REGISTRY.registerObject("cookstove") { CookstoveBlock() }
    val SHALLOW_PAN by REGISTRY.registerObject("shallow_pan") { ShallowPanBlock() }
    val SAUCEPAN by REGISTRY.registerObject("saucepan") { SaucepanBlock() }
    val STONE_MORTAR by REGISTRY.registerObject("stone_mortar") { StoneMortarBlock() }
    val OAK_PESTLE by REGISTRY.registerObject("oak_pestle") { OakPestleBlock() }
    val STRAWBERRY_BUSH by REGISTRY.registerObject("strawberry_bush") { StrawberryBushBlock() }
    val CHILI by REGISTRY.registerObject("chili") { ChiliBlock() }
}