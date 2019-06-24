<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nicg/NICC0200.js"></script>

<!--content-->
<div class="container-fluid">
  <div class="row">
    <!--.col-sm-9-->
    <div class="main" style="background-color:">
      <div class="sorry-area" id="reslt"> 
      	<img class="img-cancel" src="${ctx_res}/images/icon-cancel1.png" alt="" style="width:180px; height:180px;">
        <h3>죄송합니다. 
          처리가 불가능합니다.<br>
          아래 사유를 확인해주세요.</h3>
        <!-- 
        <p class="cancel-result" ID="resltMsg">요금정보보험이 필요합니다. 대리점을 방문해주세요.(가입한도 체크)</p>
         -->
        <!-- <p class="cancel-result" id="reslt"></p> -->
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
<footer class="footer">
  <div class="left">
  <!-- 
  	<span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="javascript:goPrev();"></span>
  -->	
  	<span><img src="${ctx_res}/images/left_home.png" class="img-responsive"  id="goHome2"></span>
  </div>
  <div class="right main">
    <div class="col-sm-2 floatR">
      <button type="button" id="goHome" class="btn btn-page  btn-block " >홈</button>
    </div>
  </div>
</footer>

