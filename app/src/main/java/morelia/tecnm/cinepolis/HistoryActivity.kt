package morelia.tecnm.cinepolis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_history.*
import morelia.tecnm.cinepolis.adaptadores.Compra
import morelia.tecnm.cinepolis.adaptadores.MiAdaptadorCompra
import org.json.JSONObject

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        conecta("http://192.168.3.83:10080/api.cinepolis.android/historial.php")

        val btnHome = findViewById<Button>(R.id.btnHome)
        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun conecta(url: String) {
        val request = StringRequest(
                Request.Method.GET,
                url,
                Response.Listener { response ->
                    println(response.toString())
                    interpreta(response)
                },
                Response.ErrorListener { error ->
                    println("Hubo un error.\n$error")
                }
        )
        Volley.newRequestQueue(this).add(request)
    }

    private fun interpreta(response: String) {
        response?.let {
            if(response.contains("{") && response.contains("}")) {
                try {
                    val json = JSONObject(it)
                    val jsonArray = json.getJSONArray("output")
                    val lista = ArrayList<Compra>()
                    for (i in 0 until jsonArray.length()) {
                        val j = jsonArray.getJSONObject(i)

                        val compra = Compra(
                                j.getString("id_compra"),
                                j.getString("cantidad"),
                                j.getString("fecha"),
                                j.getString("boletos"),
                                j.getString("usuario"),
                                j.getString("id_funcion"),
                                "http://192.168.3.83:10080/api.cinepolis.android/img/ticket.jpg"
                        )

                        lista.add(compra)
                    }
                    recyclerCompras.adapter = MiAdaptadorCompra(this,R.layout.row_compra,lista)
                    recyclerCompras.layoutManager = LinearLayoutManager(this)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}