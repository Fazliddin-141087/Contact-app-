package uz.mobiler.kontakt.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mobiler.kontakt.databinding.ItemRvBinding
import uz.mobiler.kontakt.models.Contact

class RvAdapter(var list: ArrayList<Contact>,var onpress:MyCallItemOnClickListener) : RecyclerView.Adapter<RvAdapter.Vh>() {

    inner class Vh(var itemRvBinding: ItemRvBinding,) : RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(contact: Contact,position: Int) {
            itemRvBinding.nameTv.text=contact.name
            itemRvBinding.phoneTv.text=contact.number

            itemRvBinding.callBtn.setOnClickListener {
                onpress.callItemOnClick(contact, position)
            }

            itemRvBinding.sendSmsBtn.setOnClickListener {
                onpress.sendSmsItemOnClick(contact, position)
            }

            itemRvBinding.editBtn.setOnClickListener {
                onpress.imgItemOnClick(contact, position)
            }

            itemRvBinding.root.setOnClickListener {
                onpress.itemOnClick(contact, position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface MyCallItemOnClickListener{
        fun callItemOnClick(contact: Contact,position: Int)
        fun sendSmsItemOnClick(contact: Contact,position: Int)
        fun itemOnClick(contact: Contact,position: Int)
        fun imgItemOnClick(contact: Contact,position: Int)
    }

}