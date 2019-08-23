<#assign pageListCount = 10>
<#assign pageNavigationCount = 5>

<section class="form-area ctrc_box">
    <div class="btn-area float-right mb-2">
        <button type="button" class="btn btn-secondary btn-sm btn-modal-approve">승인</button>
        <button type="button" class="btn btn-secondary btn-sm btn-modal-reject" data-who="partner">반려</button>
        <button type="button" class="btn btn-secondary btn-sm btn-modal-cancel-approve">승인취소</button>
        <button type="button" class="btn btn-secondary btn-sm btn-modal-copy">작업이력 복사</button>
        <button type="button" class="btn btn-dark btn-sm btn-modal-save-confirm">저장</button>
    </div>
    <table class="table">
        <colgroup>
            <col width="*">
            <col width="*">
            <col width="*">
            <col width="*">
            <col width="*">
            <col width="*">
        </colgroup>
        <tbody>
        <tr>
            <th scope="col">협력사</th>
            <td data-col="partner_nm">${result.ctrc.coprcpNm}</td>
            <th scope="col">계약</th>
            <td data-col="ctrc_nm">${result.ctrc.ctrcNm}</td>
            <th scope="col">공종</th>
            <td data-col="cstknd_nm">${result.ctrc.cstkndNm}</td>
        </tr>
        </tbody>
    </table>
</section>

<section class="contents mt-3 data_box comp_box" data-storage_key="comp">
    <div class="d-flex justify-content-between pb-2">
        <h6 class="float-left">주요업무</h6>
    </div>
    <div class="table-area data_table_wrapper">
        <table class="table data_table comp_data_table" data-need_change="" data-row_status="" data-dept_cd="${result.master.deptCd}" data-day="${result.master.day}" data-ordr_no="${result.master.ordrNo}" data-partner_no="${result.ctrc.coprcpNo}" data-ctrc_no="${result.ctrc.ctrcNo}" data-perdd_main_work="${(result.comp.perddMainWork)!""}" data-nmdd_main_work="${(result.comp.nmddMainWork)!""}" data-objt_yn="${(result.comp.objtYn)!"N"}" data-sort_no_seq="#{((result.comp.sortNoSeq)!99)}" data-sta_cd="${(result.comp.staCd)!""}" data-submitter="${(result.comp.submitter)!""}">
            <thead>
            <tr>
                <th>금일주요작업</th>
                <th>명일예정사항</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><textarea class="form-control validate" rows="2" name="perdd_main_work" data-parsley-maxlength="2000" required>${(result.comp.perddMainWork)!""}</textarea></td>
                <td><textarea class="form-control validate" rows="2" name="nmdd_main_work" data-parsley-maxlength="2000" required>${(result.comp.nmddMainWork)!""}</textarea></td>
            </tr>
            </tbody>
        </table>
    </div><!-- end : table-list -->
</section>

