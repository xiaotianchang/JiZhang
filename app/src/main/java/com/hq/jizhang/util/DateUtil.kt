package com.hq.jizhang.util

import android.content.Context
import android.provider.Settings
import java.text.DateFormat
import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author ZGL007
 * @time 2017/10/27 10:03
 * @des ${TODO}  获取日期时间相关工具类
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
class DateUtil {

    companion object {

        //获取年
        fun getYear(): String {
            val sdf = SimpleDateFormat("yyyy")
            val curDate = Date(System.currentTimeMillis())
            return sdf.format(curDate)
        }

        //获取月
        fun getMonth(month : Date?=Date()): String {
            val sdf = SimpleDateFormat("MM")
            val curDate = Date(System.currentTimeMillis())
            return sdf.format(if (StringUtil.isEmpty(month)) curDate else month)
        }

        //获取日
        fun getDay(day : Date?=Date()): String {
            val sdf = SimpleDateFormat("dd")
            val curDate = Date(System.currentTimeMillis())
            return sdf.format(if (StringUtil.isEmpty(day)) curDate else day)
        }

        //获取年月
        val yearMonth: String
            get() {
                val sdf = SimpleDateFormat("yyyyMM")
                val curDate = Date(System.currentTimeMillis())
                return sdf.format(curDate)
            }

        //获取年月+单位
        val yearMonthUnit: String
            get() {
                val sdf = SimpleDateFormat("yyyy年MM月")
                val curDate = Date(System.currentTimeMillis())
                return sdf.format(curDate)
            }

        //获取年月日当前日期  yyyy-MM-dd
        val curDate: String
            get() {
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val curDate = Date(System.currentTimeMillis())
                return sdf.format(curDate)
            }

        //获取年月日当前日期 yyyyMMdd
        val curDates: String
            get() {
                val sdf = SimpleDateFormat("yyyyMMdd")
                val curDate = Date(System.currentTimeMillis())
                return sdf.format(curDate)
            }

        //获取时分 12:00
        val curTime: String
            get() {
                val calendar = Calendar.getInstance()
                val hour = calendar[Calendar.HOUR_OF_DAY]
                val minute = calendar[Calendar.MINUTE]
                return "$hour:$minute"
            }

        //获取年月日时分 2017-11-08 12:12
        val allTime: String
            get() {
                val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
                return format.format(Date())
            }

        //获取年月日时分秒 2017-11-08 12:12:59
        val thisTime: String
            get() {
                val format =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                return format.format(Date())
            }

        //获取年月日时分秒 2017-11-08 12:12:59
        val thisDateTime: String
            get() {
                val format =
                    SimpleDateFormat("yyyyMMdd HH:mm:ss")
                return format.format(Date())
            }

        //获取年月日时分秒 12:12:59
        val thisTimes: String
            get() {
                val format =
                    SimpleDateFormat("HH:mm:ss")
                return format.format(Date())
            }

        fun dateToLong(date : String?): Long {
            try {
                return SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date).time
            } catch (e : ParseException) {
            }
            return 0
        }

        fun dateToString(date : Date? , format : String? = "yyyy-MM-dd"): String {
            val dateFormat: DateFormat = SimpleDateFormat(format)
            return dateFormat.format(date)
        }

        //获取指定时间  后past 天的日期
        fun getAfterDate(time : String , past : Int): String {
            // 时间表示格式可以改变，yyyyMMdd需要写例如20160523这种形式的时间
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            // 将字符串的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
            val date = sdf.parse(time , ParsePosition(0))
            val calendar = Calendar.getInstance()
            calendar.time = date
            // add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
            calendar.add(Calendar.DATE , past)
            val date1 = calendar.time
            return sdf.format(date1)
        }

        // 获取前5天日期
        val dateList: List<String>
            get() {
                val list: MutableList<String> =
                    ArrayList()
                val formatDate = SimpleDateFormat("MM-dd")
                val calendar = Calendar.getInstance()
                val beginDate = Date()
                for (i in 4 downTo -1 + 1) {
                    calendar.time = beginDate
                    calendar[Calendar.DATE] = calendar[Calendar.DATE] - i - 1
                    val strDay = formatDate.format(calendar.time)
                    list.add(strDay)
                }
                return list
            }

