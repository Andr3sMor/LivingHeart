import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel // Cambia ViewModel por AndroidViewModel

class PerfilUsuarioViewModel(application: Application) : AndroidViewModel(application) { // Hereda de AndroidViewModel

    // Clave para identificar el archivo de SharedPreferences
    private val PREFERENCES_FILE_KEY = "com.cardiovascularmodel.livingheart.USER_PREFERENCES"

    // Obtén una instancia de SharedPreferences
    private val sharedPreferences = application.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)

    var nombre by mutableStateOf(sharedPreferences.getString("nombre", "") ?: "")
    var apellido by mutableStateOf(sharedPreferences.getString("apellido", "") ?: "")
    var peso by mutableStateOf(sharedPreferences.getString("peso", "") ?: "")
    var estatura by mutableStateOf(sharedPreferences.getString("estatura", "") ?: "")
    var edad by mutableStateOf(sharedPreferences.getString("edad", "") ?: "")
    var actividadFisica by mutableStateOf(sharedPreferences.getString("actividadFisica", "") ?: "")

    var dropdownExpanded by mutableStateOf(false)
    val listaActividades = listOf("Sedentario", "Ligera", "Moderada", "Intensa")

    // Función para guardar los datos del usuario
    fun guardarDatosUsuario() {
        with(sharedPreferences.edit()) {
            putString("nombre", nombre)
            putString("apellido", apellido)
            putString("peso", peso)
            putString("estatura", estatura)
            putString("edad", edad)
            putString("actividadFisica", actividadFisica)
            apply() // apply() guarda los datos de forma asíncrona
        }
    }

    // Opcional: Función para cargar los datos (ya se hace en la inicialización de las propiedades)
    // fun cargarDatosUsuario() {
    //     nombre = sharedPreferences.getString("nombre", "") ?: ""
    //     apellido = sharedPreferences.getString("apellido", "") ?: ""
    //     peso = sharedPreferences.getString("peso", "") ?: ""
    //     estatura = sharedPreferences.getString("estatura", "") ?: ""
    //     edad = sharedPreferences.getString("edad", "") ?: ""
    //     actividadFisica = sharedPreferences.getString("actividadFisica", "") ?: ""
    // }
}