package com.Rishabh.Order_Service.Entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Chef {
    private Long id;
    private String name;
    private String speciality;
    private Double hourlyRate;
    private Boolean available;
}
