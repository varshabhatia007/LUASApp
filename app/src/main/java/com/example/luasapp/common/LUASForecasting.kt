package com.example.luasapp.common

import com.example.luasapp.BuildConfig
import com.example.luasapp.model.StopInfo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface LUASForecasting {

    companion object {
        operator fun invoke(): LUASForecasting {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(client)
                .addConverterFactory(
                    SimpleXmlConverterFactory.createNonStrict(
                        Persister(AnnotationStrategy())
                    )
                )
                .build()
                .create(LUASForecasting::class.java)
        }
    }

    @GET("get.ashx")
    suspend fun tramsForecast(
        @Query("stop") stopName: String,
        @Query("action") action: String = "forecast",
        @Query("encrypt") isEncrypt: Boolean = false
    ): StopInfo
}