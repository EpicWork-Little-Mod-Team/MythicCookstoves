package cc.mycraft.mythic_cookstoves.items

import cc.mycraft.mythic_cookstoves.MythicCookstoves
import cc.mycraft.mythic_cookstoves.items.food.ChiliItem
import cc.mycraft.mythic_cookstoves.items.food.StrawberryItem
import cc.mycraft.mythic_cookstoves.items.kitchen.*
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

object ModItems {
    val REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MythicCookstoves.MOD_ID)

    // mythic fire
    val MYTHIC_TINDER by REGISTRY.registerObject("mythic_tinder") { MythicTinderItem() }

    // kitchen
    val COOKSTOVE by REGISTRY.registerObject("cookstove") { CookstoveItem() }
    val BONFIRE by REGISTRY.registerObject("bonfire") { BonfireItem() }
    val SHALLOW_PAN by REGISTRY.registerObject("shallow_pan") { ShallowPanItem() }
    val SAUCEPAN by REGISTRY.registerObject("saucepan") { SaucepanItem() }
    val STONE_MORTAR by REGISTRY.registerObject("stone_mortar") { StoneMortarItem() }
    val OAK_PESTLE by REGISTRY.registerObject("oak_pestle") { OakPestleItem() }

    // food
    val STRAWBERRY by REGISTRY.registerObject("strawberry") { StrawberryItem() }
    val CHILI by REGISTRY.registerObject("chili") { ChiliItem() }
}