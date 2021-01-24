package fbo.costa.news.ui

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import fbo.costa.news.R
import fbo.costa.news.data.model.Article
import fbo.costa.news.databinding.ActivityDetailsBinding
import fbo.costa.news.presenter.ViewHome
import fbo.costa.news.presenter.favorite.FavoritePresenter
import fbo.costa.news.repository.DataSource
import fbo.costa.news.util.Constants

class DetailsActivity : AbstractActivity(), ViewHome.Favorite {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var article: Article
    private lateinit var presenter: FavoritePresenter

    override fun getLayout(): ViewBinding {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        return binding
    }

    override fun onInject() {
        getArticle()
        val dataSource = DataSource(this)
        presenter = FavoritePresenter(this, dataSource)

        binding.apply {
            iniWebViewClient()

            article.url?.let { _url ->
                webView.loadUrl(_url)
            }

            fab.setOnClickListener { _view ->
                presenter.insertModel(article)
                Snackbar.make(
                    _view, getString(R.string.text_item_saved),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun showList(articleList: List<Article>) {
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
