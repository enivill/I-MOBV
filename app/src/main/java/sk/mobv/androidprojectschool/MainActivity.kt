package sk.mobv.androidprojectschool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.airbnb.lottie.LottieAnimationView

//for binding
//import sk.mobv.animation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // WITH BINDING

    //    lateinit var binding: ActivityMainBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.button.setOnClickListener {
//            binding.animationView.playAnimation()
//        }
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            startAnimation()
        }
    }

    private fun startAnimation() {
        val animationView: LottieAnimationView = findViewById(R.id.animation_view)
        animationView.playAnimation()
    }

}
