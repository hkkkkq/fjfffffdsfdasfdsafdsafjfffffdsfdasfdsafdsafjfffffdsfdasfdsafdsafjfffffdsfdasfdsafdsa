package com.henc.cdrs.sysm.main.menu.model;

import lombok.Data;

@Data
public class Menu {
    private int rn;
    private String menuId;
    private String uprMenuId;
    private String menuClsCd;
    private int menuSeq;
    private String nmspcCd;
    private String nmspcVal;
    private String roleId;
    private String pgmId;
    private String pgmTypCd;
    private String uriNm;
    private int lvl;
    private int isLeaf;
    private int pre;
    private String nxt;
    private String nxtUprMenuId;
    private int divider;
    private String sysClsCd;
}
