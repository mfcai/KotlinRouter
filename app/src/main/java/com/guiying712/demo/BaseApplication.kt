package com.guiying712.demo

import android.app.Application
import com.guiying712.annotations.Router
import com.guiying712.router.ARouter

class BaseApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
    }
}