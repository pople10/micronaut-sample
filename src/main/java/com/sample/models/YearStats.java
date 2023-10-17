/* Mohammed Amine AYACHE (C)2022 */
package com.sample.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearStats {
    private static Set<YearStats> empty = new HashSet<>();

    static {
        for (int i = 1; i < 13; i++) empty.add(new YearStats(i, 0l));
    }

    public static Set<YearStats> getEmpty() {
        return empty.stream()
                .map(e -> new YearStats(e.month, e.getCount()))
                .collect(Collectors.toSet());
    }

    private Integer month;
    private Long count;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YearStats yearStats = (YearStats) o;
        return month.equals(yearStats.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month);
    }
}