        // 获取前5个月 +  本月
        fun getFiveMonth(context : Context?): ArrayList<String> {
            val list = ArrayList<String>()
            val sdf = SimpleDateFormat("yyyy年MM月")
            val c = Calendar.getInstance()
            for (x in 4 downTo -1 + 1) {
                c.time = Date()
                c.add(Calendar.MONTH , - x - 1)
                val m = c.time
                val format = sdf.format(m)
                list.add(format)
            }
            return list
        }


        private fun getStringToDate(dateString : String): Long {
            var dateFormat = SimpleDateFormat("yyyy-MM-dd")
            var date = Date()
            try {
                date = dateFormat.parse(dateString)
            } catch (e : ParseException) {
                e.printStackTrace()
            }
            return date.time
        }

        //判断系统是否为24小时制
        fun isHours(context : Context): Boolean {
            val cv = context.contentResolver
            val strTimeFormat = Settings.System.getString(
                cv , Settings.System.TIME_12_24)
            return strTimeFormat == "24"
        }


        //计算后七天
        fun lastMonday(startDate : String?): List<String> {
            val timeList: MutableList<String> =
                ArrayList()
            for (i in 0..6) {
                val calendar = Calendar.getInstance()
                val simpleDateFormat =
                    SimpleDateFormat("yyyy-MM-dd")
                var aDate: Date? = null
                try {
                    aDate = simpleDateFormat.parse(startDate)
                } catch (e : Exception) {
                }
                calendar.time = aDate
                calendar.add(Calendar.DAY_OF_YEAR , - i)
                val date = calendar.time
                val time = simpleDateFormat.format(date)
                timeList.add(time)
            }
            return timeList
        }//星期六//星期五//星期四//星期三//星期二//星期一//星期天

        //获取星期日
        val sunday: String?
            get() {
                val date = Date()
                val calendar = Calendar.getInstance()
                var string: String? = null
                val i = calendar[Calendar.DAY_OF_WEEK]
                val simpleDateFormat =
                    SimpleDateFormat("yyyy-MM-dd")
                //星期天
                return if (i == 1) {
                    string = simpleDateFormat.format(date)
                    LogUtil.logD("当前时间:$string")
                    string
                } else {
                    when (i) {
                        2 ->                     //星期一
                            string = getTimeLow(date , 1)
                        3 ->                     //星期二
                            string = getTimeLow(date , 2)
                        4 ->                     //星期三
                            string = getTimeLow(date , 3)
                        5 ->                     //星期四
                            string = getTimeLow(date , 4)
                        6 ->                     //星期五
                            string = getTimeLow(date , 5)
                        7 ->                     //星期六
                            string = getTimeLow(date , 6)
                    }
                    LogUtil.logD("当前时间:$string")
                    string
                }
            }

