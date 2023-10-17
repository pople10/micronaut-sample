/* Mohammed Amine AYACHE (C)2022 */
package com.sample.utils.enumerations;

import java.util.Arrays;
import java.util.List;

public enum EntityStatus {
    CREATED,
    HOLD,
    DELETED;

    public static final List<EntityStatus> excludedStatus = Arrays.asList(HOLD, DELETED);
}
