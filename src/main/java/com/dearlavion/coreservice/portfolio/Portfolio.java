package com.dearlavion.coreservice.portfolio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "portfolio")
public class Portfolio {

    @Id
    private String id;

    private String userName;
    private String title;
    private String body;
    private String link;
    private boolean hidden;
    private String image;

    private Date createdAt;
    private Date updatedAt;
}
