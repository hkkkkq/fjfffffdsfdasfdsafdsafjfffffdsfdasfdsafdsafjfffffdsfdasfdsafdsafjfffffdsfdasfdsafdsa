<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nnsb/NNSB0600.js"></script>

<body class="customer">
<!--content-->
<div class="container-fluid">
	<div class="row">

		<!--.col-sm-9-->
		<div class="main"> 
		<form role="form" class="form-horizontal" name = "formSch" method = "post">
		<input type = "hidden" name = "inputVo" id = "inputVo" value = '${inputVo}' >
     	</form>
      <h3>계약정보
        <div class="warning-info"> 고객님께서 직접 계약내용을 확인하시고, 약관동의 해주세요. </div>
      </h3>
      <dl class="dl-horizontal contract_info ">
        <dt id = "emailTab">이메일주소</dt>
        <dd id = "email"></dd>
        <dt>청구지주소</dt>
        <dd id = "billAdr"></dd>
        <dt>납부자정보</dt>
        <dd id = "billCustInfo"></dd>
        <dt>단말 모델명</dt>
        <dd id = "intmAliasNm"></dd>
<!--         <dl class="half mr4p"> -->
          <dt>전화번호</dt>
          <dd id = "svcNo"></dd>
<!--         </dl> -->
          <dt>요금제</dt>
          <dd id = "prodInfo"></dd>
        <dl class="half mr4p">
          <dt>할부기간</dt>
          <dd id = "totInslMonsNum"></dd>
        </dl>
<!--         <dl class="half mr4p"> -->
<!--           <dt>요금제</dt> -->
<!--           <dd id = "prodInfo"></dd> -->
<!--         </dl> -->
        <dl class="half">
          <dt>약정기간</dt>
          <dd id = "engtPerdMonsNum"></dd>
        </dl>
        <dl class="half mr4p">
          <dt>지원금</dt>
          <dd id = "saleEngtOptnCd"></dd>
        </dl>
        <dl class="half" id = "mlgUseValClass">
          <dt>KT멤버십</dt>
          <dd id = "mlgUseVal"></dd>
        </dl>
      </dl>
      <div class="calculate-area" style="margin-left:-45px;">
        <div class="calculate-box">
          <div class="calculate col3 mrp5">
            <h4 class="mb20"><span class="icon">A</span> 통신요금
              <p class="sum">월 <span class="primary-color pt20" id="totalProdAmt">0</span>원</p>
            </h4>
            <hr>
            <dl class="dl-horizontal calculate" style="min-height:102px;">
              <dt id="prodNm">월 정액요금</dt>
              <dd id="prodIndcChage">-0원</dd>
              <dt  id="pm"> └ 25% 요금할인 </dt>
              <dd id = "pmAmt"><span class="primary-color" >-0</span>원</dd>
              <dt class="ml20" id = "engtPerdMonsNum2">(24개월 약정)</dt>
              <dd></dd>
            </dl>
          </div>
          <div class="col3 calculate mrp5">
            <h4 class="mb20"><span class="icon">B</span> 단말구매가
              <p class="sum">월 <span class="primary-color pt20" id = "totalInstaAmt">0</span>원</p>
            </h4>
            <hr>
            <dl class="dl-horizontal calculate">
              <dt id = "intmSaleAmtClass">출고가</dt>
              <dd id="intmSaleAmt">0원</dd>
              <dt id = "firstDcSuprtTotAmtClass">└ 단말지원금 할인</dt>
              <dd id="firstDcSuprtTotAmt"><span class="primary-color">-0</span>원</dd>
              <dt id = "mlgUseVal2Class">└ KT멤버십포인트</dt>
              <dd id="mlgUseVal2"><span class="primary-color">-0</span>원</dd>
              <dt id = "instaAmtClass">총 할부원금</dt>
              <dd id="instaAmt">0원</dd>
              <dt id = "inslIntrTotAmtClass">총 할부수수료</dt>
              <dd id="inslIntrTotAmt">0원</dd>
<!--               <dt>총 할부원금</dt> -->
<!--               <dd>399,600원</dd> -->
<!--               <dt>총 할부수수료</dt> -->
<!--               <dd>29,150원</dd> -->
<!--               <dt id="calInslIntrRate"></dt> -->
              <dt id = "usimSaleAmtClass"><span class="normal-info"><span class="primary-color mark">*</span>유심비 <span class="primary-color">8,800원 별도</span></span></dt>
            </dl>
          </div>
          <div class="calculate col3">
            <h4 class="mb20"><span class="icon">A</span>+<span class="icon">B</span> 월 납부액<span class="normal-info"> (부가세포함)</span></h4>
            <hr>
            <p class="payment-sum" id="totalIAmt">월<span class="primary-color pt36">0</span>원</p>
          </div>
        </div>
      </div>
    </div>
    <!--/.main-->
  </div>
  <!--/row-->
</div>
<!-- /.container -->
<footer class="footer">
  <div class="left"><span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="goPrev()"></span> <span><img src="${ctx_res}/images/left_home.png" class="img-responsive" id="goHome"></span></div>
  <div class="right main">
    <div class="col-sm-2 floatR">
      <button type="button" class="btn btn-page  btn-block " id = "btnGoNext" onClick="goNext()">다음</button>
    </div>
  </div>
</footer>
</body>