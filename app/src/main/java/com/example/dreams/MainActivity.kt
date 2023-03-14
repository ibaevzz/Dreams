package com.example.dreams

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var quote: TextView
    lateinit var author: TextView
    lateinit var newQuote: Button
    lateinit var newStar: Button
    lateinit var viewModel: QuoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[QuoteViewModel::class.java]

        quote = findViewById(R.id.quote)
        author = findViewById(R.id.author)
        newQuote = findViewById(R.id.new_quote)
        newStar = findViewById(R.id.new_star)
        quote.typeface = Typeface.createFromAsset(assets, "ds.ttf")
        author.typeface = Typeface.createFromAsset(assets, "ds.ttf")
        newQuote.typeface = Typeface.createFromAsset(assets, "ds.ttf")
        newStar.typeface = Typeface.createFromAsset(assets, "ds.ttf")

        viewModel.quo.observe(this){
            quote.text = it.quoteText
            author.text = it.quoteAuthor
        }

        newQuote.setOnClickListener{
            viewModel.getNewQuote().observe(this){
                quote.text = it.quoteText
                author.text = it.quoteAuthor
            }
        }

        newStar.setOnClickListener{
            viewModel.quo.observe(this){
                CoroutineScope(Dispatchers.IO).launch{
                    DreamSingl.getDao()?.insert(it)
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.star, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.star){
            val intent = Intent(this, StarsActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}