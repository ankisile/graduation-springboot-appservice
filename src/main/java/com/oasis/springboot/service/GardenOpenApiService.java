package com.oasis.springboot.service;

import com.oasis.springboot.domain.garden.GardenRepository;
import com.oasis.springboot.dto.garden.GardenSaveDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class GardenOpenApiService {

    private final GardenRepository gardenRepository;

    @Value("${plant.api.key}")
    private String gardenApiKey;

    @Transactional
    public void getOpenApiGardenList(){
        String apiUrl = "http://api.nongsaro.go.kr/service/garden/gardenList";
        String detailUrl = "http://api.nongsaro.go.kr/service/garden/gardenDtl";
        URI urlTemplate = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("apiKey", gardenApiKey)
                .queryParam("numOfRows", "250")
                .build()
                .encode()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(urlTemplate, String.class);

        JSONObject object = XML.toJSONObject(response);

        JSONObject jobj = object.getJSONObject("response").getJSONObject("body").getJSONObject("items");

        JSONArray jarr = jobj.getJSONArray("item");

        for (int i = 1; i < jarr.length(); i++) {
            // {"name":"one","id":1}
            String cntntsNo = jarr.getJSONObject(i).getString("cntntsNo");
            String name = jarr.getJSONObject(i).getString("cntntsSj");
            String picture = "http://www.nongsaro.go.kr/cms_contents/301/" + cntntsNo + "_MF_REPR_ATTACH_01_TMB.jpg";

            urlTemplate = UriComponentsBuilder.fromHttpUrl(detailUrl)
                    .queryParam("apiKey", gardenApiKey)
                    .queryParam("cntntsNo", cntntsNo)
                    .build()
                    .encode()
                    .toUri();

            response = restTemplate.getForObject(urlTemplate, String.class);
            object = XML.toJSONObject(response);
            jobj = object.getJSONObject("response").getJSONObject("body").getJSONObject("item");


            String grwhTpCodeNm = jobj.getString("grwhTpCodeNm");
            String hdCodeNm = jobj.getString("hdCodeNm");
            String adviseInfo = jobj.getString("adviseInfo");
            String fncltyInfo = jobj.getString("fncltyInfo");
            String managelevelCodeNm = jobj.getString("managelevelCodeNm");
            String watercycleSprngCodeNm = jobj.getString("watercycleSprngCodeNm");
            String lighttdemanddoCodeNm = jobj.getString("lighttdemanddoCodeNm");
            String postngplaceCodeNm = jobj.getString("postngplaceCodeNm");
            String dlthtsCodeNm = jobj.getString("dlthtsCodeNm");

            String info = adviseInfo + "\n" + fncltyInfo;

            GardenSaveDto gardenSaveDto = GardenSaveDto.builder()
                    .name(name)
                    .picture(picture)
                    .temperature(grwhTpCodeNm)
                    .humidity(hdCodeNm)
                    .adviceInfo(info)
                    .manageLevel(managelevelCodeNm)
                    .waterSupply(watercycleSprngCodeNm)
                    .light(lighttdemanddoCodeNm)
                    .place(postngplaceCodeNm)
                    .bug(dlthtsCodeNm)
                    .cntntsNo(cntntsNo)
                    .build();

            gardenRepository.save(gardenSaveDto.toEntity());
        }
    }
}
