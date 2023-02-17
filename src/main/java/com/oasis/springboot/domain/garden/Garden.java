package com.oasis.springboot.domain.garden;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class Garden {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String name;
   private String picture; //rtnFileCours와 rtnStreFileNm의 마지막 값 합체
   private String temperature; //온도
   private String humidity;  //습도
   private String adviceInfo; //조언
   private String manageLevel; //관리 수준
   private String waterSupply; //물관리 봄
   private String light;
   private String place; // 배치장소
   private String bug; //병충해
   private String cntntsNo;


   @Builder
   public Garden(String cntntsNo, String name, String picture, String temperature, String humidity, String adviceInfo, String manageLevel, String waterSupply, String light, String place, String bug) {
      this.cntntsNo = cntntsNo;
      this.name = name;
      this.picture = picture;
      this.temperature = temperature;
      this.humidity = humidity;
      this.adviceInfo = adviceInfo;
      this.manageLevel = manageLevel;
      this.waterSupply = waterSupply;
      this.light = light;
      this.place = place;
      this.bug = bug;
   }
}
