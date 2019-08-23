package com.henc.cdrs.daily.model;

import com.henc.cdrs.exception.CopyObjectException;
import com.henc.dream.domain.Domain;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class SearchDailyParams extends Domain implements Cloneable {

    private String deptCd;
    private String day;

    @Nullable
    private Integer ordrNo;

    @Nullable
    private String partnerNo;

    @Nullable
    private String ctrcNo;

    @Nullable
    private String staCd;

    @Nullable
    private Integer chgNo;

    @Nullable
    private Integer ctrcRenderingPosition;

    @Nullable
    private String bizhdofCd;

    @Nullable
    private String autocompleteLabel;

    @Nullable
    private AutocompleteSearchParams autocompleteSearchParams;

    public SearchDailyParams copy() throws CopyObjectException {
        try {
            return (SearchDailyParams) clone();
        } catch (CloneNotSupportedException e) {
            throw new CopyObjectException(e);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
