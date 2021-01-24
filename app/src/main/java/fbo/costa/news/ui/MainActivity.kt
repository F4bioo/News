package fbo.costa.news.ui

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import fbo.costa.news.R
import fbo.costa.news.data.model.Article
import fbo.costa.news.databinding.ActivityMainBinding
import fbo.costa.news.presenter.ViewHome
import fbo.costa.news.presenter.main.MainPresenter
import fbo.costa.news.repository.DataSource
import fbo.costa.news.ui.adapter.AppAdapter
import fbo.costa.news.util.Constants

class MainActivity : AbstractActivity(), ViewHome.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainPresenter

    private val mainAdapter by lazy {
        AppAdapter { _article ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(Constants.EXTRA_ARTICLE, _article)
            startActivity(intent)
        }
    }

    override fun getLayout(): ViewBinding {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding
    }

    override fun onInject() {
        val dataSource = DataSource()
        presenter = MainPresenter(this, dataSource)
        presenter.onRequestList()
        initToolbar()
        iniRecycler()
    }

    override fun showProgress() {
        binding.progressMain.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
        Toast.makeText(
            this,
            message, Toast.LENGTH_SHORT
        ).show()
    }

    override fun hideProgress() {
        binding.progressMain.visibility = View.INVISIBLE
    }

    override fun showList(articleList: List<Article>) {
        mainAdapter.submitList2(articleList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                Intent(this, SearchActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.action_favorite -> {
                Intent(this, FavoriteActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbarMain)
        supportActionBar?.title = getString(R.string.text_news)
    }

    private fun iniRecycler() {
        binding.recyclerMain.adapter = mainAdapter
    }
}
