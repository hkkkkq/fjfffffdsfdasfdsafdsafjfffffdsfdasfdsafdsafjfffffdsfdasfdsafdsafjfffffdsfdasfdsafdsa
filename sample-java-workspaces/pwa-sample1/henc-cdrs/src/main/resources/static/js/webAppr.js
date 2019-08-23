/**
 * 전자결재 WebService
 * var webAppr = new WebAppr("CONSTRUCT_PCMS_SUBCON", "1", sourceName);
 * 
 * @param p_service
 * @param p_step
 * @param p_source_name
 * @returns {WebAppr}
 */
//local
//var webApprUri = "http://localhost:80";

//Test
var webApprUri = "http://172.17.211.217:8081";


function WebAppr(p_service, p_step, p_source_name) {  
    var err = false; 
    var message = null;
    var columns = new Array();
    var rowset = new Array();
    var edms_server_uri = null;
    
    var date = new Date();
    var year  = date.getYear();
    var month = date.getMonth() + 1;
    var day   = date.getDate();
    
    if( year < 1000 ) {
        year += 1900;
    }
    if( month < 10 ) {
        month = "0" + month;
    }
    if( day < 10 ) {
        day = "0" + day;
    }
    var apprDate = "" + year + month+ day;
    
    var _struct_ = {
        "service" : p_service,
        "service_seq" : 1,
        "apprType" : "",
        "step" : p_step,
        "title" : "",
        "linkTable" : "",
        "colSeparator" : "@",
        "rowSeparator" : "|",
        "count" : 0,
        "cols" : [],
        "rows" : [],
        "rowset": "",
        "jobGu" : "",
        "CodeGu" : "",
        "formScreen" : "",
        "erp" : true,
        "edms" : true,
        "apprNo": "",
        "apprDate" : apprDate,
        "apprDeptCode" : "",
        "apprDeptName" : "",
        "apprLine" : "",
        "apprFlow" : "",
        "apprCode" : "",
        "attach" : false,
        "docGrade" : "",
        "docSaveTerm" : "",
        "edmsDocFolderId": "",
        "edmsDocFolderPath" : "",
        "edmsDocClass" : "",
        "sourceName" : p_source_name,
        "regDeptCode" : "",
        "regDate": "",
        "remoteAddr": "",
        "link_id": "",
        "CustomerTrxId": "",
        "InvoiceSource": "",
        "edms_server_uri": edms_server_uri,
        "common" : "",
        "file_id" : "",
        "chg_cst_file_id" : "",
        "fileGb" : "",
        "invoice_no" : "",
        "modno" : "",
        "layout" : "vertical",
        "receivers" : "",
        "birtParam" : "",
        "drafter" : ""
        
    };
    
    this.debug = true;
    
    this.getService = function() {
        return _struct_.service;
    };
    
    this.setServiceSeq = function(p_seq) {
        _struct_.service_seq = p_seq;
    };
    
    this.getLinkTable = function() {
        return _struct_.linkTable;
    };
    
    this.setCommon = function(p_common) {
        _struct_.common = p_common;
    };
    
    this.setInvoiceNo = function(p_invoiceNo) {
        _struct_.invoice_no = p_invoiceNo;
    };
    
    /**
     * 결재처리가 되는 데이터의 원천 테이블명을 설정한다.<br><br>
     * 사용예) SAC01001 또는 OSM200T<br><br>
     * 
     * @param String 테이블 소유자 명
     * @param String 테이블 명
     */
    
    this.setFileGb = function(p_fileGb) {   
        _struct_.fileGb = p_fileGb;         
    };                                      
                                            
    this.getFileGb = function() {           
        return _struct_.fileGb;
    };
    
    this.setFileId = function(p_fileId) {
        _struct_.file_id = p_fileId;
    };                                      
                                            
    this.getFileId = function() {           
        return _struct_.file_id;             
    };

    this.setChgCstFileId = function(p__chg_cst_fileId) {
        _struct_.chg_cst_file_id = p__chg_cst_fileId;
    };

    this.getChgCstFileId = function() {
        return _struct_.chg_cst_file_id;
    };
    
    
    this.setModNo = function(p_modno) {   
        _struct_.modno = p_modno;         
    };                                      
                                            
    this.getModNo = function() {           
        return _struct_.modno;             
    };
    
    this.setLinkTable = function(p_table_name) {
      _struct_.linkTable = p_table_name;
    };

    this.getFormScreen = function() {
        return _struct_.formScreen;
    };
    
    this.setFormScreen = function(p_form_screen) {
        _struct_.formScreen = p_form_screen;
    };
    
    this.setApprNo = function(p_apprNo) {
        _struct_.apprNo = p_apprNo;
    };
    
    this.getApprNo = function() {
        return _struct_.apprNo;
    };
    
    this.setLayOut = function(p_layOut) {
        _struct_.layout = p_layOut;
    };
    
    this.getLayOut = function() {
        return _struct_.layout;
    };
    
    this.setRegDeptCode = function(p_deptCode) {
        _struct_.regDeptCode = p_deptCode;
    };
    
    this.setRegDate = function(p_date) {
        _struct_.regDate = p_date;
    };
    
    this.setApprDate = function(p_date) {
        _struct_.apprDate = p_date;
    };
    
    this.getApprDate = function() {
        return _struct_.apprDate;
    };
    
    this.setApprDeptCode = function(p_dept_code) {
        _struct_.apprDeptCode = p_dept_code;
    };
    
    this.getApprDeptCode = function() {
        return _struct_.apprDeptCode;
    };
    
    this.setApprDeptName = function(p_dept_name) {
        _struct_.apprDeptName = p_dept_name;
    };
    
    this.getApprDeptName = function() {
        return _struct_.apprDeptName;
    };
    
    this.setApprCode = function(p_appr_code) {
        _struct_.apprCode = p_appr_code;
    };
    
    this.getApprCode = function() {
        return _struct_.apprCode;
    };
    
    this.setApprLine = function(p_appr_line) {
        _struct_.apprLine = p_appr_line;
    };
    
    this.getApprLine = function() {
        return _struct_.apprLine;
    };
    
    this.setApprFlow = function(p_appr_flow) {
        _struct_.apprFlow = p_appr_flow;
    };
    
    this.getApprFlow = function() {
        return _struct_.apprFlow;
    };
    
    //2017.03.15 수신결재를 위해 추가 - k.s.j
    this.setReceivers = function(p_receivers) {
        _struct_.receivers = p_receivers;
    };
    
    this.getReceivers = function() {
        return _struct_.receivers;
    };
    //--------------------------------
    
    this.isERP = function() {
        return _struct_.erp;
    };
    
    this.setEDMSTransfer = function(p_transfer) {
        _struct_.edms = p_transfer;
    };
    
    this.isEDMS = function() {
        return _struct_.edms;
    };
    
    this.getCount = function() {
        return _struct_.count;
    };
    
    this.setColSeparator = function(p_separator) {
        _struct_.colSeparator = p_separator;
    };
    
    this.getColSeparator = function() {
        return _struct_.colSeparator;
    };
    
    /**
     * Set Row Separator
     */
    this.setRowSeparator = function(p_separator) {
        _struct_.rowSeparator = p_separator;
    };
    
    /**
     * return Row Separator
     */
    this.getRowSeparator = function() {
        return _struct_.rowSeparator;
    };
    
    /**
     * Rowset의 컬럼이름을 설정한다.<br>
     *  - LinkTable의 검색조건으로 사용되어지며 결재 데이터를 식별할 수 있는 컬럼<br>
     *  - 만일 복수개의 컬럼으로 조합이 된다면 컬럼과 컬럼사이의 구분 기호는 '@'를 사용하여 조합한다.<br><br>
     *  
     * 복수 컬럼 사용예) TMPSLIP_DEPT@TMPSLIP_DT@TMPSLIP_NO<br>
     * 단일 컬럼 사요예) SALE_NO<br><br>
     * 
     * @param String 컬럼명<br><br>
     */
    this.setColumns = function(p_name) {
        var name = p_name.toLowerCase();
        if (name.substring(name.length-1) == _struct_.colSeparator) {
            name = name.substring(0, name.length-1);
        }
        columns = name.split(_struct_.colSeparator);
        for (var i=0; i<columns.length; i++) {
            _struct_.cols[i] = columns[i];
        }
    };
    
    this.getColumns = function() {
        return _struct_.cols;
    };
    
    this.setRowset = function(p_value) {
        var value = p_value;
        if (value.substring(value.length-1) == _struct_.rowSeparator) {
            value = value.substring(0, value.length-1);
        }
        _struct_.rowset = value;
        
        rowset = value.split(_struct_.rowSeparator);
        _struct_.count = rowset.length;
        for (var i=0; i<_struct_.count; i++) {
            var col = rowset[i].split(_struct_.colSeparator);
            if (_struct_.cols.length != col.length) {
                    err = true;
                    message = "PK Column의 수와 Rowset Column의수가 일치하지 않습니다.";
                    if (this.debug) {
                        alert(message);
                    }
                    return false;
            }
            var _col = {};
            for (var j=0; j<_struct_.cols.length;j++) {
                _col[_struct_.cols[j]] = col[j];
            }
            _struct_.rows[i] = _col;
        }
        return true;
    };
    
    this.getRowset = function() {
        return _struct_.rowset;
    };
    
    this.setAttach = function(p_attach) {
        _struct_.attach = p_attach;
    };
    
    this.isAttach = function() {
        return _struct_.attach;
    };
    
    this.isError = function() {
        return err;
    };
    
    this.getProcessScreen = function() {
        return _struct_.processScreen;
    };
    
    this.setJobGu = function(p_jobGu) {
        _struct_.jobGu = p_jobGu;
    };
    
    this.getJobGu = function() {
        return _struct_.jobGu;
    };
    
    this.setCodeGu = function(p_codeGu) {
        _struct_.CodeGu = p_codeGu;
    };
    
    this.getCodeGu = function() {
        return _struct_.CodeGu;
    };
    
    this.setTitle = function(p_title) {
        _struct_.title = p_title;
    };
    
    this.getTitle = function() {
        return _struct_.title;
    };

    this.setDrafter = function(p_drafter) {
        _struct_.drafter = p_drafter;
    };
    
    this.getDrafter = function() {
        return _struct_.drafter;
    };
    
    this.setApprType = function(p_apprType) {
        _struct_.apprType = p_apprType;
    };
    this.setDocGrade = function(p_docGrade) {
        _struct_.docGrade = p_docGrade;
    };
    this.setDocSaveTerm = function(p_docSaveTerm) {
        _struct_.docSaveTerm = p_docSaveTerm;
    };
    this.setEdmsDocFolderId = function(p_id) {
        _struct_.edmsDocFolderId = p_id;
    };
    this.setEdmsDocFolderPath = function(p_path) {
        _struct_.edmsDocFolderPath = p_path;
    };
    this.setEdmsDocClass = function(p_docClass) {
        _struct_.edmsDocClass = p_docClass;
    };
    this.getSourceName = function() {
        return _struct_.sourceName;
    };
    
    this.setLinkId = function(p_ilnkId) {
        _struct_.link_id = p_ilnkId;
    };
    
    this.getLinkId = function() {
        return _struct_.link_id;
    };
    this.setRemoteAddr = function(p_remoteAddr) {
        _struct_.remoteAddr = p_remoteAddr;
    };
    
    this.getRemoteAddr = function() {
        return _struct_.remoteAddr;
    };
    
    this.setCustomerTrxId = function(p_remoteAddr) {
        _struct_.CustomerTrxId = p_remoteAddr;
    };
    
    this.getCustomerTrxId = function() {
        return _struct_.CustomerTrxId;
    };
    
    this.setInvoiceSource = function(p_InvoiceSource) {
        _struct_.InvoiceSource = p_InvoiceSource;
    };
    
    this.getInvoiceSource = function() {
        return _struct_.InvoiceSource;
    };
    
    this.setBirtParam = function(p_param) {
        _struct_.birtParam = p_param;
    };
    this.getBirtParam = function() {
        return _struct_.birtParam;
    };

    this.setApproveCodeCheck = function(p_ApproveCodeCheck) {
        _struct_.ApproveCodeCheck = p_ApproveCodeCheck;
    };
    this.getApproveCodeCheck = function() {
        return _struct_.ApproveCodeCheck;
    };
    
    this.setStdCurrentCd = function(p_StdCurrentCd){
        _struct_.StdCurrentCd = p_StdCurrentCd;
    };
    this.getStdCurrentCd = function() {
        return _struct_.StdCurrentCd;
    };  
    
    this.connect = function() {
        if (_struct_.count < 1) {
            err = true;
            message = "결재할 데이터가 존재하지 않습니다.";
            alert(message);
            return;
        }
        
        var etcParam = new Object();
        etcParam["HENC_SERVICE_NAME"] = _struct_.service;
        etcParam["HENC_REG_DEPT_CODE"] = _struct_.regDeptCode;
        etcParam["HENC_REG_DATE"] = _struct_.regDate;
        etcParam["HENC_APPR_DATE"] = _struct_.apprDate;
        etcParam["HENC_APPR_DEPT_CODE"] = _struct_.apprDeptCode;
        etcParam["HENC_APPR_DEPT_NAME"] = _struct_.apprDeptName;
        etcParam["HENC_APPRTYPE"] = _struct_.apprType;
        etcParam["HENC_STEP"] = _struct_.step;
        etcParam["HENC_LINKTABLE"] = _struct_.linkTable;
        etcParam["HENC_COLUMNS"] = _struct_.cols;
        etcParam["HENC_COLSEPARATOR"] = _struct_.colSeparator;
        etcParam["HENC_ROWSEPARATOR"] = _struct_.rowSeparator;
        etcParam["HENC_COUNT"] = _struct_.count;
        etcParam["HENC_ROWSET"] = _struct_.rowset;
        etcParam["HENC_JOBGU"] = _struct_.jobGu;
        etcParam["HENC_JOB_GU"] = _struct_.jobGu;
        etcParam["HENC_ERP"] = _struct_.erp;
        etcParam["HENC_EDMS"] = _struct_.edms;
        etcParam["HENC_REMOTEADDR"] = _struct_.remoteAddr;
        etcParam["HENC_CUSTOMERTRXID"] = _struct_.CustomerTrxId;
        etcParam["HENC_INVOICESOURCE"] = _struct_.InvoiceSource;
        etcParam["HENC_CODE_GU"] = _struct_.CodeGu;
        etcParam["HENC_APPRNO"] = _struct_.apprNo;
        etcParam["HENC_INVOICE_NO"] = _struct_.invoice_no;
        etcParam["HENC_STDCURRENTCD"] = _struct_.StdCurrentCd;
        etcParam["HENC_RECEIVERS"] = _struct_.receivers;
        etcParam["HENC_SOURCENAME"] = _struct_.sourceName;
        etcParam["HENC_TITLE"] = _struct_.title;
        etcParam["HENC_DRAFTER"] = _struct_.drafter;
        
        var popWidth = 1250;
        var popHeight = 900;
        var winHeight = document.body.clientHeight; // 현재창의 높이
        var winWidth = document.body.clientWidth;   // 현재창의 너비
        var winX = window.screenLeft;   // 현재창의 x좌표
        var winY = window.screenTop;    // 현재창의 y좌표
        var popX = winX + (winWidth - popWidth)/2;
        var popY = winY + (winHeight - popHeight)/2;

        var proParam = "width="+popWidth+"px,height="+popHeight+"px,top="+popY+",left="+popX;
        var url = webApprUri + "/common/approvalDraft";

        var webApprForm = document.webApprForm;
        webApprForm.action = url;
        webApprForm.target = "approvalDraftPopup";
        webApprForm.method = "post";
        webApprForm.apprEtcParam.value = jQuery.toJSON(etcParam);
        
        var apprDraftPop = window.open("", "approvalDraftPopup", proParam);
        
        apprDraftPop.focus();
        
        webApprForm.submit();
    };
}

window.addEventListener("message", receiveMessage, false);

function receiveMessage(event){
    webApprCallBack(event.data);
}
