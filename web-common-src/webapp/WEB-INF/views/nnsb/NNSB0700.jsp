<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nnsb/NNSB0700.js"></script>

<body class="customer">
<!--content-->
<div class="container-fluid">
	<div class="row">

		<!--.col-sm-9-->
		 <div class="main">
		 <form role="form" class="form-horizontal" name = "formSch" method = "post">
		<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
		<input type="hidden" id="testYn" name = "testYn" value = '${testYn}' >
    	 </form>
      <h3>이용약관
        <div class="warning-info"> 고객님께서 직접 계약내용을 확인하시고, 약관동의 해주세요. </div>
      </h3>
      <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel pa-allagree">
          <div class="panel-heading">
            <h4 class="panel-title">
              <div class="checkbox-terms-white mr5">
                <input type="checkbox" value="1" id="checkboxStplt0" name="" onclick = "goCheckboxStplt0()">
                <label for="checkboxStplt0"></label>
              </div>
              <div class="collaps-arrow collapsed allagree">모두 동의합니다. </div>
            </h4>
          </div>
        </div>
        <div class="panel">
          <div class="panel-heading">
            <h4 class="panel-title">
              <div class="checkbox-terms mr5">
                <input type="checkbox" value="1" id="checkboxStplt1" name=""  onclick = "goCheckboxStplt1()">
                <label for="checkboxStplt1"></label>
              </div>
              <span class="label label-essential">필수</span>
              <div class="collaps-arrow collapsed" data-toggle="modal" data-target="#stpltModal1" onClick = "hideSignApp()"> KT mobile 이용약관 </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="stpltModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>KT mobile 이용약관
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick = "showSignApp()"><span aria-hidden="true">×</span></button>
                  </h4>
                  <div class="mt20"> <img src="${ctx_res}/images/terms_01.png" class="img-responsive"></div>
                </div>
              </div>
            </div>
          </div>
          <!--/modal-->
        </div>
        <div class="panel">
          <div class="panel-heading">
            <h4 class="panel-title">
              <div class="checkbox-terms mr5">
                <input type="checkbox" value="1" id="checkboxStplt2" name=""  onclick = "goCheckboxStplt2()">
                <label for="checkboxStplt2"></label>
              </div>
              <span class="label label-essential">필수</span>
              <div class="collaps-arrow collapsed" data-toggle="modal" data-target="#stpltModal2" onClick = "hideSignApp()"> 지원금 방식 </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="stpltModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>지원금 방식
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick = "showSignApp()"><span aria-hidden="true">×</span></button>
                  </h4>
                  <div class="mt20"> 지원금에 상응하는 요금할인은 12개월 또는 24개월 약정 가능하며, 월 할인금액은 동일합니다.
                    본인은 지원금에 상응하는 요금할인 또는 단말기 지원금 선택 시 혜택에 대해 충분한 설명을 들었으며 단말기
                    지원금 또는 요금할인 선택하였습니다.</div>
                </div>
              </div>
            </div>
          </div>
          <!--/modal-->
        </div>
        <div class="panel">
          <div class="panel-heading">
            <h4 class="panel-title">
              <div class="checkbox-terms mr5">
                <input type="checkbox" value="1" id="checkboxStplt3" name="" onclick = "goCheckboxStplt3()">
                <label for="checkboxStplt3"></label>
              </div>
              <span class="label label-essential">필수</span>
              <div class="collaps-arrow" data-toggle="modal" data-target="#stpltModal3" onClick = "hideSignApp()"> 단말매매 </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="stpltModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>단말매매
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick = "showSignApp()"><span aria-hidden="true">×</span></button>
                  </h4>
                  <div class="mt20"> 일시불 신용 단말매매 계약의 대금을 일시불로 상환하여야 하며, 일시불로 상환을 원하지 않는 경우 분할 또는
                    부분분할상환을 선택할 수 있고, (부분)분할상환 선택 시 (부분)분할상환 단말매매 대금 채권의 양도 및 그에 따라 발생하는 
                    수수료를 부담할 것에 동의합니다.</div>
                </div>
              </div>
            </div>
          </div>
          <!--/modal-->
        </div>
        <div class="panel">
          <div class="panel-heading">
            <h4 class="panel-title">
              <div class="checkbox-terms mr5">
                <input type="checkbox" value="1" id="checkboxStplt4" name="" onclick = "goCheckboxStplt4()">
                <label for="checkboxStplt4"></label>
              </div>
              <span class="label label-essential">필수</span>
              <div class="collaps-arrow" data-toggle="modal" data-target="#stpltModal4" onClick = "hideSignApp()"> 신규계약 체결 </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="stpltModal4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>신규계약 체결
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick = "showSignApp()"><span aria-hidden="true">×</span></button>
                  </h4> 
                  <div class="mt20"> 신청자 본인은 대출업자 등 제3자에게 고객명의의 휴대폰을 (개통)해 주거나, 개통에 필요한 신청서류를 제공하는 경우
                    휴대폰 대출 사기 등에 (악용)되어 심각한 경제적 피해를 입을 수 있음을 안내 받았으며, 아울러 신규계약서 내용,
                    뒷면의 이동전화서비스 이용약관 및 유의사항, 관련 약관의 중요내용에 대한 명시와 설명을 듣고 이에 동의하며 개인정보
                    취급방침, 이용약관, 위치기반 서비스/위치정보사업/통신과금서비스/본인확인서비스 이용약관에 따라 위와 같이 
                    신규계약을 체결합니다.</div>
                </div>
              </div>
            </div>
          </div>
          <!--/modal-->
        </div>
        <div class="panel" id="divStplt7" >
          <div class="panel-heading">
            <h4 class="panel-title">
              <div class="checkbox-terms mr5">
                <input type="checkbox" value="1" id="checkboxStplt7" name="" onclick = "goCheckboxStplt7()">
                <label for="checkboxStplt7"></label>
              </div>
              <span class="label label-essential">필수</span>
              <div class="collaps-arrow" data-toggle="modal" data-target="#stpltModal7" onClick = "hideSignApp()"> 스폰서 </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="stpltModal7" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>스폰서
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick = "showSignApp()"><span aria-hidden="true">×</span></button>
                  </h4>
                  <div class="mt20"> 본인은 약정기간 동안 선택한 요금제에 따라 약정지원금 또는 요금할인을 제공 받으며, 약정기간 내 해지 또는 변경 시 위약금
                    또는 할인금액에 대한 할인반환금 청구와 그 밖의 유의사항에 대해 충분히 안내 받고 가입을 동의합니다.</div>
                </div>
              </div>
            </div>
          </div>
          <!--/modal-->
        </div>
        <div class="panel" id="divStplt5" >
          <div class="panel-heading">
            <h4 class="panel-title">
              <div class="checkbox-terms mr5">
                <input type="checkbox" value="1" id="checkboxStplt5" name="" onclick = "goCheckboxStplt5()">
                <label for="checkboxStplt5"></label>
              </div>
              <span class="label label-essential">필수</span>
              <div class="collaps-arrow" data-toggle="modal" data-target="#stpltModal5" onClick = "hideSignApp()"> 요금할인(지원금) </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="stpltModal5" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>요금할인(지원금)
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick = "showSignApp()"><span aria-hidden="true">×</span></button>
                  </h4>
                  <div class="mt20"> 단말기에 대한 지원금 대신 선택하신 요금제에 따른 추가 요금 할인을 받으실 수 있습니다.
                    단, 약정기간 중 해지 또는 가입불가 상품으로 변경 시 할인반환금이 부과될 수 있습니다.
                    약정기간 중 유심이동은 불가하며, 대상 단말 외 단말로 변경 시 요금할인은 중단됩니다.</div>
                </div>
              </div>
            </div>
          </div>
          <!--/modal-->
        </div>
        <div class="panel" id="divStplt6">
          <div class="panel-heading">
            <h4 class="panel-title">
              <div class="checkbox-terms mr5">
                <input type="checkbox" value="1" id="checkboxStplt6" name="" onclick = "goCheckboxStplt6()">
                <label for="checkboxStplt6"></label>
              </div>
              <span class="label label-essential">필수</span>
              <div class="collaps-arrow" data-toggle="modal" data-target="#stpltModal6" onClick = "hideSignApp()"> 단말지원금(심플코스) </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="stpltModal6" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>단말지원금(심플코스)
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick = "showSignApp()"><span aria-hidden="true">×</span></button>
                  </h4>
                  <div class="mt20"> 단말기에 대한 지원금 대신 선택하신 요금제에 따른 추가 요금 할인을 받으실 수 있습니다.
                    단, 약정기간 중 해지 또는 가입불가 상품으로 변경 시 할인반환금이 부과될 수 있습니다.
                    약정기간 중 유심이동은 불가하며, 대상 단말 외 단말로 변경 시 요금할인은 중단됩니다.</div>
                </div>
              </div>
            </div>
          </div>
          <!--/modal-->
        </div>
      </div>
      <h3 class="mt50">개인정보활용동의</h3>
      <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel pa-allagree">
          <div class="panel-heading">
            <h4 class="panel-title">
              <div class="checkbox-terms-white mr5">
                <input type="checkbox" value="1" id="checkboxAgree0" name="" onclick = "goCheckboxAgree0()">
                <label for="checkboxAgree0"></label>
              </div>
              <div class="collaps-arrow collapsed allagree">모두 동의합니다. </div>
            </h4>
          </div>
        </div>
        <div class="panel">
          <div class="panel-heading">
            <h4 class="panel-title">
              <div class="checkbox-terms mr5">
                <input type="checkbox" value="1" id="checkboxAgree1" name=""  onclick = "goCheckboxAgree1()">
                <label for="checkboxAgree1"></label>
              </div>
              <span class="label label-essential">필수</span>
              <div class="collaps-arrow collapsed" data-toggle="modal" data-target="#agreeModal1" onClick = "hideSignApp()">고유식별정보/개인정보 수집·이용 동의</div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="agreeModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>고유식별정보/개인정보 수집·이용 동의
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick = "showSignApp()"><span aria-hidden="true">×</span></button>
                  </h4>
                  <div class="mt20"> <img src="${ctx_res}/images/privacy_img01.png" class="img-responsive"></div>
                </div>
              </div>
            </div>
          </div>
          <!--/modal-->
        </div>
        <div class="panel">
          <div class="panel-heading">
            <h4 class="panel-title">
              <div class="checkbox-terms mr5">
                <input type="checkbox" value="1" id="checkboxAgree2" name=""  onclick = "goCheckboxAgree2()">
                <label for="checkboxAgree2"></label>
              </div>
              <span class="label label-essential">필수</span>
              <div class="collaps-arrow collapsed" data-toggle="modal" data-target="#agreeModal2" onClick = "hideSignApp()"> 개인(신용)정보의 조회 및 이용/제공에 대한 동의 </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="agreeModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>개인(신용)정보의 조회 및 이용/제공에 대한 동의
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick = "showSignApp()"><span aria-hidden="true">×</span></button>
                  </h4>
                  <div class="mt20"> <img src="${ctx_res}/images/privacy_img02.png" class="img-responsive"></div>
                </div>
              </div>
            </div>
          </div>
          <!--/modal-->
        </div>
        <div class="panel">
          <div class="panel-heading">
            <h4 class="panel-title">
              <div class="checkbox-terms mr5">
                <input type="checkbox" value="1" id="checkboxAgree3" name="" onclick = "goCheckboxAgree3()">
                <label for="checkboxAgree3"></label>
              </div>
              <span class="label label-choice">선택</span>
              <div class="collaps-arrow" data-toggle="modal" data-target="#agreeModal3" onClick = "hideSignApp()"> 고객 편의제공을 위한 이용 및 처리위탁, 정보/광고 수신동의 </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="agreeModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>고객 편의제공을 위한 이용 및 처리위탁, 정보/광고 수신동의
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick = "showSignApp()"><span aria-hidden="true">×</span></button>
                  </h4>
                  <div class="mt20"> <img src="${ctx_res}/images/privacy_img03.png" class="img-responsive"></div>
                </div>
              </div>
            </div>
          </div>
          <!--/modal-->
        </div>
        <div class="panel">
          <div class="panel-heading">
            <h4 class="panel-title">
              <div class="checkbox-terms mr5">
                <input type="checkbox" value="1" id="checkboxAgree4" name="" onclick = "goCheckboxAgree4()">
                <label for="checkboxAgree4"></label>
              </div>
              <span class="label label-choice">선택</span>
              <div class="collaps-arrow" data-toggle="modal" data-target="#agreeModal4" onClick = "hideSignApp()"> 타사로부터 의뢰받은 정보/광고 수신동의 </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="agreeModal4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>타사로부터 의뢰받은 정보/광고 수신동의
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick = "showSignApp()"><span aria-hidden="true">×</span></button>
                  </h4>
                  <div class="mt20"> <img src="${ctx_res}/images/privacy_img04.png" class="img-responsive"></div>
                </div>
              </div>
            </div>
          </div>
          <!--/modal-->
        </div>
        <div class="panel">
          <div class="panel-heading">
            <h4 class="panel-title">
              <div class="checkbox-terms mr5">
                <input type="checkbox" value="1" id="checkboxAgree5" name="" onclick = "goCheckboxAgree5()">
                <label for="checkboxAgree5"></label>
              </div>
              <span class="label label-choice">선택</span>
              <div class="collaps-arrow" data-toggle="modal" data-target="#agreeModal5" onClick = "hideSignApp()"> 타사로부터 의뢰받은 정보/광고 전송을 위한 개인정보 처리위탁 동의 </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="agreeModal5" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4> 타사로부터 의뢰받은 정보/광고 전송을 위한 개인정보 처리위탁 동의
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick = "showSignApp()"><span aria-hidden="true">×</span></button>
                  </h4>
                  <div class="mt20"> <img src="${ctx_res}/images/privacy_img05.png" class="img-responsive"></div>
                </div>
              </div>
            </div>
          </div>
          <!--/modal-->
        </div>
      </div>
      <h3 class="mt50">서명
        <div class="warning-info">서명 및 사인란을 2초 이상 길게 누르시면 새로 작성할 수 있습니다.</div>
      </h3>
      <div class="box-area">
        <div class="col-sm-6">
        </div>
        <div class="col-sm-6">
        </div>
      </div>
    </div>
    <!--/.main-->
  </div>
  <!--/row-->
</div>
<!-- /.container -->
<footer class="footer">
  <div class="left"><span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick = "goPrev()"></span> <span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id="goHome"></span></div>
  <div class="right main">
    <div class="col-sm-2 floatR">
      <button type="button" class="btn btn-page  btn-block " id = "btnGoNext" onClick="goNext()">다음</button>
    </div>
  </div>
</footer>
</body>