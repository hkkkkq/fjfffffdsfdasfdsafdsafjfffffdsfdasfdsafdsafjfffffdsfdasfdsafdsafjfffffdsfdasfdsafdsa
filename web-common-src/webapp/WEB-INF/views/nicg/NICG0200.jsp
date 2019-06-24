<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nicg/NICG0200.js"></script>

<!--content-->
<div class="container-fluid">
  <div class="row">
    <!--.col-sm-9-->
    <div class="main">
      <h3 id="h3_title">기본정보
        <div class="floatR">
          <button type="button" id="btnPrimary" class="btn btn-control">스캔다시하기</button>
        </div>
      </h3>
      <h3 id="h3_title_mi" style="display:none;">기본정보(미성년자)
        <div class="floatR">
          <button type="button" id="btnPrimary1" class="btn btn-control">스캔다시하기</button>
        </div>
      </h3>
      <div id="h3_title_mi_forgn" class="tab-area"  style="display:none;">
        <ul class="nav nav-tabs nav-justified">
          <li id="btnForgn" class="active"><a data-toggle="tab" href="#menu1" aria-expanded="false">내국인</a></li>
          <li id="btnForgn1" class=""><a data-toggle="tab" href="#menu2" aria-expanded="true">외국인</a></li>
        </ul>
      </div>
      <form role="form" class="form-horizontal" name="formSch" method="post" >
      	<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
      	<input type="hidden" id="retvCdVal" name = "retvCdVal" value="" />
      	<input type="hidden" id="legalRetvCdVal" name = "legalRetvCdVal" value="" />
      	<input type="hidden" id="legalCustIdfyNoTypeCd" name = "legalCustIdfyNoTypeCd" value="" />
      	<input type="hidden" id="custSexCd" name="custSexCd" value="" />
      	
		<!--  스캔용 테이터 --> 		
 		<input type="hidden" id="idType" name="idType"  />
 		<input type="hidden" id="idNo" name="idNo"  />
 		<input type="hidden" id="idCustName" name="idCustName"  />
 		<input type="hidden" id="idDate" name="idDate"  />
 		<input type="hidden" id="idLicenseNo" name="idLicenseNo"  />
 		<input type="hidden" id="idIssueOrg" name="idIssueOrg"  />
 		<input type="hidden" id="idAddress" name="idAddress"  />
 		
 		<input type="hidden" id="idLegalType" name="idLegalType"  />
 		<input type="hidden" id="idLegalNo" name="idLegalNo"  />
 		<input type="hidden" id="idLegalCustName" name="idLegalCustName"  />
 		<input type="hidden" id="idLegalDate" name="idLegalDate"  />
 		<input type="hidden" id="idLegalLicenseNo" name="idLegalLicenseNo"  />
 		<input type="hidden" id="idLegalIssueOrg" name="idLegalIssueOrg"  />
 		<input type="hidden" id="idLegalAddress" name="idLegalAddress"  />
 		
        <div class="form-group">
          <label class="col-sm-2" for="">고객명<span class="essential"></span></label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="custNm" placeholder="홍길동" value="">
          </div>
        </div>
        <div id="divJumin" class="form-group" style="display:none;">
          <label id="lblJumin" class="col-sm-2" for="">주민번호<span class="essential"></span></label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="juminNo" placeholder="000000-0000000" maxlength="14" onkeydown = "onlyNumberCustIdfyNo(this)">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2" for="">생년월일<span class="essential"></span></label>
          <div class="col-sm-6">
            <input type="text" class="form-control" id="birthDay" placeholder="1980.10.10"  maxlength="10"  onkeydown = "onlyNumberIssDateVal(this)" pattern = "\d*">
          </div>
		  <div class="col-sm-2">
			<button type="button" id="btnF" class="btn btn-default  btn-block active">여</button>
		  </div>	
		  <div class="col-sm-2">
			<button type="button" id="btnM" class="btn btn-default  btn-block ">남</button>
		  </div>	
			<!-- <a href="javascript:onClickSex('2')" class="btn btn-default" role="button" id="btnF">여</a>
				<a href="javascript:onClickSex('1')" class="btn btn-default" role="button" id="btnM">남</a> --> 
        </div>
        <div class="form-group">
          <label class="col-sm-2" for="">서비스번호<span class="essential"></span></label>
          <div class="col-sm-10">
          	<input type="text" class="form-control" id="svcNo"  placeholder="010-0000-0000" maxlength="13" onkeyup="onlyNumber(this)" pattern = "\d*">
          </div>
        </div>
        <div id="divCntry" class="form-group" style="display:none;">
              <label class="col-sm-2" for="">국적<span class="essential"></span></label>
              <div class="col-sm-6">
                <input type="text" class="form-control" id="dgt3CntryNm0" placeholder="국적" >
              </div>
              <div class="col-sm-2">  
                <input type="text" class="form-control" id="dgt3CntryCd0" placeholder="" >
              </div>
              <div class="col-sm-2">
                <button type="button" id="btnCntry2" class="btn btn-primary btn-block" data-toggle="modal" data-target="#myModal">국적 찾기</button>
                <!--modal-->
			       <div class="modal fade in" data-backdrop = "static" data-keyboard = "false" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
			         <div class="modal-dialog" style="width:500px;">
			           <div class="modal-content">
			             <div class="modal-header">
			               <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
			               <h4 class="modal-title" id="myModalLabel">국가  선택</h4>
			             </div>
			             <div class="modal-body">
			               <div class="tab-area-popup mb0">
			                 <ul class="nav nav-tabs-popup nation">
			                   <li class="active"><a data-toggle="tab" href="#popup-menu1" aria-expanded="true">ㄱ ~ ㅂ</a></li>
			                   <li class=""><a data-toggle="tab" href="#popup-menu2" aria-expanded="false">ㅅ ~ ㅇ</a></li>
			                   <li class=""><a data-toggle="tab" href="#popup-menu3" aria-expanded="false">ㅈ ~ ㅎ</a></li>
			                 </ul>
			               </div>
			               <div class="tab-content">
			                 <div id="popup-menu1" class="tab-pane fade active in">
			                   <ul class="list-grpp" style="height:420px;">
			                     <li>
			                       
			                     </li>
			                   </ul>
			                 </div>
			                 <div id="popup-menu2" class="tab-pane fade">
			                 <ul class="list-grpp" style="height:420px;">
			                     <li>
			                       <div class="list-grpp-item"></div>
			                     </li>
			                   </ul>
			                 </div>
			                 <div id="popup-menu3" class="tab-pane fade">
			                 <ul class="list-grpp" style="height:420px;">
			                     <li>
			                       <div class="list-grpp-item"></div>
			                     </li>
			                   </ul>
			                 </div>
			               </div>
			             </div>
			             <div class="modal-footer">
			               <button type="button" class="btn btn-page" id = "cntryModalConfirm" data-dismiss="modal">확인</button>
			             </div>
			           </div>
			         </div>
			       </div>
			       <!--/modal-->
              </div>
        </div>
        <div class="form-group" id="comIssDateValA">
          <label class="col-sm-2" for="">발급일자<span class="essential"></span></label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="issDateVal" placeholder="2008.02.02"  onkeydown = "onlyNumberIssDateVal(this)" pattern = "\d*">
          </div>
        </div>
        
        
        <!-- 
        <div id="divCntry2" class="form-group" style="display:none;">
           <label class="col-sm-2" for="">발급일자<span class="essential"></span></label>
           <div class="col-sm-10">
             <input type="text" class="form-control" id="issDateVal0" placeholder="2008.02.02" maxlength="10" onkeydown = "onlyNumberIssDateVal(this)">
           </div>
        </div>
         -->
      </form>
      <div class="area" id="h3_area" >
      <br/>
      <h3 id="h3_title2" class="mt50">기본정보(<span class="primary-color">법정대리인</span>) </h3>
      <form role="form" class="form-horizontal">
        <div class="form-group">
          <label class="col-sm-2" for="">고객명<span class="essential"></span></label>
          <div class="col-sm-4">
            <input type="text" class="form-control" id="legalCustNm" placeholder="고객명" value="">
          </div>
          <label class="col-sm-2" for="">법정대리인</label>
          <div class="col-sm-1 col-11">
		    <button type="button" id="buBtn" class="btn btn-default  btn-block active">부</button>
		  </div> 
		  <div class="col-sm-1 col-11">
		    <button type="button" id="moBtn" class="btn btn-default  btn-block ">모</button>
		  </div>
		  <div class="col-sm-2 col-11">
		    <button type="button" id="fuBtn" class="btn btn-default  btn-block">후견인</button>
		  </div>
		    

         <!--  <div class="col-sm-4">
            <div class="btn-group btn-group-justified" role="group"	aria-label="Justified button group">
				<a href="javascript:goBuBtn()" class="btn btn-default" role="button" id="buBtn">부</a>
				<a href="javascript:goMoBtn()" class="btn btn-default" role="button" id="moBtn">모</a> 
				<a href="javascript:goFuBtn()" class="btn btn-default" role="button" id="fuBtn">후견인</a>
			</div>
          </div> -->
        </div>
        <div class="form-group">
          <label id="lblJumin2" class="col-sm-2" for="">주민번호<span class="essential"></span></label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="legalCustIdfyNo" placeholder="000000-0000000" maxlength="14" onkeydown = "onlyNumberCustIdfyNo(this)">
          </div>
        </div>
        <div class="form-group" style="display:none;">
          <label class="col-sm-2" for="">생년월일<span class="essential"></span></label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="legalbirthDay"   maxlength="10" onkeydown = "onlyNumberIssDateVal(this)" placeholder="1980.10.10">
          </div>
        </div>
        <div class="form-group" id="divCntry2">
          <label class="col-sm-2" for="">국적<span class="essential"></span></label>
          <div class="col-sm-6">
            <input type="text" class="form-control" id="dgt3CntryNm" placeholder="국적" >
          </div>
          <div class="col-sm-2">  
            <input type="text" class="form-control" id="dgt3CntryCd" placeholder="" >
          </div>
          <div class="col-sm-2">
            <button type="button" id="btnCntry" class="btn btn-primary btn-block" data-toggle="modal" data-target="#myModal">국적 찾기</button>
          </div>
        </div>
		<div class="form-group" id="comIssDateValC">
          <label class="col-sm-2" for="">발급일자<span class="essential"></span></label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="issDateValC" placeholder="2008.02.02"  onkeydown = "onlyNumberIssDateVal(this)" pattern = "\d*">
          </div>
        </div>
      </form>
      </div>
      
      <h3 class="mt50" id="h3_title3" style="display:none;">본인인증(<span class="primary-color">운전면허증</span>) </h3>
      <div class="tab-area"  style="display:none;">
        <ul class="nav nav-tabs nav-justified">
          <li class="active"><a data-toggle="tab" href="#menu3" aria-expanded="true">주민등록증</a></li>
          <li class=""><a data-toggle="tab" href="#menu4" aria-expanded="false">운전면허증</a></li>
          <li class=""><a data-toggle="tab" href="#menu5" aria-expanded="false">대한민국여권</a></li>
          <li class=""><a data-toggle="tab" href="#menu6" aria-expanded="false">국가유공자증</a></li>
          <li class=""><a data-toggle="tab" href="#menu7" aria-expanded="false">장애인등록증</a></li>
          <li class=""><a data-toggle="tab" href="#menu8" aria-expanded="false">외국인등록증</a></li>
        </ul>
      </div>
      
      <div id = "tabContentTab" class="tab-content">
      <!-- 
        <div id="menu3" class="tab-pane fade">
          <form role="form" class="form-horizontal">
            <div class="form-group">
              <label class="col-sm-2" for="">발급일자<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="issDateVal" placeholder="2008.02.02"  onkeydown = "onlyNumberIssDateVal(this)">
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2" for="">연락처<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="rnmConfrTelNo0" placeholder="010-0000-0000"  maxlength="13" onkeyup="autoHypenPhone(this)">
              </div>
            </div>
          </form>
        </div>
         -->
        <div id="menu4" class="tab-pane fade active in">
          <form role="form" class="form-horizontal">
            <div class="form-group">
              <label class="col-sm-2" for="">면허번호<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="licnsNo" placeholder="경기0123456789">
              </div>
            </div>
            <div class="form-group" id="comIssDateValB">
              <label class="col-sm-2" for="">발급일자<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="licnsDate" placeholder="2008.02.02" maxlength="10" onkeydown = "onlyNumberIssDateVal(this)" pattern = "\d*">
              </div>
            </div>
            <!-- 
            <div class="form-group">
              <label class="col-sm-2" for="">연락처<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="rnmConfrTelNo1" placeholder="010-0000-0000"  maxlength="13" onkeyup="autoHypenPhone(this)">
              </div>
            </div> -->
          </form>
        </div>
        <div id="menu5" class="tab-pane fade active in">
          <form role="form" class="form-horizontal">
            <div class="form-group">
              <label class="col-sm-2" for="">여권번호<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="custPassNo" placeholder="M12345678">
              </div>
            </div>
          </form>
        </div>
        <!-- 
        <div id="menu5" class="tab-pane fade">
          <form role="form" class="form-horizontal">
            <div class="form-group">
              <label class="col-sm-2" for="">연락처<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="rnmConfrTelNo2" placeholder="010-0000-0000"  maxlength="13" onkeyup="autoHypenPhone(this)">
                
              </div>
            </div>
          </form>
        </div>
        <div id="menu6" class="tab-pane fade">
          <form role="form" class="form-horizontal">
            <div class="form-group">
              <label class="col-sm-2" for="">연락처<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="rnmConfrTelNo3" placeholder="010-0000-0000"  maxlength="13" onkeyup="autoHypenPhone(this)">
              </div>
            </div>
          </form>
        </div>
        <div id="menu7" class="tab-pane fade">
          <form role="form" class="form-horizontal">
            <div class="form-group">
              <label class="col-sm-2" for="">발급일자<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="dpsonRegIssDate" placeholder="2008.02.02" maxlength="10" onkeydown = "onlyNumberIssDateVal(this)">
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2" for="">고객연락처<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="rnmConfrTelNo4" placeholder="010-0000-0000"  maxlength="13" onkeyup="autoHypenPhone(this)">
              </div>
            </div>
          </form>
        </div>
        
         
        <div id="menu8" class="tab-pane fade">
          <form role="form" class="form-horizontal">
            <div class="form-group">
              <label class="col-sm-2" for="">국적<span class="essential"></span></label>
              <div class="col-sm-6">
                <input type="text" class="form-control" id="dgt3CntryNm" placeholder="국적" >
              </div>
              <div class="col-sm-2">  
                <input type="text" class="form-control" id="dgt3CntryCd" placeholder="" >
              </div>
              <div class="col-sm-2">
                <button type="button" id="btnCntry" class="btn btn-page  btn-block" data-toggle="modal" data-target="#myModal">국적 찾기</button>
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-sm-2" for="">발급일자<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="issDateVal2" placeholder="2008.02.02" maxlength="10" onkeydown = "onlyNumberIssDateVal(this)">
              </div>
            </div>
          </form>
        </div>
         -->
      </div>
      <!-- 
      <h3 class="mt50">비상연락망</h3>
      <form role="form" class="form-horizontal">
        <div class="form-group">
          <label class="col-sm-2" for="">전화번호<span class="essential"></span></label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="" placeholder="010-0000-0000">
          </div>
        </div>
         -->
      </form>
      
    </div>
    <!--/.col-sm-9-->
  </div>
  <!--/row-->
