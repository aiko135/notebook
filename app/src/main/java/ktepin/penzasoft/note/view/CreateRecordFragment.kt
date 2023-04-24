package ktepin.penzasoft.note.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ktepin.penzasoft.note.R
import ktepin.penzasoft.note.databinding.FragmentCreateRecordBinding
import ktepin.penzasoft.note.model.record.LatLng
import ktepin.penzasoft.note.util.CameraManager
import ktepin.penzasoft.note.vm.CreateRecordViewModel
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
            intent.action = Intent.ACTION_GET_CONTENT //TODO use ACTION_OPEN_CONTENT чтобы поулчить постоянный URI, этот URI временный
            selectImageLauncher.launch(intent)
        }

        binding.openCamera.setOnClickListener {
            val intent = cameraManager.getIntent()
            intent?.let {
                takePhotoLauncher.launch(intent)
            }
        }

        binding.addGeotag.setOnClickListener{ getLocation() }

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
            if(it.img != "")
                applyImage(it.img)
            if (it.geotag != null)
                applyGeoTag(it.geotag!!)
        })

        binding.saveButton.setOnClickListener {
            binding.saveButton.isEnabled = false
            if( binding.textField.editText?.text.toString() != ""){
                viewModel.persist()
                this.findNavController().navigate(R.id.action_navigation_create_to_navigation_home)
            }

        }

        return root
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        try {
            val lm = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val hasGps = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if(hasGps){
                val gpsLocationListener: LocationListener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        viewModel.setGeoTag(location.latitude.toFloat(),location.longitude.toFloat())
                    }

                    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                    override fun onProviderEnabled(provider: String) {}
                    override fun onProviderDisabled(provider: String) {}
                }
                lm.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    0F,
                    gpsLocationListener
                )
            }
            else{
                Toast.makeText(requireActivity(), "Cannot get your location", Toast.LENGTH_LONG).show()
            }
        }
        catch (e: Exception){
            Toast.makeText(requireActivity(), "Cannot get your location", Toast.LENGTH_LONG).show()
        }

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

    private fun applyGeoTag(latLng: LatLng){
        binding.geotagText.text = "[${latLng.lat}, ${latLng.lng}]"
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
            if(vmRecord.geotag != null)
                applyGeoTag(vmRecord.geotag!!)
        }

        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}