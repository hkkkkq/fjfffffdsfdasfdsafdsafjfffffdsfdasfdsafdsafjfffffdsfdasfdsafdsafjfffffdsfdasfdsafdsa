<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/bootstrap.min.js"></script>
<script src="${ctx_res}/js/nnpb/NNPB0300.js"></script>


<div class="container-fluid">
  <div class="row">
    <!--.col-sm-9-->
    <div class="main">
      <h3>타사해지
        <!--div class="question-light bs-docs-popover" data-toggle="popover" title="" data-content="And here's some amazing content. It's very engaging. Right?" data-original-title="청구예정금액"> 
        	<img src="${ctx_res}/images/Info-Light.png" class="img-responsive" onClick="payAmtGuide()">
        </div-->
      </h3>
      <form role="form" class="form-horizontal" name="formSch">
        <div class="form-group">
          <label class="col-sm-3" for="">청구예정금액</label>
          <div class="col-sm-9">
            <input type="text" class="form-control txtR" id="nchrgChageTotAmt" disabled = "disabled" placeholder="0원">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-3" for="">번호이동 수수료</label>
          <div class="col-sm-9">
            <input type="text" class="form-control txtR" id="npFee" disabled = "disabled" placeholder="0원">
          </div>
        </div>
      </form>
      <h3 class="mt40">계약정보(타사)</h3>
      <form role="form" class="form-horizontal">
        <div class="form-group">
          <label class="col-sm-3" for="">선납금액</label>
          <div class="col-sm-9">
            <input type="text" class="form-control txtR" id="prepayAmt" disabled = "disabled" placeholder="0원">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-3" for="">위약금</label>
          <div class="col-sm-9">
            <input type="text" class="form-control txtR" id="penltAmt" disabled = "disabled" placeholder="0원">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-3" for="">단말기할부금</label>
          <div class="col-sm-9">
            <input type="text" class="form-control txtR" id="hndsetInslAmt" disabled = "disabled" placeholder="0원">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-3" for="">단말기 분납 지속<span class="essential"></span></label>
          <div id="hndsetInstaDuratGrp">
          	<div class="col-sm-4 col-2">
            	<button type="button" class="btn btn-default  btn-block " id="lmsPayYes" onClick="clickLmsPay('Y')">문자명세서 신청</button>
          	</div>
          	<div class="col-sm-5 col-2">
            	<button type="button" class="btn btn-default  btn-block active" id="lmsPayNo" onClick="clickLmsPay('N')">문자명세서 신청안함</button>
          	</div>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-3" for="">수신번호<span class="essential"></span></label>
          <div class="col-sm-9">
            <input type="text" class="form-control" id="lmsTelNo" placeholder="문자명세서 신청 선택 시 수신전화번호 입력" onFocus="selectAll(this)" maxlength="11" onkeydown = "onlyNumber(this)">
          </div>
          <!--div class="col-sm-9 col-sm-offset-3">
            <div class="alert-info" role="alert"> <span class="alert-info-icon"></span>이동전화 입력번호로 수신되며, 변경 가능합니다.</div>
          </div-->
        </div>
      </form>
      <h3 class="mt40">환불정보(타사)</h3>
      <form role="form" class="form-horizontal">
        <div class="form-group">
          <label class="col-sm-3" for="">계좌정보</label>
          <div class="col-sm-2">
            <button type="button" class="btn btn-primary btn-block" id="bank" onclick="goBankClick()">은행선택</button>            
          </div>
          <div class="col-sm-3">
            <input type="text" class="form-control" id="bankName" disabled = "disabled" placeholder="">
            <input type="hidden" id = "rfundNpBankCd">
          </div>
          <div class="col-sm-4">
            <input type="text" class="form-control" id="rfundBankBnkacnNo" placeholder="'-' 제외하고 숫자만 입력" onkeydown = "onlyNumber(this)" pattern = "\d*">
          </div>
        </div>
      </form>
    </div>
    <div class="form-group">	 
			<form role="form" class="form-horizontal" name="formMnp" method="post">
				<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
			</form>
	</div>
    <!--/.col-sm-9-->
  </div>
  <!--/row-->
</div>
<!-- /.container -->
<!-- Error Modal S -->
<div class="modal fade" id="modalErrMsgPopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
	<div class="modal-dialog" style="width:680px;">
		<div class="modal-content">
			<div class="modal-body">
				<div class="pop-alert">
					<h4 id="msgTitle"></h4>
					<div id="msgText"></div>
					<!--p class="alert-txt" id="msgText"></p>
					<h4  id = "modalErrMsg">null</h4-->
					<div class="alert-btn" id="closeBtn">
						<!--button type="button" class="btn btn-page" onclick = "goModalOkBtn()" id = "modalOkBtn">확인</button-->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Error Modal E -->
<!-- Bank Modal S -->
<div class="modal fade" id="bankModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:775px;">
        <div class="modal-content">     
        	<div class="modal-header">       
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<!--div class="tab-area-popup">
					<ul class="nav nav-tabs-popup nav-justified">
						<li class="active"><a data-toggle="tab" href="#popup-menu1" aria-expanded="true">은행</a></li>
                    </ul>
                </div-->
                <h4 class="modal-title" id="myModalLabel">은행</h4>
            </div>
            <div class="modal-body bank">
                <div class="tab-content" id = "bankTab">
					<div id="popup-menu1" class="tab-pane fade active in">
						<div class="bank-area" style="height:355px;">
							<div class="row show-grid">
                            </div>
						</div>
					</div>      
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-page" id = "bankModalConfirm" data-dismiss="modal">확인</button>
			</div>
		</div>
	</div>
</div>
<!-- Bank Modal E -->
<!-- roading modal S>
<div class="modal fade" id="progressbar" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="top:300px;left:350px;">  
      <div class="loader"></div> 
    </div>
  </div>
</div>
<!-- roading modal E -->
<footer class="footer">
  <div class="left"><span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="goPrev()"></span> 
  					<span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id="goHome"></span>
  </div>
  <div class="right main">
    <div class="col-sm-10">
      <div class="toast" id="sucessMsg"> <span class="toast-icon"></span> 전사업자 해지처리 성공  </div> <!-- style="visibility:hidden;" -->
    </div>
    <div class="col-sm-2 floatR">
      <button type="button" class="btn btn-page btn-block" id="npRqtBtn" onClick="goNpRqtNext()">다음</button>
    </div>
  </div>
</footer>		