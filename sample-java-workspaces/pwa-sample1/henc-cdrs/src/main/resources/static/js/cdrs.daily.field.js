/**
 * 데이터 유틸 객체
 * @type {{row_data_bind: (function(*=, *): *), autocomplete: DataUtils.autocomplete, staCdProps: (function(*): {textShort: string, text: string, class: string}), staCd: {REJECT: string, DRAFT: string, APPROVAL: string, CHANGE: string, APPROVE: string}, camelize: (function(*)), decamelize: (function(*, *=))}}
 */
var DataUtils = {
    /**
     * Camelize data Key
     * @param data
     */
    camelize: function (data) {
        var pk = {};
        // model과 맞도록 camelize 처리.
        for (var key in data) {
            var oldKey = key,
                newKey = key.replace(/[\-_\s]+(.)?/g, function (match, ch) {
                    return ch ? ch.toUpperCase() : '';
                });
            pk[newKey] = data[oldKey];
        }
        return pk;
    },

    /**
     * DeCamelize data key
     * @param data
     * @param separator
     */
    decamelize: function (data, separator) {
        separator = typeof separator === 'undefined' ? '_' : separator;

        var pk = {};
        // model과 맞도록 camelize 처리.
        for (var key in data) {
            var oldKey = key,
                newKey = key.replace(/([a-z\d])([A-Z])/g, '$1' + separator + '$2').replace(/([A-Z]+)([A-Z][a-z\d]+)/g, '$1' + separator + '$2').toLowerCase();
            pk[newKey] = data[oldKey];
        }
        return pk;
    },

    /**
     * 플랜트 사업본부 체크.
     */
    is_plant: (_.cdrs.storage.bizhdofCd()+"" == "55"),

    /**
     * 일일 보고 상태 코드(건설/현장)
     * DD_BRFG.STA_CD enumerations.
     * @see DdBrfgStaCd
     */
    staCd: {
        DRAFT: "10",
        APPROVAL: "20",
        REJECT: "40",
        APPROVE: "50",
        CHANGE: "60",
        HOLD: "61"
    },

    /**
     * 일일 보고 회사 상태 코드(협력사)
     * DD_BRFG_COMP.STA_CD enumerations.
     */
    compStaCd: {
        NONE: "00",
        DRAFT: "10",
        APPROVAL: "20",
        REJECT: "40",
        APPROVE: "50"
    },

    staCdProps: function (staCd, isPartner) {
        var textClass = "", staCdClass = "", staCdText = "", staCdTextShort = "", staCdOptionalText = "";
        if (staCd == "10") {
            textClass = "text-secondary";
            staCdClass = "bg-secondary";
            staCdText = "작성";
            staCdTextShort = "작";
            staCdOptionalText = "작성";
        } else if (staCd == "20") {
            textClass = "text-danger";
            staCdClass = "bg-danger";
            staCdText = (isPartner == true ? "제출" : "상신");
            staCdTextShort = (isPartner == true ? "제" : "상");
            staCdOptionalText = "결재";
        } else if (staCd == "40") {
            textClass = "text-warning";
            staCdClass = "bg-warning";
            staCdText = "반려";
            staCdTextShort = "반";
            staCdOptionalText = "반려";
        } else if (staCd == "50") {
            textClass = "text-success";
            staCdClass = "bg-success";
            staCdText = "승인";
            staCdTextShort = "승";
            staCdOptionalText = "승인";
        } else if (staCd == "60") {
            textClass = "text-primary";
            staCdClass = "bg-primary";
            staCdText = "변경";
            staCdTextShort = "변";
            staCdOptionalText = "변경";
        } else if (staCd == "61") {
            textClass = "text-primary";
            staCdClass = "bg-primary";
            staCdText = "Hold";
            staCdTextShort = "H";
            staCdOptionalText = "Hold";
        } else if (staCd == "00") {
            textClass = "text-primary";
            staCdClass = "bg-primary";
            staCdText = "미작성";
            staCdTextShort = "미";
            staCdOptionalText = "미작성";
        }
        return {
            "class": staCdClass,
            "text": staCdText,
            "textShort": staCdTextShort,
            "textClass": textClass,
            "optionalText": staCdOptionalText
        };
    },

    /**
     * 데이터 테이블 대상 row의 data와 form값 바인드
     * @param data
     * @param $row
     * @returns {*}
     */
    row_data_bind: function (data, $row) {
        var decamelizedData = DataUtils.decamelize(data),
            row_data = $row.data();
        // bind row data.
        for (var key in row_data) {
            $row.data(key, decamelizedData[key]);
        }

        // bind form value.
        $row.find("input, select, textarea").each(function (i, el) {
            if ($(el).prop("type") == "text") {
                $(el).val(decamelizedData[$(el).prop("name")]);
                if ($(el).hasClass("autocomplete")) {
                    $(el).data("selected_" + $(el).prop("name"), decamelizedData[$(el).prop("name")]);
                }
                if ($(el).data("parsley-type") == "date") {
                    $(el).inputmask("yyyy-mm-dd");
                }
            } else if ($(el).prop("type") == "select-one") {
                $(el).val(decamelizedData[$(el).prop("name")]);
            }
        });

        return $row;
    },

    find_index: function (data, rowId) {
        var index = -1;
        data.some(function (row, i) {
            index = i;
            return Number(row.rowId) == Number(rowId);
        });
        return index;
    },

    /**
     * 데이터 테이블 rows 데이터 배열을 반환
     * @param $table 데이터 테이블
     * @param row_status row_sttus
     * @param is_camelize 카멜표기법 여부
     * @returns {Array}
     */
    get_rows_data: function ($table, row_status, is_camelize) {
        var data = [],
            camelize = (typeof is_camelize === 'undefined' ? true : is_camelize);

        $("tbody > tr", $table).each(function (i, row) {
            var row_data = (camelize == true ? DataUtils.camelize($(row).data()) : $(row).data());
            if (typeof row_status !== 'undefined') {
                if ($(row).data("row_status") == row_status) {
                    data.push(row_data);
                }
            } else {
                data.push(row_data);
            }
        });
        return data;
    },

    /**
     * 자동완성
     * @param data 자동완성 퀵서치 데이터
     * @param cells 자동완성 리스트 html colums
     * @param $box 렌더링 대상 .box
     * @param binder 선택 결과를 처리할 콜백 함수
     */
    autocomplete: function (searchDailyParams, cells, $box, binder) {
        $box.find("input.autocomplete").each(function (i, el) {
            $(this).autocomplete({
                maxShowItems: 5,
                source: function (request, response) {
                    searchDailyParams.autocompleteSearchParams.type = this.element.prop("name");
                    searchDailyParams.autocompleteSearchParams.term = request.term;
                    $.get("/daily/field/autocompleteSearch", searchDailyParams, function (data) {
                        response(data);
                    });
                },
                select: function (event, ui) {
                    event.preventDefault();
                    if ($.isFunction(binder)) {
                        binder(ui.item, this);
                    }
                },

                focus: function (event, ui) {
                    event.preventDefault();
                    return false;
                }

            }).data("ui-autocomplete")._renderItem = function (ul, item) {
                var $row = $("<li>", {class: "row", display: "table-row"});
                cells.forEach(function (cell) {
                    ($("<div>", {class: "ui-widget-sub", display: "table-cell"}).html(item[cell])).appendTo($row);
                });
                ul.addClass("datatable_autocomplete");
                return $row.appendTo(ul);
            };
        });
    },

    /**
     * 매뉴얼 입력 중복체크.
     * @param $rows
     * @param $rowself
     * @param cols
     * @param show_alert
     * @returns {boolean}
     */
    manual_dup_check: function ($rows, $rowself, cols, show_alert) {
        var self_data = DataUtils.camelize($rowself.data());
        var is_not_dup = true;

        $.each($rows, function (i, row_data) {
            if (row_data.rowId == self_data.rowId) {
                return true;
            }
            var row_key_string = "", input_row_key_string = "";
            for (var col in cols) {
                row_key_string += (row_data[cols[col]] + "_");
                input_row_key_string += (self_data[cols[col]] + "_");
            }

            if (row_key_string == input_row_key_string) {
                is_not_dup = false;
                return false;
            }
        });

        if (show_alert && !is_not_dup) {
            notifyUtils.show("경고", "중복된 데이터가 존재합니다.<br>중복된 자료는 저장되지 않습니다.");
        }
        return is_not_dup;
    }
};

/**
 * 데이터 테이블 페이징 객체
 * @type {{set_page_no: PageUtils.set_page_no, build_navigator: PageUtils.build_navigator, paging_data: (function(*, *, *): *), set_pager: PageUtils.set_pager}}
 */
var PageUtils = {

    paging_data: function (data, page_list_count, page_no) {
        var size = data.length,
            page_no = typeof page_no === 'undefined' ? 1 : page_no,
            first_index = page_no === 1 ? 0 : page_list_count * (page_no - 1),
            page_list_count = page_list_count == "" ? size : page_list_count,
            limit_index = size < page_list_count ? size : (page_list_count * page_no),
            paged_data = data.slice(first_index, limit_index);

        return paged_data;
    },

    build_navigator: function ($navigator) {
        $navigator.empty();
        var page_navigation_count = $navigator.data("page_navigation_count"),
            current_page = $navigator.data("current_page"),
            size = $navigator.data("total"),
            page_list_count = $navigator.data("page_list_count") == "" ? size : $navigator.data("page_list_count");

        // 전체 페이지 수
        $navigator.data("total_page", Math.ceil(size / page_list_count));

        // 전체 중에서 navigation 수가 포지션된 페이지(page_navigation_count 값을 기준으로 계산)
        var page_no = (Math.ceil($navigator.data("page_no") / page_navigation_count)),
            last_page_no = page_no * page_navigation_count,
            start_page_no = last_page_no - page_navigation_count + 1,
            paging_index = 0;

        var $prev = $('<li class="page-item prev" data-page_no="prev"><a class="page-link" aria-label="Previous"><i class="fas fa-angle-left"></i></a></li>'),
            $next = $('<li class="page-item next" data-page_no="next"><a class="page-link" aria-label="Next"><i class="fas fa-angle-right"></i></a></li>');

        if (current_page > 1) {
            $navigator.append($prev);
        }

        while (start_page_no <= last_page_no) {
            ++paging_index;
            if (start_page_no > $navigator.data("total_page")) {
                break;
            }
            $navigator.append($('<li class="page-item ' + (start_page_no == $navigator.data("page_no") ? 'active' : '') + '" data-page_no="' + start_page_no + '"><a class="page-link">' + start_page_no + '</a></li>'));
            start_page_no++;
        }

        if (current_page < Math.ceil($navigator.data("total_page") / page_navigation_count)) {
            $navigator.append($next);
        }
    },

    set_pager: function ($box, data) {
        var $page_navigator = $(".page_navigator", $box);
        $page_navigator.data("page_list_count", $(".data_list_counter", $box).val());
        $page_navigator.data("page_no", 1);
        $page_navigator.data("total", data.length);
        PageUtils.build_navigator($page_navigator);
        Renderer.render_data_table($box, data);
    },

    set_page_no: function ($box, $li, data) {
        var $page_navigator = $(".page_navigator", $box);

        if ($li.data("page_no") == "prev" || $li.data("page_no") == "next") {
            var clickable = false;

            if ($li.data("page_no") == "prev") {
                clickable = $page_navigator.data("current_page") > 1;
                if (clickable) {
                    $page_navigator.data("current_page", $page_navigator.data("current_page") - 1);
                    $page_navigator.data("page_no", ($page_navigator.data("current_page") * $page_navigator.data("page_navigation_count")) - $page_navigator.data("page_navigation_count") + 1);
                }
            } else {
                clickable = $page_navigator.data("current_page") < Math.ceil($page_navigator.data("total_page") / $page_navigator.data("page_navigation_count"));
                if (clickable) {
                    $page_navigator.data("page_no", ($page_navigator.data("current_page") * $page_navigator.data("page_navigation_count")) + 1);
                    $page_navigator.data("current_page", $page_navigator.data("current_page") + 1);
                }
            }

            if (clickable) {
                PageUtils.build_navigator($page_navigator);
            }

        } else {
            $page_navigator.data("page_no", $li.data("page_no"));
            $("li", $page_navigator).removeClass("active");
            $li.addClass("active");
            if (typeof data !== "undefined") {
                Renderer.render_data_table($box, data);
            }
        }
    }
};

/**
 * Notification Util 객체.
 * Dreamfreamework bottstrap 버전 차이로 인하여 toast를 직적 JS로 구현처리함.
 * @type {{init: notifyUtils.init, build: notifyUtils.build, show: notifyUtils.show, close: notifyUtils.close}}
 */
var notifyUtils = {
    init: function () {
        $(document.body).on("click", "#notification .close", function (e) {
            notifyUtils.close();
        });
    },
    show: function (title, body, autoclose) {
        var $toast = $("#notification .toast");
        $("span.title", $toast).html(title);
        $(".toast-body", $toast).html(body);
        $toast.addClass("show").removeClass("hide");
        autoclose = (typeof autoclose === "undefined" ? 3000 : autoclose);
        setTimeout(function () {
            notifyUtils.close();
        }, autoclose);
    },
    close: function () {
        var $toast = $("#notification .toast");
        $toast.addClass("hide").removeClass("show");
    }
};

/**
 * UI 렌더링 객체
 * @type {{render_data_table: Renderer.render_data_table}}
 */
