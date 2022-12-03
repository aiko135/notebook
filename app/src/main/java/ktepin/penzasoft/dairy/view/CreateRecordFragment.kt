package ktepin.penzasoft.dairy.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ktepin.penzasoft.dairy.databinding.FragmentCreateRecordBinding
import ktepin.penzasoft.dairy.vm.CreateRecordViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateRecordFragment : Fragment() {
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_PICK_IMAGE = 2

    private val createRecordViewModel: CreateRecordViewModel by viewModel()
    private var _binding: FragmentCreateRecordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreateRecordBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.openCamera.setOnClickListener{
            openCamera()
        }

        binding.addPhoto.setOnClickListener{
            selectPhoto()
        }

        return root
    }


    fun openCamera(){
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            // display error state to the user
        }
    }

    fun selectPhoto(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_IMAGE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}