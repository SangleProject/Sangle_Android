package org.three.minutes.util

import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object AppVersionChecker {
    fun getMarketVersion(packageName: String): String? {
        try {
            val doc = Jsoup.connect(
                "https://play.google.com/store/apps/details?id=${packageName}"
            ).get()

            val version: Elements = doc.select(".content")

            for (element in version) {
                if (element.attr("itemprop").equals("softwareVersion"))
                    return element.text().trim()
            }

        } catch (e : IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun getMarketVersionFast(packageName: String): String? {
        var mData = ""
        var mVer: String? = null

        try {
            val mUrl = URL(
                "https://play.google.com/store/apps/details?id="
                        + packageName
            )
            val mConnection: HttpURLConnection = mUrl.openConnection() as HttpURLConnection ?: return null
            mConnection.connectTimeout = 5000
            mConnection.useCaches = false
            mConnection.doOutput = true
            if (mConnection.responseCode == HttpURLConnection.HTTP_OK) {
                val mReader = BufferedReader(
                    InputStreamReader(mConnection.inputStream)
                )
                while (true) {
                    val line: String = mReader.readLine() ?: break
                    mData += line
                }
                mReader.close()
            }
            mConnection.disconnect()
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }

        val startToken = "softwareVersion\">"
        val endToken = "<"
        val index = mData.indexOf(startToken)

        if (index == -1) {
            mVer = null
        } else {
            mVer = mData.substring(
                index + startToken.length, (index
                        + startToken.length + 100)
            )
            mVer = mVer.substring(0, mVer.indexOf(endToken)).trim { it <= ' ' }
        }

        return mVer
    }
}