var Renderer = {
    mode: "view",
    is_paging: false,

    set_paging: function (is_paging) {
        this.is_paging = is_paging;
    },

    init: function (options) {
        this.mode = options.mode;
        this.is_paging = options.is_paging;
    },

    /**
     * 일보 데이터 테이블 데이터 렌더링
     * @param $box
     * @param storage_data
     */
    render_data_table: function ($box, storage_data) {
        var $data_table = $(".data_table", $box),
            $tbody = $("tbody", $data_table),
            $template = $("tfoot .template", $data_table),
            page_list_count = $(".data_list_counter", $box).val(),
            page_no = $(".page_navigator", $box).data("page_no");

        $tbody.empty();

        var paged_data = Renderer.is_paging ? PageUtils.paging_data(storage_data, page_list_count, page_no) : storage_data;
        paged_data.forEach(function (data, i) {
            var rowStatus = data.rowStatus == null ? "R" : data.rowStatus,
                $new_row = $template.clone();
            $new_row.removeClass("template");
            $new_row.addClass(rowStatus);
            $new_row = DataUtils.row_data_bind(data, $new_row);
            $new_row.data("row_status", rowStatus);
            $new_row.appendTo($tbody);
        });
        $data_table.trigger("rendered_data_table");
    },

    /**
     * 일보 계약 전체 데이터 렌더링
     * 일보 상세화면 진입시 호출된 Ajax로부터 로드된 데이터로 렌더링 한다.
     * @param data
     */
    render_ctrcs_data: function (data) {
        // 일보 일자요일 설정(dream framework moment 활용)
        var m = moment(data.result.master.day, "YYYYMMDD");
        $("#date_text").html(m.format("YYYY-MM-DD dddd"));

        // 일보 데이터 freemarker template 렌더링
        $("#fieldDataWrapper").html(data.html);

        $(".master_box").trigger("load_master_data");

        var $ctrc_group = $(".ctrc_group.active");
        $(".data_table_wrapper", $ctrc_group).trigger("load_data", [data]);
        $ctrc_group.trigger("rendered_ctrc_group");

        $(".ctrc_group").each(function (i, ctrc) {
            var $ctrc = $(ctrc),
                ctrc_data = $(ctrc).data(),
                staCdProps = DataUtils.staCdProps(ctrc_data.sta_cd);
            $(".header .state", $ctrc).addClass(staCdProps.class);
        });
    },

    /**
     * 데이터 테이블 row folding 처리(모바일).
     * @param event
     */
    fold_data_table_row: function (event) {
        var $el = $(event.currentTarget),
            $tr = $el.closest("tr");
        $tr.toggleClass("on");
        $("i.fas", $el).toggleClass("fa-sort-down");
        $("i.fas", $el).toggleClass("fa-sort-up");
    }
};

/**
 * 일일 보고 자료 처리를 위한 데이터셋 객체.
 * @type {{set: Storage.set, submit: Storage.submit, keys: {calendar: string, comp: string, labatd: string, qty: string, mtil: string, eqip: string}, get: (function(*=): string), showMessage: Storage.showMessage, empty: Storage.empty}}
 */
var Storage = {

    /**
     * local storage key
     */
    keys: {
        calendar: "HENC_CDRS_FIELD_CALENDAR",

        master: "HENC_CDRS_FIELD_MASTER",

        comp: "HENC_CDRS_FIELD_COMP",
        comp_saving: "HENC_CDRS_FIELD_COMP_SAVING",

        labatd: "HENC_CDRS_FIELD_LABATD",
        labatd_henc: "HENC_CDRS_FIELD_LABATD_HENC",
        labatd_saving: "HENC_CDRS_FIELD_LABATD_SAVING",

        eqip: "HENC_CDRS_FIELD_EQIP",
        eqip_saving: "HENC_CDRS_FIELD_EQIP_SAVING",

        mtil: "HENC_CDRS_FIELD_MTIL",
        mtil_saving: "HENC_CDRS_FIELD_MTIL_SAVING",

        qty: "HENC_CDRS_FIELD_QTY",
        qty_saving: "HENC_CDRS_FIELD_QTY_SAVING"
    },

    set: function (key, data, isJson) {
        if (isJson) {
            localStorage.setItem(key, data);
        } else {
            localStorage.setItem(key, JSON.stringify(data));
        }
    },

    get: function (key) {
        return localStorage.getItem(key);
    },

    empty: function () {
        for (var key in this.keys) {
            localStorage.removeItem(this.keys[key])
        }
    },

    validated: true,

    before_submit: function () {
        Storage.validated = true;
        $("#company-modify-modal tbody .validate").each(function (i, el) {
            var $row = $(el).closest("tr"), row_status = $row.data("row_status");
            if (row_status == "D" || $row.hasClass("E")) {
                return true
            }

            var validated = $(el).parsley().on("field:validated", function () {
                if (this.validationResult !== true) {
                    this.$element.tooltip("show");
                }
            }).validate();
            if (validated !== true) {
                Storage.validated = false;
                return false;
            }
        });

        if (Storage.validated) {
            var main_storage_data = ['labatd', 'eqip', 'mtil', 'qty'],
                saving_storage_data = ['labatd', 'eqip', 'mtil', 'qty'];

            main_storage_data['labatd'] = JSON.parse((Storage.get(Storage.keys.labatd)));
            main_storage_data['eqip'] = JSON.parse((Storage.get(Storage.keys.eqip)));
            main_storage_data['mtil'] = JSON.parse((Storage.get(Storage.keys.mtil)));
            main_storage_data['qty'] = JSON.parse((Storage.get(Storage.keys.qty)));
            saving_storage_data['labatd'] = JSON.parse((Storage.get(Storage.keys.labatd_saving)));
            saving_storage_data['eqip'] = JSON.parse((Storage.get(Storage.keys.eqip_saving)));
            saving_storage_data['mtil'] = JSON.parse((Storage.get(Storage.keys.mtil_saving)));
            saving_storage_data['qty'] = JSON.parse((Storage.get(Storage.keys.qty_saving)));

            // 일보 주요업무 처리
            Storage.set(Storage.keys.comp_saving, JSON.parse(Storage.get(Storage.keys.comp)));

            // 데이터 테이블 처리
            ['labatd', 'eqip', 'mtil', 'qty'].forEach(function (key) {
                // 저장 대상 스토리지를 데이터 원본 스토리지에 반영한다.
                var saving_data = saving_storage_data[key],
                    main_data = main_storage_data[key];

                if (saving_data != null) {
                    saving_data.forEach(function (data, i) {
                        switch (data.rowStatus) {
                            case "D":
                                var rowIndex = DataUtils.find_index(main_data, data.rowId);
                                if (rowIndex > -1) main_data.splice(rowIndex, 1);
                                break;

                            case "U":
                                var rowIndex = DataUtils.find_index(main_data, data.rowId);
                                data.rowStatus = "R";
                                main_data[rowIndex] = data;
                                break;

                            case "I":
                                data.rowStatus = "R";
                                main_data.push(data);
                                break;

                            default:
                                break;
                        }
                    });
                    Storage.set(Storage.keys[key], main_data);
                }
            });
            return true;

        } else {
            return false;
        }
    },

    showMessage: function (message) {
        var html = "<div class='alert alert-warning alert-dismissible fade show mb-0' role='alert'><strong>NOTICE <i class='fas fa-exclamation-circle'></i></strong><ul class='mb-0 pl-0'></ul>";
        html += "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>";
        html += "</div>";

        var $html = $(html),
            li = $("<li>" + message.content + "</li>");

        li.appendTo($html.find("ul"));
        message.place.prepend($html);

        if (message.autoclose != null) {
            $html.delay(message.autoclose).fadeOut(300, function () {
                $(this).remove();
            });
        }
    }
};

/**
 * 일일 보고 달력 객체
 * @type {{date: {month: string, year: string}, next: FieldCalendar.next, init: FieldCalendar.init, previous: FieldCalendar.previous, copyHistoryData: FieldCalendar.copyHistoryData, renderCalendarData: FieldCalendar.renderCalendarData, saveDailyPartnerData: FieldCalendar.saveDailyPartnerData, check_status: FieldCalendar.check_status, getDailyData: FieldCalendar.getDailyData, $table: string, build: FieldCalendar.build, getCalendarData: FieldCalendar.getCalendarData, getHistories: FieldCalendar.getHistories, renderHistoryTable: FieldCalendar.renderHistoryTable}}
 */
var FieldCalendar = {
    $table: "",
    $mobile_table: "",
    date: {year: "", month: ""},

    /**
     * 일보 초기화
     * @param element 데스크탑 모드 달력 html 객체
     * @param mini_calendar_element 모바일 모드 추가달력 html 객체
     */
    init: function (element, mini_calendar_element) {
        // 초기화
        FieldCalendar.$table = element;
        FieldCalendar.$mobile_table = mini_calendar_element;
        Storage.empty();

        var today = new Date(),
            calendarData = Storage.get(Storage.keys.calendar);

        if (typeof calendarData === "object" && calendarData != null) {
            FieldCalendar.date.year = calendarData.year;
            FieldCalendar.date.month = calendarData.month;
        } else {
            FieldCalendar.date.year = today.getFullYear();
            FieldCalendar.date.month = today.getMonth();
        }

        // 달력닫기
        $(document.body).on("click", ".calenda_table_toggler", function (e) {
            // 일보 내용 제거.
            $("#fieldDataWrapper").empty();
            $("#calendar").removeClass("expanded");
            // 일보 달력 데이터 재 조회
            // FieldCalendar.$table.empty();
            FieldCalendar.getCalendarData();
        });

        // 일보 계약 클릭
        $(document.body).on("click", ".calendar-table tbody td > li.clickable", function (e) {
            $("#calendar").addClass("expanded");

            var ddBrfg = DataUtils.camelize($(this).closest("td").data()),
                ddBrfgComp = DataUtils.camelize($(this).data()),
                searchDailyParams = {
                    deptCd: _.cdrs.storage.deptCd(),
                    bizhdofCd: _.cdrs.storage.bizhdofCd(),
                    day: ddBrfgComp.day,
                    ordrNo: ddBrfg.ordrNo,
                    partnerNo: ddBrfgComp.partnerNo,
                    ctrcNo: ddBrfgComp.ctrcNo
                };
            MasterEvent.get_daily_all_data(searchDailyParams);
            e.preventDefault();
        });

        // 일보 없는 일자 클릭
        $(document.body).on("click", ".calendar-table tbody td", function (e) {
            $("#calendar").addClass("expanded");

            var ddBrfg = DataUtils.camelize($(this).data()),
                ddBrfgComp = {
                    deptCd: ddBrfg.deptCd,
                    day: ddBrfg.day,
                    ordrNo: ddBrfg.ordrNo,
                    partnerNo: _.cdrs.storage.partnerNo()
                },
                searchDailyParams = {
                    deptCd: _.cdrs.storage.deptCd(),
                    bizhdofCd: _.cdrs.storage.bizhdofCd(),
                    day: ddBrfgComp.day,
                    ordrNo: ddBrfg.ordrNo,
                    partnerNo: ddBrfgComp.partnerNo,
                    ctrcNo: ddBrfgComp.ctrcNo
                };
            MasterEvent.get_daily_all_data(searchDailyParams);
            e.preventDefault();
        });

        // 모바일 달력 일자 클릭
        $(document.body).on("click", "#top-calendar-body td", function (e) {
            if (!$(this).is(".empty")) {
                var rowpos = $("#calendar-body td#day" + $(this).data("day")).position();
                $("body, html").animate({scrollTop: rowpos.top}, 800);
            }
        });

        FieldCalendar.build(FieldCalendar.$table);
        FieldCalendar.build(FieldCalendar.$mobile_table);
        notifyUtils.init();

        FieldCalendar.getCalendarData();

        MasterEvent.init();
        ModalEvent.init();
        SubmittedEvent.init();
        CompEvent.init();
        LabatdEvent.init();
        MtilEvent.init();
        EqipEvent.init();
        if (DataUtils.is_plant) {
            QtyEvent.init();
        }
    },

    /**
     * 일보 달력 생성
     */
    build: function ($table) {
        var today = new Date(),
            month = FieldCalendar.date.month,
            year = FieldCalendar.date.year,
            firstDay = (new Date(year, month)).getDay(),
            lastDay = (new Date(year, month + 1, 0)).getDay(),
            daysInMonth = 32 - new Date(year, month, 32).getDate(),
            trRows = Math.ceil((firstDay + (6 - lastDay) + daysInMonth) / 7),
            date = 1,
            calendar_type = $table.data("calendar_type");

        if (calendar_type !== "mobile_only") {
            $("#year-text").html(FieldCalendar.date.year + "년");
            $("#month-text").html((parseInt(FieldCalendar.date.month) + 1) + "월");
        }
        $table.empty();

        for (var i = 0; i < trRows; i++) {
            var row = $("<tr style='cursor: pointer'>"); // iPad tablr row click workaround: cursor: pointer
            var cells = [];
            for (var j = 0; j < 7; j++) {
                if (i === 0 && j < firstDay) {
                    // 첫 빈칸
                    cells.push($("<td class='empty'><div></div></td>"));

                } else if (date > daysInMonth) {
                    break;

                } else {
                    var day_prefix = (calendar_type == "mobile_only" ? "_m" : "");
                    var day = FieldCalendar.date.year + "" + (("0" + (FieldCalendar.date.month + 1)).slice(-2)) + (("0" + date).slice(-2)),
                        cell = $("<td id='" + day_prefix + "day" + day + "' data-dept_cd='" + _.cdrs.storage.deptCd() + "' data-day='" + day + "' data-ordr_no='' data-sta_cd='' data-chg_no='' data-chg_seq=''></td>"),
                        cellDay = $("<div class='day'>" + date + "</div>");

                    if (date === today.getDate() && year === today.getFullYear() && month === today.getMonth()) {
                        // 오늘일자에 특별하게 해주고 싶다면 cell.addClass("bg-info"); 사용할 수 있음.
                    }

                    cell.prepend(cellDay);
                    cells.push(cell);
                    date++;
                }
            }

            // 마지막 일자의 빈칸 계산
            if (cells.length < 7) {
                while (7 - cells.length) {
                    cells.push($("<td class='empty'><div></div></td>"));
                }
            }

            cells.forEach(function (element, index) {
                row.append(element);
            });

            $table.append(row);
        }
    },

    /**
     * 다음 월 조회
     */
    next: function () {
        FieldCalendar.date.year = (FieldCalendar.date.month === 11) ? FieldCalendar.date.year + 1 : FieldCalendar.date.year;
        FieldCalendar.date.month = (FieldCalendar.date.month + 1) % 12;
        FieldCalendar.build(FieldCalendar.$table);
        FieldCalendar.build(FieldCalendar.$mobile_table);
        FieldCalendar.getCalendarData();
    },

    /**
     * 이전 월 조회
     */
    previous: function () {
        FieldCalendar.date.year = (FieldCalendar.date.month === 0) ? FieldCalendar.date.year - 1 : FieldCalendar.date.year;
        FieldCalendar.date.month = (FieldCalendar.date.month === 0) ? 11 : FieldCalendar.date.month - 1;
        FieldCalendar.build(FieldCalendar.$table);
        FieldCalendar.build(FieldCalendar.$mobile_table);
        FieldCalendar.getCalendarData();
    },

    /**
     * 오늘 날짜로 렌더링.
     */
    toDate: function () {
        var today = new Date(),
            m = moment().format("YYYYMMDD");

        FieldCalendar.date.year = today.getFullYear();
        FieldCalendar.date.month = today.getMonth();

        FieldCalendar.build(FieldCalendar.$table);
        FieldCalendar.build(FieldCalendar.$mobile_table);

        FieldCalendar.getCalendarData(function (data) {
            var rowpos = $("#calendar-body td#day" + m).position();
            $("body, html").animate({scrollTop: rowpos.top}, 800);
        });
    },

    /**
     * 일보 달력 데이터 조회
     * @param callback 호출된 쪽에서 콜백처리할 함수
     */
    getCalendarData: function (callback) {
        var params = {
            deptCd: _.cdrs.storage.deptCd(),
            day: FieldCalendar.date.year + "" + (("0" + (FieldCalendar.date.month + 1)).slice(-2)) + "01",
        };

        $.get("/daily/partner/monthlyData", params, function (data) {
            Storage.set(Storage.keys.calendar, {
                year: FieldCalendar.date.year,
                month: FieldCalendar.date.month,
                data: data.result
            });
            FieldCalendar.renderCalendarData(data.result, callback);
        });
    },

    /**
     * 일보 달력 데이터 렌더링
     * @param data
     */
    renderCalendarData: function (data, callback) {
        var calendarData = data.calendarData,
            partnerData = data.partnerData;

        calendarData.forEach(function (day, i) {
            var $day = $("#day" + day.day),
                m = moment(day.day, "YYYYMMDD"),
                dd = (m.format("YYYY-MM-DD dddd")),
                // nullable variables.
                weatherCd = (day.weatherCd == null ? '' : day.weatherCd),
                weatherNm = (day.weatherNm == null ? '' : day.weatherNm),
                lowestTempe = (day.lowestTempe == null ? '' : day.lowestTempe),
                highestTempe = (day.highestTempe == null ? '' : day.highestTempe);

            $day.data("dept_cd", day.deptCd);
            $day.data("sta_cd", day.staCd);
            $day.data("ordr_no", day.ordrNo);
            $day.data("weather_cd", day.weatherCd);
            $day.data("lowest_tempe", day.lowestTempe);
            $day.data("highest_tempe", day.highestTempe);
            $day.data("ptcl_mtr", day.ptclMtr);
            $day.data("chg_no", day.chgNo);
            $day.data("chg_seq", day.chgSeq);
            $(".data", $day).remove();

            var staCdProps = DataUtils.staCdProps(day.staCd),
                $dayDataDiv = $("<div class='data " + staCdProps.class + "'/>"),
                $box = $("<div class='box'/>"),
                $span = $("<span class='state clickable'>" + staCdProps.text + "</span>"),
                $ul = $("<ul/>");

            // 제출된 일보가 있으면 상태명 대신 제출된 갯수를 보여준다.
            var aprvData = (partnerData[day.day] !== undefined ? (partnerData[day.day][0].tot > 0 ? partnerData[day.day][0].cnt + "/" + partnerData[day.day][0].tot : null) : null);
            if (day.staCd == DataUtils.staCd.DRAFT && aprvData != null) {
                $span.html(aprvData);
            }

            $span.appendTo($box);
            $ul.appendTo($box);
            $box.appendTo($dayDataDiv);

            var $li = $("<li class='date'><span class='name'>일자</span><span>" + dd + "</span></li>");
            $li.appendTo($ul);

            $li = $("<li class='weather'><span class='name'>날씨</span><span>" + weatherNm + "</span></li>");
            $li.appendTo($ul);

            $li = $("<li class='temp'><span class='name'>기온</span><span>" + lowestTempe + "℃</span>/<span>" + highestTempe + "℃</span></li>");
            $li.appendTo($ul);

            $dayDataDiv.appendTo($day);
        });

        if ($.isFunction(callback)) {
            callback.apply(this, [data]);
        }
    }
};

