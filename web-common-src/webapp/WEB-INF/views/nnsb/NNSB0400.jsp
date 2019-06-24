<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nnsb/NNSB0400.js"></script>

<script>

function callWebIEMIFromIpad(imei){
	console.log("imei : " + imei);
	//alert(imei);
	 $('#imei').val(imei);
	 
	 fnImei();
	 
}

function callWebIccIdFromIpad(iccId){
	console.log("iccId : " + iccId);
	//alert(iccId);
	$('#usimSeq').val(iccId);
	
	//촬영시 imei 정보가 채워져 있는 경우 카메라 앱 종료
	if($('#imei').val().trim() != ""){
		callNative("akos://web/barcode/close");
	}
	
	$("#usimRegister").children("span").eq(0).attr("style", "display : none;");
	$("#usimRegister").children("span").eq(1).attr("style", "display : ;");
	
}

</script>

<!--content-->
<div class="container-fluid">
  <div class="row">
    <!--.main-->
    <div class="main">
      <h3>바코드촬영
        <div class="floatR">
          <button type="button" class="btn btn-control" id = "reImeiScan">촬영다시하기</button>
        </div>
      </h3>
      <div class="barcode-box">
      	  <a href = "#">
		     <span>여기에 바코드를 맞추세요.</span>
		  </a>
	  </div>
      <form role="form" class="form-horizontal" name = "formSch" method = "post">
        <input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
        <input type="hidden" id="currNativeURL" name = "currNativeURL" value = 'akos://web/barcode' >
        <div class="form-group">
        <h3>단말정보</h3>
          <label class="col-sm-2" for="">IMEI<span class="essential"></span></label>
          <div class="col-sm-10" id = "register">
            <input type="text" class="form-control" id="imei" placeholder="IMEI" maxlength = "15" pattern = "\d*">
            <span class="register" role = "button">
              <img src="${ctx_res}/images/gray_checkbox.png" style = "width : 27px; height : 23px;">
            </span>
            <span class="register" role = "button" style = "display : none;">
              <img src="${ctx_res}/images/green_checkbox.png" style = "width : 27px; height : 23px;">
            </span>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2" for="">USIM ID<span class="essential"></span></label>
          <div class="col-sm-10" id = "usimRegister">
            <input type="text" class="form-control" id="usimSeq" placeholder="USIM ID" maxlength = "19" pattern = "\d*">
            <span class="register" role = "button">
              <img src="${ctx_res}/images/gray_checkbox.png" style = "width : 27px; height : 23px;">
            </span>
            <span class="register" role = "button" style = "display : none;">
              <img src="${ctx_res}/images/green_checkbox.png" style = "width : 27px; height : 23px;">
            </span>
          </div>
        </div>
      </form>
    </div>
    <!--/.main-->
  </div>
  <!--/row-->
</div>
<!-- /.container -->
<footer class="footer">
  <div class="left">
   <span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="javascript:goPrev();"></span>  
    <span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id = "goHome"></span></div>  	  
  <div class="right main">
    <div class="col-sm-2 floatR">
      <button type="button" class="btn btn-page  btn-block " id = "btnGoNext">다음</button>
    </div>
  </div>
</footer>
