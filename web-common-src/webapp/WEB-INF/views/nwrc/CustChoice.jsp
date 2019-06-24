<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nwrc/CustChoice.js"></script>

<body class="sub-bg">
<div class="main-container">
  <nav class="navbar navbar-fixed-top">
    <ul class="nav navbar-nav">
      <li>
        <button type="button" class="btn btn-nav" id="goHome">HOME</button>
      </li>
    </ul>
  </nav>
  <div class="sub-header" id="OrderTitle">
    <h1>신규가입</h1>
  </div>
  <div class="marketing">
    <ul class="nav navbar-sub">
      <li><a href="#" class="personally" id="MajorChoice"> <span class="img"> </span>
        <h4>본인</h4>
        </a></li>
      <li><a href="#" class="legal-representative" id="AgentChoice"> <span class="img"></span>
        <h4>법정대리인</h4>
        </a></li>
    </ul>
  </div>
  <form role="form" class="form-horizontal" name="formUser" method="post" >
      	<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}'  >
  </form>
</div>
<footer class="footer">
  <p class="text-footer">Copyright ⓒ 2017 KT corp. All rights reserved.</p>
</footer>
</body>