/**
 * 일일 보고 마스터 객체
 * @type {{getDailyData: MasterEvent.getDailyData, init: MasterEvent.init, get_stroage_data: (function(): any), approval: MasterEvent.approval, save_storage: MasterEvent.save_storage, save: MasterEvent.save, set_val_to_data: MasterEvent.set_val_to_data, check_status: MasterEvent.check_status}}
 */
var MasterEvent = {
    init: function () {
        var box = ".master_box";
        $(document.body).on("blur", box + " .data_table input, " + box + " .data_table textarea", this.set_val_to_data);
        $(document.body).on("blur", box + " .data_table select", this.set_val_to_data);
        $(document.body).on("save_master_data", box + " .data_table", this.save_storage);

        // 일보 저장
        $(document.body).on("click", ".master_btns .btn-save-data", this.save);
        // 일보 결재
        $(document.body).on("click", ".master_btns .btn-approval-data", this.approval);
        // 일보 반려
        $(document.body).on("click", ".master_btns .btn-reject-data", this.reject);
        // 일보 변경작성
        $(document.body).on("click", ".master_btns .btn-change-data", this.verify_change_data);
        $(document.body).on("click", ".master_btns .btn-change-cancel-data", this.cancel_change_data);

        // 상태 체크
        $(document.body).on("rendered_ctrc_group", ".ctrc_group", this.rendered);

        $(document.body).on("load_master_data", box, this.load_data);
    },

    get_stroage_data: function () {
        return JSON.parse(Storage.get(Storage.keys.master));
    },

    set_val_to_data: function () {
        var $table = $(this).closest(".data_table");
        $table.data($(this).prop("name"), $(this).val());
    },

    save_storage: function () {
        var data = DataUtils.camelize($(this).data());
        Storage.set(Storage.keys.master, data);
    },

    /**
     * 일보 공통정보 및 일보제출여부 체크리스트 데이터 저장
     */
    save: function () {
        var $ctrc_group = $(this).closest(".ctrc_group"),
            ctrc_data = $ctrc_group.data(),
            master_validated = true;

        $(".master_data_table .validate", $ctrc_group).each(function (i, el) {
            var validated = $(el).parsley().on("field:validated", function () {
                if (this.validationResult !== true) {
                    this.$element.tooltip("show");
                }
            }).validate();
            if (validated !== true) {
                master_validated = false;
                return false;
            }
        });

        if (master_validated) {
            var action = $(this).hasClass("btn-save-data") ? "save" : "approval",
                confirm_message = (action == "save") ? "일보 공통정보 및 제출현황을 저장하시겠습니까?" : "상신하시겠습니까?";

            // 공통정보, 일보제출 대상 데이터 변경여부 저장
            $(".data_table", $ctrc_group).trigger("save_master_data");

            var saveDailyFieldMasterParams = {
                master: MasterEvent.get_stroage_data(),
                comps: (SubmittedEvent.get_stroage_data() == null ? [] : SubmittedEvent.get_stroage_data()),
                partnerNo: _.cdrs.storage.partnerNo()
            };
            _.dialog.confirm(confirm_message, function () {
                $.post("/daily/field/saveDailyMasterData", saveDailyFieldMasterParams, function (data) {
                    notifyUtils.show("확인", "처리되었습니다");
                    Renderer.render_ctrcs_data(data);
                });
            });
        }
    },

    approval: function () {
        var ctrc_data = $(".ctrc_group").data();
        if (!ApprovalEvent.is_possible_approval(ctrc_data)) {
            if (ctrc_data.sta_cd == DataUtils.staCd.APPROVAL || ctrc_data.sta_cd == DataUtils.staCd.APPROVE) {
                notifyUtils.show("경고", "해당 공사일보는 상신 할 수 없는 상태 입니다.");
                return;
            }
        }

        // 일보 결재는 제출대상 일보 모두가 승인이어야 가능하도록 한다.
        var comp_data = CompEvent.get_stroage_data(),
            is_approval = true;
        comp_data.forEach(function (data, i) {
            if (data.objtYn === "Y" && (data.staCd + "") != DataUtils.compStaCd.APPROVE) {
                is_approval = false;
            }
        });
        if (!is_approval) {
            notifyUtils.show("경고", "일보대상인 공사일보가 모두 승인되어야 상신 할 수 있습니다.");
            return;
        }

        ModalEvent.build_approval_modal();
    },

    reject: function (event) {
        var ctrc_data = $(".ctrc_group").data();
        if (!ApprovalEvent.is_possible_approval(ctrc_data)) {
            if (ctrc_data.sta_cd == DataUtils.staCd.APPROVAL) {
                notifyUtils.show("경고", "해당 공사일보는 반려 할 수 없는 상태 입니다.");
                return;
            }
        }
        ModalEvent.build_reject_modal(event, "field");
    },

    /**
     * 일보 데이터(협력사 전체 데이터) 조회
     * @param ddBrfg
     * @param ddBrfgComp
     */
    get_daily_all_data: function (searchDailyParams) {
        $.get("/daily/field/dailyAllData", searchDailyParams, function (data) {
            Renderer.init({mode: "view", is_page: false});
            Renderer.render_ctrcs_data(data);
        });
    },

    /**
     * 변경작성이 가능한지 데이터 상태 검증을 요청한다.
     */
    verify_change_data: function () {
        var ctrc_data = $(".ctrc_group").data(),
            searchDailyParams = {
                deptCd: ctrc_data.dept_cd,
                day: ctrc_data.day,
                ordrNo: ctrc_data.ordr_no
            };

        if (ctrc_data.sta_cd_count > 0) {
            notifyUtils.show("변경작성 불가", "결재중인 일보가 존재합니다.");

        } else {

            $.getJSON("/daily/field/verifyChangeData", searchDailyParams, function (data) {
                if (data.result.verify) {
                    var startDay = moment(data.result.startDay, "YYYYMMDD");
                    var endDay = moment(data.result.endDay, "YYYYMMDD");
                    _.dialog.confirm("변경작성 하시겠습니까?\n영향:" + startDay.format("YYYY-MM-DD") + " ~ " + endDay.format("YYYY-MM-DD") + "\n변경 작성 완료 전 일보를 작성 하실 수 없습니다.", function () {
                        $.post("/daily/field/createChangeData", searchDailyParams, function (data) {
                            notifyUtils.show("확인", "처리되었습니다");
                            $(".calenda_table_toggler").click();
                        });
                    });
                }
            });
        }
    },

    /**
     * 변경작성 취소.
     */
    cancel_change_data: function () {
        var ctrc_data = $(".ctrc_group").data(),
            is_changed = ctrc_data.ordr_no > 1,
            searchDailyParams = {
                deptCd: ctrc_data.dept_cd,
                day: ctrc_data.day,
                ordrNo: ctrc_data.ordr_no,
                chgNo: ctrc_data.chg_no
            };

        if (!is_changed || (is_changed && !(ctrc_data.sta_cd == DataUtils.staCd.CHANGE || ctrc_data.sta_cd == DataUtils.staCd.REJECT))) {
            notifyUtils.show("경고", "변경작성을 취소할 수 없습니다.");
            return;
        }

        _.dialog.confirm("변경작성을 취소 하시겠습니까?", function () {
            $.post("/daily/field/cancelChangeData", searchDailyParams, function (data) {
                notifyUtils.show("확인", "처리되었습니다");
                $(".calenda_table_toggler").click();
            });
        });
    },

    load_data: function () {
        $(".aprv_users li").each(function (i, li) {
            var $li = $(li),
                $el = $(".aprv_sta_cd", $li),
                staCdProps = DataUtils.staCdProps($li.data("sta_cd"));
            $el.addClass(staCdProps.textClass);
            $el.html(staCdProps.optionalText);
        })
    },

    /**
     * 데이터 조회 후 상태코드 값에 따른 처리
     */
    rendered: function () {
        var ctrc_data = $(this).data(),
            staCdProps = DataUtils.staCdProps(ctrc_data.sta_cd),
            staCd = ctrc_data.sta_cd + "",
            is_changed = ctrc_data.ordr_no > 1; // 변경작성이였는지 판단.

        switch (staCd) {
            case DataUtils.staCd.DRAFT:
            case DataUtils.staCd.REJECT:
            case "":
                $(".btn-reject-data, .btn-change-data, .btn-change-cancel-data", $(this)).hide();
                if (is_changed && staCd == DataUtils.staCd.REJECT) {
                    $(".btn-change-cancel-data", $(this)).show();
                }
                break;

            case DataUtils.staCd.CHANGE:
                $(".btn-reject-data, .btn-change-data", $(this)).hide();
                break;

            case DataUtils.staCd.HOLD:
                $(".btn-save-data, .btn-approval-data, .btn-reject-data, .btn-change-data, .btn-change-cancel-data", $(this)).hide();
                $(".data_table_actions", $(this)).hide();
                $("input, textarea, select", $(this)).prop("disabled", true);
                break;

            case DataUtils.staCd.APPROVAL:
            case DataUtils.staCd.APPROVE:
                $(".btn-save-data, .btn-approval-data, .btn-reject-data, .btn-change-cancel-data", $(this)).hide();
                $(".data_table_actions", $(this)).hide();
                $("input, textarea, select", $(this)).prop("disabled", true);

                if (staCd == DataUtils.staCd.APPROVAL) {
                    $(".btn-change-data, .btn-change-cancel-data", $(this)).hide();
                    // 결재대상자 이고, 승인완료 전이면 결재버튼이 활성화 되어야 한다.
                    if (ctrc_data.is_signer_seq > 0) {
                        $(".btn-reject-data, .btn-approval-data", $(this)).show();
                    }
                }

                if (staCd == DataUtils.staCd.APPROVE) {
                    $(".btn-change-data", $(this)).show();
                }
                break;

            default:
                break;
        }

        $(".master_btns", $(this)).show();
        $("span.state", $(this)).html(staCdProps.text);

        if (ctrc_data.sta_cd == "20" || ctrc_data.sta_cd == "50") {
            // 제출요청 숨김
            $(".submitted_data_table .request_submit", $(this)).hide();
        }

        $(".submitted_box .data_table_opener").click();
        $(".submitted_box .data_table_wrapper").addClass("show");
    }
};

