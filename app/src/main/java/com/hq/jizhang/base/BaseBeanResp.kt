package com.hq.jizhang.base

import android.text.TextUtils
import java.io.Serializable

/**
 * @创建者 肖天长
 * @创建时间 2018/7/4 0004  18:20
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 2018/7/4 0004$
 * @更新描述 ${TODO}
 */

class BaseBeanResp<T>(
    var code: String = "",
    var channel: String = "",
    var returnMsg: String = "",
    var resultcode: String = "",//保安编码返回码  000000代表成功
    var message: String = "",
    var traceId: String = "",
    var loginIp: String = "",
    var data: T? = null
) : Serializable {
    val isSuccess: Boolean
        get() {
            if (TextUtils.isEmpty(code))
                return false
            return code.contains("00000")
        }
    val isSuccessSecurityCode: Boolean
        get() {
            if (TextUtils.isEmpty(resultcode))
                return false
            return resultcode == "000000"
        }

    override fun toString(): String {
        return "BaseBeanResp(code='$code', message='$message', traceId='$traceId', data=$data, loginIp=$loginIp)"
    }


}
