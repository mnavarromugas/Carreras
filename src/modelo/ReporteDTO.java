package modelo;

public class ReporteDTO {
	public String nombre;
	public double promedio;

	ReporteDTO(String nombre, Double promedio) {
		this.nombre = nombre;
		this.promedio = promedio;
	}

	@Override
	public String toString() {
		return "ReporteDTO{" + "nombre=" + nombre + ", promedio=" + promedio + '}';
	}
}