</div>
<!-- /.container -->

	   
              
		<!--modal-
		<div class="modal fade in" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-backdrop fade in" style="height: 832px;"></div>
		  <div class="modal-dialog" style="width:500px;height: 700px;">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		        <h4 class="modal-title" id="myModalLabel">국가 선택</h4>
		      </div>
		      <div class="modal-body">
		         <div class="tab-area-popup mb0">
		          <ul class="nav nav-tabs-popup nation">
		            <li class="active"><a data-toggle="tab" href="#popup-menu1" aria-expanded="true">ㄱ ~ ㅂ</a></li>
		            <li class=""><a data-toggle="tab" href="#popup-menu2" aria-expanded="false">ㅅ ~ ㅇ</a></li>
		            <li class=""><a data-toggle="tab" href="#popup-menu3" aria-expanded="false">ㅈ ~ ㅎ</a></li>
		          </ul>
		        </div>  
		        <div class="tab-content">
		          <div id="popup-menu1" class="tab-pane fade active in">
		            <ul class="list-grpp" style="height:420px;" id = "ProdCdList">
		              <li>
		                <div class="list-grpp-item"></div>
		              </li>
		
		            </ul>
		          </div>
		          <div id="popup-menu2" class="tab-pane fade">ㅅ ~ ㅇ</div>
		          <div id="popup-menu3" class="tab-pane fade">ㅈ ~ ㅎ</div>
		        </div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" id="btnConfirm" class="btn btn-page">확인</button>
		      </div>
		    </div>
		  </div>
		</div>
		/modal-->
			
<footer class="footer">
  <div class="left">
  	<span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="javascript:goPrev();"></span>
  	<span><img src="${ctx_res}/images/left_home.png" class="img-responsive"  id="goHome"></span>
  </div>
  <div class="right main">
    <div class="col-sm-10">
      <div id="idtoast" class="toast"><span class="toast-icon"></span>본인인증과 사전체크가 성공적으로 완료되었습니다. </div>
    </div>
    <div class="col-sm-2 floatR">
      <button type="button" id="btnNext" class="btn btn-page  btn-block ">다음</button>
    </div>
  </div>
</footer>