/**
 * 일일 보고 제출현황 객체
 */
var SubmittedEvent = {
    init: function () {
        var box = ".submitted_box";
        $(document.body).on("click", box + " .data_table input", this.set_val_to_data);
        $(document.body).on("change", box + " .data_table input, " + box + " .data_table textarea", this.need_change);
        $(document.body).on("click", box + " .data_table_opener", this.render_table);
        $(document.body).on("click", box + " .company_open", this.view_company_modal);
        $(document.body).on("click", box + " td .request_submit > a", this.request_submit);
        $(document.body).on("save_master_data", box + " .data_table", this.save_storage);
        $(document.body).on("rendered_data_table", box + " .data_table", this.rendered_data_table);
    },

    pk_data: function ($table, isCamelCase) {
        return isCamelCase === true ? DataUtils.camelize($table.data()) : $table.data();
    },

    get_stroage_data: function () {
        return JSON.parse(Storage.get(Storage.keys.comp_saving));
    },

    set_val_to_data: function (event) {
        var $row = $(this).closest("tr");
        $row.data($(this).prop("name"), $(this).val());

        if ($(this).prop("type") == "checkbox") {
            $row.data($(this).prop("name"), $(this).is(":checked") ? $(this).val() : "");
        }
    },

    need_change: function () {
        var $row = $(this).closest("tr");
        if ($row.data("row_status") == "R") {
            $row.removeClass("R");
            $row.addClass("U");
            $row.data("row_status", "U");
        }
        $row.data("need_change", "true");
    },

    save_storage: function () {
        var data = [], $table = $(this);

        $(this).find("tbody tr").each(function (i, row) {
            if ($(row).data("need_change") == "true") {
                data.push($.extend(SubmittedEvent.pk_data($table, true), DataUtils.camelize($(row).data())));
            }
        });
        Storage.set(Storage.keys.comp_saving, data);
    },

    render_table: function () {
        var $box = $(this).closest(".box"),
            $table = $(".data_table", $box),
            $tbody = $("tbody", $table),
            $template = $("tfoot .template", $table);

        $tbody.empty();
        // CompEvent 객체의 스토리지를 사용해야 한다(일보제출현황은 데이터 변경이 가능하기 때문에).
        var storage_data = CompEvent.get_stroage_data();

        storage_data.forEach(function (data, i) {
            var rowStatus = data.rowStatus == null ? "R" : data.rowStatus,
                $new_row = $template.clone(),
                decamelizedData = DataUtils.decamelize(data);

            $new_row.removeClass("template");
            $new_row.addClass(rowStatus);

            var staCdProps = DataUtils.staCdProps(decamelizedData["sta_cd"], true);

            $("td[data-col='ctrc_nm']", $new_row).html(decamelizedData["ctrc_nm"]);
            $("td[data-col='partner_nm'] a", $new_row).html(decamelizedData["partner_nm"]);
            $("td[data-col='sta_cd'] span", $new_row).html(staCdProps.text);
            $("td[data-col='sta_cd'] span", $new_row).addClass(staCdProps.textClass);
            $("td[data-col='updt_dm']", $new_row).html(decamelizedData["updt_dm"]);

            if (decamelizedData["objt_yn"] == "Y") {
                $("td[data-col='objt_yn'] input", $new_row).prop("checked", true);
            } else {
                $("td[data-col='request']", $new_row).html("");
                $("td[data-col='history']", $new_row).html("");
            }

            $new_row.data("row_status", rowStatus);
            $new_row.data("ctrc_no", decamelizedData["ctrc_no"]);
            $new_row.data("partner_no", decamelizedData["partner_no"]);
            $new_row.data("objt_yn", decamelizedData["objt_yn"]);
            $new_row.data("sort_no_seq", decamelizedData["sort_no_seq"]);
            $new_row.data("cstknd_nm", decamelizedData["cstknd_nm"]);
            $new_row.data("partner_nm", decamelizedData["partner_nm"]);
            $new_row.data("sta_cd", decamelizedData["sta_cd"]);
            $new_row.appendTo($tbody);
        });
    },

    rendered_data_table: function () {
        var $table = $(this),
            $box = $(this).closest(".box");
    },

    /**
     * 제출된 협력사의 일보 정보를 조회 하여 모달에 렌더링을 한다.
     */
    view_company_modal: function () {
        var $table = $(this).closest(".data_table"),
            $tr = $(this).closest("tr");

        var searchDailyParams = $.extend(SubmittedEvent.pk_data($table, true), {
            bizhdofCd: _.cdrs.storage.bizhdofCd(),
            ctrcNo: $tr.data("ctrc_no"),
            partnerNo: $tr.data("partner_no")
        });
        ModalEvent.build_modal(searchDailyParams);
    },

    /**
     * 일보 제출요청.
     */
    request_submit: function () {
        console.log("request_submit");
    }
};

/**
 * 일일 보고 주요업무 객체
 * @type {{init: CompEvent.init, need_change: CompEvent.need_change, get_stroage_data: (function(): any), render_data_table: CompEvent.render_data_table, save_storage: CompEvent.save_storage, set_val_to_data: CompEvent.set_val_to_data, load_data: CompEvent.load_data}}
 */
var CompEvent = {
    init: function (mode) {
        var box = ".comp_box";
        $(document.body).on("blur", box + " .data_table input, " + box + " .data_table textarea", this.set_val_to_data);
        $(document.body).on("change", box + " .data_table input, " + box + " .data_table textarea", this.need_change);
        $(document.body).on("click", box + " .data_table_opener", this.render_table);
        $(document.body).on("save_data_table", box + " .data_table", this.save_storage);
        $(document.body).on("load_data", box + " .data_table_wrapper", this.load_data);
        $(document.body).on("render_data_table", box + " .data_table", this.render_data_table);
        $(document.body).on("rendered_data_table", box + " .data_table", this.rendered_data_table);
    },

    pk_data: function ($table, isCamelCase) {
        return isCamelCase === true ? DataUtils.camelize($table.data()) : $table.data();
    },

    get_stroage_data: function () {
        return JSON.parse(Storage.get(Storage.keys.comp));
    },

    set_val_to_data: function () {
        var $table = $(this).closest(".data_table");
        $table.data($(this).prop("name"), $(this).val());
    },

    need_change: function () {
        var $table = $(this).closest(".data_table");

        if ($table.data("row_status") == "R") {
            $table.removeClass("R");
            $table.addClass("U");
            $table.data("row_status", "U");
        }

        $table.data("need_change", "true");
    },

    save_storage: function (event, action) {
        if (action == "approval") {
            $(this).closest(".ctrc_group").data("sta_cd", DataUtils.compStaCd.APPROVAL);
            $(this).data("sta_cd", DataUtils.compStaCd.APPROVAL);
            $(this).data("submitter", _.cdrs.storage.loginName());
        }
        var data = DataUtils.camelize($(this).data());
        Storage.set(Storage.keys.comp, data);
    },

    render_table: function () {
        var $box = $(this).closest(".box");

        var $table = $(".view_table", $box),
            $tbody = $("tbody", $table),
            $template = $("tfoot .template", $table);

        $tbody.empty();
        var storage_data = CompEvent.get_stroage_data();
        storage_data.forEach(function (data, i) {
            if (data.objtYn == "Y") {
                var rowStatus = data.rowStatus == null ? "R" : data.rowStatus,
                    $new_row = $template.clone(),
                    decamelizedData = DataUtils.decamelize(data),
                    row_data = $new_row.data();

                $new_row.removeClass("template");
                $new_row.addClass(rowStatus);
                $new_row.find("td").each(function (i, td) {
                    $(td).html(decamelizedData[$(td).data("col")]);
                });

                $new_row.data("row_status", rowStatus);
                $new_row.appendTo($tbody);
            }
        });

        if (!Renderer.is_paging) {
            $(".page_control", $box).hide();
        } else {
            $(".page_control", $box).show();
        }
    },

    render_data_table: function () {
        var data = CompEvent.get_stroage_data();
        $(".cstknd_nm", $(this)).html(data.cstkndNm);
        $("textarea[name='perdd_main_work']", $(this)).val(data.perddMainWork).blur();
        $("textarea[name='nmdd_main_work']", $(this)).val(data.nmddMainWork).blur();
    },

    rendered_data_table: function () {
        var $table = $(this),
            $box = $(this).closest(".box");
    },

    load_data: function (e, data) {
        if (data != null) {
            Storage.set(Storage.keys.comp, data.result.comp);
        }
    }
};

/**
 * 일일 보고 출역 객체
 * @type {{init: LabatdEvent.init, need_change: LabatdEvent.need_change, add_row: LabatdEvent.add_row, pk_data: (function(*, *): *), delete_row: LabatdEvent.delete_row, render_data_table: LabatdEvent.render_data_table, select_row: LabatdEvent.select_row, save_storage: LabatdEvent.save_storage, set_val_to_data: LabatdEvent.set_val_to_data}}
 */
