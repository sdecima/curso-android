Ejemplos varios sobre uso eficiente de CPU y Batería
====================================================

[AndroidDeepSleepAsyncTask](AndroidDeepSleepAsyncTask)
-----------
Ejemplo que demuestra cómo el dispositivo se "duerme" luego de que se apaga la pantalla. Es un servicio que hace sonar una campana cada 15 segundos.

[AndroidDeepSleepTimer](AndroidDeepSleepTimer)
-----------
Igual al ejemplo anterior, pero utiliza un Timer en lugar de un AsyncTask. Tiene muchísima más precisión que el Thread.sleep(), pero no evita que se duerma el dispositivo.
