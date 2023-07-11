package com.hq.jizhang.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.*
import android.content.ClipboardManager
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.text.*
import android.text.style.AbsoluteSizeSpan
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.FileProvider
import com.hq.jizhang.cons.CommonConstant
import com.hq.jizhang.base.BaseApplication
import com.umeng.soexample.interfaces.AddSoftKeyBoardVisibleListener
import com.umeng.soexample.interfaces.OnKeyListener
import com.umeng.soexample.util.ToastUtil
import org.jetbrains.anko.runOnUiThread
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.RandomAccessFile
import java.math.BigDecimal
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern


/**
 * 获取context以及ui需要的resource
 */
class AppUtil {


    companion object {

        fun getScreenWidth(context : Context) : Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val metrics = DisplayMetrics()
            display.getMetrics(metrics)
            return metrics.widthPixels
        }

        fun getScreenHeight(context : Context) : Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val metrics = DisplayMetrics()
            display.getRealMetrics(metrics)
            return metrics.heightPixels
        }

        /**
         * 返回当前程序版本号
         */
        fun getAppVersionCode(context : Context) : String {
            var versioncode = 0
            try {
                val pm : PackageManager = context.packageManager
                val pi : PackageInfo = pm.getPackageInfo(context.packageName , 0)
                versioncode = pi.versionCode
            } catch (e : java.lang.Exception) {
                Log.e("VersionInfo" , "Exception" , e)
                return ""
            }
            return versioncode.toString()
        }

        /**
         * 返回当前程序版本名
         */
        fun getAppVersionName(context : Context) : String {
            var versionName : String = ""
            try {
                val pm : PackageManager = context.packageManager
                val pi : PackageInfo = pm.getPackageInfo(context.packageName , 0)
                versionName = pi.versionName
            } catch (e : java.lang.Exception) {
                Log.e("VersionInfo" , "Exception" , e)
            }
            return versionName
        }

        //判断是否有网
        fun getIsNetworkConnected(context : Context?) : Boolean {
            if (! isNetworkConnected(context)) {
                context?.runOnUiThread {
                    ToastUtil.showText("貌似网络断开了...")
                }
                return false
            }
            return true
        }

        private fun isNetworkConnected(context : Context?) : Boolean {
            if (context != null) {
                val mConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val mNetworkInfo = mConnectivityManager.activeNetworkInfo
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable
                }
            }
            return false
        }

        //判断是否连wifi
        fun isWifiConnected(context : Context) : Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            cm ?: return false
            val networkInfo = cm.activeNetworkInfo
            return networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI
        }

        /**
         * 获取ip地址
         */
        fun getIpAddress(context : Context) : String? {
            //获取wifi服务
            val wifiManager : WifiManager =
                context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            //判断wifi是否开启
            if (wifiManager.isWifiEnabled) {
                val wifiInfo : WifiInfo = wifiManager.connectionInfo
                val ipAddress = wifiInfo.ipAddress
                return intToIp(ipAddress)

            }
            return getLocalIpAddress()

        }

        /**
         * 使用GPRS获取ip
         */
        private fun getLocalIpAddress() : String? {
            try {
                val en : Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val intf : NetworkInterface = en.nextElement()
                    val enumIpAddr : Enumeration<InetAddress> = intf.inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress : InetAddress = enumIpAddr.nextElement()
                        if (! inetAddress.isLoopbackAddress) {
                            return inetAddress.hostAddress.toString()
                        }
                    }
                }
            } catch (ex : SocketException) {
                LogUtil.logD("WifiPreference IpAddress $ex")
            }
            return null
        }

        private fun intToIp(i : Int) : String {
            return (i and 0xFF).toString() + "." + ((i shr 8) and 0xFF) + "." + ((i shr 16) and 0xFF) + "." + ((i shr 24) and 0xFF)
        }

        /**
         * textView动态设置图片位置
         *
         * @param
         */
        fun setCompoundDrawables(mipmap : Int , textView : TextView? , orientation : Int) {
            textView?.apply {
                val drawable = BaseApplication.mAppContext.getDrawable(mipmap)
                setCompoundDrawabless(drawable , textView , orientation)
            }
        }

        /**
         * textView动态设置图片位置
         *
         * @param
         */
        fun setCompoundDrawabless(drawable : Drawable? , textView : TextView? , orientation : Int) {
            textView?.apply {
                drawable?.apply {
                    setBounds(
                        0 , 0 , drawable.minimumWidth , drawable.minimumHeight)
                    when (orientation) {
                        CommonConstant.ORIENTATION_LEFT -> textView.setCompoundDrawables(
                            drawable , null , null , null)
                        CommonConstant.ORIENTATION_TOP  -> textView.setCompoundDrawables(
                            null , drawable , null , null)
                        CommonConstant.ORIENTATION_RIGHT  -> textView.setCompoundDrawables(
                            null , null , drawable , null)
                        CommonConstant.ORIENTATION_BOTTOM -> textView.setCompoundDrawables(
                            null , null , null , drawable)
                    }
                }
            }
        }

        /**
         * textView动态设置图片为空
         *
         * @param
         */
        fun setCompoundDrawablesNull(textView : TextView) {
            textView.setCompoundDrawables(
                null , null , null , null)
        }

        /**
         * 弹出输入键盘
         *
         * @param context
         */
        fun showInput(context : Activity) {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(
                0 , InputMethodManager.HIDE_NOT_ALWAYS)
        }

        /**
         * 隐藏键盘
         *
         * @param activity
         */
        fun hideInput(activity : Activity) {
            try {
                (activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    activity.currentFocus !!.windowToken ,
                    InputMethodManager.HIDE_NOT_ALWAYS)
            } catch (re : Exception) {
            }

        }

        //隐藏键盘
        fun hideInputs(context : Context , view : View) {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken , 0)
        }

        /**
         * 获取软键盘状态
         *
         * @param context
         * @return
         */
        fun isShowSoftInput(context : Context , view : View) : Boolean {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            //获取状态信息
            return imm.isActive(view) //true 打开
        }

        /**
         * EditText获取焦点并显示软键盘
         */
        fun showSoftInputFromWindow(activity : Activity , editText : EditText) {
            editText.isEnabled = true
            editText.isFocusable = true
            editText.isFocusableInTouchMode = true
            editText.requestFocus()
            editText.requestFocusFromTouch()
            editText.selectAll()
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText , InputMethodManager.RESULT_SHOWN)
            imm.toggleSoftInput(
                InputMethodManager.SHOW_FORCED , InputMethodManager.HIDE_IMPLICIT_ONLY)

        }

        /**
         * 监听软键盘显示或者隐藏
         *
         * @param activity
         * @param
         */
        fun addSoftVisibleListener(activity : Activity ,
            addSoftKeyBoardVisibleListener : AddSoftKeyBoardVisibleListener) {
            val decorView = activity.window.decorView
            decorView.viewTreeObserver.addOnGlobalLayoutListener {
                val rect = Rect()
                decorView.getWindowVisibleDisplayFrame(rect)
                val displayHight = rect.bottom - rect.top
                val height = decorView.height
                val visible = displayHight.toDouble() / height > 0.8
                addSoftKeyBoardVisibleListener.softKeyBoardVisibleListener(visible)
            }
        }

        /**
         * 监听软键盘显示或者隐藏
         *
         * @param
         * @param
         */
        fun addSoftVisibleListener(rootView : View) { //根部局
            rootView.addOnLayoutChangeListener { v , left , top , right , bottom , oldLeft , oldTop , oldRight , oldBottom ->
                if (bottom - oldBottom < - 1) {
                    //软键盘弹上去了,动态设置高度为0
                    //   mAddshopLlEdit.setVisibility(View.GONE);

                } else if (bottom - oldBottom > 1) {
                    //软键盘弹下去了，动态设置高度，恢复原先控件高度
                    //（"1"这个高度值可以换做：屏幕高度的1/3）
                    //   mAddshopLlEdit.setVisibility(View.VISIBLE);
                }
            }
        }

        //获取软键盘高度
        //静态变量存储高度
        var keyboardHeight = 0
        private var isVisiableForLast = false
        private var onGlobalLayoutListener : ViewTreeObserver.OnGlobalLayoutListener? = null

        fun addSoftHeightListener(activity : Activity) {
            if (keyboardHeight > 0) {
                return
            }
            val decorView = activity.window.decorView
            onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
                val rect = Rect()
                decorView.getWindowVisibleDisplayFrame(rect)
                //计算出可见屏幕的高度
                val displayHight = rect.bottom - rect.top
                //获得屏幕整体的高度
                val hight = decorView.height
                val visible = displayHight.toDouble() / hight < 0.8
                var statusBarHeight = 0
                try {
                    val c = Class.forName("com.android.internal.R\$dimen")
                    val obj = c.newInstance()
                    val field = c.getField("status_bar_height")
                    val x = Integer.parseInt(field.get(obj) !!.toString())
                    statusBarHeight = ResourceUtil.resources.getDimensionPixelSize(x)
                } catch (e : Exception) {
                }

                if (visible && visible != isVisiableForLast) {
                    //获得键盘高度
                    keyboardHeight = hight - displayHight - statusBarHeight   //需要px转为dp
                    LogUtil.logD(
                        "$keyboardHeight--------------" + ResourceUtil.px2Dip(
                            keyboardHeight))

                }
                isVisiableForLast = visible
            }
            decorView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
        }

        /**
         * 判断dd-MM-yyyy是否合法
         *
         * @param
         * @return
         */
        fun isBirthDate(str : CharSequence) : Boolean {
            var regExp =
                "(((0[1-9]|[12][0-9]|3[01])-(0[13578]|1[02]))|((0[1-9]|[12][0-9]|30)-(0[469]|11))|((0[1-9]|[1][0-9]|2[0-8])-02))-([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})"
            val p = Pattern.compile(regExp)
            val m = p.matcher(str)
            return m.matches()
        }

        //调起打电话
        fun call(activity : Activity , phone : String) {
            val intent = Intent()
            intent.action = Intent.ACTION_CALL
            intent.data = Uri.parse("tel:$phone")
            activity.startActivity(intent)
        }

        //判断密码8-16并且包含大小写
        fun checkPassWord(password : String) : Boolean {
            var isDigit = false      //是否包含数字
            var isLetter = false    //是否包含字母

            for (i in password.indices) {
                if (Character.isDigit(password[i])) {
                    isDigit = true
                } else if (Character.isLetter(password[i])) {
                    isLetter = true
                }
            }
            //String regex = "^[a-zA-Z0-9]{8,20}$";
            val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$"

            return isDigit && isLetter && password.matches(regex.toRegex())
        }

        //判断密码包含字母数字
        fun isMatcherFinded2(input : String) : Boolean {
            val pattern = Pattern.compile(".*[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]")
            val matcher = pattern.matcher(input)
            return matcher.find()
        }

        fun testMatch(input : String) : Boolean {
            val pattern = Pattern.compile("^(?!\\d+\$)|[\\da-zA-Z]{6,20}+$")
            val matcher = pattern.matcher(input)
            return matcher.find()
        }

        //判断密码数字或者字母或者特殊符号中的两种即可。
        fun isMatcherFinded3(input : String) : Boolean {
            val pattern = Pattern.compile("(?!^(\\d+|[a-zA-Z]+|[~!@#$%^&*?]+)$)^[\\w~!@#$%^&*?]{7,20}$")
            val matcher = pattern.matcher(input)
            return matcher.find()
        }

        //是否有中文
        fun hasChinese(str : String?) : Boolean {
            var temp = false
            val p = Pattern.compile("[\u4e00-\u9fbb]+")
            val m = p.matcher(str)
            if (m.find()) {
                temp = true
            }
            return temp
        }

        //格式化银行卡
        fun formatBankCardSpace(str : String) : String {
            if (StringUtil.isEmpty(str)) return ""
            val space = " "
            val len = str.length
            val builder = StringBuilder()
            for (i in 0 until len) {
                builder.append(str[i])
                if (i == 3 || i == 7 || i == 11 || i == 15 || i == 19) {
                    if (i != len - 1) builder.append(space)
                }
            }
            return builder.toString()
        }

        /**
         * @param
         * @return float保留两位小数返string
         *
         */
        fun floatToString(float : Float?) : String {
            if (null != float) {
                // 设置位数
                val scale = 2
                // 表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
                val roundingMode = 4
                var bd = BigDecimal(float.toString())
                bd = bd.setScale(scale , roundingMode)
                return bd.toString()
            }
            return "0.00"
        }

        /**
         * @param
         * @return
         * @desc 1.0~1之间的BigDecimal小数，格式化后失去前面的0,则前面直接加上0。
         * 2.传入的参数等于0，则直接返回字符串"0.00"
         * 3.大于1的小数，直接格式化返回字符串
         */
        fun formatToNumber(obj : BigDecimal?) : String {
            if (null != obj) {
                var df = DecimalFormat("#.00")
                /*currencyCode?.apply {
                    if (ResourceUtil.getString(R.string.JPY)==this){
                        df = DecimalFormat("0")
                    }
                }*/
                var data = if (obj.compareTo(BigDecimal.ZERO) == 0) {
                    "0.00"
                } else if (obj > BigDecimal.ZERO && obj < BigDecimal(1)) {
                    "0" + df.format(obj).toString()
                } else if (obj < BigDecimal.ZERO && obj > BigDecimal(- 1)) {
                    df.format(obj).toString().replace("-" , "-0")
                } else if (obj < BigDecimal.ZERO) {
                    df.format(obj).toString()
                } else {
                    df.format(obj).toString()
                }
                data.split(".").apply {
                    var str1 = ""
                    var str = get(0)
                    if (this.size > 1) str1 = get(1)
                    val formatStr = formatData(str)  //取小数点前整数去格式化计算
                    return if (! formatStr.contains(".")) "$formatStr.$str1" else formatStr + str1
                }
            }
            return "0.00"
        }

        private fun formatData(str : String?) : String {
            str?.apply {
                var symbol : String? = ""
                var string : String? = ""
                if (this.contains("+") || this.contains("-")) {
                    symbol = this.substring(0 , 1)
                    string = this.substring(1 , this.length)
                } else string = str
                string.apply {
                    val characters = mutableListOf<Char>()
                    for (index in this.indices) {
                        characters.add(index , this[index])
                    }
                    if (characters.size > 9) {
                        characters.add(characters.size - 9 , ',')
                        characters.add(characters.indexOf(',') + 4 , ',')
                        characters.add(characters.indexOf(',') + 8 , ',')
                        characters.add(characters.indexOf(',') + 12 , '.')
                    } else if (characters.size > 6) {
                        characters.add(characters.size - 6 , ',')
                        characters.add(characters.indexOf(',') + 4 , ',')
                        characters.add(characters.indexOf(',') + 8 , '.')
                    } else if (characters.size > 3) {
                        characters.add(characters.size - 3 , ',')
                    }
                    var formatData = ""
                    characters.forEach {
                        formatData += it
                    }
                    return symbol + formatData
                }
            }
            return "0.00"
        }

        private fun customMultiply(bigDecimal : BigDecimal? , bigDecimalTwo : BigDecimal?) : String {
            if (bigDecimal != null && bigDecimalTwo != null) {
                return formatToNumber(bigDecimal.multiply(bigDecimalTwo))
            }
            return ""
        }

        //将数字转换成万
        fun toNumber(number : Int) : String {
            var str = ""
            if (number <= 0) {
                str = ""
            } else if (number < 10000) {
                str = number.toString() + "元"
            } else {
                val d = number.toDouble()
                //将数字转换成万
                val num = d / 10000
                val b = BigDecimal(num)
                /*       //四舍五入保留小数点后一位
            double f1 = b.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();*/

                str = b.toInt().toString() + "万元"
            }
            return str
        }

        //正则过滤特殊字符和表情
        fun checkExpression(str : String) : Boolean {
            val p = Pattern.compile("^[A-Za-z0-9\u4e00-\u9fbb]+$")
            val m = p.matcher(str)
            return m.find()
        }

        /**
         * 判断文本中是否包含Emoji表情
         *
         * @param source 文本
         * @return 是否包含
         */
        fun hasExpression(source : String) : Boolean {
            val len = source.length
            for (i in 0 until len) {
                val codePoint = source[i]
                if (! hasExpression(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                    return true
                }
            }
            return false
        }

        /**
         * 判断是否是Emoji
         *
         * @param codePoint 比较的单个字符
         * @return 是否是Emoji
         */
        private fun hasExpression(codePoint : Char) : Boolean {
            return (codePoint.toInt() == 0x0 || codePoint.toInt() == 0x9 || codePoint.toInt() == 0xA || codePoint.toInt() == 0xD || codePoint.toInt() in 0x20 .. 0xD7FF || codePoint.toInt() in 0xE000 .. 0xFFFD)
        }

        //中文转义,解决上传的图片是中文名称
        fun name2En(name : String) : String {
            val stringBuffer = StringBuffer()
            var i = 0
            val length = name.length
            while (i < length) {
                val c = name[i]
                if (c <= '\u001f' || c >= '\u007f') {
                    stringBuffer.append(String.format("\\u%04x" , c.toInt()))
                } else {
                    stringBuffer.append(c)
                }
                i ++
            }
            return stringBuffer.toString()
        }

        /**
         * 获取手机型号
         *
         * @return  手机型号
         */
        fun getSystemModel() : String? {
            return Build.MODEL
        }

        //获取设备id
        @SuppressLint("HardwareIds")
        fun getDeviceId(context : Context) : String {
            var deviceId : String? = null
            deviceId = Settings.Secure.getString(context.contentResolver , Settings.Secure.ANDROID_ID)
            LogUtil.logD("设备唯一标识111-$deviceId")
            if (TextUtils.isEmpty(deviceId)) {
                LogUtil.logD("设备唯一标识222-" + getMacAddress())
                return getMacAddress()
            }
            return deviceId
        }

        @SuppressLint("HardwareIds")
        fun getMacAddress() : String {
            var macAddress = ""
            val wifiManager =
                BaseApplication.mAppContext.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val info = wifiManager.connectionInfo
            if (! wifiManager.isWifiEnabled) {
                //必须先打开，才能获取到MAC地址
                wifiManager.isWifiEnabled = true
                wifiManager.isWifiEnabled = false
            }
            if (null != info) {
                macAddress = info.macAddress
            }
            return macAddress
        }


        private var sID : String? = null
        private const val INSTALLATION = "INSTALLATION"

        @Synchronized
        fun id(context : Context) : String {
            if (sID == null) {
                val installation = File(context.filesDir , INSTALLATION)
                try {
                    if (! installation.exists()) writeInstallationFile(installation)
                    sID = readInstallationFile(installation)
                } catch (e : Exception) {
                    throw RuntimeException(e)
                }

            }
            return sID !!
        }

        @Throws(IOException::class)
        private fun readInstallationFile(installation : File) : String {
            val f = RandomAccessFile(installation , "r")
            val bytes = ByteArray(f.length().toInt())
            f.readFully(bytes)
            f.close()
            return String(bytes)
        }

        @Throws(IOException::class)
        private fun writeInstallationFile(installation : File) {
            val out = FileOutputStream(installation)
            val id = UUID.randomUUID().toString()
            out.write(id.toByteArray())
            out.close()
        }

        fun copy(context : Context , text : String) {
            //获取剪贴板管理器：
            val cliboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // 创建普通字符型ClipData
            val mClipData = ClipData.newPlainText("Label" , text)
            // 将ClipData内容放到系统剪贴板里。
            cliboardManager.setPrimaryClip(mClipData)
            ToastUtil.showText("复制成功")
            // textView.  setTextIsSelectable(true);//android:textIsSelectable="true"  自由复制
        }

        //将对象的属性赋值不为空的加到集合
        fun getUsefulObj(any : Any?) : Map<* , *> {
            val map = HashMap<String , Any>()
            val fields = any?.javaClass?.fields
            fields?.forEach {
                try {
                    val o = it.get(any)
                    if (null != o) {
                        map[it.name] = o
                    }
                } catch (e : IllegalAccessException) {
                }
            }
            return map
        }

        /**
         * 设置 hint字体大小
         * @param editText 输入控件
         * @param hintText hint的文本内容
         * @param textSize hint的文本的文字大小（以dp为单位设置即可）
         */
        fun setHintTextSize(editText : EditText , hintText : String? , textSize : Int) {
            // 新建一个可以添加属性的文本对象
            val ss = SpannableString(hintText)
            // 新建一个属性对象,设置文字的大小
            val ass = AbsoluteSizeSpan(textSize , true)
            // 附加属性到文本
            ss.setSpan(ass , 0 , ss.length , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            // 设置hint
            editText.hint = SpannedString(ss) // 一定要进行转换,否则属性会消失
        }

        fun setVisible(vararg views : View) {
            for (view in views) {
                view.visibility = View.VISIBLE
            }
        }

        fun setGone(vararg views : View) {
            for (view in views) {
                view.visibility = View.GONE
            }
        }

        fun setInvisible(vararg views : View) {
            for (view in views) {
                view.visibility = View.INVISIBLE
            }
        }


        fun getStar(length : Int) : String {
            var star = ""
            for (index in 1 .. length) {
                star += "*"
            }
            return star
        }

        /**
         * 跳转到权限设置界面
         */
        fun getAppDetailSettingIntent(context : Context) {
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            intent.data = Uri.fromParts("package" , context.packageName , null)
            context.startActivity(intent)
        }

        //判断应用是否在后台
        fun isApplicationInBackground(context : Context) : Boolean {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val taskList = am.getRunningTasks(1)
            if (taskList != null && taskList.isNotEmpty()) {
                val topActivity = taskList[0].topActivity
                if (topActivity != null && topActivity.packageName != context.packageName) {
                    return true
                }
            }
            return false
        }

        //判断应用是否处于后台
        open fun isBackground(context : Context) : Boolean {
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val appProcesses = activityManager.runningAppProcesses
            for (appProcess in appProcesses) {
                if (appProcess.processName == context.packageName) {
                    return appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                }
            }
            return false
        }

        //安装apk
        fun installApk(activity : Activity , apkPath : String?) {
            if (apkPath.isNullOrEmpty()) return
            val intent = Intent(Intent.ACTION_VIEW)
            val apkFile = File(apkPath)
            // android 7.0 fileprovider 适配
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                val contentUri = FileProvider.getUriForFile(activity, activity.packageName + ".fileProvider", apkFile)
               // val contentUri = FileProvider.getUriForFile(activity , activity.packageName , apkFile)
                intent.setDataAndType(contentUri , "application/vnd.android.package-archive")
            } else {
                intent.setDataAndType(Uri.fromFile(apkFile) , "application/vnd.android.package-archive")
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(intent)
        }

        // 密码不能连续字符超过两个，不能一半以上是同一字符
        fun filterPsd(password : String?) : Boolean {
            password?.apply {
                var map = HashMap<Char , Int>()
                password.forEachIndexed { index , c ->
                    if (index + 2 < password.length) {
                        if (c == password[index + 1] && c == password[index + 2]) {
                            return true
                        }
                    }
                    if (map.containsKey(c)) {
                        var i = map[c] as Int
                        i ++
                        map[c] = i
                    } else {
                        map[c] = 1
                    }
                }
                val keys = map.keys
                keys.forEachIndexed { index , c ->
                    var value = map[c] as Int
                    if (value > password.length / 2) {
                        return true
                    }
                }
            }
            return false
        }

        //监听是否按了最近任务列表键
        fun deviceKeyMonitor(context : Context , onKeyListener : OnKeyListener) : BroadcastReceiver {
            var deviceKeyReceiver = object : BroadcastReceiver() {
                override fun onReceive(context : Context , intent : Intent) {
                    val action = intent.action
                    if (! TextUtils.isEmpty(action)) {
                        if (intent.action == Intent.ACTION_CLOSE_SYSTEM_DIALOGS) {
                            val reason = intent.getStringExtra("reason") ?: return
                            // 最近任务列表键
                            if (reason == "recentapps") {
                                onKeyListener.onRecentClick()
                            }
                        }
                    }
                }
            }
            context.registerReceiver(
                deviceKeyReceiver , IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))
            return deviceKeyReceiver
        }

        /**
         * 判断手机是否安装某个应用
         * @param context
         * @param appPackageName  应用包名
         * @return   true：安装，false：未安装
         */
        fun isInstallApp(context : Context , appPackageName : String) : Boolean {
            val packageManager = context.packageManager // 获取packagemanager
            val info = packageManager.getInstalledApplications(0) // 获取所有已安装程序的包信息
            for (i in 0 until info.size) {
                val pn = info[i].packageName
                if (appPackageName == pn) {
                    return true
                }
            }
            return false
        }

        /**
         * 打开另一个app
         * @param context
         * @param appPackageName 另app包名
         */
        fun openTagActivity(context : Context , appPackageName : String) {
            val intent : Intent? = context.packageManager.getLaunchIntentForPackage(appPackageName)
            context.startActivity(intent)
        }

        /**
         * 跳转到指定Activity
         * @param context
         * @param appPackageName 另app包名
         * @param tagActivity  指定目标Activity全路径
         */
        fun startTagActivity(context : Context , custId : String? , appPackageName : String , tagActivity : String) {
            val intent = Intent()
            try {
                //第一种方式
                // val cn = ComponentName("cn.com.spdb.hk.pwms", "com.hq.main.activity.LoginActivity")
                val cn = ComponentName(appPackageName , tagActivity)
                intent.component = cn
                //第二种方式
                // intent.setClassName("cn.com.spdb.hk.pwms", "com.hq.main.activity.LoginActivity")
                intent.putExtra("appName" , "PWMS")
                intent.putExtra("masterId" , custId)
                context.startActivity(intent)
            } catch (e : java.lang.Exception) {
                ToastUtil.showText("跳转失败")
            }
        }

        //判断是否使用代理IP
        fun isWifiProxy(context: Context): Boolean {
            var proxyAddress: String?=""
            val proxyPort: Int
            proxyAddress = System.getProperty("http.proxyHost")
            val portStr = System.getProperty("http.proxyPort")
            proxyPort = (portStr ?: "-1").toInt()
            return !TextUtils.isEmpty(proxyAddress) && proxyPort != -1
        }

        //判断是否使用VPN
        fun checkVPN(): Boolean {
            try {
                val niList = NetworkInterface.getNetworkInterfaces()
                if (niList != null) {
                    for (inf in Collections.list(niList)) {
                        if (!inf.isUp || inf.interfaceAddresses.size == 0) {
                            continue
                        }
                        if ("tun0" == inf.name || "ppp0" == inf.name) {
                            return true // The VPN is up
                        }
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            return false
        }

        //判断是否使用VPN
        fun checkVPN2(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_VPN)
            return networkInfo?.isConnected ?: false
        }

    }
}

