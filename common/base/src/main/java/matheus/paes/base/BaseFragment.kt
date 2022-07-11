package matheus.paes.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.allViews
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    private var _binding: VB? = null
    protected val binding get() = requireNotNull(_binding)

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragment()
    }

    abstract fun setupFragment()
    override fun onDestroyView() {
        super.onDestroyView()
        // Prevent memory leak with RecyclerView adapter
        _binding?.root?.allViews?.forEach {
            if (it is RecyclerView) {
                it.adapter = null
            }
        }
        _binding = null
    }

    protected fun <T> LiveData<T>.observe(observer: Observer<T>) {
        if (view != null) this.observe(viewLifecycleOwner, observer)
    }
}