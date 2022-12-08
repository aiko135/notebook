package ktepin.penzasoft.dairy.view

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import ktepin.penzasoft.dairy.databinding.FragmentCreateRecordBinding
import ktepin.penzasoft.dairy.util.CameraManager
import ktepin.penzasoft.dairy.vm.CreateRecordViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class CreateRecordFragment : Fragment() {

    private val cameraManager: CameraManager by inject { parametersOf(this) }
    private val createRecordViewModel: CreateRecordViewModel by viewModel()
    private var _binding: FragmentCreateRecordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //startActivityForResult depricated, so use this:
    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = activityResult.data
                data?.let {
                    val img: Uri? = it.data
                    Log.i("testtag", data.toString())
                }
            }
        }

    private val takePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
//            if (activityResult.resultCode == Activity.RESULT_OK) {
                val img:Uri? = cameraManager.saveToGallery();
                Log.i("testtag", img.toString())
//            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreateRecordBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.addPhoto.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            selectImageLauncher.launch(intent)
        }

        binding.openCamera.setOnClickListener {
            val intent = cameraManager.getIntent()
            intent?.let {
                takePhotoLauncher.launch(intent)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}