var LabatdEvent = {

    init: function () {
        var box = ".labatd_box";
        $(document.body).on("focusin", box + " .data_table input, " + box + " .data_table select", this.select_row);
        // $(document.body).on("blur", box + " .data_table input, " + box + " .data_table select", this.set_val_to_data);
        $(document.body).on("change", box + " .data_table input, " + box + " .data_table select", this.need_change);
        $(document.body).on("click", box + " .data_table_opener", this.render_view_table);
        $(document.body).on("click", box + " .btn-toolbar .fold", Renderer.fold_data_table_row);
        $(document.body).on("click", box + " .data_table_actions .add", this.add_row);
        $(document.body).on("click", box + " .data_table_actions .del", this.delete_row);
        $(document.body).on("save_data_table", box + " .data_table", this.save_storage);
        $(document.body).on("load_data", box + " .data_table_wrapper", this.load_data);
        $(document.body).on("change", box + " .data_list_counter", this.set_pager);
        $(document.body).on("click", box + " .page-link", this.set_page_no);
        $(document.body).on("rendered_data_table", box + " .data_table", this.rendered_data_table);
    },

    pk_data: function ($table, isCamelCase) {
        return isCamelCase === true ? DataUtils.camelize($table.data()) : $table.data();
    },

    get_stroage_data: function (key) {
        return JSON.parse(Storage.get(key));
    },

    select_row: function () {
        var $row = $(this).closest("tr");
        $(this).closest(".data_table").find("tbody tr").each(function (i, row) {
            $(row).removeClass("focus");
        });
        $row.addClass("focus");
    },

    set_val_to_data: function () {
        var $el = $(this);
        var $row = $el.closest("tr");
        $row.data($el.prop("name"), $el.val());
        if ($el.prop("type") == "checkbox") {
            $row.data($el.prop("name"), $el.is(":checked") ? $el.val() : "");
        }

        // 출역인원 성명이 변경될 때, 기존에 선택된 이름과 다를경우 신규 입력 대상으로 처리한다.
        if ($el.prop("name") == "name") {
            if ($el.val() != $el.data("selected_name")) {
                $row.data("labatd_pcnt_no", "");
            }
        } else if ($el.prop("name") == "ocpt_no") {
            if ($el.val() != $el.data("selected_ocpt_no")) {
                $row.data("labatd_pcnt_no", "");
            }
        }
        (DataUtils.get_rows_data($el.closest(".data_table")).concat(LabatdEvent.get_stroage_data(Storage.keys.labatd))).forEach(function (labatd, i) {
            if (labatd.rowStatus != "D" && $row.data("row_id") != labatd.rowId && $el.val() != "" && !($el.prop("readonly") == true || $el.prop("disabled") == true) &&
                labatd.ocptNo == $row.data("ocpt_no") && labatd.name == $row.data("name") ) {
                notifyUtils.show("경고", "이미 존재하는 데이터 입니다.");
                $el.val('');
            }
        });
    },

    need_change: function () {
        var $table = $(this).closest("table"), $row = $(this).closest("tr");
        $row.data($(this).prop("name"), $(this).val());

        if ($(this).prop("type") == "checkbox") {
            $row.data($(this).prop("name"), $(this).is(":checked") ? $(this).val() : "");
        }

        // 기존에 선택된 이름과 다를경우 신규 입력 대상으로 처리한다.
        if ($(this).prop("name") == "name") {
            if ($(this).val() != $(this).data("selected_name")) {
                $row.data("labatd_pcnt_no", "");
            }

        } else if ($(this).prop("name") == "ocpt_no") {
            if ($(this).val() != $(this).data("selected_ocpt_no")) {
                $row.data("labatd_pcnt_no", "");
            }
        }

        // 키입력 중복검사.
        if (!DataUtils.manual_dup_check( (DataUtils.get_rows_data($table).concat(LabatdEvent.get_stroage_data(Storage.keys.labatd))), $row, ['name', 'ocptNo'], true)) {
            $row.addClass("E");
        } else {
            $row.removeClass("E");
        }

        if ($row.data("row_status") == "R") {
            $row.removeClass("R");
            $row.addClass("U");
            $row.data("row_status", "U");
        }
        if (!$row.hasClass("E")) {
            $row.data("need_change", "true");
        }
    },

    save_storage: function () {
        var data = [];
        $(this).find("tbody tr").each(function (i, row) {
            if ($(row).data("need_change") == "true") {
                data.push(DataUtils.camelize($(row).data()));
            }
        });
        Storage.set(Storage.keys.labatd_saving, data);
    },

    delete_row: function () {
        var $row = $(this).closest(".data_box").find(".data_table tbody tr.focus").each(function (i, row) {
            return row;
        });

        if ($row.length == 0) {
            notifyUtils.show("경고", "삭제할 행을 클릭해주십시오.");
            return;
        }

        if ($row.data("row_status") == "I") {
            $row.remove();

        } else {
            $row.removeClass("R I U");
            $row.addClass("D");
            $row.data("row_status", "D");
            $row.data("need_change", "true");
        }

        $row.find(".validate").each(function (i, el) {
            // Remove validation tooltip
            $(el).tooltip("hide");
        });
    },

    add_row: function () {
        var $table = $(this).closest(".data_box").find(".data_table"),
            $row = $table.find(".template").clone(),
            pk_data = LabatdEvent.pk_data($table);

        var ocptCount = $(".template select[name='ocpt_no'] option", $table).length;
        if (ocptCount <= 1) {
            notifyUtils.show("확인", "직종이 등록되지 않은 공종입니다.");
            return;
        } else if (ocptCount == 2) {
            $("select option:eq(1)", $row).prop("selected", true);
        }

        $row.removeClass("template");
        $row.addClass("I");
        $row.data("row_status", "I");
        $row.data("row_id", $table.find("tbody > tr").length + 1);
        $("input.autocomplete", $row).prop("readonly", false);
        $("select", $row).prop("disabled", false);
        $("input[name='brtdy']", $row).prop("readonly", false);
        $("input[name='brtdy']", $row).inputmask("yyyy-mm-dd");
        $row.appendTo($table.find("tbody"));

        for (var pk in pk_data) {
            $row.data(pk, pk_data[pk]);
        }

        $table.trigger("rendered_data_table");
    },

    render_view_table: function () {
        var $box = $(this).closest(".box"),
            $table = $(".view_table", $box),
            $tbody = $("tbody", $table),
            $template = $("tfoot .template", $table);

        $tbody.empty();
        var storage_data = [
            LabatdEvent.get_stroage_data(Storage.keys.labatd_henc), // 한화건설 출역 데이터
            LabatdEvent.get_stroage_data(Storage.keys.labatd) // 협력사 출역 데이터
        ];

        var total = {cur_qty: 0, tot_qty: 0};
        for (var labatds in storage_data) {
            var rowspan = storage_data[labatds].length;
            var prev_row_data = {}
            storage_data[labatds].forEach(function (data, i) {
                var rowStatus = data.rowStatus == null ? "R" : data.rowStatus,
                    $new_row = $template.clone(),
                    decamelizedData = DataUtils.decamelize(data);

                if (labatds == 0) {
                    // 한화건설 Cell Merge
                    if (i == 0) {
                        $("td[data-col='partner_nm']", $new_row).prop("rowspan", rowspan);
                    } else {
                        if (i < rowspan) {
                            $("td[data-col='partner_nm']", $new_row).remove();
                        }
                    }
                } else {
                    // 협력사 공종 Row
                    if (prev_row_data.cstkndNm != data.cstkndNm) {
                        $tbody.append("<tr><th colspan='4'><i class='fas fa-caret-right'></i> " + data.cstkndNm + "</th></tr>");
                    }
                }

                $new_row.removeClass("template");
                $new_row.addClass(rowStatus);
                $new_row.find("td").each(function (i, td) {
                    $(td).html(decamelizedData[$(td).data("col")]);
                });
                $new_row.data("row_status", rowStatus);
                $new_row.appendTo($tbody);
                prev_row_data = data;
                total.cur_qty = total.cur_qty + Number(data.curQty);
                total.tot_qty = total.tot_qty + Number(data.totQty);
            });
        }
        // Total Row
        $tbody.append("<tr><th colspan='2' class='text-center'>Total</th><th class='text-right'>" + total.cur_qty + "</th><th class='text-right'>" + total.tot_qty + "</th></tr>");
        $(".page_control", $box).hide();
    },

    rendered_data_table: function () {
        var $table = $(this),
            $box = $(this).closest(".data_box");

        var searchDailyParams = LabatdEvent.pk_data($table, true);
        searchDailyParams.bizhdofCd = _.cdrs.storage.bizhdofCd();
        searchDailyParams.autocompleteSearchParams = {target: "labatd", type: "", term: ""};

        DataUtils.autocomplete(searchDailyParams, ["name", "ocptNm", "brtdy"], $box, function (data, el) {
            var is_dup = false;
            (DataUtils.get_rows_data($table, "I").concat(LabatdEvent.get_stroage_data(Storage.keys.labatd))).forEach(function (labatd, i) {
                if (labatd.rowStatus != "D" && labatd.labatdPcntNo == data.labatdPcntNo) {
                    is_dup = true;
                    notifyUtils.show("경고", "이미 존재하는 데이터 입니다.");
                    $(el).val('');
                    return false;
                }
            });
            if (is_dup) return;

            var $row = $(el).closest("tr");
            $row.data("labatd_pcnt_no", data.labatdPcntNo);
            $("input[name='name']", $row).data("selected_name", data.value);
            $("input[name='name']", $row).val(data.value).blur();
            $("select[name='ocpt_no']", $row).data("selected_ocpt_no", data.ocptNo);
            $("select[name='ocpt_no']", $row).val(data.ocptNo).blur();
            $("input[name='brtdy']", $row).data("selected_brtdy", data.brtdy);
            $("input[name='brtdy']", $row).val(data.brtdy).blur();
        });
        $(".page_control", $box).show();
    },

    load_data: function (e, data) {
        var storage_data = [];
        if (data != null) {
            storage_data = data.result.labatds;
            // 한화건설 출역 인원
            Storage.set(Storage.keys.labatd_henc, data.result.labatds_henc);
            // 협력사 출역 인원
            Storage.set(Storage.keys.labatd, storage_data);
        } else {
            storage_data = LabatdEvent.get_stroage_data(Storage.keys.labatd);
        }

        if ($(e.currentTarget).is(".table-list")) {
            var $page_navigator = $(".page_navigator", $(this).closest(".data_box"));
            $page_navigator.data("total", storage_data.length);
            PageUtils.build_navigator($page_navigator);

            $(".total_text", $(this).closest(".data_box")).html(storage_data.length);
        }
    },

    set_pager: function () {
        PageUtils.set_pager($(this).closest(".data_box"), LabatdEvent.get_stroage_data(Storage.keys.labatd));
    },

    set_page_no: function () {
        var $box = $(this).closest(".data_box"),
            $li = $(this).parent("li");

        PageUtils.set_page_no($box, $li, LabatdEvent.get_stroage_data(Storage.keys.labatd));
    }
};

/**
 * 일일 보고 주요자재 객체
 * @type {{init: MtilEvent.init, need_change: MtilEvent.need_change, add_row: MtilEvent.add_row, pk_data: (function(*, *): *), delete_row: MtilEvent.delete_row, render_data_table: MtilEvent.render_data_table, select_row: MtilEvent.select_row, save_storage: MtilEvent.save_storage, set_val_to_data: MtilEvent.set_val_to_data}}
 */
var MtilEvent = {
    init: function () {
        var box = ".mtil_box";
        $(document.body).on("focusin", box + " .data_table input, " + box + " .data_table select", this.select_row);
        $(document.body).on("change", box + " .data_table input, " + box + " .data_table select", this.need_change);
        $(document.body).on("click", box + " .data_table_opener", this.render_view_table);
        $(document.body).on("click", box + " .btn-toolbar .fold", Renderer.fold_data_table_row);
        $(document.body).on("click", box + " .data_table_actions .add", this.add_row);
        $(document.body).on("click", box + " .data_table_actions .del", this.delete_row);
        $(document.body).on("save_data_table", box + " .data_table", this.save_storage);
        $(document.body).on("load_data", box + " .data_table_wrapper", this.load_data);
        $(document.body).on("change", box + " .data_list_counter", this.set_pager);
        $(document.body).on("click", box + " .page-link", this.set_page_no);
        $(document.body).on("rendered_data_table", box + " .data_table", this.rendered_data_table);
    },

    pk_data: function ($table, isCamelCase) {
        return isCamelCase === true ? DataUtils.camelize($table.data()) : $table.data();
    },

    get_stroage_data: function () {
        return JSON.parse(Storage.get(Storage.keys.mtil));
    },

    select_row: function () {
        var $row = $(this).closest("tr");
        $(this).closest(".data_table").find("tbody tr").each(function (i, row) {
            $(row).removeClass("focus");
        });
        $row.addClass("focus");
    },

    need_change: function () {
        var $table = $(this).closest("table"), $row = $(this).closest("tr");
        $row.data($(this).prop("name"), $(this).val());

        if ($(this).prop("type") == "checkbox") {
            $row.data($(this).prop("name"), $(this).is(":checked") ? $(this).val() : "");
        }

        // 기존에 선택된 이름과 다를경우 신규 입력 대상으로 처리한다.
        if ($(this).prop("name") == "mtil_nm") {
            if ($(this).val() != $(this).data("selected_mtil_nm")) {
                $row.data("mtil_no", "");
            }
        } else if ($(this).prop("name") == "spec") {
            if ($(this).val() != $(this).data("selected_spec")) {
                $row.data("mtil_no", "");
            }
        } else if ($(this).prop("name") == "unit") {
            if ($(this).val() != $(this).data("selected_unit")) {
                $row.data("mtil_no", "");
            }
        }

        // 키입력 중복검사.
        if (!DataUtils.manual_dup_check((DataUtils.get_rows_data($table).concat(MtilEvent.get_stroage_data())), $row, ['mtilNm', 'spec', 'unit'], true)) {
            $row.addClass("E");
        } else {
            $row.removeClass("E");
        }

        if ($row.data("row_status") == "R") {
            $row.removeClass("R");
            $row.addClass("U");
            $row.data("row_status", "U");
        }

        if (!$row.hasClass("E")) {
            $row.data("need_change", "true");
        }
    },

    save_storage: function () {
        var data = [];
        $(this).find("tbody tr").each(function (i, row) {
            if ($(row).data("need_change") == "true" && !$(row).hasClass("E")) {
                data.push(DataUtils.camelize($(row).data()));
            }
        });
        Storage.set(Storage.keys.mtil_saving, data);
    },

    delete_row: function () {
        var $row = $(this).closest(".data_box").find(".data_table tbody tr.focus").each(function (i, row) {
            return row;
        });

        if ($row.length == 0) {
            notifyUtils.show("경고", "삭제할 행을 클릭해주십시오.");
            return;
        }

        if ($row.data("row_status") == "I") {
            $row.remove();

        } else {
            $row.removeClass("R I U");
            $row.addClass("D");
            $row.data("row_status", "D");
            $row.data("need_change", "true");
        }

        $row.find(".validate").each(function (i, el) {
            // Remove validation tooltip
            $(el).tooltip("hide");
        });
    },

    add_row: function () {
        var $table = $(this).closest(".data_box").find(".data_table"),
            $row = $table.find(".template").clone(),
            pk_data = MtilEvent.pk_data($table);
        $row.removeClass("template");
        $row.addClass("I");
        $row.data("row_status", "I");
        $row.data("row_id", $table.find("tbody > tr").length + 1);
        $("input.autocomplete", $row).prop("readonly", false);
        $row.appendTo($table.find("tbody"));

        for (var pk in pk_data) {
            $row.data(pk, pk_data[pk]);
        }
        // 사업본부코드 셋팅
        $row.data("bizhdof_cd", _.cdrs.storage.bizhdofCd());
        $table.trigger("rendered_data_table");
    },

    render_view_table: function () {
        var $box = $(this).closest(".box");

        var $table = $(".view_table", $box),
            $tbody = $("tbody", $table),
            $template = $("tfoot .template", $table);

        $tbody.empty();
        var storage_data = MtilEvent.get_stroage_data();

        storage_data.forEach(function (data, i) {
            var rowStatus = data.rowStatus == null ? "R" : data.rowStatus,
                $new_row = $template.clone(),
                decamelizedData = DataUtils.decamelize(data),
                row_data = $new_row.data();

            $new_row.removeClass("template");
            $new_row.addClass(rowStatus);
            $new_row.find("td").each(function (i, td) {
                $(td).html(decamelizedData[$(td).data("col")]);
            });

            $new_row.data("row_status", rowStatus);
            $new_row.appendTo($tbody);
        });

        $(".page_control", $box).hide();
    },

    rendered_data_table: function () {
        var $table = $(this),
            $box = $(this).closest(".data_box");

        var searchDailyParams = LabatdEvent.pk_data($table, true);
        searchDailyParams.bizhdofCd = _.cdrs.storage.bizhdofCd();
        searchDailyParams.autocompleteSearchParams = {target: "mtil", type: "", term: ""};

        DataUtils.autocomplete(searchDailyParams, ["mtilNm", "spec", "unit"], $box, function (data, el) {
            var is_dup = false;
            (DataUtils.get_rows_data($table, "I").concat(MtilEvent.get_stroage_data())).forEach(function (mtil, i) {
                if (mtil.rowStatus != "D" && (mtil.mtilNo == data.mtilNo || (mtil.mtilNm == data.value && mtil.spec == data.parentSpec && mtil.unit == data.parentUnit))) {
                    is_dup = true;
                    notifyUtils.show("경고", (data.gb == "CHILD" ? "동의어가 존재하는 데이터 입니다." : "이미 등록된 데이터 입니다."));
                    $(el).val('');
                    return false;
                }
            });
            if (is_dup) return;

            var $row = $(el).closest("tr");
            $row.data("mtil_no", data.parentMtilNo);
            $("input[name='mtil_nm']", $row).data("selected_mtil_nm", data.value);
            $("input[name='mtil_nm']", $row).val(data.value).change();
            $("input[name='unit']", $row).data("selected_unit", data.parentUnit);
            $("input[name='unit']", $row).val(data.parentUnit).change();
            $("input[name='spec']", $row).data("selected_spec", data.parentSpec);
            $("input[name='spec']", $row).val(data.parentSpec).change();
        });
    },

    load_data: function (e, data) {
        var storage_data = [];
        if (data != null) {
            storage_data = data.result.mtils;
            Storage.set(Storage.keys.mtil, storage_data);
        } else {
            storage_data = MtilEvent.get_stroage_data();
        }

        if ($(e.currentTarget).is(".table-list")) {
            var $page_navigator = $(".page_navigator", $(this).closest(".data_box"));
            $page_navigator.data("total", storage_data.length);
            PageUtils.build_navigator($(".page_navigator", $(this).closest(".data_box")));

            $(".total_text", $(this).closest(".data_box")).html(storage_data.length);
        }
    },

    set_pager: function () {
        PageUtils.set_pager($(this).closest(".data_box"), MtilEvent.get_stroage_data());
    },

    set_page_no: function () {
        var $box = $(this).closest(".data_box"),
            $li = $(this).parent("li");

        PageUtils.set_page_no($box, $li, MtilEvent.get_stroage_data());
    }
};

