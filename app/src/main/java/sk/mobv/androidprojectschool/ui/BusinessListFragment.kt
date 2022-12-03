package sk.mobv.androidprojectschool.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import sk.mobv.androidprojectschool.BusinessApplication
import sk.mobv.androidprojectschool.R
import sk.mobv.androidprojectschool.data.SettingsDataStore
import sk.mobv.androidprojectschool.databinding.FragmentBusinessListBinding
import sk.mobv.androidprojectschool.viewModels.BusinessViewModel
import sk.mobv.androidprojectschool.viewModels.BusinessViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [BusinessListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BusinessListFragment : Fragment() {

    private var _binding: FragmentBusinessListBinding? = null
    private val binding get() = _binding!!
    private lateinit var settingsDataStore: SettingsDataStore
    private val businessViewModel: BusinessViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(
            this,
            BusinessViewModelFactory((activity.application as BusinessApplication).repository)
        )[BusinessViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //TODO
        super.onViewCreated(view, savedInstanceState)

        settingsDataStore = SettingsDataStore(requireContext())
        businessViewModel.networkError.observe(this.viewLifecycleOwner) { isNetworkError ->
            isNetworkError?.let { handleRefreshResult(it) }
        }

        setMenuBar()

        refreshDataOnStart()

        bind()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setMenuBar() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.layout_menu, menu)

                val layoutButton = menu.findItem(R.id.action_sort)
                layoutButton.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_arrow_downward_24)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_sort -> {
                        val isSortedAsc = chooseSort()
                        setIcon(menuItem, isSortedAsc)
                        return true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun bind() {
        binding.apply {
            businessViewModel = this@BusinessListFragment.businessViewModel
            thisFragment = this@BusinessListFragment
            lifecycleOwner = this@BusinessListFragment.viewLifecycleOwner
            businessListRv.adapter = ItemAdapter(
                BusinessEventListener { ownerName: String?, businessId: Long ->
                    findNavController()
                        .navigate(
                            BusinessListFragmentDirections.actionBusinessListFragmentToBusinessDetailFragment(
                                businessId = businessId,
                                ownerName = ownerName ?: ""
                            )
                        )
                })
            refreshLayout.setOnRefreshListener {
                this@BusinessListFragment.businessViewModel.refreshDataFromRepository()
                refreshLayout.isRefreshing = false
            }
        }
    }

    fun goToAddBusinessScreen() {
        findNavController().navigate(R.id.action_businessListFragment_to_formFragment)
    }

    private fun chooseSort(): Boolean {
        return businessViewModel.sortBusinessList()
    }

    private fun setIcon(menuItem: MenuItem?, isSortedAsc: Boolean) {
        if (menuItem == null)
            return

        menuItem.icon =
            if (isSortedAsc)
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_baseline_arrow_downward_24)
            else ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_baseline_arrow_upward_24)
    }

    private fun handleRefreshResult(isNetworkError: Boolean) {
        if (isNetworkError) {
            Toast.makeText(
                requireContext(),
                getString(R.string.network_error),
                Toast.LENGTH_LONG
            ).show()
        } else {
            lifecycleScope.launch {
                settingsDataStore.saveIsBusinessRefreshedPreferencesStore(true)
            }
        }
    }

    private fun refreshDataOnStart() {
        settingsDataStore.refreshDataPreferences.asLiveData()
            .observe(viewLifecycleOwner) { isBusinessesRefreshed ->
                if (!isBusinessesRefreshed) {
                    businessViewModel.refreshDataFromRepository()
                }
            }
    }
}