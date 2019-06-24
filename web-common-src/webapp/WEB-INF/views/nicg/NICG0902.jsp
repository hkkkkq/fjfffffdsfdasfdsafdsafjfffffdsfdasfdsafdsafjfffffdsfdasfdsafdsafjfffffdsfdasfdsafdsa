<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nicg/NICG0902.js"></script>

<!--content-->
<div class="container-fluid">
  <div class="row">
    <!--.main-->
    <div class="main">
      <h3>처리완료</h3>
      <form role="form" class="form-horizontal" name = "formSch" method = "post">
		<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
	  </form>
      <div class="join-area"> <img class="img-join" src="${ctx_res}/images/icon-cancel.png" alt="" style="width: 200px; height: 200px;">
        <h3 class="mt30">기기변경이 완료되었습니다. <button type="button" id="btnStandardDoc" class="btn btn-page">표준안내서</button>를 확인해주세요. </h3>
      </div>
      <div class="media">
        <div class="media-left"> <a href="#" class="thumbnail qr-code"><img id="qrCodeImg" class="img-responsive" style="width:210px; height:210px;"></a></div>
        <div class="media-body">
          <div class="form-horizontal tbline">
            <h3 class="primary-color bold">표준안내서 및 신청서</h3>
            <!-- 
            <div class="form-group mb10">
              <div class="col-sm-12">
                <button type="button" id="btnStandardDoc" class="btn btn-default  btn-block">표준안내서</button>
              </div>
            </div>
             -->
            <div class="form-group mb10">
              <div class="col-sm-6">
                <button type="button" id="btnPrint" class="btn btn-default  btn-block ">인쇄</button>
              </div>
              <div class="col-sm-6">
                <button type="button" id="btnEmail" class="btn btn-default  btn-block ">이메일 발송</button>
              </div>
            </div>
            <div class="form-group">
              <div class="col-sm-12">
                <input type="text" class="form-control" id="emailSet" placeholder="qwert@aaaaa.com" value="" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--/.main-->
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
		<span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id="goHome2"></span>
	</div>
	<div class="right main">
	    <div class="col-sm-2 floatR">
	      <button type="button" id="goHome4" class="btn btn-page  btn-block ">종료</button>
	    </div>
	</div>
</footer>
