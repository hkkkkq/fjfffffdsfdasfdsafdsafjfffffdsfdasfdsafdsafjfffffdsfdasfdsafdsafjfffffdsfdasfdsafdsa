<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<script src="${ctx_res}/js/bootstrap.min.js"></script>
<script src="${ctx_res}/js/ntrm/NTRM0300.js"></script>

<!--content-->
<div class="container-fluid">
	<div class="row">

		<!--.col-sm-9-->
	<div class="main">
      <h3>해지사유</h3>
      <form role="form" class="form-horizontal" name="formSch">
        <div class="form-group">
          <label class="col-sm-2" for="">사유선택<span class="essential"></span></label>
          <div class="col-sm-5">
            <button type="button" class="btn btn-default  btn-block" id="btn7" style="display:none;" onclick="javascript:onClickWhy('7')">해지-대리점 개통 취소(14일이내)</button>
          </div>
          <div class="col-sm-5">
            <button type="button" class="btn btn-default  btn-block" id="btn1" style="display:none;" onclick="javascript:onClickWhy('1')">해지-통화(방송)품질해지(14일이내)</button>
          </div>
		  <div class="col-sm-10">
            <button type="button" class="btn btn-default  btn-block active" id="btn6" style="display:none;" onclick="javascript:onClickWhy('6')">해지-당일개통/당일해지</button>
          </div>
          <!-- 
          <div class="col-sm-10">
            <div class="btn-group btn-group-justified" role="group"	aria-label="Justified button group">            	
                <a href="javascript:onClickWhy('7')" class="btn btn-default" role="button" id="btn7" style="display:none;">해지-대리점 개통 취소(14일이내)</a> 
                <a href="javascript:onClickWhy('1')" class="btn btn-default" role="button" id="btn1" style="display:none;">해지-통화(방송)품질해지(14일내)</a>
                <a href="javascript:onClickWhy('6')" class="btn btn-default" role="button" id="btn6" style="display:none;">해지-당일개통/당일해지</a> 
          	</div>
          </div>
           -->
        </div>
      </form>
      <h3 class="mt40">연락정보</h3>
      <form role="form" class="form-horizontal">
        <div class="form-group">
          <label class="col-sm-2" for="">연락번호<span class="essential"></span></label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="telNo" placeholder = "010-0000-0000" pattern = "\d*">
          </div>
          	<input type="text" class="form-control" id="telNo2" style="display:none;" pattern = "\d*">
        </div>
      </form>
      
      <div class="col-sm-2">
	   <!-- Modal -->
	   <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	   <div class="modal-dialog" style="width:480px;"> 
	       <div class="modal-content">
	       	<div class="modal-header">
   				<button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick="javascript:onCancel()"><span aria-hidden="true">×</span></button>
         		<h4 class="modal-title" id="myModalLabel">개통취소</h4>
   			</div>
	         <div class="modal-body agnt">
	         <!--
	           <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	         //-->
	         <!-- 
	         	<p align="center"><img class="img-cancel" src="${ctx_res}/images/icon_loading.gif" alt="" style="width:80px; height: 80px;" id="imgWait"></p>
	          -->	
	           <div class="tab-area-popup">
	           		<p align="center">	
	           		<label class="col-sm-12" for="" id="gdncMsg">
	           		<!-- 	<h4> -->
	           			사전체크 준비 중입니다.<br>잠시만 기다려 주십시오.
	           		<!-- 	</h4> -->
	           		</label>
	           		</p>
	         </div>
	         <div class="modal-footer">
	         <p align="center">
	         	<button type="button" class="btn btn-page" onClick="javascript:callTrmn()" style="visibility:hidden;" id="btnOk">확인</button>
	         	<!-- 
	         	<button type="button" class="btn btn-page" onClick="javascript:onCancel()" style="visibility:hidden;" id="btnCan">취소</button>
	         	 -->
	         </p>	
	         </div>
	       </div>
	     </div>
	   </div>
	 </div>	 
<!--
//고객조회 화면으로 이동       
      <div id = "divAgnt" style="visibility:hidden;">
      <form role="form" class="form-horizontal">
      	<h3 class="mt40">법정대리인</h3>
      		<div class="form-group">
				<label class="col-sm-2" for="" class="mt40">기본정보<span class="essential"></span></label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="agntInfo" readonly="true">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2" for="">인증<span class="essential"></span></label>
				<div class="col-sm-5">
					<input type="text" class="form-control" id="legalBthday" onkeydown="onlyNumber(this, 6)" placeholder="770101">
				</div>
				<label class="col-sm-5" for="">(주민번호 앞 6자리)</label>
			</div>
      </form>
 -->      
 <!-- 
    <div class="modal fade" id="progressbar" tabindex="-1" role="dialog" aria-labelledby="progressbarLabel" aria-hidden="true">
		<div class="modal-backdrop fade in" style="height: 832px;">
  			<div class="modal-dialog" style="top:300px;left:350px;">
    			<div class="loader"></div>
  			</div>
		</div>
	</div>
 -->       
				<div class="form-group">	 
					<form role="form" class="form-horizontal" name="formReDir" method="post">
						<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
					</form>
				</div>
    <!--/.col-sm-9-->
  			</div>
  <!--/row-->
		</div>

	</div>
</div>

<!-- /.container -->
<footer class="footer">
  <div class="left">
	  <span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="goPrev()"></span> 
	  <span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id="goHome"></span>
  </div>
  <div class="right main">
    <div class="col-sm-2 floatR">
      <button type="button" class="btn btn-page  btn-block " onClick="goPrevChk()">다음</button>
    </div>
  </div>
</footer>

