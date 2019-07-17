package com.hoppin.activity.ui.authantication.hoopinstart.model

import com.hoppin.R

/**
 * Created by Ravi Birla on 26,June,2019
 */
enum class IntroModel(layoutId:Int) {
    PAGEONE( R.layout.welcome_page_one),
    PAGETWO( R.layout.welcome_page_two),
    PAGETHREE( R.layout.welcome_page_three),
    PAGEFOUR( R.layout.hoopin_started);
     val layoutId : Int
    init {
        this.layoutId = layoutId
    }

    fun getTitleResId() :Int {
        return layoutId;
    }

    fun getLayoutResId() : Int{
        return layoutId;
    }


}