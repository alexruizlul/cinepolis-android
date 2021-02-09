package morelia.tecnm.cinepolis.adaptadores

import java.io.Serializable

data class Funcion(
    val id: String,
    val titulo:String,
    val sala: String,
    val horarios:ArrayList<String>,
    val img:String?,
) : Serializable