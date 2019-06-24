<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--content-->
<div class="container-fluid">
  <div class="row">
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
      <ul class="list-inline scan_area">
        <li><a href="#">신분증 촬영</a></li>
        <li><a href="#">신분증 스캔</a></li>
      </ul>
      <h3>본인인증</h3>
      <div class="tab-area">
        <ul class="nav nav-tabs nav-justified">
          <li class="active"><a data-toggle="tab" href="#menu1" aria-expanded="false">명의자</a></li>
          <li class=""><a data-toggle="tab" href="#menu2" aria-expanded="true">법정대리인</a></li>
        </ul>
      </div>
      <div class="tab-content">
        <div id="menu1" class="tab-pane fade active in">
          <form role="form" class="form-horizontal">
            <div class="form-group">
              <label class="col-sm-2" for="">고객명<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="" placeholder="홍길동">
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2" for="">주민번호<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="" placeholder="000000-0000000">
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2" for="">생년월일<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="" placeholder="0000.00.00">
              </div>
            </div>
          </form>
        </div>
        <div id="menu2" class="tab-pane fade">
          <form role="form" class="form-horizontal">
            <div class="form-group">
              <label class="col-sm-2" for="">대리인 스캔번호<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="" placeholder="000000-0000000">
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2" for="">후견인 스캔번호<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="" placeholder="000000-0000000">
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2" for="">스캔번호<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="" placeholder="0000.00.00">
              </div>
            </div>
          </form>
        </div>
      </div>
      <h3 class="mt40">실명인증 증빙자료</h3>
      <div class="tab-area">
        <ul class="nav nav-tabs nav-justified">
          <li class=""><a data-toggle="tab" href="#menu3" aria-expanded="false">주민등록증</a></li>
          <li class="active"><a data-toggle="tab" href="#menu4" aria-expanded="true">운전면허증</a></li>
          <li class=""><a data-toggle="tab" href="#menu5" aria-expanded="true">대한민국여권</a></li>
          <li class=""><a data-toggle="tab" href="#menu6" aria-expanded="true">국가유공자증</a></li>
          <li class=""><a data-toggle="tab" href="#menu7" aria-expanded="true">장애인등록증</a></li>
        </ul>
      </div>
      <div class="tab-content">
        <div id="menu3" class="tab-pane fade">
          <h3>주민등록증</h3>
        </div>
        <div id="menu4" class="tab-pane fade active in">
          <form role="form" class="form-horizontal">
            <div class="form-group">
              <label class="col-sm-2" for="">면허번호<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="" placeholder="경기0123456789">
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-2" for="">발급일자<span class="essential"></span></label>
              <div class="col-sm-10">
                <input type="text" class="form-control" id="" placeholder="2008.02.02">
              </div>
            </div>
          </form>
        </div>
        <div id="menu5" class="tab-pane fade">
          <h3>대한민국여권</h3>
        </div>
        <div id="menu6" class="tab-pane fade">
          <h3>국가유공자</h3>
        </div>
        <div id="menu7" class="tab-pane fade">
          <h3>장애인등록증</h3>
        </div>
      </div>
    </div>
    <!--/.col-xs-12.col-sm-9-->
  </div>
  <!--/row-->
</div>
<!--/content-->
