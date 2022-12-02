package ktepin.penzasoft.dairy

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ktepin.penzasoft.dairy.databinding.ActivityMainBinding
import ktepin.penzasoft.dairy.util.PermissionManager
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


//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
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val permissionManager : PermissionManager by inject { parametersOf(this@MainActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(!permissionManager.arePermissionsGranted())
            permissionManager.requestPermissions()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        // user declined permissions
        if(!permissionManager.arePermissionsGranted())
            Toast.makeText(baseContext, "Please enable all permissions in the app settings...", Toast.LENGTH_LONG).show()
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //fix jetpack navigation's back button
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}