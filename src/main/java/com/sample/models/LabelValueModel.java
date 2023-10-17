/* Mohammed Amine AYACHE (C)2023 */
package com.sample.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LabelValueModel<T> {
    private String label;
    private T value;
}
