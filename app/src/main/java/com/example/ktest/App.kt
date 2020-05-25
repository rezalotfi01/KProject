package com.example.ktest

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.example.ktest.di.appComponent
import com.example.ktest.utils.common.AppConstants
import com.example.ktest.utils.locale.FontUtils
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.QueueProcessingType
import io.fabric.sdk.android.Fabric
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        initFabric()
        initKoin()
        initTimber()
        initCalligraphy()
        initImageLoader()
    }

    //--------------------------------------

    private fun initKoin() {
        // start Koin!
        startKoin {
            //Logger Object
            AndroidLogger(Level.DEBUG)
            // modules
            modules(appComponent)
        }
    }

    private fun initTimber() = Timber.plant(Timber.DebugTree())

    private fun initFabric() {
        if (!BuildConfig.DEBUG)
            Fabric.with(this, Crashlytics())
    }

    private fun initCalligraphy() {
        FontUtils.setFont(AppConstants.FONT_ADDRESS_FA)
    }

    private fun initImageLoader() {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by the
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        val config = ImageLoaderConfiguration.Builder(this)
        config.threadPriority(Thread.NORM_PRIORITY - 2)
        config.denyCacheImageMultipleSizesInMemory()
        config.diskCacheFileNameGenerator(Md5FileNameGenerator())
        config.diskCacheSize(50 * 1024 * 1024) // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.FIFO)
        config.writeDebugLogs() // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build())
    }
}