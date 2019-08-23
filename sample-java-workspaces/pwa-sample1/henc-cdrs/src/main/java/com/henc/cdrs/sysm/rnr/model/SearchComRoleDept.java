package com.henc.cdrs.sysm.rnr.model;

import java.util.List;

import com.henc.cdrs.sysm.sec.usr.model.ComUserRoleDept;
import lombok.Data;

@Data
public class SearchComRoleDept extends ComRoleDept{
    private String userId;
    private String useYn;
    private List<ComUserRoleDept> comUserRoleDeptsYes;
    private List<ComUserRoleDept> comUserRoleDeptsNo;	
}
