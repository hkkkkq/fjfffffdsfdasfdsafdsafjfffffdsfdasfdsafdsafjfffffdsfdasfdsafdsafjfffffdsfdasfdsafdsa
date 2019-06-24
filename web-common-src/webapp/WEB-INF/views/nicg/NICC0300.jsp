<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nicg/NICC0300.js"></script>

<!--content-->
<div class="container-fluid">
  <div class="row">
    <!--.col-sm-9-->
    <div class="main">
      <h3>단말정보
        <div class="warning-info"> 기변전에 사용하던 단말을 지참하여 매장에 방문하셔야 취소 가능합니다. </div>
      </h3>
      <form role="form" class="form-horizontal" name="formSch" method="post" >
      	<input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
      	
        <div class="form-group">
          <label class="col-sm-3" for="">단말모델명(변경전)</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" id="befIntmModelNm" placeholder="">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-3" for="">단말모델명(변경후)</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" id="intmModelNm" placeholder="">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-3" for="">처리일</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" id="efctStDt" placeholder="">
          </div>
        </div>
      </form>
      <h3 class="mt40">가입정보</h3>
      <form role="form" class="form-horizontal">
      <!--   <div class="form-group">
          <label class="col-sm-2" for="">번호</label>
          <div class="col-sm-10 ">
            <input type="text" class="form-control" id="svcNo" placeholder="">
          </div>
        </div> -->
        
        <div class="form-group">
          <label class="col-sm-2" for="">요금제</label>
          <div class="col-sm-2">
          <!--     <button type="button" class="btn btn-primary btn-block" data-toggle="modal" data-target="#myModal" id = "chooProdCd">요금제 선택</button>  -->
              <!--modal-->
              <div class="modal fade" id="myModal" data-backdrop = "static" data-keyboard = "false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" style="width:500px;">
                  <div class="modal-content">
                    <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                      <h4 class="modal-title" id="myModalLabel">요금제 선택</h4>
                    </div>
                    <div class="modal-body" style="height:481px;">
                      <div class="tab-area">
                        <ul class="nav nav-tabs nav-justified">
                          <li class="active"><a data-toggle="tab" href="#menu1" aria-expanded="false">자주사용하는 요금제</a></li>
                          <li class=""><a data-toggle="tab" href="#menu2" aria-expanded="true">요금제 검색</a></li>
                        </ul>
                      </div>
                      <div class="tab-content">
                        <div id="menu1" class="tab-pane fade active in">
                          <ul class="list-grpp" style="border-top:none; margin-top:-20px; height:421px;">
                            <li><a href="#" class="list-grpp-item">LTE스폰서(LTE WARP 어린이/청소년요금)<span class="price">월 1000,000원</span></a></li>
                          </ul>
                        </div>
                        <div id="menu2" class="tab-pane fade ">
                          <div class="form-group">
                            <div class="col-sm-10">
                              <input type="text" class="form-control" id="findProdCd" placeholder="LTE">
                            </div>
                            <div class="col-sm-2">
                              <button type="button" class="btn btn-primary btn-block" id = "btnFindProdCd">검색</button>
                            </div>
                          </div>
                          <div class="search-result">"<span class="primary-color">LTE</span>" 검색결과</div>
                          <ul class="list-grpp" style="height:298px;" id = "ProdCdList">
                            <li><a href="#" class="list-grpp-item">LTE스폰서(LTE WARP 어린이/청소년요금)<span class="price">월 1000,000원</span></a></li>
                          </ul>
                        </div>
                      </div>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-page" id = "btnConfirm" data-dismiss="modal">확인</button>
                    </div>
                  </div>
                </div>
              </div>
              <!--/modal-->
              
              <!--modal-->
              <div class="modal fade" id="prodChkRslt" data-backdrop = "static" data-keyboard = "false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-backdrop fade in" style="height: 832px;"></div>
                <div class="modal-dialog" style="width:500px;">
                  <div class="modal-content">
                    <div class="modal-body">
                      <div class="pop-alert">
                      <!--   <h5 align="center"><b>상품제약사항메세지</b></h5>  -->
                      	<p></p>
                        <div align="center"><img src="${ctx_res}/images/icon_notification.png" class="img-responsive"></div>
                        <br>
                     <!--      <p align="left" class="alert-txt" id="prodChkRsltTxt"></p>  -->
                        
                        <table border="1" id="prodPopMsg2" class="">
                        </table>
                        
                        <div align="center" class="alert-btn">
                          <button type="button" class="btn btn-page" id="btnProdConfirm">확인</button>
                        </div>
                        <p></p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <!--/modal-->  
              
            </div>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="prodNm" placeholder="선택한 요금제" value="LTE 데이터 선택 43.8" readonly>
            </div>
            <div class="col-sm-2">
              <input type="hidden" class="form-control txtR" id="prodIndcChage" placeholder="원" readonly>
            </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2" for="">할부기간</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="intmInslMonsNum" placeholder="">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2" for="">약정기간</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="hccEngtPerdMonsNum" placeholder="">
          </div>
        </div>
        
        <div class="form-group">
          <label class="col-sm-2" for="">지원금</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="hccSaleEngtOptnNm" placeholder=" ">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 mb10" for=""> KT 멤버십 </label>
          <div class="col-sm-10"> <span class="form-group-addon" id="basic-addon1">사용한 포인트</span>
            <input type="text" class="form-control txtR" id="intmSettlMlgAmt" placeholder="">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2" for="">위약금</label>
          <div class="col-sm-10">
            <input type="text" class="form-control txtR" id="penlt" placeholder="">
          </div>
        </div>
      </form>
    </div>
    <!--/.col-sm-9-->
  </div>
  <!--/row-->
</div>
<!-- /.container -->


	
<footer class="footer">
  <div class="left">
  	<span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="javascript:goPrev();"></span> 
  	<span><img src="${ctx_res}/images/left_home.png" class="img-responsive"  id="goHome"></span>
  </div>
  <div class="right main">
    <div class="col-sm-2 floatR">
      <button type="button" id="btnNext" class="btn btn-page  btn-block ">다음</button>
    </div>
  </div>
 
 

  
</footer>

