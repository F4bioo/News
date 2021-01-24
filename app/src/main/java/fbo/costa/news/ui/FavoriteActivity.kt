package fbo.costa.news.ui

import android.content.Intent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import fbo.costa.news.R
import fbo.costa.news.data.model.Article
import fbo.costa.news.databinding.ActivityFavoriteBinding
import fbo.costa.news.presenter.ViewHome
import fbo.costa.news.presenter.favorite.FavoritePresenter
import fbo.costa.news.repository.DataSource
import fbo.costa.news.ui.adapter.AppAdapter
import fbo.costa.news.util.Constants

class FavoriteActivity : AbstractActivity(), ViewHome.Favorite {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var presenter: FavoritePresenter

    private val mainAdapter by lazy {
        AppAdapter { _article ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(Constants.EXTRA_ARTICLE, _article)
            startActivity(intent)
        }
    }

    override fun getLayout(): ViewBinding {
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        return binding
    }

    override fun onInject() {
        val dataSource = DataSource(this)
        presenter = FavoritePresenter(this, dataSource)
        presenter.selectList()
        initToolbar()
        iniRecycler()

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.ACTION_STATE_IDLE,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = mainAdapter.getItem(position)

                presenter.deleteModel(article)
                mainAdapter.deleteItem(position)

                Snackbar.make(
                    viewHolder.itemView,
                    getString(R.string.text_item_deleted),
                    Snackbar.LENGTH_LONG

                ).apply {
                    setAction(getString(R.string.text_undo)) {
                        presenter.insertModel(article)
                        mainAdapter.insertItem(position, article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchCallback).apply {
            attachToRecyclerView(binding.recyclerFavorite)
        }
    }

    override fun showList(articleList: List<Article>) {
        mainAdapter.submitList2(articleList)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbarFavorite)
        supportActionBar?.title = getString(R.string.text_favorite)
    }

    private fun iniRecycler() {
        binding.recyclerFavorite.adapter = mainAdapter
    }
}
