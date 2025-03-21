package ca.fxco.morecullingextra.config;

import ca.fxco.moreculling.api.config.ConfigAdditions;
import ca.fxco.moreculling.api.config.ConfigOptionFlag;
import ca.fxco.moreculling.api.config.ConfigOptionImpact;
import ca.fxco.moreculling.api.config.defaults.ConfigBooleanOption;
import ca.fxco.morecullingextra.MoreCullingExtra;

public class MCEConfig {

    public boolean beaconCulling = true;
    public boolean paneCulling = true;

    static {
        ConfigAdditions.addOption("extra", new ConfigBooleanOption(
                "morecullingextra.config.option.beaconCulling",
                s -> MoreCullingExtra.CONFIG.beaconCulling = s,
                () -> MoreCullingExtra.CONFIG.beaconCulling,
                null,
                true,
                ConfigOptionImpact.LOW,
                ConfigOptionFlag.REQUIRES_RENDERER_RELOAD
        ));
        ConfigAdditions.addOption("extra", new ConfigBooleanOption(
                "morecullingextra.config.option.paneCulling",
                s -> MoreCullingExtra.CONFIG.paneCulling = s,
                () -> MoreCullingExtra.CONFIG.paneCulling,
                null,
                true,
                ConfigOptionImpact.LOW,
                ConfigOptionFlag.REQUIRES_RENDERER_RELOAD
        ));
    }
}
