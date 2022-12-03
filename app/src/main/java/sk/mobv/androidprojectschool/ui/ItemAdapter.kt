package sk.mobv.androidprojectschool.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sk.mobv.androidprojectschool.databinding.ListItemBinding
import sk.mobv.androidprojectschool.domain.Business



class ItemAdapter(
                  private val businessEventListener: BusinessEventListener
) : ListAdapter<Business, ItemAdapter.BusinessItemViewHolder>(DiffCallback) {

    class BusinessItemViewHolder(var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(businessEventListener: BusinessEventListener, business: Business) {
            binding.business = business
            binding.businessEventListener = businessEventListener
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Business>() {
        override fun areItemsTheSame(oldItem: Business, newItem: Business): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Business, newItem: Business): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessItemViewHolder {
        return BusinessItemViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BusinessItemViewHolder, position: Int) {
        val business: Business = getItem(position)
        holder.bind(businessEventListener, business)
    }

}

class BusinessEventListener(val clickListener: (ownerName: String?, businessId: Long) -> Unit) {
    fun onClick(ownerName: String?, businessId: Long) = clickListener(ownerName, businessId)
}