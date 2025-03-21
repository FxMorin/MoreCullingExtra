package ca.fxco.morecullingextra;

import ca.fxco.moreculling.MoreCulling;
import ca.fxco.moreculling.api.model.BakedOpacity;
import ca.fxco.moreculling.config.ModMenuConfig;
import ca.fxco.moreculling.mixin.accessors.BlockModelShaperAccessor;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import static ca.fxco.moreculling.MoreCulling.blockRenderManager;

@Mod(value = MoreCullingExtra.MOD_ID, dist = Dist.CLIENT)
public class MoreCullingExtraNeoforge {
    public MoreCullingExtraNeoforge(ModContainer container, IEventBus bus) {}
}
