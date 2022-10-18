package sk.mobv.androidprojectschool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.findNavController
import sk.mobv.androidprojectschool.databinding.FragmentFormBinding

class FormFragment : Fragment() {
    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFormBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // with BINDING
        binding.frFormBtSubmit.setOnClickListener {
            val action = FormFragmentDirections.actionFormFragmentToBusinessFragment(
                name = binding.frFormEtName.text.toString(),
                businessName = binding.frFormEtBusinessName.text.toString(),
                latitude = binding.frFormEtLatitude.text.toString(),
                longitude = binding.frFormEtLongitude.text.toString()
            )
            view.findNavController().navigate(action)

            //Without BINDING
//            val nameEt: EditText = requireView().findViewById(R.id.fr_form_et_name)
//            val businessNameEt: EditText = requireView().findViewById(R.id.fr_form_et_business_name)
//            val latitudeEt: EditText = requireView().findViewById(R.id.fr_form_et_latitude)
//            val longitudeEt: EditText = requireView().findViewById(R.id.fr_form_et_longitude)
//
//            val name = nameEt.text.toString()
//            val businessName = businessNameEt.text.toString()
//            val latitude = latitudeEt.text.toString()
//            val longitude = longitudeEt.text.toString()
//
//            val action = FormFragmentDirections.actionFormFragmentToBusinessFragment(
//                name,
//                businessName,
//                latitude,
//                longitude
//            )
//            view.findNavController().navigate(action)
        }
    }
}