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

        binding.frFormBtSubmit.setOnClickListener {
            val action = FormFragmentDirections.actionFormFragmentToBusinessFragment(
                name = binding.frFormEtName.text.toString(),
                businessName = binding.frFormEtBusinessName.text.toString(),
                latitude = binding.frFormEtLatitude.text.toString(),
                longitude = binding.frFormEtLongitude.text.toString()
            )
            view.findNavController().navigate(action)
        }
    }
}