package com.qxy.module.rank.ui.bottomversion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.qxy.lib.base.ext.trueHeight
import com.qxy.module.rank.R

class BottomVersionSelectedDialogFragment : BottomSheetDialogFragment() {

    private var mSelectedVersion = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dialog_version_selected, container, false)
        view.visibility = View.INVISIBLE
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recycler = view.findViewById<RecyclerView>(R.id.rank_bottom_rv)
        val selectedBg = view.findViewById<View>(R.id.rank_bottom_selected)
        val cancel = view.findViewById<TextView>(R.id.rank_bottom_tv_cancel)
        val confirm = view.findViewById<TextView>(R.id.rank_bottom_tv_confirm)

        val onItemClickListener = { v: View ->
            val position = recycler.getChildLayoutPosition(v)
            recycler.smoothScrollToPosition(position)
        }

        val onItemSelectedListener = { position: Int ->
            mSelectedVersion = position
        }

        recycler.layoutManager = SlideLayoutManager(requireActivity(), onItemSelectedListener)
        recycler.adapter = VersionSelectedPagedAdapter(onItemClickListener)
        recycler.post {
            view.visibility = View.VISIBLE
//            TODO if (data.size < COUNT_IN_SCREEN) return@post
            val itemView = recycler.getChildAt(0)
            itemView?.let {
                val itemHeight = it.trueHeight

                val recyclerHeight = itemHeight * COUNT_IN_SCREEN
                val layoutParams = recycler.layoutParams
                layoutParams.height = recyclerHeight
                recycler.layoutParams = layoutParams

                val padding = (recyclerHeight - itemHeight) / 2
                recycler.setPadding(0, padding, 0, padding)
                recycler.scrollToPosition(0)

                val selectedBgLp = selectedBg.layoutParams as FrameLayout.LayoutParams
                selectedBgLp.height = itemHeight
                selectedBg.layoutParams = selectedBgLp
            }
        }

        val behavior = (dialog as BottomSheetDialog).behavior
        behavior.isDraggable = false

        confirm.setOnClickListener {
            setFragmentResult(VERSION_REQUEST_KEY, bundleOf(VERSION_SELECTED to mSelectedVersion))
            dismiss()
        }
        cancel.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
        const val VERSION_REQUEST_KEY = "VersionRequestKey"
        const val VERSION_SELECTED = "VersionSelected"
        private const val COUNT_IN_SCREEN = 3
    }
}