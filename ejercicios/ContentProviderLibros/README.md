ContentProviderLibros
=====================

1. Crear un ContentProvider para administrar Libros, utilizando una base de datos SQLite
2. Crear una (segunda) aplicación con las siguientes características:
   a) Crear una Activity que muestre una lista de los Libros, y con una opción de menú para crear Libros.  
   b) Crear una segunda Activity que permita ingresar el ISBN, título y descripción de un Libro, y que lo inserte en la base de datos.
   c) Agregar un menú de contexto a la Activity que lista los libros, y mostrar dos opciones cuando se mantiene presionado en un Libro de la lista: borrar y editar.
3. Hacer que la aplicación funcione con Intentos implícitos, y utilizando las URIs que define el ContentProvider; es decir que se puedan llamar sus Activities desde cualquier otra aplicación y pasando las URIs de los Libros.  
4. Crear una (tercera) aplicación que muestre un botón, que al apretarlo pregunte al ContentProvider (de la primer aplicación) por el primer Libro de la lista, y luego llame a una Activity que edite el mismo Libro.   

--------------------------------------------
Soluciones:
-----------
1. [ContentProviderLibros](ContentProviderLibros)
2. [ContentProviderLibros_App1](ContentProviderLibros_App1)
3. [ContentProviderLibros_App1b](ContentProviderLibros_App1b)
4. [ContentProviderLibros_App2](ContentProviderLibros_App2)
