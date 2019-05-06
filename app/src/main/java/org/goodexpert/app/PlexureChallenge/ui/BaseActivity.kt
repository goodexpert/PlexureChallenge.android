package org.goodexpert.app.PlexureChallenge.ui

import android.content.BroadcastReceiver
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity(), OnFragmentInterface {

    private var compositeDisposalble: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        // Dispose all pending disposable items
        getCompositeDisposable().dispose()
        super.onDestroy()
    }

    override fun setTitleText(title: CharSequence) {
        supportActionBar?.setTitle(title)
    }

    override fun setTitleText(resId: Int) {
        supportActionBar?.setTitle(resId)
    }

    override fun setCurrentScreen(screenName: String?, screenClassOverride: String?) {
        if (TextUtils.isEmpty(screenName))
            return
    }

    override fun setEnabledDrawerLayout(enabled: Boolean) {
    }

    override fun showActionBar() {
        supportActionBar?.show()
    }

    override fun hideActionBar() {
        supportActionBar?.hide()
    }

    override fun showProgress(show: Boolean) {
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
