package fbo.costa.news.ui

import androidx.viewbinding.ViewBinding
import fbo.costa.news.databinding.ActivitySearchBinding

class FavoriteActivity : AbstractActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun getLayout(): ViewBinding {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        return binding
    }

    override fun onInject() {

    }
}
