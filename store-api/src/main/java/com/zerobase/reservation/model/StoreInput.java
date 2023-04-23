package com.zerobase.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreInput {
    @NotBlank(message = "매장 이름을 입력해주세요.")
    private String storeName;
    @NotBlank(message = "매장 정보를 입력해주세요.")
    private String explanation;
    @NotBlank(message = "매장 위치를 입력해주세요.")
    private String location;
}
