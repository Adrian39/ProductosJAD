package com.example.ezloop.productosjad.RVAdapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.ezloop.productosjad.Data.Product
import com.example.ezloop.productosjad.R

class ProductListAdapter(val productList:ArrayList<Product>) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>(){

    var currentOrder = ArrayList<Product>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.row_products_list, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return productList.size
    }


    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        //Assign current on product
        var product: Product = productList[p1]

        //Set product name
        p0?.txtProductName.text = product.name

        //Set product description
        p0?.txtProductDesc.text = product.desc

        //Set listener for "Add to order" button
        p0?.btnAddOrder.setOnClickListener {
            currentOrder.add(product)
            productList.removeAt(p1)
            notifyDataSetChanged()
            Toast.makeText(p0.btnAddOrder.context, "Added " +
            product.name, Toast.LENGTH_LONG).show()
        }

        //Set listener for "Skipp" button
        p0?.btnSkip.setOnClickListener{
            productList.removeAt(p1)
            notifyDataSetChanged()
        }

        //Set listener for Quantity seek bar
        p0?.sbQuantity.max = 10
        p0?.sbQuantity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                product.quantity = progress
                p0?.txtQuantity.text = "Amount: $progress"
            }

        })
        //Set product image
        p0?.imgProduct.setImageResource(product.imgID)
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduct = itemView.findViewById(R.id.imgProduct) as ImageView
        val txtProductName = itemView.findViewById(R.id.txtProdName) as TextView
        val txtProductDesc = itemView.findViewById(R.id.txtProdDesc) as TextView
        val sbQuantity = itemView.findViewById(R.id.sbQuantity) as SeekBar
        val btnAddOrder = itemView.findViewById(R.id.btnAddOrder) as Button
        val btnSkip = itemView.findViewById(R.id.btnSkip) as Button
        val txtQuantity = itemView.findViewById(R.id.txtQuantity) as TextView
    }
}