        //计算日期
        private fun getTimeLow(date : Date? , i : Int): String {
            var date = date
            val simpleDateFormat =
                SimpleDateFormat("yyyy-MM-dd")
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.DAY_OF_YEAR , - i)
            date = calendar.time
            return simpleDateFormat.format(date)
        }

        private fun getTimeUp(date : Date? , i : Int): String {
            var date = date
            val simpleDateFormat =
                SimpleDateFormat("yyyy-MM-dd")
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.DAY_OF_YEAR , + i)
            date = calendar.time
            return simpleDateFormat.format(date)
        }

        //获取星期六
        fun getSaturday(count : Int): String? {
            val date = Date()
            val a = count * 7
            val calendar = Calendar.getInstance()
            var string: String? = null
            val i = calendar[Calendar.DAY_OF_WEEK]
            val simpleDateFormat =
                SimpleDateFormat("yyyy-MM-dd")
            //星期六
            return if (i == 7) {
                string = simpleDateFormat.format(date)
                string
            } else {
                when (i) {
                    2 ->                     //星期一
                        string = getTimeUp(date , 5 + a)
                    3 ->                     //星期二
                        string = getTimeUp(date , 4 + a)
                    4 ->                     //星期三
                        string = getTimeUp(date , 3 + a)
                    5 ->                     //星期四
                        string = getTimeUp(date , 2 + a)
                    6 ->                     //星期五
                        string = getTimeUp(date , 1 + a)
                    1 ->                     //星期日
                        string = getTimeUp(date , 6 + a)
                }
                string
            }
        }

        /**
         * 获取月份天数
         *
         * @param date
         * @return
         */
        private fun getDaysOfMonth(date : Date?): Int {
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        }

        /**
         * 获取月份
         *
         * @return
         */
        private fun getMonth(i : Int): String {
            val cal = Calendar.getInstance()
            cal.add(Calendar.MONTH , i)
            val dft = SimpleDateFormat("yyyy-MM-dd")
            return dft.format(cal.time)
        }

        /**
         * 获取月份起始日及每月天数
         *
         * @param i
         * @return map
         * startM	起始日期
         * endM	结束日期
         * dayNum	每月天数
         * @throws Exception
         */
        @Throws(Exception::class)
        fun getMonthStartOrEnd(i : Int): Map<String , Any> {
            var day = 0
            val dateMap: MutableMap<String , Any> =
                HashMap()
            val format = SimpleDateFormat("yyyy-MM-dd")
            val calendar = Calendar.getInstance() //获取当前日期
            day = if (i != 0) {
                getDaysOfMonth(format.parse(getMonth(i)))
            } else {
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            }
            //月份第一天：
            calendar.add(Calendar.MONTH , i)
            calendar[Calendar.DAY_OF_MONTH] = 1 //设置为1号,当前日期既为本月第一天
            var str = format.format(calendar.time) //起始日期
            LogUtil.logD("===============first:$str")
            dateMap["startM"] = str

            //月份最后一天
            calendar[Calendar.DAY_OF_MONTH] = day
            str = format.format(calendar.time) //结束日期
            LogUtil.logD("===============last:$str")
            dateMap["endM"] = str
            dateMap["dayNum"] = day
            return dateMap
        }

        /**
         * 获取周起始日期
         *
         * @return
         */
        fun getThisWeekDate(i : Int): Map<String , Any> {
            val dateMap: MutableMap<String , Any> =
                HashMap()
            val df = SimpleDateFormat("yyyy-MM-dd")

            // 根据今天的时间获取本周属于本月的第几周
            val now = Calendar.getInstance()
            now.add(Calendar.WEEK_OF_MONTH , i)
            now[Calendar.DAY_OF_WEEK] = Calendar.MONDAY // 获取本周一的日期
            val wom = now[Calendar.WEEK_OF_MONTH]
            dateMap["indexOfWeek"] = wom

            // 根据今天的时间获取本周的开始时间
            now[Calendar.HOUR_OF_DAY] = 0
            dateMap["startDateOfWeek"] = df.format(now.time)

            // 根据今天的时间获取本周的结束时间
            now[Calendar.DAY_OF_WEEK] = Calendar.SUNDAY
            now.add(Calendar.WEEK_OF_YEAR , 1)
            now[Calendar.HOUR_OF_DAY] = 23
            dateMap["endDateOfWeek"] = df.format(now.time)
            return dateMap
        }


        /**
         * <pre>
         * 根据指定的日期字符串获取星期几
        </pre> *
         *
         * @param strDate 指定的日期字符串(yyyy-MM-dd 或 yyyy/MM/dd)
         * @return week
         * 星期几(MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY)
         */
        fun getWeekByDateStr(strDate : String): String {
            if(strDate.length!=10)return ""
            val year = strDate.substring(0 , 4).toInt()
            val month = strDate.substring(5 , 7).toInt()
            val day = strDate.substring(8 , 10).toInt()
            val c = Calendar.getInstance()
            c[Calendar.YEAR] = year
            c[Calendar.MONTH] = month - 1
            c[Calendar.DAY_OF_MONTH] = day
            var week = ""
            val weekIndex = c[Calendar.DAY_OF_WEEK]
            when (weekIndex) {
                1 -> week = "星期日"
                2 -> week = "星期一"
                3 -> week = "星期二"
                4 -> week = "星期三"
                5 -> week = "星期四"
                6 -> week = "星期五"
                7 -> week = "星期六"
            }
            return week
        }

        /**
         * 毫秒转时间
         * 时间格式: yyyy-MM-dd HH:mm:ss
         *
         * @param millisecond
         * @return
         */
        fun getDateTimeFromMillisecond(millisecond : Long?): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = Date(millisecond !!)
            return simpleDateFormat.format(date)
        }

        /**
         * 将时间转化成毫秒
         * 时间格式: yyyy-MM-dd HH:mm:ss
         *
         * @param time
         * @return
         */
        fun timeStrToSecond(time : String?): Long {
            try {
                val format =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                return format.parse(time).time
            } catch (e : Exception) {
            }
            return -1L
        }

        /**
         * 将日期转化成毫秒
         * 时间格式: yyyy-MM-dd HH:mm:ss
         *
         * @param time
         * @return
         */
        fun dateStrToSecond(time : String? = ""): Long {
            var date: String? = time
            if (StringUtil.isEmpty(date)) {
                date = curDate
            }
            try {
                val format = SimpleDateFormat("yyyy-MM-dd")
                return format.parse(date).time
            } catch (e : Exception) {
            }
            return -1L
        }

        //yyyyMMdd 转 yyyy-MM-dd
        fun dateConversion(date : String?): String {
            if (StringUtil.isEmpty(date)) return ""
            var parse: Date?
            var dateString = ""
            try {
                parse = SimpleDateFormat("yyyyMMdd").parse(date)
                dateString = SimpleDateFormat("yyyy-MM-dd").format(parse)
            } catch (e : ParseException) {
                dateString = date!!
                LogUtil.logD("日期格式化异常" + e.message)
                return dateString
            }
            return dateString
        }

        //yyyy-MM-dd 转 yyyy年MM月dd日
        fun dateToDateText(date : String?): String {
            if (StringUtil.isEmpty(date)) return ""
            var parse: Date?
            var dateString = ""
            try {
                parse = SimpleDateFormat("yyyy-MM-dd").parse(date)
                dateString = SimpleDateFormat("yyyy年MM月-dd日").format(parse)
            } catch (e : ParseException) {
                dateString = date!!
                LogUtil.logD("日期格式化异常" + e.message)
                return dateString
            }
            return dateString
        }

        //日期比较   simpleDateFormat yyyy-MM-dd
        fun compareDate(startDate : String? , endDate : String? , simpleDateFormat : String): Boolean {
            val simpleDateFormat = SimpleDateFormat(simpleDateFormat)
            val startDate = simpleDateFormat.parse(startDate)
            val thisDate = simpleDateFormat.parse(endDate)
            if (startDate.after(thisDate)) {
                return true
            }
            return false
        }

        /**
         * 日期不足10补零
         * @param date
         * @return
         */
        fun stringToDate(date : String?): String? {
            var formatDate: String? = ""
            var year = ""
            var month = ""
            var day = ""
            if (!date.isNullOrEmpty()) {
                year = date.substring(0 , 4)
                if (date.length != 8) {
                    if (date.length == 6) {
                        month = "0" + date.substring(4 , 5)
                        day = "0" + date.substring(5 , 6)
                    } else if (date.length == 7) {
                        var tempMonth = "0" + date.substring(4 , 5)
                        var tempDay = "0" + date.substring(5 , 6)
                        var endDay = ("0" + date.substring(6 , 7)).toInt()
                        if (tempMonth != "1") {
                            month = "0" + date.substring(4 , 5)
                            day = date.substring(5 , 7)
                        } else {
                            if (tempDay == "0" || tempDay == "1" || tempDay == "2") {
                                var tempDay = date.substring(5 , 7).toInt()
                                if (tempDay < 10) {
                                    month = "0" + date.substring(4 , 5)
                                    day = "0" + date.substring(6 , 7)
                                } else {
                                    month = "0" + date.substring(5 , 6)
                                    day = date.substring(5 , 7)
                                }
                            }
                        }
                    }
                    formatDate = year + month + day
                } else {
                    formatDate = date
                }

            } else {
                formatDate = date
            }
            return formatDate
        }
    }

}