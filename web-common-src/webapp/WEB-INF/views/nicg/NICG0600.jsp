<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nicg/NICG0600.js"></script>

<body class="customer">
<!--content-->
<div class="container-fluid">
  <div class="row">
    <!--.main-->
    <div class="main">
      <h3>계약정보
      	<div class="warning-info"> 고객님께서 직접 계약내용을 확인하시고, 약관동의 해주세요. </div>
      </h3>
	<form role="form" class="form-horizontal" name = "formSch" method = "post">
		<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
	  <br/>	
      <dl class="dl-horizontal contract_info ">
      <!-- 
        <dt>이메일주소</dt>
        <dd>qwer@aaaa.com</dd>
        <dt>청구지주소</dt>
        <dd>12345 경기도 성남시 분당구 성남대로 9903(정자동, 대림아크로텔) 000동 000호</dd>
        <dt>납부자정보</dt>
        <dd>홍길동 / 1976.09.12 / 국민은행 33344020999 / 매월 21일</dd>   -->
        
        <dt>단말 모델명</dt>
        <dd id="intmModelNm">A172-64</dd>
        <dt>전화번호</dt>
        <dd id="svcNo">010-0000-0000</dd>
        <dt>요금제</dt>
        <dd id="prodNm">LTE스폰서3 / 월87,000원</dd>

        <dl class="half mr4p">
          <dt>할부기간</dt>
          <dd id="totInslMonsNum">24개월</dd>
        </dl>
        <dl class="half">
          <dt>약정기간</dt>
          <dd id="engtPerdMonsNum">24개월</dd>
		</dl>
        <dl class="half mr4p">
          <dt>지원금</dt>
          <dd id="saleEngtTypeNm">단말지원금(심플코스)</dd>
        </dl>
        <dl class="half" id="mlgUseValClass">
          <dt>KT멤버십</dt>
          <dd id="mlgUseVal">5,000p 사용</dd>
        </dl>
      </dl>
      <br/>
      <div class="calculate-area" style="margin-left:-45px;">
		<div class="calculate-box">
        	<div class="calculate col3 mrp5">
          		<h4 class="mb20"><span class="icon">A</span> 통신요금
	          		<p id="sprodChage" class="sum">월 <span class="primary-color pt20">87,890</span>원</p>
          		</h4>
          		<hr>
          		<dl class="dl-horizontal calculate" style="min-height:102px;">
            		<dt id="dprodChage">월 정액요금</dt>
		            <dd id="lprodChage">0원</dd>
		            <dt id="dcSuprtAmtChageTx"> └ 월 요금할인 </dt>
		            <dd id="dcSuprtAmtChage"><span class="primary-color">-0</span>원</dd>
		            <dt id="dcSuprtAmtChageTx2" class="ml20">   </dt>
		            <dd id=""></dd>
		            <dt id="dcSuprtAmtChageDcTx"> └ 25% 요금할인 </dt>
		            <dd id="dcSuprtAmtChageDc"><span class="primary-color">-0</span>원</dd>
		            <dt id="dcSuprtAmtChageDcTx2" class="ml20">   </dt>
		            <dd id=""></dd>
          		</dl>
          	</div>
        	<div class="col3 calculate mrp5">
          		<h4 class="mb20"><span class="icon">B</span> 단말구매가
          			<p class="sum" id="monthInslAmt">월 <span class="primary-color pt20">45,600</span>원</p>
          		</h4>
          		<hr>
          		<dl class="dl-horizontal calculate">
		            <dt id="dintmSaleAmt">출고가</dt>
		            <dd id="lintmSaleAmt">0원</dd>
		            <dt id="dfirstDcSuprtTotAmt">└ 지원금</dt>
		            <dd id="firstDcSuprtTotAmt"><span class="primary-color">-0</span>원</dd>
		            <dt id="dicgPointAmt">└ 기변포인트</dt>
		            <dd id="licgPointAmt"><span class="primary-color">-0</span>원</dd>
		            <dt id="dsimplAcuAmt">└ 심플적립</dt>
		            <dd id="lsimplAcuAmt"><span class="primary-color">-0</span>원</dd>
		            <dt id="dmlgUseVal">└ KT멤버십포인트</dt>
		            <dd id="lmlgUseVal"><span class="primary-color">-0</span>원</dd>
		            <dt id="dtotInslAmt">총할부원금</dt>
		            <dd id="totInslAmt">0원</dd>
		            <dt id="dinslIntrTotAmt">총할부수수료</dt>
		            <dd id="inslIntrTotAmt">0원</dd>
		       <!-- <dt id="dinslIntrRate">(24개월 할부 연 0%)</dt>
		            <dd id="inslIntrRate"></dd> -->
		            <dt id="dusimAmt"><span class="normal-info"><span class="primary-color">*</span>유심비 <span class="primary-color">8,800원 별도</span></span></dt>
		            <dd id="usimAmt"></dd>
          		</dl>
        	</div>
        	
        	<div class="calculate col3">
        		<h4 class="mb20"><span class="icon">A</span>+<span class="icon">B</span> 월 납부액<span class="normal-info"> (부가세포함)</span></h4>
        		<hr>
          		<p class="payment-sum" id="payment_sum">월<span class="primary-color pt36">94,990</span>원</p>
        	</div>
      		</div>
      </div>
      
      </form>
      
    </div>
    <!--/.main-->
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
		<span><img src="${ctx_res}/images/left_home.png" class="img-responsive"   id="goHome"></span>
	</div>
	<div class="right main">
		<div class="col-sm-2 floatR">
			<button type="button" id="btnNext" class="btn btn-page  btn-block " onClick="goNext()">다음</button>
		</div>
	</div>
</footer>