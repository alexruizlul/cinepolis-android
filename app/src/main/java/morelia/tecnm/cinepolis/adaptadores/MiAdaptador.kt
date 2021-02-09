package morelia.tecnm.cinepolis.adaptadores

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import morelia.tecnm.cinepolis.BuyActivity
import morelia.tecnm.cinepolis.R

class MiAdaptador(val ctx: Context, val res: Int, val lista:ArrayList<Funcion>) : RecyclerView.Adapter<MiAdaptador.FuncionVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FuncionVH {
        val view = View.inflate(ctx, res, null)
        return FuncionVH(view)
    }

    override fun onBindViewHolder(holder: FuncionVH, position: Int) {
        val funcion = lista[position]
        holder.bind(funcion)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    inner class FuncionVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(obj : Funcion) {
            val imgView = itemView.findViewById<ImageView>(R.id.imgRow)
            val txtViewTitulo = itemView.findViewById<TextView>(R.id.textTituloRow)
            val txtViewSala = itemView.findViewById<TextView>(R.id.textSalaRow)
            val btn = itemView.findViewById<Button>(R.id.btnHorariosRow)

            if(obj.img != null) {
                if (obj.img.isNotEmpty()) {
                    Picasso.get().load(obj.img).into(imgView)
                }
            }

            txtViewTitulo.setText(obj.titulo)
            txtViewSala.text = obj.sala

            btn.setOnClickListener {
                val intent = Intent(itemView.context, BuyActivity::class.java)
                intent.putExtra("funcion", obj)
                itemView.context.startActivity(intent)
            }

        }

    }

}