<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nnsb/NNSB0800.js"></script>

<!--content-->
<div class="container-fluid">
	<div class="row">
		<!--.col-sm-9-->
		 <div class="main">
		 <form role="form" class="form-horizontal" name = "formSch" method = "post">
		 <input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
		 <input type="hidden" id="testYn" name = "testYn" value = '${testYn}' >
		 <input type="hidden" id="currNativeURL" name = "currNativeURL" value = 'akos://web/documents' >
     	</form>
      <h3>전자서식지</h3>
    </div>
    <!--/.main-->
  </div>
  <!--/row-->
</div>
<!-- /.container -->
<footer class="footer">
  <div class="left"><span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="goPrev()"></span> <span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id="goHome"></span></div>
  <div class="right main">
    <div class="col-sm-10">
    </div>
    <div class="col-sm-2 floatR">
      <button type="button" class="btn btn-page  btn-block " id = "btnNext" onClick = "nextBtn()">개통</button>
    </div>
  </div>
</footer>