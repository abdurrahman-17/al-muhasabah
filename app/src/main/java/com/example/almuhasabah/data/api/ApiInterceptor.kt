package com.example.almuhasabah.data.api
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.utils.Constants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ApiInterceptor : Interceptor {
    var sharedPreferences : SharedPreferenceImp? = null
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        sharedPreferences= SharedPreferenceImp()
        var request = chain.request()
        val builder = request.newBuilder()
        builder.header("Accept", "application/json")
        builder.header("API_VERSION", "1.0")
        builder.header("DEVICE_TYPE", "Android")
        val token = sharedPreferences?.getString(Constants.API_TOKEN)
        setAuthHeader(builder, token)
        request = builder.build()
        return chain.proceed(request)         // Sends the request (Original w/ Auth.)
    }

    private fun setAuthHeader(builder: Request.Builder, token: String?) {
        if (token != null) {
            builder.header("Authorization", String.format("Bearer %s", token))
        }
    }

}