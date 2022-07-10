package cc.mycraft.mythic_cookstoves.blocks.plant

import cc.mycraft.mythic_cookstoves.blocks.ICutoutRender
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.material.Material

class StrawberryBushBlock :
    Age3BushBlock(Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.SWEET_BERRY_BUSH)),
    ICutoutRender