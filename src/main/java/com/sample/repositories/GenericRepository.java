/* Mohammed Amine AYACHE (C)2022 */
package com.sample.repositories;

import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.jpa.repository.JpaSpecificationExecutor;
import java.io.Serializable;

public interface GenericRepository<T, I extends Serializable>
        extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {}
