package sk.mobv.androidprojectschool.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import sk.mobv.androidprojectschool.R
import sk.mobv.androidprojectschool.domain.Business
import sk.mobv.androidprojectschool.ui.ItemAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, listData: List<Business>?) {
    val adapter = recyclerView.adapter as ItemAdapter
    adapter.submitList(null)
    adapter.submitList(listData)
}

@BindingAdapter("businessType")
fun chooseBusinessTypIcon(imageView: ImageView, businessType: String) {
    imageView.setImageResource(
        when(businessType){
            "pub" -> R.drawable.ic_pub
            "bar" -> R.drawable.ic_bar
            else -> R.drawable.ic_nightclub
        }
    )
}