/**
 * 일일 보고 주요장비 객체
 * @type {{init: EqipEvent.init, need_change: EqipEvent.need_change, add_row: EqipEvent.add_row, pk_data: (function(*, *): *), delete_row: EqipEvent.delete_row, render_data_table: EqipEvent.render_data_table, select_row: EqipEvent.select_row, save_storage: EqipEvent.save_storage, set_val_to_data: EqipEvent.set_val_to_data}}
 */
var EqipEvent = {
    init: function () {
        var box = ".eqip_box";
        $(document.body).on("focusin", box + " .data_table input, " + box + " .data_table select", this.select_row);
        $(document.body).on("change", box + " .data_table input, " + box + " .data_table select", this.need_change);
        $(document.body).on("click", box + " .data_table_opener", this.render_view_table);
        $(document.body).on("click", box + " .btn-toolbar .fold", Renderer.fold_data_table_row);
        $(document.body).on("click", box + " .data_table_actions .add", this.add_row);
        $(document.body).on("click", box + " .data_table_actions .del", this.delete_row);
        $(document.body).on("save_data_table", box + " .data_table", this.save_storage);
        $(document.body).on("load_data", box + " .data_table_wrapper", this.load_data);
        $(document.body).on("change", box + " .data_list_counter", this.set_pager);
        $(document.body).on("click", box + " .page-link", this.set_page_no);
        $(document.body).on("rendered_data_table", box + " .data_table", this.rendered_data_table);
    },

    pk_data: function ($table, isCamelCase) {
        return isCamelCase === true ? DataUtils.camelize($table.data()) : $table.data();
    },

    get_stroage_data: function () {
        return JSON.parse(Storage.get(Storage.keys.eqip));
    },

    select_row: function () {
        var $row = $(this).closest("tr");
        $(this).closest(".data_table").find("tbody tr").each(function (i, row) {
            $(row).removeClass("focus");
        });
        $row.addClass("focus");
    },

    need_change: function () {
        var $table = $(this).closest("table"), $row = $(this).closest("tr");
        $row.data($(this).prop("name"), $(this).val());

        if ($(this).prop("type") == "checkbox") {
            $row.data($(this).prop("name"), $(this).is(":checked") ? $(this).val() : "");
        }

        // 기존에 선택된 이름과 다를경우 신규 입력 대상으로 처리한다.
        if ($(this).prop("name") == "eqip_nm") {
            if ($(this).val() != $(this).data("selected_eqip_nm")) {
                $row.data("eqip_no", "");
            }
        } else if ($(this).prop("name") == "spec") {
            if ($(this).val() != $(this).data("selected_spec")) {
                $row.data("eqip_no", "");
            }
        } else if ($(this).prop("name") == "unit") {
            if ($(this).val() != $(this).data("selected_unit")) {
                $row.data("eqip_no", "");
            }
        }

        // 키입력 중복검사.
        if (!DataUtils.manual_dup_check((DataUtils.get_rows_data($table).concat(EqipEvent.get_stroage_data())), $row, ['eqipNm', 'spec', 'unit'], true)) {
            $row.addClass("E");
        } else {
            $row.removeClass("E");
        }

        if ($row.data("row_status") == "R") {
            $row.removeClass("R");
            $row.addClass("U");
            $row.data("row_status", "U");
        }

        if (!$row.hasClass("E")) {
            $row.data("need_change", "true");
        }
    },

    save_storage: function () {
        var data = [];
        $(this).find("tbody tr").each(function (i, row) {
            if ($(row).data("need_change") == "true" && !$(row).hasClass("E")) {
                data.push(DataUtils.camelize($(row).data()));
            }
        });
        Storage.set(Storage.keys.eqip_saving, data);
    },

    delete_row: function () {
        var $row = $(this).closest(".data_box").find(".data_table tbody tr.focus").each(function (i, row) {
            return row;
        });

        if ($row.length == 0) {
            notifyUtils.show("경고", "삭제할 행을 클릭해주십시오.");
            return;
        }

        if ($row.data("row_status") == "I") {
            $row.remove();

        } else {
            $row.removeClass("R I U");
            $row.addClass("D");
            $row.data("row_status", "D");
            $row.data("need_change", "true");
        }

        $row.find(".validate").each(function (i, el) {
            // Remove validation tooltip
            $(el).tooltip("hide");
        });
    },

    add_row: function () {
        var $table = $(this).closest(".data_box").find(".data_table"),
            $row = $table.find(".template").clone(),
            pk_data = EqipEvent.pk_data($table);
        $row.removeClass("template");
        $row.addClass("I");
        $row.data("row_status", "I");
        $row.data("row_id", $table.find("tbody > tr").length + 1);
        $("input.autocomplete", $row).prop("readonly", false);
        $row.appendTo($table.find("tbody"));

        for (var pk in pk_data) {
            $row.data(pk, pk_data[pk]);
        }

        // 사업본부코드 셋팅
        $row.data("bizhdof_cd", _.cdrs.storage.bizhdofCd());
        $table.trigger("rendered_data_table");
    },

    render_view_table: function () {
        var $box = $(this).closest(".box");
        var $table = $(".view_table", $box),
            $tbody = $("tbody", $table),
            $template = $("tfoot .template", $table);

        $tbody.empty();
        var storage_data = EqipEvent.get_stroage_data();

        var prev_row_data = {};
        storage_data.forEach(function (data, i) {
            var header_title = "";
            if (data.directOperDivCd == "10") {
                header_title = "직영장비투입";
            } else {
                header_title = "지입장비투입";
            }

            if (prev_row_data.directOperDivCd != data.directOperDivCd) {
                $tbody.append("<tr><th colspan='6'><i class='fas fa-caret-right'></i> " + header_title + "</th></tr>");
            }

            var rowStatus = data.rowStatus == null ? "R" : data.rowStatus,
                $new_row = $template.clone(),
                decamelizedData = DataUtils.decamelize(data);

            $new_row.removeClass("template");
            $new_row.addClass(rowStatus);
            $new_row.find("td").each(function (i, td) {
                $(td).html(decamelizedData[$(td).data("col")]);
            });

            $new_row.data("row_status", rowStatus);
            $new_row.appendTo($tbody);
            prev_row_data = data;
        });

        $(".page_control", $box).hide();
    },

    rendered_data_table: function () {
        var $table = $(this),
            $box = $(this).closest(".data_box");

        var searchDailyParams = EqipEvent.pk_data($table, true);
        searchDailyParams.bizhdofCd = _.cdrs.storage.bizhdofCd();
        searchDailyParams.autocompleteSearchParams = {target: "eqip", type: "", term: ""};

        DataUtils.autocomplete(searchDailyParams, ["eqipNm", "spec", "unit"], $box, function (data, el) {
            var is_dup = false;
            (DataUtils.get_rows_data($table, "I").concat(EqipEvent.get_stroage_data())).forEach(function (eqip, i) {
                if (eqip.rowStatus != "D" && (eqip.eqipNo == data.eqipNo || (eqip.eqipNm == data.value && eqip.spec == data.parentSpec && eqip.unit == data.parentUnit))) {
                    is_dup = true;
                    notifyUtils.show("경고", (data.gb == "CHILD" ? "동의어가 존재하는 데이터 입니다." : "이미 등록된 데이터 입니다."));
                    $(el).val('');
                    return false;
                }
            });
            if (is_dup) return;

            var $row = $(el).closest("tr");
            $row.data("eqip_no", data.parentEqipNo);
            $("input[name='eqip_nm']", $row).data("selected_eqip_nm", data.value);
            $("input[name='eqip_nm']", $row).val(data.value).change();
            $("input[name='unit']", $row).data("selected_unit", data.parentUnit);
            $("input[name='unit']", $row).val(data.parentUnit).change();
            $("input[name='spec']", $row).data("selected_spec", data.parentSpec);
            $("input[name='spec']", $row).val(data.parentSpec).change();
        });
    },

    load_data: function (e, data) {
        var storage_data = [];
        if (data != null) {
            storage_data = data.result.eqips;
            Storage.set(Storage.keys.eqip, storage_data);
        } else {
            storage_data = EqipEvent.get_stroage_data();
        }

        if ($(e.currentTarget).is(".table-list")) {
            var $page_navigator = $(".page_navigator", $(this).closest(".data_box"));
            $page_navigator.data("total", storage_data.length);
            PageUtils.build_navigator($(".page_navigator", $(this).closest(".data_box")));

            $(".total_text", $(this).closest(".data_box")).html(storage_data.length);
        }
    },

    set_pager: function () {
        PageUtils.set_pager($(this).closest(".data_box"), EqipEvent.get_stroage_data());
    },

    set_page_no: function () {
        var $box = $(this).closest(".data_box"),
            $li = $(this).parent("li");

        PageUtils.set_page_no($box, $li, EqipEvent.get_stroage_data());
    }
};

/**
 * 일일 보고 공사물량 객체
 * @type {{init: QtyEvent.init, need_change: QtyEvent.need_change, add_row: QtyEvent.add_row, pk_data: (function(*, *): *), delete_row: QtyEvent.delete_row, render_data_table: QtyEvent.render_data_table, select_row: QtyEvent.select_row, save_storage: QtyEvent.save_storage, set_val_to_data: QtyEvent.set_val_to_data}}
 */
var QtyEvent = {
    init: function () {
        var box = ".qty_box";
        $(document.body).on("focusin", box + " .data_table input, " + box + " .data_table select", this.select_row);
        $(document.body).on("change", box + " .data_table input, " + box + " .data_table select", this.need_change);
        $(document.body).on("click", box + " .data_table_opener", this.render_view_table);
        $(document.body).on("click", box + " .btn-toolbar .fold", Renderer.fold_data_table_row);
        $(document.body).on("click", box + " .data_table_actions .add", this.add_row);
        $(document.body).on("click", box + " .data_table_actions .del", this.delete_row);
        $(document.body).on("save_data_table", box + " .data_table", this.save_storage);
        $(document.body).on("load_data", box + " .data_table_wrapper", this.load_data);
        $(document.body).on("change", box + " .data_list_counter", this.set_pager);
        $(document.body).on("click", box + " .page-link", this.set_page_no);
        $(document.body).on("rendered_data_table", box + " .data_table", this.rendered_data_table);
    },

    pk_data: function ($table, isCamelCase) {
        return isCamelCase === true ? DataUtils.camelize($table.data()) : $table.data();
    },

    get_stroage_data: function () {
        return JSON.parse(Storage.get(Storage.keys.qty));
    },

    select_row: function () {
        var $row = $(this).closest("tr");
        $(this).closest(".data_table").find("tbody tr").each(function (i, row) {
            $(row).removeClass("focus");
        });
        $row.addClass("focus");
    },

    need_change: function () {
        var $table = $(this).closest("table"), $row = $(this).closest("tr");
        $row.data($(this).prop("name"), $(this).val());

        if ($(this).prop("type") == "checkbox") {
            $row.data($(this).prop("name"), $(this).is(":checked") ? $(this).val() : "");
        }

        if ($(this).prop("name") == "qty_nm") {
            if ($(this).val() != $(this).data("selected_qty_nm")) {
                $row.data("qty_no", "");
            }
        } else if ($(this).prop("name") == "spec") {
            if ($(this).val() != $(this).data("selected_spec")) {
                $row.data("qty_no", "");
            }
        } else if ($(this).prop("name") == "unit") {
            if ($(this).val() != $(this).data("selected_unit")) {
                $row.data("qty_no", "");
            }
        }

        // 키입력 중복검사.
        if (!DataUtils.manual_dup_check((DataUtils.get_rows_data($table).concat(QtyEvent.get_stroage_data())), $row, ['qtyNm', 'spec', 'unit'], true)) {
            $row.addClass("E");
        } else {
            $row.removeClass("E");
        }

        if ($row.data("row_status") == "R") {
            $row.removeClass("R");
            $row.addClass("U");
            $row.data("row_status", "U");
        }

        if (!$row.hasClass("E")) {
            $row.data("need_change", "true");
        }
    },

    save_storage: function () {
        var data = [];
        $(this).find("tbody tr").each(function (i, row) {
            if ($(row).data("need_change") == "true" && !$(row).hasClass("E")) {
                data.push(DataUtils.camelize($(row).data()));
            }
        });
        Storage.set(Storage.keys.qty_saving, data);
    },

    delete_row: function () {
        var $row = $(this).closest(".data_box").find(".data_table tbody tr.focus").each(function (i, row) {
            return row;
        });

        if ($row.length == 0) {
            notifyUtils.show("경고", "삭제할 행을 클릭해주십시오.");
            return;
        }

        if ($row.data("row_status") == "I") {
            $row.remove();

        } else {
            $row.removeClass("R I U");
            $row.addClass("D");
            $row.data("row_status", "D");
            $row.data("need_change", "true");
        }

        $row.find(".validate").each(function (i, el) {
            // Remove validation tooltip
            $(el).tooltip("hide");
        });
    },

    add_row: function () {
        var $table = $(this).closest(".data_box").find(".data_table"),
            $row = $table.find(".template").clone(),
            pk_data = QtyEvent.pk_data($table);
        $row.removeClass("template");
        $row.addClass("I");
        $row.data("row_status", "I");
        $row.data("row_id", $table.find("tbody > tr").length + 1);
        $("input.autocomplete", $row).prop("readonly", false);
        $row.appendTo($table.find("tbody"));

        for (var pk in pk_data) {
            $row.data(pk, pk_data[pk]);
        }

        $table.trigger("rendered_data_table");
    },

    render_view_table: function () {
        var $box = $(this).closest(".box");

        var $table = $(".view_table", $box),
            $tbody = $("tbody", $table),
            $template = $("tfoot .template", $table);

        $tbody.empty();
        var storage_data = QtyEvent.get_stroage_data();

        storage_data.forEach(function (data, i) {
            var rowStatus = data.rowStatus == null ? "R" : data.rowStatus,
                $new_row = $template.clone(),
                decamelizedData = DataUtils.decamelize(data),
                row_data = $new_row.data();

            $new_row.removeClass("template");
            $new_row.addClass(rowStatus);
            $new_row.find("td").each(function (i, td) {
                $(td).html(decamelizedData[$(td).data("col")]);
            });

            $new_row.data("row_status", rowStatus);
            $new_row.appendTo($tbody);
        });

        $(".page_control", $box).hide();
    },

    rendered_data_table: function () {
        var $table = $(this),
            $box = $(this).closest(".data_box");

        var searchDailyParams = LabatdEvent.pk_data($table, true);
        searchDailyParams.bizhdofCd = _.cdrs.storage.bizhdofCd();
        searchDailyParams.autocompleteSearchParams = {target: "qty", type: "", term: ""};

        DataUtils.autocomplete(searchDailyParams, ["qtyNm", "spec", "unit"], $box, function (data, el) {
            var is_dup = false;
            (DataUtils.get_rows_data($table, "I").concat(QtyEvent.get_stroage_data())).forEach(function (qty, i) {
                if (qty.rowStatus != "D" && qty.qtyNo == data.qtyNo) {
                    is_dup = true;
                    notifyUtils.show("경고", "이미 존재하는 데이터 입니다.");
                    $(el).val('');
                    return false;
                }
            });
            if (is_dup) return;

            var $row = $(el).closest("tr");
            $row.data("qty_no", data.qtyNo);
            $("input[name='qty_nm']", $row).data("selected_qty_nm", data.value);
            $("input[name='qty_nm']", $row).val(data.value).change();
            $("input[name='unit']", $row).data("selected_unit", data.unit);
            $("input[name='unit']", $row).val(data.unit).change();
            $("input[name='spec']", $row).data("selected_spec", data.spec);
            $("input[name='spec']", $row).val(data.spec).change();
        });
    },

    load_data: function (e, data) {
        var storage_data = [];
        if (data != null) {
            storage_data = data.result.qties;
            Storage.set(Storage.keys.qty, storage_data);
        } else {
            storage_data = QtyEvent.get_stroage_data();
        }

        if ($(e.currentTarget).is(".table-list")) {
            var $page_navigator = $(".page_navigator", $(this).closest(".data_box"));
            $page_navigator.data("total", storage_data.length);
            PageUtils.build_navigator($(".page_navigator", $(this).closest(".data_box")));

            $(".total_text", $(this).closest(".data_box")).html(storage_data.length);
        }
    },

    set_pager: function () {
        PageUtils.set_pager($(this).closest(".data_box"), QtyEvent.get_stroage_data());
    },

    set_page_no: function () {
        var $box = $(this).closest(".data_box"),
            $li = $(this).parent("li");

        PageUtils.set_page_no($box, $li, QtyEvent.get_stroage_data());
    }
};

