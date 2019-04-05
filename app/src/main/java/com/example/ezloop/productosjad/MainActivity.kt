package com.example.ezloop.productosjad

import android.content.Context
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
import android.widget.Toast
import com.example.ezloop.productosjad.Data.Product
import com.example.ezloop.productosjad.RVAdapters.ProductListAdapter
import com.example.ezloop.productosjad.RVAdapters.ShoppingListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Handler for navigation bar
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            //Selected shopping cart
            R.id.navigation_cart -> {
                //message.setText(R.string.title_cart)
                scAdapter?.notifyDataSetChanged()
                scAdapter?.updateTotal()
                txtTotal?.text = "$" + (scAdapter?.Total).toString()
                hideUserInfo()
                showShoppingCart()
                return@OnNavigationItemSelectedListener true
            }
            //Selected settings
            R.id.navigation_settings -> {
                //message.setText(R.string.title_settings)
                hideShoppingCart()
                showUserInfo()
                return@OnNavigationItemSelectedListener true
            }
            //Selected category
            R.id.navigation_category -> {
                message.setText(R.string.title_category)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    //Animation variables
    private val animationDuration = 500L
    var shoppingcartView: ConstraintLayout? = null
    var userInfoView: ConstraintLayout? = null
    var scAdapter: ShoppingListAdapter? = null
    var txtTotal: TextView? = null

    //User data fields
    var txtName: TextView? = null
    var txtPhone: TextView? = null
    var txtAddress: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize user data elements
        txtName = findViewById(R.id.txtBsnName) as TextView
        txtPhone = findViewById(R.id.txtPhone) as TextView
        txtAddress = findViewById(R.id.txtAddress) as TextView


        //Shopping cart elements
        txtTotal = findViewById(R.id.txtTotal) as TextView

        //Move Layouts to starting position
        shoppingcartView = findViewById(R.id.clShoppingCart)
        userInfoView = findViewById(R.id.clUserInfo)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        shoppingcartView?.x = size.x.toFloat()
        userInfoView?.x = size.x.toFloat()

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

        //Set handler for navigation bar
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

        //Read preference data
        val sharedPreferences = this.getPreferences(Context.MODE_PRIVATE) ?: return
        txtName?.text = sharedPreferences.getString(getString(R.string.key_bsns_name), "")
        txtAddress?.text = sharedPreferences.getString(getString(R.string.key_address), "")
        txtPhone?.text = sharedPreferences.getString(getString(R.string.key_phone), "")
    }

    //Show shopping cart
    fun showShoppingCart(){
        shoppingcartView?.animate()?.x(0.0f)?.duration = animationDuration
    }

    //Hide shopping cart
    fun hideShoppingCart(){
        shoppingcartView?.animate()?.x(shoppingcartView?.width!!.toFloat())?.duration = animationDuration
    }

    //Show shopping cart
    fun showUserInfo(){
        userInfoView?.animate()?.x(0.0f)?.duration = animationDuration
    }

    //Hide shopping cart
    fun hideUserInfo(){
        userInfoView?.animate()?.x(shoppingcartView?.width!!.toFloat())?.duration = animationDuration
    }

    //Save button
    fun saveButton(theView: View){
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()){
            putString(getString(R.string.key_bsns_name), txtName?.text.toString())
            putString(getString(R.string.key_address), txtAddress?.text.toString())
            putString(getString(R.string.key_phone), txtPhone?.text.toString())
            commit()
        }
        hideUserInfo()
    }

    //Return button
    fun returnButton(theView: View){
        hideShoppingCart()
    }

    //Checkout button
    fun checkoutButton(theView: View){
        val sharedPreferences = this.getPreferences(Context.MODE_PRIVATE) ?: return
        if (sharedPreferences.getString(getString(R.string.key_bsns_name), "").isNullOrEmpty()
            || sharedPreferences.getString(getString(R.string.key_address), "").isNullOrEmpty()
            || sharedPreferences.getString(getString(R.string.key_phone), "").isNullOrEmpty()){
            Toast.makeText(this, "Please enter your info", Toast.LENGTH_SHORT).show()
            showUserInfo()
        }
        else{
            Toast.makeText(this, "Order has been placed", Toast.LENGTH_SHORT).show()
        }
    }
}
