package org.goodexpert.app.PlexureChallenge.ui

import androidx.annotation.StringRes

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 */
interface OnFragmentInterface {

    fun setTitleText(title: CharSequence)
    fun setTitleText(@StringRes resId: Int)

    fun setCurrentScreen(screenName: String?, screenClassOverride: String? = null)

    fun setEnabledDrawerLayout(enabled: Boolean)

    fun showActionBar()
    fun hideActionBar()

    fun showProgress(show: Boolean)
}