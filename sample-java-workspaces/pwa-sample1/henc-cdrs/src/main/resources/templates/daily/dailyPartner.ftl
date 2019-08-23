<div class="accordion" id="accordionExample" data-sta_cd="${(result.master.staCd)!"00"}">

<#list result.ctrcData as data>
    <#assign i = data?index>
    <#assign pageListCount = 10>
    <#assign pageNavigationCount = 5>

    <div class="ctrc_group" data-data_index="${i}" data-dept_cd="${result.master.deptCd}" data-day="${result.master.day}" data-ordr_no="${result.master.ordrNo}" data-partner_no="${data.ctrc.coprcpNo}" data-ctrc_no="${data.ctrc.ctrcNo}" data-partner_nm="${data.ctrc.coprcpNm}" data-cstknd_nm="${data.ctrc.cstkndNm}" data-sta_cd="${(data.comp.staCd)!"00"}">
        <div class="header" id="heading_${i}">
            <a href="" data-toggle="collapse" data-target="#collapse_${i}" aria-expanded="true" aria-controls="collapse_${i}" class="float-left">
                <span class="state">상태</span><span class="type">${data.ctrc.ctrcNm}</span>
            </a>
            <div class="btn-area float-right master_btns">
                <button type="button" class="btn btn-dark btn-sm btn-copy-data" ><span>작업이력</span>복사</button>
                <button type="button" class="btn btn-dark btn-sm btn-save-data">저장</button>
                <button type="button" class="btn btn-dark btn-sm btn-approval-data">제출</button>
            </div><!-- end : btn-toolba -->
        </div>
        <div id="collapse_${i}" class="collapse ctrc_sections" aria-labelledby="heading_${i}" data-parent="#accordionExample">
            <#include "dailyPartnerSection.ftl" >
        </div>
    </div>
</#list>
</div>
<!-- 작업이력 복사 modal -->
<div class="modal fade" id="hitoryModal" tabindex="-1" role="dialog" aria-labelledby="hitoryModalTitle" aria-hidden="true" data-target_day="" data-ctrc_group_index="">
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
                            <tr class="template text-center" data-dept_cd="" data-day="" data-ordr_no="" data-partner_no="" data-ctrc_no="">
                                <td class="day"></td>
                                <td class="dy"></td>
                                <td class="submitter"></td>
                                <td class="labatd_cnt"></td>
                                <td class="eqip_cnt"></td>
                                <td class="mtil_cnt"></td>
                                <td class="action"><button type="button" class="btn btn-sm btn-copy-create">복사</button></td>
                            </tr>
                        </tfoot>
                    </table>
                </section>
            </div>
        </div>
    </div>
</div><!-- end : 작업이력 복사 modal -->