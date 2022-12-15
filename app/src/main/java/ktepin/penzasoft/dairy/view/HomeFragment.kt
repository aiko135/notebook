package ktepin.penzasoft.dairy.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ktepin.penzasoft.dairy.R
import ktepin.penzasoft.dairy.databinding.FragmentHomeBinding
import ktepin.penzasoft.dairy.vm.HomeViewModel
import ktepin.penzasoft.dairy.vm.adapter.RecordListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private lateinit var recordRecyclerViewAdapter: RecordListAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Recycler view
        recordRecyclerViewAdapter = RecordListAdapter()
        binding.feedbacksRv.adapter = recordRecyclerViewAdapter
        binding.feedbacksRv.layoutManager = LinearLayoutManager(context)

        homeViewModel.recordList.observe(viewLifecycleOwner, Observer {
            //обновление данных ViewModel
            recordRecyclerViewAdapter.setRecords(it)

        })

        homeViewModel.loadRecords()

        binding.floatingActionButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_navigation_home_to_navigation_create)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}