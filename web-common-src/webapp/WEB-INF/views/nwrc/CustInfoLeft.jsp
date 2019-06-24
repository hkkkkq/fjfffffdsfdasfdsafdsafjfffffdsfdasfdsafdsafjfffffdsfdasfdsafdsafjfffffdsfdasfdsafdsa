<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta name="viewport" content="width=device-width, user-scalable=no">
<script>
$(document).ready(function() {
	var rcvVo;
	var stepTitleName = "";
	
	rcvVo = JSON.parse($("#inputVo").val());
	stepTitleName = "";
	
	if (rcvVo.trtBaseInfoDTO.itgOderTypeCd == "MNP") {
		stepTitleName = "<h3>번호이동</h3>"; 
	}
	else if (rcvVo.trtBaseInfoDTO.itgOderTypeCd == "NAC") {
		stepTitleName = "<h3>신규가입</h3>"; 
	}
	else if (rcvVo.trtBaseInfoDTO.itgOderTypeCd == "HCN") {
		stepTitleName = "<h3>기기변경</h3>"; 
	}
});
</script>
	
<!--.sidebar-offcanvas-->
<div class="col-sm-3 col-md-2 sidebar" id="sidebar">
  <div id=leftProcessList class="list-group">
    <h3>신규가입</h3>
    <div class="process-check-area"><span class="process-check mr5"></span><span class="pc_txt">본인인증</span></div>
    <div class="list-group-item red_line"></div>
    <div class="process-check-area"><span class="process-check mr5"></span><span class="pc_txt">정보입력</span> </div>
    <div class="list-group-item red_line"></div>
    <div class="process-active-area"><span class="process-active mr5"></span><span class="pa_txt">계약확인</span></div>
	<div class="list-group-item">
      <div class="sub-area">
        <ul>
		  <li class="sub-before">계약정보</li>
          <li class="sub-before">이용약관</li>
          <li class="sub-active">전자서식지</li>
        </ul>
      </div>
    </div>
    <div class="process-normal-area"><span class="process-normal mr5"></span> 완료안내</div>
    <div class="list-group-item"> </div>
  </div>
</div>
<!--/.sidebar-offcanvas-->