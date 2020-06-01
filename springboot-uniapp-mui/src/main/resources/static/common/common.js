/**
 * 封装通用方法
 */
var HuanziCommonFun = {
    //判断是否为main页面
    isMain: function(){
        return isMain();
    },
    //获取当前iframe的contentWindow
    getIframeContentWindow:function(){
       return  window.parent.document.getElementById(window.parent.$(".select").data("iframe")).contentWindow;
    },
    //判断字符串是否为json格式
    isJSON : function (str) {
        if (typeof str == 'string') {
            try {
                JSON.parse(str);
                return true;
            } catch(e) {
                console.log(e);
                return false;
            }
        }
    }
};

/* 封装自定义弹窗 */
var HuanziDialog = {
    mask: null,//mui遮阴层对象
    showSpeed: 700,//弹出速度
    hideSpeed: 500,//隐藏速度
    removeFlag: true,//close内部是否执行操作
    /**
     * 隐藏弹窗，内部方法
     * @param select jq元素选择器，#xxx、.xxx等，如果为空，则隐藏所有
     * @param callback 回调方法
     * @param speed 速度
     */
    hideFun: function (select, callback, speed) {
        let $huanziDialog = select ? $(select) : $(".huanzi-dialog");
        speed = speed ? speed : HuanziDialog.hideSpeed;

        //上右下左，居中
        $huanziDialog.each(function () {
            let dialog = $(this);
            let clazz = dialog.attr("class");
            if (clazz.indexOf("huanzi-dialog-top") > -1) {
                dialog.animate({top: '-100%'}, speed);
            } else if (clazz.indexOf("huanzi-dialog-right") > -1) {
                dialog.animate({right: '-85%'}, speed);
            } else if (clazz.indexOf("huanzi-dialog-bottom") > -1) {
                dialog.animate({bottom: '-100%'}, speed);
            } else if (clazz.indexOf("huanzi-dialog-left") > -1) {
                dialog.animate({left: '-85%'}, speed);
            } else if (clazz.indexOf("huanzi-dialog-center") > -1) {
                dialog.animate({opacity: 0}, speed);
            }
            setTimeout(function () {
                dialog.css("z-index", "-1");
            }, speed)
        });

        callback && callback();
    },

    /**
     * 显示弹窗，内部方法
     * @param select jq元素选择器，#xxx、.xxx等，如果为空，则显示所有
     * @param callback 回调方法
     * @param speed 速度
     */
    showFun: function (select, callback, speed) {
        let $huanziDialog = select ? $(select) : $(".huanzi-dialog");
        speed = speed ? speed : HuanziDialog.hideSpeed;

        //上右下左，居中
        $huanziDialog.each(function () {
            let dialog = $(this);
            dialog.css("z-index", "999");

            let clazz = dialog.attr("class");
            if (clazz.indexOf("huanzi-dialog-top") > -1) {
                dialog.animate({top: '0%'}, speed);
            } else if (clazz.indexOf("huanzi-dialog-right") > -1) {
                dialog.animate({right: '0%'}, speed);
            } else if (clazz.indexOf("huanzi-dialog-bottom") > -1) {
                dialog.animate({bottom: '0%'}, speed);
            } else if (clazz.indexOf("huanzi-dialog-left") > -1) {
                dialog.animate({left: '0%'}, speed);
            } else if (clazz.indexOf("huanzi-dialog-center") > -1) {
                dialog.animate({opacity: 1}, speed);
            }
        });
        HuanziDialog.removeFlag = true;
        callback && callback();
    },

    /**
     * 初始化mui遮阴层对象
     */
    init: function () {
        HuanziDialog.mask = mui.createMask();

        /**
         * 重写close方法
         */
        HuanziDialog.mask.close = function () {
            if (!HuanziDialog.removeFlag) {
                return;
            }
            //方法直接在这里执行
            HuanziDialog.hideFun();
            //调用删除
            HuanziDialog.mask._remove();
        };
    },

    /**
     * 显示弹窗，供外部调用（参数同内部方法一致）
     */
    show: function (select, callback, speed) {
        HuanziDialog.showFun(select, callback, speed);
        HuanziDialog.mask.show();//显示遮罩
    },

    /**
     * 隐藏弹窗，供外部调用（参数同内部方法一致）
     */
    hide: function (select, callback, speed) {
        HuanziDialog.hideFun(select, callback, speed);
        HuanziDialog.mask.close();//关闭遮罩
    },

    /**
     * 警告框
     * @param title 标题
     * @param message 内容
     * @param callback 点击确认的回调
     */
    alert: function (title, message, callback) {
        //huanzi修改：非main页面，调用父类
        if(!HuanziCommonFun.isMain()){
            return window.parent.HuanziDialog.alert(title, message, function () {
                //回调执行子类方法
                HuanziCommonFun.getIframeContentWindow().eval(callback());
            });
        }
        let $html = $("<div class=\"mui-popup mui-popup-in\" style=\"display: block;\">" +
            "<div class=\"mui-popup-inner\">" +
            "   <div class=\"mui-popup-title\">" + title + "</div>" +
            "   <div class=\"mui-popup-text\">" + message + "</div>" +
            "</div>" +
            "<div class=\"mui-popup-buttons\">" +
            "<span class=\"mui-popup-button mui-popup-button-bold confirm-but\">确定</span>" +
            "</div>" +
            "</div>");
        $html.find(".confirm-but").click(function () {
            HuanziDialog.removeFlag = true;
            HuanziDialog.mask.close();
            $html.remove();
            callback && callback();
        });
        HuanziDialog.mask.show();//显示遮罩
        HuanziDialog.removeFlag = false;
        $("body").append($html);
    },

    /**
     * 确认消息框
     * @param title 标题
     * @param message 内容
     * @param callback 点击确认的回调
     */
    confirm: function (title, message, callback) {
        //huanzi修改：非main页面，调用父类
        if(!HuanziCommonFun.isMain()){
            return window.parent.HuanziDialog.confirm(title, message, function () {
                //回调执行子类方法
                HuanziCommonFun.getIframeContentWindow().eval(callback());
            });
        }
        let $html = $("<div class=\"mui-popup mui-popup-in\" style=\"display: block;\">" +
            "<div class=\"mui-popup-inner\">" +
            "   <div class=\"mui-popup-title\">" + title + "</div>" +
            "   <div class=\"mui-popup-text\">" + message + "</div>" +
            "</div>" +
            "<div class=\"mui-popup-buttons\">" +
            "<span class=\"mui-popup-button mui-popup-button-bold cancel-but\" style='color: #585858;'>取消</span>" +
            "<span class=\"mui-popup-button mui-popup-button-bold confirm-but\">确定</span>" +
            "</div>" +
            "</div>");
        $html.find(".cancel-but").click(function () {
            HuanziDialog.removeFlag = true;
            HuanziDialog.mask.close();
            $html.remove();
        });
        $html.find(".confirm-but").click(function () {
            $html.find(".cancel-but").click();
            callback && callback();
        });

        HuanziDialog.mask.show();//显示遮罩
        HuanziDialog.removeFlag = false;
        $("body").append($html);
    },

    /**
     * 自动消失提示弹窗
     * @param message 内容
     * @param speed 存在时间
     */
    toast: function (message, speed) {
        speed = speed ? speed : 2000;
        //自定义样式
        // let $html = $("<div class=\"huanzi-dialog huanzi-dialog-center\" style=\"width: 45%;height: 20%;opacity: 1;z-index: 999;border-radius: 5px;background-color: rgba(0, 0, 0, 0.6);\">" +
        //     "    <p style=\" position: relative; top: 50%; left: 50%; transform: translate3d(-50%, -50%, 0) scale(1); color: #fff; font-size: 20px; \">" + message + "</p>" +
        //     "</div>");
        // $("body").append($html);
        // setTimeout(function () {
        //     $html.remove();
        // }, speed);

        //mui样式
        let toast = document.createElement('div');
        toast.classList.add('mui-toast-container');
        toast.innerHTML = '<div class="' + 'mui-toast-message' + '">' + message + '</div>';
        toast.addEventListener('webkitTransitionEnd', function() {
            if (!toast.classList.contains('mui-active')) {
                toast.parentNode.removeChild(toast);
                toast = null;
            }
        });
        //点击则自动消失
        toast.addEventListener('click', function() {
            toast.parentNode.removeChild(toast);
            toast = null;
        });
        document.body.appendChild(toast);
        toast.offsetHeight;
        toast.classList.add('mui-active');
        setTimeout(function() {
            toast && toast.classList.remove('mui-active');
        }, speed);

        return {
            isVisible: function() {return !!toast;}
        }
    },
    /**
     * 显示加载框
     * @param message
     */
    showLoading : function(message) {
        var html = '';
        html += '<i class="mui-spinner mui-spinner-white"></i>';
        html += '<p class="text">' + (message || "数据加载中") + '</p>';

        //遮罩层
        var mask=document.getElementsByClassName("mui-show-loading-mask");
        if(mask.length==0){
            mask = document.createElement('div');
            mask.classList.add("mui-show-loading-mask");
            document.body.appendChild(mask);
            mask.addEventListener("touchmove", function(e){e.stopPropagation();e.preventDefault();});
        }else{
            mask[0].classList.remove("mui-show-loading-mask-hidden");
        }
        //加载框
        var toast=document.getElementsByClassName("mui-show-loading");
        if(toast.length==0){
            toast = document.createElement('div');
            toast.classList.add("mui-show-loading");
            toast.classList.add('loading-visible');
            document.body.appendChild(toast);
            toast.innerHTML = html;
            toast.addEventListener("touchmove", function(e){e.stopPropagation();e.preventDefault();});
        }else{
            toast[0].innerHTML = html;
            toast[0].classList.add("loading-visible");
        }
    },

    /**
     * 隐藏加载框
     * @param callback
     */
    hideLoading : function(callback) {
        var mask=document.getElementsByClassName("mui-show-loading-mask");
        var toast=document.getElementsByClassName("mui-show-loading");
        if(mask.length>0){
            mask[0].classList.add("mui-show-loading-mask-hidden");
        }
        if(toast.length>0){
            toast[0].classList.remove("loading-visible");
            callback && callback();
        }
    }
};

