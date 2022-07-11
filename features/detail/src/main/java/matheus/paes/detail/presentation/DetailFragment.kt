package matheus.paes.detail.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import matheus.paes.base.BaseFragment
import matheus.paes.detail.databinding.FragmentDetailBinding

class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            viewModel.repoDetail = it?.getParcelable(REPO_DETAIL)
        }
    }

    override fun setupFragment() {
        with(binding) {
            txtAuthorName.text = viewModel.repoDetail?.ownerName
            txtRepoDesc.text = viewModel.repoDetail?.description
            txtRepoName.text = viewModel.repoDetail?.fullName
            txtStarsCount.text = viewModel.repoDetail?.stars?.toString()
            txtForksCount.text = viewModel.repoDetail?.forks?.toString()
        }
    }

    companion object {
        const val REPO_DETAIL = "repo_detail_key"
    }

}