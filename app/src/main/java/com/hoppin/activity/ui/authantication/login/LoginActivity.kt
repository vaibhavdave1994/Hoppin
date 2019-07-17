package com.hoppin.activity.ui.authantication.login

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.support.design.widget.CheckableImageButton
import android.support.v4.content.ContextCompat
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.WindowManager
import com.hoppin.R
import com.hoppin.activity.ui.authantication.forgetpassword.ForgetPasswordActivity
import com.hoppin.activity.ui.authantication.signup.SignUpActivity
import com.hoppin.activity.ui.tabactivity.TabActivity
import com.hoppin.base.BaseActivity
import com.hoppin.helper.Utility
import com.hoppin.helper.Validation
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {

    lateinit var validation: Validation
    lateinit var utility: Utility
    private var passwordToggleView: CheckableImageButton? = null
    private var remStatus: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        setContentView(R.layout.activity_login)
        inItView()
        getRememberMe()
    }

    private fun inItView() {
        validation = Validation(this)
        utility = Utility()

        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance())
        ti_Password.post(Runnable { passwordToggleView = ti_Password.findViewById(R.id.text_input_password_toggle) })
        passwordToggleView?.setColorFilter(ContextCompat.getColor(this@LoginActivity, R.color.colorDarkOrange), PorterDuff.Mode.SRC_IN)
        iv_back.setOnClickListener(this)
        tv_signup.setOnClickListener(this)
        btn_signin.setOnClickListener(this)
        ll_remberme.setOnClickListener(this)
        tv_forgetpassword.setOnClickListener(this)
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

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_back -> {
                onBackPressed()
            }

            R.id.tv_signup -> {
                intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.btn_signin -> {


                    if (validation.isEmailValid(et_email)
                            && validation.isPasswordValid(et_password)) {

                        intent = Intent(this@LoginActivity, TabActivity::class.java)
                        startActivity(intent)
                        finish()


                }

            }

            R.id.tv_forgetpassword -> {
                intent = Intent(this@LoginActivity, ForgetPasswordActivity::class.java)
                startActivity(intent)
            }

            R.id.ll_remberme -> {

                if (remStatus) {
                    remStatus = false
                    iv_remember.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_inactive_check_box_ico))
                    getDataManager().setRememberMe("", "")

                } else {
                    remStatus = true
                    iv_remember.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_active_check_box_ico))
                    getDataManager().setRememberMe(et_email.text.toString(), et_password.text.toString())
                }
            }

        }
    }


}
