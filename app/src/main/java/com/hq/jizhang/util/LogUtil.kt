package com.hq.jizhang.util

import android.os.Environment
import android.text.TextUtils
import android.util.Log
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by ThinkPad on 2016/9/16.
 */

class LogUtil {

    companion object {
        private var showLog = true
        private var HAHA = "haha"
        private val thread_local_formatter = object : ThreadLocal<ReusableFormatter>() {
            override fun initialValue(): ReusableFormatter {
                return ReusableFormatter()
            }
        }
        var customTagPrefix = "com.hq.routerbank"
        var customLogger: CustomLogger? = null

        private val callerStackTraceElement: StackTraceElement
            get() = Thread.currentThread().stackTrace[4]

        private val isSDAva: Boolean
            get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED || Environment.getExternalStorageDirectory()
                .exists()

        private fun generateTag(caller: StackTraceElement): String {
            var tag = "%s.%s(Line:%d)"
            var callerClazzName = caller.className
            callerClazzName = callerClazzName.substring(
                callerClazzName
                    .lastIndexOf(".") + 1
            )
            tag = String.format(
                tag, callerClazzName, caller.methodName,
                caller.lineNumber
            )
            tag = if (TextUtils.isEmpty(customTagPrefix))
                tag
            else
                "$customTagPrefix:$tag"
            return tag
        }

        fun logD(content: String) {
            if (! showLog)
                return
            val caller = callerStackTraceElement
            val tag = generateTag(caller)

            if (customLogger != null) {
                customLogger !!.d(HAHA + tag, content)
            } else {
                Log.d(HAHA + tag, content)
            }
        }

        /**
         * 打印日志到控制台（解决Android控制台丢失长日志记录）
         *
         * @param priority
         * @param tag
         * @param content
         */
        fun print(priority: Int = Log.VERBOSE, tag: String? = "haha", content: String) {
            if (showLog) {
                // 1. 测试控制台最多打印4062个字节，不同情况稍有出入（注意：这里是字节，不是字符！！）
                // 2. 字符串默认字符集编码是utf-8，它是变长编码一个字符用1~4个字节表示
                // 3. 这里字符长度小于1000，即字节长度小于4000，则直接打印，避免执行后续流程，提高性能哈
                if (content.length < 1000) {
                    Log.println(priority, tag, content)
                    return
                }

                // 一次打印的最大字节数
                val maxByteNum = 4000

                // 字符串转字节数组
                var bytes = content.toByteArray()

                // 超出范围直接打印
                if (maxByteNum >= bytes.size) {
                    Log.println(priority, tag, content)
                    return
                }

                // 分段打印计数
                var count = 1

                // 在数组范围内，则循环分段
                while (maxByteNum < bytes.size) {
                    // 按字节长度截取字符串
                    val subStr = cutStr(bytes, maxByteNum)

                    // 打印日志
                    val desc = String.format("分段打印(%s):%s", count++, subStr)
                    Log.println(priority, tag, desc)

                    // 截取出尚未打印字节数组
                    bytes = Arrays.copyOfRange(bytes, subStr!!.toByteArray().size, bytes.size)

                    // 可根据需求添加一个次数限制，避免有超长日志一直打印
                    /*if (count == 10) {
                    break;
                }*/
                }

                // 打印剩余部分
                Log.println(
                    priority,
                    tag,
                    String.format("分段打印(%s):%s", count, String(bytes))
                )
            }
        }


        /**
         * 按字节长度截取字节数组为字符串
         *
         * @param bytes
         * @param subLength
         * @return
         */
        private fun cutStr(bytes: ByteArray?, subLength: Int): String? {
            // 边界判断
            if (bytes == null || subLength < 1) {
                return null
            }

            // 超出范围直接返回
            if (subLength >= bytes.size) {
                return String(bytes)
            }

            // 复制出定长字节数组，转为字符串
            val subStr = String(Arrays.copyOf(bytes, subLength))

            // 避免末尾字符是被拆分的，这里减1使字符串保持完整
            return subStr.substring(0, subStr.length - 1)
        }

        fun longLog() {

        }

/*        fun d(content: String, tr: Throwable) {
            if (!showLog)
                return
            val caller = callerStackTraceElement
            val tag = generateTag(caller)

            if (customLogger != null) {
                customLogger!!.d(Constant.HAHA + tag, content, tr)
            } else {
              //  Log.d(Constant.HAHA + tag, content, tr)
            }
        }

        fun e(content: String?) {
            if (!showLog)
                return
            val caller = callerStackTraceElement
            val tag = generateTag(caller)

            if (customLogger != null) {
                customLogger!!.e(Constant.HAHA + tag, content+"")
            } else {
                Log.e(Constant.HAHA + tag, content)
            }
            if (isSaveLog) {
                //   point(PATH_LOG_INFO, tag, content);
            }
        }

        fun e(content: String, tr: Throwable) {
            if (!showLog)
                return
            val caller = callerStackTraceElement
            val tag = generateTag(caller)

            if (customLogger != null) {
                customLogger!!.e(Constant.HAHA + tag, content, tr)
            } else {
             //   Log.e(Constant.HAHA + tag, content, tr)
            }
        }

        fun i(content: String) {
            if (!showLog)
                return
            val caller = callerStackTraceElement
            val tag = generateTag(caller)

            if (customLogger != null) {
                customLogger!!.i(Constant.HAHA + tag, content)
            } else {
             //   Log.i(Constant.HAHA + tag, content)
            }

        }

        fun i(content: String, tr: Throwable) {
            if (!showLog)
                return
            val caller = callerStackTraceElement
            val tag = generateTag(caller)

            if (customLogger != null) {
                customLogger!!.i(Constant.HAHA + tag, content, tr)
            } else {
                Log.i(Constant.HAHA + tag, content, tr)
            }
        }

        fun v(content: String) {
            if (!showLog)
                return
            val caller = callerStackTraceElement
            val tag = generateTag(caller)

            if (customLogger != null) {
                customLogger!!.v(Constant.HAHA + tag, content)
            } else {
                Log.v(Constant.HAHA + tag, content)
            }
        }

        fun v(content: String, tr: Throwable) {
            if (!showLog)
                return
            val caller = callerStackTraceElement
            val tag = generateTag(caller)

            if (customLogger != null) {
                customLogger!!.v(Constant.HAHA + tag, content, tr)
            } else {
                Log.v(Constant.HAHA + tag, content, tr)
            }
        }

        fun w(content: String) {
            if (!showLog)
                return
            val caller = callerStackTraceElement
            val tag = generateTag(caller)

            if (customLogger != null) {
                customLogger!!.w(Constant.HAHA + tag, content)
            } else {
                Log.w(Constant.HAHA + tag, content)
            }
        }

        fun w(content: String, tr: Throwable) {
            if (!showLog)
                return
            val caller = callerStackTraceElement
            val tag = generateTag(caller)

            if (customLogger != null) {
                customLogger!!.w(Constant.HAHA + tag, content, tr)
            } else {
                Log.w(Constant.HAHA + tag, content, tr)
            }
        }

        fun w(tr: Throwable) {
            if (!showLog)
                return
            val caller = callerStackTraceElement
            val tag = generateTag(caller)

            if (customLogger != null) {
                customLogger!!.w(Constant.HAHA + tag, tr)
            } else {
                Log.w(Constant.HAHA + tag, tr)
            }
        }

        fun wtf(content: String) {
            if (!showLog)
                return
            val caller = callerStackTraceElement
            val tag = generateTag(caller)

            if (customLogger != null) {
                customLogger!!.wtf(Constant.HAHA + tag, content)
            } else {
                Log.wtf(Constant.HAHA + tag, content)
            }
        }

        fun wtf(content: String, tr: Throwable) {
            if (!showLog)
                return
            val caller = callerStackTraceElement
            val tag = generateTag(caller)

            if (customLogger != null) {
                customLogger!!.wtf(Constant.HAHA + tag, content, tr)
            } else {
                Log.wtf(Constant.HAHA + tag, content, tr)
            }
        }

        fun wtf(tr: Throwable) {
            if (!showLog)
                return
            val caller = callerStackTraceElement
            val tag = generateTag(caller)

            if (customLogger != null) {
                customLogger!!.wtf(Constant.HAHA + tag, tr)
            } else {
                Log.wtf(Constant.HAHA + tag, tr)
            }
        }*/


        fun point(path: String, tag: String, msg: String) {
            var path = path
            if (isSDAva) {
                val date = Date()
                val dateFormat = SimpleDateFormat(
                    "",
                    Locale.SIMPLIFIED_CHINESE
                )
                dateFormat.applyPattern("yyyy")
                path = path + dateFormat.format(date) + "/"
                dateFormat.applyPattern("MM")
                path += dateFormat.format(date) + "/"
                dateFormat.applyPattern("dd")
                path += dateFormat.format(date) + ".log"
                dateFormat.applyPattern("[yyyy-MM-dd HH:mm:ss]")
                val time = dateFormat.format(date)
                val file = File(path)
                if (!file.exists())
                    createDipPath(path)
                var out: BufferedWriter? = null
                try {
                    out = BufferedWriter(
                        OutputStreamWriter(
                            FileOutputStream(file, true)
                        )
                    )
                    out.write("$time $tag $msg\r\n")
                } catch (e: Exception) {
                } finally {
                    try {
                        out?.close()
                    } catch (e: IOException) {
                    }

                }
            }
        }

        private fun createDipPath(file: String) {
            val parentFile = file.substring(0, file.lastIndexOf("/"))
            val file1 = File(file)
            val parent = File(parentFile)
            if (!file1.exists()) {
                parent.mkdirs()
                try {
                    file1.createNewFile()
                } catch (e: IOException) {
                }

            }
        }

        fun format(msg: String, vararg args: Any): String {
            val formatter = thread_local_formatter.get()
            return formatter!!.format(msg, *args)
        }


        interface CustomLogger {
            fun d(tag: String, content: String)

            fun d(tag: String, content: String, tr: Throwable)

            fun e(tag: String, content: String)

            fun e(tag: String, content: String, tr: Throwable)

            fun i(tag: String, content: String)

            fun i(tag: String, content: String, tr: Throwable)

            fun v(tag: String, content: String)

            fun v(tag: String, content: String, tr: Throwable)

            fun w(tag: String, content: String)

            fun w(tag: String, content: String, tr: Throwable)

            fun w(tag: String, tr: Throwable)

            fun wtf(tag: String, content: String)

            fun wtf(tag: String, content: String, tr: Throwable)

            fun wtf(tag: String, tr: Throwable)
        }

        private class ReusableFormatter {

            private val formatter: Formatter
            private val builder: StringBuilder = StringBuilder()

            init {
                formatter = Formatter(builder)
            }

            fun format(msg: String, vararg args: Any): String {
                formatter.format(msg, *args)
                val s = builder.toString()
                builder.setLength(0)
                return s
            }
        }
    }
}