/**
 * 操作头部方法
 */
var HuanziHeader = {
    /**
     * 左右无按钮
     */
    init : function () {
        HuanziHeader.hideBackButton();
        window.parent.$(".huanzi-header-right-button-list").empty();
    },
    /**
     * 显示头部
     */
    show : function(){
        window.parent.$(".huanzi-header").show();
        window.parent.$(".huanzi-content").css("top",window.parent.$(".huanzi-header").css("height"));
        window.parent.document.getElementById(window.parent.$(".select").data("iframe")).height = window.parent.$('.huanzi-content').css("height");;
    },
    /**
     * 隐藏头部
     */
    hide : function(){
        window.parent.$(".huanzi-header").hide();
        window.parent.$('.huanzi-content').css("top","0");
        window.parent.document.getElementById(window.parent.$(".select").data("iframe")).height = window.parent.$('.huanzi-content').css("height");;
    },
    /**
     * 显示左边返回按钮
     */
    showBackButton : function (callback) {
        window.parent.$(".huanzi-header-back").show();
        if(callback){
            window.parent.$(".huanzi-header-back")[0].addEventListener('tap', function (event) {
                callback();
            });
        }else{
            window.parent.$(".huanzi-header-back")[0].addEventListener('tap', function (event) {
                mui.back();
            });
        }
    },
    /**
     * 隐藏左边返回按钮
     */
    hideBackButton : function () {
        window.parent.$(".huanzi-header-back").hide();
    },
    /**
     * 右边新增按钮
     */
    appendButton : function (html) {
        html = html.replace(/onclick="/,"onclick=\"document.getElementById($('.select').data('iframe')).contentWindow.");
        html = html.replace(/onclick='/,"onclick=\'document.getElementById($(\".select\").data(\"iframe\")).contentWindow.");
        window.parent.$(".huanzi-header-right-button-list").append(html);
    },
    /**
     * 删除按钮
     */
    removeButton : function (select) {
        window.parent.$(select).remove();
    },
    /**
     * 修改标题
     */
    title : function (title) {
        window.parent.$("title").text(title);
        window.parent.$(".mui-title").text(title);
    }
};

/**
 * 操作底部按钮方法
 */
var HuanziFooter = {
    /**
     * 显示底部按钮组
     */
    show : function () {
        window.parent.$(".huanzi-footer").show();
        window.parent.$('.huanzi-content').css("bottom","50px");
        window.parent.document.getElementById(window.parent.$(".select").data("iframe")).height = window.parent.$('.huanzi-content').css("height");;
    },
    /**
     * 隐藏底部按钮组
     */
    hide : function () {
        window.parent.$(".huanzi-footer").hide();
        window.parent.$('.huanzi-content').css("bottom","0");
        window.parent.document.getElementById(window.parent.$(".select").data("iframe")).height = window.parent.$('.huanzi-content').css("height");;
    }
};

//先初始化自定义弹窗
HuanziDialog.init();