<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nicg/NICC0100.js"></script>

<!--content-->
<div class="container-fluid">
  <div class="row">
    <!--.col-sm-9-->
    <div class="main">
    
      <h3>기본정보</h3>
      <form role="form" class="form-horizontal" name="formSch" method="post" >
      	<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
      	
        <div class="form-group">
          <label class="col-sm-2" for="">고객명<span class="essential"></span></label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="custNm" placeholder="홍길동">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2" for="">생년월일<span class="essential"></span></label>
          <div class="col-sm-7">
            <input type="text" class="form-control" id="brthDate" placeholder="">
            
            <!--  <input type="text" class="form-control" id="brthDate" onkeydown = "onlyNumber(this,8)" placeholder="19801010">  -->
          </div>
          <div class="col-sm-3 ">
            <div class="btn-group btn-group-justified" role="group" aria-label="Justified button group"> 
				<a href="javascript:onClickSex('1')" class="btn btn-default" role="button" id="btnM">남</a> 
				<a href="javascript:onClickSex('2')" class="btn btn-default" role="button" id="btnF">여</a>
            </div>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2" for="">전화번호<span class="essential"></span></label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="svcNo" onkeydown = "onlyNumber(this,11)" placeholder="전화번호">
          </div>
        </div>
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
  	<span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="javascript:goPrev();"></span> 
  	<span><img src="${ctx_res}/images/left_home.png" class="img-responsive"  id="goHome"></span>
  </div>
  <div class="right main">
    <div class="col-sm-10">
      <div id="idtoast" class="alert"><span class="alert-icon">!</span> 미성년자입니다. 법정대리인의 신분증을 스캔해 주세요.</div>
    </div>
    <div class="col-sm-2 floatR">
      <button type="button" id="btnNext" class="btn btn-page  btn-block ">다음</button>
    </div>
  </div>
</footer>

