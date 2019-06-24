<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nwrc/main.js"></script>

<body class="main-bg">
<div class="main-container">
  <nav class="navbar">
    <ul class="nav navbar-nav">
      <li class="name" id="usernm"></li>
      <li>
        <button type="button" class="btn btn-nav" id="logOut">로그아웃</button>
      </li>
      <li>
        <button type="button" class="btn btn-nav" id="ResultManage">처리내역</button>
      </li>
      <li class="set">
        <div class="setting">
        <img src="${ctx_res}/images/icon-setting.png" class="img-responsive" id="openSourceNoti" >
        </div>
      </li>
    </ul>
  </nav>
  <div class="page-header">
    <div class="kt-logo"></div>
  </div>
  <div class="marketing">
    <ul class="nav navbar-main">
      <li><a href="#"  id="NacOrder" class="new-subscribe">신규가입</a></li> <!-- class="new-subscribe" -->
      <li><a href="#"  id="MnpOrder" class="new-transfer">번호이동</a></li>
      <li><a href="#"  id="HcnOrder" class="new-change">기기변경</a></li>
      <li><a href="#"  id="CanOrder" class="new-cancel">취소</a></li>
    </ul>
  </div>
  <div class="modal fade in" data-backdrop="static" data-keyboard="false" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false" style="display: none;">
    <!-- <div class="modal-backdrop fade in" style="height: 832px;"></div> -->
      <div class="modal-dialog" style="width:650px;">
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title" id="myModalLabel1">사용자 확인</h4>
          </div>
          <div class="modal-body">
                <div class="form-group">
                  <div class="txtC">
                    <br/><p class="alert-txt" id="UserErrMsg"></p><br/>
                    <button type="button" class="btn btn-page" id="MkosEnd">종료</button>
                  </div>
                </div>
          </div>
        </div>
      </div>
  </div>

  <div class="modal fade in" data-backdrop="static" data-keyboard="false" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false" style="display: none;">
    <!-- <div class="modal-backdrop fade in" style="height: 832px;"></div> -->
      <div class="modal-dialog" style="width:550px;">
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title" id="myModalLabel2">입력정보 복구 안내</h4>
          </div>
          <div class="modal-body">
                <div class="form-group">
                  <div class="txtC">
                    <h4><span class="primary-color" id="ReUseMsg1"></span><span class="alert-txt" id="ReUseMsg2"></span></h4>
                    <p class="alert-txt" id="ReUseMsg3"></p><br/>
                    <button type="button" class="btn btn-warning mr10" id="deletePreData">아니요</button>
                    <button type="button" class="btn btn-page" id="reRoadPreData">예</button><br/>
                  </div>
                </div>
          </div>
        </div>
      </div>
  </div>
 
   <div class="modal fade in" data-backdrop="static" data-keyboard="false" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false" style="display: none;">
    <!-- <div class="modal-backdrop fade in" style="height: 832px;"></div> -->
      <div class="modal-dialog" style="width:650px;">
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title" id="myModalLabel3">업무 처리 불가 안내</h4>
          </div>
          <div class="modal-body">
                <div class="form-group">
                  <div class="txtC">
                    <!-- <br/><p class="payment-sum" id="ReUseMsg1" ></p> -->
                    <br/><p class="alert-txt" id="reslt"></p><br/>
                    <button type="button" class="btn btn-page" id="close_modal3">닫기</button>
                  </div>
                </div>
          </div>
        </div>
      </div>
  </div>
   <div class="modal fade in" data-backdrop="static" data-keyboard="false" id="myModal4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false" style="display: none;">
    <!-- <div class="modal-backdrop fade in" style="height: 832px;"></div> -->
      <div class="modal-dialog" style="width:540px;">
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title" id="myModalLabel3">고객정보의 개인용도 사용 절대 엄금</h4>
          </div>
          <div class="modal-body">
                <div class="form-group">
                  <div class="txtL">
                         <p class="alert-txt">1. 고객정보를 조회하여 제3자(판매점, 친구, 친지 등)에게 유출ㆍ제공하는 경우</p><br/>
                         <p class="alert-txt">2. 고객정보를 조회하여 업무 이외에 사용시</p><br/>
                         <p class="alert-txt">&nbsp; ⇒  위반시 5년 이하의 징역 또는 5천만원 이하의 벌금 부과(제공받은 자도 처벌)</p>
                         <p class="alert-txt">&nbsp; &nbsp; &nbsp; (관련법규 : 정보통신망 이용촉진 및 정보보호 등에 관한 법률)</p><br/>
                         <p class="alert-txt">&nbsp; ⇒ kt앱을 이용해 고객정보를 조회할 경우 누가,</p>
                         <p class="alert-txt">&nbsp; &nbsp; &nbsp; 어떤 컴퓨터를 통해 언제, 어디서 조회했는지 모든 이력(단순조회 포함)이</p>
                         <p class="alert-txt">&nbsp; &nbsp; &nbsp; 전산으로 자동 관리되므로 고객정보를 불법으로 사용하였을 경우</p>
                         <p class="alert-txt">&nbsp; &nbsp; &nbsp; 당사자를 즉시 적발할 수 있습니다.</p><br/>
                  </div>
                  <div class="txtC">
                    <button type="button" class="btn btn-page" id="close_modal4">닫기</button>
                  </div>
                </div>
          </div>
        </div>
      </div>
  </div>
  <div class="modal" id="mainProgressbar" tabindex="-1" role="dialog" aria-labelledby="progressbarLabel" aria-hidden="false" style="display: none;">
      <div class="modal-backdrop-loader" style="height: 832px;"></div>
      <div class="modal-dialog" style='left:20px;width:200px;'>
           <div class='loader-text'>처리 중 입니다.</div>
           <div class='loader' style='margin-left: 30px;'></div>
      </div>
  </div>
  <form role="form" class="form-horizontal" name="formUser" method="post" >
      	<input type="hidden" id="outVO" name = "outVO" value = '${outVO}' >
      	<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}'  >
      	<input type="hidden" id="firstYn" name = "firstYn" value = '${firstYn}'  >
      	<input type="hidden" id="appleUserId" name = "appleUserId">
  </form>   
 
 	
</div>
<footer class="footer">
  <p class="text-footer">Copyright ⓒ 2017 KT corp. All rights reserved.</p>
</footer>
</body>



		