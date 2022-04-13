package com.spbstu.reversemarket.di

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.spbstu.reversemarket.login.data.model.SignInResponse
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        const val BASE_URL = "https://reverse-market.ru"
        const val REFRESH_ENDPOINT = "https://reverse-market.ru/auth/refresh"
        private val TAG = NetworkModule::class.simpleName
        private const val AUTHORIZATION_KEY = "Authorization"
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()


    @Provides
    @Singleton
    fun provideOkHttpClient(sharedPreferences: SharedPreferences, gson: Gson): OkHttpClient {
        val tokenInterceptor = Interceptor { chain ->
            val original = chain.request()
            val jwt: String? = sharedPreferences.getString("token", null)
            val refresh = sharedPreferences.getString("refresh", null)
            val requestBuilder = original.newBuilder()
            if (!jwt.isNullOrEmpty() && !original.url.toString().contains("auth")) {
                requestBuilder.addHeader(AUTHORIZATION_KEY, "Bearer $jwt")
            }
            val request = requestBuilder.build()
            var response = kotlin.runCatching { chain.proceed(request) }.getOrElse {
                throw IOException("Couldn't procees request. Probably no Internet connection")
            }
            Log.i(TAG, "Processed request(no tokens)=$original, response=$response")
            if ((response.code == 401 || response.code == 403) && !original.url.toString()
                    .contains("auth")
            ) {
                val refreshBody = RefreshBody(refresh)
                val authRequest = request.newBuilder()
                    .post(gson.toJson(refreshBody).toRequestBody())
                    .url(REFRESH_ENDPOINT)
                    .build()

                response.close()
                val refreshTokenResponse = chain.proceed(authRequest)
                if (refreshTokenResponse.code == 200) {
                    val tokens =
                        gson.fromJson(
                            refreshTokenResponse.body!!.string(),
                            SignInResponse::class.java
                        )

                    sharedPreferences.edit().putString("token", tokens.jwtToken).apply()
                    sharedPreferences.edit().putString("refresh", tokens.refreshToken).apply()
                    val currentRequest = original.newBuilder()
                        .addHeader(AUTHORIZATION_KEY, "Bearer ${tokens.jwtToken}")
                        .build()
                    Log.d(
                        TAG,
                        "NetworkModule: Tokens refreshed for $authRequest response=$refreshTokenResponse new_tokens=$tokens"
                    )
                    refreshTokenResponse.close()
                    response = chain.proceed(currentRequest)
                } else if (refreshTokenResponse.code == 401) {
                    Log.d(TAG, "NetworkModule: Refresh token died response=$refreshTokenResponse")
                    EventBus.getDefault().post(AuthEvent())
                }
            }
            response
        }
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .callTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
}