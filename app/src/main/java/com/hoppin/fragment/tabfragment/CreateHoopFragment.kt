package com.hoppin.fragment.tabfragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v4.text.HtmlCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.doviesfitness.utils.ImageRotator
import com.hoppin.R
import com.hoppin.activity.hoopActivity.HoopDetailActivity
import com.hoppin.activity.hoopActivity.PreviousCreateHoopActivity
import com.hoppin.activity.hoopActivity.SelcetGuestActivity
import com.hoppin.activity.ui.tabactivity.TabActivity
import com.hoppin.base.BaseFragment
import com.hoppin.base.Constant
import com.hoppin.base.Constant.Companion.TAG
import com.hoppin.fragment.adapter.HoopGuestAdapter
import com.hoppin.helper.Utility
import com.hoppin.helper.Validation
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment
import kotlinx.android.synthetic.main.fragment_create_hoop.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class CreateHoopFragment : BaseFragment(), View.OnClickListener, Utility.OnDateSelectedListener, Utility.OnOptionDateSelectedListener {

    private var userImageFile: File? = null
    lateinit var utility: Utility
    lateinit var intent: Intent
    lateinit var description: String
    lateinit var option: String
    lateinit var day1: String
    lateinit var month1: String
    lateinit var year1: String
    private var totalmonth: Int? = 0
    lateinit var validation: Validation
    private var hoopGuestAdapter: HoopGuestAdapter? = null
    private var dateTimeFragment: SwitchDateTimeDialogFragment? = null
    private var dateTimeFragment1: SwitchDateTimeDialogFragment? = null
    private val TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_hoop, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as TabActivity).updateStatusBarColor("#ffffff")
        inItView()
        et_title.requestFocus();
        showKeyboard()
        adapterData()
        dateTimepicker()


    }

    fun inItView() {
        utility = Utility()
        validation = Validation(context!!)
        rl_startdate.setOnClickListener(this)
        rl_optiondate.setOnClickListener(this)
        rl_hoopprofile.setOnClickListener(this)
        btn_pasthoop.setOnClickListener(this)
        btn_create.setOnClickListener(this)
        ll_invited.setOnClickListener(this)
        et_title.addTextChangedListener(watcherClass_title)
//        et_location.addTextChangedListener(watcherClass_location)
        tv_date.addTextChangedListener(watcherClass_startdate)


        description = "<font color=\"#828282\"> " + getString(R.string.description) + "<> </font>"
        option = "<font color=\"#626262\"> <small>" + getString(R.string.option) + " <small></font>"

        et_description.hint = HtmlCompat.fromHtml(description + " " + option, 0)


    }

    fun adapterData() {

        hoopGuestAdapter = HoopGuestAdapter(context!!)
        val mLayoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.layoutManager = mLayoutManager
        //addprop_recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.adapter = hoopGuestAdapter

    }

    override fun onClick(p0: View) {

        when (p0.id) {
            R.id.rl_startdate -> {
                dateTimeFragment?.startAtCalendarView()
                dateTimeFragment?.setDefaultDateTime(Calendar.getInstance().time)
                dateTimeFragment?.show(this.childFragmentManager, TAG_DATETIME_FRAGMENT)
//                utility.startDateMathod(activity!!, this@CreateHoopFragment)
            }
            R.id.rl_optiondate -> {
                if (!tv_date.text.toString().isEmpty()) {
                    totalmonth = month1.toInt() - 1
                    dateTimepicker1(day1, totalmonth.toString(), year1)
                    dateTimeFragment1?.startAtCalendarView()
                    dateTimeFragment1?.setDefaultDateTime(GregorianCalendar(year1.toInt(), month1.toInt().minus(1), day1.toInt()).time)
                    dateTimeFragment1?.show(this.childFragmentManager, TAG_DATETIME_FRAGMENT)
                } else {
                    Toast.makeText(context, "Please select start date", Toast.LENGTH_LONG).show();
                }
            }
            R.id.rl_hoopprofile -> {
                mActivity?.getImagePickerDialog()
                hideKeyboard()
            }
            R.id.btn_pasthoop -> {
                intent = Intent(context, PreviousCreateHoopActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_create -> {
                if (validation.checkTitle(et_title, tv_titleerror) && validation.checkStartDate(tv_date, tv_startdateerror)) {
                    intent = Intent(context, HoopDetailActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.ll_invited -> {
                intent = Intent(context, SelcetGuestActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDateSelect(formatedDate: String) {
        tv_date.text = formatedDate
    }

    override fun onOptionDateSelect(formatedDate: String) {
        tv_optiondate.text = formatedDate
    }

    /*Image picker code*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            tmpUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(mActivity!!.contentResolver, tmpUri)
            userImageFile = Constant.savebitmap(mActivity!!, bitmap, UUID.randomUUID().toString() + ".jpg")
            Glide.with(iv_profile.context).load(bitmap).into(iv_profile)
            iv_profile.borderWidth = 0
        } else if (requestCode == Constant.CAMERA && resultCode == Activity.RESULT_OK) {
            val imageFile = mActivity?.getTemporalFile(mActivity!!)
            val photoURI = Uri.fromFile(imageFile)
            var bm = Constant.getImageResized(mActivity!!, photoURI) ///Image resizer
            val rotation = ImageRotator.getRotation(mActivity!!, photoURI, true)
            bm = ImageRotator.rotate(bm, rotation)
            val profileImagefile = File(mActivity!!.externalCacheDir, UUID.randomUUID().toString() + ".jpg")
            tmpUri = FileProvider.getUriForFile(
                    mActivity!!, mActivity!!.applicationContext.packageName + ".fileprovider",
                    profileImagefile
            )
            userImageFile = Constant.savebitmap(mActivity!!, bm, StringBuilder().append(UUID.randomUUID()).append(".jpg").toString())
            iv_profile.borderWidth = 0
            iv_profile.setImageBitmap(bm)
        }
    }

    fun showKeyboard() {
        val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        inputMethodManager.showSoftInput(et_title, InputMethodManager.SHOW_IMPLICIT);

    }

    fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        inputMethodManager.showSoftInput(et_title, InputMethodManager.HIDE_IMPLICIT_ONLY);

    }

    fun dateTimepicker() {
        // Construct SwitchDateTimePicker
        dateTimeFragment = childFragmentManager.findFragmentByTag(TAG_DATETIME_FRAGMENT) as SwitchDateTimeDialogFragment?
        if (dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(android.R.string.ok),
                    getString(R.string.clear)
            )
        }

        // Optionally define a timezone
        dateTimeFragment?.setTimeZone(TimeZone.getDefault())


        // Init format
        val myDateFormat = SimpleDateFormat("dd MMMM yyyy , hh:mm a", java.util.Locale.getDefault())
        // Assign unmodifiable values
        dateTimeFragment?.set24HoursMode(false)
        dateTimeFragment?.setHighlightAMPMSelection(false)
        dateTimeFragment?.setMinimumDateTime(Calendar.getInstance().time)
        dateTimeFragment?.setMaximumDateTime(GregorianCalendar(2030, Calendar.DECEMBER, 31).time)

        // Define new day and month format
        try {
            dateTimeFragment?.setSimpleDateMonthAndDayFormat(SimpleDateFormat("MMMM dd", Locale.getDefault()))
        } catch (e: SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException) {
            Log.e(TAG, e.message)
        }


        // Set listener for date
        // Or use dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
        dateTimeFragment?.setOnButtonClickListener(object : SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener {
            override fun onPositiveButtonClick(date: Date) {
                tv_date.setText(myDateFormat.format(date))
                //Mon Jul 29 02:20:20 GMT+05:30 2019
                val formatday = SimpleDateFormat("dd", java.util.Locale.getDefault())
                val formatmonth = SimpleDateFormat("M", java.util.Locale.getDefault())
                val formatyear = SimpleDateFormat("yyyy", java.util.Locale.getDefault())
                day1 = formatday.format(date)
                month1 = formatmonth.format(date)
                year1 = formatyear.format(date)
                tv_optiondate.setText("")




            }

            override fun onNegativeButtonClick(date: Date) {
                tv_date.setText("")
                // Do nothing
            }

            override fun onNeutralButtonClick(date: Date) {
                // Optional if neutral button does'nt exists
                // textView.setText("")
            }
        })

    }


    @SuppressLint("SimpleDateFormat")
    fun dateTimepicker1(day1: String, month1: String, year1: String) {
        // Construct SwitchDateTimePicker
        dateTimeFragment1 = childFragmentManager.findFragmentByTag(TAG_DATETIME_FRAGMENT) as SwitchDateTimeDialogFragment?
        if (dateTimeFragment1 == null) {
            dateTimeFragment1 = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(android.R.string.ok),
                    getString(R.string.clear)
            )
        }

        // Optionally define a timezone
        dateTimeFragment1?.setTimeZone(TimeZone.getDefault())


        // Init format
        val myDateFormat1 = SimpleDateFormat("dd MMMM yyyy , hh:mm a", java.util.Locale.getDefault())
        // Assign unmodifiable values
        dateTimeFragment1?.set24HoursMode(false)
        dateTimeFragment1?.setHighlightAMPMSelection(false)
        var date = Date()
        val dateform = (month1.toInt()+1).toString() +"-"+day1+"-"+year1

        date= SimpleDateFormat("MM-dd-yyyy").parse(dateform)
        dateTimeFragment1?.setMinimumDateTime(date);
//        dateTimeFragment1?.setMinimumDateTime(date)
        dateTimeFragment1?.setMaximumDateTime(GregorianCalendar(2030, Calendar.DECEMBER, 31).time)

        // Define new day and month format
        try {
            dateTimeFragment1?.setSimpleDateMonthAndDayFormat(SimpleDateFormat("MMMM dd", Locale.getDefault()))
        } catch (e: SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException) {
            Log.e(TAG, e.message)
        }


        // Set listener for date
        // Or use dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
        dateTimeFragment1?.setOnButtonClickListener(object : SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener {
            override fun onPositiveButtonClick(date: Date) {
                tv_optiondate.setText(myDateFormat1.format(date))
            }

            override fun onNegativeButtonClick(date: Date) {
                tv_optiondate.setText("")
                // Do nothing
            }

            override fun onNeutralButtonClick(date: Date) {
                // Optional if neutral button does'nt exists
                // textView.setText("")
            }
        })

    }

    private val watcherClass_title = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            if (editable.isNotEmpty()) {
                tv_titleerror.visibility = View.GONE
            } else {
                tv_titleerror.visibility = View.VISIBLE

            }
        }
    }

   /* private val watcherClass_location = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            if (editable.isNotEmpty()) {
                tv_locationerror.visibility = View.GONE
            } else {
                tv_locationerror.visibility = View.VISIBLE

            }
        }
    }*/


    private val watcherClass_startdate = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            if (editable.isNotEmpty()) {
                tv_startdateerror.visibility = View.GONE
            } else {
                tv_startdateerror.visibility = View.VISIBLE

            }
        }
    }


}
