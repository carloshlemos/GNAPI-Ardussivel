package br.alfa.gnapi_ardussivel.domain;

import java.io.Serializable;

import com.orm.SugarRecord;

public class Comando extends SugarRecord<Comando> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ambiente;
	private String utensilio;
	private String acao;
	private String comando;
	private String url;

	public Comando() {

	}

	public Comando(String ambiente, String utensilio, String acao, String comando, String url) {
		super();
		this.ambiente = ambiente;
		this.utensilio = utensilio;
		this.acao = acao;
		this.comando = comando;
		this.url = url;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getUtensilio() {
		return utensilio;
	}

	public void setUtensilio(String utensilio) {
		this.utensilio = utensilio;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public String getComando() {
		return comando;
	}

	public void setComando(String comando) {
		this.comando = comando;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return acao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acao == null) ? 0 : acao.hashCode());
		result = prime * result + ((ambiente == null) ? 0 : ambiente.hashCode());
		result = prime * result + ((comando == null) ? 0 : comando.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((utensilio == null) ? 0 : utensilio.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comando other = (Comando) obj;
		if (acao == null) {
			if (other.acao != null)
				return false;
		} else if (!acao.equals(other.acao))
			return false;
		if (ambiente == null) {
			if (other.ambiente != null)
				return false;
		} else if (!ambiente.equals(other.ambiente))
			return false;
		if (comando == null) {
			if (other.comando != null)
				return false;
		} else if (!comando.equals(other.comando))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (utensilio == null) {
			if (other.utensilio != null)
				return false;
		} else if (!utensilio.equals(other.utensilio))
			return false;
		return true;
	}

}
