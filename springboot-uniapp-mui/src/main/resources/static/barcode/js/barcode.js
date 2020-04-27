//二维码扫描
function barcode() {
    let currentWebview = null;
    let scan = null;
    let backVew = null;
    let flashVew = null;
    let flash = false;

    mui.showLoading("正在加载..","div");

    mui.plusReady(function () {
        //获取当前webview
        currentWebview = plus.webview.currentWebview();

        setTimeout(function () {
            mui.hideLoading();

            //构造扫码识别控件
            scan = plus.barcode.getBarcodeById('bcid');
            if(scan == null){
                scan = new plus.barcode.Barcode('bcid', [plus.barcode.QR], {
                    top: '0',
                    left: '0',
                    width: '100%',
                    height: '100%',
                    scanbarColor: '#1DA7FF',
                    position: 'static',
                    frameColor: '#1DA7FF'
                });
            }

            //扫码识别成功事件
            scan.onmarked = onmarked;

            currentWebview.append(scan);

            //开始扫码
            scan.start();

            // 创建原生按钮
            createBackVew();
            createFlashVew();

            //重写返回按键
            mui.back = function () {
                closeFun();
            };
        }, 100)
    });

    //关闭扫描，并隐藏控件
    function closeFun() {
        scan.setFlash(false);
        scan.close();
        backVew.close();
        flashVew.close();
        muiBackFun();
    }

    //扫码识别成功事件
    function onmarked(type, result) {
        closeFun();

        //非json，弹窗提示内容
        if(!HuanziCommonFun.isJSON(result)){
            HuanziDialog.alert("扫描结果通知", result, barcode);
            return;
        }

        //json，执行自己的业务
        let json = JSON.parse(result);
    }

    // 创建返回原生按钮
    function createBackVew() {
        let backImageUrl = window.location.origin;
        backVew = plus.nativeObj.View.getViewById('backVew');
        if(backVew == null){
            backVew = new plus.nativeObj.View('backVew', {
                    top: '30px',
                    left: '10px',
                    width: '35px',
                    height: '35px'
                },
                [{
                    tag: 'img',
                    id: 'backBar',
                    src: backImageUrl+'/barcode/img/back.png',
                }]);
        }
        currentWebview.append(backVew);
        backVew.addEventListener("click", function(e) {
            mui.back();
        }, false);
    }

    // 创建打开、关闭闪光灯原生按钮
    function createFlashVew() {
        let backImageUrl = window.location.origin;
        flashVew = plus.nativeObj.View.getViewById('flashVew');
        if(flashVew == null){
            flashVew = new plus.nativeObj.View('flashVew', {
                    bottom: '30px',
                    right: '10px',
                    width: '35px',
                    height: '35px'
                },
                [{
                    tag: 'img',
                    id: 'flashBar',
                    src: backImageUrl+'/barcode/img/flash_on.png',
                }]);
        }
        currentWebview.append(flashVew);
        flashVew.addEventListener("click", function(e) {
            flash = !flash;
            scan.setFlash(flash);
        }, false);
    }
}

//打开新页面
function openWindow() {
    let backImageUrl = window.location.origin;
    mui.openWindow({
        url:backImageUrl + '/muiwrapper/muiDialog',
        extras:{
            name:'mui'  //扩展参数
        }
    });
}