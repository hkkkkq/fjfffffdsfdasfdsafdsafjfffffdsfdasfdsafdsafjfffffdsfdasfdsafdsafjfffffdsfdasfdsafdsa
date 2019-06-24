<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<script src="${ctx_res}/js/bootstrap.min.js"></script>
<script src="${ctx_res}/js/ntrm/NTRM0100.js"></script>

<!--content-->
<div class="container-fluid">
	<div class="row">
		<!--.col-sm-9-->
		<div class="main">
			<h3>기본정보</h3>
			<form role="form" class="form-horizontal" name="formSch" method="POST">
				<div class="form-group">
					<label class="col-sm-2" for="">고객명<span class="essential"></span></label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="custNm" placeholder = "홍길동">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2" for="">생년월일<span class="essential"></span></label>
					<div class="col-sm-6">
						<input type="text" class="form-control" id="brthDate" placeholder = "1980.10.10" pattern = "\d*">
					</div>
					<div class="col-sm-2">
						<button type="button" id="btnF" class="btn btn-default  btn-block" onclick="javascript:onClickSex('2')">여</button>
					</div>	
					<div class="col-sm-2">
						<button type="button" id="btnM" class="btn btn-default  btn-block" onclick="javascript:onClickSex('1')">남</button>
					</div>
					<!-- 
					<div class="col-sm-2 ">
						<div class="btn-group btn-group-justified" role="group"	aria-label="Justified button group">
							<a href="javascript:onClickSex('1')" class="btn btn-default" role="button" id="btnM">남</a> 
							<a href="javascript:onClickSex('2')" class="btn btn-default" role="button" id="btnF">여</a>
						</div>
					</div>
					 -->
				</div>
				<div class="form-group">
					<label class="col-sm-2" for="">서비스번호<span class="essential"></span></label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="telNo" placeholder = "010-0000-0000" pattern = "\d*">
					</div>
				</div>
			</form>
		<!--/.col-sm-9-->
		</div>	
		<!-- Div 버전
		<div class="main" id="divAgnt" style="visibility:hidden;">
			<form role="form" class="form-horizontal" name="formAgnt">
			<h3>기본정보(법정대리인)</h3>
				<div class="form-group">
					<label class="col-sm-2" for="">고객명<span class="essential"></span></label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="legalCustNm" readonly>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2" for="">관계<span class="essential"></span></label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="legalRelNm" readonly>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2" for="">인증번호<span class="essential"></span></label>
					<div class="col-sm-10 placeholder" data-placeholder="770101" id="ph_legalBthday">
						<input type="text" class="form-control" id="legalBthday">
					</div>
				</div>
				<div class="form-group" style="visibility:hidden;">
					<label class="col-sm-2" for="">연락처</label>
					<div class="col-sm-10 placeholder" data-placeholder="010-1234-5678" id="ph_housTel">
						<input type="text" class="form-control" id="housTel">
					</div>
				</div>
			</form>
		</div>
		 -->
		
			 <div class="col-sm-2">
			   	<div class="modal fade" id="agntModal" tabindex="-1" role="dialog" aria-labelledby="agntModalLabel" aria-hidden="true">
			    	<div class="modal-dialog" style="width:580px;">
			       		<div class="modal-content">
			       			<div class="modal-header">
			       				<button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick="javascript:canAgnt()"><span aria-hidden="true">×</span></button>
                				<h4 class="modal-title" id="myModalLabel">법정대리인 인증</h4>
			       			</div>
			         		<div class="modal-body agnt">
				           		<div class="tab-area-popup">			           			           
									<form role="form" class="form-horizontal" name="formAgnt">
									<div class="form-group">
										<label class="col-sm-3" for="">성명<span class="essential"></span></label>
										<div class="col-sm-9">
											<input type="text" class="form-control" id="legalCustNm" readonly>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3" for="">관계<span class="essential"></span></label>
										<div class="col-sm-9">
											<input type="text" class="form-control" id="legalRelNm" readonly>
										</div>									
									</div>
									<div class="form-group">
										<label class="col-sm-3" for="">생년월일<span class="essential"></span></label>
										<div class="col-sm-9 placeholder" data-placeholder="000000" id="ph_legalBthday">
											<input type="text" class="form-control" id="legalBthday">
										</div>
									</div>
									<div class="form-group" style="display:none;">
										<label class="col-sm-3" for="">연락처</label>
										<div class="col-sm-9 placeholder" data-placeholder="010-1234-5678" id="ph_housTel">
											<input type="text" class="form-control" id="housTel">
										</div>
									</div>
									</form>
				         		</div>
						         <div class="modal-footer">
						         	<button type="button" class="btn btn-page" onClick="javascript:chkAgnt()">확인</button>
					         	</div>
			       			</div>
			     		</div>
			   		</div>
			 	</div>
 	 	
				<div class="form-group">	 
					<form role="form" class="form-horizontal" name="formReDir" method="post">
						<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
					</form>
				</div>
		
		</div>
	<!--/row-->
	</div>
</div>


 
<!-- /.container -->
<footer class="footer">
	<div class="left">
		<span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" id="goHome2"></span>
		<span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id="goHome"></span>
	</div>
	<div class="right main">
		<div class="col-sm-2 floatR">
			<button type="button" class="btn btn-page  btn-block " onClick="javascript:goNext()">다음</button>
		</div>
	</div>
</footer>

