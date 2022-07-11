package matheus.paes.home.presentation.ui.home

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import matheus.paes.base.BaseFragment
import matheus.paes.home.databinding.FragmentHomeBinding
import matheus.paes.home.presentation.ui.home.adapter.HomeAdapter

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var adapter: HomeAdapter


    override fun setupFragment() {
        binding.bindList()
        binding.bindSearchBar()
        binding.bindSwipeRefresh()
        collectUiState()
    }

    private fun FragmentHomeBinding.bindList() {
        adapter = HomeAdapter()
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(list.context)
        val decoration = DividerItemDecoration(list.context, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(decoration)
    }

    private fun FragmentHomeBinding.bindSearchBar() {
        searchRepo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        searchRepo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
    }


    private fun FragmentHomeBinding.updateRepoListFromInput() {
        searchRepo.text.trim().let {
            if (it.isNotEmpty()) {
                list.scrollToPosition(0)
                viewModel.accept(UiAction.Search(query = it.toString()))
            }
        }
    }

    private fun FragmentHomeBinding.bindSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            viewModel.accept(UiAction.Refresh)
        }
    }

    private fun collectUiState() {
        val items = viewModel.data
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collectLatest {
                    binding.swipeRefresh.apply { if (isRefreshing) isRefreshing = false}
                    adapter.submitData(it)
                }
            }
        }
    }
}
