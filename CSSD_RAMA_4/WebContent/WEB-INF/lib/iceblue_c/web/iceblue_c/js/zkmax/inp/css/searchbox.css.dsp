<%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %><%@ taglib uri="http://www.zkoss.org/dsp/zk/core" prefix="z" %><%@ taglib uri="http://www.zkoss.org/dsp/web/theme" prefix="t" %>.z-searchbox{display:inline-block;position:relative;height:24px;width:180px;border:1px solid #D9D9D9;border-radius:4px;color:rgba(0,0,0,0.9);background:#FFFFFF;cursor:pointer}.z-searchbox:hover{border-color:#A8A8A8}.z-searchbox-focus,.z-searchbox-open{border-color:#0093F9}.z-searchbox:active{border-color:#0064ED;background:#0093F9;color:#FFFFFF}.z-searchbox:active .z-searchbox-placeholder{color:#FFFFFF}.z-searchbox:active .z-searchbox-icon{color:#FFFFFF}.z-searchbox[disabled]{color:rgba(0,0,0,0.34);background:#F2F2F2;cursor:default;border-color:#D9D9D9}.z-searchbox[disabled]-icon{color:rgba(0,0,0,0.34)}.z-searchbox[disabled] .z-searchbox-icon{color:rgba(0,0,0,0.34)}.z-searchbox:before{content:'';display:inline-block;vertical-align:middle;height:100%}.z-searchbox-label{user-select:none;display:inline-block;width:100%;height:100%;line-height:calc(24px - 2px);padding:0 5px;padding-right:36px;vertical-align:middle;font-family:"Helvetica Neue",Helvetica,Arial,sans-serif;font-size:12px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis}.z-searchbox-placeholder{user-select:none;display:none;width:100%;height:100%;line-height:calc(24px - 2px);padding:0 5px;padding-right:18px;vertical-align:middle;font-family:"Helvetica Neue",Helvetica,Arial,sans-serif;font-size:12px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;color:rgba(0,0,0,0.34)}.z-searchbox-icon{color:rgba(0,0,0,0.57);font-size:18px;position:absolute;right:0;top:50%;-ms-transform:translateY(-50%);transform:translateY(-50%)}.z-searchbox-clear{right:18px}.z-searchbox-popup{position:absolute;z-index:1000;border:1px solid #0093F9;border-radius:4px;background:#FFFFFF;padding:4px;font-family:"Helvetica Neue",Helvetica,Arial,sans-serif;font-size:12px;color:rgba(0,0,0,0.9)}.z-searchbox-shadow{box-shadow:0 3px 6px rgba(0,0,0,0.3)}.z-searchbox .z-searchbox-popup{display:none}.z-searchbox-search{height:24px;width:100%;padding:4px 5px;border:1px solid #D9D9D9;border-radius:4px;background:#FFFFFF;margin-bottom:4px}.z-searchbox-search::placeholder{color:rgba(0,0,0,0.34);opacity:1}.z-searchbox-search::-webkit-input-placeholder{color:rgba(0,0,0,0.34);opacity:1}.z-searchbox-search::-moz-placeholder{color:rgba(0,0,0,0.34);opacity:1}.z-searchbox-search:-ms-input-placeholder{color:rgba(0,0,0,0.34);opacity:1}.z-searchbox-search:hover{border-color:#0093F9}.z-searchbox-search:focus{border-color:#0093F9}.z-searchbox-cave{margin:0;padding:0;max-height:350px;overflow:auto}.z-searchbox-item{height:24px;white-space:nowrap;list-style:none;display:block;padding:4px 5px;cursor:pointer;border-radius:4px;vertical-align:middle;-moz-user-select:none;-webkit-user-select:none;-ms-user-select:none;user-select:none}.z-searchbox-item:before{content:'';display:inline-block;vertical-align:middle;height:100%}.z-searchbox-item.z-searchbox-selected{color:#0093F9}.z-searchbox-item:hover{background:#e0f2ff;color:rgba(0,0,0,0.9)}.z-searchbox-item.z-searchbox-active{background:#7ac8ff;color:rgba(0,0,0,0.9)}.z-searchbox-item.z-searchbox-selected.z-searchbox-active,.z-searchbox-item.z-searchbox-selected:hover{color:#0093F9}.z-searchbox-item-check{display:inline-block;vertical-align:middle;border:1px solid #D9D9D9;background:#FFFFFF;border-radius:4px;width:16px;height:16px;margin-right:5px}.z-searchbox-item.z-searchbox-selected>.z-searchbox-item-check{color:#FFFFFF;background:#0093F9;display:inline-block;font-family:ZK85Icons, FontAwesome;font-style:normal;font-weight:normal;font-size:inherit;line-height:1;-webkit-font-smoothing:antialiased;-moz-osx-font-smoothing:grayscale;text-rendering:auto;font-size:16px}.z-searchbox-item.z-searchbox-selected>.z-searchbox-item-check::before{content:"\f00c"}