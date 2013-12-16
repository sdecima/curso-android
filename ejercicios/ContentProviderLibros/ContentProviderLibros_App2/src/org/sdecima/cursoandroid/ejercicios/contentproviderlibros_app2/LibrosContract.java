package org.sdecima.cursoandroid.ejercicios.contentproviderlibros_app2;

import android.net.Uri;

public class LibrosContract {
	public static final String LIBROS = "libros";
	
	public static final String ID = "_id";
	public static final String ISBN = "isbn";
	public static final String TITULO = "titulo";
	public static final String DESCRIPCION = "descripcion";
	
	public static final String AUTHORITY = "org.sdecima.cursoandroid.ejercicios.libros.provider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + LIBROS);
}
