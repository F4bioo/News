package fbo.costa.news.ui

import android.content.Intent
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.viewbinding.ViewBinding
import fbo.costa.news.R
import fbo.costa.news.data.model.Article
import fbo.costa.news.databinding.ActivitySearchBinding
import fbo.costa.news.presenter.ViewHome
import fbo.costa.news.presenter.search.SearchPresenter
import fbo.costa.news.repository.DataSource
import fbo.costa.news.ui.adapter.AppAdapter
import fbo.costa.news.util.Constants
import fbo.costa.news.util.UtilQueryTextListener

class SearchActivity : AbstractActivity(), ViewHome.View {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var presenter: SearchPresenter

    private val mainAdapter by lazy {
        AppAdapter { _article ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(Constants.EXTRA_ARTICLE, _article)
            startActivity(intent)
        }
    }

    override fun getLayout(): ViewBinding {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        return binding
    }

    override fun onInject() {
        val dataSource = DataSource(this)
        presenter = SearchPresenter(this, dataSource)
        initToolbar()
        iniRecycler()
    }

    override fun showProgress() {
        binding.progressSearch.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
        Toast.makeText(
            this,
            message, Toast.LENGTH_SHORT
        ).show()
    }

    override fun hideProgress() {
        binding.progressSearch.visibility = View.INVISIBLE
    }

    override fun showList(articleList: List<Article>) {
        mainAdapter.submitList2(articleList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val itemSearch = menu?.findItem(R.id.action_search)
        val searchView = itemSearch?.actionView as SearchView
        // Expand SearchView
        searchView.isIconified = false;

        searchView.setOnQueryTextListener(
            UtilQueryTextListener(this.lifecycle) { _newText ->
                _newText?.let { _query ->
                    if (_query.isNotEmpty()) {
                        presenter.onSearch(_query)
                        binding.progressSearch.visibility = View.VISIBLE
                    }
                }
            }
        )
        return super.onCreateOptionsMenu(menu)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbarSearch)
        supportActionBar?.title = getString(R.string.text_news)
    }

    private fun iniRecycler() {
        binding.recyclerSearch.adapter = mainAdapter
    }
}
