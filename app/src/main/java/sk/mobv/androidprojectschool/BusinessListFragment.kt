package sk.mobv.androidprojectschool

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sk.mobv.androidprojectschool.adapter.ItemAdapter
import sk.mobv.androidprojectschool.data.DataSource
import sk.mobv.androidprojectschool.databinding.FragmentBusinessListBinding
import sk.mobv.androidprojectschool.databinding.FragmentFormSummaryBinding

/**
 * A simple [Fragment] subclass.
 * Use the [BusinessListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BusinessListFragment : Fragment() {

    private var _binding: FragmentBusinessListBinding? = null
    private val binding get() = _binding!!
    private lateinit var businessListRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataSource.loadBusinesses(requireContext())
        businessListRecyclerView = binding.businessListRv
        businessListRecyclerView.adapter = ItemAdapter()

        binding.businessListBtnAdd.setOnClickListener(){
            val action = BusinessListFragmentDirections.actionBusinessListFragmentToFormFragment()
            view.findNavController().navigate(action)
        }

    }
}