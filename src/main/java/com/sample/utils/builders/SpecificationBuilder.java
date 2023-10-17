/* Mohammed Amine AYACHE (C)2022 */
package com.sample.utils.builders;

import io.micronaut.core.util.CollectionUtils;
import io.micronaut.data.jpa.repository.criteria.Specification;
import java.util.*;
import javax.persistence.criteria.Predicate;
import lombok.Data;
import com.sample.repositories.entities.audit.TimeStampEntity;

@Data
public class SpecificationBuilder<T extends TimeStampEntity> {
    private static Set<String> joins = CollectionUtils.setOf();

    private static Map<String, Enum[]> enums = CollectionUtils.mapOf();

    private Map<String, List<Object>> values = new HashMap<>();

    private SpecificationBuilder() {}

    public static SpecificationBuilder newBuilder() {
        return new SpecificationBuilder();
    }

    public SpecificationBuilder values(Map<String, List<Object>> values) {
        if (values == null || values.isEmpty()) return this;
        this.values = values;
        return this;
    }

    public Specification build() {
        Specification specification =
                (Specification<T>)
                        (root, query, criteriaBuilder) -> {
                            List<Predicate> predicates = new ArrayList<>();
                            for (Map.Entry<String, List<Object>> e : values.entrySet()) {
                                if (e.getValue() == null) continue;
                                List<Object> obj = e.getValue();
                                if (enums.containsKey(e.getKey())) {
                                    for (int i = 0; i < obj.size(); i++) {
                                        Object o = obj.get(i);
                                        obj.set(i, fromObjToEnum(o, enums.get(e.getKey())));
                                    }
                                }
                                if (joins.contains(e.getKey())) {
                                    predicates.add(root.join(e.getKey()).in(obj));
                                    continue;
                                }
                                predicates.add(root.get(e.getKey()).in(obj));
                            }
                            return criteriaBuilder.and(
                                    predicates.toArray(new Predicate[predicates.size()]));
                        };
        return specification;
    }

    private Enum fromObjToEnum(Object obj, Enum[] enums) {
        for (Enum enumy : enums) {
            if (enumy.name().equals(obj.toString())) {
                obj = enumy;
                break;
            }
        }
        return (Enum) obj;
    }
}
