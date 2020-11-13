package modelo;

public class Tiempo {
	private int id;
	private Corredor c;
	private double tiempo;
	private int anio;

	public Tiempo(int id, Corredor c, double tiempo, int anio) {
		this.id = id;
		this.c = c;
		this.tiempo = tiempo;
		this.anio = anio;
	}

	public Tiempo(Corredor c, double tiempo, int anio) {
		this.id = -1;
		this.c = c;
		this.tiempo = tiempo;
		this.anio = anio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Corredor getC() {
		return c;
	}

	public void setC(Corredor c) {
		this.c = c;
	}

	public double getTiempo() {
		return tiempo;
	}

	public void setTiempo(double tiempo) {
		this.tiempo = tiempo;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	@Override
	public String toString() {
		return "Tiempo{" + "c=" + c + ", tiempo=" + tiempo + ", anio=" + anio + '}';
	}
}
