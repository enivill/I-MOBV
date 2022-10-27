package sk.mobv.androidprojectschool

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sk.mobv.androidprojectschool.adapter.ItemAdapter
import sk.mobv.androidprojectschool.data.DataSource
import sk.mobv.androidprojectschool.databinding.FragmentBusinessListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [BusinessListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BusinessListFragment : Fragment() {

    private var _binding: FragmentBusinessListBinding? = null
    private val binding get() = _binding!!
    private lateinit var businessListRecyclerView: RecyclerView
    private var isSortedAsc = true
    private val mAdapter = ItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        businessListRecyclerView = binding.businessListRv
        businessListRecyclerView.adapter = mAdapter

        binding.businessListBtnAdd.setOnClickListener() {
            val action = BusinessListFragmentDirections.actionBusinessListFragmentToFormFragment()
            view.findNavController().navigate(action)
        }
        chooseSort()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
         val sortButton = menu.findItem(R.id.action_sort)
        setIcon(sortButton)
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null)
            return

        menuItem.icon =
            if (isSortedAsc)
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_baseline_arrow_downward_24)
            else ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_baseline_arrow_upward_24)
    }


    private fun chooseSort() {
        when(isSortedAsc){
            true -> {
                DataSource.businesses = (DataSource.businesses.sortedBy { it.name }).toMutableList()
            }
            false -> {
                DataSource.businesses = (DataSource.businesses.sortedByDescending { it.name }).toMutableList()
            }

        }
        mAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                isSortedAsc = !isSortedAsc
                chooseSort()
                setIcon(item)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}