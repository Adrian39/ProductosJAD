package com.example.ezloop.productosjad

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.example.ezloop.productosjad.Data.Product
import com.example.ezloop.productosjad.RVAdapters.ProductListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_cart -> {
                message.setText(R.string.title_cart)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

        //RECYCLER VIEWS
        //Products
        val rvProducts = findViewById(R.id.rvProducts) as RecyclerView
        rvProducts.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        val adapter = ProductListAdapter(productList)
        rvProducts.adapter = adapter
    }
}
