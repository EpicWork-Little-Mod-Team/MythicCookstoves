package cc.mycraft.mythic_cookstoves.block_entities.renderer

import cc.mycraft.mythic_cookstoves.MythicCookstoves
import cc.mycraft.mythic_cookstoves.block_entities.MythicTinderBlockEntity
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Vector3f
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraftforge.client.model.data.EmptyModelData
import net.minecraftforge.client.model.renderable.BakedRenderable
import kotlin.math.cos
import kotlin.math.sin

class MythicTinderRenderer(pContext: BlockEntityRendererProvider.Context) : BlockEntityRenderer<MythicTinderBlockEntity> {
    private var model: BakedRenderable? = null

    private fun getModel(block: Block): BakedRenderable {
        return model ?: BakedRenderable.of(
            ResourceLocation(
                MythicCookstoves.MOD_ID,
                "block/${checkNotNull(block.registryName).path}"
            )
        ).also { model = it }
    }

    override fun render(
        pBlockEntity: MythicTinderBlockEntity,
        pPartialTick: Float,
        pPoseStack: PoseStack,
        pBufferSource: MultiBufferSource,
        pPackedLight: Int,
        pPackedOverlay: Int
    ) {
        val gameTime = pBlockEntity.level?.gameTime ?: 0
        pPoseStack.pushPose()
        // rotation
        pPoseStack.translate(0.5, 0.5, 0.5)
        val rotationRange = (1 - cos((gameTime + pPartialTick) % 40 / 40 * 2 * Math.PI)) / 2
        pPoseStack.mulPose(Vector3f.YP.rotation(-(rotationRange * 2 * Math.PI).toFloat()))
        pPoseStack.translate(-0.5, -0.5, -0.5)
        // floating
        val floatingRange = sin((gameTime + pPartialTick) % 50 / 50 * 2 * Math.PI)
        pPoseStack.translate(0.0, 0.125 + floatingRange * 0.125, 0.0)
        getModel(pBlockEntity.blockState.block).render(
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