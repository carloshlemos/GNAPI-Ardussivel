package br.alfa.gnapi_ardussivel.utensilio;

public class Comando {

	private String id;
	private String ambiente;
	private String utensilio;
	private String acao;
	private String comando;
	private String url;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