/**
 * 협력사 모달 데이터 이벤트 처리 객체
 */
var ModalEvent = {
    init: function () {
        $(document.body).on("rendered_modal", this.check_modal_status);

        $(document.body).on("click", "#company-modify-modal .btn-modal-save-confirm", this.confirm_modal_save);
        $(document.body).on("click", ".btn-modal-save", "#company-modify-reason-modal", this.modal_save);
        $(document.body).on("click", "#company-modify-modal .btn-modal-approve", this.partner_approve);
        $(document.body).on("click", "#company-modify-modal .btn-modal-reject", this.build_reject_modal);
        $(document.body).on("click", "#company-modify-modal .btn-modal-cancel-approve", this.cancel_approve);
        $(document.body).on("click", "#company-modify-modal .btn-modal-copy", this.build_modal_history);
        // 작업이력 복사 생성
        $(document.body).on("click", "#hitoryModal .btn-copy-create", this.copy_history_data);

        // 반려 모달 버튼 액션
        $(document.body).on("click", "#modal-return .btn-cancel", this.cancel_reject);
        $(document.body).on("click", "#modal-return .btn-reject", this.reject);

        // 상신 모달 버튼 액션
        $(document.body).on("click", "#modal-approval .btn-search", this.search_users);
        $(document.body).on("click", "#modal-approval .btn-approval", this.approval);
        $(document.body).on("click", "#modal-approval .page-link", this.search_users_set_page_no);

        $(document.body).on("hidden.bs.modal", "#company-modify-modal", this.after_close_modal);

        $(document.body).on("show.bs.modal", "#modal-change-history", this.build_modal_change_history);
        // $("#modal-return").on("hidden.bs.modal", this.after_close_modal);
    },

    build_modal: function (searchDailyParams) {
        var $modal = $("#company-modify-modal");

        $.getJSON("/daily/field/dailyModalData", searchDailyParams, function (data) {
            $modal.data("ctrc_no", searchDailyParams.ctrcNo);
            $modal.data("partner_no", searchDailyParams.partnerNo);
            $modal.data("partner_nm", data.result.ctrc.coprcpNm);
            $modal.data("ctrc_nm", data.result.ctrc.ctrcNm);
            $modal.data("cstknd_nm", data.result.ctrc.cstkndNm);
            $modal.data("sta_cd", data.result.comp.staCd);
            $modal.data("sort_no_seq", data.result.comp.sortNoSeq);
            $modal.data("objt_yn", data.result.comp.objtYn);

            $modal.modal({backdrop: 'static'});
            var $modal_body = $(".modal-body", $modal);
            $modal_body.html(data.html);
            Renderer.set_paging(true);

            // 로드된 Data를 Storage에 적재하고, 데이터 테이블의 페이징을 초기화 한다.
            $(".data_table_wrapper", $modal).trigger("load_data", [data]);

            // 데이터 테이블을 랜더링 한다.
            $(".comp_box .data_table", $modal).trigger("render_data_table");
            $(".data_box:not(.comp_box)", $modal).each(function (i, box) {
                Renderer.render_data_table($(box), JSON.parse(Storage.get(Storage.keys[$(box).data("storage_key")])));
            });
            $modal.trigger("rendered_modal");
        });
    },

    /**
     * 일보 모달 닫힌 후 이벤트 처리.
     */
    after_close_modal: function () {
        var data = $(".ctrc_group.active").data(),
            searchDailyParams = {
                deptCd: _.cdrs.storage.deptCd(),
                bizhdofCd: _.cdrs.storage.bizhdofCd(),
                day: data.day,
                ordrNo: data.ordr_no,
                partnerNo: data.partner_no,
                ctrcNo: "" // null string!
            };
        // 일보 데이터 재조회(및 렌더링) 실행
        MasterEvent.get_daily_all_data(searchDailyParams);
    },

    /**
     * 저장 사유 모달 생성.
     * 협력사 일보 모달 데이터를 저장하기 전,
     * 사용자에게 저장사유를 입력받기 위한 모달을 생성하고, 저장 처리 진행.
     */
    confirm_modal_save: function () {
        var $modal = $("#company-modify-modal");
        $(".data_table", $modal).trigger("save_data_table", ["save"]);
        if (Storage.before_submit()) {
            $("#company-modify-reason-modal").modal({backdrop: 'static', keyboard: false});
        }
    },

    /**
     * 일보 모달 데이터 저장.
     */
    modal_save: function (event) {
        var company_modify_modal_data = $("#company-modify-modal").data();

        if (Storage.before_submit()) {
            var dailySaveParams = {
                comp: JSON.parse(Storage.get(Storage.keys.comp_saving)),
                labatds: JSON.parse(Storage.get(Storage.keys.labatd_saving)),
                eqips: JSON.parse(Storage.get(Storage.keys.eqip_saving)),
                mtils: JSON.parse(Storage.get(Storage.keys.mtil_saving)),
                qties: (DataUtils.is_plant ? JSON.parse(this.get(this.keys.qty_saving)) : []),
                chgRsn: $("#company-modify-reason-modal textarea[name='chg_rsn']").val(),
                action: "save"
            };

            $.post("/daily/field/saveDailyModalData", dailySaveParams, function (data) {
                $("#company-modify-reason-modal").modal("hide");
                ModalEvent.build_modal({
                    deptCd: company_modify_modal_data.dept_cd,
                    day: company_modify_modal_data.day,
                    ordrNo: company_modify_modal_data.ordr_no,
                    partnerNo: company_modify_modal_data.partner_no,
                    ctrcNo: company_modify_modal_data.ctrc_no,
                    staCd: company_modify_modal_data.sta_cd
                });
                notifyUtils.show("확인", "처리되었습니다");
            });
        }
    },

    /**
     * 현장담당 공사일보 승인.
     */
    partner_approve: function () {
        var modal_data = $("#company-modify-modal").data();
        var saveDailyApprovalParams = {
            deptCd: modal_data.dept_cd,
            day: modal_data.day,
            ordrNo: modal_data.ordr_no,
            partnerNo: modal_data.partner_no,
            ctrcNo: modal_data.ctrc_no,
            staCd: DataUtils.compStaCd.APPROVE,
            rsn: DataUtils.staCdProps(DataUtils.compStaCd.APPROVE).text
        };
        _.dialog.confirm("공사일보를 승인하시겠습니까?", function () {
            ApprovalEvent.partner_approve(saveDailyApprovalParams);
        });
    },

    /**
     * 현장담당 공사일보 상신 모달 생성.
     */
    build_approval_modal: function () {
        var $modal = $("#modal-approval");
        var ctrc_data = $(".ctrc_group").data();
        var aprvDt = moment().format('YYYY[-]MM[-]DD');

        $(".page_navigator", $modal).empty();

        var $table = $(".data_table", $modal),
            $tbody = $("tbody", $table);

        $tbody.empty();
        var $new_row = $("tfoot .template_nodata", $table).clone();
        $new_row.removeClass("template_nodata");
        $new_row.appendTo($tbody);

        if (ctrc_data.is_signer_seq > 0 && (ctrc_data.sta_cd + "") == DataUtils.staCd.APPROVAL) {
            $(".modal-title", $modal).html("일보 승인");
            $(".btn-approval", $modal).html("승인");
            $(".btn-approval", $modal).data("appr_action", "approve");
        } else {
            $(".modal-title", $modal).html("일보 상신");
            $(".btn-approval", $modal).html("상신");
            $(".btn-approval", $modal).data("appr_action", "approval");
        }

        $modal.data("ctrc_no", ctrc_data.ctrc_no);
        $modal.data("partner_no", ctrc_data.partner_no);
        $(".aprv_dt", $modal).html(aprvDt);
        $modal.modal({backdrop: 'static', keyboard: false});

        $(document.body).on("keydown", "input[name='search']", $modal, function (key) {
            if (key.keyCode == 13) {
                ModalEvent.search_users();
            }
        });

        $(document.body).on("change", ".aprv_type", $modal, function () {
            if ($(this).val() == "complete") {
                $(".aprv_type_next").hide();
            } else {
                $(".aprv_type_next").show();
            }
        }).change();
    },

    /**
     * 일보 반려 모달 생성.
     */
    build_reject_modal: function (event, who) {
        var $modal = $("#modal-return"), modal_data = $modal.data(), parent_data = $("#company-modify-modal").data();
        var aprvDt = moment().format('YYYY[-]MM[-]DD');
        // 협력사 반려와, 현장담당 반려 공통으로 사용하기에 구분자가 필요함.

        if (who == "field") {
            $modal.data("who", who);

        } else {
            if (parent_data.sta_cd != DataUtils.compStaCd.APPROVAL) {
                notifyUtils.show("경고", "해당 공사일보는 반려를 할 수 없는 상태 입니다.");
                return;
            }
            $modal.data("ctrc_no", parent_data.ctrc_no);
            $modal.data("partner_no", parent_data.partner_no);

            // 협력사 반려는 버튼에서 바로 모달 생성이 호출되므로 who 비교 값이 undefined 이다.
            $modal.data("who", "partner");
        }

        $(".aprv_dt", $modal).html(aprvDt);
        $modal.modal({backdrop: 'static', keyboard: false});
    },

    /**
     * 현장담당 공사일보 상신.
     */
    approval: function () {
        var $modal = $("#modal-approval"), modal_data = $modal.data(), ctrc_data = $(".ctrc_group").data(),
            $table = $(".data_table", $modal),
            action = $(this).data("appr_action"),
            aprv_type = $(".aprv_type", $modal).val(),
            aprv_dt = moment().format('YYYY[-]MM[-]DD'),
            is_complete = (action == "approve" && aprv_type == "complete" ? true : false),
            sta_cd = (is_complete ? DataUtils.staCd.APPROVE : DataUtils.staCd.APPROVAL),
            confirm_message = (is_complete == true ? "최종 승인하시겠습니까?" : action == "approval" ? "상신하시겠습니까?" : "승인하시겠습니까?");

        // 차상위 결재자를 지정하였을 경우, 결재자가 선택되었는지 체크해야 한다.
        if ($(":radio[name='approve_user']:checked", $table).length == 0 && aprv_type == "next") {
            notifyUtils.show("경고", "결재자를 선택해주십시오.");
            return;
        }

        var saveDailyApprovalParams = {
            deptCd: modal_data.dept_cd,
            day: modal_data.day,
            ordrNo: modal_data.ordr_no,
            partnerNo: modal_data.partner_no,
            ctrcNo: modal_data.ctrc_no,
            staCd: sta_cd,
            rsn: "",
            empeNo: $(":radio[name='approve_user']:checked", $table).val(),
            aprvDt: aprv_dt,
            seq: ctrc_data.is_signer_seq,
            chgNo: ctrc_data.chg_no,
            chgSeq: ctrc_data.chg_seq,
            weatherCd: $(".master_data_table select[name='weather_cd'] option:selected").val(),
            lowestTempe: $(".master_data_table input[name='lowest_tempe']").val(),
            highestTempe: $(".master_data_table input[name='highest_tempe']").val(),
            ptclMtr: $(".master_data_table textarea[name='ptcl_mtr']").val()
        };
        _.dialog.confirm(confirm_message, function () {
            ApprovalEvent.field_approve(saveDailyApprovalParams, is_complete);
        });
    },

    /**
     * 공사일보 협력사 승인취소.
     */
    cancel_approve: function () {
        var $modal = $("#company-modify-modal"), modal_data = $modal.data(), ctrc_data = $(".ctrc_group").data(),
            aprv_dt = moment().format('YYYY[-]MM[-]DD');

        var saveDailyApprovalParams = {
            deptCd: modal_data.dept_cd,
            day: modal_data.day,
            ordrNo: modal_data.ordr_no,
            partnerNo: modal_data.partner_no,
            ctrcNo: modal_data.ctrc_no,
            staCd: DataUtils.compStaCd.APPROVAL,
            rsn: "승인취소",
            aprvDt: aprv_dt
        };

        _.dialog.confirm("승인취소 하시겠습니까?", function () {
            ApprovalEvent.cancel_approve(saveDailyApprovalParams);
        });
    },

    /**
     * 결재자 검색.
     * @param event
     * @param init_page
     */
    search_users: function (event, init_page) {
        var $navigator = $("#modal-approval .page_navigator");
        if (typeof init_page === "undefined") {
            $navigator.data("page_no", 1);
            $navigator.data("current_page", 1);
        }

        var page_no = $navigator.data("page_no"),
            page_list_count = $navigator.data("page_list_count");

        var searchApprovalUserParams = {
            search: $("#modal-approval input[name='search']").val(),
            first: (page_no * page_list_count) - page_list_count + 1,
            last: page_no * page_list_count
        };

        $.post("/daily/field/approvalSearchUsers", searchApprovalUserParams, function (data) {
            var $navigator = $("#modal-approval .page_navigator");
            $navigator.data("total", data.result.total);
            PageUtils.build_navigator($navigator);

            var $table = $("#modal-approval .data_table"),
                $tbody = $("tbody", $table),
                $template = $("tfoot .template", $table);

            $tbody.empty();
            if (data.result.list.length == 0) {
                var $new_row = $("tfoot .template_nodata", $table).clone();
                $new_row.removeClass("template_nodata");
                $new_row.appendTo($tbody);
            }

            data.result.list.forEach(function (user, i) {
                var $new_row = $template.clone();
                $new_row.removeClass("template");
                $("td.check input", $new_row).val(user.empNo);
                $("td.emp_no", $new_row).html(user.empNo);
                $("td.name", $new_row).html(user.nameKo);
                $("td.position", $new_row).html(user.positionName);
                $new_row.appendTo($tbody);
            });

        });
    },

    /**
     * 결재자 테이블 페이지 선택.
     * @param event
     */
    search_users_set_page_no: function (event) {
        var $box = $(this).closest("section"),
            $li = $(this).parent("li"),
            $page_navigator = $(this).parent(".page_navigator");

        PageUtils.set_page_no($box, $li);
        ModalEvent.search_users(event, false);
    },

    cancel_reject: function () {
        $("#modal-return").modal("hide");
    },

    reject: function () {
        var modal_data = $("#modal-return").data(), ctrc_data = $(".ctrc_group").data(),
            aprv_dt = moment().format('YYYY[-]MM[-]DD'),
            is_changed = modal_data.ordr_no > 1, // 변경작성이였는지 판단.
            saveDailyApprovalParams = {
                deptCd: modal_data.dept_cd,
                day: modal_data.day,
                ordrNo: modal_data.ordr_no,
                aprvDt: aprv_dt,
                rsn: $("textarea[name='return_rsn']", $("#modal-return")).val()
            };

        if (modal_data.who == "field") {

            if (is_changed) {
                saveDailyApprovalParams.chgNo = ctrc_data.chg_no; // dd_brfg.chg_no, dd_brfg_chg_mtr.chg_no
                saveDailyApprovalParams.chgSeq = ctrc_data.chg_seq; // dd_brfg.chg_seq, dd_brfg_chg_mtr.chg_seq
            }
            saveDailyApprovalParams.seq = ctrc_data.is_signer_seq; // dd_brfg_aprv.seq
            saveDailyApprovalParams.staCd = DataUtils.staCd.REJECT;//(is_changed ? DataUtils.staCd.CHANGE : DataUtils.staCd.REJECT);

            _.dialog.confirm("공사일보를 반려하시겠습니까?", function () {
                ApprovalEvent.field_reject(saveDailyApprovalParams);
            });

        } else {
            saveDailyApprovalParams.partnerNo = modal_data.partner_no;
            saveDailyApprovalParams.ctrcNo = modal_data.ctrc_no;
            saveDailyApprovalParams.staCd = DataUtils.compStaCd.REJECT;

            _.dialog.confirm("협력사 공사일보를 반려하시겠습니까?", function () {
                ApprovalEvent.partner_reject(saveDailyApprovalParams);
            });
        }

    },

    /**
     * 협력사 일보 상태코드에 따라 모달 버튼 컨트롤.
     */
    check_modal_status: function () {
        var $modal = $("#company-modify-modal"), ctrc_data = $(".ctrc_group").data();

        switch ($modal.data("sta_cd")) {
            case DataUtils.compStaCd.APPROVAL:
                $(".btn-modal-copy, .btn-modal-cancel-approve", $modal).hide();
                break;

            case DataUtils.compStaCd.APPROVE:
                $(".btn-modal-copy, .btn-modal-approve, .btn-modal-reject, .btn-modal-cancel-approve", $modal).hide();
                break;

            case DataUtils.compStaCd.NONE:
            case DataUtils.compStaCd.REJECT:
            case DataUtils.compStaCd.DRAFT:
                $(".btn-modal-reject, .btn-modal-cancel-approve, .btn-modal-approve", $modal).hide();
                break;

            default:
                break;
        }

        // 한화건설 현장담당자가 자사의 계약정보를 입력할 때,
        // 작성중이면 공사일보를 승인(바로 승인처리)할 수 있다.
        // todo-sunyoupk 한화건설 협력사 번호 공통 사용값으로 처리될 수 있도록 해야함.
        if ($modal.data("sta_cd") == DataUtils.compStaCd.DRAFT && $modal.data("partner_no") && _.cdrs.storage.partnerNo() && $modal.data("partner_no") == "T00001") {
            $(".btn-modal-approve", $modal).show();
        }

        // 상태가 홀드(61)인 경우는 모달에서 아무작업도 안된다.
        if ((ctrc_data.sta_cd + "") == DataUtils.staCd.HOLD) {
            $(".btn-modal-cancel-approve, .btn-modal-copy, .btn-modal-reject", $modal).hide();
        }
        // 상태가 변경작성(60)의 경우 승인취소가 가능하다.
        if ((ctrc_data.sta_cd + "") == DataUtils.staCd.CHANGE && $modal.data("sta_cd") == DataUtils.compStaCd.APPROVE) {
            $(".btn-modal-cancel-approve", $modal).show();
        }
    },

    /**
     * 일보 복사 대상 조회
     */
    build_modal_history: function () {
        var modal_data = $(this).closest(".modal").data(),
            searchDailyParams = {
                deptCd: modal_data.dept_cd,
                day: modal_data.day,
                ordrNo: modal_data.ordr_no,
                partnerNo: modal_data.partner_no,
                ctrcNo: modal_data.ctrc_no
            };

        $.getJSON("/daily/field/historyiesByCtrcNo", searchDailyParams, function (data) {
            var $modal = $('#hitoryModal'),
                $header_table = $(".header_table", $modal),
                $data_table = $(".data_table", $modal),
                $template = $("tfoot .template", $data_table);

            $("tbody", $data_table).empty();
            $modal.data("target_day", searchDailyParams.day);

            $(".partner_nm", $header_table).html(modal_data.partner_nm);
            $(".cstknd_nm", $header_table).html(modal_data.cstknd_nm);

            data.forEach(function (history, i) {
                var $row = $template.clone();
                $row.removeClass("template");
                $("td.day", $row).html(history.day);
                $("td.dy", $row).html(history.dy);
                $("td.submitter", $row).html(history.submitter);
                $("td.labatd_cnt", $row).html(history.labatdCnt);
                $("td.eqip_cnt", $row).html(history.eqipCnt);
                $("td.mtil_cnt", $row).html(history.mtilCnt);

                $row.data("dept_cd", history.deptCd);
                $row.data("day", history.day);
                $row.data("ordr_no", history.ordrNo);
                $row.data("partner_no", history.partnerNo);
                $row.data("ctrc_no", history.ctrcNo);
                $("tbody", $data_table).append($row);
            });

            $modal.modal('show');
        });
    },

    /**
     * 히스토리 데이터 복사
     */
    copy_history_data: function () {
        var $row = $(this).closest("tr"),
            targetDay = $("#hitoryModal").data("target_day"),
            data = $row.data();

        _.dialog.confirm("선택하신 일자의 데이터를 복사하시겠습니까?", function () {
            var historyDailyCopyParams = {
                // 신규대상
                targetDayParams: {
                    deptCd: data.dept_cd,
                    day: targetDay,
                    ordrNo: data.ordr_no,
                    partnerNo: data.partner_no,
                    ctrcNo: data.ctrc_no,
                    staCd: DataUtils.compStaCd.DRAFT,
                },
                // 복사대상
                copyDayParams: {
                    deptCd: data.dept_cd,
                    day: data.day,
                    ordrNo: data.ordr_no,
                    partnerNo: data.partner_no,
                    ctrcNo: data.ctrc_no
                }
            };

            $.post("/daily/field/copyHisotryData", historyDailyCopyParams, function (data) {
                $('#hitoryModal').find("button.close").click();
                ModalEvent.build_modal(historyDailyCopyParams.targetDayParams);
            });
        });
    },

    /**
     * 변경이력 모달 생성.
     */
    build_modal_change_history: function (event) {
        var $target = $(event.relatedTarget), $submitted_table = $target.closest(".data_table"),
            $row = $target.closest("tr"), row_data = $row.data(),
            comp_pk = $.extend(SubmittedEvent.pk_data($submitted_table, true), DataUtils.camelize(row_data));

        var $modal = $(this),
            $header_table = $(".header_table", $modal),
            $data_table = $(".data_table", $modal),
            $template = $("tfoot .template", $data_table);

        $.getJSON("/daily/field/getChangedCompStaHistories", comp_pk, function (data) {
            $("tbody", $data_table).empty();
            $(".partner_nm", $header_table).html(row_data.partner_nm);
            $(".cstknd_nm", $header_table).html(row_data.cstknd_nm);

            data.forEach(function (history, i) {
                var $row = $template.clone(),
                    bfStaCdProps = DataUtils.staCdProps(history.bfStaCd, true),
                    chgStaCdProps = DataUtils.staCdProps(history.chgStaCd, true);
                $row.removeClass("template");

                $("td.bf_sta_cd", $row).html(bfStaCdProps.text);
                $("td.chg_updt_name", $row).html(history.chgUpdtName);
                $("td.chg_sta_cd", $row).html(chgStaCdProps.text);
                $("td.wrtr_dm", $row).html(history.wrtrDm);
                $("tbody", $data_table).append($row);
            });

        });

    }

};

