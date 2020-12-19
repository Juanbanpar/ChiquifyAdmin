package g16.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


	@Entity
	public class Mensaje implements Serializable {

		private static final long serialVersionUID = 1L;

		@Id   
	    private int id;
		
	    private String contenido;
	    
	    private String origen;
	    
	    private String destino;
		
		public Mensaje(int id, String contenido, String origen, String destino) {
			super();
			this.id = id;
			this.contenido = contenido;
			this.origen = origen;
			this.destino = destino;
		}
		
		public Mensaje(){
	    	super();
	    }

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getContenido() {
			return contenido;
		}

		public void setContenido(String contenido) {
			this.contenido = contenido;
		}

		public String getOrigen() {
			return origen;
		}

		public void setOrigen(String origen) {
			this.origen = origen;
		}

		public String getDestino() {
			return destino;
		}

		public void setDestino(String destino) {
			this.destino = destino;
		}

	
	}
