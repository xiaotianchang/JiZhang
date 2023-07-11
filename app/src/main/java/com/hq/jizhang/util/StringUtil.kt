package com.hq.jizhang.util

import android.annotation.SuppressLint
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException


class StringUtil {

    companion object {

        //  const val matchPsd="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$"
        const val matchPsd =
            "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z@\$!%*#?&]{8,20}$" //8-20位数字或字母或符号至少两种组合
        //   const val matchPsd="^([a-zA-Z0-9@\$!%*#?&]){6,20}$"

        // 空判断
        fun isEmpty(obj : Any?) : Boolean {
            if (null == obj) return true
            else if (obj == "") return true
            else if (obj is Int || obj is Long || obj is Double) {
                try {
                    java.lang.Double.parseDouble(obj.toString() + "")
                } catch (e : Exception) {
                    return true
                }
            } else if (obj is String) {
                if (obj.length <= 0) return true
                if ("null" == obj) return true
            } else if (obj is Map<* , *>) {
                if (obj.size == 0) return true
            } else if (obj is Collection<*>) {
                if (obj.size == 0) return true
            }
            return false
        }

        //判断6-20字母数字组合
        fun matcherSixToTwentySum(input : String) : Boolean {
            val pattern = Pattern.compile(matchPsd)
            val matcher = pattern.matcher(input)
            return (matcher.find())
        }

        //判断6-20字母数字组合
        fun matcherSixToTwentyName(input : String) : Boolean {
            val pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$")
            val matcher = pattern.matcher(input)
            return (matcher.find())
        }

        //判断6-20字母数字组合 可纯字母不能纯数字
        fun matcherSixToTwenty(input : String) : Boolean {
            val pattern = Pattern.compile("^(?!\\d+\$)[\\da-zA-Z]{6,20}$")
            val matcher = pattern.matcher(input)
            return (matcher.find())
        }

        // 判断是否存在汉字
        fun hasChineseCharacter(str : String?) : Boolean {
            val p = Pattern.compile("[\u4e00-\u9fbb]+")
            val m = p.matcher(str)
            return m.find()
        }

        // 判断整个字符串是否都是汉字
        fun isAllChineseCharacter(str : String) : Boolean {
            var n = 0
            for (element in str) {
                n = element.toInt()
                if (n !in 19968 .. 40868) {
                    return false
                }
            }
            return true
        }

        // 判断有多少个汉字
        fun manyChinese(str : String) : Int {
            val p = Pattern.compile("[\u4e00-\u9fbb]")
            val m = p.matcher(str)
            var count = 0
            while (m.find()) {
                for (index in 0 .. m.groupCount()) {
                    count += 1
                }
            }
            return count
        }

        //* 验证输入的名字是否为“中文”或者是否包含“·”
        fun hasChineseCharacterPot(name : String) : Boolean {
            return if (name.contains("·") || name.contains("•")) {
                name.matches("^[\u4e00-\u9fbb]+[·•][\u4e00-\u9fbb]+$".toRegex())
            } else {
                name.matches("^[\u4e00-\u9fbb]+$".toRegex())
            }
        }

        /**
         * 判断邮箱是否合法
         *
         * @param
         * @return
         */
        fun isEmail(strEmail : String) : Boolean {
            val strPattern =
                "^\\s*\\w+(?:\\.?[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$"
            val p = Pattern.compile(strPattern)
            val m = p.matcher(strEmail)
            return m.matches()
        }

        /**
         * 检测邮箱是否合法
         *
         * @param email
         * @return
         */
        fun isEmailLegal(email : String) : Boolean {
            val p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")
            val m = p.matcher(email)
            return m.matches()
        }

        /**
         * 获取string,为null则返回""
         *
         * @param s
         * @return
         */
        fun getString(any : Any?) : String {
            any?.apply {
                if (this is String) return this
                else if (this is Int) return this.toString()
                else if (this is BigDecimal) return this.toString()
            }
            return ""
        }

        /**
         * 大陆号码或香港号码均可
         */
        @Throws(PatternSyntaxException::class)
        fun isPhoneLegal(str : String) : Boolean {
            return isChinaPhoneLegal(str) || isHKPhoneLegal(str)
        }

        /**
         * 香港手机号码8位数，5|6|8|9开头+7位任意数
         */
        @Throws(PatternSyntaxException::class)
        fun isHKPhoneLegal(str : String) : Boolean {
            val regExp = "^([123456789])\\d{7}$"
            val p = Pattern.compile(regExp)
            val m = p.matcher(str)
            return m.matches()
        }

        /**
         * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
         */
        @Throws(PatternSyntaxException::class)
        fun isChinaPhoneLegal(tel : String) : Boolean {
            val regExp = "^(1[2-9][0-9])\\d{8}$"
            val p = Pattern.compile(regExp)
            val m = p.matcher(tel)
            return m.matches()
        }


        /**
         * 检测密码是否合法
         * 必须包含数字和字母,且长度为8-16位
         *
         * @param psw
         * @return
         */
        fun isPswLegal(psw : String) : Boolean {
            val p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$")
            val m = p.matcher(psw)
            return m.matches()
        }

        /**
         * 检测验证码是否合法
         *
         * @param verifyCode
         * @return
         */
        fun isVerifyCodeLegal(verifyCode : String) : Boolean {
            val p = Pattern.compile("[0-9]{6}")
            val m = p.matcher(verifyCode)
            return m.matches()
        }

        /**
         * 18位或者15位身份证验证 18位的最后一位可以是字母X
         *
         * @param text
         * @return
         */
        fun isIdCardLegal(text : String) : Boolean {
            var flag : Boolean
            val regx = "[0-9]{17}X"
            val reg1 = "[0-9]{15}"
            val regex = "[0-9]{18}"
            val regehk = "^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?$"
            val regeam = "^[1|5|7][0-9]{6}\\([0-9Aa]\\)"
            flag =
                text.matches(regx.toRegex()) || text.matches(reg1.toRegex()) || text.matches(regex.toRegex()) || text.matches(
                    regehk.toRegex()
                ) || text.matches(regeam.toRegex())
            return flag
        }

        /**
         * 判断身份证是否合法
         *
         * @param
         * @return
         */
        fun isIdCard(strIdCard : String) : Boolean {
            val strPattern =
                "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$"
            val p = Pattern.compile(strPattern)
            val m = p.matcher(strIdCard)
            return m.matches()
        }

        /**
         * 格式化小数,清除无用的0
         *
         * @param decimal
         * @return
         */
        fun formatDecimalWithoutZoro(decimal : Double) : String {
            val decimalFormat = DecimalFormat("###################.###########")
            return decimalFormat.format(decimal)
        }

        /**
         * 格式化时间戳,格式为yyyy-MM-dd
         *
         * @param stamp
         * @return
         */
        @SuppressLint("SimpleDateFormat")
        fun stampToDate(stamp : String) : String? {
            if (! isEmpty(stamp)) {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val dateLong = java.lang.Long.valueOf(stamp)
                return dateFormat.format(Date(dateLong * 1000))
            }
            return null
        }

        /**
         * 格式化时间戳,格式为yyyy-MM-dd HH:mm:ss
         *
         * @param stamp
         * @return
         */
        @SuppressLint("SimpleDateFormat")
        fun stampToDateTime(stamp : String) : String? {
            if (! isEmpty(stamp)) {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val dateLong = java.lang.Long.valueOf(stamp)
                return dateFormat.format(Date(dateLong * 1000))
            }
            return null
        }

        /**
         * 将时间转换为时间戳
         * yyyy-MM-dd HH:mm:ss
         *
         * @param dateTime
         * @return
         */
        @SuppressLint("SimpleDateFormat")
        fun dateTimeToStamp(dateTime : String) : String {
            val res : String
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var date : Date? = null
            try {
                date = simpleDateFormat.parse(dateTime)
            } catch (e : ParseException) {
            }

            val ts = date !!.time
            res = (ts / 1000).toString()
            return res
        }

        /**
         * 格式化时间字符串,去掉T
         * 2018-11-14T10:29:18
         * @param date
         * @return
         */
        fun formatDate(date : String) : String {
            return if (! isEmpty(date)) {
                date.replace('T' , ' ')
            } else ""
        }

        /**
         * 查询字符串中首个数字出现的位置
         * @param str 查询的字符串
         * @return 若存在，返回位置索引，否则返回-1；
         */
        fun findFirstIndexNumberOfStr(str : String?) : Int {
            var i = - 1
            val matcher = Pattern.compile("[0-9]").matcher(str)
            if (matcher.find()) {
                i = matcher.start()
            }
            return i
        }

        /**
         * 中文姓名脱敏
         * @param fullNameC 中文名字
         * @return
         */
        fun nameToStarC(fullNameC : String?) : String {
            fullNameC?.apply {
                if (this.contains("*"))return this
                val fullNameArray = fullNameC.toCharArray()
                val sb = java.lang.StringBuilder()
                for (i in fullNameArray.indices) {
                    if (i % 2 == 1) {
                        sb.append("*")
                    } else {
                        sb.append(fullNameArray[i])
                    }
                }
                return sb.toString()
            }
           return getString(fullNameC)
        }

        /**
         * 中文姓名脱敏
         * @param fullNameE 英文名字
         * @return
         */
        fun nameToStarE(fullNameE : String?) : String {
            fullNameE?.apply {
                if (this.contains("*"))return this
                val indexOfSpace : Int = this.indexOf(" ")
                val length : Int = this.length
                val fullNameArr : CharArray = this.toCharArray()
                val stringBuilder : StringBuilder = StringBuilder()
                var i = 0
                while (i < length) {
                    val nameChar = fullNameArr[i]
                    if (i > indexOfSpace - 1 && indexOfSpace != - 1) {
                        if (nameChar == ' ') {
                            stringBuilder.append(nameChar).append(fullNameArr[i + 1])
                            i += 1
                        } else {
                            stringBuilder.append('*')
                        }
                    } else {
                        stringBuilder.append(nameChar)
                    }
                    i ++
                }
                return stringBuilder.toString()
            }
            return getString(fullNameE)
        }

        /**
         * 邮箱脱敏
         * @param email 邮箱
         * @return
         */
        fun emailToStar(email : String?) : String {
            email?.apply {
                if (this.contains("*"))return this
                var startSubString = this.substring(0 ,this.indexOf("@") )
                var endSubString = this.substring(this.indexOf("@") ,this.length )
                var length = BigDecimal(startSubString.length).divide(BigDecimal(2),BigDecimal.ROUND_HALF_UP).toInt()
                var substring = startSubString.substring(0,length)
                return substring+AppUtil.getStar(length)+endSubString
            }
            return getString(email)
        }

        /**
         * 手机号
         * @param phone 手机号
         * @return
         */
        fun phoneToStar(phone : String?) : String {
            phone?.apply {
                if (this.contains("*"))return this
                return if(this.startsWith("+86-")){
                    var delSubstring = this.substring(this.indexOf("-")+1 , this.length)
                    var length = BigDecimal(delSubstring.length).divide(BigDecimal(2),BigDecimal.ROUND_HALF_UP).toInt()
                    var substring = "+86-"+delSubstring.substring(0,length)
                    substring+AppUtil.getStar(length)
                }else if (this.startsWith("+852")){
                    var delSubstring = this.substring(this.indexOf("-")+1 , this.length)
                    var length = BigDecimal(delSubstring.length).divide(BigDecimal(2),BigDecimal.ROUND_HALF_UP).toInt()
                    var substring = "+852-"+delSubstring.substring(0,length)
                    substring+AppUtil.getStar(length)
                }else{
                    var length=  BigDecimal(this.length).divide(BigDecimal(2),BigDecimal.ROUND_HALF_UP).toInt()
                    var substring = this.substring(0,length)
                    substring+AppUtil.getStar(this.length-substring.length)
                }
            }
            return getString(phone)
        }

    }
}
