<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nnpb/NNPB0120.js"></script>



<!--content-->
<div class="container-fluid">
  <div class="row">

    <!--.col-sm-9-->
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
    <form role="form" class="form-horizontal" name = "formSch" method = "post">
     </form>
      <h3>촬영</h3> 
      <div class="shooting-box">
        <p>여기에 서류를 맞추고 촬영하세요.</p>
      </div>
    </div>
    <!--/.col-sm-9-->
  </div>
  <!--/row-->
</div>
<!-- /.container -->
<footer class="footer">
  <div class="left"><span><img src="${ctx_res}/images/left_previous.png" class="img-responsive"></span> <span><img src="${ctx_res}/images/left_home.png" class="img-responsive"></span></div>
  <div class="right main">
    <div class="col-sm-2 floatR">
      <button type="button" class="btn btn-page  btn-block " onClick="goNext()">다음</button>
    </div>
  </div>
</footer>


		