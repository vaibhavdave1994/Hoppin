package com.hoppin.activity.countrycodepicker

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hoppin.R
import com.hoppin.base.BaseActivity
import com.hoppin.helper.Utility
import kotlinx.android.synthetic.main.activity_country_selection.*


class CountrySelectionActivity : BaseActivity(), View.OnClickListener {

    lateinit var mCountries: ArrayList<Country>
    var countryAdapter: CountryAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_selection)

        overridePendingTransition(R.anim.slide_up,R.anim.slide_down)
        mCountries = ArrayList()
        val turnsType = object : TypeToken<List<Country>>() {}.type
        val turns = Gson().fromJson<List<Country>>(Utility.loadJSONFromAsset(this, "country_code.json"), turnsType)
        mCountries.addAll(turns)

        etFilterField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (countryAdapter != null) {
                    countryAdapter?.filter(s.toString())
                    //  customList1.notifyDataSetChanged();
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        iv_back.setOnClickListener(this)
        setAdapter()
    }

    private fun setAdapter() {
        countryAdapter = CountryAdapter(this, this.mCountries)
        countryListRecyclerView.layoutManager = LinearLayoutManager(this)
        countryListRecyclerView.adapter = countryAdapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        hideKeyboard()
        finish()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> {
                onBackPressed()
            }

        }
    }
}

