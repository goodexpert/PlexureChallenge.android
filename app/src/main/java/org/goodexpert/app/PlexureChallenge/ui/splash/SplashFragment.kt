package org.goodexpert.app.PlexureChallenge.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.goodexpert.app.PlexureChallenge.R
import org.goodexpert.app.PlexureChallenge.ui.BaseFragment
import java.util.concurrent.TimeUnit

class SplashFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Observable.timer(3, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                postAction {
                    findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                }
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_splash, container, false)
        return root
    }

    override fun onStart() {
        super.onStart()
        hideActionBar()
    }

    override fun onStop() {
        showActionBar()
        super.onStop()
    }
}