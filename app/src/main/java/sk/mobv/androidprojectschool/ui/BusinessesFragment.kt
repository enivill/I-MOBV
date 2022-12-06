package sk.mobv.androidprojectschool.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import sk.mobv.androidprojectschool.R
import sk.mobv.androidprojectschool.databinding.FragmentBusinessListBinding
import sk.mobv.androidprojectschool.databinding.FragmentBusinessesBinding

class BusinessesFragment : Fragment() {

    private var _binding: FragmentBusinessesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessesBinding.inflate(inflater, container, false)
        return binding.root
    }


    fun goToAddBusinessScreen() {}

}