package cc.mycraft.mythic_cookstoves

import cc.mycraft.mythic_cookstoves.block_entities.ModBlockEntities
import cc.mycraft.mythic_cookstoves.block_entities.renderer.MortarRenderer
import cc.mycraft.mythic_cookstoves.blocks.ModBlocks
import cc.mycraft.mythic_cookstoves.items.ModItems
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.event.EntityRenderersEvent
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ForgeModelBakery
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import net.minecraftforge.registries.ForgeRegistries
import org.apache.logging.log4j.LogManager
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runForDist

@Mod(MythicCookstoves.MOD_ID)
object MythicCookstoves {
    const val MOD_ID = "mythic_cookstoves"
    val LOGGER = LogManager.getLogger(MOD_ID);

    init {
        ModBlocks.REGISTRY.register(MOD_BUS)
        ModItems.REGISTRY.register(MOD_BUS)
        ModBlockEntities.REGISTRY.register(MOD_BUS)

        runForDist(
            clientTarget = {
                MOD_BUS.addListener(::onModelRegistry)
                MOD_BUS.addListener(::onRendererRegister)
                MOD_BUS.addListener(::onClientSetup)
            },
            serverTarget = {
                MOD_BUS.addListener(::onServerSetup)
            }
        )
    }

    private fun onClientSetup(event: FMLClientSetupEvent) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.STRAWBERRY_BUSH, RenderType.cutout())
        LOGGER.info("Mode: Client")
    }

    private fun onServerSetup(event: FMLDedicatedServerSetupEvent) {
        LOGGER.info("Mode: Dedicated server")
    }

    private fun onModelRegistry(event: ModelRegistryEvent) {
        ForgeRegistries.BLOCKS.values.filter {
            it.registryName?.namespace == MOD_ID && it.registryName?.path?.endsWith("_pestle") ?: false
        }.forEach {
            ForgeModelBakery.addSpecialModel(
                ResourceLocation(
                    MOD_ID,
                    "block/${checkNotNull(it.registryName).path}_inside"
                )
            )
        }
    }

    private fun onRendererRegister(event: EntityRenderersEvent.RegisterRenderers) {
        event.registerBlockEntityRenderer(ModBlockEntities.MORTAR, ::MortarRenderer)
    }
}