package com.dearlavion.coreservice.portfolio;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDTO {

    private String id;
    private String username;
    private String title;
    private String body;
    private String link;

    private boolean hidden;
    private String image;

    private Date createdAt;
    private Date updatedAt;
}
