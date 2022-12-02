package ktepin.penzasoft.dairy.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ktepin.penzasoft.dairy.databinding.FragmentCreateRecordBinding
import ktepin.penzasoft.dairy.vm.CreateRecordViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateRecordFragment : Fragment() {

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

        binding.addPhoto.setOnClickListener{
            takePhoto()
        }
        return root
    }

    val REQUEST_IMAGE_CAPTURE = 1
    fun takePhoto(){
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            // display error state to the user
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}