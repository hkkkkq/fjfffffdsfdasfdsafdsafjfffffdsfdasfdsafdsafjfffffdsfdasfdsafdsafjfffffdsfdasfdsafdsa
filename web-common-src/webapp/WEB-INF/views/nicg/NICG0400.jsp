<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nicg/NICG0400.js"></script>

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
	
	$("#usimRegister").children("span").eq(0).attr("style", "display : none;");
	$("#usimRegister").children("span").eq(1).attr("style", "display : ;");
}
</script>

<div class="container-fluid">
  <div class="row">
	<!--.main-->
    <div class="main">
      <h3>바코드촬영
        <div class="floatR">
          <button type="button" id="btnPrimary" class="btn btn-control">촬영다시하기</button>
        </div>
      </h3>
      <div class="barcode-box" id="barcodeBox"><a href="#"><span>여기에 바코드를 맞추세요.</span></a></div>
      <h3>단말정보</h3>
      <form role="form" class="form-horizontal" name="formSch" method="post" >
      	<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
      	<input type="hidden" id="currNativeURL" name = "currNativeURL" value = 'akos://web/barcode' >
      	
        <div class="form-group">
          <label class="col-sm-2" for="">IMEI<span class="essential"></span></label>
          <div class="col-sm-10" id = "register">
            <input type="text" class="form-control" id="imei" placeholder="IMEI" value="" onkeydown = "onlyNumber(this)" maxlength = "15" pattern = "\d*">
            <span class="register" id="btnSearch" role = "button">
            	<img src="${ctx_res}/images/gray_checkbox.png" style = "width : 27px; height : 23px;">
			</span>
            <span class="register" id="btnSearch_ok" role = "button" style = "display : none;">
            	<img src="${ctx_res}/images/green_checkbox.png" style = "width : 27px; height : 23px;">
            </span>
          </div>
         <!--  <div class="col-sm-2 ">
            <button type="button" id="btnSearch" class="btn btn-page btn-block">등록</button>
          </div> -->
        </div>
        <div class="form-group" style="display:none;">
          <label class="col-sm-2" for="">단말 모델ID</label>
          <div class="col-sm-4">
            <input type="text" class="form-control" id="intmModelId" placeholder="단말 모델ID" readonly>
          </div>
          <label class="col-sm-2 pl12" for="">단말 모델명</label>
          <div class="col-sm-4">
            <input type="text" class="form-control" id="intmModelNm" placeholder="단말 모델명" readonly>
            <input type="text" class="form-control" id="intmAliasNm" placeholder="단말 모델명" readonly>
          </div>
        </div>
        
        <div class="form-group">
          <label class="col-sm-2" for="">USIM ID<span class="essential"></span></label>

              <input type="hidden" id="igcyUsimUseYn" name="igcyUsimUseYn" value="" /> 
			  <div class="col-sm-5">
				<button type="button" id="btnMons1" class="btn btn-default  btn-block">기존 USIM</button>
			  </div>	
			  <div class="col-sm-5">
				<button type="button" id="btnMons2" class="btn btn-default  btn-block ">신규 USIM</button>
			  </div>
        <!--     <div class="btn-group btn-group-justified" role="group" aria-label="Justified button group"> 
              <a href="javascript:chButton1('Y');" id="btnMons1" class="btn btn-default active" role="button">기존 USIM</a> 
              <a href="javascript:chButton1('N');" id="btnMons2" class="btn btn-default" role="button">신규 USIM</a>
            </div>  -->

          <div class="col-sm-10 col-sm-offset-2 mt10" id = "usimRegister">
            <input type="text" class="form-control" id="usimSeq" placeholder="USIM ID" value="" onkeydown = "onlyNumber(this)" maxlength = "19" pattern = "\d*">
            <input type="text" class="form-control" id="nowUsimSeq" placeholder="기존 USIM을 사용합니다." readonly style="display:none;">
            
            <span class="register" id="btnSearch" role = "button">
            	<img src="${ctx_res}/images/gray_checkbox.png" style = "width : 27px; height : 23px;">
			</span>
            <span class="register" id="btnSearch_ok" role = "button" style = "display : none;">
            	<img src="${ctx_res}/images/green_checkbox.png" style = "width : 27px; height : 23px;">
            </span>
          </div>
          
          <div class="col-sm-3" style="display:none;">
            <div class="checkbox-red" style="position:relative; top:4px; left:10px;" >
              <input type="checkbox" value="1" id="checkbox-red" name="checkbox-red">
              <label  id="checkbox" for="checkbox-red"><span>기존 USIM 사용</span></label>
            </div>
          </div>
          
          <!--modal-->
          <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="intmChkRslt" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-backdrop fade in" style="height: 832px;"></div>
            <div class="modal-dialog" style="width:350px;">
              <div class="modal-content">
                <div class="modal-body">
                  <div class="pop-alert">
                    <h4 align="center"><b>기기제약사항메세지</b></h4>
                    <p class="alert-txt" id="intmChkRsltTxt"></p>
                    <div align="center" class="alert-btn">
                      <button type="button" id="btnClose" class="btn btn-warning mr10">팝업 닫기</button>
                    </div>
                    <p></p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!--/modal-->                        
        </div>
      </form>
    </div>
    <!--/.col-sm-9-->
  </div>
  <!--/row-->
</div>
<!-- /.container -->

<!--modal-->
<div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="progressbar" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-backdrop fade in" style="height: 832px;">
    <div class="modal-dialog" style="top:300px;left:350px;">  
      <div class="loader"></div> 
    </div>
  </div>
</div>
<!--/modal-->

<div class='modal fade' data-backdrop = "static" data-keyboard = "false" id='intmModal' tabindex='-1' role='dialog' aria-labelledby='alertModalLabel' aria-hidden='true'>
	<div class='modal-dialog' style='width:480px;'>
		<div class='modal-content'>
			<div class='modal-header'>
			<button type='button' class='close' data-dismiss='modal' aria-label='Close' id='btnAlertClose2'><span aria-hidden='true'>×</span></button>
			<h4 class='modal-title' id='myModalLabel'>Warning</h4>
			</div>
			<div class='modal-body agnt'>
				<p align='center'><img class='img-cancel' src='${ctx_res}/images/icon-alert.png' alt='' id='imgWait'></p>
			<div class='tab-area-popup'>	
				<label class='col-sm-12' for='' id='alertMsg' align='center'></label>
			</div>
			<div class='modal-footer'>
				<p align='center'>
				<button type='button' class='btn btn-page' id='btnAlertClose'>확인</button>
				</p>	
			</div>
			</div>
		</div>
	</div>
</div>

<footer class="footer">
  <div class="left">
  	<span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="javascript:goPrev();"></span> 
  	<span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id="goHome" onClick="closeBar()"></span>
  </div>
  <div class="right main">
    <div class="col-sm-2 floatR">
      <button type="button" id="btnNext" class="btn btn-page  btn-block ">다음</button>
    </div>
  </div>
</footer>
