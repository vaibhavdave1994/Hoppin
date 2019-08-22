package com.hoppin.activity.ui.authantication.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.CheckableImageButton
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import com.hoppin.R
import com.hoppin.activity.ui.authantication.forgetpassword.ForgetPasswordActivity
import com.hoppin.activity.ui.authantication.signup.SignUpActivity
import com.hoppin.activity.ui.tabactivity.TabActivity
import com.hoppin.base.BaseActivity
import com.hoppin.data.model.LoginInfo
import com.hoppin.helper.Utility
import com.hoppin.helper.Validation
import com.hoppin.retrofitmodule.RetrofitClient
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.util.*
import java.util.regex.Pattern

class LoginActivity : BaseActivity(), View.OnClickListener {

    lateinit var validation: Validation
    lateinit var utility: Utility
    private var passwordToggleView: CheckableImageButton? = null
    private var remStatus: Boolean = false
    private var passwordStatus: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        setContentView(R.layout.activity_login)
        inItView()
        getRememberMe()
        ti_email.isErrorEnabled = false
        ti_Password.isErrorEnabled = false

    }

    @SuppressLint("NewApi")
    private fun inItView() {

        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorWhite));
        validation = Validation(this)
        utility = Utility()

        iv_back.setOnClickListener(this)
        tv_signup.setOnClickListener(this)
        btn_signin.setOnClickListener(this)
        ll_remberme.setOnClickListener(this)
        tv_forgetpassword.setOnClickListener(this)
        iv_eyepassword.setOnClickListener(this)
        et_email.addTextChangedListener(watcherClass_email)
        et_password.addTextChangedListener(watcherClass_password)
    }

    private fun getRememberMe() {
        if (!getDataManager().getRememberMe().get(0)!!.isEmpty()) {
            et_email.setText(getDataManager().getRememberMe().get(0))
            et_password.setText(getDataManager().getRememberMe().get(1))
            iv_remember.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_active_check_box_ico))
        } else {
            et_email.setText("")
            et_password.setText("")
            iv_remember.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_inactive_check_box_ico))

        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.iv_eyepassword -> {

                if (!passwordStatus) {
                    passwordStatus = true
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    iv_eyepassword.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_close_eye))
                } else {
                    passwordStatus = false
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    iv_eyepassword.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_open_eyes_ico))
                }
            }

            R.id.tv_signup -> {
                intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.btn_signin -> {
                if (utility.checkInternetConnection(this)) {
                    if (validation.checkEmail(et_email, ti_email) && validation.checkPassword(et_password, ti_Password)) {
                        if (remStatus) {
                            remStatus = false
                            iv_remember.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_inactive_check_box_ico))
                            getDataManager().setRememberMe("", "")

                        } else {
                            remStatus = true
                            iv_remember.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_active_check_box_ico))
                            getDataManager().setRememberMe(et_email.text.toString(), et_password.text.toString())
                        }

                        signinData()

                    }
                } else {
                    Toast.makeText(this, "Please check your network", Toast.LENGTH_LONG).show()

                }
            }

            R.id.tv_forgetpassword -> {
                intent = Intent(this@LoginActivity, ForgetPasswordActivity::class.java)
                startActivity(intent)
            }


        }
    }


    private val watcherClass_email = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun afterTextChanged(editable: Editable) {
            if (editable.isNotEmpty()) {

                if (!Pattern.compile(validation.EMAIL_STRING).matcher(et_email.text.toString()).matches() && !et_email.getText().toString().isEmpty()) {
                    ti_email.isErrorEnabled = true
                    ti_email.setError(getString(R.string.emailInvalidError))
                } else {
                    ti_email.isErrorEnabled = false
                }
            } else {

                ti_email.setError(getString(R.string.emailEmptyError))
            }
        }
    }

    private val watcherClass_password = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun afterTextChanged(editable: Editable) {

            if (editable.isNotEmpty()) {
                ti_Password.isErrorEnabled = false
            } else {
                ti_Password.isErrorEnabled = true
                ti_Password.setError(getString(R.string.passEmptyError))
            }
        }
    }

    private fun signinData() {
        progress_bar.visibility = View.VISIBLE
        val call = RetrofitClient.instance.api.signin("password", et_email.text.toString(), et_password.text.toString())
        call.enqueue(object : Callback<ResponseBody> {
            @SuppressLint("NewApi")
            override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                try {
                    progress_bar.visibility = View.GONE
                    when (response.code()) {

                        200 -> {
                            val stresult = Objects.requireNonNull<ResponseBody>(response.body()).string()
                            val jsonObject = JSONObject(stresult)
                            val refresh_token = jsonObject.optString("refresh_token")
                            val access_token = jsonObject.optString("access_token")
                            val loginInfo = LoginInfo(access_token, refresh_token)
                            getDataManager().setLoginInfo(loginInfo)
                            Log.d("response", stresult.toString())
                            intent = Intent(this@LoginActivity, TabActivity::class.java)
                            startActivity(intent)
                        }
                        400 -> {
                            val result = Objects.requireNonNull<ResponseBody>(response.errorBody()).string()
                            val jsonObject = JSONObject(result)
                            val jsonArray = jsonObject.getJSONArray("errors")
                            val jsonObject1 = jsonArray.getJSONObject(0)
                            val message = jsonObject1.getString("code")
                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()


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
