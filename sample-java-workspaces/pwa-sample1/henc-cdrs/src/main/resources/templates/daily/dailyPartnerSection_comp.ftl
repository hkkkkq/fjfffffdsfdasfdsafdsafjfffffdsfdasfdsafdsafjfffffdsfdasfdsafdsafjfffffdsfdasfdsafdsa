<#assign perddMainWork = (data.comp.perddMainWork)!"">
<#assign nmddMainWork = (data.comp.nmddMainWork)!"">

<div class="box comp_box" data-storage_key="comp">
    <div class="title">
        <a class="data_table_opener" data-toggle="collapse" href="#multiCollapseExample1_${i}" aria-expanded="true" aria-controls="#multiCollapseExample1_${i}">
            <i class="fas fa-caret-right"></i><span>주요업무</span>
        </a><!-- end : title -->
    </div>
    <div class="collapse multi-collapse show" id="multiCollapseExample1_${i}">
        <div class="tablest data_table comp_data_table" data-need_change="" data-row_status="" data-dept_cd="${result.master.deptCd}" data-day="${result.master.day}" data-ordr_no="${result.master.ordrNo}" data-partner_no="${data.ctrc.coprcpNo}" data-ctrc_no="${data.ctrc.ctrcNo}" data-perdd_main_work="${perddMainWork}" data-nmdd_main_work="${nmddMainWork}" data-sta_cd="${(data.comp.staCd)!""}" data-objt_yn="${(data.comp.objtYn)!"Y"}" data-sort_no_seq="${(data.comp.sortNoSeq)!99}" data-submitter="${(data.comp.submitter)!""}">
            <dl class="w-20">
                <dt>공종</dt>
                <dd class="cstknd_nm">${data.ctrc.cstkndNm}</dd>
            </dl>
            <dl class="w-40">
                <dt>금일주요작업</dt>
                <dd><textarea class="form-control validate" rows="4" name="perdd_main_work" data-parsley-maxlength="2000" required>${perddMainWork}</textarea></dd>
            </dl>
            <dl class="w-40">
                <dt>명일예정사항</dt>
                <dd><textarea class="form-control validate" rows="4" name="nmdd_main_work" data-parsley-maxlength="2000" required>${nmddMainWork}</textarea></dd>
            </dl>
        </div><!-- end : tablest -->
    </div>
</div>