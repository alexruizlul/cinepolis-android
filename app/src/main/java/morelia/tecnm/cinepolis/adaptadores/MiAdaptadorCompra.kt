package morelia.tecnm.cinepolis.adaptadores

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import morelia.tecnm.cinepolis.BuyActivity
import morelia.tecnm.cinepolis.R

class MiAdaptadorCompra(val ctx: Context, val res: Int, val lista:ArrayList<Compra>) : RecyclerView.Adapter<MiAdaptadorCompra.CompraVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompraVH {
        val view = View.inflate(ctx, res, null)
        return CompraVH(view)
    }

    override fun onBindViewHolder(holder: CompraVH, position: Int) {
        val compra = lista[position]
        holder.bind(compra)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    inner class CompraVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(obj : Compra) {
            val imgTicketView = itemView.findViewById<ImageView>(R.id.imgTicketRow)
            val txtViewBoleto = itemView.findViewById<TextView>(R.id.textBoletoRow)
            val txtViewFecha = itemView.findViewById<TextView>(R.id.textFechaRow)
            val btn = itemView.findViewById<Button>(R.id.btnHorariosRow)

            if(obj.imgTicket != null) {
                if (obj.imgTicket.isNotEmpty()) {
                    Picasso.get().load(obj.imgTicket).into(imgTicketView)
                }
            }

            txtViewBoleto.setText(obj.boletos)
            txtViewFecha.text = obj.fecha

            // Es el botón que te permite comprar, por ahora omitelo.
            /*
            btn.setOnClickListener {
                val intent = Intent(itemView.context, BuyActivity::class.java)
                intent.putExtra("funcion", obj)
                itemView.context.startActivity(intent)
            }*/

        }

    }
}