package ktepin.penzasoft.note.view

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import ktepin.penzasoft.note.R
import ktepin.penzasoft.note.databinding.FragmentNotificationsBinding
import ktepin.penzasoft.note.model.notification.Notification
import ktepin.penzasoft.note.util.Util
import ktepin.penzasoft.note.vm.NotificationsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment : Fragment() {

    private val viewModel: NotificationsViewModel by viewModel()
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.notification.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.notificationText.text = getString(R.string.no_notifications);
            } else {
                binding.notificationText.text =
                    "[${Util.intToTimeStr(it.hour)}:${Util.intToTimeStr(it.min)}] ${it.title}"
            }
        }

        binding.addNotification.setOnClickListener{onClickCreate()}
        binding.clearNotification.setOnClickListener{viewModel.clearNotification()}

        viewModel.getNotification()
        return root
    }

    private fun onClickCreate(){
        val text : String = binding.textField.editText?.text.toString()
        if (text != ""){
            val isSystem24Hour = is24HourFormat(activity)
            val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(clockFormat)
                    .setHour(12)
                    .setMinute(10)
                    .build()
            picker.show(parentFragmentManager, "tag");
            picker.addOnPositiveButtonClickListener {
                binding.textField.editText?.setText("")
                val notification = Notification(
                    text,
                    isSystem24Hour,
                    picker.hour,
                    picker.minute
                )
                viewModel.addNotification(notification)
                // call back code
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}