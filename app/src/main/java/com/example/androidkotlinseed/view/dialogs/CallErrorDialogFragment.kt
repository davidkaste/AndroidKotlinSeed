package com.example.androidkotlinseed.view.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.androidkotlinseed.R


class CallErrorDialogFragment : DialogFragment() {
    private var mMsg: String? = null

    private fun setArgs(rationalMsg: String) {
        this.mMsg = rationalMsg
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = AlertDialog.Builder(activity)

        alertDialogBuilder.setTitle(R.string.server_error_dialog_title)

        alertDialogBuilder.setMessage(this.mMsg ?: "")

        alertDialogBuilder.setPositiveButton(R.string.ok
        ) { _, _ -> dismiss() }

        return alertDialogBuilder.create()
    }

    companion object {
        fun newInstance(): CallErrorDialogFragment {
            return CallErrorDialogFragment()
        }

        fun newInstance(rationalMsg: String): CallErrorDialogFragment {
            val serverErrorDialogFragment = CallErrorDialogFragment()
            serverErrorDialogFragment.setArgs(rationalMsg)

            return serverErrorDialogFragment
        }
    }
}