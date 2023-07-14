package com.hq.jizhang.cons

/*
 * @创建者     肖天长
 * @创建时间   2020/5/7  16:59
 * @描述      常量类
 *
 */
object CommonConstant {

    const val HAHA = "haha"

    const val yyyy_MM_dd: String = "yyyy-MM-dd"  //yyyy-MM-dd
    const val yyyyMMdd: String = "yyyyMMdd"  //yyyyMMdd

    //列表加载页数
    const val PAGESIZE: Int = 50

    //验证码长度
    const val CODELENGTH: Int = 6

    //方向位置
    const val ORIENTATION_LEFT: Int = 1
    const val ORIENTATION_TOP: Int = 2
    const val ORIENTATION_RIGHT: Int = 3
    const val ORIENTATION_BOTTOM: Int = 4


    /**
     * 用户信息sp文件的key
     */
    object SpKey {
        const val GUO_MI_DEVICE = "guoMiDevice"   //国密下载的设备
        const val LOGINACTIVITY_RESPBEAN = "login_respBean"   //登录返回数据bean
        const val HAVE_FINGERPRINT_LOGIN = "hasFingerprintLogin"   //当前用户是否有开过指纹识别
        const val PRIVACY_POLICY = "agree"   //登录隐私政策是否同意
        const val BACKSTAGE_TIME = "time"   //进入后台的时间
        const val APK_PATH = "apkPath"   //apk下载路径
        const val IS_LOAD_OFFLINE = "isLoadOffline"
        const val OFFLINE_RES_VISION = "offlineResVision"
        const val OFFLINE_RES_PATH = "offlineResPath"
        const val SPLASH_LOCAL_PATH = "splashLocalPath" //本地启动页
        const val SPLASH_SERVICE_PATH = "splashServicePath" //启动页服务器路径
        const val HOME_MENU = "homeMenu" //首页菜单项
        const val OPEN_VOICE = "openVoice" //是否开声音
        const val OPEN_VIBRATOR = "openVibrator"  //是否开震动
    }

    object WebView {
        const val WEB_URL=""
        const val WEB_TITLE=""
        var isLoadOffline = false
        var offlineResPath = "hybridapp/"
    }

    object MainActivity {
        const val TYPE_INCOME="income"
        const val TYPE_DISBURSE="disburse"

        const val UPDATE_DATA="updateData"

    }
}