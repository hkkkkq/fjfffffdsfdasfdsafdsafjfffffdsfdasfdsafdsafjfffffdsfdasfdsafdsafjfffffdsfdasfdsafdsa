<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<body class="error-bg">
<!--content-->
<div class="container-fluid">
  <div class="row">
    <!--.col-sm-9-->
    <div class="main">
      <div class="page-header-error">
			<h2>Ooooops! Sorrrry!</h2>
			<div class="error">403
				<p>요청한 화면에 대한 접속 권한이 없습니다.</p>
				<p class="cause">요청한 화면의 주소(URL)에 대한 접속 권한이 없습니다.<br>
				아래 버튼을 이용하여 홈 화면으로 이동할 수 있습니다.</p>
			</div>
  		</div>
    </div>
    <!--/.col-sm-9-->
  </div>
  <form role="form" class="form-horizontal" name = "formSch" method = "post">
		<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
  </form>
  <!--/row-->
</div>
<!-- /.container -->
<footer class="footer">
  <div class="right main">
    <div class="col-sm-2 floatR">
      <button type="button" class="btn btn-page  btn-block" id="goHome3">홈</button>
    </div>
  </div>
</footer>
</body>
<!-- 
<div class="panel">
	<article>
		<section>
			<section>
				<h1><span class="label label-essential">해당 화면 접속 권한이 없습니다.</span></h1>
				
			</section>
		</section>
	</article>
	<footer>
		<p>Copyright © 2017 kt All Rights Reserved.</p>
	</footer>
</div>
		
<footer></footer>
 -->
 		
<script>
	$(function(){
		$('button').button();
		$('.dialog').dialog({
			autoOpen: true,
			resizable: false,
			width: 640,
			height: 'auto',
			modal: false,
			draggable: false,
			closeOnEscape: false
		});
		

		//windows resize fix
		$(window).bind('resize',function() {
			
			//dialog reposition
			if($('.dialog').hasClass('ui-dialog-content')){
				$('.dialog').dialog({ 
					position: { my:'center',at:'center',of:window }
				});
			}
			
		}).trigger('resize');
		
		//windows scroll fix
		$(window).bind('scroll',function() {

			//dialog reposition
			if($('.dialog').hasClass('ui-dialog-content')){
				$('.dialog').dialog({ 
					position: { my:'center',at:'center',of:window }
				});
			}
			
		}).trigger('scroll');
		
		$('#authReqBtn').click(function(){
			location.replace(E2E.getCtx() + '/userapyreq/pop');
		});
		
		$('#homeBtn').click(function(){
			location.replace(E2E.getCtx() + '/');	
		});
	});
</script>
