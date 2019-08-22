package com.hoppin.activity.ui.authantication.signup

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.support.design.widget.CheckableImageButton
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.doviesfitness.utils.ImageRotator
import com.hoppin.R
import com.hoppin.activity.countrycodepicker.CountrySelectionActivity
import com.hoppin.activity.ui.authantication.login.LoginActivity
import com.hoppin.activity.ui.authantication.otpmessage.PhoneVarificationActivity
import com.hoppin.activity.ui.authantication.signup.model.SignupModel
import com.hoppin.base.BaseActivity
import com.hoppin.base.Constant
import com.hoppin.helper.Alert
import com.hoppin.helper.Utility
import com.hoppin.helper.Utility.Companion.SECOND_ACTIVITY_REQUEST_CODE
import com.hoppin.helper.Validation
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.io.File
import java.util.*
import java.util.regex.Pattern

class SignUpActivity : BaseActivity(), View.OnClickListener, View.OnFocusChangeListener {


    private var passwordToggleView: CheckableImageButton? = null
    private var userImageFile: File? = null
    lateinit var validation: Validation
    lateinit var utility: Utility
    lateinit var alert: Alert
    private var myPhoneCountryCode: String = ""
    private var status: String = "true"
    private var photoUrl: String = ""
    private var passwordStatus: Boolean = false
    lateinit var signupModel: SignupModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
        setContentView(R.layout.activity_sign_up)
        inItView()
        ti_email.isErrorEnabled = false
        ti_Password.isErrorEnabled = false
        ti_firstname.isErrorEnabled = false
        ti_lastname.isErrorEnabled = false
    }

    @SuppressLint("NewApi")
    private fun inItView() {
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorWhite));
        validation = Validation(this)
        utility = Utility()
        alert = Alert()


        iv_back.setOnClickListener(this)
        tv_signin.setOnClickListener(this)
        rl_profile.setOnClickListener(this)
        et_password.setOnFocusChangeListener(this)
        et_phonenumber.setOnFocusChangeListener(this)
        btn_signup.setOnClickListener(this)
        ll_countrycode.setOnClickListener(this)
        iv_eyepassword.setOnClickListener(this)
        iv_info.setOnClickListener(this)
        et_firstname.addTextChangedListener(watcherClass_FirstName)
        et_lastname.addTextChangedListener(watcherClass_LastName)
        et_email.addTextChangedListener(watcherClass_Email)
        et_password.addTextChangedListener(watcherClass_Password)
        et_phonenumber.addTextChangedListener(watcherClass_Mobile)

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

                if (utility.checkCameraPermission(this@SignUpActivity)) {
                    getImagePickerDialog()
                }

            }
            R.id.iv_info -> {
                alert.whyProvideAlert(this)
            }
            R.id.ll_countrycode -> {
                intent = Intent(this, CountrySelectionActivity::class.java)
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE)
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

            R.id.btn_signup -> {
                if (utility.checkInternetConnection(this)) {
                    if (validation.checkFirstName(et_firstname, ti_firstname) && validation.checkLastName(et_lastname, ti_lastname)
                            && validation.checkMobile(et_phonenumber, view_mob, tv_moberror)
                            && validation.checkEmail(et_email, ti_email) && validation.checkPassword1(et_password, ti_Password)) {

                        if (tmpUri == null) {
                            tmpUri = Uri.parse("content://media/external/images/media/6210")
                            status = "false"
                        }
                        signupModel = SignupModel(et_firstname.text.toString()
                                , et_lastname.text.toString()
                                , et_email.text.toString()
                                , et_phonenumber.text.toString()
                                , et_password.text.toString()
                                , status
                                , photoUrl)
                        intent = Intent(this@SignUpActivity, PhoneVarificationActivity::class.java)
                        intent.putExtra("mobile", et_phonenumber.text.toString())
                        intent.putExtra("cc", tv_countrycode.text.toString())
                        intent.putExtra("signupdata", signupModel)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this, "Please check your network", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onFocusChange(p0: View, p1: Boolean) {
        when (p0.id) {
            R.id.et_phonenumber -> {
                view_mob.setBackgroundColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorGreen))
            }
            R.id.et_password -> {
                if (et_phonenumber.text.toString().isEmpty() && et_phonenumber.isFocused) {
                    tv_moberror.visibility = View.VISIBLE
                    view_mob.setBackgroundColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorRedError))
                } else {
                    tv_moberror.visibility = View.GONE
                    view_mob.setBackgroundColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorLight))

                }
            }
        }
    }

    /*Image picker code*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            tmpUri = data.data
            photoUrl = utility.getRealPathFromURI(this, tmpUri!!)
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, tmpUri)
            userImageFile = Constant.savebitmap(this, bitmap, UUID.randomUUID().toString() + ".jpg")
            Glide.with(iv_profile.context).load(bitmap).into(iv_profile)
        } else if (requestCode == Constant.CAMERA && resultCode == Activity.RESULT_OK) {
            val imageFile = getTemporalFile(this)
            val photoURI = Uri.fromFile(imageFile)
            var bm = Constant.getImageResized(this, photoURI) ///Image resizer
            val rotation = ImageRotator.getRotation(this, photoURI, true)
            bm = ImageRotator.rotate(bm, rotation)
            val profileImagefile = File(this.externalCacheDir, UUID.randomUUID().toString() + ".jpg")
            tmpUri = FileProvider.getUriForFile(
                    this, this.applicationContext.packageName + ".fileprovider", profileImagefile)
            photoUrl = utility.getRealPathFromURI(this,photoURI)

            userImageFile = Constant.savebitmap(getActivity(), bm, StringBuilder().append(UUID.randomUUID()).append(".jpg").toString())
            iv_profile.setImageBitmap(bm)
        }
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Constant.hideSoftKeyBoard(this, et_email)
                val country_flag = data!!.getIntExtra("country_flag", -1)
                myPhoneCountryCode = data.getStringExtra("country_phone_code")
                val country_code = "+$myPhoneCountryCode"
                tv_countrycode.text = country_code
                println("*******$country_flag" + "myName")
            }
        }
    }

    private val watcherClass_Email = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            if (editable.isNotEmpty()) {
                if (!Pattern.compile(validation.EMAIL_STRING).matcher(et_email.getText().toString()).matches() && !et_email.getText().toString().isEmpty()) {
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

    private val watcherClass_Password = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            if (et_password.text.toString().equals("")) {
                ti_Password.isErrorEnabled = true
                view_password.visibility = View.GONE
                ti_Password.setError(getString(R.string.passEmptyError))
            } else if (et_password.text.toString().length >= 1 && et_password.text.toString().length <= 4) {
                view_password.visibility = View.VISIBLE
                ti_Password.isErrorEnabled = true
                ti_Password.setError(getString(R.string.passlenthError))
                view_password.setBackgroundColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorRedError))
            } else if (et_password.text.toString().length > 4 && !Pattern.compile(validation.PASS_STRING).matcher(et_password.text).matches()) {

                ti_Password.isErrorEnabled = true
                ti_Password.setError(getString(R.string.passlenthError))
                view_password.visibility = View.VISIBLE
                view_password.setBackgroundColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorYellow))
            } else {
                ti_Password.isErrorEnabled = false
                view_password.setBackgroundColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorGreen))
                Handler().postDelayed({
                    view_password.visibility = View.GONE
                }, 2000)

            }
        }
    }
    private val watcherClass_FirstName = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            if (editable.isNotEmpty()) {
                ti_firstname.isErrorEnabled = false
            } else {
                ti_firstname.isErrorEnabled = true
                ti_firstname.setError(getString(R.string.firstNameEmptyError))
            }
        }
    }


    private val watcherClass_LastName = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            if (editable.isNotEmpty()) {
                ti_lastname.isErrorEnabled = false
            } else {
                ti_lastname.isErrorEnabled = true
                ti_lastname.setError(getString(R.string.lastNameEmptyError))
            }
        }
    }


    private val watcherClass_Mobile = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            if (editable.isNotEmpty()) {
                view_mob.setBackgroundColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorGreen))
                tv_moberror.visibility = View.GONE

            } else {
                view_mob.setBackgroundColor(ContextCompat.getColor(this@SignUpActivity, R.color.colorRedError))
                tv_moberror.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

}
