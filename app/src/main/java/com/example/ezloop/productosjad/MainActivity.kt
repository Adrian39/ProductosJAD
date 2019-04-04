package com.example.ezloop.productosjad

import android.graphics.Point
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.ezloop.productosjad.Data.Product
import com.example.ezloop.productosjad.RVAdapters.ProductListAdapter
import com.example.ezloop.productosjad.RVAdapters.ShoppingListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_cart -> {
                //message.setText(R.string.title_cart)
                scAdapter?.notifyDataSetChanged()
                scAdapter?.updateTotal()
                txtTotal?.text = "$" + (scAdapter?.Total).toString()
                showShoppingCart()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                message.setText(R.string.title_settings)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_category -> {
                message.setText(R.string.title_category)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private val animationDuration = 500L
    var shoppingcartView: ConstraintLayout? = null
    var scAdapter: ShoppingListAdapter? = null
    var txtTotal: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Shopping cart Total
        txtTotal = findViewById(R.id.txtTotal) as TextView

        //Move Layouts to starting position
        shoppingcartView = findViewById(R.id.clShoppingCart)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        shoppingcartView?.x = size.x.toFloat()

        var productList = ArrayList<Product>()
        productList.add(
            Product(1L, "Test1", "Description1",
                R.drawable.test_image_1, 40.00f, 0))
        productList.add(
            Product(2L, "Test2", "Description2",
                R.drawable.test_image_2, 80.00f, 0))
        productList.add(
            Product(3L, "Test3", "Description3",
                R.drawable.test_image_3, 12.00f, 0))

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //Products RecyclerView
        val rvProducts = findViewById(R.id.rvProducts) as RecyclerView
        rvProducts.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val adapter = ProductListAdapter(productList)
        rvProducts.adapter = adapter

        //Shopping cart RecyclerView
        val rvShoppingCart = findViewById(R.id.rvShoppingCart) as RecyclerView
        rvShoppingCart.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        scAdapter = ShoppingListAdapter(adapter.currentOrder, txtTotal)
        rvShoppingCart.adapter = scAdapter
    }

    //Show shopping cart
    fun showShoppingCart(){
        shoppingcartView?.animate()?.x(0.0f)?.duration = animationDuration
    }

    //Hide shopping cart
    fun hideShoppingCart(theView: View){
        shoppingcartView?.animate()?.x(shoppingcartView?.width!!.toFloat())?.duration = animationDuration
    }
}
