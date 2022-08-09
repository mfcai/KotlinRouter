package com.guiying712.router

import android.app.Application
import android.content.Context

object ARouter {

    private var handler :RouterAnnotationHandler = DefaultRouterAnnotationHandler()

    fun init(app:Application) {
        var classNames=ClassUtitls.getFileNameByPackageName(app)
        classNames.forEach {
           var clz:Class<*> = Class.forName(it)
            if (RouterAnnotationInit::class.java.isAssignableFrom(clz)){
                val routerAnnotationInit = clz.newInstance() as RouterAnnotationInit
                routerAnnotationInit.init(handler)
            }
        }
    }

    fun setAnnotationHandler(annotationHandler: RouterAnnotationHandler) {
        handler = annotationHandler
    }

    fun startUrl(context: Context, url :String) {
        handler.startUrl(context, url)
    }


}