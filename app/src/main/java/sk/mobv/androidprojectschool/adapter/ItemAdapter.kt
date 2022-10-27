package sk.mobv.androidprojectschool.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sk.mobv.androidprojectschool.BusinessListFragmentDirections
import sk.mobv.androidprojectschool.R
import sk.mobv.androidprojectschool.data.DataSource


class ItemAdapter() :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val businessName: TextView = view.findViewById(R.id.item_title)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = DataSource.businesses[position]
        holder.businessName.text = item.name

        holder.businessName.setOnClickListener {
            val action =
                BusinessListFragmentDirections.actionBusinessListFragmentToBusinessDetailFragment(
                    item.name,
                    item.latitude.toString(),
                    item.longitude.toString(),
                    item.website,
                    item.id
                )
            holder.view.findNavController().navigate(action)
        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount(): Int {
        return DataSource.businesses.size
    }

}