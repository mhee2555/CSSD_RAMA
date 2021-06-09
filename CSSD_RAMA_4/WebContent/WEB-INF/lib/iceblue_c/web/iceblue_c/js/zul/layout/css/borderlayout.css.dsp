<%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %><%@ taglib uri="http://www.zkoss.org/dsp/zk/core" prefix="z" %><%@ taglib uri="http://www.zkoss.org/dsp/web/theme" prefix="t" %>@media print{.safari .z-borderlayout{position:static}}@media screen{.z-borderlayout{position:relative}}.z-borderlayout{width:100%;height:100%;border:0;background:#FFFFFF;overflow:hidden}.z-borderlayout-icon{font-size:12px;color:rgba(0,0,0,0.57);display:block;width:24px;height:24px;line-height:24px;text-align:center;position:absolute;top:4px;right:3px;overflow:hidden;cursor:pointer}.z-borderlayout-icon:hover{opacity:1;filter:alpha(opacity=100);}.z-north,.z-south,.z-west,.z-center,.z-east{border:1px solid #D9D9D9;background:#FFFFFF;position:absolute;overflow:hidden}.z-north-noborder,.z-south-noborder,.z-west-noborder,.z-center-noborder,.z-east-noborder{border:0}.z-north-header,.z-south-header,.z-west-header,.z-center-header,.z-east-header{font-family:"Helvetica Neue",Helvetica,Arial,sans-serif;font-size:12px;font-weight:normal;font-style:normal;color:rgba(0,0,0,0.57);background:#FFFFFF;border-bottom:1px solid #D9D9D9;padding:8px 4px 9px;overflow:hidden;cursor:default;white-space:nowrap}.z-north-body,.z-south-body,.z-west-body,.z-center-body,.z-east-body{line-height:12px;padding:2px;color:rgba(0,0,0,0.9)}.z-north-collapsed,.z-south-collapsed,.z-west-collapsed,.z-center-collapsed,.z-east-collapsed{width:32px;height:32px;border:1px solid #D9D9D9;padding:4px;background:#F9FCFF;position:absolute;overflow:hidden;cursor:pointer}.z-north-collapsed:hover,.z-south-collapsed:hover,.z-west-collapsed:hover,.z-center-collapsed:hover,.z-east-collapsed:hover{background:#e0f2ff}.z-north-collapsed:hover .z-borderlayout-icon,.z-south-collapsed:hover .z-borderlayout-icon,.z-west-collapsed:hover .z-borderlayout-icon,.z-center-collapsed:hover .z-borderlayout-icon,.z-east-collapsed:hover .z-borderlayout-icon{color:rgba(0,0,0,0.57)}.z-north-collapsed .z-borderlayout-icon,.z-south-collapsed .z-borderlayout-icon,.z-west-collapsed .z-borderlayout-icon,.z-center-collapsed .z-borderlayout-icon,.z-east-collapsed .z-borderlayout-icon{color:rgba(0,0,0,0.57)}.z-north-slide>.z-north-collapsed,.z-north-slide>.z-south-collapsed,.z-north-slide>.z-west-collapsed,.z-north-slide>.z-center-collapsed,.z-north-slide>.z-east-collapsed,.z-south-slide>.z-north-collapsed,.z-south-slide>.z-south-collapsed,.z-south-slide>.z-west-collapsed,.z-south-slide>.z-center-collapsed,.z-south-slide>.z-east-collapsed,.z-west-slide>.z-north-collapsed,.z-west-slide>.z-south-collapsed,.z-west-slide>.z-west-collapsed,.z-west-slide>.z-center-collapsed,.z-west-slide>.z-east-collapsed,.z-center-slide>.z-north-collapsed,.z-center-slide>.z-south-collapsed,.z-center-slide>.z-west-collapsed,.z-center-slide>.z-center-collapsed,.z-center-slide>.z-east-collapsed,.z-east-slide>.z-north-collapsed,.z-east-slide>.z-south-collapsed,.z-east-slide>.z-west-collapsed,.z-east-slide>.z-center-collapsed,.z-east-slide>.z-east-collapsed{background:#FFFFFF}.z-north-slide>.z-north-collapsed:hover,.z-north-slide>.z-south-collapsed:hover,.z-north-slide>.z-west-collapsed:hover,.z-north-slide>.z-center-collapsed:hover,.z-north-slide>.z-east-collapsed:hover,.z-south-slide>.z-north-collapsed:hover,.z-south-slide>.z-south-collapsed:hover,.z-south-slide>.z-west-collapsed:hover,.z-south-slide>.z-center-collapsed:hover,.z-south-slide>.z-east-collapsed:hover,.z-west-slide>.z-north-collapsed:hover,.z-west-slide>.z-south-collapsed:hover,.z-west-slide>.z-west-collapsed:hover,.z-west-slide>.z-center-collapsed:hover,.z-west-slide>.z-east-collapsed:hover,.z-center-slide>.z-north-collapsed:hover,.z-center-slide>.z-south-collapsed:hover,.z-center-slide>.z-west-collapsed:hover,.z-center-slide>.z-center-collapsed:hover,.z-center-slide>.z-east-collapsed:hover,.z-east-slide>.z-north-collapsed:hover,.z-east-slide>.z-south-collapsed:hover,.z-east-slide>.z-west-collapsed:hover,.z-east-slide>.z-center-collapsed:hover,.z-east-slide>.z-east-collapsed:hover{background:#e0f2ff}.z-north-slide .z-north-header,.z-north-slide .z-south-header,.z-north-slide .z-west-header,.z-north-slide .z-center-header,.z-north-slide .z-east-header,.z-south-slide .z-north-header,.z-south-slide .z-south-header,.z-south-slide .z-west-header,.z-south-slide .z-center-header,.z-south-slide .z-east-header,.z-west-slide .z-north-header,.z-west-slide .z-south-header,.z-west-slide .z-west-header,.z-west-slide .z-center-header,.z-west-slide .z-east-header,.z-center-slide .z-north-header,.z-center-slide .z-south-header,.z-center-slide .z-west-header,.z-center-slide .z-center-header,.z-center-slide .z-east-header,.z-east-slide .z-north-header,.z-east-slide .z-south-header,.z-east-slide .z-west-header,.z-east-slide .z-center-header,.z-east-slide .z-east-header{border-bottom-width:0}.z-north-caption,.z-south-caption,.z-west-caption,.z-center-caption,.z-east-caption{height:24px}.z-north,.z-south,.z-center{width:100%}.z-west,.z-east{height:100%}.z-west,.z-west-collapsed,.z-west-splitter{z-index:12}.z-center{z-index:8}.z-east,.z-east-collapsed,.z-east-splitter{z-index:10}.z-north,.z-north-collapsed,.z-north-splitter{z-index:16}.z-south,.z-south-collapsed,.z-south-splitter{z-index:14}.z-east-splitter,.z-west-splitter,.z-north-splitter,.z-south-splitter{width:8px;height:8px;background-color:#F9FCFF;position:absolute;overflow:hidden;cursor:ew-resize}.z-east-splitter:hover,.z-west-splitter:hover,.z-north-splitter:hover,.z-south-splitter:hover{background-color:#e0f2ff}.z-east-splitter:hover .z-east-splitter-button,.z-west-splitter:hover .z-east-splitter-button,.z-north-splitter:hover .z-east-splitter-button,.z-south-splitter:hover .z-east-splitter-button,.z-east-splitter:hover .z-west-splitter-button,.z-west-splitter:hover .z-west-splitter-button,.z-north-splitter:hover .z-west-splitter-button,.z-south-splitter:hover .z-west-splitter-button,.z-east-splitter:hover .z-north-splitter-button,.z-west-splitter:hover .z-north-splitter-button,.z-north-splitter:hover .z-north-splitter-button,.z-south-splitter:hover .z-north-splitter-button,.z-east-splitter:hover .z-south-splitter-button,.z-west-splitter:hover .z-south-splitter-button,.z-north-splitter:hover .z-south-splitter-button,.z-south-splitter:hover .z-south-splitter-button{color:rgba(0,0,0,0.34)}.z-east-splitter-button,.z-west-splitter-button,.z-north-splitter-button,.z-south-splitter-button{color:rgba(0,0,0,0.34);display:inline-block;vertical-align:top;position:relative;cursor:pointer}.z-east-icon,.z-west-icon,.z-north-icon,.z-south-icon{font-size:12px;line-height:12px;position:absolute}.z-north-splitter,.z-south-splitter{border-left:1px solid #D9D9D9;border-right:1px solid #D9D9D9;cursor:ns-resize}.z-north-splitter-button-disabled .z-icon-caret-up,.z-south-splitter-button-disabled .z-icon-caret-down,.z-west-splitter-button-disabled .z-icon-caret-left,.z-east-splitter-button-disabled .z-icon-caret-right{display:none}.z-north-splitter-button-disabled,.z-south-splitter-button-disabled{cursor:ns-resize}.z-west-splitter-button-disabled,.z-east-splitter-button-disabled{cursor:ew-resize}.z-west-icon,.z-east-icon{top:8px;left:-3px}.z-west-icon.z-icon-ellipsis-v,.z-east-icon.z-icon-ellipsis-v{font-size:10px;top:-21px;left:3px;cursor:ew-resize;visibility:hidden}.z-north-icon,.z-south-icon{left:9px;top:-4px}.z-west-icon.z-icon-ellipsis-v~.z-west-icon.z-icon-ellipsis-v,.z-east-icon.z-icon-ellipsis-v~.z-east-icon.z-icon-ellipsis-v{top:39px}.z-north-icon.z-icon-ellipsis-h,.z-south-icon.z-icon-ellipsis-h{top:-2px;left:-20px;cursor:ns-resize;visibility:hidden}.z-north-icon.z-icon-ellipsis-h~.z-north-icon.z-icon-ellipsis-h,.z-south-icon.z-icon-ellipsis-h~.z-south-icon.z-icon-ellipsis-h{left:40px}.z-west-splitter-button,.z-east-splitter-button{width:8px;height:30px;border-width:1px 0}.z-north-splitter-button,.z-south-splitter-button{width:30px;height:8px;border-width:0 1px}.z-north-title,.z-south-title,.z-west-title,.z-east-title{font-family:"Helvetica Neue",Helvetica,Arial,sans-serif;font-size:12px;font-weight:normal;font-style:normal;color:rgba(0,0,0,0.57);text-overflow:ellipsis;white-space:nowrap;overflow:hidden;line-height:24px}.z-west-title,.z-east-title{-webkit-transform:rotate(90deg);-moz-transform:rotate(90deg);-o-transform:rotate(90deg);-ms-transform:rotate(90deg);transform:rotate(90deg);-webkit-transform-origin:left bottom;-moz-transform-origin:left bottom;-o-transform-origin:left bottom;-ms-transform-origin:left bottom;transform-origin:left bottom}.ie9 .z-north-splitter,.ie9 .z-south-splitter{cursor:row-resize}.ie9 .z-east-splitter,.ie9 .z-west-splitter{cursor:col-resize}.ie9 .z-north-icon.z-icon-ellipsis-h,.ie9 .z-south-icon.z-icon-ellipsis-h{cursor:row-resize}.ie9 .z-north-icon.z-icon-ellipsis-v,.ie9 .z-south-icon.z-icon-ellipsis-v{cursor:col-resize}.ie9 .z-north-splitter-button-disabled,.ie9 .z-south-splitter-button-disabled{cursor:row-resize}.ie9 .z-west-splitter-button-disabled,.ie9 .z-east-splitter-button-disabled{cursor:col-resize}