package com.example.ktest.utils.locale

import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump

object FontUtils {

    fun setFont(fontAddress: String){
        ViewPump.init(
            ViewPump.builder()
            .addInterceptor(CalligraphyInterceptor(
                CalligraphyConfig.Builder()
                    .setDefaultFontPath(fontAddress)
                    .setFontMapper { font -> font }
                    .build()))
            .build())
    }

}