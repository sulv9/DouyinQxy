package com.qxy.module.rank.ui.rankversion

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.qxy.lib.base.ext.log
import com.qxy.lib.base.ext.observe
import com.qxy.lib.base.util.toJson
import com.qxy.lib.common.network.processView
import com.qxy.module.rank.R
import com.qxy.module.rank.data.model.RankVersionItem
import com.qxy.module.rank.databinding.FragmentDialogVersionSelectedBinding
import com.qxy.module.rank.ui.ranklist.RankListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankVersionDialogFragment private constructor() : BottomSheetDialogFragment() {

    private var mSelectedVersion = 0
    private var mDataLength = 0
    private var isInitView = false

    private val viewModel: RankListViewModel by viewModels({ requireParentFragment() })

    private var _binding: FragmentDialogVersionSelectedBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAdapter: RankVersionListAdapter

    private val onItemClickListener = { v: View ->
        val position = binding.rankVersionRv.getChildAdapterPosition(v)
        binding.rankVersionRv.smoothScrollToPosition(position)
    }

    private val onItemSelectedListener = { position: Int ->
        mSelectedVersion = position
    }

    private val onItemViewInitListener = { itemView: View ->
        setupRecyclerView(itemView)
    }

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogVersionSelectedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initData()
    }

    private fun initData() {
        observe(viewModel.versionListData) { result ->
            result.processView(
                binding.rankVersionPageLoading.root,
                binding.rankVersionPageLoadError.root,
                binding.rankVersionContent
            ) {
                initViewOnce()
                val versionLists = it.map { rankVersion ->
                    rankVersion.versionList ?: emptyList()
                }.flatten()
                val newList = listOf(mAdapter.currentList, versionLists).flatten()
                mAdapter.submitList(newList)
                if (mDataLength != 0) binding.rankVersionRv.scrollToPosition(mDataLength - 1)
                mDataLength = newList.size
                log { "mDataLength:$mDataLength" }
            }
        }
    }

    private fun initViewOnce() {
        if (isInitView) return
        val layoutManager = SlideLayoutManager(requireActivity(), onItemSelectedListener)
        binding.rankVersionRv.layoutManager = layoutManager
        binding.rankVersionRv.isNestedScrollingEnabled = false
        binding.rankVersionRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
//                log { "lastPos:${layoutManager.findLastVisibleItemPosition()} mDataLength:$mDataLength" }
                if (layoutManager.findLastVisibleItemPosition() == mDataLength - 2) {
                    viewModel.fetchMoreVersionData(isRefresh = false)
                }
            }
        })
        mAdapter = RankVersionListAdapter(
            onItemClickListener, onItemViewInitListener
        ).apply { submitList(listOf(RankVersionItem.empty())) }
        binding.rankVersionRv.adapter = mAdapter

        val behavior = (dialog as BottomSheetDialog).behavior
        behavior.isDraggable = false

        binding.rankVersionTvConfirm.setOnClickListener {
            setFragmentResult(
                REQUEST_KEY_VERSION,
                bundleOf(
                    RESULT_VERSION_SELECTED to
                            if (mSelectedVersion < 0)
                                null
                            else
                                mAdapter.getSelectedVersionItem(mSelectedVersion).toJson()
                )
            )
            dismiss()
        }
        binding.rankVersionTvCancel.setOnClickListener {
            dismiss()
        }
        isInitView = true
    }

    private var isItemViewInit = false
    private fun setupRecyclerView(itemView: View) {
        if (isItemViewInit) return
        isItemViewInit = true
        itemView.post {
            val itemHeight = itemView.height

            // 让第一个和最后一个数据居中
            val padding = (binding.rankVersionRv.height - itemHeight) / 2
            binding.rankVersionRv.setPadding(0, padding, 0, padding)
            binding.rankVersionRv.scrollToPosition(0)
            // 动态设置选择框高度
            val selectedBgLp = binding.rankVersionSelected.layoutParams as FrameLayout.LayoutParams
            selectedBgLp.topMargin = padding
            binding.rankVersionSelected.layoutParams = selectedBgLp
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
        const val REQUEST_KEY_VERSION = "VersionRequestKey"
        const val RESULT_VERSION_SELECTED = "VersionSelected"

        fun newInstance() = RankVersionDialogFragment()
    }
}