var ApprovalEvent = {

    is_possible_approval: function (data) {
        var ctrc_data = typeof data === "undefined" ? $(".ctrc_group").data() : data;
        if ((ctrc_data.sta_cd + "") == DataUtils.staCd.APPROVAL && ctrc_data.is_signer_seq > 0) {
            return true;
        } else {
            return false;
        }
    },

    /**
     * 공사일보 현장담당자 상신, 승인 처리.
     * @param saveDailyApprovalParams
     * @param is_complete 최종 승인 여부
     */
    field_approve: function (saveDailyApprovalParams, is_complete) {
        $.post("/daily/field/approvalToField", saveDailyApprovalParams, function (data) {
            notifyUtils.show("확인", "처리되었습니다");
            $("#modal-approval").modal("hide");
            ModalEvent.after_close_modal();
        });
    },

    /**
     * 공사일보 현장담당 반려 처리.
     * @param saveDailyApprovalParams
     */
    field_reject: function (saveDailyApprovalParams) {
        $.post("/daily/field/approvalToField", saveDailyApprovalParams, function (data) {
            $("#modal-return").modal("hide");
            notifyUtils.show("확인", "처리되었습니다");
            ModalEvent.after_close_modal();
        });
    },

    /**
     * 공사일보 협력사 반려 처리.
     * @param saveDailyApprovalParams
     */
    partner_reject: function (saveDailyApprovalParams) {
        saveDailyApprovalParams.action = "reject";
        $.post("/daily/field/approvalToPartner", saveDailyApprovalParams, function (data) {
            notifyUtils.show("확인", "처리되었습니다");
            $("#modal-return").modal("hide");
            $("#company-modify-modal").modal("hide");
        });
    },

    /**
     * 공사일보 현장담당 협력사 승인 처리.
     */
    partner_approve: function (saveDailyApprovalParams) {
        saveDailyApprovalParams.action = "approve";
        $.post("/daily/field/approvalToPartner", saveDailyApprovalParams, function (data) {
            notifyUtils.show("확인", "처리되었습니다");
            $("#company-modify-modal").modal("hide");
        });
    },

    /**
     * 공사일보 협력사 승인 취소.
     */
    cancel_approve: function (saveDailyApprovalParams) {
        saveDailyApprovalParams.action = "cancel";
        $.post("/daily/field/approvalToPartner", saveDailyApprovalParams, function (data) {
            notifyUtils.show("확인", "처리되었습니다");
            $("#modal-approval").modal("hide");
            ModalEvent.after_close_modal();
        });
    }
};