package com.henc.cdrs.daily.model;

import com.henc.dream.domain.Domain;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class AutocompleteSearchParams extends Domain {

    @Nullable
    private String target;

    @Nullable
    private String type;

    @Nullable
    private String term;

}
