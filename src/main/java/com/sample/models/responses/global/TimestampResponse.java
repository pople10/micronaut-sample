/* Mohammed Amine AYACHE (C)2023 */
package com.sample.models.responses.global;

import com.sample.utils.enumerations.EntityStatus;
import io.micronaut.core.annotation.Introspected;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Introspected
@NoArgsConstructor
public class TimestampResponse implements Serializable {
    private Date createDate;
    private Date modifyDate;
    private EntityStatus status;
}
