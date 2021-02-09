package morelia.tecnm.cinepolis.adaptadores

import java.io.Serializable

data class Compra(
        val id: String,
        val cantidad: String,
        val fecha: String,
        val boletos: String,
        val usuario: String,
        val idFuncion: String,
        val imgTicket: String,
) : Serializable