/* Mohammed Amine AYACHE (C)2022 */
package com.sample.models;

import com.sample.utils.enumerations.Labelled;
import io.micronaut.core.annotation.Introspected;
import java.util.List;
import lombok.Data;

@Data
@Introspected
public class EnumModel {
    private List<EnumElement> data;
    private String name;

    public static class EnumElement implements Labelled {
        private String value;
        private String label;

        public EnumElement(String value, String label) {
            this.value = value;
            this.label = label;
        }

        @Override
        public String getLabel() {
            return label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
