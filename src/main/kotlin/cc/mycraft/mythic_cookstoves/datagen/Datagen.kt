package cc.mycraft.mythic_cookstoves.datagen

import cc.mycraft.mythic_cookstoves.MythicCookstoves
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MythicCookstoves.MOD_ID)
object Datagen {
    @SubscribeEvent
    fun gatherData(event: GatherDataEvent) {
        val generator = event.generator
        val existingFileHelper = event.existingFileHelper
        if (event.includeClient()) {
            //block/item models, blockstates, language files...
            generator.addProvider(BlockStatesGen(generator, MythicCookstoves.MOD_ID, existingFileHelper))
            generator.addProvider(ItemModelsGen(generator, MythicCookstoves.MOD_ID, existingFileHelper))
        }
        if (event.includeServer()) {
            //recipes, loottables, advancements, tags...
            generator.addProvider(LootTablesGen(generator))
            generator.addProvider(BlockTagsGen(generator, MythicCookstoves.MOD_ID, existingFileHelper))
        }
    }
}