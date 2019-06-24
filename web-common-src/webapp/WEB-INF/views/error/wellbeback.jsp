<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<div class="dialog">
	<article>
		<section>
			<section>
				<div><span>!</span></div>
			</section>
			<section>
				<h1><span>서비스 일시중지 안내</span></h1>
				<p>
				    <strong style="color:red;">${AT_WORK_REASON}</strong>으로 인하여 아래와 같이 <strong style="color:red;">서비스가 일시 중지될 예정</strong>입니다.<br/> 
				    이용에 불편을 드려 대단히 죄송합니다.
				</p>
				<br/>
				<p style="padding: 0;"><strong>작업일시</strong> : ${AT_WORK_DATE}<br/>
				<strong>작업사유</strong> : ${AT_WORK_REASON}</p>
			</section>
			
		</section>
	</article>
	<footer>
		<p>Copyright © 2017 kt All Rights Reserved.</p>
	</footer>
</div>

<footer></footer>
		
<script>
	$(function(){
		$('#goMainBtn').click(function(){
			location.replace('${ctx}');
			return false;
		});
		
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
		
	});
</script>