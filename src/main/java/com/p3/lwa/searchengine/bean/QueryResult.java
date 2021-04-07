package com.p3.lwa.searchengine.bean;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class QueryResult extends RepresentationModel<QueryResult> implements Serializable {
    private UUID dataId;
    private String query;
    private List<Map<String,Object>> page;
}
