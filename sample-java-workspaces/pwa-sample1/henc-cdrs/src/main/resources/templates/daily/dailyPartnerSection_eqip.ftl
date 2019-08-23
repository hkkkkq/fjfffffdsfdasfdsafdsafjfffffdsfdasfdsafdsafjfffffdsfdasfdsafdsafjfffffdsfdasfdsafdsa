<div class="box eqip_box" data-storage_key="eqip">
    <div class="title">
        <a class="data_table_opener" data-toggle="collapse" href="#multiCollapseExample4_${i}" aria-expanded="true" aria-controls="#multiCollapseExample4_${i}">
            <i class="fas fa-caret-right"></i><span>장비투입</span>
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
    <div class="collapse multi-collapse show data_table_wrapper" id="multiCollapseExample4_${i}">
        <div class="btn-toolbar btn-area eqip_btns data_table_actions">
            <button type="button" class="btn btn-sm add"><i class="fas fa-plus"></i></button>
            <button type="button" class="btn btn-sm del"><i class="fas fa-minus"></i></button>
        </div><!-- end : btn-toolba -->
        <div class="table-list">
            <table class="table data_table eqip_data_table" data-dept_cd="${result.master.deptCd}" data-day="${result.master.day}" data-ordr_no="${result.master.ordrNo}" data-partner_no="${data.ctrc.coprcpNo}" data-ctrc_no="${data.ctrc.ctrcNo}">
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
                    <td class="triple"><input type="text" class="form-control form-control-sm autocomplete" placeholder="규격" name="spec" data-parsley-maxlength="100" data-selected_spec="" readonly></td>
                    <td class="triple hide-row"><input type="text" class="form-control form-control-sm autocomplete" placeholder="단위" name="unit" data-parsley-maxlength="10" data-selected_unit="" readonly></td>
                    <td class="triple hide-row"><input type="text" class="form-control form-control-sm text-right" placeholder="전일누계" name="" data-parsley-type="integer" readonly></td>
                    <td class="triple "><input type="text" class="form-control form-control-sm text-right validate" placeholder="금일" name="qty" data-parsley-type="integer" required></td>
                    <td class="triple hide-row"><input type="text" class="form-control form-control-sm text-right" placeholder="누계" name="" data-parsley-type="integer" readonly></td>
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