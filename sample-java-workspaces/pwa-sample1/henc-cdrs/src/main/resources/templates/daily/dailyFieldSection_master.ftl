<div class="box master_box" data-storage_key="master">
    <div class="title">
        <a class="data_table_opener" data-toggle="collapse" href="#multiCollapseExample1_${i}" aria-expanded="true" aria-controls="#multiCollapseExample1_${i}">
            <i class="fas fa-caret-right"></i><span>공통정보</span>
        </a><!-- end : title -->
    </div>
    <div class="collapse multi-collapse show" id="multiCollapseExample1_${i}">
        <section class="form-area">
            <table class="table data_table master_data_table" data-dept_cd="${result.master.deptCd}" data-day="${result.master.day}" data-ordr_no="${result.master.ordrNo}" data-weather_cd="${(result.master.weatherCd)!""}" data-lowest_tempe="${(result.master.lowestTempe)!""}" data-highest_tempe="${(result.master.highestTempe)!""}" data-ptcl_mtr="${(result.master.ptclMtr)!""}" data-sta_cd="${(result.master.staCd)!"10"}">
                <colgroup>
                    <col width="140px">
                    <col width="*">
                    <col width="140px">
                    <col width="*">
                </colgroup>
                <tbody>
                <tr>
                    <th scope="col">날씨</th>
                    <td>
                        <select class="form-control form-control-sm min weather_cd validate" name="weather_cd" required>

                            <#list weatherCd as data>
                                <option data-key="${data.key}" value="${data.key}" ${(data.key == (result.master.weatherCd)!"")?then("selected", "")} >${data.value}</option>
                            </#list>

                        </select>
                    </td>
                    <th scope="col">기온(최저/최고)</th>
                    <td class="tem-dual">
                        <input type="text" class="form-control form-control-sm mr-1 validate" placeholder="최저온도" name="lowest_tempe" value="${(result.master.lowestTempe)!""}" data-parsley-type="number" required />
                        <input type="text" class="form-control form-control-sm validate" placeholder="최고온도" name="highest_tempe" value="${(result.master.highestTempe)!""}" data-parsley-type="number" required />
                    </td>
                </tr>
                <tr>
                    <th scope="col">특이사항</th>
                    <td colspan="3"><textarea class="form-control" rows="2" name="ptcl_mtr">${(result.master.ptclMtr)!""}</textarea></td>
                </tr>
                <#if result.aprvUsers?size gt 0>
                <tr>
                    <th scope="col">결재자</th>
                    <td colspan="3">
                        <ol class="aprv_users">
                            <#list result.aprvUsers as aprvUser>
                            <li data-sta_cd="${(aprvUser.staCd)!"00"}">
                                <strong>${aprvUser.name}</strong>
                                <span class="aprv_sta_cd"></span><span>${(aprvUser.aprvDt)!""}</span>
                                <#if ((aprvUser.rsn)!"") != "">
                                    <span>${(aprvUser.rsn)!""}</span>
                                </#if>
                            </li>
                            </#list>
                        </ol>
                    </td>
                </tr>
                </#if>
                </tbody>
            </table>
        </section>
    </div>
</div>
