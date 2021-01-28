package fbo.costa.news.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fbo.costa.news.R
import fbo.costa.news.data.model.Article
import fbo.costa.news.databinding.AdapterItemBinding
import fbo.costa.news.util.Constants
import fbo.costa.news.util.DiffCallBack
import java.text.SimpleDateFormat
import java.util.*

class AppAdapter(
    private val onClickListener: (article: Article) -> Unit
) : RecyclerView.Adapter<AppAdapter.ViewHolder>() {

    private val articleList = arrayListOf<Article>()

    private val differCallBack = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppAdapter.ViewHolder {
        val biding = AdapterItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(biding, onClickListener)
    }

    override fun onBindViewHolder(holder: AppAdapter.ViewHolder, position: Int) {
        val article = articleList[position]
        holder.viewBind(article)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    inner class ViewHolder(
        private val biding: AdapterItemBinding,
        private val onClickListener: (article: Article) -> Unit
    ) : RecyclerView.ViewHolder(biding.root) {

        fun viewBind(article: Article) {
            biding.apply {
                imageNews.loadImage(article.urlToImage)
                textTitle.text = article.author ?: article.source?.name
                textSource.text = article.source?.name ?: article.author
                textDescription.text = article.description
                textPublishedAt.text = article.publishedAt?.let { _publishedAt ->
                    datetimeFormat(_publishedAt)
                }

                itemView.setOnClickListener {
                    onClickListener(article)
                }
            }
        }
    }

    fun submitList2(newList: List<Article>) {
        val oldList = articleList
        val diffResult = DiffUtil.calculateDiff(
            DiffCallBack(oldList, newList)
        )

        articleList.clear()
        articleList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun submitList(newList: List<Article>) {
        differ.submitList(newList.toList())
        articleList.clear()
        articleList.addAll(differ.currentList)
    }

    fun getItem(position: Int): Article {
        return articleList[position]
    }

    fun insertItem(position: Int, article: Article) {
        articleList.add(position, article)
        notifyItemInserted(position)
    }

    fun deleteItem(position: Int) {
        articleList.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun ImageView.loadImage(url: String?) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_image_placeholder_200dp)
            .error(R.drawable.ic_image_error_200dp)
            .into(this)
    }

    // ApiNews provides this type: 2021-01-25T10:55:15Z
    // Fun returns this type: Mon, 25 Jan 10:55
    private fun datetimeFormat(publishedAt: String): String {
        val apiDatetime = SimpleDateFormat(Constants.API_DATETIME_FORMAT, Locale.getDefault())
        val pattern = SimpleDateFormat(Constants.PATTERN, Locale.getDefault())

        apiDatetime.parse(publishedAt)?.let { _date ->
            return pattern.format(_date)
        }
        return publishedAt
    }
}
