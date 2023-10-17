/* Mohammed Amine AYACHE (C)2022 */
package com.sample.controllers.audit;

import com.sample.models.responses.PageableResponse;
import com.sample.utils.builders.PageableBuilder;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.sample.repositories.AuditRepository;
import com.sample.repositories.entities.audit.Audit;

@Controller("/api/audit")
@Transactional
@Tag(name = "Audit")
@RequiredArgsConstructor
public class AuditController {
    private final AuditRepository auditRepository;

    @Get
    public PageableResponse<Audit> getAllAudit(
            @QueryValue(defaultValue = "-1") Integer page,
            @QueryValue(defaultValue = "0") Integer size) {
        return new PageableResponse<>(
                auditRepository.findAll(
                        PageableBuilder.newBuilder().page(page).size(size).build()));
    }
}
