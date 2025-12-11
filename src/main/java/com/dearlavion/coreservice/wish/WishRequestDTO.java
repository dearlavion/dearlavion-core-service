package com.dearlavion.coreservice.wish;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishRequestDTO {
    private String requestId;
    private String copilotName;
    private String status;
}
