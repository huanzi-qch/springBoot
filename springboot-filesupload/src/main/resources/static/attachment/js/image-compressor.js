/*
    js-image-compressor 是一个实现轻量级图片压缩的 javascript 库
    https://gitee.com/vivi0119/js-image-compressor

    示例：
    let file = $("input[name='attachment']")[0].files[0];
    new ImageCompressor({
      file: file,
      quality: 0.6,//自定义配置压缩比
      mimeType: 'image/jpeg',//输出图片类型
      maxWidth: 2000,//最大宽
      maxHeight: 2000,//最大高
      width: 1000,//宽
      height: 1000,//高
      minWidth: 500,//最小宽
      minHeight: 500,//最大高
      convertSize: 2048000,//png转jpeg阈值
      loose: true,//是否宽松模式，控制当压缩的图片 size 大于源图片，输出源图片，否则输出压缩后图片，默认是 true

      // 压缩前回调
      beforeCompress: function (result) {
        console.log('压缩之前图片尺寸大小: ', result.size);
        console.log('mime 类型: ', result.type);
      },

      // 压缩成功回调
      success: function (result) {
        console.log('压缩之后图片尺寸大小: ', result.size);
        console.log('mime 类型: ', result.type);
        console.log('实际压缩率： ', ((file.size - result.size) / file.size * 100).toFixed(2) + '%');

        //test页面追加压缩后的图片效果
        ImageCompressor.file2DataUrl(result, function (url) {
            let img = document.createElement("img");
            img.src = url;

            document.body.appendChild(img);
        })
      },

      // 发生错误
      error: function (msg) {
        console.error(msg);
      }
    });
 */
