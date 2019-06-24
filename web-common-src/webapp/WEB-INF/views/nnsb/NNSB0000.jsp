<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nnsb/NNSB0000.js"></script>
<body class="cancel-bg">
<div class="container-fluid">
  <div class="row">
    <!--.col-sm-9-->
    <form role="form" class="form-horizontal" name = "formSch" method = "post">
		<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
    </form>
    <div class="main" style="background-color:">
      <div class="sorry-area"> <img class="img-cancel" src="${ctx_res}/images/icon-cancel1.png" alt="" style="width:180px; height:180px;">
        <h3>죄송합니다. 
          처리가 불가능합니다.<br>
          아래 사유를 확인해주세요.</h3>
        <p class="cancel-result" id = "msg">요금정보보험이 필요합니다. 대리점을 방문해주세요.(가입한도 체크)<br>
          요금정보보험이 필요합니다.(가입한도)</p>
      </div>
    </div>
    <!--/.col-sm-9-->
  </div>
  <!--/row-->
</div>
<!-- /.container -->
<footer class="footer">
  <div class="left">
  <!-- 
   <span><img src="${ctx_res}/images/left_previous.png" class="img-responsive"></span> 
   -->
  <span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id = "goHome4"></span></div>
  <div class="right main">
    <div class="col-sm-2 floatR">
      <button type="button" class="btn btn-page  btn-block " id = "goHome2">종료</button>
    </div>
  </div>
</footer>
</body>