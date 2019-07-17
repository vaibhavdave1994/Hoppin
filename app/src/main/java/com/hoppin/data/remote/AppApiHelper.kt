package com.hoppin.data.remote



class AppApiHelper: ApiHelper {
    companion object{
        private val instance = AppApiHelper()

        fun getAppApiHelper(): AppApiHelper {
            return instance
        }
    }


   /* override fun doSignup(param: HashMap<String, String>): ANRequest<out ANRequest<*>>? {
        return  AndroidNetworking.upload(Webservice.USER_SIGNUP_API)
            .addMultipartParameter(param)
            .setPriority(Priority.HIGH)
            .build()
    }




    override fun singleJobCreate(param: HashMap<String, String>): ANRequest<out ANRequest<*>>? {
        return AndroidNetworking.post(Webservice.CREATE_JOB_API)
            .addBodyParameter(param)
            .setPriority(Priority.MEDIUM)
            .build()
    }

    override fun skills(): ANRequest<out ANRequest<*>>? {
        return AndroidNetworking.get(Webservice.CATEGORY_LIST_API)
            .setPriority(Priority.MEDIUM)
            .build()
    }*/

}