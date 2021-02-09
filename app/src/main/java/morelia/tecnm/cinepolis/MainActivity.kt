package morelia.tecnm.cinepolis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import morelia.tecnm.cinepolis.adaptadores.Funcion
import morelia.tecnm.cinepolis.adaptadores.MiAdaptador
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        conecta("http://192.168.3.83:10080/api.cinepolis.android/funciones.php")

        val btnCompras = findViewById<Button>(R.id.btnCompras)
        btnCompras.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
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
                    val lista = ArrayList<Funcion>()
                    for (i in 0 until jsonArray.length()) {
                        val j = jsonArray.getJSONObject(i)

                        val funcion = Funcion(
                                j.getString("id_funcion"),
                                j.getString("nombre_funcion"),
                                j.getString("sala"),
                                ArrayList<String>(),
                                j.getString("cartel")
                        )

                        lista.add(funcion)
                    }
                    recyclerFunciones.adapter = MiAdaptador(this, R.layout.row_funcion, lista)
                    recyclerFunciones.layoutManager = LinearLayoutManager(this)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

}