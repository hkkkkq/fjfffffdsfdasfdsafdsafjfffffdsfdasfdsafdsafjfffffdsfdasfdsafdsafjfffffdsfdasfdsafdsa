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
        CHANGE: "60"
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
        APPROVE: "50",
        CHANGE: "60"
    },

    staCdProps: function (staCd) {
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
            staCdText = "제출";
            staCdTextShort = "제";
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
        return {"class": staCdClass, "text": staCdText, "textShort": staCdTextShort, "textClass": textClass, "optionalText": staCdOptionalText};
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
    get_rows_data: function ($table, is_camelize, row_status) {
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
                    $.get("/daily/partner/autocompleteSearch", searchDailyParams, function (data) {
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
            console.log(row_key_string);
            console.log(input_row_key_string);
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
        // $page_navigator.data("page_list_count", $(".data_list_counter", $box).val());
        // $page_navigator.data("page_no", 1);
        $page_navigator.data("page_list_count", $(".data_list_counter", $box).val());
        $page_navigator.data("page_no", 1);
        $page_navigator.data("total", data.length);
        // PageUtils.build_navigator(data, $page_navigator);
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
        var paged_data = PageUtils.paging_data(storage_data, page_list_count, page_no);
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
     * 일보 계약 그룹 렌더링
     * @param $ctrc_group
     * @param data
     */
    render_ctrc_group: function ($ctrc_group, data) {
        // 로드된 Data를 Storage에 적재하고, 데이터 테이블의 페이징을 초기화 한다.
        if (data != null) {
            Storage.set(Storage.keys.ctrcs, data.result.ctrcData);
        }
        var ctrc_data = data.result.ctrcData[$ctrc_group.data("data_index")];
        $ctrc_group.data("sta_cd", ctrc_data.comp.staCd);

        $(".data_table", $ctrc_group).trigger("load_data", [$ctrc_group.data("data_index")]);

        // 데이터 테이블을 랜더링 한다.
        $(".comp_box .data_table", $ctrc_group).trigger("render_data_table");
        $(".box:not(.comp_box)", $ctrc_group).each(function (i, box) {
            Renderer.render_data_table($(box), JSON.parse(Storage.get(Storage.keys[$(box).data("storage_key")])));
        });
        $ctrc_group.trigger("rendered_ctrc_group");
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
        calendar: "HENC_CDRS_CALENDAR",

        ctrcs: "HENC_CDRS_CTRCS",

        comp: "HENC_CDRS_COMP",
        comp_saving: "HENC_CDRS_COMP_SAVING",

        labatd: "HENC_CDRS_LABATD",
        labatd_ac: "HENC_CDRS_LABATD_AC",
        labatd_saving: "HENC_CDRS_LABATD_SAVING",

        eqip: "HENC_CDRS_EQIP",
        eqip_ac: "HENC_CDRS_EQIP_AC",
        eqip_saving: "HENC_CDRS_EQIP_SAVING",

        mtil: "HENC_CDRS_MTIL",
        mtil_ac: "HENC_CDRS_MTIL_AC",
        mtil_saving: "HENC_CDRS_MTIL_SAVING",

        qty: "HENC_CDRS_QTY",
        qty_ac: "HENC_CDRS_QTY_AC",
        qty_saving: "HENC_CDRS_QTY_SAVING"
    },

    set: function (key, data, isJson) {
        if (isJson) {
            localStorage.setItem(key, data);
        } else {
            localStorage.setItem(key, JSON.stringify(data));
        }
        localStorage.setItem(key, JSON.stringify(data));
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

    /**
     * 일일보고 데이터 스토리지를 저장 처리.
     */
    submit: function ($ctrc_group, action) {
        if (Storage.before_submit($ctrc_group)) {
            var dailySaveParams = {
                comp: JSON.parse(this.get(this.keys.comp_saving)),
                labatds: JSON.parse(this.get(this.keys.labatd_saving)),
                eqips: JSON.parse(this.get(this.keys.eqip_saving)),
                mtils: JSON.parse(this.get(this.keys.mtil_saving)),
                qties: (DataUtils.is_plant ? JSON.parse(this.get(this.keys.qty_saving)) : []),
                // 계약정보 렌더링 포지션
                ctrcRenderingPosition: $ctrc_group.data("data_index")
            };

            $.post("/daily/partner/savePartnerDailyData", dailySaveParams, function (data) {
                notifyUtils.show("확인", (action == "save" ? "저장되었습니다." : "제출하였습니다"));
                Renderer.render_ctrc_group($ctrc_group, data);

            }).fail(function () {
                Storage.showMessage({
                    place: $(".ctrc_sections", $ctrc_group),
                    content: "현재 네트워크가 오프라인 상태입니다.<br>온라인이 될 경우 데이터가 서버에 반영됩니다.",
                    autoclose: 3000
                });
                Renderer.render_ctrc_group($ctrc_group, null);
            });
        }
    },

    before_submit: function ($ctrc_group) {
        Storage.validated = true;
        $("tbody .validate, dd .validate", $ctrc_group).each(function (i, el) {
            if ($(el).prop("type") == "textarea") {
                return true;
            }
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
 * 일일 보고 메인 객체
 */
var Calendar = {
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
        Calendar.$table = element;
        Calendar.$mobile_table = mini_calendar_element;
        Storage.empty();
        $.ajaxSetup({
            global: false
        });

        var today = new Date(),
            calendarData = Storage.get(Storage.keys.calendar);

        if (typeof calendarData === "object" && calendarData != null) {
            Calendar.date.year = calendarData.year;
            Calendar.date.month = calendarData.month;
        } else {
            Calendar.date.year = today.getFullYear();
            Calendar.date.month = today.getMonth();
        }

        // 달력닫기
        $(document.body).on("click", ".calenda_table_toggler", function (e) {
            // 일보 내용 제거.
            $("#partnerCtrcsWrapper").empty();
            $("#calendar").removeClass("expanded");
            // 일보 달력 데이터 재 조회
            Calendar.getPartnerData();
        });

        // 일보 계약 클릭
        $(document.body).on("click", ".calendar-table tbody td > li.clickable", function (e) {
            var ddBrfg = DataUtils.camelize($(this).closest("td").data()),
                ddBrfgComp = DataUtils.camelize($(this).data());

            $("#calendar").addClass("expanded");
            Calendar.getDailyPartnerData(ddBrfg, ddBrfgComp);
            e.preventDefault();
        });

        // 일보 없는 일자 클릭
        $(document.body).on("click", ".calendar-table tbody td", function (e) {
            var ddBrfg = DataUtils.camelize($(this).data()),
                ddBrfgComp = {
                    deptCd: ddBrfg.deptCd,
                    day: ddBrfg.day,
                    ordrNo: ddBrfg.ordrNo,
                    partnerNo: _.cdrs.storage.partnerNo()
                };

            $("#calendar").addClass("expanded");
            Calendar.getDailyPartnerData(ddBrfg, ddBrfgComp);
            e.preventDefault();
        });

        // 모바일 달력 일자 클릭
        $(document.body).on("click", "#top-calendar-body td", function (e) {
            if (!$(this).is(".empty")) {
                var rowpos = $("#calendar-body td#day" + $(this).data("day")).position();
                $("body, html").animate({scrollTop: rowpos.top}, 800);
            }
        });

        // 작업이력 복사
        $(document.body).on("click", ".master_btns .btn-copy-data", this.getHistories);
        // 일보 저장
        $(document.body).on("click", ".master_btns .btn-save-data", this.saveDailyPartnerData);
        // 일보 제출(저장 포함)
        $(document.body).on("click", ".master_btns .btn-approval-data", this.saveDailyPartnerData);

        // 작업이력 복사 생성
        $(document.body).on("click", "#hitoryModal .btn-copy-create", this.copyHistoryData);

        // 상태 체크
        $(document.body).on("rendered_ctrc_group", ".ctrc_group", this.rendered);

        $(document.body).on("click", ".header a", ".ctrc_group", function () {
            var $ctrc = $(this).closest(".ctrc_group");
            $(".ctrc_group", $ctrc.closest(".accordion")).removeClass("active");
            $ctrc.addClass("active");
            $(".master_btns button", $ctrc).prop("disabled", false);
        });

        $(document.body).on("hide.bs.collapse", ".ctrc_group", function () {
            $(this).removeClass("active");
            $(".master_btns button", $(this)).prop("disabled", true);
        });

        Calendar.build(Calendar.$table);
        Calendar.build(Calendar.$mobile_table);
        notifyUtils.init();

        Calendar.getPartnerData();

        CompEvent.init();
        LabatdEvent.init();
        MtilEvent.init();
        EqipEvent.init();

        console.log(DataUtils.is_plant);
        if (DataUtils.is_plant) {
            QtyEvent.init();
        }
    },

    /**
     * 일보 달력 생성
     */
    build: function ($table) {
        var today = new Date(),
            month = Calendar.date.month,
            year = Calendar.date.year,
            firstDay = (new Date(year, month)).getDay(),
            lastDay = (new Date(year, month + 1, 0)).getDay(),
            daysInMonth = 32 - new Date(year, month, 32).getDate(),
            trRows = Math.ceil((firstDay + (6 - lastDay) + daysInMonth) / 7),
            date = 1,
            calendar_type = $table.data("calendar_type");

        if (calendar_type === "mobile_only") {

        } else {
            $("#year-text").html(Calendar.date.year + "년");
            $("#month-text").html((parseInt(Calendar.date.month) + 1) + "월");
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
                    var day = Calendar.date.year + "" + (("0" + (Calendar.date.month + 1)).slice(-2)) + (("0" + date).slice(-2)),
                        cell = $("<td id='" + day_prefix + "day" + day + "' data-dept_cd='" + _.cdrs.storage.deptCd() + "' data-day='" + day + "' data-ordr_no='' data-sta_cd='' data-chg_no='' data-chg_seq=''></td>"),
                        cellDay = $("<div class='day'>" + date + "</div>");

                    $("<li class='date'><span class='name'>일자</span><span>" + today + "</span></li>");

                    if (date === today.getDate() && year === today.getFullYear() && month === today.getMonth()) {
                        // 오늘일자에 특별하게 해주고 싶다면 cell.addClass("bg-info"); 사용할 수 있음.
                    }

                    /**
                     * 달력 생성시 모바일 화면에 표기 내용 생성
                     */
                    var m = moment(day, "YYYYMMDD");
                    var dayString = m.format("YYYY-MM-DD dddd");
                    var cellDayDetail = $("<div class='data'><ul><li class='date'><span class='name'>일자</span><span>" + dayString + "</span></li></ul></div>");

                    cell.prepend(cellDay);
                    if (calendar_type != "mobile_only") {
                        // 모바일 달력은 데이터가 필요하지 않다.
                        cell.prepend(cellDayDetail);
                    }
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
        Calendar.date.year = (Calendar.date.month === 11) ? Calendar.date.year + 1 : Calendar.date.year;
        Calendar.date.month = (Calendar.date.month + 1) % 12;
        Calendar.build(Calendar.$table);
        Calendar.build(Calendar.$mobile_table);
        Calendar.getPartnerData();
    },

    /**
     * 이전 월 조회
     */
    previous: function () {
        Calendar.date.year = (Calendar.date.month === 0) ? Calendar.date.year - 1 : Calendar.date.year;
        Calendar.date.month = (Calendar.date.month === 0) ? 11 : Calendar.date.month - 1;
        Calendar.build(Calendar.$table);
        Calendar.build(Calendar.$mobile_table);
        Calendar.getPartnerData();
    },

    /**
     * 오늘 날짜로 렌더링.
     */
    toDate: function () {
        var today = new Date(),
            m = moment().format("YYYYMMDD");

        Calendar.date.year = today.getFullYear();
        Calendar.date.month = today.getMonth();

        Calendar.build(Calendar.$table);
        Calendar.build(Calendar.$mobile_table);

        Calendar.getPartnerData(function (data) {
            var rowpos = $("#calendar-body td#day" + m).position();
            $("body, html").animate({scrollTop: rowpos.top}, 800);
        });
    },

    /**
     * 일보 달력 데이터 조회
     */
    getPartnerData: function (callback) {
        var params = {
            deptCd: _.cdrs.storage.deptCd(),
            day: Calendar.date.year + "" + (("0" + (Calendar.date.month + 1)).slice(-2)) + "01",
        };

        $.get("/daily/partner/monthlyData", params, function (data) {
            Storage.set(Storage.keys.calendar, {
                year: Calendar.date.year,
                month: Calendar.date.month,
                data: data.result
            });
            Calendar.renderPartnerData(data.result, callback);
        });
    },

    /**
     * 일보 데이터(협력사) 조회
     * @param ddBrfg
     * @param ddBrfgComp
     */
    getDailyPartnerData: function (ddBrfg, ddBrfgComp) {
        var searchDailyParams = {
            deptCd: _.cdrs.storage.deptCd(),
            bizhdofCd: _.cdrs.storage.bizhdofCd(),
            day: ddBrfgComp.day,
            ordrNo: ddBrfg.ordrNo,
            partnerNo: ddBrfgComp.partnerNo,
            ctrcNo: ddBrfgComp.ctrcNo
        };

        $.get("/daily/partner/dailyData", searchDailyParams, function (data) {
            // 로드데이터 적재.
            Storage.set(Storage.keys.ctrcs, data.result.ctrcData);

            // 일보 일자요일 설정(dream framework moment 활용)
            var m = moment(data.result.master.day, "YYYYMMDD");
            $("#date_text").html(m.format("YYYY-MM-DD dddd"));

            // 일보 데이터 freemarker template 렌더링
            $("#partnerCtrcsWrapper").html(data.html);

            var $first_ctrc_group = $(".ctrc_group").eq(0);

            $(".ctrc_group").each(function (i, ctrc) {
                var $ctrc = $(ctrc);
                $(".master_btns button", $ctrc).prop("disabled", true);
                $(".data_table", $ctrc).trigger("load_data", [$ctrc.data("data_index")]);
                $ctrc.trigger("rendered_ctrc_group");
            });

            // 첫 번째 계약 폴딩
            $(".header a", $first_ctrc_group).click();
        });
    },

    /**
     * 일보 복사 대상 조회
     */
    getHistories: function () {
        var $ctrc_group = $(this).closest(".ctrc_group"),
            ctrc_data = DataUtils.camelize($ctrc_group.data()),
            searchDailyParams = {
                deptCd: ctrc_data.deptCd,
                day: ctrc_data.day,
                ordrNo: ctrc_data.ordrNo,
                partnerNo: ctrc_data.partnerNo,
                ctrcNo: ctrc_data.ctrcNo
            };
        $.getJSON("/daily/partner/historyiesByCtrcNo", searchDailyParams, function (data) {
            Calendar.renderHistoryTable({
                targetDay: searchDailyParams.day,
                partnerNm: ctrc_data.partnerNm,
                cstkndNm: ctrc_data.cstkndNm,
                list: data,
                ctrc_group_index: ctrc_data.dataIndex
            });
        });
    },

    /**
     * 일보 달력 데이터 렌더링
     * @param data
     */
    renderPartnerData: function (data, callback) {
        var calendarData = data.calendarData,
            partnerData = data.partnerData;

        calendarData.forEach(function (day, i) {
            var $el = $("#day" + day.day);
            if (day.deptCd != null) {
                $el.data("dept_cd", day.deptCd);
                $el.data("sta_cd", day.staCd);
                $el.data("ordr_no", day.ordrNo);
                $el.data("chg_no", day.chgNo);
                $el.data("chg_seq", day.chgSeq);
            }
        });

        for (var el in partnerData) {
            var $day = $("#day" + el),
                $dayDataDiv = $("<div class='data'/>"),
                $ul = $("<ul/>"),
                elCount = 0;
            $day.empty();


            /**
             * 모바일 화면에 날짜 정보 표시 되도록 수정(이범근)
             */
            var m = moment(el, "YYYYMMDD");
            var today = m.format("YYYY-MM-DD dddd");
            var oday = m.format("DD");
            var $dayIcon = $("<div class='day'>" + oday + "</div>");
            $dayIcon.appendTo($day);


            $ul.appendTo($dayDataDiv);

            var $li = $("<li class='date'><span class='name'>일자</span><span>" + today + "</span></li>");
            $li.appendTo($ul);

            partnerData[el].forEach(function (day, i) {
                if (elCount >= 2) {
                    // return;
                }
                var staCdProps = DataUtils.staCdProps(day.staCd);
                $li = $("<li class='clickable list' data-dept_cd='" + day.deptCd + "' data-day='" + day.day + "' data-partner_no='" + day.partnerNo + "' data-ordr_no='" + day.ordrNo + "' data-ctrc_no='" + day.ctrcNo + "' data-sta_cd='" + day.staCd + "'><span class='title' title='" + day.ctrcNm + "'>" + day.ctrcNm + "</span><strong class='" + staCdProps.class + "'>" + staCdProps.textShort + "</strong></li>");
                $li.appendTo($ul);
                elCount = elCount + 1;
            });

            if (partnerData[el].length - 2 > 0) {
                var $more = $("<div class='more'>more<span class='num'>" + (partnerData[el].length - 2) + "</span><em>+</em></div>");
                $more.appendTo($dayDataDiv);
            }

            $dayDataDiv.appendTo($day);
        }

        if ($.isFunction(callback)) {
            callback.apply(this, [data]);
        }
    },

    /**
     * 히스토리 복사대상 모달 생성
     * @param data
     */
    renderHistoryTable: function (data) {
        var $modal = $('#hitoryModal'),
            $header_table = $(".header_table", $modal),
            $data_table = $(".data_table", $modal),
            $template = $("tfoot .template", $data_table);

        $("tbody", $data_table).empty();

        // ctrc group pointer.
        $modal.data("target_day", data.targetDay);
        $modal.data("ctrc_group_index", data.ctrc_group_index);

        $(".partner_nm", $header_table).html(data.partnerNm);
        $(".cstknd_nm", $header_table).html(data.cstkndNm);

        data.list.forEach(function (history, i) {
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
    },

    /**
     * 히스토리 데이터 복사
     */
    copyHistoryData: function () {
        var $row = $(this).closest("tr"),
            targetDay = $("#hitoryModal").data("target_day"),
            ctrc_group_index = $("#hitoryModal").data("ctrc_group_index"),
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
                    staCd: '10',
                    ctrcRenderingPosition: ctrc_group_index
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

            $.post("/daily/partner/copyHisotryData", historyDailyCopyParams, function (data) {
                $('#hitoryModal').find("button.close").click();
                var $ctrc_group = $(".ctrc_group").eq(ctrc_group_index);
                notifyUtils.show("확인", "작업이력이 복사 되었습니다.");
                Renderer.render_ctrc_group($ctrc_group, data);
            });
        });
    },

    /**
     * 일보 데이터 저장
     */
    saveDailyPartnerData: function () {
        var $ctrc_group = $(this).closest(".ctrc_group");
        var action = $(this).hasClass("btn-save-data") ? "save" : "approval",
            confirm_message = (action == "save") ? "일보 데이터를 저장하시겠습니까?" : "일보 데이터를 제출하시겠습니까?";

        _.dialog.confirm(confirm_message, function () {
            $(".data_table", $ctrc_group).trigger("save_data_table", [action]);
            Storage.submit($ctrc_group, action);
        });
    },

    /**
     * 상태코드 값에 따른 처리
     */
    rendered: function () {
        var $ctrc = $(this),
            ctrc_data = DataUtils.camelize($ctrc.data()),
            compStaCdProps = DataUtils.staCdProps(ctrc_data.staCd);

        $(".btn-copy-data, .btn-save-data, .btn-approval-data", $ctrc).show();
        switch (ctrc_data.staCd + "") {//staCd data is String
            case DataUtils.compStaCd.NONE:
                $(".btn-approval-data", $ctrc).hide();
                break;

            case DataUtils.compStaCd.DRAFT:
            case DataUtils.compStaCd.CHANGE:
            case DataUtils.compStaCd.REJECT:
            case "":
                break;

            default:
                $(".btn-copy-data, .btn-save-data, .btn-approval-data", $ctrc).hide();
                $(".data_table_actions", $ctrc).hide();
                $("input, textarea, select:not(.page_control)", $ctrc).prop("disabled", true);
                break;
        }

        $(".header .state", $ctrc).addClass(compStaCdProps.class);
        $("span.state", $ctrc).html(compStaCdProps.text);

        if ($ctrc.is(".active")) {
            $(".master_btns button", $ctrc).prop("disabled", false);
        }

        // 일보 상태 코드(DD_BRFG.STA_CD)
        var master_sta_cd = $ctrc.closest(".accordion").data("sta_cd");
        if (master_sta_cd == "20" || master_sta_cd == "50") {
            $(".btn-copy-data, .btn-save-data, .btn-approval-data", $ctrc).hide();
            $(".data_table_actions", $ctrc).hide();
            $("input, textarea, select:not(.page_control)", $ctrc).prop("disabled", true);

            notifyUtils.show("확인", "해당 일자는 현장 결재가 진행중 또는 완료되어 수정 할 수 없습니다.");
        }
    }
};

/**
 * 일일 보고 주요업무 객체
 */
var CompEvent = {
    init: function () {
        var box = ".comp_box";
        $(document.body).on("blur", box + " .data_table input, " + box + " .data_table textarea", this.set_val_to_data);
        $(document.body).on("change", box + " .data_table input, " + box + " .data_table textarea", this.need_change);
        $(document.body).on("save_data_table", box + " .data_table", this.save_storage);
        $(document.body).on("load_data", box + " .data_table", this.load_data);
        $(document.body).on("render_data_table", box + " .data_table", this.render_data_table);
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
        // if (DataUtils.validate($(".validate", $(this)))) {
        var data = DataUtils.camelize($(this).data());
        Storage.set(Storage.keys.comp, data);
        // }
    },

    render_data_table: function () {
        var data = CompEvent.get_stroage_data();
        $(this).data("sta_cd", data.staCd);
        $(this).data("objt_yn", data.objtYn);
        $(this).data("sort_no_seq", data.sortNoSeq);
        $(".cstknd_nm", $(this)).html(data.cstkndNm);
        $("textarea[name='perdd_main_work']", $(this)).val(data.perddMainWork).blur();
        $("textarea[name='nmdd_main_work']", $(this)).val(data.nmddMainWork).blur();
    },

    load_data: function (e, ctrc_index) {
        var data = JSON.parse(Storage.get(Storage.keys.ctrcs));
        if (data != null) {
            Storage.set(Storage.keys.comp, data[ctrc_index].comp);
        }
    }
};

/**
 * 일일 보고 출역 객체
 */
var LabatdEvent = {

    init: function () {
        var box = ".labatd_box";
        $(document.body).on("focusin", box + " .data_table input, " + box + " .data_table select", this.select_row);
        $(document.body).on("change", box + " .data_table input, " + box + " .data_table select", this.need_change);
        $(document.body).on("click", box + " .btn-toolbar .fold", Renderer.fold_data_table_row);
        $(document.body).on("click", box + " .data_table_actions .add", this.add_row);
        $(document.body).on("click", box + " .data_table_actions .del", this.delete_row);
        $(document.body).on("save_data_table", box + " .data_table", this.save_storage);
        $(document.body).on("load_data", box + " .data_table", this.load_data);
        $(document.body).on("change", box + " .data_list_counter", this.set_pager);
        $(document.body).on("click", box + " .page-link", this.set_page_no);
        $(document.body).on("rendered_data_table", box + " .data_table", this.rendered_data_table);
    },

    pk_data: function ($table, isCamelCase) {
        return isCamelCase === true ? DataUtils.camelize($table.data()) : $table.data();
    },

    get_stroage_data: function () {
        return JSON.parse(Storage.get(Storage.keys.labatd));
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
        if (!DataUtils.manual_dup_check( (DataUtils.get_rows_data($table).concat(LabatdEvent.get_stroage_data())), $row, ['name', 'ocptNo'], true)) {
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
        // if (DataUtils.validate($("tbody .validate", $(this)))) {
        var data = [];
        $(this).find("tbody tr").each(function (i, row) {
            if ($(row).data("need_change") == "true") {
                data.push(DataUtils.camelize($(row).data()));
            }
        });
        Storage.set(Storage.keys.labatd_saving, data);
        // }
    },

    delete_row: function () {
        var $row = $(this).closest(".box").find(".data_table tbody tr.focus").each(function (i, row) {
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
        var $table = $(this).closest(".box").find(".data_table"),
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

    rendered_data_table: function () {
        var $table = $(this),
            $box = $(this).closest(".box");

        var searchDailyParams = LabatdEvent.pk_data($table, true);
        searchDailyParams.bizhdofCd = _.cdrs.storage.bizhdofCd();
        searchDailyParams.autocompleteSearchParams = {target: "labatd", type: "", term: ""};

        DataUtils.autocomplete(searchDailyParams, ["name", "ocptNm", "brtdy"], $box, function (data, el) {
            var is_dup = false;
            (DataUtils.get_rows_data($table, true, "I").concat(LabatdEvent.get_stroage_data())).forEach(function (labatd, i) {
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
    },

    load_data: function (e, ctrc_index) {
        var $box = $(this).closest(".box");
        var data = JSON.parse(Storage.get(Storage.keys.ctrcs));
        var storage_data = data[ctrc_index].labatds;
        Storage.set(Storage.keys.labatd, storage_data);

        var $page_navigator = $(".page_navigator", $(this).closest(".data_table_wrapper"));
        $page_navigator.data("total", storage_data.length);
        PageUtils.build_navigator($page_navigator);

        $(".total_text", $box).html(storage_data.length);

        Renderer.render_data_table($box, LabatdEvent.get_stroage_data());
    },

    set_pager: function () {
        PageUtils.set_pager($(this).closest(".box"), LabatdEvent.get_stroage_data());
    },

    set_page_no: function () {
        var $box = $(this).closest(".box"),
            $li = $(this).parent("li");

        PageUtils.set_page_no($box, $li, LabatdEvent.get_stroage_data());
    }
};

/**
 * 일일 보고 주요자재 객체
 */
var MtilEvent = {
    init: function () {
        var box = ".mtil_box";
        $(document.body).on("focusin", box + " .data_table input, " + box + " .data_table select", this.select_row);
        $(document.body).on("change", box + " .data_table input, " + box + " .data_table select", this.need_change);
        $(document.body).on("click", box + " .btn-toolbar .fold", Renderer.fold_data_table_row);
        $(document.body).on("click", box + " .data_table_actions .add", this.add_row);
        $(document.body).on("click", box + " .data_table_actions .del", this.delete_row);
        $(document.body).on("save_data_table", box + " .data_table", this.save_storage);
        $(document.body).on("load_data", box + " .data_table", this.load_data);
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
        }

        // 키입력 중복검사.
        if (!DataUtils.manual_dup_check( (DataUtils.get_rows_data($table).concat(MtilEvent.get_stroage_data())), $row, ['mtilNm', 'spec', 'unit'], true)) {
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
        var $row = $(this).closest(".box").find(".data_table tbody tr.focus").each(function (i, row) {
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
        var $table = $(this).closest(".box").find(".data_table"),
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

    rendered_data_table: function () {
        var $table = $(this),
            $box = $(this).closest(".box");

        var searchDailyParams = LabatdEvent.pk_data($table, true);
        searchDailyParams.bizhdofCd = _.cdrs.storage.bizhdofCd();
        searchDailyParams.autocompleteSearchParams = {target: "mtil", type: "", term: ""};

        DataUtils.autocomplete(searchDailyParams, ["mtilNm", "spec", "unit"], $box, function (data, el) {
            var is_dup = false;
            (DataUtils.get_rows_data($table, true, "I").concat(MtilEvent.get_stroage_data())).forEach(function (mtil, i) {
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
            $("input[name='mtil_nm']", $row).val(data.value).blur();
            $("input[name='unit']", $row).data("selected_unit", data.parentUnit);
            $("input[name='unit']", $row).val(data.parentUnit).blur();
            $("input[name='spec']", $row).data("selected_spec", data.parentSpec);
            $("input[name='spec']", $row).val(data.parentSpec).blur();
        });
    },

    load_data: function (e, ctrc_index) {
        var $box = $(this).closest(".box");
        var data = JSON.parse(Storage.get(Storage.keys.ctrcs));
        var storage_data = data[ctrc_index].mtils;
        Storage.set(Storage.keys.mtil, storage_data);

        var $page_navigator = $(".page_navigator", $(this).closest(".data_table_wrapper"));
        $page_navigator.data("total", storage_data.length);
        PageUtils.build_navigator($page_navigator);

        $(".total_text", $box).html(storage_data.length);

        Renderer.render_data_table($box, MtilEvent.get_stroage_data());
    },

    set_pager: function () {
        PageUtils.set_pager($(this).closest(".box"), MtilEvent.get_stroage_data());
    },

    set_page_no: function () {
        var $box = $(this).closest(".box"),
            $li = $(this).parent("li");

        PageUtils.set_page_no($box, $li, MtilEvent.get_stroage_data());
    }
};

/**
 * 일일 보고 주요장비 객체
 */
var EqipEvent = {
    init: function () {
        var box = ".eqip_box";
        $(document.body).on("focusin", box + " .data_table input, " + box + " .data_table select", this.select_row);
        $(document.body).on("change", box + " .data_table input, " + box + " .data_table select", this.need_change);
        $(document.body).on("click", box + " .btn-toolbar .fold", Renderer.fold_data_table_row);
        $(document.body).on("click", box + " .data_table_actions .add", this.add_row);
        $(document.body).on("click", box + " .data_table_actions .del", this.delete_row);
        $(document.body).on("save_data_table", box + " .data_table", this.save_storage);
        $(document.body).on("load_data", box + " .data_table", this.load_data);
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
        }

        // 키입력 중복검사.
        if (!DataUtils.manual_dup_check( (DataUtils.get_rows_data($table).concat(EqipEvent.get_stroage_data())), $row, ['eqipNm', 'spec', 'unit'], true)) {
            $row.addClass("E");
        } else {
            $row.removeClass("E");
        }

        // var $row = $(this).closest("tr"), $rows = $(this).closest("tbody").find("tr");
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
        var $row = $(this).closest(".box").find(".data_table tbody tr.focus").each(function (i, row) {
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
        var $table = $(this).closest(".box").find(".data_table"),
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

    rendered_data_table: function () {
        var $table = $(this),
            $box = $(this).closest(".box");

        var searchDailyParams = EqipEvent.pk_data($table, true);
        searchDailyParams.bizhdofCd = _.cdrs.storage.bizhdofCd();
        searchDailyParams.autocompleteSearchParams = {target: "eqip", type: "", term: ""};

        DataUtils.autocomplete(searchDailyParams, ["eqipNm", "spec", "unit"], $box, function (data, el) {
            var is_dup = false;
            (DataUtils.get_rows_data($table, true, "I").concat(EqipEvent.get_stroage_data())).forEach(function (eqip, i) {
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
            $("input[name='eqip_nm']", $row).val(data.value).blur();
            $("input[name='unit']", $row).data("selected_unit", data.parentUnit);
            $("input[name='unit']", $row).val(data.parentUnit).blur();
            $("input[name='spec']", $row).data("selected_spec", data.parentSpec);
            $("input[name='spec']", $row).val(data.parentSpec).blur();
        });
    },

    load_data: function (e, ctrc_index) {
        var $box = $(this).closest(".box");
        var data = JSON.parse(Storage.get(Storage.keys.ctrcs));
        var storage_data = data[ctrc_index].eqips;
        Storage.set(Storage.keys.eqip, storage_data);

        var $page_navigator = $(".page_navigator", $(this).closest(".data_table_wrapper"));
        $page_navigator.data("total", storage_data.length);
        PageUtils.build_navigator($page_navigator);

        $(".total_text", $box).html(storage_data.length);

        Renderer.render_data_table($box, EqipEvent.get_stroage_data());
    },

    set_pager: function () {
        PageUtils.set_pager($(this).closest(".box"), EqipEvent.get_stroage_data());
    },

    set_page_no: function () {
        var $box = $(this).closest(".box"),
            $li = $(this).parent("li");

        PageUtils.set_page_no($box, $li, EqipEvent.get_stroage_data());
    }
};

/**
 * 일일 보고 공사물량 객체
 */
var QtyEvent = {
    init: function () {
        var box = ".qty_box";
        $(document.body).on("focusin", box + " .data_table input, " + box + " .data_table select", this.select_row);
        $(document.body).on("change", box + " .data_table input, " + box + " .data_table select", this.need_change);
        $(document.body).on("click", box + " .btn-toolbar .fold", Renderer.fold_data_table_row);
        $(document.body).on("click", box + " .data_table_actions .add", this.add_row);
        $(document.body).on("click", box + " .data_table_actions .del", this.delete_row);
        $(document.body).on("save_data_table", box + " .data_table", this.save_storage);
        $(document.body).on("load_data", box + " .data_table", this.load_data);
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

        // 출역인원 성명이 변경될 때, 기존에 선택된 이름과 다를경우 신규 입력 대상으로 처리한다.
        if ($(this).prop("name") == "qty_nm") {
            if ($(this).val() != $(this).data("selected_qty_nm")) {
                $row.data("qty_no", "");
            }
        }

        // 키입력 중복검사.
        if (!DataUtils.manual_dup_check( (DataUtils.get_rows_data($table).concat(QtyEvent.get_stroage_data())), $row, ['qtyNm', 'spec', 'unit'], true)) {
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
        var $row = $(this).closest(".box").find(".data_table tbody tr.focus").each(function (i, row) {
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
        var $table = $(this).closest(".box").find(".data_table"),
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

    rendered_data_table: function () {
        var $table = $(this),
            $box = $(this).closest(".box");

        var searchDailyParams = LabatdEvent.pk_data($table, true);
        searchDailyParams.bizhdofCd = _.cdrs.storage.bizhdofCd();
        searchDailyParams.autocompleteSearchParams = {target: "qty", type: "", term: ""};

        DataUtils.autocomplete(searchDailyParams, ["qtyNm", "spec", "unit"], $box, function (data, el) {
            var is_dup = false;
            (DataUtils.get_rows_data($table, true, "I").concat(QtyEvent.get_stroage_data())).forEach(function (qty, i) {
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
            $("input[name='qty_nm']", $row).val(data.value).blur();
            $("input[name='unit']", $row).data("selected_unit", data.unit);
            $("input[name='unit']", $row).val(data.unit).blur();
            $("input[name='spec']", $row).data("selected_spec", data.spec);
            $("input[name='spec']", $row).val(data.spec).blur();
        });
    },

    load_data: function (e, ctrc_index) {
        var $box = $(this).closest(".box");
        var data = JSON.parse(Storage.get(Storage.keys.ctrcs));
        var storage_data = data[ctrc_index].qties;
        Storage.set(Storage.keys.qty, storage_data);

        var $page_navigator = $(".page_navigator", $(this).closest(".data_table_wrapper"));
        $page_navigator.data("total", storage_data.length);
        PageUtils.build_navigator($page_navigator);

        $(".total_text", $box).html(storage_data.length);

        Renderer.render_data_table($box, QtyEvent.get_stroage_data());
    },

    set_pager: function () {
        PageUtils.set_pager($(this).closest(".box"), QtyEvent.get_stroage_data());
    },

    set_page_no: function () {
        var $box = $(this).closest(".box"),
            $li = $(this).parent("li");

        PageUtils.set_page_no($box, $li, QtyEvent.get_stroage_data());
    }
};