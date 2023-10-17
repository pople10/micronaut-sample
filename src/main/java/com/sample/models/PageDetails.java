/* Mohammed Amine AYACHE (C)2022 */
package com.sample.models;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.model.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

@Data
@Introspected
public class PageDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "le nombre d'éléments retournés")
    private int numberOfElements;

    @Schema(description = "la taille de la page")
    private int size;

    @Schema(description = "Le nombre total d'éléments au niveau de la BDD")
    private long totalElements;

    @Schema(description = "le nombre total de pages")
    private int totalPages;

    @Schema(description = "le numéro de la page courante")
    private int number;

    /**
     * Public constructor.
     *
     * <p>To construct a {@link PageDetails} with :
     */
    public PageDetails() {
        super();
    }

    /**
     * Public constructor.
     *
     * <p>To construct a {@link PageDetails} with :
     *
     * @param numberOfElements
     * @param size
     * @param totalElements
     * @param totalPages
     * @param number
     */
    public PageDetails(
            int numberOfElements, int size, long totalElements, int totalPages, int number) {
        super();
        this.numberOfElements = numberOfElements;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.number = number;
    }

    /**
     * To get a {@link PageDetails} from {@link Page}.
     *
     * @param <T>
     * @param page
     * @return
     */
    public static <T> PageDetails fromPage(Page<T> page) {
        return new PageDetails(
                page.getNumberOfElements(),
                page.getSize(),
                page.getTotalSize(),
                page.getTotalPages(),
                page.getPageNumber());
    }

    public boolean isNext()
    {
        return (totalPages-1)>number;
    }

}
