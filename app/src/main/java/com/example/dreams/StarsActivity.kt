package com.example.dreams

import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StarsActivity : AppCompatActivity() {

    lateinit var stars: RecyclerView
    lateinit var viewModel: StarsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stars)

        stars = findViewById(R.id.stars)

        viewModel = ViewModelProvider(this)[StarsViewModel::class.java]

        updateRecycle()
    }

    private inner class StarsHolder(item: View): ViewHolder(item){
        val quote = item.findViewById<TextView>(R.id.quote)
        val author = item.findViewById<TextView>(R.id.author)
        val parent = item.findViewById<LinearLayout>(R.id.parent)
    }

    private inner class StarsAdapter(val list: List<Dream>): Adapter<StarsHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarsHolder {
            return StarsHolder(layoutInflater.inflate(R.layout.item_star, parent, false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: StarsHolder, position: Int) {
            holder.quote.typeface = Typeface.createFromAsset(assets, "ds.ttf")
            holder.author.typeface = Typeface.createFromAsset(assets, "ds.ttf")
            holder.quote.text = list[position].quoteText
            if(list[position].quoteAuthor==""){
                holder.author.text = "Без имени"
            }else {
                holder.author.text = list[position].quoteAuthor
            }

            holder.parent.setOnLongClickListener{
                val view = layoutInflater.inflate(R.layout.dialog, null)
                val dialog = AlertDialog.Builder(this@StarsActivity)
                dialog.setView(view)
                val d = dialog.create()
                view.findViewById<TextView>(R.id.copy_quote).setOnClickListener{
                    val clipboard = holder.quote.context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Order Number", holder.quote.text.toString())
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this@StarsActivity, "Цитата скопирована.", Toast.LENGTH_SHORT).show()
                    d.dismiss()
                }
                view.findViewById<TextView>(R.id.copy_name).setOnClickListener{
                    val clipboard = holder.author.context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Order Number", holder.author.text.toString())
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this@StarsActivity, "Имя автора скопировано.", Toast.LENGTH_SHORT).show()
                    d.dismiss()
                }
                view.findViewById<TextView>(R.id.delete).setOnClickListener{
                    CoroutineScope(Dispatchers.IO).launch {
                        DreamSingl.getDao()?.delete(list[position])
                        viewModel.update()
                        launch(Dispatchers.Main) {
                            updateRecycle()
                        }
                    }
                    d.dismiss()
                }
                d.show()
                return@setOnLongClickListener true
            }
        }
    }

    fun updateRecycle(){
        viewModel.starsList.observe(this){
            stars.layoutManager = LinearLayoutManager(this)
            stars.adapter = StarsAdapter(it)
            stars.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}