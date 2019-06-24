<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/bootstrap.min.js"></script>
<script src="${ctx_res}/js/nnpb/NNPB0200.js"></script>


<div class="container-fluid">
  <div class="row">
    <!--.col-sm-9-->
    <div class="main">
      <h3>번호인증</h3>
      <form role="form" class="form-horizontal">
        <div class="form-group">
          <label class="col-sm-2" for="">이동전화<span class="essential"></span></label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="telNo" placeholder="010-0000-0000" maxlength="11" onkeydown = "onlyNumber(this)" pattern = "\d*">
            <input type="hidden" id="bchngTelNo">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2" for="">이통사 선택<span class="essential"></span></label>
          <div id="bchngCmpnGrp">
          	<div class="col-sm-3 col-3">
            	<button type="button" class="btn btn-default  btn-block " onClick="cmpnBtnControl('S')" id="sktBtn">SKT</button>
          	</div>
 			<div class="col-sm-3 col-3">
            	<button type="button" class="btn btn-default  btn-block " onClick="cmpnBtnControl('L')" id="lguBtn">LGU+</button>
          	</div>
 			<div class="col-sm-4 col-3">
            	<button type="button" class="btn btn-default  btn-block " onClick="cmpnBtnControl('M')" id="mvnoBtn">MVNO 선택</button>
	        </div>
          </div>
          <div class="col-sm-10 col-sm-offset-2 mt10">
            <input type="text" class="form-control" id="bchngCmpnNm" disabled placeholder="" >
            <input type="hidden" id = "bchngCmpnCd">     
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2" for="">인증번호<span class="essential"></span></label>
          <div id="athnCdGrp">
            <div class="col-sm-2 col-13">
            	<button type="button" class="btn btn-default  btn-block " onClick="npCustAthnCdControl('5')" id="cardNoBtn">카드번호</button>
          	</div>
 			<div class="col-sm-2 col-13">
            	<button type="button" class="btn btn-default  btn-block " onClick="npCustAthnCdControl('3')" id="bnkacnNoBtn">계좌번호</button>
          	</div>
 			<div class="col-sm-2 col-20">
            	<button type="button" class="btn btn-default  btn-block " onClick="npCustAthnCdControl('2')" id="hndsetSeqBtn">단말기일련번호</button>
          	</div>
 			<div class="col-sm-2 col-20">
            	<button type="button" class="btn btn-default  btn-block " onClick="npCustAthnCdControl('4')" id="ktcalsumBillNoBtn">KT합산청구번호</button>
          	</div>
 			<div class="col-sm-1 col-14">
            	<button type="button" class="btn btn-default  btn-block " onClick="npCustAthnCdControl('1')" id="giroBtn">지로(우편)</button>
          	</div>
          </div>
          <div class="col-sm-10 col-sm-offset-2 mt10">
            <input type="text" class="form-control" id="athnNoText" maxlength="4" placeholder="" pattern = "\d*">
          </div>
          <!--div class="col-sm-2 ">
            <button type="button" class="btn btn-warning btn-block mt10" id="cnclBtn" onClick="goNoChgCncl()">타사번호변경취소</button>
          </div>
          <div class="col-sm-2 ">
            <button type="button" class="btn btn-page btn-block mt10" onClick="goCustAgrNext()" id="custAgrBtn">인증요청</button>
          </div-->
        </div>
      </form>
      <h3 class="mt50">고객동의</h3>
      <form role="form" class="form-horizontal">
        <div class="form-group">
          <label class="col-sm-3" for="">해지미환급금 요금상계<span class="essential"></span></label>
          <div id="agrGrp">
            <div class="col-sm-4 col-2">
            	<button type="button" class="btn btn-default  btn-block " id = "agrBtn" onClick="trChageAgreeControl('Y')">동의</button>
          	</div>
 			<div class="col-sm-5 col-2">
            	<button type="button" class="btn btn-default  btn-block " id="noAgrBtn" onClick="trChageAgreeControl('N')">동의안함</button>
          	</div> 
          </div>
          <!--div class="col-sm-2 ">
            <button type="button" class="btn btn-page btn-block" id="npAthnBtn" onClick="goNpAthnNext()">동의요청</button>
          </div-->
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
<!-- Mvno Modal S -->
<div class="modal fade" id="mvnoModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:775px;">
		<div class="modal-content">
			<div class="modal-header">       
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<!--div class="tab-area-popup">
					<ul class="nav nav-tabs-popup nav-justified">
						<li class="active"><a data-toggle="tab" href="#popup-menu1" aria-expanded="true">은행</a></li>
                    </ul>
                </div-->
                <h4 class="modal-title" id="myModalLabel">MVNO 선택</h4>
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
<!-- Mvnon Modal E -->
<!-- ErrMsg modal S -->
<div class="modal fade" id="errMsgModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:680px;">
		<div class="modal-content">
			<div class="modal-body">
				<div class="pop-alert" id="popMsg">
					<h4 id="msgTitle"></h4>
					<p class="alert-txt" id="msgText"></p>
					<div class="alert-btn" id="closeBtn">
						<!-- button type="button" class="btn btn-warning mr10"></button>
						<button type="button" class="btn btn-page"></button-->
					</div>       	
				</div>
			</div>
		</div>
	</div>
</div>
<!-- ErrMsg modal E -->   
<!-- roading modal S  >
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
    <div class="col-sm-10" id="toastMsg">
      <div class="toast" id="sucessMsg"> </div>
    </div>
    <div id="nextButtonGrp">
    	<!--div class="col-sm-2 floatR">	
      		<button type="button" class="btn btn-page btn-block mt10" id="goNextBtn" onClick="goNext()">다음</button>
    	</div-->
    </div>
  </div>
</footer>		