Ejemplos varios sobre uso eficiente de CPU y Batería
====================================================

[AndroidDeepSleepAsyncTask](AndroidDeepSleepAsyncTask)
-----------
Ejemplo que demuestra cómo el dispositivo se "duerme" luego de que se apaga la pantalla. Es un servicio que hace sonar una campana cada 15 segundos.

[AndroidDeepSleepTimer](AndroidDeepSleepTimer)
-----------
Igual al ejemplo anterior, pero utiliza un Timer en lugar de un AsyncTask. Tiene muchísima más precisión que el Thread.sleep(), pero no evita que se duerma el dispositivo.

[AndroidDeepSleepWakeLock](AndroidDeepSleepWakeLock)
-----------
Ejemplo que muestra el uso de CPU Locks para impedir que se duerma el dispositivo.  
Nota: esto hace que el dispositivo nunca se duerma mientras se ejecuta nuestro servicio, y no es recomendado en un servicio que dura mucho tiempo como el de este ejemplo.

[AndroidAlarmManager](AndroidAlarmManager)
-----------
Ejemplo que muestra el uso del Administrador de Alarmas.

[EjecutarEnBoot](EjecutarEnBoot)
-----------
Ejemplo que muestra como ejecutar un BroadcastReceiver cuando termina de iniciar el sistema.
