<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nnsb/NNSB0100.js"></script>


<!--content-->
<div class="container-fluid">
  <div class="row">
    <!--.col-sm-9-->
    
    <div class="main manage">
		<form role="form" class="form-horizontal" name = "formSch" method = "post">
		<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
    	</form>
      <nav class="navbar navbar-fixed-top">
        <ul class="nav navbar-nav">
          <li class="name" id="usernm"></li>
          <li>
            <button type="button" class="btn btn-nav" id = "logOut">로그아웃</button>
          </li>
        </ul>
      </nav>
      <h3>
        <div class="manage-logo"> <img src="${ctx_res}/images/kt_logo.png" class="img-responsive"></div>
        처리내역
        <div class="floatR">
          <button type="button" class="btn btn-control" id= "reRetv" onClick = "reRetv()">재조회</button>
        </div>
      </h3>
      <table class="table ">
        <thead>
          <tr>
            <th>처리유형</th>
            <!-- <th>계약번호</th> -->
            <th>고객명</th>
            <th>요금제명</th>
            <th>처리일시</th>
            <th>QR코드</th>
          </tr>
        </thead>
        <tbody id = "listBody">
          <tr>
            <td scope="row">신규</td>
            <td>123456789</td>
            <td>LG데이터 선택 65.8</td>
            <td><a href=# class="ta-qrcode" data-toggle="modal" data-target="#modalQrcode">보기</a></td>
            <!--modal-->
		     <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="modalQrcode" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		          <div class="modal-dialog" style="width:300px;">
		             <div class="modal-content">
		               <div class="modal-body-qr">
		                 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" onClick = "frameclose()">×</span></button>
		                  <div class="pop-alert">
		                   <p class="qr-area"> <a href="#" class="thumbnail qr-code"><img id ="qrCodeImg" src="" class="img-responsive" style="width:210px; height:210px;"></a></p>
		                  </div>
		               </div>
		            </div>
				</div>
		     </div>
           <!---->
          </tr>
          <tr>
            <td scope="row">기변</td>
            <td>123456789</td>
            <td>LG데이터 선택 65.8</td>
            <td><a href=# class="ta-qrcode">보기</a></td>
          </tr>
        </tbody>
      </table>
	</div>   
    </div>
    <!--/.col-sm-9-->
  </div>
<!-- /.container -->
<!--/modal-->
<footer class="footer">
  <div class="left"><span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="goClose()"></span> <span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id="goHome3"></span></div>
  <div class="right main">
    <div class="col-sm-2 floatR">
      <button type="button" class="btn btn-page  btn-block " id="goHome3">닫기</button>
    </div>
  </div>
</footer>