(function webpackUniversalModuleDefinition(root, factory) {
    if(typeof exports === 'object' && typeof module === 'object')
        module.exports = factory();
    else if(typeof define === 'function' && define.amd)
        define([], factory);
    else if(typeof exports === 'object')
        exports["ImageCompressor"] = factory();
    else
        root["ImageCompressor"] = factory();
})(window, function() {
    return /******/ (function(modules) { // webpackBootstrap
        /******/ 	// The module cache
        /******/ 	var installedModules = {};
        /******/
        /******/ 	// The require function
        /******/ 	function __webpack_require__(moduleId) {
            /******/
            /******/ 		// Check if module is in cache
            /******/ 		if(installedModules[moduleId]) {
                /******/ 			return installedModules[moduleId].exports;
                /******/ 		}
            /******/ 		// Create a new module (and put it into the cache)
            /******/ 		var module = installedModules[moduleId] = {
                /******/ 			i: moduleId,
                /******/ 			l: false,
                /******/ 			exports: {}
                /******/ 		};
            /******/
            /******/ 		// Execute the module function
            /******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
            /******/
            /******/ 		// Flag the module as loaded
            /******/ 		module.l = true;
            /******/
            /******/ 		// Return the exports of the module
            /******/ 		return module.exports;
            /******/ 	}
        /******/
        /******/
        /******/ 	// expose the modules object (__webpack_modules__)
        /******/ 	__webpack_require__.m = modules;
        /******/
        /******/ 	// expose the module cache
        /******/ 	__webpack_require__.c = installedModules;
        /******/
        /******/ 	// define getter function for harmony exports
        /******/ 	__webpack_require__.d = function(exports, name, getter) {
            /******/ 		if(!__webpack_require__.o(exports, name)) {
                /******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
                /******/ 		}
            /******/ 	};
        /******/
        /******/ 	// define __esModule on exports
        /******/ 	__webpack_require__.r = function(exports) {
            /******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
                /******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
                /******/ 		}
            /******/ 		Object.defineProperty(exports, '__esModule', { value: true });
            /******/ 	};
        /******/
        /******/ 	// create a fake namespace object
        /******/ 	// mode & 1: value is a module id, require it
        /******/ 	// mode & 2: merge all properties of value into the ns
        /******/ 	// mode & 4: return value when already ns object
        /******/ 	// mode & 8|1: behave like require
        /******/ 	__webpack_require__.t = function(value, mode) {
            /******/ 		if(mode & 1) value = __webpack_require__(value);
            /******/ 		if(mode & 8) return value;
            /******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
            /******/ 		var ns = Object.create(null);
            /******/ 		__webpack_require__.r(ns);
            /******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
            /******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
            /******/ 		return ns;
            /******/ 	};
        /******/
        /******/ 	// getDefaultExport function for compatibility with non-harmony modules
        /******/ 	__webpack_require__.n = function(module) {
            /******/ 		var getter = module && module.__esModule ?
                /******/ 			function getDefault() { return module['default']; } :
                /******/ 			function getModuleExports() { return module; };
            /******/ 		__webpack_require__.d(getter, 'a', getter);
            /******/ 		return getter;
            /******/ 	};
        /******/
        /******/ 	// Object.prototype.hasOwnProperty.call
        /******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
        /******/
        /******/ 	// __webpack_public_path__
        /******/ 	__webpack_require__.p = "";
        /******/
        /******/
        /******/ 	// Load entry module and return exports
        /******/ 	return __webpack_require__(__webpack_require__.s = 0);
        /******/ })
        /************************************************************************/
        /******/ ([
            /* 0 */
            /***/ (function(module, __webpack_exports__, __webpack_require__) {

                "use strict";
                __webpack_require__.r(__webpack_exports__);
                var WIN = window;
                var REGEXP_IMAGE_TYPE = /^image\//;
                var REGEXP_EXTENSION = /\.\w+$/;
                var util = {};
                var defaultOptions = {
                    file: null,
                    quality: 0.8,
                    convertSize: 2048000,
                    loose: true
                };

                /**
                 * 判断是否为函数
                 * @param {Any} value 任意值
                 * @returns {Boolean} 判断结果
                 */
                var isFunc = function (value) {
                    return typeof value === 'function';
                };

                /**
                 * 判断是否为图片类型
                 * @param {String} value 类型字符串
                 * @returns {Boolean} 判断结果
                 */
                var isImageType = function (value) {
                    return REGEXP_IMAGE_TYPE.test(value);
                };

                /**
                 * 图片类型转化为文件拓展名
                 * @param {String} value
                 */
                var imageTypeToExtension = function (value) {
                    var extension = isImageType(value) ? value.substr(6) : '';
                    if (extension === 'jpeg') {
                        extension = 'jpg';
                    }
                    return '.' + extension;
                }

                /**
                 * 图片压缩构造函数
                 * @param {Object} options 相关参数
                 */
                function ImageCompressor(options) {
                    options = Object.assign({}, defaultOptions, options);
                    this.options = options;
                    this.file = options.file;
                    this.image = null;
                    this.init();
                }

                var _proto = ImageCompressor.prototype;
// WIN.ImageCompressor = ImageCompressor;
                /* harmony default export */ __webpack_exports__["default"] = (ImageCompressor);

                /**
                 * 初始化
                 */
                _proto.init = function () {
                    var _this = this;
                    var file = this.file;
                    var options = this.options;

                    if (!file || !isImageType(file.type)) {
                        _this.error('请上传图片文件!');
                        return;
                    }

                    if (!isImageType(options.mimeType)) {
                        options.mimeType = file.type;
                    }

                    util.file2Image(file, function (img) {

                        if (isFunc(_this.beforeCompress)) {
                            _this.image = img;
                            file.width = img.naturalWidth;
                            file.height = img.naturalHeight;
                            _this.beforeCompress(file);
                        }

                        var edge = _this.getExpectedEdge();

                        var canvas = util.image2Canvas(img, edge.width, edge.height, _this.beforeDraw.bind(_this), _this.afterDraw.bind(_this));

                        util.canvas2Blob(canvas, function (blob) {
                            blob.width = canvas.width;
                            blob.height = canvas.height;
                            _this.success(blob);
                        }, options.quality, options.mimeType)
                    }, _this.error)
                }

                /**
                 * 压缩之前，读取图片之后钩子函数
                 */
                _proto.beforeCompress = function () {
                    if (isFunc(this.options.beforeCompress)) {
                        this.options.beforeCompress(this.file);
                    }
                }

                /**
                 * 获取用户想要输出的边（宽高）
                 */
                _proto.getExpectedEdge = function () {
                    var image = this.image;
                    var options = this.options;
                    var naturalWidth = image.naturalWidth;
                    var naturalHeight = image.naturalHeight;
                    var aspectRatio = naturalWidth / naturalHeight;
                    var maxWidth = Math.max(options.maxWidth, 0) || Infinity;
                    var maxHeight = Math.max(options.maxHeight, 0) || Infinity;
                    var minWidth = Math.max(options.minWidth, 0) || 0;
                    var minHeight = Math.max(options.minHeight, 0) || 0;
                    var width = Math.max(options.width, 0) || naturalWidth;
                    var height = Math.max(options.height, 0) || naturalHeight;

                    if (maxWidth < Infinity && maxHeight < Infinity) {
                        if (maxHeight * aspectRatio > maxWidth) {
                            maxHeight = maxWidth / aspectRatio;
                        } else {
                            maxWidth = maxHeight * aspectRatio;
                        }
                    } else if (maxWidth < Infinity) {
                        maxHeight = maxWidth / aspectRatio;
                    } else if (maxHeight < Infinity) {
                        maxWidth = maxHeight * aspectRatio;
                    }

                    if (minWidth > 0 && minHeight > 0) {
                        if (minHeight * aspectRatio > minWidth) {
                            minHeight = minWidth / aspectRatio;
                        } else {
                            minWidth = minHeight * aspectRatio;
                        }
                    } else if (minWidth > 0) {
                        minHeight = minWidth / aspectRatio;
                    } else if (minHeight > 0) {
                        minWidth = minHeight * aspectRatio;
                    }

                    if (height * aspectRatio > width) {
                        height = width / aspectRatio;
                    } else {
                        width = height * aspectRatio;
                    }

                    width = Math.floor(Math.min(Math.max(width, minWidth), maxWidth));
                    height = Math.floor(Math.min(Math.max(height, minHeight), maxHeight));

                    return {
                        width: width,
                        height: height
                    }
                }

                /**
                 * 画布上绘制图片前的一些操作：设置画布一些样式，支持用户自定义
                 * @param {CanvasRenderingContext2D} ctx Canvas 对象的上下文
                 * @param {HTMLCanvasElement} canvas Canvas 对象
                 */
                _proto.beforeDraw = function (ctx, canvas) {
                    var file = this.file;
                    var options = this.options;
                    var fillStyle = 'transparent';

                    // `png` 格式图片大小超过 `convertSize`, 转化成 `jpeg` 格式
                    if (file.size > options.convertSize && options.mimeType === 'image/png') {
                        fillStyle = '#fff';
                        options.mimeType = 'image/jpeg';
                    }

                    // 覆盖默认的黑色填充色
                    ctx.fillStyle = fillStyle;
                    ctx.fillRect(0, 0, canvas.width, canvas.height);

                    // 用户自定义画布样式
                    if (isFunc(options.beforeDraw)) {
                        options.beforeDraw.call(this, ctx, canvas);
                    }
                }

                /**
                 * 画布上绘制图片后的一些操作：支持用户自定义
                 * @param {CanvasRenderingContext2D} ctx Canvas 对象的上下文
                 * @param {HTMLCanvasElement} canvas Canvas 对象
                 */
                _proto.afterDraw = function (ctx, canvas) {
                    var options = this.options;
                    // 用户自定义画布样式
                    if (isFunc(options.afterDraw)) {
                        options.afterDraw.call(this, ctx, canvas);
                    }
                }

                /**
                 * 错误触发函数
                 * @param {String} msg 错误消息
                 */
                _proto.error = function (msg) {
                    var options = this.options;
                    if (isFunc(options.error)) {
                        options.error.call(this, msg);
                    } else {
                        throw new Error(msg);
                    }
                }

                /**
                 * 成功触发函数
                 * @param {File|Blob} result `Blob` 对象
                 */
                _proto.success = function (result) {
                    var options = this.options;
                    var file = this.file;
                    var image = this.image;
                    var edge = this.getExpectedEdge();
                    var naturalHeight = image.naturalHeight;
                    var naturalWidth = image.naturalWidth;

                    if (result) {
                        // 在非宽松模式下，用户期待的输出宽高没有大于源图片的宽高情况下，输出文件大小大于源文件，返回源文件
                        if (!options.loose && result.size > file.size && !(
                            edge.width > naturalWidth
                            || edge.height > naturalHeight
                        )) {
                            result = file;
                        } else {
                            const date = new Date();

                            result.lastModified = date.getTime();
                            result.lastModifiedDate = date;
                            result.name = file.name;

                            // 文件 `name` 属性中的后缀转化成实际后缀
                            if (result.name && result.type !== file.type) {
                                result.name = result.name.replace(
                                    REGEXP_EXTENSION,
                                    imageTypeToExtension(result.type)
                                );
                            }
                        }
                    } else {
                        // 在某些情况下压缩后文件为 `null`，返回源文件
                        result = file;
                    }

                    if (isFunc(options.success)) {
                        options.success.call(this, result);
                    }
                }

                /**
                 * 文件转化成 `data URL` 字符串
                 * @param {File} file 文件对象
                 * @param {Function} callback 成功回调函数
                 * @param {Function} error 取消回调函数
                 */
                util.file2DataUrl = function (file, callback, error) {
                    var reader = new FileReader();
                    reader.onload = function () {
                        callback(reader.result);
                    };
                    reader.onerror = function () {
                        if (isFunc(error)) {
                            error('读取文件失败！');
                        }
                    };
                    reader.readAsDataURL(file);
                }

                /**
                 * 文件转化成 `Image` 对象
                 * @param {File} file 文件对象
                 * @param {Function} callback 成功回调函数
                 * @param {Function} error 错误回调函数
                 */
                util.file2Image = function (file, callback, error) {
                    var image = new Image();
                    var URL = WIN.URL || WIN.webkitURL;

                    if (WIN.navigator && /(?:iPad|iPhone|iPod).*?AppleWebKit/i.test(WIN.navigator.userAgent)) {
                        // 修复IOS上webkit内核浏览器抛出错误 `The operation is insecure` 问题
                        image.crossOrigin = 'anonymous';
                    }

                    image.alt = file.name;
                    image.onerror = function () {
                        if (isFunc(error)) {
                            error('图片加载错误！');
                        }
                    }

                    if (URL) {
                        var url = URL.createObjectURL(file);
                        image.onload = function () {
                            callback(image);
                            URL.revokeObjectURL(url);
                        };
                        image.src = url;
                    } else {
                        this.file2DataUrl(file, function (dataUrl) {
                            image.onload = function () {
                                callback(image);
                            }
                            image.src = dataUrl;
                        }, error);
                    }
                }

                /**
                 * `url` 转化成 `Image` 对象
                 * @param {File} url `url`
                 * @param {Function} callback 成功回调函数
                 * @param {Function} error 失败回调函数
                 */
                util.url2Image = function (url, callback, error) {
                    var image = new Image();
                    image.src = url;
                    image.onload = function () {
                        callback(image);
                    };
                    image.onerror = function () {
                        if (isFunc(error)) {
                            error('图片加载错误！');
                        }
                    }
                }

                /**
                 * `Image` 转化成 `Canvas` 对象
                 * @param {File} file `Image` 对象
                 * @param {Number} destWidth 目标宽度
                 * @param {Number} destHeight 目标高度
                 * @param {Function} beforeDraw 在图片绘画之前的回调函数
                 * @param {Function} beforeDraw 在图片绘画之后的回调函数
                 * @return {HTMLCanvasElement} `canvas` 对象
                 */
                util.image2Canvas = function (image, destWidth, destHeight, beforeDraw, afterDraw) {
                    var canvas = document.createElement('canvas');
                    var ctx = canvas.getContext('2d');
                    canvas.width = destWidth || image.naturalWidth;
                    canvas.height = destHeight || image.naturalHeight;
                    if (isFunc(beforeDraw)) {
                        beforeDraw(ctx, canvas);
                    }
                    ctx.save();
                    ctx.drawImage(image, 0, 0, canvas.width, canvas.height);
                    ctx.restore();
                    if (isFunc(afterDraw)) {
                        afterDraw(ctx, canvas);
                    }
                    return canvas;
                }

                /**
                 * `Canvas` 转化成 `data URL` 对象
                 * @param {File} file  `Canvas` 对象
                 * @param {Float} quality 输出质量比例
                 * @return {String} `data URL` 字符串
                 */
                util.canvas2DataUrl = function (canvas, quality, type) {
                    return canvas.toDataURL(type || 'image/jpeg', quality);
                }

                /**
                 * `data URL` 转化成 `Image` 对象
                 * @param {File} dataUrl `data URL` 字符串
                 * @param {Function} callback 成功回调函数
                 * @param {Function} error 失败回调函数
                 */
                util.dataUrl2Image = function (dataUrl, callback, error) {
                    var image = new Image();
                    image.onload = function () {
                        callback(image);
                    };
                    image.error = function () {
                        if (isFunc(error)) {
                            error('图片加载错误！');
                        }
                    }
                    image.src = dataUrl;
                }

                /**
                 * `data URL` 转化成 `Blob` 对象
                 * @param {File} dataUrl `data URL` 字符串
                 * @param {String} type `mime`
                 * @return {Blob} `Blob` 对象
                 */
                util.dataUrl2Blob = function (dataUrl, type) {
                    var data = dataUrl.split(',')[1];
                    var mimePattern = /^data:(.*?)(;base64)?,/;
                    var mime = dataUrl.match(mimePattern)[1];
                    var binStr = atob(data);
                    var len = data.length;
                    var arr = new Uint8Array(len);

                    for (var i = 0; i < len; i++) {
                        arr[i] = binStr.charCodeAt(i);
                    }
                    return new Blob([arr], { type: type || mime });
                }

                /**
                 * `Blob` 对象转化成 `data URL`
                 * @param {Blob} blob `Blob` 对象
                 * @param {Function} callback 成功回调函数
                 * @param {Function} error 失败回调函数
                 */
                util.blob2DataUrl = function (blob, callback, error) {
                    this.file2DataUrl(blob, callback, error);
                }

                /**
                 * `Blob`对象 转化成 `Image` 对象
                 * @param {Blob} blob `Blob` 对象
                 * @param {Function} callback 成功回调函数
                 * @param {Function} callback 失败回调函数
                 */
                util.blob2Image = function (blob, callback, error) {
                    this.file2Image(blob, callback, error);
                }

                /**
                 * `Canvas` 对象转化成 `Blob` 对象
                 * @param {HTMLCanvasElement} canvas `Canvas` 对象
                 * @param {Function} callback 回调函数
                 * @param {Float} quality 输出质量比例
                 * @param {String} type `mime`
                 */
                util.canvas2Blob = function (canvas, callback, quality, type) {
                    var _this = this;
                    if (!HTMLCanvasElement.prototype.toBlob) {
                        Object.defineProperty(HTMLCanvasElement.prototype, 'toBlob', {
                            value: function (callback, type, quality) {
                                let dataUrl = this.toDataURL(type, quality);
                                callback(_this.dataUrl2Blob(dataUrl));
                            }
                        });
                    }
                    canvas.toBlob(function (blob) {
                        callback(blob);
                    }, type || 'image/jpeg', quality || 0.8);
                }

                /**
                 * 文件上传
                 * @param {String} url 上传路径
                 * @param {File} file 文件对象
                 * @param {Function} callback 回调函数
                 */
                util.upload = function (url, file, callback) {
                    var xhr = new XMLHttpRequest();
                    var fd = new FormData();
                    fd.append('file', file);
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState === 4 && xhr.status === 200) {
                            // 上传成功
                            callback && callback(xhr.responseText);
                        } else {
                            throw new Error(xhr);
                        }
                    }
                    xhr.open('POST', url, true);
                    xhr.send(fd);
                }

                for (var key in util) {
                    if (util.hasOwnProperty(key)) {
                        ImageCompressor[key] = util[key];
                    }
                }


                /***/ })
            /******/ ])["default"];
});