<div class="box submitted_box" data-storage_key="submitted">
    <div class="title">
        <a class="data_table_opener" data-toggle="collapse" href="#multiCollapseExample2_${i}" aria-expanded="false" aria-controls="#multiCollapseExample2_${i}">
            <i class="fas fa-caret-right"></i><span>일보제출현황</span>
        </a><!-- end : title -->
    </div>
    <div class="collapse multi-collapse data_table_wrapper" id="multiCollapseExample2_${i}">
        <div class="table-area m-view">
            <table class="table data_table submitted_data_table" data-dept_cd="${result.master.deptCd}" data-day="${result.master.day}" data-ordr_no="${result.master.ordrNo}">
                <thead>
                <tr>
                    <th>계약</th>
                    <th>업체명</th>
                    <th>일보상태</th>
                    <th>변경일시</th>
                    <th>일보대상</th>
                    <th class="request_submit">요청</th>
                    <th>변경이력</th>
                </tr>
                </thead>
                <tbody></tbody>
                <tfoot>
                <tr class="template" data-ctrc_no="" data-partner_no="" data-objt_yn="" data-sort_no_seq="" data-cstknd_nm="" data-partner_nm="" data-sta_cd="">
                    <td data-col="ctrc_nm" class="name"></td>
                    <td data-col="partner_nm" class="company font-weight-bold"><a href="#" class="company_open" data-toggle="modal" data-target="#company-modify-modal"></a></td>
                    <td data-col="sta_cd" class="text-center"><span class="state"></span></td>
                    <td data-col="updt_dm" class="text-center m-hide"></td>
                    <td data-col="objt_yn" class="text-center subject">
                        <div class="form-check">
                            <input type="checkbox" class="form-check-input" id="objt_yn${i}" name="objt_yn" value="Y">
                            <label class="form-check-label" for="objt_yn${i}">일보대상</label>
                        </div>
                    </td>
                    <td data-col="request" class="text-center request_submit"><a href="#none" class="text-primary">제출요청</a></td>
                    <td data-col="history" class="text-center history"><a href="#none" class="text-primary" data-toggle="modal" data-target="#modal-change-history">변경이력</a></td>
                </tr>
                </tfoot>
            </table>
        </div><!-- end : table-list -->
    </div>
</div>
