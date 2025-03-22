package ca.fxco.morecullingextra.api;

import ca.fxco.morecullingextra.cullcondition.CullCondition;

public interface ExtendedBlockModel {

    default void setCullCondition(CullCondition condition) {}

    default CullCondition getCullCondition() {
        return null;
    }
}
