<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nicg/NICG0700.js"></script>

<body class="customer">
<!--content-->
<div class="container-fluid">
  <div class="row">
    <!--.main-->
    <div class="main">
    	<form role="form" class="form-horizontal" name = "formSch" method = "post">
			<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
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
              
              <div class="collaps-arrow collapsed" data-toggle="modal" data-target="#stpltModal1" onclick="fnModalHidden();">  지원금 방식 </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" id="stpltModal1" data-backdrop = "static" data-keyboard = "false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>지원금 방식
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="fnModalShow();"><span aria-hidden="true">×</span></button>
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
                <input type="checkbox" value="1" id="checkboxStplt2" name="" onclick = "goCheckboxStplt2()">
                <label for="checkboxStplt2"></label>
              </div>
              <span class="label label-essential">필수</span>
              <div class="collaps-arrow" data-toggle="modal" data-target="#stpltModal2" onclick="fnModalHidden();"> 단말매매 </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" id="stpltModal2" data-backdrop = "static" data-keyboard = "false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>단말매매
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="fnModalShow();"><span aria-hidden="true">×</span></button>
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
        
        <div class="panel" id="plStplt5" >
          <div class="panel-heading">
            <h4 class="panel-title">
              <div class="checkbox-terms mr5">
                <input type="checkbox" value="1" id="checkboxStplt5" name="" onclick = "goCheckboxStplt5()">
                <label for="checkboxStplt5"></label>
              </div>
              <span class="label label-essential">필수</span>
              <div class="collaps-arrow" data-toggle="modal" data-target="#stpltModal5"> 스폰서 </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="stpltModal5" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>스폰서
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                  </h4>
                  <div class="mt20"> 본인은 약정기간 동안 선택한 요금제에 따라 약정지원금 또는 요금할인을 제공 받으며, 약정기간 내 해지 또는 변경 시 위약금
                    또는 할인금액에 대한 할인반환금 청구와 그 밖의 유의사항에 대해 충분히 안내 받고 가입을 동의합니다.</div>
                </div>
              </div>
            </div>
          </div>
          <!--/modal-->
        </div>
        
        <div class="panel" id="plStplt3" >
          <div class="panel-heading">
            <h4 class="panel-title">
              <div class="checkbox-terms mr5">
                <input type="checkbox" value="1" id="checkboxStplt3" name="" onclick = "goCheckboxStplt3()">
                <label for="checkboxStplt3"></label>
              </div>
              <span class="label label-essential">필수</span>
              <div class="collaps-arrow" data-toggle="modal" data-target="#stpltModal3" onclick="fnModalHidden();"> 요금할인(지원금) </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" id="stpltModal3" data-backdrop = "static" data-keyboard = "false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4> 요금할인(지원금)
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="fnModalShow();"><span aria-hidden="true">×</span></button>
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
        <div class="panel" id="plStplt4" >
          <div class="panel-heading">
            <h4 class="panel-title">
              <div class="checkbox-terms mr5">
                <input type="checkbox" value="1" id="checkboxStplt4" name="" onclick = "goCheckboxStplt4()">
                <label for="checkboxStplt4"></label>
              </div>
              <span class="label label-essential">필수</span>
              <div class="collaps-arrow" data-toggle="modal" data-target="#stpltModal4" onclick="fnModalHidden();"> 단말지원금(심플코스) </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" id="stpltModal4" data-backdrop = "static" data-keyboard = "false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4> 단말지원금(심플코스)
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="fnModalShow();"><span aria-hidden="true">×</span></button>
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
     <!-- <--><!-- %@include file="test.jsp" %> -->
     
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
              <div class="collaps-arrow collapsed" data-toggle="modal" data-target="#agreeModal1" onclick="fnModalHidden();">고유식별정보/개인정보 수집·이용 동의</div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" id="agreeModal1" data-backdrop = "static" data-keyboard = "false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>고유식별정보/개인정보 수집·이용 동의
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="fnModalShow();"><span aria-hidden="true">×</span></button>
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
              <div class="collaps-arrow collapsed" data-toggle="modal" data-target="#agreeModal2" onclick="fnModalHidden();"> 개인(신용)정보의 조회 및 이용/제공에 대한 동의 </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" id="agreeModal2" data-backdrop = "static" data-keyboard = "false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>개인(신용)정보의 조회 및 이용/제공에 대한 동의
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="fnModalShow();"><span aria-hidden="true">×</span></button>
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
              <div class="collaps-arrow" data-toggle="modal" data-target="#agreeModal3" onclick="fnModalHidden();"> 고객 편의제공을 위한 이용 및 처리위탁, 정보/광고 수신동의 </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" id="agreeModal3" data-backdrop = "static" data-keyboard = "false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>고객 편의제공을 위한 이용 및 처리위탁, 정보/광고 수신동의
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="fnModalShow();"><span aria-hidden="true">×</span></button>
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
              <div class="collaps-arrow" data-toggle="modal" data-target="#agreeModal4" onclick="fnModalHidden();"> 타사로부터 의뢰받은 정보/광고 수신동의 </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" id="agreeModal4" data-backdrop = "static" data-keyboard = "false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4>타사로부터 의뢰받은 정보/광고 수신동의
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="fnModalShow();"><span aria-hidden="true">×</span></button>
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
              <div class="collaps-arrow" data-toggle="modal" data-target="#agreeModal5" onclick="fnModalHidden();"> 타사로부터 의뢰받은 정보/광고 전송을 위한 개인정보 처리위탁 동의 </div>
            </h4>
          </div>
          <!--modal-->
          <div class="modal fade" id="agreeModal5" data-backdrop = "static" data-keyboard = "false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:980px;">
              <div class="modal-content">
                <div class="modal-body-agree">
                  <h4> 타사로부터 의뢰받은 정보/광고 전송을 위한 개인정보 처리위탁 동의
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="fnModalShow();"><span aria-hidden="true">×</span></button>
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
          <!-- <div class="name-box1 ">서명</div> -->
        </div>
        <div class="col-sm-6">
          <!-- <div class="sign-box1">사인</div> -->
        </div>
      </div>
       
    </div>
    <!--/.main-->
  </div>
  <!--/row-->
</div>
<!-- /.container -->

  <!--modal-->
  <!-- 
	<div class="modal fade" id="modalErrMsgPopup" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		style="display: none;">
		<div class="modal-backdrop fade in" style="height: 832px;"></div>
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
	 -->
	<!--/modal-->

<footer class="footer">
	<div class="left">
		<span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="goPrev()"></span> 
		<span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id="goHome"></span>
	</div>
	<div class="right main">
	<!-- 
	    <div class="col-sm-10">
	      <div class="alert"><span class="alert-icon">!</span> 고객님께서 직접 계약내용을 확인하시고, 약관동의 해주세요.</div>
	    </div>
	 -->
		<div class="col-sm-2 floatR">
			<button type="button" id="btnNext2" class="btn btn-page  btn-block " onClick="goNext()">다음</button>
		</div>
	</div>
</footer>