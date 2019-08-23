/**
 * 공사일보 자바스크립트 정의
 * 프레임워크 dream.js 패턴 방식
 */

/**
 * Dialog.
 * Dream 프레임워크의 dialog를 확장하여 사용함.
 * info dialog 추가
 * @type {jQuery}
 */
Dream.dialog = $.extend(function ($) {
    /**
     * alert, modal 등을 화면에 출력
     * @param {string} selector dialog selector
     * @param {string} message dialog message
     * @return {jQuery} dialog 객체
     */
    function showDialog(selector, message, options) {
        var $dialog = $(selector).clone();
        // $dialog를 clone 해서 사용하므로 dialog가 닫힐 때 $dialog를 remove
        $dialog.on("hidden.bs.modal", function () {
            $(this).remove();
        });
        // 개행문자(newline)를 처리하기 위해 white-space: pre-line 추가.
        // XSS 공격 방지를 위해 html(message) 사용 금지.
        $dialog.find(".modal-body").css("white-space", "pre-line").text(message);
        // dialog 창 밖의 여백을 클릭하더라도 dialog 창이 닫히지 않도록 static 옵션으로 오픈
        $dialog.modal($.extend({backdrop: "static"}, options));
        return $dialog;
    }

    return {
        /**
         * 시간이 지나면 자동으로 닫히는 dialog
         * 만일 dealy 값이 false일 경우 자동으로 닫히지 않음.
         *
         * 사용예
         * _.dialog.info("Cdrs dialog info"}); // 단순 메시지 출력 후 자동 닫힘
         * _.dialog.info("Cdrs dialog info", function(){console.log('callback!')}); // 콜백처리
         * _.dialog.info("Cdrs dialog info", false, false}); // 자동닫힘 방지
         *
         * @param message 표시 메시지
         * @param callback 콜백 함수
         * @param delay 자동 닫힘 시간(초 단위)
         * @param options 모달 옵션(왠만하면 기본값 사용 바람)
         */
        info: function (message, callback, delay, options) {
            var infoOptions = $.extend({/*your option here!*/}, options);
            var $info = showDialog("#_dream_info_", message, infoOptions);

            if (!(delay === 0 || delay === false)) {
                delay = delay == null ? 3000 : delay * 1000;
                setTimeout(function () {
                    if ($.isFunction(callback)) {
                        callback.apply(this, arguments);
                    }
                    $info.modal('hide');
                }, delay);
            }
        }
    }
}(jQuery), Dream.dialog);

Dream.namespace("Dream.cdrs");
Dream.cdrs = $.extend(function ($) {
    return {
        /**
         * 현재 선택된 현장, 사업부 코드를 반환
         * _.cdrs.storage.deptCd
         * _.cdrs.storage.bizhdofCd()
         */
        storage: {
            deptCd: function() { return localStorage.getItem("mainDeptCd"); },
            bizhdofCd: function() { return localStorage.getItem("mainBizhdofCd"); },
            partnerNo: function() { return localStorage.getItem("partnerNo"); },
            loginId: function() { return localStorage.getItem("loginId"); },
            loginName: function() { return localStorage.getItem("loginInfo"); },
            reportUrl: function() { return localStorage.getItem("reportUrl"); }
        },

        /**
         * 현장코드 조회 팝업
         * @param sheet 대상 시트
         * @param row 시트 Row
         * @param col 코드와 명을 입력받을 객체 {cd: "deptCd", nm: "deptNm", search: 검색어}
         * @param callback 콜백 함수
         * @param options 팝업 옵션 {}
         */
        deptDialog: function(sheet, row, col, callback, options) {
            options = $.extend({width: "800px"}, options);
            _.dialog.modal("/sysm/com/uscd/comDeptCdList", {title: "현장조회", width: options.width}, function(data) {
                sheet.SetCellValue(row, col.cd, data.deptCd);
                sheet.SetCellValue(row, col.nm, data.deptNm);
                if ($.isFunction(callback)) {
                    callback.apply(this, arguments);
                }
            });
        },

        deptInputDialog: function(input, callback, options) {
            options = $.extend({width: "800px"}, options);
            _.dialog.modal( _.url.format("/sysm/com/uscd/comDeptCdList/{searchDeptNm}", {searchDeptNm: input.search}), {title: "현장조회", width: options.width}, function(data) {
                input.cd.val(data.deptCd)
                input.nm.val(data.deptNm)
                if ($.isFunction(callback)) {
                    callback.apply(this, arguments);
                }
            });
        },

        ibsheetValid: function(json) {
            if (json.Code !== undefined && json.Code !== "IBS000") {
                return false;
            } else {
                return true;
            }
        },

        /**
         * birt Report 를 새창으로 띄워준다.
         * @param option
         *          reportName  : report 이름 ex) loigAsgnInfo
         *          reportParameters : 파라미터 ex) ?userId=CM00005&zoneDiv=10
         *          accessToken : token
         */
        birtReport : function(option){
            let reportUrl = localStorage.getItem("reportUrl");
            if(reportUrl == null){
                _.dialog.alert("리포트 URL 정보가 없습니다. 확인후 진행 바랍니다.");
                return false;
            }

            window.open("","form");
            var form = document.createElement("form");
            form.setAttribute("method", "POST");
            form.setAttribute("action", reportUrl);
            form.setAttribute("target", "form");
            document.body.appendChild(form);

            var formInput = document.createElement("input");
            formInput.setAttribute("type", "hidden");
            formInput.setAttribute("name", "reportName");
            formInput.setAttribute("value", option.reportName);
            form.appendChild(formInput);

            var formInput = document.createElement("input");
            formInput.setAttribute("type", "hidden");
            formInput.setAttribute("name", "reportParameters");
            formInput.setAttribute("value", option.reportParameters);
            form.appendChild(formInput);

            //token
            var formInput = document.createElement("input");
            formInput.setAttribute("type", "hidden");
            formInput.setAttribute("name", "accessToken");
            formInput.setAttribute("value", option.accessToken);
            form.appendChild(formInput);

            form.submit();
        },

        /**
         * Notification.
         * _.cdrs.notify.show( title, body );
         */
        notify: {
            show: function(title, body, autoclose) {
                var $toast = $("#notification .toast");
                $("span.title", $toast).html(title);
                $(".toast-body", $toast).html(body);
                $toast.addClass("show").removeClass("hide");
                autoclose = (typeof autoclose === "undefined" ? 3000 : autoclose);
                setTimeout(function () {
                    _.cdrs.notify.close();
                }, autoclose);
            },
            close: function() {
                var $toast = $("#notification .toast");
                $toast.addClass("hide").removeClass("show");
            }
        }
    }
}(jQuery), Dream.utils);
