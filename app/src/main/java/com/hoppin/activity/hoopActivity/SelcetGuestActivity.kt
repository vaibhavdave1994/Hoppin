package com.hoppin.activity.hoopActivity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.hoppin.R
import com.hoppin.activity.hoopActivity.adapter.SelectGuestAdapter
import com.hoppin.activity.hoopActivity.model.ContactModel
import com.hoppin.activity.hoopActivity.model.PriviousHoopDataModel
import com.hoppin.helper.GetContact
import kotlinx.android.synthetic.main.activity_selcet_guest.*


class SelcetGuestActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var selectGuestAdapter: SelectGuestAdapter
    lateinit var arrayList: ArrayList<PriviousHoopDataModel>
    lateinit var priviousHoopDataModel: PriviousHoopDataModel
    lateinit var contectList: List<ContactModel>
    lateinit var getContact: GetContact
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selcet_guest)
        inItView()
        showContacts()
        adapterData()


    }

    @SuppressLint("NewApi")
    fun inItView() {
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorWhite));
        iv_back.setOnClickListener(this)
        btn_done.setOnClickListener(this)
        arrayList = ArrayList()
        contectList = ArrayList()
        getContact = GetContact()
        priviousHoopDataModel = PriviousHoopDataModel()

    }

    fun adapterData() {

        val value = ArrayList<Boolean>()
        for (i in 1..contectList.size + 6) {
            value.add(true)
        }

        selectGuestAdapter = SelectGuestAdapter(this, value, contectList)
        val mLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = mLayoutManager
        //addprop_recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.adapter = selectGuestAdapter
    }


    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.btn_done -> {
                onBackPressed()
            }

        }

    }

    private fun showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), getContact.PERMISSIONS_REQUEST_READ_CONTACTS)
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            contectList = getContact.getContacts(this)
            Log.e("contectList", contectList.toString())
            adapterData()


        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == getContact.PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts()
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
