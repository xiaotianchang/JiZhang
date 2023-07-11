package com.hq.jizhang.base

import com.hq.jizhang.util.AppUtil


/**
 * Created by zhout on 2017/8/1.
 */

data class BaseBeanReq(var body : Any?) {
    var channel = "APP-Android"
    var loginIp = AppUtil.getIpAddress(BaseApplication.mAppContext)
    var serviceCode = System.currentTimeMillis()
    var requestId = "APP-" + System.currentTimeMillis()
    var bindDevice = AppUtil.getSystemModel()
}
