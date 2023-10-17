/* Mohammed Amine AYACHE (C)2022 */
package com.sample.controllers.constants;

import com.sample.models.EnumModel;
import com.sample.utils.enumerations.Labelled;
import com.sample.utils.enumerations.common.Country;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Controller("/enum")
@Transactional
@Tag(name = "Enumerations")
@RequiredArgsConstructor
public class EnumController {
    private static List<Labelled[]> list = Arrays.asList(Country.values(), Country.values());

    @Get()
    HttpResponse<List<EnumModel>> getEnum() {
        List<EnumModel> out = new ArrayList<>();
        for (Labelled[] i : list) {
            EnumModel enumModel = new EnumModel();
            if (i.length > 0) enumModel.setName(i[0].getClass().getSimpleName().toLowerCase());
            else enumModel.setName(i.getClass().getSimpleName().toLowerCase());
            List<EnumModel.EnumElement> data = new ArrayList<>();
            for (Labelled j : i) {
                data.add(new EnumModel.EnumElement(j.toString(), j.getLabel()));
            }
            enumModel.setData(data);
            out.add(enumModel);
        }
        return HttpResponse.ok(out);
    }
}
