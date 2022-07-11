package matheus.paes.home.presentation

import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import matheus.paes.base.BaseActivity
import matheus.paes.home.databinding.ActivityHomeBinding

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    override fun setupActivity() {

    }
}