/**
 * 附件管理
 * js脚本
 */
var Attachment = {
    //上传附件
    upload: function (applyId) {
        //终止上传
        if (!applyId) {
            layui.layer.msg("applyId不能为空！");
            return;
        }

        //添加附件
        var formData = new FormData();
        let $form = $("form[applyId='"+applyId+"']");

        $form.find("input[name='attachment']").each((index, element) =>  {
            //过滤操作：input框有值，才append到formData
            if ($(element).val()) {
                //如果是图片，可以进行压缩再上传，依赖js-image-compressor
                formData.append("files",element.files[0]);
            }
        });

        $form.find(".attachments-list")[0]["delete"].forEach((element, index) => {
            formData.append("deletes",element);
        });

        if(formData.length <= 0){
            layui.layer.msg("附件不能为空！");
            return;
        }

        //追加applyId到formData
        formData.append("applyId", applyId);

        let element;
        layui.use('element', function() {
            element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
        });

        $form.find('.progress-div').css('display', 'block');

        //执行上传
        $.ajax({
            url: "/attachment/upload",
            type: "post",
            data: formData,
            processData: false,
            contentType: false,
            xhr: function() {
                let xhr = new XMLHttpRequest();
                //使用XMLHttpRequest.upload监听上传过程，注册progress事件
                xhr.upload.addEventListener('progress', function (e) {
                    //loaded代表上传了多少，total代表总数为多少
                    let progressRate = (e.loaded / e.total) * 100 + '%';

                    //设置进度条效果
                    element.progress('progress', progressRate);
                });
                return xhr;
            },
            success: function (data) {
                console.log('附件上传成功：', data);
            },
            error: function (e) {
                console.log('附件上传失败');
                throw e;
            }
        });
    },
    //添加附件
    appendAttachmentInput: function (btn) {
        //先追加html
        let $parent = $(btn).parents('.attachments-form').find(".attachments-list");

        //显示附件列表
        if($parent.children().length <= 0){
            $parent.parent().css('display', 'block');
        }

        $parent.append("<div><input type=\"file\" name=\"attachment\" class=\"hidden\"/></div>");

        //最新追加的input
        var attachments = $parent.find("input[name='attachment']");

        //绑定input的change事件，注意：当我们点击取消或×号时并不触发，但是无所谓，我们在upload方法进行过滤空的input就可以了
        attachments[attachments.length - 1].onchange = function(){
            var fileName = $(this).val();
            if (fileName) {
                $(this).parent("div").append("<p class='attachment-text-p'>" + fileName + "</p><i class=\"attachment-remove\">X</i>");
            }else{
                $(this).parent("div").remove();
            }
        };

        //触发最新的input的click
        attachments[attachments.length - 1].click();
    },
    //事件监听
    attachmentListener: function () {
        let $form = $(".attachments-form");
        $form.find(".attachments-list").each((index, element) =>{
            element["delete"] = [];
        });

        //删除附件事件监听
        $form.on("click", ".attachment-remove", function (even) {
            let $parent = $(this).parent();

            //需要调用后台删除文件
            let url = $(this).data("url");
            if(url){
                $parent.parent()[0]["delete"].push(url);
            }

            //隐藏附件列表
            if($parent.parent().children().length <= 1){
                $parent.parent().parent().css('display', 'none');
            }

            $parent.remove();
        });

        //附件下载事件监听
        $form.on("click", ".attachment-download", function (even) {
            //创建临时的、隐藏的form表单，post提交，数据在请求体里，相对安全
            let postData = {url:$(this).data("url")};
            let $form = $(document.createElement('form')).css({display: 'none'}).attr("method", "POST").attr("action", "/attachment/download");
            for (let key in postData) {
                let $input = $(document.createElement('input')).attr('name', key).val(postData[key]);
                $form.append($input);
            }
            $("body").append($form);
            $form.submit();
            //过河拆桥，提交完成后remove掉
            $form.remove();
        });
    },
    //附件回显编辑
    showDndEditAttachments:function(applyId){
        let $form = $("form[applyId='"+applyId+"']");
        let $list = $form.find(".attachments-list");
        $list.parent().css("display","block");

        $.post("/attachment/getAttachmentByApplyId",{applyId:applyId},function (data) {
            let attachments = data.fileUrl;
            for (let i = 0; i < attachments.length; i++) {
                let attachment = attachments[i];
                $list.append("<div><p class='attachment-text-p'>" + attachment + "</p><i data-url='" + attachment + "' class=\"attachment-remove\">X</i></div>");
            }
        });
    },
    //附件回显下载
    showDndDownloadAttachments:function(applyId){
        let $form = $("form[applyId='"+applyId+"']");
        $form.find(".button-div").css("display","none");
        let $list = $form.find(".attachments-list");
        $list.parent().css("display","block");
        $.post("/attachment/getAttachmentByApplyId",{applyId:applyId},function (data) {
            let attachments = data.fileUrl;
            for (let i = 0; i < attachments.length; i++) {
                let attachment = attachments[i];
                $list.append("<div><p class='attachment-text-p'>" + attachment + "</p><i data-url='" + attachment + "' class=\"attachment-download\">↓</i></div>");
            }
        });
    }
};
//事件监听
Attachment.attachmentListener();