import android.app.Application
import com.pluto.Pluto
import com.pluto.plugins.network.PlutoNetworkPlugin

const val PLUTO_NETWORK_PLUGIN = "network"

object Initializer {
    fun initPluto(app: Application) {
        Pluto.Installer(app)
            .addPlugin(PlutoNetworkPlugin(PLUTO_NETWORK_PLUGIN))
            .install()

    }
}