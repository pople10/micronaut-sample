/* Mohammed Amine AYACHE (C)2022 */
package com.sample.models.responses;

import com.sample.models.PageDetails;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.model.Page;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Introspected
public class PageableResponse<T> implements Serializable {

    private PageDetails pageDetails;

    private List<T> content;

    public PageableResponse(Page<T> page) {
        this.content = page.getContent();
        this.pageDetails = PageDetails.fromPage(page);
    }

    public static PageableResponse empty() {
        return new PageableResponse<>(Page.empty());
    }
}
