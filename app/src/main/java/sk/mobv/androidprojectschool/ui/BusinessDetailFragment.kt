package sk.mobv.androidprojectschool.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView
import sk.mobv.androidprojectschool.BusinessApplication
import sk.mobv.androidprojectschool.R
import sk.mobv.androidprojectschool.databinding.FragmentBusinessDetailBinding
import sk.mobv.androidprojectschool.viewModels.BusinessViewModel
import sk.mobv.androidprojectschool.viewModels.BusinessViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [BusinessDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BusinessDetailFragment : Fragment(){
    private val navigationArgs: BusinessDetailFragmentArgs by navArgs()

    private val businessViewModel: BusinessViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(
            this,
            BusinessViewModelFactory((activity.application as BusinessApplication).repository)
        )[BusinessViewModel::class.java]
    }

    private var _binding: FragmentBusinessDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var pourAnimation: LottieAnimationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        businessViewModel.getBusiness(navigationArgs.businessId)
        binding.apply {
            businessViewModel = this@BusinessDetailFragment.businessViewModel
            thisFragment = this@BusinessDetailFragment
            lifecycleOwner = this@BusinessDetailFragment
        }

        pourAnimation = binding.animationView

        binding.addButton.setOnTouchListener { _: View, motionEvent: MotionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    startAddWaterToGlass()
                }
                MotionEvent.ACTION_UP -> {
                    stopAnimateWater()
                }
            }
            true
        }
        binding.removeButton.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    startRemoveWaterFromGlass()
                }
                MotionEvent.ACTION_UP -> {
                    stopAnimateWater()
                }
            }
            true
        }

    }

    fun pourOutGlass() {
        if (pourAnimation.progress != 0F) {
            pourAnimation.speed = -5F
            pourAnimation.resumeAnimation()
        }
    }

    private fun startAddWaterToGlass() {
        if (pourAnimation.progress != 1F) {
            pourAnimation.speed = 1F
            pourAnimation.resumeAnimation()
        }
    }

    private fun startRemoveWaterFromGlass() {
        if (pourAnimation.progress != 0F) {
            pourAnimation.speed = -1F
            pourAnimation.resumeAnimation()
        }
    }

    private fun stopAnimateWater() {
        pourAnimation.pauseAnimation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun goToEditBusinessScreen() {
        findNavController().navigate(
            BusinessDetailFragmentDirections.actionBusinessDetailFragmentToFormFragment(
                navigationArgs.businessId
            )
        )
    }

    fun showOnMapIntent(latitude: String, longitude: String) {
        context?.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:${latitude},${longitude}")
            )
        )

    }

    fun callPhoneNumberIntent(phoneNumber: String) {
        context?.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phoneNumber}")))
    }

    fun showWebPageIntent(uriWeb: String) {
        if (uriWeb.startsWith("https://") || uriWeb.startsWith("http://")) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uriWeb))
            context?.startActivity(intent)
        } else {
            Toast.makeText(context, getString(R.string.invalid_url), Toast.LENGTH_SHORT).show()
        }
    }
}