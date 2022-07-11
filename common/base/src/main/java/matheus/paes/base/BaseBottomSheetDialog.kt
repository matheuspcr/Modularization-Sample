package matheus.paes.base

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialog<VB : ViewBinding> : BottomSheetDialogFragment(),
    IAvoidMemoryLeaks {
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    protected val binding get() = requireNotNull(_binding)
    private var _binding: VB? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        clearVariables()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomSheetBehaviour(requireView().parent as View)
        setupDialog()
    }

    abstract fun setupBottomSheetBehaviour(dialog: View)

    abstract fun setupDialog()

    protected fun <T> LiveData<T>.observe(observer: Observer<T>) {
        if (view != null) this.observe(viewLifecycleOwner, observer)
    }

    companion object {
        fun <VB : ViewBinding> BaseBottomSheetDialog<VB>.showSafe(
            fm: FragmentManager,
            tag: String
        ) {
            try {
                show(fm, tag)
            } catch (e: IllegalStateException) {
                Log.e(this.javaClass.name, e.message.toString())
            }
        }
    }
}