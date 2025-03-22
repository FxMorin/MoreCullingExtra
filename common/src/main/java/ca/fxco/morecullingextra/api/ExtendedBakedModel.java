package ca.fxco.morecullingextra.api;

import ca.fxco.morecullingextra.cullcondition.CullCondition;

public interface ExtendedBakedModel {

    default void setCullCondition(CullCondition list) {}

    default CullCondition getCullCondition() {
        return null;
    }

}
