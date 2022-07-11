package cc.mycraft.mythic_cookstoves.block_entities.renderer

import cc.mycraft.mythic_cookstoves.MythicCookstoves
import cc.mycraft.mythic_cookstoves.block_entities.BonfireBlockEntity
import cc.mycraft.mythic_cookstoves.blocks.BonfireBlock
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf
import net.minecraftforge.client.model.data.EmptyModelData
import net.minecraftforge.client.model.renderable.BakedRenderable

class BonfireRenderer(pContext: BlockEntityRendererProvider.Context) : BlockEntityRenderer<BonfireBlockEntity> {
    private val modelMap = mutableMapOf<Boolean, BakedRenderable>()

    private fun getBonfireModel(bonfire: BlockState): BakedRenderable {
        val block = bonfire.block
        check(block is BonfireBlock)
        val name = checkNotNull(block.registryName?.path)
        return modelMap.getOrPut(bonfire.getValue(BonfireBlock.LIT)) {
            BakedRenderable.of(
                ResourceLocation(
                    MythicCookstoves.MOD_ID,
                    "block/" + name + if (bonfire.getValue(BonfireBlock.LIT)) "_${BonfireBlock.LIT.name}" else ""
                )
            )
        }
    }

    override fun render(
        pBlockEntity: BonfireBlockEntity,
        pPartialTick: Float,
        pPoseStack: PoseStack,
        pBufferSource: MultiBufferSource,
        pPackedLight: Int,
        pPackedOverlay: Int
    ) {
        if (pBlockEntity.blockState.getValue(BonfireBlock.HALF) != DoubleBlockHalf.LOWER) return
        pPoseStack.pushPose()
        getBonfireModel(pBlockEntity.blockState).render(
            pPoseStack,
            pBufferSource,
            { RenderType.cutout() },
            pPackedLight,
            pPackedOverlay,
            pPartialTick,
            EmptyModelData.INSTANCE
        )
        pPoseStack.popPose()
    }
}