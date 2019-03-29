package com.example.ezloop.productosjad.RVAdapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.ezloop.productosjad.Data.Product
import com.example.ezloop.productosjad.R

class ShoppingListAdapter(val shoppingList: ArrayList<Product>) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>(){

    var Total: Float = 0f

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.row_cart_list, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return shoppingList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val product: Product = shoppingList[p1]
        p0?.txtItemName.text = product.name
        p0?.txtItemPrice.text = "$" + (product.quantity * product.price).toString()

        p0?.btnDelete.setOnClickListener {
            shoppingList.removeAt(p1)
            notifyItemRemoved(p1)
            updateTotal()
        }
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        //Declare elements of shopping cart
        val txtItemName = itemView.findViewById(R.id.txtProdNameList) as TextView
        val txtItemPrice = itemView.findViewById(R.id.txtProdTotal) as TextView
        val btnDelete = itemView.findViewById(R.id.icoClear) as ImageView
    }

    fun updateTotal(){
        Total = 0f
        for (n in shoppingList){
            Total += (n.price * n.quantity)
        }
    }
}