<#assign i = 0>

<div class="ctrc_group active" data-data_index="${i}" data-dept_cd="${result.master.deptCd}"
     data-day="${result.master.day}" data-ordr_no="${result.master.ordrNo}" data-chg_no="${result.master.chgNo!-1}"
     data-chg_seq="#{result.master.chgSeq!-1}" data-partner_no="${result.ctrc.coprcpNo}"
     data-ctrc_no="${result.ctrc.ctrcNo}" data-partner_nm="${result.ctrc.coprcpNm}"
     data-cstknd_nm="${result.ctrc.cstkndNm}" data-sta_cd="${(result.master.staCd)!"10"}"
     data-is_signer_seq="${result.isSignerSeq}" data-bf_sta_cd="${(result.staHst.bfStaCd)!""}" data-sta_cd_count="${(result.staCdCount)!0}">

    <div class="header" id="heading_${i}">
        <a href="" data-toggle="collapse" data-target="#collapse_${i}" aria-expanded="true"
           aria-controls="collapse_${i}" class="float-left">
            <span class="state">상태</span><span class="type">공사일보</span>
        </a>
        <div class="btn-area float-right master_btns hide">
            <button type="button" class="btn btn-dark btn-sm btn-save-data">저장</button>
            <button type="button" class="btn btn-dark btn-sm btn-change-data">변경작성</button>
            <button type="button" class="btn btn-dark btn-sm btn-change-cancel-data">변경작성취소</button>
            <button type="button" class="btn btn-dark btn-sm btn-approval-data">결재</button>
            <button type="button" class="btn btn-dark btn-sm btn-reject-data" data-who="field">반려</button>
            <button type="button" class="btn btn-dark btn-sm btn-report"><i class="far fa-file-pdf"></i>공사일보</button>
        </div><!-- end : btn-toolba -->
    </div>
    <div id="collapse_${i}" class="collapse show ctrc_sections" aria-labelledby="heading_${i}"
         data-parent="#accordionExample">

        <#include "dailyFieldSection.ftl" >

    </div>
</div>

<!-- 협력사 일보 조회 modal -->
<div class="modal fade" id="company-modify-modal" role="dialog" data-dept_cd="${result.master.deptCd}"
     data-day="${result.master.day}" data-ordr_no="${result.master.ordrNo}" data-partner_no="" data-partner_nm=""
     data-ctrc_no="" data-ctrc_nm="" data-cstknd_nm="" data-sta_cd="" data-sort_no_seq="" data-objt_yn="">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">협력사 일보 조회</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body"></div>
        </div>
    </div>
</div><!-- end : modal -->

<!-- 작업이력 복사 modal -->
<div class="modal fade" id="hitoryModal" tabindex="-1" role="dialog" aria-labelledby="hitoryModalTitle"
     aria-hidden="true" data-target_day="" data-ctrc_group_index="">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="hitoryModalTitle">작업이력 복사</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <section class="form-area">
                    <table class="table header_table">
                        <colgroup>
                            <col width="*">
                            <col width="*">
                            <col width="*">
                            <col width="*">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="col">협력사</th>
                            <td class="partner_nm"></td>
                            <th scope="col">공종</th>
                            <td class="cstknd_nm"></td>
                        </tr>
                        </tbody>
                    </table>
                </section>
                <section class="table-area">
                    <table class="table data_table">
                        <thead>
                        <tr>
                            <th>날짜</th>
                            <th>요일</th>
                            <th>작성자</th>
                            <th>출역</th>
                            <th>장비</th>
                            <th>자재</th>
                            <th>적용</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                        <tfoot>
                        <tr class="template text-center" data-dept_cd="" data-day="" data-ordr_no="" data-partner_no=""
                            data-ctrc_no="">
                            <td class="day"></td>
                            <td class="dy"></td>
                            <td class="submitter"></td>
                            <td class="labatd_cnt"></td>
                            <td class="eqip_cnt"></td>
                            <td class="mtil_cnt"></td>
                            <td class="action">
                                <button type="button" class="btn btn-sm btn-copy-create">복사</button>
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                </section>
            </div>
        </div>
    </div>
</div>
<!-- end : 작업이력 복사 modal -->

<!-- 협력사 일보 저장 사유 modal -->
<div class="modal fade" id="company-modify-reason-modal" role="dialog" data-dept_cd="${result.master.deptCd}"
     data-day="${result.master.day}" data-ordr_no="${result.master.ordrNo}" data-partner_no="" data-ctrc_no="">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">일보 저장 사유</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <section class="form-area">
                    <div class="btn-area float-right mb-2">
                        <button type="button" class="btn btn-dark btn-sm btn-modal-save">저장</button>
                    </div>
                    <table class="table ">
                        <thead>
                        <tr>
                            <th>저장사유</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><textarea class="form-control" rows="2" name="chg_rsn"
                                          data-parsley-maxlength="2000"></textarea></td>
                        </tr>
                        </tbody>
                    </table>
                </section>
            </div>
        </div>
    </div>
