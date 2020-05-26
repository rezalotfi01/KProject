package com.example.ktest.di


import android.content.Context
import android.os.Environment
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.example.ktest.App
import com.example.ktest.data.db.DatabaseManager
import com.example.ktest.data.models.MyObjectBox
import com.example.ktest.data.net.ApiClient
import com.example.ktest.data.sharedpref.SharedPreferencesManager
import com.example.ktest.ui.activities.language.LanguageRepository
import com.example.ktest.ui.activities.splash.SplashRepository
import com.example.ktest.ui.fragments.matrix.MatrixRepository
import com.example.ktest.ui.fragments.people.PeopleRepository
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val applicationModule = module {

    //Provide Application class instance
    single { App.instance }

    //Provide context
    single { get<App>().applicationContext }

    //Provide resources
    single {
        val context = get<Context>()
        context.resources
    }

}


val reactiveModule = module {
    //Provide CompositeDisposable
    factory { CompositeDisposable() }
}

private const val NAME_TOKEN = "token"
private const val NAME_CACHED = "cached"
private const val NAME_NON_CACHED = "non_cached"
private const val NAME_API_URL = "API_URL"
private const val NAME_LOGGING_INTERCEPTOR = "loggingInterceptor"
private const val NAME_NORMAL_INTERCEPTOR = "normalInterceptor"

val networkModule = module {
    //Provide TokenSign
    factory(StringQualifier(NAME_TOKEN)) {
//        "Bearer " + ""
        "XXXYYYYZZZZSSSSHSHHSKSHSFALKSFJASKHGASLG"
    }

    //Provide HttpLoggingInterceptor
    single(StringQualifier(NAME_LOGGING_INTERCEPTOR)) {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        logging
    }

    //Provide OkHTTP Interceptor
    single(StringQualifier(NAME_NORMAL_INTERCEPTOR)){
        Interceptor {
            val request = it.request().newBuilder()
                .addHeader("Authorization", get(StringQualifier(NAME_TOKEN)))
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build()
            it.proceed(request)
        }
    }


    //Provide OkHTTPClient With cache
    single(StringQualifier(NAME_CACHED)) {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>(StringQualifier(NAME_NORMAL_INTERCEPTOR)))
            .addInterceptor(get<HttpLoggingInterceptor>(StringQualifier(NAME_LOGGING_INTERCEPTOR)))
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .cache(Cache(Environment.getDownloadCacheDirectory(), 10 * 1024 * 1024))
            .build()
    }

    //Provide OkHTTPClient Without cache
    single(StringQualifier(NAME_NON_CACHED)) {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>(StringQualifier(NAME_NORMAL_INTERCEPTOR)))
            .addInterceptor(get<Interceptor>(StringQualifier(NAME_LOGGING_INTERCEPTOR)))
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    //Provide Moshi
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    //Provide Retrofit.Builder
    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>(StringQualifier(NAME_CACHED)))
            .addConverterFactory(MoshiConverterFactory.create(get<Moshi>()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    //Provide API URL
    single (StringQualifier(NAME_API_URL)) {
        "http://karafsapp.com/"
    }

    //Provide API Service
    single {
        get<Retrofit.Builder>().baseUrl(get<String>(StringQualifier(NAME_API_URL)))
            .build()
            .create(ApiClient::class.java)
    }
}



val databaseModule = module {
    //Provide Database store
    single {
        MyObjectBox.builder()
            .androidContext(get<Context>())
            .build()
    }

    single {
        DatabaseManager(get())
    }
}


val sharedPreferencesModule = module {
    //Provide System SharedPreferences
    single {
        val context = get<Context>()
        context.getSharedPreferences(context.packageName,Context.MODE_PRIVATE)
    }

    //Provide SharedPreferencesManager
    single {
        SharedPreferencesManager(get(),get())
    }
}

val repositoryModule = module {
    single {
        MatrixRepository()
    }

    single {
        PeopleRepository()
    }

    single {
        LanguageRepository()
    }

    single {
        SplashRepository()
    }
}