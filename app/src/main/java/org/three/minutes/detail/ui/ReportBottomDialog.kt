package org.three.minutes.detail.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import min.dev.singleclick.mingSingleClickListener
import org.three.minutes.R
import org.three.minutes.databinding.BottomSheetReportBinding
import org.three.minutes.detail.viewmodel.DetailOtherViewModel


class ReportBottomDialog(private val listener: ReportClickListener) : BottomSheetDialogFragment() {
    private val mViewModel: DetailOtherViewModel by activityViewModels()
    private lateinit var reportBottomBinding: BottomSheetReportBinding

    interface ReportClickListener {
        fun onClickOk()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.customBottomSheet)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        reportBottomBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.bottom_sheet_report, container, false)

        reportBottomBinding.run {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
        }

        observeEvent()

        return reportBottomBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reportBottomBinding.closeBottomSheetBtn.mingSingleClickListener {
            dismiss()
        }

        reportBottomBinding.radioGroupReport.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_report_a_term_of_abuse -> {
                    mViewModel.reportContents = "욕설이나 차별, 혐오성 게시물"
                    hideReportEtcLayout()
                }
                R.id.radio_report_promotion -> {
                    mViewModel.reportContents = "홍보 혹은 영리목적의 게시물"
                    hideReportEtcLayout()
                }
                R.id.radio_report_ero -> {
                    mViewModel.reportContents = "음란, 선정적으로 유해한 게시물"
                    hideReportEtcLayout()
                }
                R.id.radio_report_illegal -> {
                    mViewModel.reportContents = "불법 정보를 제공하는 행위"
                    hideReportEtcLayout()
                }
                R.id.radio_report_spam -> {
                    mViewModel.reportContents = "같은 내용 도배, 스팸 게시물"
                    hideReportEtcLayout()
                }
                R.id.radio_report_privacy -> {
                    mViewModel.reportContents = "개인 정보를 노출하는 행위"
                    hideReportEtcLayout()
                }
                R.id.radio_report_etc -> {
                    showReportEtcLayout()
                }
            }
            reportBottomBinding.edtReportEtc.clearFocus()

        }

        reportBottomBinding.btnReport.mingSingleClickListener {
            listener.onClickOk()
            dismiss()
        }

    }

    private fun observeEvent() {
        mViewModel.reportEtc.observe(viewLifecycleOwner, {
            if (it.isNullOrBlank() && reportBottomBinding.radioReportEtc.isChecked) {
                reportBottomBinding.btnReport.isEnabled = false
            }
            else if (it.isNotBlank() && reportBottomBinding.radioReportEtc.isChecked)
                reportBottomBinding.btnReport.isEnabled = true
        })
    }

    private fun showReportEtcLayout() {
        reportBottomBinding.btnReport.isEnabled = false
        mViewModel.reportContents = ""
        if (reportBottomBinding.layoutEtc.visibility == View.INVISIBLE) {
            reportBottomBinding.layoutEtc.visibility = View.VISIBLE
            mViewModel.reportEtc.value = ""
        }
    }

    private fun hideReportEtcLayout() {
        reportBottomBinding.btnReport.isEnabled = true
        if (reportBottomBinding.layoutEtc.visibility == View.VISIBLE) {
            reportBottomBinding.layoutEtc.visibility = View.INVISIBLE
            mViewModel.reportEtc.value = ""
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        reportBottomBinding.radioGroupReport.clearCheck()
        reportBottomBinding.btnReport.isEnabled = false
        mViewModel.reportEtc.value = ""
        mViewModel.reportContents = ""
    }

}