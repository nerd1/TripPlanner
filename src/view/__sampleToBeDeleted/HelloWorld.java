package view.__sampleToBeDeleted;

/**
 * Created by user on 21.12.2016.
 */

import com.teamdev.jxmaps.*;
import com.teamdev.jxmaps.swing.*;

import javax.swing.*;
import java.awt.*;

public class HelloWorld extends MapView {
    public HelloWorld(MapViewOptions options) {
        super(options);
        setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus status) {
                if (status == MapStatus.MAP_STATUS_OK) {
                    final Map map = getMap();
                    map.setZoom(8.0);
                    GeocoderRequest request = new GeocoderRequest(map);
                    request.setAddress("Gürtelstrasse 48, 7000 Chur, Schweiz");
//                  request.setLocation(new LatLng(15.000000, 18.222222));

                    getServices().getGeocoder().geocode(request, new GeocoderCallback(map) {
                        @Override
                        public void onComplete(GeocoderResult[] result, GeocoderStatus status) {
                            if (status == GeocoderStatus.OK) {
                                map.setCenter(result[0].getGeometry().getLocation());
                                Marker marker = new Marker(map);
                                marker.setPosition(result[0].getGeometry().getLocation());

                                final InfoWindow window = new InfoWindow(map);
                                window.setContent("Hoi Dieter");
                                window.open(map, marker);
                            }
                        }
                    });
                }
            }
        });
    }

    public static void main(String[] args) {

        MapViewOptions options = new MapViewOptions();
        options.importPlaces();
        final HelloWorld mapView = new HelloWorld(options);

        JFrame frame = new JFrame("JxMaps - Hello, World!");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(mapView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
