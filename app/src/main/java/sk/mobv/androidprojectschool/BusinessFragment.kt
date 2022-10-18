package sk.mobv.androidprojectschool

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import sk.mobv.androidprojectschool.databinding.FragmentBusinessBinding

/**
 * A simple [Fragment] subclass.
 * Use the [BusinessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BusinessFragment : Fragment() {
    private var _binding: FragmentBusinessBinding? = null
    private val binding get() = _binding!!
    private val args: BusinessFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBusinessBinding.inflate(inflater, container, false)

        binding.frBusinessBtMap.setOnClickListener {
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
        binding.frBusinessTvName.text = name.toString()
        binding.frBusinessTvBusinessName.text = businessName.toString()

        // without BINDING
//        val nameTv: TextView = view.findViewById(R.id.fr_business_tv_name)
//        val businessNameTv: TextView = view.findViewById(R.id.fr_business_tv_business_name)
//        val name = args.name
//        val businessName = args.businessName
//        nameTv.text = name.toString()
//        businessNameTv.text = businessName.toString()

        // start animation
        binding.animationView.playAnimation()
    }
}