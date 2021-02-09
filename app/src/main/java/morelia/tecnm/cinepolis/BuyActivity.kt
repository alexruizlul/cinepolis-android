package morelia.tecnm.cinepolis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_buy.*
import morelia.tecnm.cinepolis.adaptadores.Funcion
import org.json.JSONObject
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class BuyActivity : AppCompatActivity() {

    private lateinit var funcion: Funcion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)

        funcion = intent.getSerializableExtra("funcion") as Funcion

        funcion.img?.let {
            if (it.isNotEmpty()) {
                Picasso.get().load(it).into(imgBuyCartel)
            }
        }
        textBuyTitulo.text = funcion.titulo
        textSala.text = funcion.sala

        conectaPost("http://192.168.3.83:10080/api.cinepolis.android/horarios.php")

        btnComprar.setOnClickListener {
            conecta2Post("http://192.168.3.83:10080/api.cinepolis.android/comprar.php")
        }
    }

    private fun conectaPost(url: String) {
        val MyStringRequest: StringRequest = object : StringRequest(
            Method.POST,
            url,
            Response.Listener {
                println("Respuesta: $it")
                interpreta(it)
            },
            Response.ErrorListener {
                println("Error.\n$it")
            }
        ) {
            override fun getParams(): Map<String, String> {
                val MyData: MutableMap<String, String> = HashMap()
                MyData["id"] = funcion.id
                return MyData
            }
        }
        Volley.newRequestQueue(this).add(MyStringRequest)
    }

    private fun conecta2Post(url: String) {
        val MyStringRequest: StringRequest = object : StringRequest(
            Method.POST,
            url,
            Response.Listener {
                println("Respuesta: $it")
                interpreta2(it)
            },
            Response.ErrorListener {
                println("Error.\n$it")
            }
        ) {
            override fun getParams(): Map<String, String> {
                val MyData: MutableMap<String, String> = HashMap()
                MyData["id"] = funcion.id
                MyData["cantidad"] = "${spinnerCantidad.selectedItemPosition + 1}"
                MyData["fecha"] = Calendar.getInstance().time.toString()
                MyData["boletos"] = "Pelicula: ${funcion.titulo}. Sala: ${funcion.sala}. Horario: ${spinnerHorarios.selectedItem}"
                MyData["usuario"] = "1"
                return MyData
            }
        }
        Volley.newRequestQueue(this).add(MyStringRequest)
    }

    private fun interpreta(response: String) {
        response?.let {
            if(response.contains("{") && response.contains("}")) {
                try {
                    val json = JSONObject(it)
                    val jsonArray = json.getJSONArray("output")
                    val lista = ArrayList<String>()
                    for (i in 0 until jsonArray.length()) {
                        val json = jsonArray.getJSONObject(i)

                        lista.add("${json.getString("horario")}")
                    }
                    spinnerHorarios.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,lista)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun interpreta2(response: String) {
        response?.let {
            if(response.contains("{") && response.contains("}")) {
                try {
                    val json = JSONObject(it)
                    val jsonArray = json.getJSONArray("output")

                    if(jsonArray.length() > 0) {
                        Toast.makeText(this, "Compra realizada", Toast.LENGTH_LONG).show()
                        finish()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

}