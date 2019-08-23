package com.henc.cdrs.daily.model;

import com.henc.dream.domain.Domain;
import lombok.Data;

@Data
public class SearchApprovalUserParams extends Domain {

    private String search;
    private Integer first;
    private Integer last;
}
