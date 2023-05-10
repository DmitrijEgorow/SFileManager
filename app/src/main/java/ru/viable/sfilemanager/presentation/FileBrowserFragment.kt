package ru.viable.sfilemanager.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.viable.sfilemanager.R

class FileBrowserFragment : Fragment() {

    companion object {
        fun newInstance() = FileBrowserFragment()
    }

    private lateinit var viewModel: FileBrowserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModel = ViewModelProvider(this).get(FileBrowserViewModel::class.java)
        return inflater.inflate(R.layout.fragment_file_browser, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                handlePermissions()
            }
        } else {
            TODO("VERSION.SDK_INT < R")
        }
    }

    private fun handlePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val startForResult =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        val intent = result.data
                        Log.d(this.toString(), Environment.isExternalStorageManager().toString())
                    }
                }
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse("package:${requireContext().packageName}")
                startForResult.launch(intent)
            } catch (e: Exception) {
                Toast.makeText(context, R.string.permissions, Toast.LENGTH_SHORT).show()
            }
        } else {
            TODO("VERSION.SDK_INT < R")
        }
    }
}
