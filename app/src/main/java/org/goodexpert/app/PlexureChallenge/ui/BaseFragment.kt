package org.goodexpert.app.PlexureChallenge.ui

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment : Fragment() {

    private var compositeDisposalble: CompositeDisposable? = null

    override fun onDestroy() {
        // Dispose all pending disposable items
        getCompositeDisposable().dispose()
        super.onDestroy()
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

    @Synchronized
    fun isFinishing(): Boolean {
        return !isAdded || isDetached
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