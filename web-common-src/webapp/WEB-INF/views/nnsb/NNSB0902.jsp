<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nnsb/NNSB0902.js"></script>

<!--content-->
<div class="container-fluid">
  <div class="row">
    <!--.main-->
    <div class="main">
      <form role="form" class="form-horizontal" name = "formSch" method = "post">
	  <input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
   	  </form>
      <h3>처리완료</h3>
      <div class="join-area"><img class="img-join" src="${ctx_res}/images/icon-join.png" alt="" style="width: 180px; height: 180px;">
        <h3 class="mt30" id="MnpTitle">개통이 완료되었습니다. <button type="button" class="btn btn-page" id = "btnStandardDoc">표준안내서</button>를 확인해주세요.</h3>
        <div class="join-info" id = "aminor"> 취소를 원할실 경우 반드시 신규가입시와 동일한 법정대리인과 방문하셔야 합니다. </div>
      </div>
      <div class="media">
        <div class="media-left"> <a href="#" class="thumbnail qr-code"><img id="qrCodeImg" class="img-responsive" style="width:210px; height:210px;"></a></div>
        <div class="media-body">
          <div class="form-horizontal tbline">
            <h3 class="primary-color bold">표준안내서 및 신청서</h3>
            <!--
            <div class="form-group mb10">
              <div class="col-sm-12">
                <button type="button" class="btn btn-default  btn-block" id = "btnStandardDoc">표준안내서</button>
              </div>
            </div>
            -->
            <div class="form-group mb10">
              <div class="col-sm-6">
                <button type="button" class="btn btn-default  btn-block " id="btnPrint" disabled>인쇄</button>
			                 <!--modal
			        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
			        <div class="modal-backdrop fade in" style="height: 768px;"></div>
			          <div class="modal-dialog" style="width:680px;">
			            <div class="modal-content">
			              <div class="modal-header">
			                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
			                <h4 class="modal-title" id="myModalLabel">계약정보</h4>
			              </div>
			              <div class="modal-body">
			                <form role="form" class="form-horizontal">
			                  <div class="form-group">
			                    <label class="col-sm-2" for="">이메일 주소</label>
			                    <div class="col-sm-10">
			                      <input type="text" class="form-control" id="emailAdr" placeholder="xxxp@hanmail.net" readonly="true">
			                    </div>
			                  </div>
			                  <div class="form-group">
			                    <label class="col-sm-2" for="">청구지 주소</label>
			                    <div class="col-sm-10">
			                      <input type="text" class="form-control" id="arnoAdr" placeholder="경기도 성남시 분당구 성남대로 222-22222" readonly="true">
			                    </div>
			                  </div>
			                  <div class="form-group">
			                    <label class="col-sm-2" for="">납부자 정보</label>
			                    <div class="col-sm-10">
			                      <input type="text" class="form-control" id="billCustInfo" placeholder="홍길동 고객님 / 1976.02.03 / 국민은행 456523584869 / 매월 21일" readonly="true">
			                    </div>
			                  </div>
			                  <div class="form-group">
			                    <label class="col-sm-2" for="">단말 모델명</label>
			                    <div class="col-sm-10">
			                      <input type="text" class="form-control" id="intmModelNm" placeholder="A1723-64" readonly="true">
			                    </div>
			                  </div>
			                  <div class="form-group">
			                    <label class="col-sm-2" for="">개통번호</label>
			                    <div class="col-sm-4">
			                      <input type="text" class="form-control" id="svcNo" placeholder="010-0000-0000" readonly="true">
			                    </div>
			                    <label class="col-sm-2 pl12" for="">할부기간</label>
			                    <div class="col-sm-4">
			                      <input type="text" class="form-control" id="totInslMonsNum" placeholder="12개월" readonly="true">
			                    </div>
			                  </div>
			                  <div class="form-group">
			                    <label class="col-sm-2" for="prodInfo">요금제</label>
			                    <div class="col-sm-4">
			                      <input type="text" class="form-control" id="prodNm" placeholder="LTE" readonly="true">
			                    </div>
			                    <label class="col-sm-2 pl12" for="">약정기간</label>
			                    <div class="col-sm-4">
			                      <input type="text" class="form-control" id="engtPerdMonsNum" placeholder="24개월" readonly="true">
			                    </div>
			                  </div>
			                  <div class="form-group">
			                    <label class="col-sm-2" for="">지원금</label>
			                    <div class="col-sm-4">
			                      <input type="text" class="form-control" id="saleEngtOptnCdNm" placeholder="단말할인(심플코스)" readonly="true">
			                    </div>
			                    <label class="col-sm-2 pl12" for="">KT멤버십</label>
			                    <div class="col-sm-4">
			                      <input type="text" class="form-control" id="intmDcAmt" placeholder="5,000원 사용" readonly="true">
			                    </div>
			                  </div>
			                  <div class="form-group pop-box">
			                    <div class="col-sm-3">
			                      <div class="pop-calculate">
			                        <h6>통신요금(월정액)</h6>
			                        <p class="pop-sum" id = "famtTarifAmt">월 87,890원</p>
			                      </div>
			                    </div>
			                    <div class="col-sm-1">
			                      <div class=" pop-plus">+</div>
			                    </div>
			                    <div class="col-sm-3">
			                      <div class="pop-calculate">
			                        <h6>단말구매가</h6>
			                        <p class="pop-sum" id = "mbyPayAmt">월 45,600원</p>
			                      </div>
			                    </div>
			                    <div class="col-sm-1">
			                      <div class=" pop-plus">=</div>
			                    </div>
			                    <div class="col-sm-4">
			                      <div class="pop-calculate">
			                        <h6>월 납부금액(부가세포함)</h6>
			                        <p class="pop-sum" id = "mmBlpymAmnt">94,990원</p>
			                      </div>
			                    </div>
			                  </div>
			                </form>
			              </div>
			              <div class="modal-footer">
			                <button type="button" class="btn btn-page" data-dismiss = "modal">확인</button>
			              </div>
			            </div>

			          </div>

			        </div>
			        <!--/modal-->
              </div>
              <div class="col-sm-6">
                <button type="button" class="btn btn-default  btn-block " id="btnEmail" disabled>이메일 발송</button>
              </div>
            </div>
            <div class="form-group">
              <div class="col-sm-12">
                <input type="text" class="form-control" id="emailSet" placeholder="qwert@aaaaa.com">
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--/.main-->
  </div>
  <!--/row-->
</div>
<!-- /.container -->

<!--modal-->
<div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="progressbar" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="top:300px;left:350px;">  
      <div class="loader"></div> 
    </div>
  </div>
</div>
<!--/modal-->
<footer class="footer">
	<div class="left">
	<!-- 
		<span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="goPrev()"></span> 
	-->
		<span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id="goHome2"></span>
	</div>
	<div class="right main">
	    <div class="col-sm-2 floatR">
	      <button type="button" class="btn btn-page  btn-block" id="goHome4">종료</button>
	    </div>
	</div>
</footer>