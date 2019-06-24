<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nnsb/NNSB0500.js"></script>

<div class="container-fluid">
  <div class="row">
    <!--.main-->
    <div class="main">
      <h3>가입정보</h3>
      <div>
        <form role="form" class="form-horizontal" name="formSch" method = "post">
        <input type = "hidden" name = "inputVo" id = "inputVo" value = '${inputVo}' >
          <div class="form-group">
            <label class="col-sm-2" for="">희망번호<span class="essential"></span></label>
            <div class="col-sm-2 ">
              <button type="button" class="btn btn-primary btn-block" data-toggle="modal" data-target="#myModal1" id = "hopeNumber">희망번호 선택</button>
              <!--modal-->
              <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" style="width:500px;">
                  <div class="modal-content">
                    <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                      <h4 class="modal-title" id="myModalLabel">희망번호 선택</h4>
                    </div>
                    <div class="modal-body" style="height:456px;">
                      <div class="phone-choice-info">희망번호 뒷자리를 입력하시면 사용 가능한 번호를 확인할 수 있습니다.<br>
                        (0000, 1234, 0070 등의 특정 번호에 대한 선택은 할 수 없습니다.)</div>
                      <div class="phone-choice-number form-inline"> 010 - XXXX -
                        <input type="text" class="form-control" id="endNum" placeholder="뒷자리 번호입력" style="width:160px; margin-top:-4px;" pattern = "\d*" maxlength = "4">
                      </div>
                      <ul class="list-grp mt40" id = "resultNumber">
                      </ul>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-page" id = "btnConfirmEndNum">확인</button>
                    </div>
                  </div>
                </div>
              </div>
              <!--/modal-->
            </div>
            <div class="col-sm-8 ">
              <input type="text" class="form-control" id="nwnumSvcNo" placeholder="010-0000-0000" readonly=true>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2" for="">요금제<span class="essential"></span>
            <!-- 
            <div class="info-light"> <img src="${ctx_res}/images/Info-Light.png" class="img-responsive" data-toggle="modal" data-target="#myModal2"></div>
             -->
            </label>
            <div class="col-sm-2">
              <button type="button" class="btn btn-primary btn-block" data-toggle="modal" data-target="#myModal" id = "chooProdCd">요금제 선택</button>
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
                            <div class="col-sm-12">
                              <input type="text" class="form-control" id="findProdCd" placeholder="요금제명을 입력해주세요." >
                            </div>
                            <!-- <div class="col-sm-2">
                              <button type="button" class="btn btn-primary btn-control" style="min-width:67.98px;">검색</button>
                            </div> -->
                          </div>
                          <ul class="list-grpp" style = "height : 365px";>
                          </ul>
                        </div>
                      </div>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-page" id = "btnConfirm" >확인</button>
                    </div>
                  </div>
                </div>
              </div>
              <!--/modal-->
            </div>
            <div class="col-sm-6">
              <input type="text" class="form-control" id="prodNm" placeholder="선택한 요금제"  readonly=true>
              <input type = "hidden" id = "prodId">
            </div>
            <div class="col-sm-2">
              <input type="text" class="form-control txtR" id="prodIndcChage" placeholder="원"  readonly=true>
            </div>
          </div>
          <div class="form-group" id = "inslInpymMons">
            <label class="col-sm-2" for="">할부기간<span class="essential"></span></label>
            <div class="col-sm-5">
              <button type="button" class="btn btn-default  btn-block">12개월</button>
            </div>
            <div class="col-sm-5">
              <button type="button" class="btn btn-default  btn-block active">24개월</button>
            </div>
          </div>
          <div class="form-group" id = "engtPerdMonsNum">
            <label class="col-sm-2" for="">약정기간<span class="essential"></span></label>
            <div class="col-sm-5">
              <button type="button" class="btn btn-default  btn-block " id="engtPerdButton12">12개월</button>
            </div>
            <div class="col-sm-5">
              <button type="button" class="btn btn-default  btn-block active" id="engtPerdButton24">24개월</button>
            </div>
          </div>
          <div class="form-group" id = "saleEngtTypeNm">
            <label class="col-sm-2" for="">지원금
            	<span class="essential"></span>
            	<div class="info-light"> <img src="${ctx_res}/images/Info-Light.png" class="img-responsive" data-toggle="modal" data-target="#myModal2"></div>
            </label>
            <div class="col-sm-5">
              <button type="button" class="btn btn-default  btn-block">단말지원금(심플코스)</button>
            </div>
            <div class="col-sm-5">
              <button type="button" class="btn btn-default  btn-block active">요금할인(지원금)</button>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 mb10" for="">
            KT 멤버십
            <div class="info-light"> <img src="${ctx_res}/images/Info-Light.png" class="img-responsive" data-toggle="modal" data-target="#myModal3"></div>
            <!--modal-->
            <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
              <div class="modal-dialog" style="width:350px;">
                <div class="modal-content">
                  <div class="modal-body">
                    <div class="pop-alert">
                      <h4>KT멤버십 포인트</h4>
                      <p class="alert-txt" id="mlgUseAlert" align = "left"> 보유하신 KT멤버십 포인트를 1,000점 단위로 사용 가능합니다. 최대 사용가능 금액 내에서 원하시는 금액을 입력하세요</p>
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
              <input type="text" class="form-control txtR" placeholder="0" id="mlgUseLmtAmt" readonly>
            </div>
            <div class="col-sm-5"><span class="form-group-addon" id="basic-addon1">사용할 포인트</span>
              <input type="text" class="form-control txtR" placeholder="0" id = "mlgUseVal" onkeyup="inputNumberFormat(this)">
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 floatR">
              <button type="button" class="btn btn-page  btn-block " id = "calcProd">계산</button>
            </div>
          </div>
        </form>
      </div>
      <!--/row-->
      <div class="calculate-area">
        <div class="calculate-box">
          <div class="calculate col3 mrp5">
            <h4 class="mb20"><span class="icon">A</span> 통신요금
              <p class="sum">월 <span class="primary-color pt20" id="calMbyProdIndcChage">0</span>원</p>
            </h4>
            <hr>
            <dl class="dl-horizontal calculate" style="min-height:102px;" id="calProdArea">
              <dt id="calProdIndcChageTitle">월 정액요금</dt>
              <dd id="calProdIndcChage"></dd>
              <dt id="calPm"> └ 25% 요금할인 </dt>
              <dd id="calPmAmtWon"><span class="primary-color" id="calPmAmt">0</span>원</dd>
              <dt class="ml20" id="calEngtPerdMonsNum"></dt>
            </dl>
          </div>
          <div class="col3 calculate mrp5">
            <h4 class="mb20"><span class="icon">B</span> 단말구매가
              <p class="sum">월 <span class="primary-color pt20"  id="calMbyPayAmt">0</span>원</p>
            </h4>
            <hr>
            <dl class="dl-horizontal calculate" style="" id="calIntmArea">
              <dt id="calIntmSaleAmtTitle">출고가</dt>
              <dd id="calIntmSaleAmt">0원</dd>
              <dt id="calSuprt">└ 단말지원금 할인</dt>
              <dd id="calSuprtAmtWon"><span class="primary-color" id="calFirstDcSuprtTotAmt">-0</span>원</dd>
              <dt id="calMlgUse">└ KT멤버십포인트</dt>
              <dd id="calMlgUseValWon"><span class="primary-color" id="calMlgUseVal">-0</span>원</dd>
              <dt id="calInstaAmtTitle">총 할부원금</dt>
              <dd id="calInstaAmt">0원</dd>
              <dt id="calIntmInslTotAmtTitle">총 할부수수료</dt>
              <dd id="calIntmInslTotAmt">0원</dd>
			  <dt id="calInslIntrRate"></dt> 
			  <dt><span class="normal-info"><span class="primary-color mark">*</span>유심비 <span class="primary-color" id="calUsimSaleAmt">0원 별도</span></span></dt>
            </dl>
          </div>
          <div class="calculate col3">
            <h4 class="mb20"><span class="icon">A</span>+<span class="icon">B</span> 월 납부액<span class="normal-info"> (부가세포함)</span></h4>
            <hr>
            <a id="calTotArea">
            <p class="payment-sum">월<span class="primary-color pt36" id="calMbyTotPayAmt">0</span>원</p>
            </a>
          </div>
        </div>
      </div>
    </div>
    <!--/.main-->
  </div>
  <!--/row-->
</div>
<!--modal-->
<div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog" style="width:350px;">
    <div class="modal-content">
      <div class="modal-body">
        <div class="pop-alert">
          <h4>단말지원금(심플코스)</h4>
          <p class="alert-txt" id="saleEngtTypeNmMsg" align = "left"> 약정기간 내 요금제를 변경 시 지원금 차액 청구방식을 선택하는 메뉴입니다.
          심플코스는 6개월이 지나면 지원금 차액이 없습니다.</p>
          <div class="alert-btn">
            <button type="button" class="btn btn-page" data-dismiss="modal" aria-label="Close">확인</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<!--/modal-->
<!-- /.container -->
<footer class="footer">
  <div class="left"><span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="goPrev()"></span> <span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id="goHome"></span></div>
  <div class="right main">
    <div class="col-sm-2 floatR">
      <button type="button" class="btn btn-page  btn-block " id = "newSbscProcess" disabled="true">다음</button>
    </div>
  </div>
</footer>