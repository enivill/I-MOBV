package sk.mobv.androidprojectschool

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import sk.mobv.androidprojectschool.data.DataSource
import sk.mobv.androidprojectschool.databinding.FragmentBusinessDetailBinding
import sk.mobv.androidprojectschool.databinding.FragmentFormSummaryBinding

/**
 * A simple [Fragment] subclass.
 * Use the [BusinessDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BusinessDetailFragment : Fragment() {

    private var _binding: FragmentBusinessDetailBinding? = null
    private val binding get() = _binding!!
    private val args: BusinessDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBusinessDetailBinding.inflate(inflater, container, false)

        binding.frBusinessDetailBtMap.setOnClickListener {
            val latitude = args.latitude
            val longitude = args.longitude
            val location: Uri = Uri.parse("geo:${latitude},${longitude}?z=14&q=${latitude},${longitude}") // z - zoom level, q - marker
            val mapIntent = Intent(
                Intent.ACTION_VIEW,
                location
            )
            startActivity(mapIntent)
        }

        binding.frBusinessDetailBtWebsite.setOnClickListener {
            val website = args.website
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(website));
            startActivity(webIntent)
        }

        binding.frBusinessDetailBtDelete.setOnClickListener{
            DataSource.businesses.remove(DataSource.businesses.find { e -> e.id == args.id })
            val action = BusinessDetailFragmentDirections.actionBusinessDetailFragmentToBusinessListFragment()
            binding.root.findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = args.name
        val latitude = args.latitude
        val longitude = args.longitude
        val website = args.website
        binding.frBusinessDetailName.text = name.toString()
        binding.frBusinessDetailLatitude.text = latitude.toString()
        binding.frBusinessDetailLongitude.text = longitude.toString()

        if(website==""){
            binding.frBusinessDetailBtWebsite.visibility = View.GONE
        }

    }
}