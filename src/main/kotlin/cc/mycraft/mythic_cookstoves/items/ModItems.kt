package cc.mycraft.mythic_cookstoves.items

import cc.mycraft.mythic_cookstoves.MythicCookstoves
import cc.mycraft.mythic_cookstoves.items.food.StrawberryItem
import cc.mycraft.mythic_cookstoves.items.kitchen.CookstoveItem
import cc.mycraft.mythic_cookstoves.items.kitchen.OakPestleItem
import cc.mycraft.mythic_cookstoves.items.kitchen.SaucepanItem
import cc.mycraft.mythic_cookstoves.items.kitchen.StoneMortarItem
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

object ModItems {
    val REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MythicCookstoves.MOD_ID)

    // kitchen
    val COOKSTOVE by REGISTRY.registerObject("cookstove") { CookstoveItem() }
    val SAUCEPAN by REGISTRY.registerObject("saucepan") { SaucepanItem() }
    val STONE_MORTAR by REGISTRY.registerObject("stone_mortar") { StoneMortarItem() }
    val OAK_PESTLE by REGISTRY.registerObject("oak_pestle") { OakPestleItem() }

    // food
    val STRAWBERRY by REGISTRY.registerObject("strawberry") { StrawberryItem() }
}