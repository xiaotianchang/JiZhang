package com.hq.jizhang.util

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import android.widget.ImageView
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * 主要功能：用于存储缓存数据
 *
 * @Prject: common
 * @author: xtc
 * @date:
 * @Copyright: 个人版权所有
 * @Company:
 * @version: 1.0.0
 */

class SpUtils {

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private object SharedPreferencesCompat {
        private val sApplyMethod = findApplyMethod()

        /**
         * 反射查找apply的方法
         */
        private fun findApplyMethod(): Method? {
            try {
                val clz = SharedPreferences.Editor::class.java
                return clz.getMethod("apply")
            } catch (e: Exception) {
            }

            return null
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        fun apply(editor: SharedPreferences.Editor?) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor)
                    return
                }
            } catch (e: IllegalAccessException) {
            } catch (e: InvocationTargetException) {
            }

            editor?.commit()
        }
    }

    companion object {
        /**
         * 保存在手机里面的文件名
         */
        private const val FILE_NAME = "com.hq.routerbank"         //退出时需要删除数据的

        /**
         * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
         */
        fun put(context : Context?,key: String, any: Any?) {
            if (null != any) {
                val sp = context?.getSharedPreferences(FILE_NAME , Context.MODE_PRIVATE)
                val editor = sp?.edit()
                if (any is String) {
                    editor?.putString(key, any)
                } else if (any is Int) {
                    editor?.putInt(key, (any))
                } else if (any is Boolean) {
                    editor?.putBoolean(key, any)
                } else if (any is Float) {
                    editor?.putFloat(key, any)
                } else if (any is Long) {
                    editor?.putLong(key, any)
                } else {
                    //editor?.putString(key, ApiManager.goon.toJson(any))
                }
                SharedPreferencesCompat.apply(editor)
            }
        }

        /**
         * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
         */
        operator fun get(context : Context? , key : String, defaultObject : Any): Any? {
                val sp = context?.getSharedPreferences(FILE_NAME , Context.MODE_PRIVATE)
                if (defaultObject is String) {
                    return sp?.getString(key, defaultObject)
                } else if (defaultObject is Int) {
                    return sp?.getInt(key, defaultObject)
                } else if (defaultObject is Boolean) {
                    return sp?.getBoolean(key, defaultObject)
                } else if (defaultObject is Float) {
                    return sp?.getFloat(key, defaultObject)
                } else if (defaultObject is Long) {
                    return sp?.getLong(key, defaultObject)
                }
            return defaultObject

        }

        /**
         * 移除某个key值已经对应的值
         */
        fun remove(context: Context, key: String) {
            val sp = context.getSharedPreferences(FILE_NAME , Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.remove(key)
            SharedPreferencesCompat.apply(editor)
        }

        /**
         * 清除所有数据
         */
        fun clear(context: Context) {
            val sp = context.getSharedPreferences(FILE_NAME , Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.clear()
            SharedPreferencesCompat.apply(editor)
        }

        /**
         * 查询某个key是否已经存在
         */
        fun contains(context: Context, key: String): Boolean {
            val sp = context.getSharedPreferences(FILE_NAME , Context.MODE_PRIVATE)
            return sp.contains(key)
        }

        /**
         * 返回所有的键值对
         */
        fun getAll(context: Context): Map<String, *> {
            val sp = context.getSharedPreferences(FILE_NAME , Context.MODE_PRIVATE)
            return sp.all
        }

        /**
         * 保存图片到Sp
         *
         * @param context
         * @param imageView
         */
        fun putImage(context: Context, key: String, imageView: ImageView) {
            val drawable = imageView.drawable as BitmapDrawable
            val bitmap = drawable.bitmap
            // 将Bitmap压缩成字节数组输出流
            val byStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream)
            // 利用Base64将我们的字节数组输出流转换成String
            val byteArray = byStream.toByteArray()
            val imgString = java.lang.String(Base64.encodeToString(byteArray, Base64.DEFAULT))
            // 将String保存shareUtils
            put(context,key, imgString)
        }

        /**
         * 从Sp读取图片
         *
         * @param context
         * @param key
         */
        fun getImage(context : Context, key: String): Bitmap? {
            val imgString = SpUtils[context , key , ""] as String?
            if (imgString != "") {
                // 利用Base64将我们string转换
                val byteArray = Base64.decode(imgString, Base64.DEFAULT)
                val byStream = ByteArrayInputStream(byteArray)
                // 生成bitmap
                return BitmapFactory.decodeStream(byStream)
            }
            return null
        }

    }
}
