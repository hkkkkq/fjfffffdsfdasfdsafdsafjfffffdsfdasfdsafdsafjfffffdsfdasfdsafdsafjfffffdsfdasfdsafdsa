<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nnsb/NNSB0200.js"></script>
<!--content-->
<div class="container-fluid">
  <div class="row">
    <div class="main">
      <h3 id = "baseInfoAminorN">기본정보
        <div class="floatR">
          <button type="button" class="btn btn-control" id = "reidScan">스캔다시하기</button>
        </div>
      </h3>
      <h3 id = "baseInfoAminorY" style = "display : none;">기본정보(미성년자)
        <div class="floatR">
          <button type="button" class="btn btn-control" id = "reidScan2">스캔다시하기</button>
        </div>
      </h3>
      <div class="tab-area" id = "nanTab" style = "display : none;">
        <ul class="nav nav-tabs nav-justified">
          <li id = "menu1Tab" class="active"><a data-toggle="tab" href="#menu1" aria-expanded="false">내국인</a></li>
          <li id = "menu2Tab" class=""><a data-toggle="tab" href="#menu2" aria-expanded="true">외국인</a></li>
        </ul>
      </div>
      <div class="tab-content">
        <div id="menu1" class="tab-pane fade active in">
          <form role="form" class="form-horizontal" name = "formSch" method = "post">
	   	  <input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
	   	  <input type = "hidden" id = "idType" name = "idType" value = "" >
		  <input type = "hidden" id = "idLicenseNo" name = "idLicenseNo" value = "" >
		  <input type = "hidden" id = "idDate" name = "idDate" value = "" >
		  <input type = "hidden" id = "idCustName" name = "idCustName" value = "" >
		  <input type = "hidden" id = "idNo" name = "idNo" value = "" >
		  <input type = "hidden" id = "idIssueOrg" name = "idIssueOrg" value = "" >
		  <input type = "hidden" id = "idAddress" name = "idAddress" value = "" >
	      <input type = "hidden" id = "idLegalType" name = "idLegalType" value = "" >
		  <input type = "hidden" id = "idLegalLicenseNo" name = "idLegalLicenseNo" value = "" >
		  <input type = "hidden" id = "idLegalDate" name = "idLegalDate" value = "" >
		  <input type = "hidden" id = "idLegalCustName" name = "idLegalCustName" value = "" >
		  <input type = "hidden" id = "idLegalNo" name = "idLegalNo" value = "" >
		  <input type = "hidden" id = "idLegalIssueOrg" name = "idLegalIssueOrg" value = "" >
		  <input type = "hidden" id = "idLegalAddress" name = "idLegalAddress" value = "" >
            <div class="form-group">
              <label class="col-sm-2" for="">고객명<span class="essential"></span></label>
              <div class="col-sm-10" >
                <input type="text" class="form-control" id="custNm" placeholder="홍길동">
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2" for="" id = custIdfyNoTab>주민번호<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="custIdfyNo" placeholder="000000-0000000" onkeydown = "onlyNumberCustIdfyNo(this)" pattern = "\d*">
              </div>
            </div>
          </form>
          <h3 class="mt50" id = "legalInfo" style = "display : none;">기본정보(<span class="primary-color">법정대리인</span>) </h3>
          <form role="form" class="form-horizontal" style = "display : none;">
            <div class="form-group">
              <label class="col-sm-2" for="">고객명<span class="essential"></span></label>
              <div class="col-sm-4">
                <input type="text" class="form-control" id="legalCustNm" placeholder="홍길동">
              </div>
              <label class="col-sm-2 pl12" for="">법정대리인</label>
              <div class="col-sm-1 col-11">
                <button type="button" id="legalRelNmFather" class="btn btn-default  btn-block active">부</button>
              </div>
              <div class="col-sm-1 col-11">
                <button type="button" id="legalRelNmMother" class="btn btn-default  btn-block ">모</button>
              </div>
              <div class="col-sm-2 col-11">
                <button type="button" id="legalRelNmGuardian" class="btn btn-default  btn-block">후견인</button>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2" for="" id  = "custIdfyNoTab2">주민번호<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="legalCustIdfyNo" placeholder="000000-0000000" onkeydown = "onlyNumberCustIdfyNo(this)" pattern = "\d*">
              </div>
            </div>
          </form>
          <h3 class="mt50" id = "tab1" style = "display : none;">본인인증(<span class="primary-color" id = "idCadrTab">주민등록증</span>)</h3>
      <form role="form" class="form-horizontal">
        <div class="form-group">
          <label class="col-sm-2" for="" id = "driveLicnsNoTab">면허번호<span class="essential" id = "essential1"></span></label>
          <div class="col-sm-10" id = "col_sm_Tab">
            <input type="text" class="form-control" id="driveLicnsNo" placeholder="경기0123456789">
          </div>
         </div>
         <div class="form-group" style = "display : none;">
          <label class="col-sm-2" for="" id = "nationalityTab">국적<span class="essential"></span></label>
          <div class="col-sm-8">
            <input type="text" class="form-control" id="nationality" placeholder="CAN 캐나다" readonly = "true">
          </div>
       	<!-- 성인이 외국인일경우 -->
		<!-- 국가선택 팝업창 추가 -->
          <div class="col-sm-2" id = "contModalTab" style="display: none;">
              <button type="button" class="btn btn-primary btn-block" data-toggle="modal" data-target="#myModal" onclick = "contModal()">국적 찾기</button>
              <!--modal-->
              <div class="modal fade in" data-backdrop = "static" data-keyboard = "false" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog" style="width:500px;">
                  <div class="modal-content">
                    <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                      <h4 class="modal-title" id="myModalLabel">국적 찾기</h4>
                    </div>
                    <div class="modal-body">
                      <div class="tab-area-popup mb0">
                        <ul class="nav nav-tabs-popup nation">
                          <li class="active"><a data-toggle="tab" href="#popup-menu1" aria-expanded="true">ㄱ ~ ㅂ</a></li>
                          <li class=""><a data-toggle="tab" href="#popup-menu2" aria-expanded="false">ㅅ ~ ㅇ</a></li>
                          <li class=""><a data-toggle="tab" href="#popup-menu3" aria-expanded="false">ㅈ ~ ㅎ</a></li>
                        </ul>
                      </div>
                      <div class="tab-content">
                        <div id="popup-menu1" class="tab-pane fade active in">
                          <ul class="list-grpp" style="height:420px;">
                            <li>
                              
                            </li>
                          </ul>
                        </div>
                        <div id="popup-menu2" class="tab-pane fade">
                        <ul class="list-grpp" style="height:420px;">
                            <li>
                              <div class="list-grpp-item"></div>
                            </li>
                          </ul>
                        </div>
                        <div id="popup-menu3" class="tab-pane fade">
                        <ul class="list-grpp" style="height:420px;">
                            <li>
                              <div class="list-grpp-item"></div>
                            </li>
                          </ul>
                        </div>
                      </div>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-page" id = "cntryModalConfirm" data-dismiss="modal">확인</button>
                    </div>
                  </div>
                </div>
              </div>
              <!--/modal-->
            </div>
            <!-- /국가선택 팝업창 추가 -->
           <!-- /성인이 외국인일경우 --> 
          </div>
          <div class="form-group">
          <label class="col-sm-2" for="" id = "issDateValTab">발급일자<span class="essential" id = "essential2"></span></label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="issDateVal" placeholder="2008.02.02" onkeydown = "onlyNumberIssDateVal(this)" pattern = "\d*">
          </div>
        </div>
        <!--
        <div class="form-group">
          <label class="col-sm-2" for="">연락처<span class="essential" id = "essential3"></span></label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="telNo" placeholder="010-0000-0000" onkeydown = "onlyNumber(this)" pattern = "\d*">
          </div>
        </div>
        -->
          </form>
        </div>
        <div id="menu2" class="tab-pane fade">
          <form role="form" class="form-horizontal">
            <div class="form-group">
              <label class="col-sm-2" for="">고객명<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="custNm2" placeholder="홍길동">
              </div>
            </div>
              <div class="form-group">
              <label class="col-sm-2" for="" id = custIdfyNoTab2>등록번호<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="custIdfyNo2" placeholder="000000-0000000" onkeydown = "onlyNumberCustIdfyNo(this)" pattern = "\d*">
              </div>
            </div>
            <!-- 미성년 외국인일경우 -->
        <div class="form-group">
          <label class="col-sm-2" for="" id = nationalityTab2>국적<span class="essential"></span></label>
          <div class="col-sm-8">
            <input type="text" class="form-control" id="nationality2" placeholder="CAN 캐나다" readonly = "true">
          </div>
          <!-- 국가선택 팝업창 추가 -->
          <div class="col-sm-2" id = "contModalTab2" >
              <button type="button" class="btn btn-primary btn-block" data-toggle="modal" data-target="#myModal2" onclick = "contModal2()">국적 찾기</button>
              <!--modal-->
              <div class="modal fade in" data-backdrop = "static" data-keyboard = "false" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog" style="width:500px;">
                  <div class="modal-content">
                    <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                      <h4 class="modal-title" id="myModalLabel">국적 찾기</h4>
                    </div>
                    <div class="modal-body">
                      <div class="tab-area-popup mb0">
                        <ul class="nav nav-tabs-popup nation">
                          <li class="active"><a data-toggle="tab" href="#popup-menu4" aria-expanded="true">ㄱ ~ ㅂ</a></li>
                          <li class=""><a data-toggle="tab" href="#popup-menu5" aria-expanded="false">ㅅ ~ ㅇ</a></li>
                          <li class=""><a data-toggle="tab" href="#popup-menu6" aria-expanded="false">ㅈ ~ ㅎ</a></li>
                        </ul>
                      </div>
                      <div class="tab-content">
                        <div id="popup-menu4" class="tab-pane fade active in">
                          <ul class="list-grpp" style="height:420px;">
                            <li>
                              
                            </li>
                          </ul>
                        </div>
                        <div id="popup-menu5" class="tab-pane fade">
                        <ul class="list-grpp" style="height:420px;">
                            <li>
                              <div class="list-grpp-item"></div>
                            </li>
                          </ul>
                        </div>
                        <div id="popup-menu6" class="tab-pane fade">
                        <ul class="list-grpp" style="height:420px;">
                            <li>
                              <div class="list-grpp-item"></div>
                            </li>
                          </ul>
                        </div>
                      </div>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-page" id = "cntryModalConfirm2" data-dismiss="modal">확인</button>
                    </div>
                  </div>
                </div>
              </div>
              <!--/modal-->
            </div>
            <!-- /국가선택 팝업창 추가 -->
        </div>
        <!-- /미성년 외국인일경우 -->
        <div class="form-group">
              <label class="col-sm-2" for="" id = "issDateValTab2">발급일자<span class="essential" id = "essential7"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="issDateVal2" placeholder="2008.02.02" onkeydown = "onlyNumberIssDateVal(this)" pattern = "\d*">
              </div>
            </div>
          </form>
          <h3 class="mt50" id = "legalInfo">기본정보(<span class="primary-color">법정대리인</span>) </h3>
          <form role="form" class="form-horizontal">
            <div class="form-group">
              <label class="col-sm-2" for="">고객명<span class="essential"></span></label>
              <div class="col-sm-4">
                <input type="text" class="form-control" id="legalCustNm2" placeholder="홍길동">
              </div>
              <label class="col-sm-2 pl12" for="">법정대리인</label>
              <div class="col-sm-1 col-11">
                <button type="button" id="legalRelNmFather2" class="btn btn-default  btn-block active">부</button>
              </div>
              <div class="col-sm-1 col-11">
                <button type="button" id="legalRelNmMother2" class="btn btn-default  btn-block ">모</button>
              </div>
              <div class="col-sm-2 col-11">
                <button type="button" id="legalRelNmGuardian2" class="btn btn-default  btn-block">후견인</button>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2" for="" id = "custIdfyNoTab3">주민번호<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="legalCustIdfyNo2" placeholder="000000-0000000" onkeydown = "onlyNumberCustIdfyNo(this)" pattern = "\d*">
              </div>
            </div>
          </form>
          <h3 class="mt50" style = "display : none;">본인인증(<span class="primary-color" id = "idCadrTab2">주민등록증</span>)
           <!-- 
            <div class="warning-info"> 증빙자료는 외국인등록증만 가능합니다. </div>
            --> 
          </h3>
          <form role="form" class="form-horizontal">
            <div class="form-group"  id="nationalityTab3" style="display: none;">
              <label class="col-sm-2" for="">국적<span class="essential"></span></label>
              <div class="col-sm-8">
                <input type="text" class="form-control" id="nationality3" placeholder="CAN 캐나다" readonly = "true">
              </div>
        <!-- 미성년 법정대리인이 외국인일경우 --> 
		<!-- 국가선택 팝업창 추가 -->
          <div class="col-sm-2" id = "contModalTab3" style="display: none;">
              <button type="button" class="btn btn-primary btn-block" data-toggle="modal" data-target="#myModal3" onclick = "contModal3()">국적 찾기</button>
              <!--modal-->
              <div class="modal fade in" data-backdrop = "static" data-keyboard = "false" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog" style="width:500px;">
                  <div class="modal-content">
                    <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                      <h4 class="modal-title" id="myModalLabel">국적 찾기</h4>
                    </div>
                    <div class="modal-body">
                      <div class="tab-area-popup mb0">
                        <ul class="nav nav-tabs-popup nation">
                          <li class="active"><a data-toggle="tab" href="#popup-menu7" aria-expanded="true">ㄱ ~ ㅂ</a></li>
                          <li class=""><a data-toggle="tab" href="#popup-menu8" aria-expanded="false">ㅅ ~ ㅇ</a></li>
                          <li class=""><a data-toggle="tab" href="#popup-menu9" aria-expanded="false">ㅈ ~ ㅎ</a></li>
                        </ul>
                      </div>
                      <div class="tab-content">
                        <div id="popup-menu7" class="tab-pane fade active in">
                          <ul class="list-grpp" style="height:420px;">
                            <li>
                              
                            </li>
                          </ul>
                        </div>
                        <div id="popup-menu8" class="tab-pane fade">
                        <ul class="list-grpp" style="height:420px;">
                            <li>
                              <div class="list-grpp-item"></div>
                            </li>
                          </ul>
                        </div>
                        <div id="popup-menu9" class="tab-pane fade">
                        <ul class="list-grpp" style="height:420px;">
                            <li>
                              <div class="list-grpp-item"></div>
                            </li>
                          </ul>
                        </div>
                      </div>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-page" id = "cntryModalConfirm3" data-dismiss="modal">확인</button>
                    </div>
                  </div>
                </div>
              </div>
              <!--/modal-->
            </div>
            <!-- /국가선택 팝업창 추가 -->
           <!-- /미성년 법정대리인이 외국인일경우 --> 
            </div>
            <div class="form-group">
            <label class="col-sm-2" for="" id = "driveLicnsNoTab2">면허번호<span class="essential" id = "essential1"></span></label>
          <div class="col-sm-10" >
            <input type="text" class="form-control" id="driveLicnsNo2" placeholder="경기0123456789">
            </div>
          </div>
            <div class="form-group">
              <label class="col-sm-2" for="" id = "issDateValTab3">발급일자<span class="essential" id = "essential4"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="issDateVal3" placeholder="2008.02.02" onkeydown = "onlyNumberIssDateVal(this)" pattern = "\d*">
              </div>
            </div>
            <!-- 
            <div class="form-group">
              <label class="col-sm-2" for="">연락처<span class="essential" id = "essential5"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="telNo2" onkeydown = "onlyNumber(this)" pattern = "\d*" placeholder="010-0000-0000">
              </div>
            </div>
            -->
          </form>
        </div>
      </div>
    </div>
    <!--/.main-->
  </div>
  <!--/row-->
</div>
<!--modal-->
	<div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="modalErrMsgPopup" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		style="display: none;">
		<div class="modal-dialog" style="width: 350px;">
			<div class="modal-content">
				<div class="modal-body">
					<div class="pop-alert">
						<h4  id = "modalErrMsg">null</h4>
						<div class="alert-btn">
							<button type="button" class="btn btn-page" onclick = "goModalOkBtn()" id = "modalOkBtn">확인</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<!--/modal-->
<!-- /.container -->
<footer class="footer">
  <div class="left"><span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="goPrev()"></span> <span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id = "goHome"></span></div>
  <div class="right main">
    <div class="col-sm-10" id="toastMsg">
      <div class="toast" id="sucessMsg"><span class="toast-icon"></span>본인인증과 사전체크가 성공적으로 완료되었습니다.
        <!--toast를 띄웁니다.-->
      </div>
    </div>
    <div class="col-sm-2 floatR">
      <button type="button" class="btn btn-page  btn-block " id = "btnGoNext" onClick = "goNext()">다음</button>
    </div>
  </div>
</footer>	