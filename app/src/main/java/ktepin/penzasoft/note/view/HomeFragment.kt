package ktepin.penzasoft.note.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ktepin.penzasoft.note.R
import ktepin.penzasoft.note.databinding.FragmentHomeBinding
import ktepin.penzasoft.note.view.adapter.RecordListAdapter
import ktepin.penzasoft.note.vm.HomeViewModel
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

        //Recycler view
        recordRecyclerViewAdapter = RecordListAdapter()
        binding.feedbacksRv.adapter = recordRecyclerViewAdapter
        binding.feedbacksRv.layoutManager = LinearLayoutManager(context)
        binding.feedbacksRv.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                DividerItemDecoration.VERTICAL
            )
        )

        homeViewModel.recordList.observe(viewLifecycleOwner, Observer {
            //обновление данных ViewModel
            recordRecyclerViewAdapter.setRecords(it)

        })

        binding.floatingActionButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_navigation_home_to_navigation_create)
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.loadRecords()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}