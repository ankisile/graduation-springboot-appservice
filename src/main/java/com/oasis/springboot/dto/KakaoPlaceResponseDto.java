package com.oasis.springboot.dto;

import lombok.Data;

@Data
public class KakaoPlaceResponseDto {
    public Document[] documents;

    @Data
    static class Document {
        public String place_name;
        public String phone;
        public String address_name;
        public String road_address_name;
        public String place_url;
        public String x;
        public String y;
    }
}
