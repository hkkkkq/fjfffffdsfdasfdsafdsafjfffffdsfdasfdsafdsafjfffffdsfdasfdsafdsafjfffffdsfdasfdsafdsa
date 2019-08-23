<div class="box labatd_box" data-storage_key="labatd">
    <div class="title">
        <a class="data_table_opener" data-toggle="collapse" href="#multiCollapseExample2_${i}" aria-expanded="true" aria-controls="#multiCollapseExample2_${i}">
            <i class="fas fa-caret-right"></i><span>출역인원</span>
        </a><!-- end : title -->
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
    <div class="collapse multi-collapse show data_table_wrapper" id="multiCollapseExample2_${i}">
        <div class="btn-toolbar btn-area labatd_btns data_table_actions">
            <button type="button" class="btn btn-sm add"><i class="fas fa-plus"></i></button>
            <button type="button" class="btn btn-sm del"><i class="fas fa-minus"></i></button>
        </div><!-- end : btn-toolba -->

        <div class="table-list">
            <table class="table data_table labatd_data_table" data-dept_cd="${result.master.deptCd}" data-day="${result.master.day}" data-ordr_no="${result.master.ordrNo}" data-partner_no="${data.ctrc.coprcpNo}" data-ctrc_no="${data.ctrc.ctrcNo}">
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
                            <#list data.ocpts as ocpt>
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

        <nav class="mt-2 d-flex justify-content-center page_control">
            <ul class="pagination page_navigator" data-page_no="1" data-page_list_count="${pageListCount}" data-page_navigation_count="${pageNavigationCount}" data-total="" data-total_page="" data-current_page="1">
            </ul>
        </nav>
    </div>
</div>