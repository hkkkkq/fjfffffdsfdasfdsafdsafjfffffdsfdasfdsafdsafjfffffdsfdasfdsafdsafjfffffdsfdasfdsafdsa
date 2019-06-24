<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<script src="${ctx_res}/js/ntrm/NTRM0400.js"></script>

<!--content-->
<div class="container-fluid">
  <div class="row">
    <!--.col-sm-9-->
    <div class="main" style="background-color:">
      <div class="sorry-area" id="reslt"> 
      <img class="img-cancel" src="${ctx_res}/images/icon-cancel.png" alt="" style="width:180px; height:180px;">
        <h3 class="mt30">취소가 완료되었습니다.</h3>
      </div>
    </div>
    <!--/.col-sm-9-->
  </div>
  <!--/row-->
</div>

<div class="form-group">	 
	<form role="form" class="form-horizontal" name="formReDir" method="post">
		<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
	</form>
</div>
<!-- /.container -->
<!-- OtcomNoChg modal S -->
<div class="modal fade" id="otcomNoChgModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:680px;">		
		<div class="modal-content">
			<div class="modal-header">       
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="myModalLabel">타사번호변경취소</h4>
            </div>
			<div class="modal-body">
				<div class="pop-alert">
					<div  id="popMsg">
					<!--h4 id="msgTitle"></h4>
					<div class="col-sm-10">
            			<input type="text" class="form-control" id="telNo" placeholder="011-000-0000" maxlength="11">
          			</div-->
          			</div>
					<div class="alert-btn" id="modalBtn">
						<!--button type="button" class="btn btn-page" onclick="sendKtoaMot()">확인</button-->
					</div>       	
				</div>
			</div>
		</div>
	</div>
</div>
<!-- OtcomNoChg modal E -->
<footer class="footer">
  <div class="left">
  <!-- 
	<span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onclick="javascritp:goPrev();"></span>
 -->	
  	<span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id="goHome2"></span>
  
  </div>
  <div class="right main">
  	<div class="col-sm-2 floatR">    
      <button type="button" class="btn btn-page  btn-block " id="goHome4">종료</button>
    </div>
  	<div class="col-sm-3 floatR">      
      <button type="button" class="btn btn-warning btn-block " onclick="javascript:cnclOtcomNoChg()" style="visibility:hidden;" id="btnOtcom">타사번호변경취소</button>
    </div>
  </div>
</footer>
