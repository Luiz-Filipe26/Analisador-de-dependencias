package analisadorDependencias.util;

public class Objeto {
	
	public String tipo;
	public String nome;
	
	public Objeto() {
		
	}

	public Objeto(String tipo, String nome) {
		this.tipo = tipo;
		this.nome = nome;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Objeto object = (Objeto) obj;

		if (tipo != null ? !tipo.equals(object.tipo) : object.tipo != null) {
			return false;
		}

		if (nome != null ? !nome.equals(object.nome) : object.nome != null) {
			return false;
		}

		return true;
	}

    @Override
    public int hashCode() {
        int resultado = tipo != null ? tipo.hashCode() : 0;
        resultado = 31 * resultado + (nome != null ? nome.hashCode() : 0);
        return resultado;
    }
	
	@Override
    public String toString() {
        return 
			"Objeto{" +
			"tipo='" + tipo + '\'' +
			", nome='" + nome + '\'' +
			'}';
    }
}
