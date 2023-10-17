/* Mohammed Amine AYACHE (C)2022 */
package com.sample.utils.builders;

import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import lombok.Data;

@Data
public class PageableBuilder {
    public static Integer DEFAULT_PAGE = 0;
    public static Integer DEFAULT_SIZE = 15;
    private Integer page = DEFAULT_PAGE;
    private Integer size = DEFAULT_SIZE;

    private PageableBuilder() {}

    public static PageableBuilder newBuilder() {
        return new PageableBuilder();
    }

    public PageableBuilder page(Integer page) {
        if (page == null || page < 0) return this;
        this.page = page;
        return this;
    }

    public PageableBuilder size(Integer size) {
        if (size == null || size == 0) return this;
        this.size = size;
        return this;
    }

    public Pageable build(boolean sortByCreationDate) {
        Pageable pageable = Pageable.from(page, size);
        if (sortByCreationDate)
            pageable = Pageable.from(page, size, Sort.of(Sort.Order.desc("createDate")));
        return pageable;
    }

    public Pageable build() {
        return this.build(false);
    }
}
