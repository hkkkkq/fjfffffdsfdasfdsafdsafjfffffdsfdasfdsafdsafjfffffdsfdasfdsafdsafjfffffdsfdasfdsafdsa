<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nnsb/NNSB0901.js"></script>

<!--content-->
<div class="container-fluid">
	<div class="row">
		<!--.col-sm-9-->
		<div class="main">
		<form role="form" class="form-horizontal" name = "formSch" method = "post">
		<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
		 <input type="hidden" id="currNativeURL" name = "currNativeURL" value = 'akos://web/standard' >
   	    </form>
      <h3>표준안내서
      	<div class="warning-info" id = "finMsg"> 개통이 완료되었습니다. 표준안내서를 확인해주세요.</div>
      </h3>
    </div>
    <!--/.col-sm-9-->
  </div>
  <!--/row-->
</div>
<!-- /.container -->
<footer class="footer">
  <div class="left">
  <!-- 
  <span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="goPrev()"></span>
   -->
     <span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id="goHome"></span>
  </div>
  <div class="right main">
    <div class="col-sm-10">
    </div>
    <div class="col-sm-2 floatR">
      <button type="button" class="btn btn-page  btn-block " id = "btnGoNext" onClick="goNext()">다음</button>
    </div>
  </div>
</footer>