package com.qxy.module.rank.ui.rankversion

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
import com.qxy.lib.base.util.args
import com.qxy.module.rank.R

class RankVersionDialogFragment private constructor() : BottomSheetDialogFragment() {

    private var mSelectedVersion = 0

    private var type: Int by args()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dialog_version_selected, container, false)
        view.visibility = View.INVISIBLE
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
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

                // 动态改变RecyclerView高度，但是会在加载布局时闪一下
                val recyclerHeight = itemHeight * COUNT_IN_SCREEN
//                val layoutParams = recycler.layoutParams
//                layoutParams.height = recyclerHeight
//                recycler.layoutParams = layoutParams

                // 让第一个和最后一个数据居中
                val padding = (recyclerHeight - itemHeight) / 2
                recycler.setPadding(0, padding, 0, padding)
                recycler.scrollToPosition(0)

                // 动态设置选择框高度
//                val selectedBgLp = selectedBg.layoutParams as FrameLayout.LayoutParams
//                selectedBgLp.height = itemHeight
//                selectedBg.layoutParams = selectedBgLp
            }
        }

        val behavior = (dialog as BottomSheetDialog).behavior
        behavior.isDraggable = false

        confirm.setOnClickListener {
            setFragmentResult(
                REQUEST_KEY_VERSION,
                bundleOf(RESULT_VERSION_SELECTED to mSelectedVersion)
            )
            dismiss()
        }
        cancel.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
        const val REQUEST_KEY_VERSION = "VersionRequestKey"
        const val RESULT_VERSION_SELECTED = "VersionSelected"
        private const val COUNT_IN_SCREEN = 3

        fun newInstance(type: Int) = RankVersionDialogFragment().apply {
            this.type = type
        }
    }
}