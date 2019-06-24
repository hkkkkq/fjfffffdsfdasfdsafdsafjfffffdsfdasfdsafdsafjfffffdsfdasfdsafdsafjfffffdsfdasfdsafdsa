<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>

<script src="${ctx_res}/js/nnsb/NNSB0300.js"></script>

<div class="container-fluid">
  <div class="row">
    <!--.main-->
    <div class="main">
      <h3>청구정보<div class="question-light"> <img src="${ctx_res}/images/Info-Light.png" class="img-responsive" data-toggle="modal" data-target="#myModal2"></div>     	          
            <!--modal-->
            <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
              <div class="modal-dialog" style="width:350px;">
                <div class="modal-content">
                  <div class="modal-body">
                    <div class="pop-alert">
                      <h4>청구정보</h4>
                      <p class="alert-txt" id="mlgUseAlert" align = "left">우편명세서를 원할 경우 고객센터(114번)로 신청해 주시기 바랍니다. <br><br> 이메일,모바일(MMS),스마트(앱)을 선택하시면 매월 KT멤버십 200점이 누적 합니다.</p>
                      <div class="alert-btn">
                        <button type="button" class="btn btn-page" data-dismiss="modal" aria-label="Close">확인</button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!--/modal-->
       </h3>
      <div class="tab-area">
        <ul class="nav nav-tabs nav-justified" id = "acrndDspTypeCd">
          <li class="active"><a data-toggle="tab" href="#menu1" aria-expanded="false" value = "CB" onClick = "essensialAdd()">이메일</a></li>
          <li class=""><a data-toggle="tab" href="#menu2" aria-expanded="true" value = "MB" onClick = "essensialRemove()">모바일(MMS)</a></li>
          <li class=""><a data-toggle="tab" href="#menu3" aria-expanded="true" value = "AP" onClick = "essensialRemove()">스마트(앱)</a></li>
        </ul>
      </div>
      <div class="tab-content">
        <div id="menu1" class="tab-pane fade active in">
          <form role="form" class="form-horizontal" name = "formSch" method = "post">
          <input type = "hidden" name = "inputVo" id = "inputVo" value = '${inputVo}' >
            <div class="form-group">
              <label class="col-sm-2" for="" id = "emailaddress">이메일 주소<span class="essential"></span> </label>
              <div class="col-sm-4">
                <input type="text" class="form-control" id="email" placeholder="">
              </div>
              <div class="col-sm-1" style="width:3.33333333%"> <span class="at">@</span> </div>
              <div class="col-sm-5" style="width:46.66666667%">
                <input type="text" class="form-control" id="email2" placeholder="">
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2" for="">청구지 주소<span class="essential"></span></label>
              <div class="col-sm-2 ">
                <button type="button" class="btn btn-primary btn-block" data-toggle="modal" data-target="#myModal1" id = "chAddress">주소 찾기</button>
                <!--modal-->
                <div class="modal fade" data-backdrop = "static" data-keyboard = "false" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                  <div class="modal-dialog" style="width:750px;">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                        <h4 class="modal-title" id="myModalLabel">주소 찾기</h4>
                      </div>
                      <div class="modal-body" style="height:563px;">
                        <div class="tab-area">
                          <ul class="nav nav-tabs nav-justified" id = "addrModalChoo">
                            <li class="active"><a data-toggle="tab" href="#menu7" aria-expanded="true">도로명주소</a></li>
                            <li class=""><a data-toggle="tab" href="#menu8" aria-expanded="false">지번주소</a></li>
                          </ul>
                        </div>
                        <div class="tab-content">
                          <div id="menu7" class="tab-pane fade active in">
                            <div class="form-inline">
                              <div class="btn-group" id = "roadOrBuilding" style = "display : none;">
                                <button type="button" class="btn btn-select dropdown-toggle" data-toggle="dropdown" aria-expanded="false" style="width:100px;">도로명<span class="caret"></span></button>
                                <ul class="dropdown-menu" role="menu">
                                  <li><a href="#">도로명</a></li>
                                </ul>
                              </div>
                              <div class="btn-group" id = "sidoNm">
                                <button type="button" class="btn btn-select dropdown-toggle" data-toggle="dropdown" aria-expanded="false" style="width:170px;">선택<span class="caret"></span></button>
                                <ul class="dropdown-menu" role="menu">
                                </ul>
                              </div>
                              <div class="btn-group" id = "sggNm">
                                <button type="button" class="btn btn-select dropdown-toggle" data-toggle="dropdown" aria-expanded="false" style="width:207px;">선택<span class="caret"></span></button>
                                <ul class="dropdown-menu" role="menu" style="height:272px;">
                                </ul>
                              </div>
                              <div class="form-group">
                                <input type="text" class="form-control" id="roadNm" placeholder="예)불정로" style="width:120px;">
                              </div>
                              <div class="form-group">
                                <input type="text" class="form-control" id="bldgNo" placeholder="100" style="width:70px;" pattern = "\d*">
                              </div>
                              <button type="button" class="btn btn-primary" style="width:70px;" id = "searchAddr">검색</button>
                            </div>
                            <p class="post-info">지역 선택 후 도로명을 입력하세요.</p>
                            <ul class="list-post" style="height:380px;" id = "addrList">
                            </ul>
                            <ul class="list-post-detail" style = "display : none;" id = "addrDetail">
                              <li><span class="list-post-item col20">우편번호</span> <span class="list-post-item col80"></span></li>
                              <li><span class="list-post-item col20">기본주소</span> <span class="list-post-item col80"></span></li>
                              <li style=""><span class="list-post-item col20">상세주소</span>
                              <span class="list-post-item col80">
                                <input type="text" class="form-control" id="inputAdrDtl" placeholder="상세주소">
                              </span></li>
                            </ul>
                          </div>
                          <div id="menu8" class="tab-pane fade">
                            <div class="form-group">
                              <div class="col-sm-10">
                                <input type="text" class="form-control" id="lotNoSearch" placeholder="예) 청담동, 논현동, 사천읍, 고양면">
                              </div>
                              <div class="col-sm-2">
                                <button type="button" class="btn btn-primary btn-block" id = "searchAddr2">검색</button>
                              </div>
                            </div>
                            <p class="post-info">읍/면/동을 입력하세요.</p>
                            <ul class="list-post" style="height:370px;" id = "addrList2">
                            </ul>
                            <ul class="list-post-detail" style = "display : none;" id = "addrDetail2">
                              <li><span class="list-post-item col20">우편번호</span> <span class="list-post-item col80"></span></li>
                              <li><span class="list-post-item col20">기본주소</span> <span class="list-post-item col80"></span></li>
                              <li style=""><span class="list-post-item col20">상세주소</span>
                              <span class="list-post-item col80">
                                <input type="text" class="form-control" id="inputAdrDtl2" placeholder="상세주소" value = "">
                             </span></li>
                           </ul>
                          </div>
                        </div>
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-page" id = "confirmAddr">확인</button>
                      </div>
                    </div>
                  </div>
                </div>
                <!--/modal-->
              </div>
              <div class="col-sm-8">
                <input type="text" class="form-control" id="roadnAdrBasSbst" placeholder="" readonly>
              </div>
              <div class="col-sm-10 col-sm-offset-2 mt10">
                <input type="text" class="form-control" id="roadnAdrDtlSbst" placeholder="상세주소" readonly>
              </div>
            </div>
          </form>
        </div>
       <!-- <div id="menu2" class="tab-pane fade">
          <form role="form" class="form-horizontal">
            <div class="form-group">
              <label class="col-sm-2" for="">명의자 번호</label>
              <div class="col-sm-8 ">
                <input type="text" class="form-control" id="" placeholder="010-0000-0000">
              </div>
              <div class="col-sm-2 ">
                <button type="submit" class="btn btn-primary btn-block">이동전화인증</button>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2" for="">모바일 번호</label>
              <div class="col-sm-8 ">
                <input type="text" class="form-control" id="" placeholder="010-0000-0000">
              </div>
              <div class="col-sm-2 ">
                <button type="submit" class="btn btn-primary btn-block">이동전화인증</button>
              </div>
            </div>
          </form>
        </div>
        <div id="menu3" class="tab-pane fade">
          <form role="form" class="form-horizontal">
            <div class="form-group">
              <label class="col-sm-2" for="">명의자 번호</label>
              <div class="col-sm-8 ">
                <input type="text" class="form-control" id="" placeholder="010-0000-0000">
              </div>
              <div class="col-sm-2 ">
                <button type="submit" class="btn btn-primary btn-block">이동전화인증</button>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2" for="">모바일 번호</label>
              <div class="col-sm-8 ">
                <input type="text" class="form-control" id="" placeholder="010-0000-0000">
              </div>
              <div class="col-sm-2 ">
                <button type="submit" class="btn btn-primary btn-block">이동전화인증</button>
              </div>
            </div>
          </form>
        </div> -->
      </div>
      <h3 class="mt50">납부방법</h3>
      <div class="tab-area">
        <ul class="nav nav-tabs nav-justified" id = "payMethCd">
          <li class="active"><a data-toggle="tab" href="#menu4" aria-expanded="false">자동이체</a></li>
          <li class=""><a data-toggle="tab" href="#menu5" aria-expanded="true">신용카드</a></li>
          <li class=""><a data-toggle="tab" href="#menu6" aria-expanded="true">지로(우편)</a></li>
        </ul>
      </div>
      <div class="tab-content">
        <div id="menu4" class="tab-pane fade active in">
          <form role="form" class="form-horizontal">
            <div class="form-group">
              <label class="col-sm-2" for="">납부고객명<span class="essential"></span></label>
              <div class="col-sm-5">
                <input type="text" class="form-control" id="payCustNm" placeholder="홍길동">
              </div>
              <label class="col-sm-1 pay" for="">납부자</label>
              <div class="col-sm-2">
                <button type="button" class="btn btn-default  btn-block active" id = "payerSelf" onClick = "goPayerSelf()">본인</button>
              </div>
              <div class="col-sm-2">
                <button type="button" class="btn btn-default  btn-block" id = "payerLegal" onClick = "goPayerLegal()">타인</button>
              </div>
              <div class="payer col-sm-5" id = "payerType" style = "display : none;">
                <div class="col-sm-6">
                  <button type="button" id="legalRepre" class="btn btn-default  btn-block active" value = "02">법정대리인</button>
                </div>
                <div class="col-sm-3">
                  <button type="button" id="legalFamily" class="btn btn-default  btn-block " value = "03">가족</button>
                </div>
                <div class="col-sm-3">
                  <button type="button" id="legalOthers" class="btn btn-default  btn-block " value = "05">기타</button>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2"  for="">생년월일<span class="essential"></span></label>
              <div class="col-sm-5">
                <input type="text" class="form-control" id="parCustBthDay" placeholder="2010.10.01" pattern = "\d*">
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2" for="">계좌정보<span class="essential"></span></label>
              <div class="col-sm-2">
                <button type="button" class="btn btn-primary btn-block" data-toggle="modal" data-target="#myModal" id = "bank">은행 선택</button>
                <!-- Modal -->
                <div class="modal fade in" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                  <div class="modal-dialog" style="width:775px;">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                        <h4 class="modal-title" id="myModalLabel">은행 선택</h4>
                      </div>
                      <div class="modal-body bank">
                        <div class="tab-area-popup">
                          <ul class="nav nav-tabs-popup">
                            <li class="active"><a data-toggle="tab" href="#popup-menu1" aria-expanded="true">은행</a></li>
                            <li class=""><a data-toggle="tab" href="#popup-menu2" aria-expanded="false">증권</a></li>
                          </ul>
                        </div>
                        <div class="tab-content" id = "bankTab">
                          <div id="popup-menu1" class="tab-pane fade active in">
                            <div class="bank-area" style="height:355px;">
                              <div class="row show-grid">
                              </div>
                            </div>
                          </div>
                          <div id="popup-menu2" class="tab-pane fade">
                            <div class="bank-area" style="height:355px;">
                              <div class="row show-grid">
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-page" id = "bankModalConfirm" data-dismiss="modal">확인</button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-3">
                <input type="text" class="form-control" id="payMethIdfyCdNm" placeholder="" disabled>
                <input type="hidden" id = "payMethIdfyCd">
              </div>
              <div class="col-sm-5">
                <input type="text" class="form-control" id="payMethIdfyNo" placeholder="계좌번호'-' 제외하고 숫자만 입력" pattern = "\d*" disabled>
              </div>
            </div> 
            <div class="form-group" id="payDateTab">
              <label class="col-sm-2" for="">납부일<span class="essential"></span></label>
              <div class="col-sm-3 col-3">
                <button type="button" class="btn btn-default  btn-block active" id = "payDateTab1" value = "21">매월21일</button>
              </div>
              <div class="col-sm-3 col-3">
                <button type="button" class="btn btn-default  btn-block " id = "payDateTab2" value = "25">매월25일</button>
              </div>
              <div class="col-sm-4 col-3">
                <button type="button" class="btn btn-default  btn-block " id = "payDateTab3" value = "27">매월27일</button>
              </div>
            </div>
          </form>
        </div>
        <div id="menu5" class="tab-pane fade">
          <form role="form" class="form-horizontal">
            <div class="form-group">
              <label class="col-sm-2" for="">납부고객명<span class="essential"></span></label>
              <div class="col-sm-5">
                <input type="text" class="form-control" id="payCustNm2" placeholder="홍길동">
              </div>
              <label class="col-sm-1 pay" for="">납부자</label>
              <div class="col-sm-2">
                <button type="button" class="btn btn-default  btn-block active" id = "payerSelf2" onClick = "goPayerSelf2()">본인</button>
              </div>
              <div class="col-sm-2">
                <button type="button" class="btn btn-default  btn-block" id = "payerLegal2" onClick = "goPayerLegal2()">타인</button>
              </div>
              <div class="payer col-sm-5" id = "payerType2" style = "display : none;">
                <div class="col-sm-6">
                  <button type="button" id="legalRepre2" class="btn btn-default  btn-block active" value = "02">법정대리인</button>
                </div>
                <div class="col-sm-3">
                  <button type="button" id="legalFamily2" class="btn btn-default  btn-block " value = "03">가족</button>
                </div>
                <div class="col-sm-3">
                  <button type="button" id="legalOthers2" class="btn btn-default  btn-block " value = "05">기타</button>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2" for="">생년월일<span class="essential"></span></label>
              <div class="col-sm-5">
                <input type="text" class="form-control" id="parCustBthDay2" placeholder="2010.10.01" pattern = "\d*">
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2" for="">카드정보<span class="essential"></span></label>
              <div class="col-sm-7">
                <input type="text" class="form-control" id="cardNo" placeholder="카드번호" pattern = "\d*">
              </div>
              <div class="col-sm-3">
                <input type="text" class="form-control" id="cardEfct" placeholder="MM/YY" pattern = "\d*">
              </div>
              <!-- 
               <div class="col-sm-2">
                <button type="button" class="btn btn-primary btn-block">카드인증</button>
              </div> -->
            </div>
            <div class="form-group" id = "cardPayDateTab">
              <label class="col-sm-2" for="">납부일<span class="essential"></span></label>
              <div class="col-sm-5">
                <button type="button" class="btn btn-default  btn-block active" value = "01" id = "cardPayDateTab1">1회차 (매월 15일)</button>
              </div>
              <div class="col-sm-5">
                <button type="button" class="btn btn-default  btn-block " value = "02" id = "cardPayDateTab2">2회차 (매월말일)</button>
              </div>
            </div>
          </form>
        </div>
        <div id="menu6" class="tab-pane fade">
          <div class="form-group">
            <label class="col-sm-2" for="">납부일<span class="essential"></span></label>
              <label class="col-sm-10" id="">지로 납부시 매월말일에 청구됩니다.</label>
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
      <button type="button" class="btn btn-page  btn-block " id = "btnGoNext" onClick = "goNext()">다음</button>
    </div>
  </div>
</footer>
