<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nicg/NICG0500.js"></script>

<div class="container-fluid">
  <div class="row">
  
    <!--.col-sm-9-->
    <div class="main">
      <h3>가입정보
        <div class="floatR">
          <button type="button" id="btnPreProdCd" class="btn btn-control">이전 요금제</button>
        </div>
      </h3>
      <div>
        <form role="form" class="form-horizontal" name="formSch" method="post" >
        	<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
        	
        	<input type="hidden" id="totInslMonsNum" value="24" >
        	<input type="hidden" id="saleEngtOptnCd" value="PM" >
        	<input type="hidden" id="engtPerdMonsNum" value="24" >
        	<input type="hidden" id="freeCall" value="" >
        	<input type="hidden" id="freeData" value="" >
        	<input type="hidden" id="freeSms"  value="" >

              <!--modal-->
              <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-backdrop fade in" style="height: 832px;"></div>
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                      <h4 class="modal-title" id="myModalLabel">희망번호 선택</h4>
                    </div>
                    <div class="modal-body">
                      <div class="form-group">
                        <div class="col-sm-12">
                          <input type="text" class="form-control" id="" placeholder="뒷자리 번호입력">
                        </div>
                      </div>
                      <ul class="list-grp">
                        <a href="#" class="list-grp-item">0000</a> <a href="#" class="list-grp-item">1111</a> <a href="#" class="list-grp-item">2222</a> <a href="#" class="list-grp-item">3333</a> <a href="#" class="list-grp-item">4444</a>
                      </ul>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-primary">Save</button>
                    </div>
                  </div>
                </div>
              </div>
              <!--/modal-->

              <input type="hidden" class="form-control" id="svcNo" placeholder="010-0000-0000">


          <div class="form-group">
            <label class="col-sm-2" for="">요금제<span class="essential"></span>
            <!-- <div class="info-light"> <img src="${ctx_res}/images/Info-Light.png" class="img-responsive" data-toggle="modal" data-target="#myModal2"></div> -->
            </label>
            <div class="col-sm-2">
              <button type="button" id="chooProdCd" class="btn btn-primary btn-block" data-toggle="modal" data-target="#myModal">요금제 선택</button>
              <!--modal-->
              <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" style="width:500px;">
                  <div class="modal-content">
                    <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                      <h4 class="modal-title" id="myModalLabel">요금제 선택</h4>
                    </div>
                    <div class="modal-body">
                      <div class="tab-content">
                        <div id="menu1" class="">
                          <div class="form-group">
                            <div class="col-sm-10">
                              <input type="text" class="form-control" id="findProdCd" placeholder="요금제명을 입력해주세요." >
                            </div>
                            <!-- <div class="col-sm-2">
                              <button type="button" class="btn btn-primary btn-control" style="min-width:67.98px;">검색</button>
                            </div>  -->
                          </div>
                          <ul class="list-grpp" style = "height : 393px";>
                          </ul>
                        </div>
                      </div>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-page" id = "btnSave" data-dismiss="modal">확인</button>
                    </div>
                  </div>
                </div>
              </div>
              <!--/modal-->
              
              
              <!--modal-->
              <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="prodChkRslt" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-backdrop fade in" style="height: 832px;"></div>
                <div class="modal-dialog" style="width:500px;">
                  <div class="modal-content">
                    <div class="modal-body">
                      <div class="pop-alert">
                      <!--   <h5 align="center"><b>상품제약사항메세지</b></h5>  -->
                      	<p></p>
                        <div align="center"><img src="${ctx_res}/images/icon_notification.png" class="img-responsive"></div>
                        <br>
                     <!--      <p align="left" class="alert-txt" id="prodChkRsltTxt"></p>  -->
                        <div id="tab-content" class="tab-content" style="height:500px;overflow:auto">
                        <table border="1" id="menu2" class="">
                          <tr class="list-grpp" style = "height : 293px";>
                          </tr>
                        </table>
                        </div>
                        
                        <div align="center" class="alert-btn">
                          <button type="button" class="btn btn-page" id="btnProdSbscY">확인</button>
                          <button type="button" class="btn btn-page" id="btnProdConfirm">확인</button>                          
                          <button type="button" class="btn btn-warning mr10" id="btnProdSbscN">취소</button>
                          <button type="button" class="btn btn-warning mr10" id="btnProdSbsc0" style="display:none;">상품변경필요</button>
                        </div>
                        <p></p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <!--/modal-->              
            </div>
            <div class="col-sm-6">
              <input type="text" class="form-control" id="choosePay" placeholder="선택한 요금제">
            </div>
            <div class="col-sm-2">
              <input type="text" class="form-control txtR" id="choosePayCharge" placeholder="원">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2" for="">할부기간<span class="essential"></span></label>
            <div class="col-sm-5">
              <button type="button" id="btnMons1" class="btn btn-default  btn-block active">12개월</button>
            </div>
            <div class="col-sm-5">
              <button type="button" id="btnMons2" class="btn btn-default  btn-block ">24개월</button>
            </div>
         <!--    <div class="col-sm-10">
              <div class="btn-group btn-group-justified" role="group" aria-label="Justified button group"> 
              	<a href="javascript:chButton1('1');" id="btnMons1" class="btn btn-default active" role="button">12개월</a> 
              	<a href="javascript:chButton1('2');" id="btnMons2" class="btn btn-default" role="button">24개월</a> 
              </div>
              <div> </div>
            </div>  -->
          </div>
          <div class="form-group">
            <label class="col-sm-2" for="">약정기간<span class="essential"></span></label>
            <div class="col-sm-5">
              <button type="button" id="btnMons5" class="btn btn-default  btn-block">12개월</button>
            </div>
            <div class="col-sm-5">
              <button type="button" id="btnMons6" class="btn btn-default  btn-block active">24개월</button>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2" for="">지원금<span class="essential"></span>
            <div class="info-light"> <img src="${ctx_res}/images/Info-Light.png" class="img-responsive" data-toggle="modal" data-target="#myModal3"></div>
            <!--modal-->
            <div class="modal fade" id="myModal3" data-backdrop = "static" data-keyboard = "false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
              <div class="modal-dialog" style="width:350px;">
                <div class="modal-content">
                  <div class="modal-body">
                    <div class="pop-alert">
                      <h4>단말지원금(심플코스)</h4>
                      <p class="alert-txt" align = "left"> 약정기간 내 요금제를 변경 시 지원금 차액 청구방식을 선택하는 메뉴입니다. 심플코스는 6개월이 지나면 지원금 차액이 없습니다.</p>
                      <div class="alert-btn">
                        <button type="button" class="btn btn-page" data-dismiss="modal" aria-label="Close">확인</button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!--/modal-->
            </label>
            <div class="col-sm-5">
              <button type="button" id="btnMons3" class="btn btn-default  btn-block active">단말지원금(심플코스)</button>
            </div>
            <div class="col-sm-5">
              <button type="button" id="btnMons4" class="btn btn-default  btn-block ">요금할인(지원금)</button>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 mb10" for="">
            KT 멤버십
            <div class="info-light"> <img src="${ctx_res}/images/Info-Light.png" class="img-responsive" data-toggle="modal" data-target="#myModal2"></div>
            <!--modal-->
            <div class="modal fade" id="myModal2" data-backdrop = "static" data-keyboard = "false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
              <div class="modal-dialog" style="width:350px;">
                <div class="modal-content">
                  <div class="modal-body">
                    <div class="pop-alert">
                      <h4>KT멤버십 포인트</h4>
                      <p class="alert-txt" align = "left"> 보유하신 KT멤버십 포인트를 최종 단말구매가(단말할부원금)의 5%(최대5만원)까지 1,000점 단위로 사용 가능합니다. 최대 사용가능 금액 내에서 원하시는 금액을 입력하세요</p>
                      <div class="alert-btn">
                        <button type="button" class="btn btn-page" data-dismiss="modal" aria-label="Close">확인</button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!--/modal-->
            </label>
            <div class="col-sm-5"> <span class="form-group-addon" id="basic-addon1">사용 가능한 포인트</span>
              <input type="text" id="useAbleAmt" name="useAbleAmt" class="form-control txtR" placeholder="0" value="" readonly>
              <input type="hidden" id="icgPointAmt" name="icgPointAmt" value="">
              <input type="hidden" id="simplAcuAmt" name="simplAcuAmt" value="">
              <input type="hidden" id="tmpFirstDcSuprtTotAmt" name="tmpFirstDcSuprtTotAmt" value="">
              <input type="hidden" id="tmpHndsetAmntRate" name="tmpHndsetAmntRate" value="">
              <input type="hidden" id="intmSaleAmt" name="intmSaleAmt" value="">
            </div>
            <div class="col-sm-3 " style="display:none;"> <span class="form-group-addon" id="basic-addon1">보유</span>
              <input type="text" id="custMlgAmt" name="custMlgAmt" class="form-control txtR" placeholder="0" value="" readonly>
            </div>            
            <div class="col-sm-5"><span class="form-group-addon" id="basic-addon1">사용할 포인트</span>
              <input type="text" id="mlgUseVal" name="mlgUseVal" class="form-control txtR" placeholder="0" onkeyup="inputNumberFormat(this)" />
            </div>
          </div>
          <!--  div class="form-group">
            <label class="col-sm-2" for="">위약금<span class="essential"></span></label>
            <div class="col-sm-8">
              <input type="text" class="form-control txtR" id="" placeholder="원">
            </div>
            <div class="col-sm-2">
              <button type="button" id="btnEngtType" class="btn btn-primary btn-block">약정유예안내</button>
            </div>
          </div>   -->
          <div class="form-group">
            <div class="col-sm-2 floatR">
              <button type="button" id="btnConfirm" class="btn btn-page  btn-block ">계산</button>
            </div>
          </div>
        </form>
      </div>
      <!--/row-->
      <div id="mstrDiv" class="damage-area">
        <div class="media">
          <div class="media-left">
            <div class="damage-title">약정유예 - <br>
              위약금 안내</div>
          </div>
          <div class="media-body pt30"> 아래와 같이 위약금이 부과 또는 유예되며, 위약금 유예는 기존 약정 만료 전 해지 시 유예된 위약금과 재약정에 따른 위약금이 합산 청구됨을 안내해주시기 바랍니다.<br> 
            * 산정기준: 단말위약금 기변시점, 요금위약금 전월 혹은 전전월(월 초 조회 시) 기준 금액
            
            <div class="due-date">
            	<ul>
            		<li id="engtExpirPamDate">만료예정일<span class="ml10">2018.06.20</span></li>
            		<li id="engtRmndDayNum">잔여약정기간<span class="ml10">250일</span></li>
            	</ul> 
            </div>
            
            <div class="media">
              <div class="media-body">
                <div class="col4 calculate-damage mr10p">
                  <dl class="dl-horizontal calculate">
                    <dt id="dmstrIntmPenlt">지원금 위약금</dt>
                    <dd id="mstrIntmPenlt"><span class="primary-color bold">120,000</span>원</dd>
                    <dt id="dmstrIntmGracePenlt">지원금 위약금(유예)</dt>
                    <dd id="mstrIntmGracePenlt"><span class="primary-color bold">100,000</span>원</dd>
                    <dt id="dmstrChageDcPenlt">요금 위약금</dt>
                    <dd id="mstrChageDcPenlt"><span class="primary-color bold">33,000</span>원</dd>
                    <dt id="dmstrChageDcGracePenlt">요금 위약금(유예)</dt>
                    <dd id="mstrChageDcGracePenlt"><span class="primary-color bold">15,000</span>원</dd>
                    <dt id="dmstrBfdcAmt">단말기할인2/선할인</dt>
                    <dd id="mstrBfdcAmt"><span class="primary-color bold">100,000</span>원</dd>
                  </dl>
                </div>
                <div class="calculate col5">
                  <h4 class="mb10"> 위약금 부과 금액<span class="normal-info"> (유예 불가하여 부과되는 금액)</span></h4>
                  <hr>
                  <p id="mstrsum" class="payment-sum"><span class="primary-color pt36">358,000</span>원</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div id="graceDiv" class="damage-area">
        <div class="media">
          <div class="media-left">
            <div class="damage-title">약정유예 - <br>
             위약금 안내</div>
          </div>
          <div class="media-body pt30"> 아래와 같이 위약금이 유예되나, 기존 약정 만료 전 해지 시 유예된 위약금과 재약정에 따른 위약금이 합산 청구됨을 안내해주시기 바랍니다. <br>
            * 산정기준: 단말위약금 기변시점, 요금위약금 전월 혹은 전전월(월 초 조회 시) 기준 금액
            
            <div class="due-date">
            	<ul>
            		<li id="engtExpirPamDate2">만료예정일<span class="ml10">2018.06.20</span></li>
            		<li id="engtRmndDayNum2">잔여약정기간<span class="ml10">250일</span></li>
            	</ul> 
            </div>
            
            <div class="media">
              <div class="media-body">
                <div class="col4 calculate-damage mr10p">
                  <dl class="dl-horizontal calculate">
                    <dt id="dgraceIntmPenlt">지원금 위약금</dt>
                    <dd id="graceIntmPenlt"><span class="primary-color bold">120,000</span>원</dd>
                    <dt id="dgraceChageDcPenlt">요금 위약금</dt>
                    <dd id="graceChageDcPenlt"><span class="primary-color bold">33,000</span>원</dd>
                  </dl>
                </div>
                <div class="calculate col5">
                  <h4 class="mb10"> 위약금 유예 금액<span class="normal-info"> (유예 후 잔여 약정기간 내 해지시 부과)</span></h4>
                  <hr>
                  <p id="gracesum" class="payment-sum"><span class="primary-color pt36">358,000</span>원</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <div id="calculateDiv" class="calculate-area">
		<div class="calculate-box">
        	<div class="calculate col3 mrp5">
          		<h4 class="mb20"><span class="icon">A</span> 통신요금
	          		<p id="sprodChage" class="sum">월 <span class="primary-color pt20">0</span>원</p>
          		</h4>
          		<hr>
          		<dl class="dl-horizontal calculate" style="min-height:102px;">
            		<dt id="dprodChage">월 정액요금</dt>
		            <dd id="lprodChage">0원</dd>
		            <dt id="dcSuprtAmtChageTx"> └ 월 요금할인 </dt>
		            <dd id="dcSuprtAmtChage"><span class="primary-color">-0</span>원</dd>
		            <dt id="dcSuprtAmtChageTx2" class="ml20">   </dt>
		            <dd id=""></dd>
		            <dt id="dcSuprtAmtChageDcTx"> └ 25% 요금할인 </dt>
		            <dd id="dcSuprtAmtChageDc"><span class="primary-color">-0</span>원</dd>
		            <dt id="dcSuprtAmtChageDcTx2" class="ml20">   </dt>
		            <dd id=""></dd>
          		</dl>
          	</div>
        	<div class="col3 calculate mrp5">
          		<h4 class="mb20"><span class="icon">B</span> 단말구매가
          			<p class="sum" id="monthInslAmt">월 <span class="primary-color pt20">0</span>원</p>
          		</h4>
          		<hr>
          		<dl class="dl-horizontal calculate">
		            <dt id="dintmSaleAmt">출고가</dt>
		            <dd id="lintmSaleAmt">0원</dd>
		            <dt id="dfirstDcSuprtTotAmt">└ 지원금</dt>
		            <dd id="firstDcSuprtTotAmt"><span class="primary-color">-0</span>원</dd>
		            <dt id="dicgPointAmt">└ 기변포인트</dt>
		            <dd id="licgPointAmt"><span class="primary-color">-0</span>원</dd>
		            <dt id="dsimplAcuAmt">└ 심플적립</dt>
		            <dd id="lsimplAcuAmt"><span class="primary-color">-0</span>원</dd>
		            <dt id="dmlgUseVal">└ KT멤버십포인트</dt>
		            <dd id="lmlgUseVal"><span class="primary-color">-0</span>원</dd>
		            <dt id="dtotInslAmt">총할부원금</dt>
		            <dd id="totInslAmt">0원</dd>
		            <dt id="dinslIntrTotAmt">총할부수수료</dt>
		            <dd id="inslIntrTotAmt">0원</dd>
		         <!-- <dt id="dinslIntrRate">(24개월 할부 연 0%)</dt>
		            <dd id="inslIntrRate"></dd> -->
		            <dt id="dusimAmt"><span class="normal-info"><span class="primary-color mark">*</span>유심비 <span class="primary-color"> 별도</span></span></dt>
		            <dd id="usimAmt"></dd>
          		</dl>
        	</div>
        	
        	<div class="calculate col3">
        		<h4 class="mb20"><span class="icon">A</span>+<span class="icon">B</span> 월 납부액<span class="normal-info"> (부가세포함)</span></h4>
        		<hr>
          		<p class="payment-sum" id="payment_sum">월<span class="primary-color pt36">0</span>원</p>
        	</div>
      		</div>
      </div>
      
    </div>
    <!--/.col-sm-9-->
  </div>
  <!--/row-->
</div>
<!-- /.container -->

<!--modal-->
<div class="modal fade" id="progressbar" data-backdrop = "static" data-keyboard = "false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-backdrop fade in" style="height: 832px;">
    <div class="modal-dialog" style="top:300px;left:350px;">  
      <div class="loader"></div> 
    </div>
  </div>
</div>
<!--/modal-->
	
<footer class="footer">
  <div class="left">
  	<span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="javascript:goPrev();"></span> 
  	<span><img src="${ctx_res}/images/left_home.png" class="img-responsive"  id="goHome"></span>
  </div>
  <div class="right main">
    <div class="col-sm-2 floatR">
    	<button type="button" id="btnNext" class="btn btn-page  btn-block ">다음</button>
    </div>
  </div>
</footer>