<section class="contents mt-3 data_box labatd_box" data-storage_key="labatd">
    <div class="d-flex justify-content-between pb-1">
        <div>
            <h6 class="float-left">출역인원</h6>
            <!-- total 추가 -->
            <div class="total-list">
                <dl>
                    <dt>Total.</dt>
                    <dd class="total_text"></dd>
                </dl>
                <select class="form-control form-control-sm data_list_counter page_control">
                    <option value="10" selected>10</option>
                    <option value="50">50</option>
                    <option value="100">100</option>
                    <option value="">전체</option>
                </select>
            </div><!-- end : total-list -->
        </div>
        <div class="btn-area labatd_btns data_table_actions">
            <button type="button" class="btn btn-sm add"><i class="fas fa-plus"></i></button>
            <button type="button" class="btn btn-sm del"><i class="fas fa-minus"></i></button>
        </div><!-- end : btn-toolba -->
    </div>
    <div class="table-list data_table_wrapper">
        <table class="table data_table labatd_data_table" data-dept_cd="${result.master.deptCd}" data-day="${result.master.day}" data-ordr_no="${result.master.ordrNo}" data-partner_no="${result.ctrc.coprcpNo}" data-ctrc_no="${result.ctrc.ctrcNo}">
            <thead>
            <tr>
                <th>성명</th>
                <th>직종</th>
                <th>생년월일</th>
                <th>비고</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
            <tfoot>
            <tr class="template" data-need_change="" data-row_status="" data-row_id="" data-dept_cd="" data-day="" data-ordr_no="" data-partner_no="" data-ctrc_no="" data-name="" data-note="" data-labatd_pcnt_no="" data-ocpt_no="" data-brtdy="">
                <td class="triple">
                    <input type="text" class="form-control form-control-sm autocomplete validate" placeholder="성명" name="name" data-selected_name="" data-new="" data-parsley-maxlength="50" required readonly>
                </td>
                <td class="triple">
                    <select class="form-control form-control-sm validate" name="ocpt_no" data-selected_ocpt_no="" required disabled>
                        <option value="">직종</option>
                        <#list result.ocpts as ocpt>
                            <option value="${ocpt.ocptNo}">${ocpt.ocptNm}</option>
                        </#list>
                    </select>
                </td>
                <td class="triple"><input type="text" class="form-control form-control-sm validate" placeholder="생년월일" name="brtdy" data-parsley-maxlength="10" data-inputmask="'alias': 'yyyy-mm-dd'" data-parsley-type="date" required readonly></td>
                <td class="dual hide-row"><input type="text" class="form-control form-control-sm" placeholder="비고" name="note" data-parsley-maxlength="200"></td>
                <td class="btn-default">
                    <div class="btn-toolbar">
                        <button type="button" class="btn btn-sm"><i class="fas fa-times"></i></button>
                        <button type="button" class="btn btn-sm fold"><i class="fas fa-sort-down"></i></button>
                    </div><!-- end : btn-toolba -->
                </td>
            </tr>
            </tfoot>
        </table>
    </div><!-- end : table-list -->

    <!-- pagination 추가 -->
    <nav class="mt-2 d-flex justify-content-center page_control">
        <ul class="pagination page_navigator" data-page_no="1" data-page_list_count="${pageListCount}" data-page_navigation_count="${pageNavigationCount}" data-total="" data-total_page="" data-current_page="1">
        </ul>
    </nav>
    <!-- end : pagination -->
</section>

<section class="contents mt-3 data_box mtil_box" data-storage_key="mtil">
    <div class="d-flex justify-content-between pb-1">
        <div>
            <h6 class="float-left">주요자재</h6>
            <!-- total 추가 -->
            <div class="total-list">
                <dl>
                    <dt>Total.</dt>
                    <dd class="total_text"></dd>
                </dl>
                <select class="form-control form-control-sm data_list_counter page_control">
                    <option value="10" selected>10</option>
                    <option value="50">50</option>
                    <option value="100">100</option>
                    <option value="">전체</option>
                </select>
            </div><!-- end : total-list -->
        </div>
        <div class="btn-area mtil_btns data_table_actions">
            <button type="button" class="btn btn-sm add"><i class="fas fa-plus"></i></button>
            <button type="button" class="btn btn-sm del"><i class="fas fa-minus"></i></button>
        </div><!-- end : btn-toolba -->
    </div>
    <div class="table-list data_table_wrapper">
        <table class="table data_table mtil_data_table" data-dept_cd="${result.master.deptCd}" data-day="${result.master.day}" data-ordr_no="${result.master.ordrNo}" data-partner_no="${result.ctrc.coprcpNo}" data-ctrc_no="${result.ctrc.ctrcNo}">
            <thead>
            <tr>
                <th>품명</th>
                <th>규격</th>
                <th>단위</th>
                <th>전일누계</th>
                <th>금일</th>
                <th>누계</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
            <tfoot>
            <tr class="template" data-need_change="" data-row_status="" data-row_id="" data-dept_cd="" data-day="" data-ordr_no="" data-partner_no="" data-ctrc_no="" data-mtil_no="" data-bizhdof_cd="" data-mtil_nm="" data-spec="" data-unit="" data-qty="">
                <td class="triple ui-widget"><input type="text" class="form-control form-control-sm autocomplete validate" placeholder="품명" name="mtil_nm" data-parsley-maxlength="500" data-selected_mtil_nm="" required readonly></td>
                <td class="triple ui-widget"><input type="text" class="form-control form-control-sm autocomplete" placeholder="규격" name="spec" data-parsley-maxlength="100" data-selected_spec="" readonly></td>
                <td class="triple hide-row"><input type="text" class="form-control form-control-sm autocomplete" placeholder="단위" name="unit" data-parsley-maxlength="10" data-selected_unit="" readonly></td>
                <td class="triple hide-row"><input type="text" class="form-control form-control-sm text-right" placeholder="전일누계" name="before_sum_qty" data-parsley-type="integer" readonly></td>
                <td class="triple ui-widget"><input type="text" class="form-control form-control-sm text-right validate" placeholder="금일" name="qty" data-parsley-type="integer" required></td>
                <td class="triple hide-row"><input type="text" class="form-control form-control-sm text-right" placeholder="누계" name="sum_qty" data-parsley-type="integer" readonly></td>
                <td class="btn-default">
                    <div class="btn-toolbar">
                        <button type="button" class="btn btn-sm"><i class="fas fa-times"></i></button>
                        <button type="button" class="btn btn-sm fold"><i class="fas fa-sort-down"></i></button>
                    </div><!-- end : btn-toolba -->
                </td>
            </tr>
            </tfoot>
        </table>
    </div><!-- end : table-list -->
    <!-- pagination 추가 -->
    <nav class="mt-2 d-flex justify-content-center page_control">
        <ul class="pagination page_navigator" data-page_no="1" data-page_list_count="${pageListCount}" data-page_navigation_count="${pageNavigationCount}" data-total="" data-total_page="" data-current_page="1">
        </ul>
    </nav><!-- end : pagination -->
