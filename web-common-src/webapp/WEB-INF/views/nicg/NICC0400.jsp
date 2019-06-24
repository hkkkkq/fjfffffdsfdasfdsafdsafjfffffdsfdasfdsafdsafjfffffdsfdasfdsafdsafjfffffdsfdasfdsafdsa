<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nicg/NICC0400.js"></script>

<!--content-->
<div class="container-fluid">
  <div class="row">
    <!--.col-sm-9-->
    <div class="main">
      <div class="cancel-area"> <img class="img-cancel" src="${ctx_res}/images/icon-cancel.png" alt="" style="width: 131px; height: 99px;">
        <h3>취소가 완료되었습니다.</h3>
      </div>

      <form role="form" class="form-horizontal" name="formSch" method="post" >
      	<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
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
  <!--	<span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="javascript:goPrev();"></span>
  --> 
  	<span><img src="${ctx_res}/images/left_home.png" class="img-responsive"  id="goHome2"></span>
  </div>
  <div class="right main">
    <div class="col-sm-2 floatR">
      <button type="button" id="goHome4" class="btn btn-page  btn-block ">홈</button>
    </div>
  </div>
</footer>

