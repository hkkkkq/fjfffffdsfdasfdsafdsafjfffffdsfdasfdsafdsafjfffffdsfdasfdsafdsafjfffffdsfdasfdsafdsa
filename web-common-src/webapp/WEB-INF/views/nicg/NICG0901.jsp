<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nicg/NICG0901.js"></script>


<!--content-->
<div class="container-fluid">
	<div class="row">
		<!--.col-sm-9-->
		<div class="main">

			<h3>표준안내서
				<div class="warning-info"> 기기변경이 완료되었습니다. 표준안내서를 확인해주세요. </div>
			</h3>
      <form role="form" class="form-horizontal" name = "formSch" method = "post">
		<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
		<input type="hidden" id="currNativeURL" name = "currNativeURL" value = 'akos://web/standard' >
			<!-- <div class="guide-box">내용</div> -->
			
   	  </form>
			
		</div>
		<!--/.col-sm-9-->
	</div>
	<!--/row-->
</div>
<!-- /.container -->

<!--modal-->
<div class="modal fade" id="progressbar" data-backdrop = "static" data-keyboard = "false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-backdrop fade in" style="height: 832px;">
    <div class="modal-dialog" style="top:300px;left:350px;">  
      <div class="loader"></div> 
    </div>
  </div>
</div>
<!--/modal-->

<footer class="footer">
	<div class="left">
		<!-- <span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="goPrev()"></span> --> 
		<span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id="goHome"></span>
	</div>
	<div class="right main">
	    <!--  <div class="col-sm-10">
	      <div class="alert"><span class="alert-icon">!</span> 고객님께서 직접 계약내용을 확인하시고, 약관동의 해주세요.</div>
	    </div>  -->
		<div class="col-sm-2 floatR">
			<button type="button" id="btnNext" class="btn btn-page  btn-block " onClick="goNext()">다음</button>
		</div>
	</div>
</footer>