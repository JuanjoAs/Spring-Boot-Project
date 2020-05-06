package com.juanjo.proyecto.model;

public class Notificacion {
	private String Imagen;
	private String Mensaje;
	private String ruta;

	/**
	 * @return the imagen
	 */
	public String getImagen() {
		return Imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(String imagen) {
		Imagen = imagen;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return Mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		Mensaje = mensaje;
	}

	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
	}

	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public Notificacion(String imagen, String mensaje, String ruta) {
		super();
		Imagen = imagen;
		Mensaje = mensaje;
		this.ruta = ruta;
	}
}
