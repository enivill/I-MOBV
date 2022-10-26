package sk.mobv.androidprojectschool

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import sk.mobv.androidprojectschool.databinding.FragmentFormSummaryBinding

/**
 * A simple [Fragment] subclass.
 * Use the [FormSummaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FormSummaryFragment : Fragment() {
    private var _binding: FragmentFormSummaryBinding? = null
    private val binding get() = _binding!!
    private val args: FormSummaryFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFormSummaryBinding.inflate(inflater, container, false)

        binding.frFormSummaryBtMap.setOnClickListener {
            val latitude = args.latitude
            val longitude = args.longitude
            val location: Uri = Uri.parse("geo:${latitude},${longitude}?z=14&q=${latitude},${longitude}") // z - zoom level, q - marker
            val mapIntent = Intent(
                Intent.ACTION_VIEW,
                location
            )
            startActivity(mapIntent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // with BINDING
        val businessName = args.businessName
        val name = args.name
        binding.frFormSummaryTvName.text = name.toString()
        binding.frFormSummaryTvBusinessName.text = businessName.toString()

        // start animation
        binding.animationView.playAnimation()
    }
}