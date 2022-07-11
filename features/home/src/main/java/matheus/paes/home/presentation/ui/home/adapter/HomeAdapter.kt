package matheus.paes.home.presentation.ui.home.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import matheus.paes.home.R
import matheus.paes.home.presentation.ui.home.viewHolder.RepoViewHolder
import matheus.paes.models.RepoEntity

class HomeAdapter: PagingDataAdapter<RepoEntity, RecyclerView.ViewHolder>(UIMODEL_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RepoViewHolder.create(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.repo_view_item
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repo = getItem(position)
        repo?.let {
            (holder as RepoViewHolder).bind(it)
        }
    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<RepoEntity>() {
            override fun areItemsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean {
                return (oldItem.fullName == newItem.fullName)
            }

            override fun areContentsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean =
                oldItem == newItem
        }
    }
}
