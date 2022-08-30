package matheus.paes.home.presentation.ui.home.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import matheus.paes.home.R
import matheus.paes.home.data.models.Repo
import matheus.paes.home.databinding.RepoViewItemBinding
import matheus.paes.home.presentation.ui.home.viewHolder.RepoViewHolder
import matheus.paes.models.RepoEntity

class HomeAdapter: PagingDataAdapter<RepoEntity, RepoViewHolder>(GitRepoDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder.create(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.repo_view_item
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class GitRepoDiff: DiffUtil.ItemCallback<RepoEntity>() {
        override fun areItemsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean {
            return (oldItem.id == newItem.id)
        }

        override fun areContentsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean =
            oldItem == newItem
    }
}
