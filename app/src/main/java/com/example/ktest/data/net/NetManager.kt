package com.example.ktest.data.net

import android.content.Context
import com.example.ktest.R
    import com.example.ktest.data.models.PersonModel
import com.example.ktest.utils.common.AppConstants
import com.example.ktest.utils.common.GeneralUtils
import com.example.ktest.utils.extensions.logE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetManager(private val context: Context, private val api: ApiClient) {

    private fun <T> doNetworkRequest(requestCall: Call<T>, restResponse: RestCallback<T>) {
        if (!GeneralUtils.isNetworkAvailable(context)) {
            restResponse.onFailureData(context.getString(R.string.network_error_no_internet))
            return
        }

        requestCall.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.code() == AppConstants.NETWORK_ERROR_CODE_UNAUTHORIZED) {
                    restResponse.onFailureData(context.getString(R.string.network_error_unauthorized))
                }

                when {
                    response.isSuccessful -> {
                        restResponse.onResponseData(response.body()!!)
                    }
                    response.errorBody() != null -> {
                        when {
                            response.code() == AppConstants.NETWORK_ERROR_CODE_TOO_MANY_REQUESTS -> {
                                restResponse.onFailureData(context.getString(R.string.network_error_too_many_request))
                            }
                            response.code() == AppConstants.NETWORK_ERROR_CODE_NOT_FOUND -> {
                                restResponse.onFailureData(context.getString(R.string.network_error_not_found))
                            }
                            else -> {
                                restResponse.onFailureData(response.errorBody()!!.string())
                            }
                        }

                    }
                    else -> {
                        restResponse.onFailureData(context.getString(R.string.unexpected_error_occurred))
                    }

                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                logE("ApiRequestError : " + t.localizedMessage)
                restResponse.onFailureData(t.message!!)
            }
        })

    }

    //--------------------------------------------------------------------------------

    fun getPeopleOnline(callback: RestCallback<List<PersonModel>>) = doNetworkRequest(api.getPeopleDetails(),callback)
}