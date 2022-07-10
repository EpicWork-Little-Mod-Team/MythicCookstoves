package cc.mycraft.mythic_cookstoves.blocks.pestle

import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.material.Material

class OakPestleBlock : AbstractPestleBlock(Properties.of(Material.WOOD).noOcclusion().sound(SoundType.WOOD))