</section>

<section class="contents mt-3 data_box eqip_box" data-storage_key="eqip">
    <div class="d-flex justify-content-between pb-1">
        <div>
            <h6 class="float-left">주요장비</h6>
            <!-- total 추가 -->
            <div class="total-list">
                <dl>
                    <dt>Total.</dt>
                    <dd class="total_text"></dd>
                </dl>
                <select class="form-control form-control-sm data_list_counter page_control">
                    <option value="10" selected>10</option>
                    <option value="50">50</option>
                    <option value="100">100</option>
                    <option value="">전체</option>
                </select>
            </div><!-- end : total-list -->
        </div>
        <div class="btn-area eqip_btns data_table_actions">
            <button type="button" class="btn btn-sm add"><i class="fas fa-plus"></i></button>
            <button type="button" class="btn btn-sm del"><i class="fas fa-minus"></i></button>
        </div><!-- end : btn-toolba -->
    </div>
    <div class="table-list data_table_wrapper">
        <table class="table data_table eqip_data_table" data-dept_cd="${result.master.deptCd}" data-day="${result.master.day}" data-ordr_no="${result.master.ordrNo}" data-partner_no="${result.ctrc.coprcpNo}" data-ctrc_no="${result.ctrc.ctrcNo}">
            <thead>
            <tr>
                <th>장비명</th>
                <th>규격</th>
                <th>단위</th>
                <th>전일누계</th>
                <th>금일</th>
                <th>누계</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
            <tfoot>
            <tr class="template" data-need_change="" data-row_status="" data-row_id="" data-dept_cd="" data-day="" data-ordr_no="" data-partner_no="" data-ctrc_no="" data-eqip_no="" data-bizhdof_cd="" data-eqip_nm="" data-spec="" data-unit="" data-qty="">
                <td class="triple" ><input type="text" class="form-control form-control-sm autocomplete validate" placeholder="장비명" name="eqip_nm" data-parsley-maxlength="500" data-selected_eqip_nm="" required readonly></td>
                <td class="triple "><input type="text" class="form-control form-control-sm autocomplete" placeholder="규격" name="spec" data-parsley-maxlength="100" data-selected_spec="" readonly></td>
                <td class="triple hide-row"><input type="text" class="form-control form-control-sm autocomplete" placeholder="단위" name="unit" data-parsley-maxlength="10" data-selected_unit="" readonly></td>
                <td class="triple hide-row"><input type="text" class="form-control form-control-sm text-right" placeholder="전일누계" name="before_sum_qty" data-parsley-type="integer" readonly></td>
                <td class="triple "><input type="text" class="form-control form-control-sm text-right validate" placeholder="금일" name="qty" data-parsley-type="integer" required></td>
                <td class="triple hide-row"><input type="text" class="form-control form-control-sm text-right" placeholder="누계" name="sum_qty" data-parsley-type="integer" readonly></td>
                <td class="btn-default">
                    <div class="btn-toolbar">
                        <button type="button" class="btn btn-sm"><i class="fas fa-times"></i></button>
                        <button type="button" class="btn btn-sm fold"><i class="fas fa-sort-down"></i></button>
                    </div><!-- end : btn-toolba -->
                </td>
            </tr>
            </tfoot>
        </table>
    </div><!-- end : table-list -->
    <!-- pagination 추가 -->
    <nav class="mt-2 d-flex justify-content-center page_control">
        <ul class="pagination page_navigator" data-page_no="1" data-page_list_count="${pageListCount}" data-page_navigation_count="${pageNavigationCount}" data-total="" data-total_page="" data-current_page="1">
        </ul>
    </nav><!-- end : pagination -->
