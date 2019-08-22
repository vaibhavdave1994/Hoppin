package com.hoppin.activity.ui.authantication.otpmessage

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.Secure
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.gson.JsonObject
import com.hoppin.R
import com.hoppin.activity.ui.authantication.signup.model.SignupModel
import com.hoppin.activity.ui.tabactivity.TabActivity
import com.hoppin.base.BaseActivity
import com.hoppin.base.GetCodeDialog
import com.hoppin.helper.Utility
import com.hoppin.helper.Validation
import com.hoppin.retrofitmodule.RetrofitClient
import com.mukesh.OnOtpCompletionListener
import kotlinx.android.synthetic.main.activity_phone_varification.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.io.File
import java.util.*


//9630457331

class PhoneVarificationActivity : BaseActivity(), View.OnClickListener, SMSReceiver.OTPReceiveListener, OnOtpCompletionListener,GetCodeDialog.GetCodeDialogCallBack {


    lateinit var validation: Validation
    lateinit var utility: Utility
    private var smsBroadcast: SMSReceiver? = null
    private var c1: String? = null
    private var c2: String? = null
    private var c3: String? = null
    private var c4: String? = null
    private var c5: String? = null
    private var c6: String? = null
    private var id: String? = ""
    private var android_id: String? = ""
    private var otpcode: String? = ""
    private var email: String = ""
    private var fullname: String = ""
    private var password: String = ""
    private var phone: String = ""
    private var status: String = ""
    private var bitmap: Bitmap? = null
    private var photoUrl: String = ""
    private var imgUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_varification)
        inItView()


    }


    @SuppressLint("HardwareIds")
    fun inItView() {
        utility = Utility()
        android_id = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID)
        validation = Validation(this)
        iv_back.setOnClickListener(this)
        btn_send.setOnClickListener(this)
        tv_getcode.setOnClickListener(this)
        tv_edit.setOnClickListener(this)
        this.otp_view.setOtpCompletionListener(this)
        getIntentData()
        startSMSListener()
        smsBroadcast = SMSReceiver()
        smsBroadcast?.initOTPListener(this)
        val intentFilter = IntentFilter()
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
        this.applicationContext?.registerReceiver(smsBroadcast, intentFilter)
    }

    private fun getIntentData() {
        intent = getIntent()
        val mobile = intent.getStringExtra("mobile")
        val cc = intent.getStringExtra("cc")
        val signupModel = intent.getParcelableExtra<SignupModel>("signupdata")
        email = signupModel.email
        password = signupModel.password
        status = signupModel.stauts
        fullname = signupModel.firstName + " " + signupModel.lastName
        photoUrl = signupModel.photourl
        phone = cc + mobile
        tv_number.setText(phone)
        if (utility.checkInternetConnection(this)) {
            getVerificationData()
        } else {
            Toast.makeText(this, "Please check your network", Toast.LENGTH_LONG).show()

        }


    }

    private fun startSMSListener() {
        val client = SmsRetriever.getClient(this@PhoneVarificationActivity /* context */)
        val task = client.startSmsRetriever()
        task.addOnSuccessListener {
            // Successfully started retriever, expect broadcast intent
            Log.d(TAG, "SMS Retriever starts...")
        }

        task.addOnFailureListener {
            Log.e(TAG, "Error to start SMS Retriever")
        }
    }


    override fun onOTPReceived(otp: String) {

        Log.e("The OTP is", otp)

        c1 = otp.get(0).toString()
        c2 = otp.get(1).toString()
        c3 = otp.get(2).toString()
        c4 = otp.get(3).toString()
        c5 = otp.get(4).toString()
        c6 = otp.get(5).toString()
        otp_view.setText(String.format(c1 + c2 + c3 + c4 + c5 + c6))

    }

    override fun onOTPTimeOut() {
        Toast.makeText(this, "The OTP error ", Toast.LENGTH_SHORT).show();
    }

    override fun onClick(p0: View) {

        when (p0.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.tv_getcode -> {
                GetCodeDialog.newInstance(this).show(supportFragmentManager)
            }R.id.tv_edit -> {
                onBackPressed()
            }
            R.id.btn_send -> {
                if (utility.checkInternetConnection(this)) {
                    if (validation.checkCode(otp_view, tv_errorcode))
                        otpcode = otp_view.text.toString()
                    VerifyCodeData(otpcode!!)
                } else {
                    Toast.makeText(this, "Please check your network", Toast.LENGTH_LONG).show()

                }
            }
        }
    }


    override fun onOtpCompleted(otp: String?) {

    }


    private fun getVerificationData() {
        progress_bar.visibility = View.VISIBLE

        val jsonObject = JsonObject()
        try {
            jsonObject.addProperty("phoneNumber", phone)
            jsonObject.addProperty("email", email)
            jsonObject.addProperty("deviceId", android_id)
            jsonObject.addProperty("deviceTypeId", 1)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        val call = RetrofitClient.instance.api.verificationCode(jsonObject)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: retrofit2.Response<JsonObject>) {
                try {
                    progress_bar.visibility = View.GONE
                    when (response.code()) {
                        200 -> {
                            val stresult = Objects.requireNonNull<JsonObject>(response.body())
                            id = stresult.get("id").asString
                            Log.d("response", stresult.toString())
                        }
                        400 -> {
                            val result = Objects.requireNonNull<ResponseBody>(response.errorBody())
                            Log.d("response400", result.string())
                        }
                        500 -> {
                            val result = Objects.requireNonNull<ResponseBody>(response.errorBody()).string()
                            val jsonObject1 = JSONObject(result)
                            val jsonArray = jsonObject1.getJSONArray("errors")
                            val jsonObject2 = jsonArray.getJSONObject(0)
                            val message = jsonObject2.getString("message")
                            Toast.makeText(this@PhoneVarificationActivity, message, Toast.LENGTH_LONG).show()

                        }
                        401 -> try {
                            Log.d("ResponseInvalid", Objects.requireNonNull<ResponseBody>(response.errorBody()).toString())
                        } catch (e1: Exception) {
                            e1.printStackTrace()
                        }

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("", "")
                progress_bar.visibility = View.GONE
            }
        })
    }

    private fun VerifyCodeData(otpcode: String) {
        progress_bar.visibility = View.VISIBLE
        val jsonObject = JsonObject()
        try {
            jsonObject.addProperty("id", id)
            jsonObject.addProperty("email", email)
            jsonObject.addProperty("code", otpcode)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        val call = RetrofitClient.instance.api.verifyCode(jsonObject)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: retrofit2.Response<JsonObject>) {
                progress_bar.visibility = View.GONE
                try {
                    when (response.code()) {
                        200 -> {
                            val stresult = Objects.requireNonNull<JsonObject>(response.body())
                            val status1 = stresult.get("success").asString
                            if (status1.equals("true")) {
                                signUpData()
                            } else {
                                Toast.makeText(this@PhoneVarificationActivity, "Please check your otp code.", Toast.LENGTH_LONG).show()
                            }
                        }
                        400 -> {
                            val result = Objects.requireNonNull<ResponseBody>(response.errorBody())
                            Log.d("response400", result.string())
                        }
                        401 -> try {

                            Log.d("ResponseInvalid", Objects.requireNonNull<ResponseBody>(response.errorBody()).toString())

                        } catch (e1: Exception) {
                            e1.printStackTrace()
                        }

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("", "")
                progress_bar.visibility = View.GONE
            }
        })
    }

    private fun signUpData() {

        progress_bar.visibility = View.VISIBLE
        val jsonObject = JsonObject()
        try {
            jsonObject.addProperty("phoneNumber", phone)
            jsonObject.addProperty("email", email)
            jsonObject.addProperty("password", password)
            jsonObject.addProperty("fullName", fullname)
            jsonObject.addProperty("deviceId", android_id)
            jsonObject.addProperty("deviceTypeId", 1)
            jsonObject.addProperty("hasPhotoFile", true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val call = RetrofitClient.instance.api.signup(jsonObject)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: retrofit2.Response<JsonObject>) {
                progress_bar.visibility = View.GONE
                try {
                    when (response.code()) {
                        200 -> {
                            val result = Objects.requireNonNull<JsonObject>(response.body())
                            val jsonArray = result.getAsJsonArray("errors")
                            val jsonObject2 = jsonArray.get(0).asJsonObject
                            val message = jsonObject2.get("message").asString
                            Toast.makeText(this@PhoneVarificationActivity, message, Toast.LENGTH_LONG).show()

                        }
                        201 -> {
                            val stresult = Objects.requireNonNull<JsonObject>(response.body())
                            val putPhotoUrl = stresult.get("putPhotoUrl").asString
                            Log.d("response", stresult.toString())
                            if (status.equals("true")) {
                                uploadphotoData(putPhotoUrl)

                            } else {
                                intent = Intent(this@PhoneVarificationActivity, TabActivity::class.java)
                                startActivity(intent)

                            }

                        }
                        400 -> {
                            val result = Objects.requireNonNull<ResponseBody>(response.errorBody())
                            Log.d("response400", result.string())
                        }
                        401 -> try {
                            Log.d("ResponseInvalid", Objects.requireNonNull<ResponseBody>(response.errorBody()).toString())
                        } catch (e1: Exception) {
                            e1.printStackTrace()
                        }

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("", "")
                progress_bar.visibility = View.GONE
            }
        })
    }


    private fun uploadphotoData(putPhotoUrl: String) {
        progress_bar.visibility = View.VISIBLE
        if (photoUrl.isNotEmpty()) {
            var fileToUpload1: MultipartBody.Part? = null

            val file = File(photoUrl)

            if (true) {
                val mFile1 = RequestBody.create(MediaType.parse("image/*"), file)
                fileToUpload1 = MultipartBody.Part.createFormData("profileImage", file.getName(), mFile1)
            }

            val call = RetrofitClient.instance.api.uploadImageToAWS(putPhotoUrl, fileToUpload1!!)
            call.enqueue(object : Callback<ResponseBody> {
                @SuppressLint("NewApi")
                override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                    progress_bar.visibility = View.GONE
                    try {
                        when (response.code()) {
                            200 -> {
                                intent = Intent(this@PhoneVarificationActivity, TabActivity::class.java)
                                startActivity(intent)

                            }
                            400 -> {
                                val result = Objects.requireNonNull<ResponseBody>(response.errorBody()).string()
                                Log.d("response400", result)
                            }
                            401 -> try {
                                Log.d("ResponseInvalid", Objects.requireNonNull<ResponseBody>(response.errorBody()).string())
                            } catch (e1: Exception) {
                                e1.printStackTrace()
                            }

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    progress_bar.visibility = View.GONE
                }
            })
        }
    }

    override fun textcode(type: String) {
        if (type.equals("send")) {
            if (utility.checkInternetConnection(this)) {
                getVerificationData()
            } else {
                Toast.makeText(this, "Please check your network", Toast.LENGTH_LONG).show()

            }

        } else if (type.equals("edit")) {


        finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}



