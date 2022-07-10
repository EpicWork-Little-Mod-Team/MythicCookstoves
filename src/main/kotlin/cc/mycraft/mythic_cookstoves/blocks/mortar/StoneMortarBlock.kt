package cc.mycraft.mythic_cookstoves.blocks.mortar

import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.material.Material

class StoneMortarBlock : AbstractMortarBlock(Properties.of(Material.STONE).noOcclusion().sound(SoundType.STONE))