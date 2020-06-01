/**
 *  自定义封装键盘，该键盘分两种 纯数字键盘 和 常规键盘
 *  https://www.jianshu.com/p/cb21ca8786a4
 *  基于jQuery库
 *  type 0-纯数字；1-常规键盘；2-支付密码键盘(支付密码键盘必须主动触发),默认为1
 *  eventNode 目标元素
 *  assignEventNode 需要赋值的目标，不存在则默认赋值目标为eventNode
 *  passWordLength 密码的位数，不传默认6位
 *  completeCallBack  密码输入完成后需要调用的函数(若同时存在passWordLength/completeCallBack/clickCallBack，则优先执行clickCallBack，再执行completeCallBack)
 *  clickCallBack  所有按钮点击后调用的函数，不传则不执行
 *  isAssign 是否赋值，有些可能需要做特殊处理的，不需要直接给目标赋值，默认true-赋值
 *  clickHidden 是否支持点击遮罩层隐藏，默认false-不支持
 *  title 标题，支持html输入
 *  selectionStart 光标位置，该参数不支持支付密码键盘，且只支持目标INPUT元素
 *  length 限制输出长度，默认不限制（支付键盘不可用）
 *  author libh
 */
function keyBoard(params) {
    function NewKeyBoard() {
        this._default = {
            BODY_NODE: "libh_key_board", // 最外层键盘的ID值
            BODY_NODE_ID: "#libh_key_board ",
            EVENT_NODE_CLASS: ".libh_key_board ", // 触发键盘的目标class类名，主要用于被动触发
            inputValue: '', // 输入内容
            type: 1, // 键盘类型
            selectionStart: 0, // 光标位置，该参数不支持支付密码键盘
            eventType: 'click', // 事件方法
            isInput : true, // 默认触发目标为input元素
            length: 0,

            // 支付密码不支持以下参数
            isAssign: true, // 是否赋值
            EVENT_NODE_ORIGIN: '', // 触发键盘的元素
            EVENT_NODE: '', // 目标元素
            EVENT_NODE_TAGNAME: 'input', // 目标元素的标签
            clickCallBack: '', // 所有按钮点击后调用的函数

            // 支付密码专用参数
            title: '请输入交易密码', // 标题，支持html输入
            clickHidden: false, // 是否支持点击遮罩层隐藏
            completeCallBack: '', // 密码输入完成后需要调用的函数
            passWordLength: 6, // 密码长度
            hasInputValueLength: 0, // 已输入字符串长度
            passwordKeyPanel: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '', '0', 'del'],

            // 纯数字键盘
            NumberKeyPanel: ['1', '2', '3', '4', '5', '6', '7', '8', '9', 'del' , '0', 'enter'],

            // 常规键盘
            status: '0', // 0-小写；1-大写；2-字符
            routineNumberHtml: '', // 数字列html
            routineLowerHtml: '', // 小写列html
            routineUpperHtml: '', // 大写列html
            routineSymbolHtml: '', // 字符列html
            routineLetterLastHtml: '', // 字母底部列html
            routineSymbolLastHtml: '', // 字符底部列html
            routineNumberKeyPanel: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0'],
            routineLowerKeyPanel: [
                ['q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'],
                ['a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l'],
                ['upper', 'z', 'x', 'c', 'v', 'b', 'n', 'm', 'del']
            ],
            routineUpperKeyPanel: [
                ['Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'],
                ['A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L'],
                ['lower', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', 'del']
            ],
            routineSymbolKeyPanel: [
                ['<', '>', '{', '}', '(', ')', '[', ']', '$'],
                ['', '|', '%', '&', '*', '^', '/', '?', '!'],
                ['+', ':', ';', '"', "'", '`', '#'],
                ['@', '=', '~', '_', '-', ',', 'del']
            ],
            routineLetterLast: ['symbol', '.', 'blank', 'enter'],
            routineSymbolLast: ['letter', '.', 'blank', 'enter']
        }; // 设置默认值
    };

    // 绑定目标元素触发事件或主动生成键盘
    NewKeyBoard.prototype.addEvent = function() {
        console.log("生成键盘");
        //生成键盘时隐藏底部按钮
        HuanziFooter.hide();

        var _this = this;
        var elems = document.querySelectorAll(_this._default.EVENT_NODE_CLASS);
        if(options.type !== '' && options.type !== null && options.type !== 'undefined') {
            window.event.stopPropagation();
            window.event.preventDefault();
            document.activeElement.blur();
            // 获取事件方式
            _this._default.eventType = window.event.type;
            // 密码键盘必须主动触发
            var _target = null;
            if(options.type != 2 && !options.eventNode) {
                console.error("目标不存在指定类名且不存在指定触发键盘类名");
                return;
            }
            // 判断赋值目标是否为eventNode
            if(options.assignEventNode) {
                _target = $(options.assignEventNode)[0];
            } else {
                _target = $(options.eventNode)[0];
            }

            _this._default.EVENT_NODE_ORIGIN = $(options.eventNode)[0];
            _this._default.type = options.type == '0' ? '0' : (options.type == '2' ? '2' : '1');
            _this._default.passWordLength = options.passWordLength;
            _this._default.completeCallBack = options.completeCallBack;
            _this._default.clickCallBack = options.clickCallBack;
            if(_this._default.type == '2') {
                // 支付密码键盘
                _this._default.inputValue = '';
                _this._default.hasInputValueLength = 0;
                if(options.passWordLength === '' || isNaN(options.passWordLength)) {
                    _this._default.passWordLength = 6;
                } else {
                    _this._default.passWordLength = parseInt(options.passWordLength);
                }
                if(options.title) {
                    _this._default.title = options.title;
                }
                _this._default.EVENT_NODE = '';
                _this._default.EVENT_NODE_TAGNAME = '';
                _this._default.isAssign = true;
                _this._default.clickHidden = options.clickHidden;
            } else {
                _this._default.EVENT_NODE = _target;
                _this._default.EVENT_NODE_TAGNAME = _target.tagName;
                _this._default.isAssign = options.isAssign === false ? false : true;
                if(options.length === '' || isNaN(options.length)) {
                    _this._default.length = 0;
                } else {
                    _this._default.length = parseInt(options.length);
                }
                // 判断是否为input/textarea还是使用div构造的输入框
                if(_this._default.EVENT_NODE_TAGNAME.toLowerCase() == 'input' || _this._default.EVENT_NODE_TAGNAME.toLowerCase() == 'textarea') {
                    _this._default.isInput = true;
                    _this._default.inputValue = _this._default.EVENT_NODE.value;
                } else {
                    _this._default.isInput = false;
                    _this._default.inputValue = _this._default.EVENT_NODE.getAttribute("data-content") || "";
                    $(".libh_change_key").each(function(index, elem) {
                        $(elem).removeClass("libh_input_cursor");
                        if(!$(elem).attr("data-content")) {
                            $(elem).removeClass("libh_change_key");
                        }
                    });
                    $(_this._default.EVENT_NODE).addClass("libh_change_key libh_input_cursor");
                }
            }
            _this.init();

        } else if(elems.length) {
            elems.forEach(function(elem) {
                $(elem).off('click');
                $(elem).on('click', function(e) {
                    window.event.stopPropagation();
                    window.event.preventDefault();
                    // 获取事件方式
                    _this._default.eventType = window.event.type;

                    var type = e.target.getAttribute('data-type');
                    var _target_id = e.target.getAttribute('data-assignEventNode');
                    var _target_isAssign = e.target.getAttribute('data-isAssign');
                    var _target_passWordLength = e.target.getAttribute('data-passWordLength');
                    var _target_length = e.target.getAttribute('data-length');
                    var _target = null;
                    // 判断赋值目标是否为事件对象
                    // 为保证唯一性，请使用ID配对
                    if(_target_id) {
                        if($("#" + _target_id).length) {
                            _target = $("#" + _target_id)[0];
                        } else {
                            _target = $("." + _target_id)[0];
                        }
                    } else {
                        _target = e.target;
                    }
                    _this._default.EVENT_NODE_ORIGIN = e.target;
                    _this._default.EVENT_NODE = _target;
                    _this._default.EVENT_NODE_TAGNAME = _target.tagName;
                    _this._default.type = type == '0' ? '0' : '1';// 支付密码键盘必须主动触发，这里不做支付密码键盘处理
                    _this._default.isAssign = _target_isAssign === false ? false : true;
                    _this._default.passWordLength = _target_passWordLength;
                    _this._default.completeCallBack = ''; // 被动调起软键盘的暂不支持
                    _this._default.clickCallBack = options.clickCallBack;
                    if(_target_length === '' || isNaN(_target_length)) {
                        _this._default.length = 0;
                    } else {
                        _this._default.length = parseInt(_target_length);
                    }

                    // 判断是否为input/textarea还是使用div构造的输入框
                    if(_this._default.EVENT_NODE_TAGNAME.toLowerCase() == 'input' || _this._default.EVENT_NODE_TAGNAME.toLowerCase() == 'textarea') {
                        _this._default.isInput = true;
                        _this._default.inputValue = _this._default.EVENT_NODE.value;
                    } else {
                        _this._default.isInput = false;
                        _this._default.inputValue = _this._default.EVENT_NODE.getAttribute("data-content") || "";
                        $(".libh_change_key").each(function(index, elem) {
                            $(elem).removeClass("libh_input_cursor");
                            if(!$(elem).attr("data-content")) {
                                console.log(33333)
                                $(elem).removeClass("libh_change_key");
                            }
                        });
                        $(_this._default.EVENT_NODE).addClass("libh_change_key libh_input_cursor");
                    }
                    _this.init();
                })
            })
        } else {
            console.error("目标不存在指定类名且不存在指定触发键盘类名")
        }
    };

    // 初始化
    NewKeyBoard.prototype.init = function() {
        var _this = this;
        var _parentNode = document.querySelector(_this._default.BODY_NODE_ID);
        var _type = _parentNode ? _parentNode.getAttribute("type") : null;
        if(_parentNode && _type !== _this._default.type) {
            document.querySelector(_this._default.BODY_NODE_ID).parentNode.removeChild(document.querySelector(_this._default.BODY_NODE_ID));
        }
        // 初始化分两种情况，一种已存在一样类型的键盘，一种不存在或存在不同类型的键盘
        if(_parentNode && _type == _this._default.type) {
            _parentNode.setAttribute("display", 'block');
            if(_this._default.type == '1') {
                if(_this._default.status == '0') {
                    // 目前为小写键盘
                } else if(_this._default.status == '1') {
                    $(_this._default.BODY_NODE_ID + ".board_middle_content").html(_this._default.routineLowerHtml);
                } else if(_this._default.status == '2') {
                    var html = _this._default.routineNumberHtml
                        +'<div class="board_middle_content">'
                        +     _this._default.routineLowerHtml
                        +'</div>'
                    $(_this._default.BODY_NODE_ID + ".board_main_content").html(html);
                    $(_this._default.BODY_NODE_ID + ".board_bottom_content").html(_this._default.routineLetterLastHtml);
                }
                _this._default.status = '0';
                $(_this._default.BODY_NODE_ID).removeClass('routine_down_fade').addClass('routine_up_fade');
                _this.addRoutineEvent();
                // 全局监听事件
                // $(document).off('’focus', 'body.libh_key_board_flag');
                $(document).off(_this._default.eventType, 'body.libh_key_board_flag');
                // 全局监听事件
                $(document).on(_this._default.eventType, 'body.libh_key_board_flag', _this.hideKeyBoard);
            } else if(_this._default.type == '2') {
                $(_this._default.BODY_NODE_ID).removeClass('password_down_fade').addClass('password_up_fade');
                _this.addPasswordEvent();
            } else {
                $(_this._default.BODY_NODE_ID).removeClass('number_down_fade').addClass('number_up_fade');
                _this.addNumberEvent();
                // 全局监听事件
                $(document).off(_this._default.eventType, 'body.libh_key_board_flag');
                // 全局监听事件
                $(document).on(_this._default.eventType, 'body.libh_key_board_flag', _this.hideKeyBoard);
            }
        } else {
            if(_this._default.type == 1) {
                _this.generateRoutineNumberHtml();
                _this.generateRoutineLowerHtml();
                _this.generateRoutineLetterLastHtml();
                var routineHtml = _this.generateRoutineHtml();
                $("body").append(routineHtml);
                _this.addRoutineEvent();
                _this.generateRoutineUpperHtml();
                _this.generateRoutineSymbolHtml();
                _this.generateRoutineSymbolLastHtml();
            } else if(_this._default.type == 2) {
                _this._default.passwordHtml = $(_this.generatePasswordHtml());
                $("body").append(_this._default.passwordHtml);
                _this.addPasswordEvent();
            } else {
                _this._default.numberElem = $(_this.generateNumberHtml());
                $("body").append(_this._default.numberElem);
                _this.addNumberEvent();
            }

            // 全局监听事件
            $(document).off(_this._default.eventType, 'body.libh_key_board_flag');
            // 全局监听事件
            $(document).on(_this._default.eventType, 'body.libh_key_board_flag', _this.hideKeyBoard);
        }
    };

    // 生成纯数字键盘代码
    NewKeyBoard.prototype.generateNumberHtml = function() {
        var _this = this;
        var numberHtml = '<div id="' + _this._default.BODY_NODE +'" class="number_up_fade" type="0" display="block">'
            +' <table width="100%" border="0" cellspacing="0" cellpadding="0" class="random">'
            +'   <tbody>'
            +'     <tr>'
        _this._default.NumberKeyPanel.forEach(function(num, key) {
            if(key == 0 || key % 3) {
                if(num == 'enter') {
                    numberHtml +=   '        <td><a herf="javascript:void(0)" class="click_event font_style" flag="'+num+'">确定</a></td>'
                } else if(num == 'del') {
                    numberHtml +=   '        <td><a herf="javascript:void(0)" class="click_event font_style" flag="del"><svg width="42px" height="42px" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 84 84" enable-background="new 0 0 84 84" xml:space="preserve"><rect opacity="0.5" fill="none"></rect><g><g><path fill="#666" d="M76,25v34H23L8.9,42L23,25H76 M79,22H21.6L5,42l16.6,20H79V22L79,22z"></path></g><polygon fill="#666" points="41.3,53 49.6,44.7 57.9,53 60.6,50.3 52.3,42 60.6,33.7 57.9,31 49.6,39.3 41.3,31 38.6,33.7 46.9,42 38.6,50.3  "></polygon></g></svg></a></td>'
                } else {
                    numberHtml +=   '        <td><a herf="javascript:void(0)" class="click_event" flag="'+num+'">'+num+'</a></td>'
                }
            } else {
                numberHtml +=   '     </tr>'
                    +  '     <tr>';
                if(num == 'enter') {
                    numberHtml +=   '        <td><a herf="javascript:void(0)" class="click_event font_style" flag="'+num+'">确定</a></td>'
                } else if(num == 'del') {
                    numberHtml +=   '        <td><a herf="javascript:void(0)" class="click_event font_style" flag="del"><svg width="42px" height="42px" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 84 84" enable-background="new 0 0 84 84" xml:space="preserve"><rect opacity="0.5" fill="none"></rect><g><g><path fill="#666" d="M76,25v34H23L8.9,42L23,25H76 M79,22H21.6L5,42l16.6,20H79V22L79,22z"></path></g><polygon fill="#666" points="41.3,53 49.6,44.7 57.9,53 60.6,50.3 52.3,42 60.6,33.7 57.9,31 49.6,39.3 41.3,31 38.6,33.7 46.9,42 38.6,50.3  "></polygon></g></svg></a></td>'
                } else {
                    numberHtml +=   '        <td><a herf="javascript:void(0)" class="click_event" flag="'+num+'">'+num+'</a></td>'
                }
            }
        });
        numberHtml +=   '     </tr>'
            +  '   </tbody>'
            +  ' </table>'
            +  '</div>';
        return numberHtml;
    }

    // 生成支付密码键盘
    NewKeyBoard.prototype.generatePasswordHtml = function() {
        var _this = this;
        var passwordHtml ='<div id="' + _this._default.BODY_NODE +'" class="password_popup password_up_fade" type="2" display="block">'
            +'  <div class="libh_key_board_pop"></div>'
            +'  <div class="libh_cipher_box">'
            +'    <p class="back_btn"><a herf="javascript:void(0)" class="close_btn"></a>请输入支付密码</p>'
            +'    <div class="libh_cipher_box_content">'
            +'       <table width="100%" border="0" cellspacing="0" cellpadding="0" class="random">'
            +'         <tbody>'
            +'           <tr>'
        for(var i = 0; i < _this._default.passWordLength; i++) {
            passwordHtml += '             <td><a herf="javascript:void(0)"></a></td>';
        }
        passwordHtml += '           </tr>'
            +'         </tbody>'
            +'       </table>'
            +'     </div>'
            +'   </div>'
            +'  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="random password_table">'
            +'   <tbody>'
            +'     <tr>'
        _this._default.passwordKeyPanel.forEach(function(num, key) {
            if(key == 0 || key % 3) {
                if(num === '') {
                    passwordHtml +=   '        <td><a herf="javascript:void(0)" class="click_event font_style" flag="enter">'+num+'</a></td>'
                } else if(num == 'del') {
                    passwordHtml +=   '        <td><a herf="javascript:void(0)" class="click_event font_style" flag="del"><svg width="42px" height="42px" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 84 84" enable-background="new 0 0 84 84" xml:space="preserve"><rect opacity="0.5" fill="none"></rect><g><g><path fill="#666" d="M76,25v34H23L8.9,42L23,25H76 M79,22H21.6L5,42l16.6,20H79V22L79,22z"></path></g><polygon fill="#666" points="41.3,53 49.6,44.7 57.9,53 60.6,50.3 52.3,42 60.6,33.7 57.9,31 49.6,39.3 41.3,31 38.6,33.7 46.9,42 38.6,50.3    "></polygon></g></svg></a></td>'
                } else {
                    passwordHtml +=   '        <td><a herf="javascript:void(0)" class="click_event" flag="'+num+'">'+num+'</a></td>'
                }
            } else {
                passwordHtml +=   '     </tr>'
                    +  '     <tr>';
                if(num === '') {
                    passwordHtml +=   '        <td><a herf="javascript:void(0)" class="click_event font_style" flag="enter">'+num+'</a></td>'
                } else if(num == 'del') {
                    passwordHtml +=   '        <td><a herf="javascript:void(0)" class="click_event font_style" flag="del"><svg width="42px" height="42px" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 84 84" enable-background="new 0 0 84 84" xml:space="preserve"><rect opacity="0.5" fill="none"></rect><g><g><path fill="#666" d="M76,25v34H23L8.9,42L23,25H76 M79,22H21.6L5,42l16.6,20H79V22L79,22z"></path></g><polygon fill="#666" points="41.3,53 49.6,44.7 57.9,53 60.6,50.3 52.3,42 60.6,33.7 57.9,31 49.6,39.3 41.3,31 38.6,33.7 46.9,42 38.6,50.3    "></polygon></g></svg></a></td>'
                } else {
                    passwordHtml +=   '        <td><a herf="javascript:void(0)" class="click_event" flag="'+num+'">'+num+'</a></td>'
                }
            }
        });
        passwordHtml +=   '     </tr>'
            +  '   </tbody>'
            +  ' </table>'
            +  '</div>';
        return passwordHtml;
    }

    // 生成常规键盘
    NewKeyBoard.prototype.generateRoutineHtml = function() {
        var _this = this;
        var routineHtml ='<div id="' + _this._default.BODY_NODE +'" class="routine_board routine_up_fade" type="1" display="block">'
            +'  <div class="board_main_content">'
            +      _this._default.routineNumberHtml
            +'     <div class="board_middle_content">'
            +        _this._default.routineLowerHtml
            +'     </div>'
            +'   </div>'
            +'   <div class="board_bottom_content">'
            +      _this._default.routineLetterLastHtml
            +'   </div>'
            +'</div>';

        return routineHtml;
    }

    // 生成数字列
    NewKeyBoard.prototype.generateRoutineNumberHtml = function() {
        var routineNumberHtml = '<p>';
        this._default.routineNumberKeyPanel.forEach(function(number) {
            routineNumberHtml   +='<i class="click_event" flag="'+number+'">'+number+'</i>';
        })
        routineNumberHtml    += '</p>';
        this._default.routineNumberHtml = routineNumberHtml;
    }

    // 生成小写字母列
    NewKeyBoard.prototype.generateRoutineLowerHtml = function() {
        var routineLowerHtml = '';
        this._default.routineLowerKeyPanel.forEach(function(letters, index) {
            if(index == 1) {
                routineLowerHtml  += '<p class="line_invagination">';
            } else {
                routineLowerHtml  += '<p>';
            }
            letters.forEach(function(letter) {
                if(letter === 'upper') {
                    routineLowerHtml+='<i class="click_event tab-upper" flag="'+letter+'">'
                        + '<svg width="42px" height="42px" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 84 84" enable-background="new 0 0 84 84" xml:space="preserve">'
                        + ' <rect opacity="0.5" fill="none" width="84" height="84"></rect>'
                        + ' <g>'
                        + '   <path fill="#fff" d="M 40,3 L 80,40 L 60,40 L 60,70 L 20,70 L 20,40 L 0,40 Z " stroke="#666666"></path>'
                        + ' </g>'
                        + '</svg>'
                        +'</i>';
                } else if(letter === 'del') {
                    routineLowerHtml+='<i class="click_event key-delete" flag="'+letter+'"><svg width="42px" height="42px" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 84 84" enable-background="new 0 0 84 84" xml:space="preserve"><rect opacity="0.5" fill="none"></rect><g><g><path fill="#666" d="M76,25v34H23L8.9,42L23,25H76 M79,22H21.6L5,42l16.6,20H79V22L79,22z"></path></g><polygon fill="#666" points="41.3,53 49.6,44.7 57.9,53 60.6,50.3 52.3,42 60.6,33.7 57.9,31 49.6,39.3 41.3,31 38.6,33.7 46.9,42 38.6,50.3   "></polygon></g></svg></i>';
                } else {
                    routineLowerHtml+='<i class="click_event" flag="'+letter+'">'+letter+'</i>';
                }
            })
            routineLowerHtml    += '</p>';
        })
        this._default.routineLowerHtml = routineLowerHtml;
    }

    // 生成大写字母列
    NewKeyBoard.prototype.generateRoutineUpperHtml = function() {
        var routineUpperHtml = '';
        this._default.routineUpperKeyPanel.forEach(function(letters, index) {
            if(index == 1) {
                routineUpperHtml  += '<p class="line_invagination">';
            } else {
                routineUpperHtml  += '<p>';
            }
            letters.forEach(function(letter) {
                if(letter === 'lower') {
                    routineUpperHtml+='<i class="click_event tab-upper" flag="'+letter+'">'
                        + '<svg width="42px" height="42px" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 84 84" enable-background="new 0 0 84 84" xml:space="preserve">'
                        + ' <rect opacity="0.5" fill="none" width="84" height="84"></rect>'
                        + ' <g>'
                        + '   <path fill="#7A7A7A" d="M 40,3 L 80,40 L 60,40 L 60,70 L 20,70 L 20,40 L 0,40 Z "></path>'
                        + ' </g>'
                        + '</svg>'
                        +'</i>';
                } else if(letter === 'del') {
                    routineUpperHtml+='<i class="click_event key-delete" flag="'+letter+'"><svg width="42px" height="42px" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 84 84" enable-background="new 0 0 84 84" xml:space="preserve"><rect opacity="0.5" fill="none"></rect><g><g><path fill="#666" d="M76,25v34H23L8.9,42L23,25H76 M79,22H21.6L5,42l16.6,20H79V22L79,22z"></path></g><polygon fill="#666" points="41.3,53 49.6,44.7 57.9,53 60.6,50.3 52.3,42 60.6,33.7 57.9,31 49.6,39.3 41.3,31 38.6,33.7 46.9,42 38.6,50.3   "></polygon></g></svg></i>';
                } else {
                    routineUpperHtml+='<i class="click_event" flag="'+letter+'">'+letter+'</i>';
                }
            })
            routineUpperHtml    += '</p>';
        })
        this._default.routineUpperHtml = routineUpperHtml;
    }

    // 生成字符列
    NewKeyBoard.prototype.generateRoutineSymbolHtml = function() {
        var routineSymbolHtml = '';
        this._default.routineSymbolKeyPanel.forEach(function(letters, index) {
            if(index == 2 || index == 3) {
                routineSymbolHtml  += '<p class="line_invagination02">';
            } else {
                routineSymbolHtml  += '<p>';
            }
            letters.forEach(function(letter) {
                if(letter === 'del') {
                    routineSymbolHtml+='<i class="click_event symbol_del" flag="'+letter+'"><svg width="42px" height="42px" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 84 84" enable-background="new 0 0 84 84" xml:space="preserve"><rect opacity="0.5" fill="none"></rect><g><g><path fill="#666" d="M76,25v34H23L8.9,42L23,25H76 M79,22H21.6L5,42l16.6,20H79V22L79,22z"></path></g><polygon fill="#666" points="41.3,53 49.6,44.7 57.9,53 60.6,50.3 52.3,42 60.6,33.7 57.9,31 49.6,39.3 41.3,31 38.6,33.7 46.9,42 38.6,50.3  "></polygon></g></svg></i>';
                } else {
                    routineSymbolHtml+='<i class="click_event" flag="'+letter+'">'+letter+'</i>';
                }
            })
            routineSymbolHtml    += '</p>';
        })
        this._default.routineSymbolHtml = routineSymbolHtml;
    }

    // 生成字母底部列
    NewKeyBoard.prototype.generateRoutineLetterLastHtml = function() {
        var routineLetterLastHtml = '<p>';
        this._default.routineLetterLast.forEach(function(letter) {
            if(letter === 'symbol') {
                routineLetterLastHtml+='<i class="click_event tab-num" flag="'+letter+'">#=+</i>';
            } else if(letter === 'blank') {
                routineLetterLastHtml+='<i class="click_event tab-blank" flag="'+letter+'">空格</i>';
            } else if(letter === 'enter') {
                routineLetterLastHtml+='<i class="click_event tab-enter" flag="'+letter+'">确定</i>';
            } else {
                routineLetterLastHtml+='<i class="click_event tab-point" flag="'+letter+'">'+letter+'</i>';
            }
        })
        routineLetterLastHtml    += '</p>';
        this._default.routineLetterLastHtml = routineLetterLastHtml;
    }

    // 生成字符底部列
    NewKeyBoard.prototype.generateRoutineSymbolLastHtml = function() {
        var routineSymbolLastHtml = '<p>';
        this._default.routineSymbolLast.forEach(function(letter) {
            if(letter === 'letter') {
                routineSymbolLastHtml+='<i class="click_event tab-num" flag="'+letter+'">abc</i>';
            } else if(letter === 'blank') {
                routineSymbolLastHtml+='<i class="click_event tab-blank" flag="'+letter+'">空格</i>';
            } else if(letter === 'enter') {
                routineSymbolLastHtml+='<i class="click_event tab-enter" flag="'+letter+'">确定</i>';
            } else {
                routineSymbolLastHtml+='<i class="click_event tab-point" flag="'+letter+'">'+letter+'</i>';
            }
        })
        routineSymbolLastHtml    += '</p>';
        this._default.routineSymbolLastHtml = routineSymbolLastHtml;
    }

    // 纯数字键盘的事件绑定
    NewKeyBoard.prototype.addNumberEvent = function() {
        var _this = this;
        $(_this._default.BODY_NODE_ID).off('click');
        $(_this._default.BODY_NODE_ID).on('click', function(e) {
            e.preventDefault();
            e.stopPropagation();
            if(_this._default.isInput) {
                _this._default.selectionStart = _this.getCaretPosition(); // 获取光标位置
                _this.setCaretPosition(); // 设置光标位置
            }
        });
        $(_this._default.BODY_NODE_ID).off('touchstart', '.click_event');
        $(_this._default.BODY_NODE_ID).on('touchstart', '.click_event', function(e) {
            var flag = $(this)[0].getAttribute("flag");
            if(flag !== "del") {
                e.target.style.backgroundColor = "#cccdd0";
                e.target.style.color = "#fff";
            }
        });
        $(_this._default.BODY_NODE_ID).off('touchend', '.click_event');
        $(_this._default.BODY_NODE_ID).on('touchend', '.click_event', function(e) {
            e.preventDefault();
            e.stopPropagation();
            // 判断是否为input/textarea还是使用div构造的输入框
            if(_this._default.isInput) {
                _this._default.inputValue = _this._default.EVENT_NODE.value;
            } else {
                _this._default.inputValue = _this._default.EVENT_NODE.getAttribute("data-content") || "";
            }
            var flag = $(this)[0].getAttribute("flag");
            if(flag !== "del") {
                e.target.style.backgroundColor = "#fff";
                e.target.style.color = "#666";
            }
            if(flag == "enter") {
                $(_this._default.BODY_NODE_ID).attr("display", 'none');
                $(_this._default.BODY_NODE_ID).removeClass('number_up_fade').addClass('number_down_fade');
                if(typeof(_this._default.clickCallBack) == "function") {
                    _this._default.clickCallBack(flag, _this._default.inputValue, _this._default.EVENT_NODE);
                }
                if(!_this._default.isInput) {
                    $(_this._default.EVENT_NODE).removeClass("libh_input_cursor");
                    if(!_this._default.inputValue) {
                        $(_this._default.EVENT_NODE).removeClass("libh_change_key");
                    }
                }
                return;
            } else if(flag == "del") {
                var value = _this._default.inputValue;
                if(_this._default.isInput) {
                    if(value !== '') {
                        _this._default.selectionStart = _this.getCaretPosition(); // 获取光标位置
                        _this._default.inputValue = value.substring(0, _this._default.selectionStart - 1) + value.substring(_this._default.selectionStart, value.length);
                        _this._default.selectionStart -= 1;
                    }
                } else {
                    _this._default.inputValue = value.substring(0, value.length - 1);
                }
            } else {
                if(_this._default.isInput) {
                    var value = _this._default.inputValue;
                    if(value !== '' && !(_this._default.length > 0 && value.length >= _this._default.length)) {
                        _this._default.selectionStart = _this.getCaretPosition(); // 获取光标位置
                        _this._default.inputValue = value.substring(0, _this._default.selectionStart) + flag + value.substring(_this._default.selectionStart, value.length);
                        _this._default.selectionStart += 1;
                    }
                } else {
                    if(!(_this._default.length > 0 && _this._default.inputValue.length >= _this._default.length)) {
                        _this._default.inputValue += flag;
                    }
                }
            }

            if(_this._default.isAssign) {
                if(_this._default.isInput) {
                    _this._default.EVENT_NODE.value = _this._default.inputValue;
                    _this.setCaretPosition(); // 设置光标位置
                } else {
                    // _this._default.EVENT_NODE.innerHTML = _this._default.inputValue;
                    _this._default.EVENT_NODE.setAttribute("data-content", _this._default.inputValue);
                }
            }
            if(typeof(_this._default.clickCallBack) == "function") {
                _this._default.clickCallBack(flag, _this._default.inputValue, _this._default.EVENT_NODE);
            }
        });
    }

    // 支付密码键盘的事件绑定
    NewKeyBoard.prototype.addPasswordEvent = function() {
        var _this = this;
        if(_this._default.clickHidden) {
            // 点击遮罩层关闭支付键盘
            $(_this._default.BODY_NODE_ID).off('click', ".libh_key_board_pop");
            $(_this._default.BODY_NODE_ID).on('click', ".libh_key_board_pop", function() {
                $(_this._default.BODY_NODE_ID).attr("display", 'none');
                $(_this._default.BODY_NODE_ID).removeClass('password_up_fade').addClass('password_down_fade');
                var passwordElem = $(_this._default.BODY_NODE_ID+" .libh_cipher_box_content td");
                passwordElem.find("a").html('');
            });
        }
        // 关闭支付键盘
        $(_this._default.BODY_NODE_ID).off('click', ".close_btn");
        $(_this._default.BODY_NODE_ID).on('click', ".close_btn", function(e) {
            e.preventDefault();
            e.stopPropagation();
            $(_this._default.BODY_NODE_ID).attr("display", 'none');
            $(_this._default.BODY_NODE_ID).removeClass('password_up_fade').addClass('password_down_fade');
            var passwordElem = $(_this._default.BODY_NODE_ID+" .libh_cipher_box_content td");
            passwordElem.find("a").html('');
        });
        $(_this._default.BODY_NODE_ID).off('touchstart', '.click_event');
        $(_this._default.BODY_NODE_ID).on('touchstart', '.click_event', function(e) {
            e.target.style.backgroundColor = "#cccdd0";
            e.target.style.color = "#fff";
        });
        $(_this._default.BODY_NODE_ID).off('touchend', '.click_event');
        $(_this._default.BODY_NODE_ID).on('touchend', '.click_event', function(e) {
            var flag = $(this)[0].getAttribute("flag");
            if(flag !== "del") {
                e.target.style.backgroundColor = "#fff";
                e.target.style.color = "#666";
            }
        });
        $(_this._default.BODY_NODE_ID).off('click', '.click_event');
        $(_this._default.BODY_NODE_ID).on('click', '.click_event', function(e) {
            var flag = $(this)[0].getAttribute("flag");
            var passwordElem = $(_this._default.BODY_NODE_ID+" .libh_cipher_box_content td");
            if(flag == "del") {
                var value = _this._default.inputValue;
                if(value.length) {
                    _this._default.inputValue = value.substring(0, value.length - 1);
                    passwordElem.eq(value.length - 1).find("a").html('');
                }
            } else {
                _this._default.inputValue += flag;
                passwordElem.eq(_this._default.inputValue.length - 1).find("a").html('*');

                if(_this._default.inputValue.length === _this._default.passWordLength) {
                    $(_this._default.BODY_NODE_ID).attr("display", 'none');
                    $(_this._default.BODY_NODE_ID).removeClass('password_up_fade').addClass('password_down_fade');
                    passwordElem.find("a").html('');
                    if(typeof(_this._default.completeCallBack) == "function") {
                        _this._default.completeCallBack( _this._default.inputValue);
                    }
                }
            }
        });
    }

    // 常规键盘事件绑定
    NewKeyBoard.prototype.addRoutineEvent = function() {
        var _this = this;
        $(_this._default.BODY_NODE_ID).off('click');
        $(_this._default.BODY_NODE_ID).on('click', function(e) {
            e.preventDefault();
            e.stopPropagation();
            if(_this._default.isInput) {
                _this._default.selectionStart = _this.getCaretPosition(); // 获取光标位置
                _this.setCaretPosition(); // 设置光标位置
            }
        });
        $(_this._default.BODY_NODE_ID).off('touchstart', '.click_event');
        $(_this._default.BODY_NODE_ID).on('touchstart', '.click_event', function(e) {
            var flag = $(this)[0].getAttribute("flag");
            if(flag !== "del") {
                e.target.style.backgroundColor = "#cccdd0";
                e.target.style.color = "#fff";
            }
        });
        $(_this._default.BODY_NODE_ID).off('touchend', '.click_event');
        $(_this._default.BODY_NODE_ID).on('touchend', '.click_event', function(e) {
            e.preventDefault();
            e.stopPropagation();
            document.activeElement.blur();
            // 判断是否为input/textarea还是使用div构造的输入框
            if(_this._default.isInput) {
                _this._default.inputValue = _this._default.EVENT_NODE.value;
            } else {
                _this._default.inputValue = _this._default.EVENT_NODE.getAttribute("data-content") || "";
            }
            var flag = $(this)[0].getAttribute("flag");
            if(flag !== "del") {
                e.target.style.backgroundColor = "#fff";
                e.target.style.color = "#666";
            }
            if(flag === "upper") {
                // 切换成大写
                $(_this._default.BODY_NODE_ID + ".board_middle_content").html(_this._default.routineUpperHtml);
                _this.addRoutineEvent();
                if(_this._default.isInput) {
                    _this.setCaretPosition(); // 设置光标位置
                }
                return;
            } else if(flag === "lower") {
                // 切换成小写
                $(_this._default.BODY_NODE_ID + ".board_middle_content").html(_this._default.routineLowerHtml);
                _this.addRoutineEvent();
                if(_this._default.isInput) {
                    _this.setCaretPosition(); // 设置光标位置
                }
                return;
            } else if(flag === "symbol") {
                // 切换成字符
                $(_this._default.BODY_NODE_ID + ".board_main_content").html(_this._default.routineSymbolHtml);
                $(_this._default.BODY_NODE_ID + ".board_bottom_content").html(_this._default.routineSymbolLastHtml);
                _this.addRoutineEvent();
                if(_this._default.isInput) {
                    _this.setCaretPosition(); // 设置光标位置
                }
                return;
            } else if(flag === "letter") {
                // 切换成字母
                var html = _this._default.routineNumberHtml
                    +'<div class="board_middle_content">'
                    +     _this._default.routineLowerHtml
                    +'</div>'
                $(_this._default.BODY_NODE_ID + ".board_main_content").html(html);
                $(_this._default.BODY_NODE_ID + ".board_bottom_content").html(_this._default.routineLetterLastHtml);
                _this.addRoutineEvent();
                if(_this._default.isInput) {
                    _this.setCaretPosition(); // 设置光标位置
                }
                return;
            } else if(flag === "del") {
                var value = _this._default.inputValue;
                if(_this._default.isInput) {
                    if(value !== '') {
                        // _this._default.inputValue = value.substring(0, value.length - 1);
                        _this._default.selectionStart = _this.getCaretPosition(); // 获取光标位置
                        _this._default.inputValue = value.substring(0, _this._default.selectionStart - 1) + value.substring(_this._default.selectionStart, value.length);
                        _this._default.selectionStart -= 1;
                    }
                } else {
                    _this._default.inputValue = value.substring(0, value.length - 1);
                }
            } else if(flag === "enter") {
                $(_this._default.BODY_NODE_ID).attr("display", 'none');
                $(_this._default.BODY_NODE_ID).removeClass('routine_up_fade').addClass('routine_down_fade');
                if(typeof(_this._default.clickCallBack) == "function") {
                    _this._default.clickCallBack(flag, _this._default.inputValue, _this._default.EVENT_NODE);
                }
                if(!_this._default.isInput) {
                    $(_this._default.EVENT_NODE).removeClass("libh_input_cursor");
                    if(!_this._default.inputValue) {
                        $(_this._default.EVENT_NODE).removeClass("libh_change_key");
                    }
                }
                return;
            } else if(flag === "blank") {
                if(_this._default.isInput) {
                    if(!(_this._default.length > 0 && value.length >= _this._default.length)) {
                        _this._default.selectionStart = _this.getCaretPosition(); // 获取光标位置
                        var value = _this._default.inputValue;
                        _this._default.inputValue = value.substring(0, _this._default.selectionStart) + '' + value.substring(_this._default.selectionStart, value.length);
                        _this._default.selectionStart += 1;
                    }
                } else {
                    _this._default.inputValue += '';
                }
            } else {
                if(_this._default.isInput) {
                    if(value !== '' && !(_this._default.length > 0 && value.length >= _this._default.length)) {
                        _this._default.selectionStart = _this.getCaretPosition(); // 获取光标位置
                        var value = _this._default.inputValue;
                        _this._default.inputValue = value.substring(0, _this._default.selectionStart) + flag + value.substring(_this._default.selectionStart, value.length);
                        _this._default.selectionStart += 1;
                    }
                } else {
                    if(!(_this._default.length > 0 && _this._default.inputValue.length >= _this._default.length)) {
                        _this._default.inputValue += flag;
                    }
                }
            }
            if(_this._default.isAssign) {
                if(_this._default.isInput) {
                    _this._default.EVENT_NODE.value = _this._default.inputValue;
                    _this.setCaretPosition(); // 设置光标位置
                } else {
                    _this._default.EVENT_NODE.setAttribute("data-content", _this._default.inputValue);
                }
            }
            if(typeof(_this._default.clickCallBack) == "function") {
                _this._default.clickCallBack(flag, _this._default.inputValue, _this._default.EVENT_NODE);
            }
        });
    }

    // 获得光标位置兼容IE FF
    NewKeyBoard.prototype.getCaretPosition = function() {
        var _this = this;
        var result = 0;
        if (!isNaN(_this._default.EVENT_NODE.selectionStart)) { //IE以外
            result = _this._default.EVENT_NODE.selectionStart;
        } else { //IE
            try{
                var rng;
                if (_this._default.EVENT_NODE.tagName == "textarea") { //TEXTAREA
                    rng = event.srcElement.createTextRange();
                    rng.moveToPoint(event.x, event.y);
                } else { //Text
                    rng = document.selection.createRange();
                }
                rng.moveStart("character", -event.srcElement.value.length);
                result = rng.text.length;
            }catch (e){
                throw new Error(10,"获取光标位置失败")
            }
        }
        return result;
    }

    // 设置光标位置兼容IE/FF
    NewKeyBoard.prototype.setCaretPosition = function(){
        var _this = this;
        if(_this._default.EVENT_NODE.setSelectionRange){
            setTimeout(function(){
                _this._default.EVENT_NODE.focus();
                _this._default.EVENT_NODE.setSelectionRange(_this._default.selectionStart, _this._default.selectionStart);
            }, 0);
        }else if(_this._default.EVENT_NODE.createTextRange){
            var rng = _this._default.EVENT_NODE.createTextRange();
            rng.move('character', _this._default.selectionStart);
            rng.select();
        }
    }

    // 隐藏键盘
    NewKeyBoard.prototype.hideKeyBoard = function(){
        console.log("隐藏键盘");
        //隐藏键盘时显示底部按钮
        HuanziFooter.show();

        var _this = this;
        // 取消监听
        $(document).off(softKeyBoard._default.eventType, 'body.libh_key_board_flag');
        var _parentNode = document.querySelector(softKeyBoard._default.BODY_NODE_ID);
        if(_parentNode && _parentNode.getAttribute("type") == '1' && _parentNode.getAttribute("display") == 'block') {
            _parentNode.setAttribute("display", "none");
            $(softKeyBoard._default.BODY_NODE_ID).removeClass('routine_up_fade').addClass('routine_down_fade');
            if(!softKeyBoard._default.isInput) {
                $(softKeyBoard._default.EVENT_NODE).removeClass("libh_input_cursor");
                if(!softKeyBoard._default.inputValue) {
                    $(softKeyBoard._default.EVENT_NODE).removeClass("libh_change_key");
                }
            }
            if(typeof(softKeyBoard._default.clickCallBack) == "function") {
                softKeyBoard._default.clickCallBack('remove', softKeyBoard._default.inputValue, softKeyBoard._default.EVENT_NODE);
            }
        } else if(_parentNode && _parentNode.getAttribute("type") == '0' && _parentNode.getAttribute("display") == 'block') {
            _parentNode.setAttribute("display", "none");
            $(softKeyBoard._default.BODY_NODE_ID).removeClass('number_up_fade').addClass('number_down_fade');
            if(!softKeyBoard._default.isInput) {
                $(softKeyBoard._default.EVENT_NODE).removeClass("libh_input_cursor");
                if(!softKeyBoard._default.inputValue) {
                    $(softKeyBoard._default.EVENT_NODE).removeClass("libh_change_key");
                }
            }
            if(typeof(softKeyBoard._default.clickCallBack) == "function") {
                softKeyBoard._default.clickCallBack('remove', softKeyBoard._default.inputValue, softKeyBoard._default.EVENT_NODE);
            }
        }
    }

    $('body').addClass("libh_key_board_flag"); // 添加标识，用于事件监听
    let options = params || {};
    //huanzi修改：先从window中取，不存在再创建
    let softKeyBoard = window.softKeyBoard;
    if(!softKeyBoard){
        softKeyBoard = new NewKeyBoard();
        window.softKeyBoard = softKeyBoard;
    }
    softKeyBoard.addEvent();
}
window.keyBoard = keyBoard;