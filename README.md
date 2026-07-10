# Proyecto Firebase - Android (UTEQ)

Este proyecto contiene **dos tareas** desarrolladas sobre la misma app Android, ambas conectadas a **Firebase Realtime Database**.

---

## 📌 Tarea 1: Monitoreo de Sensores (`MainActivity`)

Pantalla que muestra y actualiza en tiempo real los valores de 4 sensores simulados, además de permitir enviar valores manuales a la base de datos.

### Funcionalidad
- Lee en tiempo real (`ValueEventListener`) los siguientes nodos de Firebase:
  - `sensores/temperatura` → mostrado en °C
  - `sensores/humedad` → mostrado en %
  - `sensores/presion` → mostrado en hPa
  - `sensores/velocidad` → mostrado en Km/h
- Cada valor se muestra en un `TextView` junto a su unidad de medida.
- Permite **escribir** nuevos valores a cada nodo desde un `EditText` + botón:
  - `clickBotonTemp` → actualiza `sensores/temperatura`
  - `clickBotonHumedad` → actualiza `sensores/humedad`
  - `clickBotonPresion` → actualiza `sensores/presion`
  - `clickBotonVelocidad` → actualiza `sensores/velocidad`

### Estructura en Firebase
```
sensores/
├── temperatura: <float>
├── humedad: <float>
├── presion: <float>
└── velocidad: <float>
```

### Archivo principal
`app/src/main/java/com/example/firebase/MainActivity.java`

---

## 📌 Tarea 2: Monitoreo GPS en Tiempo Real (`actividadMapa`)

Pantalla que muestra un mapa de Google Maps con la posición de un vehículo (GPR250), actualizada en tiempo real desde Firebase, simulando un recorrido GPS.

### Funcionalidad
- Conecta con Google Maps (`SupportMapFragment` + `OnMapReadyCallback`).
- Escucha en tiempo real el nodo `vehiculos/GPR250/ubicacion_actual` y mueve un marcador en el mapa según la latitud/longitud recibida.
- Centra la cámara automáticamente al abrir la app y sigue al marcador en cada actualización sin parpadeos.
- Obtiene también la ubicación GPS real del dispositivo (`FusedLocationProviderClient`) y la guarda en Firebase cada 5 segundos.
- Muestra la latitud/longitud actual en pantalla (`txtLatitud`, `txtLongitud`).

### Estructura en Firebase
```
vehiculos/
└── GPR250/
    └── ubicacion_actual/
        ├── latitud: <double>
        ├── longitud: <double>
        ├── timestamp: <long>
        ├── ultima_hora: <string>
        └── velocidad: <int>
```

### Simulación de ruta (Python)
El script `simulador_gps_gpr250.py` envía coordenadas de un recorrido predefinido a Firebase, una cada 5 segundos, simulando el desplazamiento del vehículo GPR250. Se conecta a la misma ruta (`vehiculos/GPR250/ubicacion_actual`) que consume la app Android.

**Ejecución:**
```bash
pip install requests
python simulador_gps_gpr250.py
```

### Archivos principales
- `app/src/main/java/com/example/firebase/actividadMapa.java`
- `app/src/main/res/layout/activity_actividad_mapa.xml`
- `simulador_gps_gpr250.py`

---

## ⚙️ Configuración necesaria para ejecutar el proyecto

1. **Firebase**: agregar el archivo `google-services.json` (descargado desde la consola de Firebase) en la carpeta `app/`.
2. **Dependencias** en `build.gradle (Module :app)`:
   ```gradle
   implementation 'com.google.android.gms:play-services-maps:19.0.0'
   implementation 'com.google.android.gms:play-services-location:21.3.0'
   implementation platform('com.google.firebase:firebase-bom:33.1.0')
   implementation 'com.google.firebase:firebase-database'
   ```
3. **Permisos** en `AndroidManifest.xml`: `INTERNET`, `ACCESS_NETWORK_STATE`, `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`.
4. **API Key de Google Maps** dentro de `<application>` en el Manifest:
   ```xml
   <meta-data
       android:name="com.google.android.geo.API_KEY"
       android:value="TU_API_KEY" />
   ```

---

## 👤 Autor
Erwin Daniel Bueno Troya — Ingeniería en Software, UTEQ
