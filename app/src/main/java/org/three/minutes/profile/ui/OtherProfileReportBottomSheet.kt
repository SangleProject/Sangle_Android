package org.three.minutes.profile.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import min.dev.singleclick.mingSingleClickListener
import org.three.minutes.R
import org.three.minutes.databinding.BottomSheetUserReportBinding


class OtherProfileReportBottomSheet(private val listener: ReportClickListener) : BottomSheetDialogFragment() {
    private lateinit var reportBottomBinding: BottomSheetUserReportBinding

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
            DataBindingUtil.inflate(layoutInflater, R.layout.bottom_sheet_user_report, container, false)

        reportBottomBinding.run {
            lifecycleOwner = viewLifecycleOwner
        }

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
                }
                R.id.radio_report_promotion -> {
                }
                R.id.radio_report_ero -> {
                }
                R.id.radio_report_illegal -> {
                }
                R.id.radio_report_spam -> {
                }
                R.id.radio_report_privacy -> {
                }
            }
            reportBottomBinding.btnReport.isEnabled = true
        }

        reportBottomBinding.btnReport.mingSingleClickListener {
            listener.onClickOk()
            dismiss()
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        reportBottomBinding.radioGroupReport.clearCheck()
        reportBottomBinding.btnReport.isEnabled = false
    }

}