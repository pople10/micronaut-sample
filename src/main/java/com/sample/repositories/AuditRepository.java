/* Mohammed Amine AYACHE (C)2022 */
package com.sample.repositories;

import io.micronaut.data.annotation.Repository;
import com.sample.repositories.entities.audit.Audit;

@Repository
public interface AuditRepository extends GenericRepository<Audit, Long> {}
