package com.hoppin.activity.ui.authantication.signup

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.CheckableImageButton
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.doviesfitness.utils.ImageRotator
import com.hoppin.R
import com.hoppin.activity.ui.authantication.login.LoginActivity
import com.hoppin.base.BaseActivity
import com.hoppin.base.Constant
import com.hoppin.helper.Utility
import com.hoppin.helper.Validation
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.io.File
import java.util.*

class SignUpActivity : BaseActivity(), View.OnClickListener {

    private var passwordToggleView: CheckableImageButton? = null
    private var userImageFile: File? = null
    lateinit var validation: Validation
    lateinit var utility: Utility
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
        setContentView(R.layout.activity_sign_up)
        inItView()
    }

    private fun inItView() {
        validation = Validation(this)
        utility = Utility()

        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance())
        ti_Password.post(Runnable { passwordToggleView = ti_Password.findViewById(R.id.text_input_password_toggle) })
        passwordToggleView?.setColorFilter(ContextCompat.getColor(this@SignUpActivity, R.color.colorDarkOrange), PorterDuff.Mode.SRC_IN)

        iv_back.setOnClickListener(this)
        tv_signin.setOnClickListener(this)
        rl_profile.setOnClickListener(this)

    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.tv_signin -> {
                intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.rl_profile -> {
                getImagePickerDialog()
            }
            R.id.btn_signup -> {
                if (utility.checkInternetConnection(this)) {
                    if (validation.isFirstNameValid(et_firstname) && validation.isLastNameValid(et_lastname)
                            && validation.isEmailValid(et_email) && validation.isPhoneNumberValid(et_phonenumber)
                            && validation.isPasswordValid(et_password)) {

                        intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                }
            }
        }
    }

    /*Image picker code*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            tmpUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, tmpUri)
            userImageFile = Constant.savebitmap(this, bitmap, UUID.randomUUID().toString() + ".jpg")
            Glide.with(iv_profile.context).load(bitmap).into(iv_profile)
//            iv_profile.borderWidth = 0
            iv_placeholder_img.visibility = View.GONE

        } else if (requestCode == Constant.CAMERA && resultCode == Activity.RESULT_OK) {
            val imageFile = getTemporalFile(this)
            val photoURI = Uri.fromFile(imageFile)
            var bm = Constant.getImageResized(this, photoURI) ///Image resizer
            val rotation = ImageRotator.getRotation(this, photoURI, true)
            bm = ImageRotator.rotate(bm, rotation)
            val profileImagefile = File(this.externalCacheDir, UUID.randomUUID().toString() + ".jpg")
            tmpUri = FileProvider.getUriForFile(
                    this, this.applicationContext.packageName + ".fileprovider",
                    profileImagefile
            )
            userImageFile = Constant.savebitmap(getActivity(), bm, StringBuilder().append(UUID.randomUUID()).append(".jpg").toString())
            iv_placeholder_img.visibility = View.GONE
//            iv_profile.borderWidth = 0
            iv_profile.setImageBitmap(bm)
        }
    }
}
