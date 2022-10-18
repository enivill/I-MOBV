package sk.mobv.androidprojectschool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import sk.mobv.androidprojectschool.databinding.FragmentBusinessBinding
import sk.mobv.androidprojectschool.databinding.FragmentFormBinding

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