package sk.mobv.androidprojectschool.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import sk.mobv.androidprojectschool.BusinessApplication
import sk.mobv.androidprojectschool.R
import sk.mobv.androidprojectschool.databinding.FragmentFormBinding
import sk.mobv.androidprojectschool.domain.Business
import sk.mobv.androidprojectschool.viewModels.BusinessViewModel
import sk.mobv.androidprojectschool.viewModels.BusinessViewModelFactory

class FormFragment : Fragment() {
    private val navigationArgs: FormFragmentArgs by navArgs()

    private val businessViewModel: BusinessViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(
            this,
            BusinessViewModelFactory((activity.application as BusinessApplication).repository)
        )[BusinessViewModel::class.java]
    }

    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!

    lateinit var business: Business
    private val businessTypesSelection: Map<String, Int> =
        mapOf(
            "bar" to 0,
            "pub" to 1,
            "nightclub" to 2
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fillBusinessTypes()
        val businessId = navigationArgs.businessId
        if (businessId > 0) {
            businessViewModel.getBusiness(businessId).observe(this.viewLifecycleOwner) { business ->
                this.business = business
                bindBusiness()
            }

            binding.deleteButton.visibility = View.VISIBLE
            binding.deleteButton.setOnClickListener {
                showDeleteBusinessConfirmationDialog()
            }
        } else {
            binding.saveButton.setOnClickListener {
                addBusiness()
            }
        }
    }

    private fun fillBusinessTypes() {
        val spinner = binding.businessType
        spinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(R.array.business_types)
        )
    }

    private fun bindBusiness() {
        binding.apply {
            businessNameEditText.setText(business.name)
            ownerNameEditText.setText(business.ownerName)
            businessTypesSelection[business.type]?.let { businessType.setSelection(it) }
            latitudeEditText.setText(business.latitude)
            longitudeEditText.setText(business.longitude)
            phoneNumberEditText.setText(business.phoneNumber)
            webUrlEditText.setText(business.webPage)
            saveButton.setOnClickListener {
                updateBusiness()
            }
        }
    }

    private fun showDeleteBusinessConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question, business.name ?: "", business.ownerName ?: ""))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ -> deleteBusiness() }
            .show()
    }

    private fun deleteBusiness() {
        businessViewModel.deleteBusiness(business)
        goToBusinessListScreen()
    }

    private fun addBusiness() {
        if (validateEntry()) {
            businessViewModel.addBusiness(
                binding.run {
                    Business(
                        name = businessNameEditText.text.toString(),
                        ownerName = ownerNameEditText.text.toString(),
                        type = businessType.selectedItem.toString(),
                        latitude = latitudeEditText.text.toString(),
                        longitude = longitudeEditText.text.toString(),
                        phoneNumber = phoneNumberEditText.text.toString(),
                        webPage = webUrlEditText.text.toString()
                    )
                }
            )
            showSuccessMessage(getString(R.string.succesfully_added))
            goToBusinessListScreen()
        }
    }

    private fun updateBusiness() {
        if (validateEntry()) {
            businessViewModel.editBusiness(
                binding.run {
                    Business(
                        id = navigationArgs.businessId,
                        name = businessNameEditText.text.toString(),
                        ownerName = ownerNameEditText.text.toString(),
                        type = businessType.selectedItem.toString(),
                        latitude = latitudeEditText.text.toString(),
                        longitude = longitudeEditText.text.toString(),
                        phoneNumber = phoneNumberEditText.text.toString(),
                        webPage = webUrlEditText.text.toString()
                    )
                }
            )
            showSuccessMessage(getString(R.string.succesfully_updated))
            goToBusinessScreen()
        }
    }

    private fun goToBusinessListScreen() {
        findNavController().navigate(R.id.action_formFragment_to_businessListFragment)
    }

    private fun goToBusinessScreen() {
        findNavController().navigate(
            FormFragmentDirections.actionFormFragmentToBusinessDetailFragment(
                binding.ownerNameEditText.text.toString().ifEmpty { "" },
                navigationArgs.businessId
            )
        )
    }

    private fun showSuccessMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun validateEntry(): Boolean {
        val entryErrorResources: MutableList<Int> = businessViewModel.validateEntry(
            binding.latitudeEditText.text.toString(),
            binding.longitudeEditText.text.toString(),
        )

        if (entryErrorResources.isNotEmpty()) {

            Toast.makeText(
                requireContext(),
                makeEntryErrorMessage(entryErrorResources),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        return true
    }

    private fun makeEntryErrorMessage(entryErrorResources: MutableList<Int>): String {
        var errorEntryMessage = getString(entryErrorResources.removeAt(0))

        entryErrorResources.forEach {
            errorEntryMessage += ", " + getString(it)
        }

        return getString(R.string.is_required, errorEntryMessage)
    }
}