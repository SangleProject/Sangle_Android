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


class ReportBottomDialog : BottomSheetDialogFragment() {
    private val mViewModel: DetailOtherViewModel by activityViewModels()
    private lateinit var reportBottomBinding: BottomSheetReportBinding

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

        return reportBottomBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reportBottomBinding.closeBottomSheetBtn.mingSingleClickListener {
            dismiss()
        }

        reportBottomBinding.radioGroupReport.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_report_a_term_of_abuse -> {
                    hideReportEtcLayout()
                }
                R.id.radio_report_promotion -> {
                    hideReportEtcLayout()
                }
                R.id.radio_report_ero -> {
                    hideReportEtcLayout()
                }
                R.id.radio_report_illegal -> {
                    hideReportEtcLayout()
                }
                R.id.radio_report_spam -> {
                    hideReportEtcLayout()
                }
                R.id.radio_report_privacy -> {
                    hideReportEtcLayout()
                }
                R.id.radio_report_etc -> {
                    showReportEtcLayout()
                }
            }
            reportBottomBinding.edtReportEtc.clearFocus()
            reportBottomBinding.btnReport.isEnabled = true
        }

    }

    private fun showReportEtcLayout() {
        if (reportBottomBinding.layoutEtc.visibility == View.INVISIBLE)
            reportBottomBinding.layoutEtc.visibility = View.VISIBLE
    }

    private fun hideReportEtcLayout() {
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
    }

}