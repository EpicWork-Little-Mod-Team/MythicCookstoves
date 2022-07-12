package cc.mycraft.mythic_cookstoves.blocks

import cc.mycraft.mythic_cookstoves.block_entities.MythicFireBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class MythicFireBlock :
    Block(Properties.of(Material.FIRE).noCollission().sound(SoundType.AMETHYST).lightLevel { 15 }), EntityBlock {
    @Deprecated("use BlockState's getShape instead")
    override fun getShape(
        pState: BlockState,
        pLevel: BlockGetter,
        pPos: BlockPos,
        pContext: CollisionContext
    ): VoxelShape {
        return shape
    }

    @Deprecated("Deprecated in Java")
    override fun entityInside(pState: BlockState, pLevel: Level, pPos: BlockPos, pEntity: Entity) {
        super.entityInside(pState, pLevel, pPos, pEntity)
        pEntity.setSecondsOnFire(120)
        pEntity.hurt(DamageSource.IN_FIRE, 10.0f)
    }

    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity {
        return MythicFireBlockEntity(pPos, pState)
    }

    companion object {
        private val shape = box(6.0, 2.0, 6.0, 10.0, 6.0, 10.0)
    }
}