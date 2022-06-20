package matheus.paes.home.presentation.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import matheus.paes.helper.makeGone
import matheus.paes.helper.toggleVisibility
import matheus.paes.home.R
import matheus.paes.models.RepoEntity

class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.repo_name)
    private val description: TextView = view.findViewById(R.id.repo_description)
    private val stars: TextView = view.findViewById(R.id.repo_stars)
    private val language: TextView = view.findViewById(R.id.repo_language)
    private val forks: TextView = view.findViewById(R.id.repo_forks)

    private var repo: RepoEntity? = null

    init {
        view.setOnClickListener {
            Toast.makeText(view.context, repo?.name, Toast.LENGTH_SHORT).show()
        }
    }

    fun bind(repo: RepoEntity?) {
        if (repo == null) showEmptyState()
        else showRepoData(repo)
    }

    private fun showEmptyState() {
        val resources = itemView.resources
        name.text = "Carregando"
        description.makeGone()
        language.makeGone()
        stars.text = ""
        forks.text = ""
    }

    private fun showRepoData(repo: RepoEntity) {
        this.repo = repo
        name.text = repo.fullName

        toggleDescriptionVisibility()
        toggleLanguageVisibility()

        stars.text = repo.stars.toString()
        forks.text = repo.forks.toString()
    }

    private fun toggleDescriptionVisibility() {
        repo?.description?.let { description.text = it }

        val hasDescription = !repo?.description.isNullOrEmpty()
        description.toggleVisibility(hasDescription)
    }

    private fun toggleLanguageVisibility() {
        repo?.language?.let { description.text = it }

        val hasLanguage = !repo?.language.isNullOrEmpty()
        language.toggleVisibility(hasLanguage)
    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_view_item, parent, false)
            return RepoViewHolder(view)
        }
    }
}
