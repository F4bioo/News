package fbo.costa.news.ui

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.viewbinding.ViewBinding
import fbo.costa.news.data.model.Article
import fbo.costa.news.databinding.ActivityDetailsBinding
import fbo.costa.news.util.Constants

class DetailsActivity : AbstractActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var article: Article

    override fun getLayout(): ViewBinding {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        return binding
    }

    override fun onInject() {
        getArticle()

        binding.apply {
            iniWebViewClient()

            article.url?.let { url ->
                webView.loadUrl(url)
            }
        }
    }

    private fun getArticle() {
        intent.extras?.let { _article ->
            article = _article.get(Constants.EXTRA_ARTICLE) as Article
        }
    }

    private fun iniWebViewClient() {
        binding.apply {
            progressDetails.visibility = View.VISIBLE

            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    progressDetails.visibility = View.INVISIBLE
                }
            }
        }
    }
}