</section>

<#if mainBizhdofCd == "55">
<section class="contents mt-3 data_box qty_box" data-storage_key="qty">
    <div class="d-flex justify-content-between pb-1">
        <div>
            <h6 class="float-left">공사진행현황</h6>
            <!-- total 추가 -->
            <div class="total-list">
                <dl>
                    <dt>Total.</dt>
                    <dd class="total_text"></dd>
                </dl>
                <select class="form-control form-control-sm data_list_counter page_control">
                    <option value="10" selected>10</option>
                    <option value="50">50</option>
                    <option value="100">100</option>
                    <option value="">전체</option>
                </select>
            </div><!-- end : total-list -->
        </div>
        <div class="btn-area eqip_btns data_table_actions">
            <button type="button" class="btn btn-sm add"><i class="fas fa-plus"></i></button>
            <button type="button" class="btn btn-sm del"><i class="fas fa-minus"></i></button>
        </div><!-- end : btn-toolba -->
    </div>
    <div class="table-list data_table_wrapper">
        <table class="table data_table qty_data_table" data-dept_cd="${result.master.deptCd}" data-day="${result.master.day}" data-ordr_no="${result.master.ordrNo}" data-partner_no="${result.ctrc.coprcpNo}" data-ctrc_no="${result.ctrc.ctrcNo}">
            <thead>
            <tr>
                <th>Activity</th>
                <th>Specification</th>
                <th>Unit</th>
                <th>계획<br>설계물량(A)</th>
                <th>전일 누계</th>
                <th>금일</th>
                <th>누계(B)</th>
                <th>PROG(%, B/A)</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
            <tfoot>
            <tr class="template" data-need_change="" data-row_status="" data-row_id="" data-dept_cd="" data-day="" data-ordr_no="" data-partner_no="" data-ctrc_no="" data-qty_no="" data-qty_nm="" data-spec="" data-unit="" data-plan_qty="" data-perdd_qty="" data-prgr_pscd="">
                <td class="dual"><input type="text" class="form-control form-control-sm autocomplete validate" placeholder="Activity" name="qty_nm" data-parsley-maxlength="500" data-selected_qty_nm="" required readonly></td>
                <td class="dual"><input type="text" class="form-control form-control-sm autocomplete" placeholder="Specification" name="spec" data-parsley-maxlength="100" data-selected_spec="" readonly></td>
                <td class="triple hide-row"><input type="text" class="form-control form-control-sm autocomplete" placeholder="Unit" name="unit" data-parsley-maxlength="10" data-selected_unit="" readonly></td>
                <td class="triple hide-row"><input type="text" class="form-control form-control-sm text-right validate" placeholder="계획설계물량" name="plan_qty" data-parsley-type="number" required></td>
                <td class="triple hide-row"><input type="text" class="form-control form-control-sm text-right" placeholder="전일누계" name="before_sum_qty" data-parsley-type="number" readonly></td>
                <td class="triple hide-row"><input type="text" class="form-control form-control-sm text-right validate" placeholder="금일" name="perdd_qty" data-parsley-type="number" required></td>
                <td class="triple hide-row"><input type="text" class="form-control form-control-sm text-right" placeholder="누계" name="sum_qty" data-parsley-type="number" readonly></td>
                <td class="triple hide-row"><input type="text" class="form-control form-control-sm text-right" placeholder="Progress" name="prgr_pscd" data-parsley-type="number" readonly></td>
                <td class="btn-default">
                    <div class="btn-toolbar">
                        <button type="button" class="btn btn-sm"><i class="fas fa-times"></i></button>
                        <button type="button" class="btn btn-sm fold"><i class="fas fa-sort-down"></i></button>
                    </div><!-- end : btn-toolba -->
                </td>
            </tr>
            </tfoot>
        </table>
    </div><!-- end : table-list -->
    <!-- pagination 추가 -->
    <nav class="mt-2 d-flex justify-content-center page_control">
        <ul class="pagination page_navigator" data-page_no="1" data-page_list_count="${pageListCount}" data-page_navigation_count="${pageNavigationCount}" data-total="" data-total_page="" data-current_page="1">
        </ul>
    </nav><!-- end : pagination -->
</section>
</#if>