package org.goodexpert.app.PlexureChallenge.ui

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.squareup.picasso.BuildConfig
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment : Fragment() {

    private var compositeDisposalble: CompositeDisposable? = null
    private var listener: OnFragmentInterface? = null

    open val screenName: String? = null

    override fun onDestroy() {
        // Dispose all pending disposable items
        getCompositeDisposable().dispose()
        super.onDestroy()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInterface) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInterface")
        }

        if (!BuildConfig.DEBUG) {
            setCurrentScreen(screenName)
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    @Synchronized
    fun isFinishing(): Boolean {
        return !isAdded || isDetached
    }

    protected fun getInterface(): OnFragmentInterface? {
        return listener
    }

    protected fun setTitleText(title: CharSequence) {
        getInterface()?.setTitleText(title)
    }

    protected fun setTitleText(@StringRes resId: Int) {
        getInterface()?.setTitleText(resId)
    }

    protected fun setCurrentScreen(screenName: String?, screenClassOverride: String? = null) {
        getInterface()?.setCurrentScreen(screenName, screenClassOverride)
    }

    protected fun setEnabledDrawerLayout(enabled: Boolean) {
        getInterface()?.setEnabledDrawerLayout(enabled)
    }

    protected fun showActionBar() {
        getInterface()?.showActionBar()
    }

    protected fun hideActionBar() {
        getInterface()?.hideActionBar()
    }

    protected fun showProgress(show: Boolean) {
        getInterface()?.showProgress(show)
    }

    private fun getCompositeDisposable(): CompositeDisposable {
        if (compositeDisposalble == null || compositeDisposalble?.isDisposed!!) {
            compositeDisposalble = CompositeDisposable()
        }
        return compositeDisposalble!!
    }

    fun addDisposable(disposable: Disposable) {
        getCompositeDisposable().add(disposable)
    }

    fun hasDisposables(): Boolean {
        return getCompositeDisposable().size() > 0
    }

    fun postAction(action: (() -> Unit)?) {
        if (action == null)
            return

        Handler(Looper.getMainLooper()).post {
            if (!isFinishing()) {
                action()
            }
        }
    }

    fun postAction(action: (() -> Unit)?, delayMillis: Long) {
        if (action == null)
            return

        Handler(Looper.getMainLooper()).postDelayed({
            if (!isFinishing()) {
                action()
            }
        }, delayMillis)
    }
}