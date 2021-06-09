<%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %><%@ taglib uri="http://www.zkoss.org/dsp/zk/core" prefix="z" %><%@ taglib uri="http://www.zkoss.org/dsp/web/theme" prefix="t" %>.z-cropper{display:inline-block;position:relative}.z-cropper-area{position:absolute;cursor:move;outline:1px solid white}.z-cropper .jcrop-dragbar{display:block;width:6px;height:6px;position:absolute}.z-cropper .jcrop-dragbar.ord-n,.z-cropper .jcrop-dragbar.ord-s{width:100%}.z-cropper .jcrop-dragbar.ord-e,.z-cropper .jcrop-dragbar.ord-w{height:100%}.z-cropper .jcrop-dragbar.ord-n{-webkit-transform:translateY(-50%);-moz-transform:translateY(-50%);-o-transform:translateY(-50%);-ms-transform:translateY(-50%);transform:translateY(-50%)}.z-cropper .jcrop-dragbar.ord-e{-webkit-transform:translateX(50%);-moz-transform:translateX(50%);-o-transform:translateX(50%);-ms-transform:translateX(50%);transform:translateX(50%)}.z-cropper .jcrop-dragbar.ord-w{-webkit-transform:translateX(-50%);-moz-transform:translateX(-50%);-o-transform:translateX(-50%);-ms-transform:translateX(-50%);transform:translateX(-50%)}.z-cropper .jcrop-dragbar.ord-s{-webkit-transform:translateY(50%);-moz-transform:translateY(50%);-o-transform:translateY(50%);-ms-transform:translateY(50%);transform:translateY(50%)}.z-cropper-handle{display:block;background:white;width:6px;height:6px;position:absolute}.z-cropper-handle.ord-n{left:50%;-webkit-transform:translate(-50%, -50%);-moz-transform:translate(-50%, -50%);-o-transform:translate(-50%, -50%);-ms-transform:translate(-50%, -50%);transform:translate(-50%, -50%)}.z-cropper-handle.ord-e{top:50%;-webkit-transform:translate(50%, -50%);-moz-transform:translate(50%, -50%);-o-transform:translate(50%, -50%);-ms-transform:translate(50%, -50%);transform:translate(50%, -50%)}.z-cropper-handle.ord-w{top:50%;-webkit-transform:translate(-50%, -50%);-moz-transform:translate(-50%, -50%);-o-transform:translate(-50%, -50%);-ms-transform:translate(-50%, -50%);transform:translate(-50%, -50%)}.z-cropper-handle.ord-s{left:50%;-webkit-transform:translate(-50%, 50%);-moz-transform:translate(-50%, 50%);-o-transform:translate(-50%, 50%);-ms-transform:translate(-50%, 50%);transform:translate(-50%, 50%)}.z-cropper .ord-n{cursor:row-resize;top:0}.z-cropper .ord-e{cursor:col-resize;right:0}.z-cropper .ord-w{cursor:col-resize;left:0}.z-cropper .ord-s{cursor:row-resize;bottom:0}.z-cropper .ord-ne{cursor:nesw-resize;top:0;right:0;-webkit-transform:translate(50%, -50%);-moz-transform:translate(50%, -50%);-o-transform:translate(50%, -50%);-ms-transform:translate(50%, -50%);transform:translate(50%, -50%)}.z-cropper .ord-nw{cursor:nwse-resize;top:0;left:0;-webkit-transform:translate(-50%, -50%);-moz-transform:translate(-50%, -50%);-o-transform:translate(-50%, -50%);-ms-transform:translate(-50%, -50%);transform:translate(-50%, -50%)}.z-cropper .ord-se{cursor:nwse-resize;bottom:0;right:0;-webkit-transform:translate(50%, 50%);-moz-transform:translate(50%, 50%);-o-transform:translate(50%, 50%);-ms-transform:translate(50%, 50%);transform:translate(50%, 50%)}.z-cropper .ord-sw{cursor:nesw-resize;bottom:0;left:0;-webkit-transform:translate(-50%, 50%);-moz-transform:translate(-50%, 50%);-o-transform:translate(-50%, 50%);-ms-transform:translate(-50%, 50%);transform:translate(-50%, 50%)}.z-cropper-holder{direction:ltr;cursor:crosshair;text-align:left;-ms-touch-action:none}.z-cropper-holder img{max-width:none}.z-cropper-vline,.z-cropper-hline{font-size:0;position:absolute}.z-cropper-vline{height:100%;width:1px !important}.z-cropper-vline.right{right:0}.z-cropper-hline{height:1px !important;width:100%}.z-cropper-hline.bottom{bottom:0}.z-cropper-tracker{width:100%;height:100%;-webkit-tap-highlight-color:transparent;-webkit-touch-callout:none;-webkit-user-select:none}.z-cropper-toolbar{position:absolute;overflow:hidden;display:inline-block;margin:8px 4px;-webkit-touch-callout:none;-webkit-user-select:none;-khtml-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none}.z-cropper-toolbar>ul{font-family:"Helvetica Neue",Helvetica,Arial,sans-serif;background:#0093F9;-webkit-border-radius:4px;-moz-border-radius:4px;-o-border-radius:4px;-ms-border-radius:4px;border-radius:4px;box-shadow:0 2px 4px 0 rgba(0,0,0,0.16);list-style-type:none;margin:0;padding:0;overflow:hidden}.z-cropper-toolbar>ul>li{float:left;padding:5px 11px}.z-cropper-toolbar>ul>li:hover{background-color:#7ac8ff}.z-cropper-toolbar>ul>li:active{background-color:#0064ED}.z-cropper-toolbar>ul>li:first-child{border-right:1px solid #0064ED}.z-cropper-toolbar>ul>li>a{height:14px;text-decoration:none;display:block;text-align:center;color:#FFFFFF;font-size:12px}