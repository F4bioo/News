package fbo.costa.news.util

import androidx.recyclerview.widget.DiffUtil
import fbo.costa.news.data.model.Article

class DiffCallBack(
    private var oldCList: List<Article>,
    private var newList: List<Article>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldCList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCList[oldItemPosition] == newList[newItemPosition]
    }
}
