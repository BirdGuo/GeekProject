package com.guoxw.geekproject.screenrecorder

import android.media.MediaCodecInfo
import android.media.MediaCodecList
import android.os.AsyncTask
import android.util.SparseArray
import java.lang.reflect.Modifier

/**
 * @auther guoxw
 * @date 2018/4/3 0003
 * @package com.guoxw.geekproject.screenrecorder
 * @desciption
 */
object MediaUtils {

    interface Callback {
        fun onResult(infos: Array<MediaCodecInfo>)
    }

    class EncoderFinder(val func: Callback) : AsyncTask<String, Void, Array<MediaCodecInfo>>() {


        override fun doInBackground(vararg params: String?): Array<MediaCodecInfo> {
            return findEncoderByType(params[0]!!)
        }

        override fun onPostExecute(result: Array<MediaCodecInfo>?) {
//            super.onPostExecute(result)
            func.onResult(result!!)
        }
    }

    fun findEncodersByTypeAsync(mineType: String, callback: Callback) {
        EncoderFinder(callback).execute(mineType)
    }

    /**
     * Find an encoder supported specified MIME type
     *
     * @return Returns empty array if not found any encoder supported specified MIME type
     */

    fun findEncoderByType(mimeType: String): Array<MediaCodecInfo> {
        val codecList = MediaCodecList(MediaCodecList.ALL_CODECS)
        val infos = ArrayList<MediaCodecInfo>()

        //不能用continue
//        codecList.codecInfos.asSequence().forEach {
//            if (!it.isEncoder) continue
//
//        }

        for (info in codecList.codecInfos) {
            if (!info.isEncoder) {
                continue
            }

            try {
                var cap: MediaCodecInfo.CodecCapabilities = info.getCapabilitiesForType(mimeType)
                        ?: continue
            } catch (e: Exception) {
                continue
            }
            infos.add(info)
        }
        return infos.toArray(arrayOfNulls<MediaCodecInfo>(infos.size))
    }

    val sAACProfiles = SparseArray<String>()
    var sAVCProfiles = SparseArray<String>()
    var sAVCLevels = SparseArray<String>()

    /**
     * @param avcProfileLevel AVC CodecProfileLevel
     */
    fun avcProfileLevelToString(avcProfileLevel: MediaCodecInfo.CodecProfileLevel): String {

        if (sAVCProfiles.size() == 0 || sAVCLevels.size() == 0) {
            initProfileLevels()
        }
        var profile: String? = null
        var level: String? = null
        var i: Int = sAACProfiles.indexOfKey(avcProfileLevel.profile)
        if (i >= 0) {
            profile = sAVCProfiles.valueAt(i)
        }

        i = sAVCLevels.indexOfKey(avcProfileLevel.level)
        if (i >= 0) {
            level = sAVCLevels.valueAt(i)
        }

        if (profile == null) {
            profile = avcProfileLevel.profile.toString()
        }

        if (level == null) {
            level = avcProfileLevel.level.toString()
        }

        return profile + "-" + level

    }

    fun initProfileLevels() {
        val fields = MediaCodecInfo.CodecProfileLevel::class.java.fields
        for (field in fields) {
            if ((field.modifiers and (Modifier.STATIC or Modifier.FINAL)) == 0) {
                continue
            }
            val name = field.name
            var target: SparseArray<String>
            if (name.startsWith("AVCProfile")) {
                target = sAVCProfiles
            } else if (name.startsWith("AVCLevel")) {
                target = sAVCLevels
            } else if (name.startsWith("AACObject")) {
                target = sAACProfiles
            } else {
                continue
            }

            try {
                target.put(field.getInt(null), name)
            } catch (e: Exception) {
            }
        }
    }

    fun aacProfiles(): Array<String?> {

        if (sAACProfiles.size() == 0) {
            initProfileLevels()
        }
        var profiles = arrayOfNulls<String>(sAACProfiles.size())

        (0 until sAACProfiles.size()).forEach { i -> profiles[i] = sAACProfiles.valueAt(i) }

        return profiles

    }

    fun toProfileLevel(str: String): MediaCodecInfo.CodecProfileLevel? {
        if (sAVCProfiles.size() == 0 || sAVCLevels.size() == 0 || sAACProfiles.size() == 0) {
            initProfileLevels()
        }
        var profile: String = str
        var level: String

        //从第一个字符开始做匹配
        var i = str.indexOf("-")

        if (i > 0) {
            //左闭右开
            profile = str.substring(0, i)
            level = str.substring(i + 1)
        }

        val res = MediaCodecInfo.CodecProfileLevel()
        if (profile.startsWith("AVC")) {
            res.profile = keyofValue(sAVCProfiles, profile)
        } else if (profile.startsWith("AAC")) {
            res.profile = keyofValue(sAACProfiles, profile)
        } else {
            try {
                res.profile = profile.toInt()
            } catch (e: Exception) {
                return null
            }
        }
        return if (res.profile > 0 && res.level >= 0) res else null
    }

    fun <T> keyofValue(array: SparseArray<T>, value: T): Int {
        var size: Int = array.size()
        (0 until size).forEach {
            var t: T = array.valueAt(it)
            if (t == value || t!! == value) {
                return array.keyAt(it)
            }
        }
        return -1
    }

    var sColorFormat = SparseArray<String>()

    fun toHumanReadbable(colorFormat: Int): String {
        if (sColorFormat.size() == 0) {
            initColorFormatFields()
        }

        var i = sColorFormat.indexOfKey(colorFormat)
        if (i >= 0) return sColorFormat.valueAt(i)
        return "0x".plus(colorFormat.toString())
    }

    fun toColorFormat(str: String): Int {

        if (sColorFormat.size() == 0) {
            initColorFormatFields()
        }

        var color = keyofValue(sColorFormat, str)
        if (color > 0) return color

        if (str.startsWith("0x")) {
            return str.substring(2).toInt(16)
        }

        return 0
    }

    fun initColorFormatFields() {

        var fields = MediaCodecInfo.CodecCapabilities::class.java.fields

        for (field in fields) {
            if ((field.modifiers and (Modifier.STATIC or Modifier.FINAL)) == 0) {
                continue
            }

            var name = field.name
            if (name.startsWith("COLOR_")) {
                try {
                    var value = field.getInt(null)
                    sColorFormat.put(value, name)
                } catch (e: Exception) {
                }
            }

        }

    }

}