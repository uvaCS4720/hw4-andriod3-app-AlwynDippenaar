package edu.nd.pmcburne.hello

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.*
import com.google.android.gms.maps.model.*

import edu.nd.pmcburne.hello.data.local.LocationEntity

@Composable
fun MapView(locations: List<LocationEntity>) {

    val defaultLocation = LatLng(38.03474, -78.50820)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 15f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        locations.forEach { loc ->
            Marker(
                state = MarkerState(
                    position = LatLng(loc.latitude, loc.longitude)
                ),
                title = loc.name,
                snippet = loc.description
            )
        }
    }
}