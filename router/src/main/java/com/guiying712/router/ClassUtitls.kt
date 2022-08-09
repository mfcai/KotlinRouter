package com.guiying712.router

import android.app.Application
import android.content.Context
import com.guiying712.router.ClassUtitls
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import dalvik.system.DexFile
import java.io.IOException
import java.lang.Exception
import java.util.*

object ClassUtitls {
    fun getFileNameByPackageName(context: Application): Set<String> {
        val classNames: MutableSet<String> = HashSet()
        val paths = getSourcePaths(context)
        for (path in paths) {
            var dexFile: DexFile? = null
            try {
                dexFile = DexFile(path)
                val dexEntries = dexFile.entries()
                while (dexEntries.hasMoreElements()) {
                    val className = dexEntries.nextElement()
                    if (className.startsWith("com.guiying712.router.generated")) {
                        classNames.add(className)
                    }
                }
            } catch (ex: Exception) {
                Log.e("tag", ex.toString())
            } finally {
                if (dexFile != null) {
                    try {
                        dexFile.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        return classNames
    }

    private fun getSourcePaths(context: Context): List<String> {
        var appInfo: ApplicationInfo? = null
        val sourcePaths: MutableList<String> = ArrayList()
        try {
            appInfo = context.packageManager.getApplicationInfo(context.packageName, 0)
            sourcePaths.add(appInfo.sourceDir)
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                if (null != appInfo.splitSourceDirs) {
                    sourcePaths.addAll(Arrays.asList(*appInfo.splitSourceDirs))
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return sourcePaths
    }
}