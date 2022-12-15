package ktepin.penzasoft.dairy.view

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ktepin.penzasoft.dairy.databinding.FragmentCreateRecordBinding
import ktepin.penzasoft.dairy.util.CameraManager
import ktepin.penzasoft.dairy.vm.CreateRecordViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class CreateRecordFragment : Fragment() {

    private val cameraManager: CameraManager by inject { parametersOf(this) }
    private val viewModel: CreateRecordViewModel by viewModel()
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
                    img?.let { im -> viewModel.setUri(im.toString()) }
                }
            }
        }

    private val takePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val img: Uri? = cameraManager.saveToGallery();
            img?.let { im -> viewModel.setUri(im.toString()) }
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

        val textWatcher = object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {
                viewModel.update(
                    binding.textField.editText?.text.toString(),
                    binding.decrField.editText?.text.toString()
                )
            }
        }

        binding.textField.editText?.addTextChangedListener(textWatcher)
        binding.decrField.editText?.addTextChangedListener(textWatcher)

        viewModel.record.observe(viewLifecycleOwner, Observer {
            if(it.img != ""){
                applyImage(it.img)
            }
        })

        binding.saveButton.setOnClickListener {
            binding.saveButton.isEnabled = false
            viewModel.persist()
        }

        return root
    }

    private fun applyImage(uri:String){
        if(uri != ""){
            try {
                if(Build.VERSION.SDK_INT < 28) {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        this.requireActivity().contentResolver,
                        android.net.Uri.parse(uri)
                    )
                    binding.imageView.setImageBitmap(bitmap)
                } else {
                    val source = ImageDecoder.createSource(this.requireActivity().contentResolver,  android.net.Uri.parse(uri))
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    binding.imageView.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onResume() {
        val vmRecord = viewModel.record.value;
        if (vmRecord != null) {
            if (vmRecord.title != "")
                binding.textField.editText?.setText(vmRecord.title)
            if(vmRecord.description != "")
                binding.decrField.editText?.setText(vmRecord.description)
            if(vmRecord.img != "")
                applyImage(vmRecord.img)
        }

        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}