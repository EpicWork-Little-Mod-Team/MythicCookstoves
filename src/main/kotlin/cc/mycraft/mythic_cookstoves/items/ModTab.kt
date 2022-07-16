package cc.mycraft.mythic_cookstoves.items

import cc.mycraft.mythic_cookstoves.MythicCookstoves
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack

object ModTab : CreativeModeTab(MythicCookstoves.MOD_ID) {
    override fun makeIcon(): ItemStack {
        return ItemStack(ModItems.MYTHIC_TINDER)
    }
}