</div><!-- end : modal -->

<!-- 공사일보 상신 모달 -->
<div class="modal fade" id="modal-approval" data-dept_cd="${result.master.deptCd}" data-day="${result.master.day}"
     data-ordr_no="${result.master.ordrNo}" data-partner_no="${result.ctrc.coprcpNo}"
     data-ctrc_no="${result.ctrc.ctrcNo}">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">일보 상신</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <section class="form-area">
                    <div class="btn-area float-right mb-2">
                        <button type="button" class="btn btn-secondary btn-sm btn-search">검색</button>
                        <button type="button" class="btn btn-dark btn-sm btn-approval" data-appr_action="approval">상신
                        </button>
                    </div>
                    <table class="table">
                        <colgroup>
                            <col width="120px">
                            <col width="*">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="col">날짜</th>
                            <td class="aprv_dt"></td>
                        </tr>
                        <tr>
                            <th scope="col">승인 유형</th>
                            <td>
                                <select class="form-control form-control-sm aprv_type">
                                    <option value="next">차상위 결재자 지정</option>
                                    <option value="complete">최종 승인</option>
                                </select>
                            </td>
                        </tr>
                        <tr class="aprv_type_next">
                            <th scope="col">결재자 검색</th>
                            <td>
                                <input type="text" class="form-control form-control-sm" placeholder="성명" name="search">
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </section>
                <section class="table-area aprv_type_next">
                    <table class="table data_table">
                        <thead>
                        <tr>
                            <th></th>
                            <th>사번</th>
                            <th>성명</th>
                            <th>직급</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                        <tfoot>
                        <tr class="template text-center">
                            <td class="check"><input type="radio" name="approve_user" value=""></td>
                            <td class="emp_no"></td>
                            <td class="name"></td>
                            <td class="position"></td>
                        </tr>
                        <tr class="template_nodata">
                            <td colspan="4" class="text-center">결재자를 검색하여 주십시오.</td>
                        </tr>
                        </tfoot>
                    </table>
                    <nav class="mt-2 d-flex justify-content-center page_control">
                        <ul class="pagination page_navigator" data-page_no="1" data-page_list_count="10"
                            data-page_navigation_count="5" data-total="" data-total_page="" data-current_page="1">
                        </ul>
                    </nav>
                </section>
            </div>
        </div>
    </div>
</div>

<!-- 공사일보 반려 모달 -->
<div class="modal fade" id="modal-return" data-dept_cd="${result.master.deptCd}" data-day="${result.master.day}" data-ordr_no="${result.master.ordrNo}" data-partner_no="" data-ctrc_no="" data-who="">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">일보 반려</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <section class="form-area">
                    <div class="btn-area float-right mb-2">
                        <button type="button" class="btn btn-secondary btn-sm btn-cancel">취소</button>
                        <button type="button" class="btn btn-dark btn-sm btn-reject">반려</button>
                    </div>
                    <table class="table">
                        <colgroup>
                            <col width="120px">
                            <col width="*">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="col">날짜</th>
                            <td class="aprv_dt"></td>
                        </tr>
                        <tr>
                            <th scope="col">반려사유</th>
                            <td>
                                <textarea class="form-control" rows="5" name="return_rsn"></textarea>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </section>
            </div>
        </div>
    </div>
</div><!-- end : modal -->

<!-- 변경이력 -->
<div class="modal fade" id="modal-change-history">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">일보 상태 변경 이력</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <section class="form-area">
                    <table class="table header_table">
                        <colgroup>
                            <col width="*">
                            <col width="*">
                            <col width="*">
                            <col width="*">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="col">협력사</th>
                            <td class="partner_nm"></td>
                            <th scope="col">공종</th>
                            <td class="cstknd_nm"></td>
                        </tr>
                        </tbody>
                    </table>
                </section>
                <section class="table-area">
                    <table class="table data_table">
                        <thead>
                        <tr>
                            <th>변경 전 상태</th>
                            <th>변경자</th>
                            <th>변경 후 상태</th>
                            <th>변경일시</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                        <tfoot>
                        <tr class="template text-center">
                            <td class="bf_sta_cd"></td>
                            <td class="chg_updt_name"></td>
                            <td class="chg_sta_cd"></td>
                            <td class="wrtr_dm"></td>
                        </tr>
                        </tfoot>
                    </table>
                </section>
            </div>
        </div>
    </div>
</div><!-- end : modal -->