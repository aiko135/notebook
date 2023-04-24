package ktepin.penzasoft.note.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ktepin.penzasoft.note.R
import ktepin.penzasoft.note.model.record.Record

class RecordListAdapter : RecyclerView.Adapter<RecordListAdapter.ViewHolder>() {
    private var records: List<Record> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            textView = view.findViewById(R.id.rvTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_record_elem, parent, false)
        view.setOnClickListener{
            //Нажатие на элемент списка
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record : Record = records[position]
        holder.textView.text = record.title
    }

    override fun getItemCount(): Int {
        return records.size
    }


    fun setRecords(newRecords:List<Record>){
        records = newRecords
        notifyDataSetChanged()
    }
}