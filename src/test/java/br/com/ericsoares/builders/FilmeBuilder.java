package br.com.ericsoares.builders;

import br.com.ericsoares.entities.Filme;

public class FilmeBuilder {

	private Filme filme;
	
	private FilmeBuilder() {
		
	}

	public static FilmeBuilder umFilme() {
		FilmeBuilder builder = new FilmeBuilder();
		builder.filme = new Filme();
		builder.filme.setEstoque(2);
		builder.filme.setNome("Filme 1");
		builder.filme.setPrecoLocacao(4.0);
		return builder;
	}
	
	public static FilmeBuilder umFilmeSemEstoque() { 			// PODEMOS TAMBEM, CASO SEJA NECESSARIO, CRIAR MAIS DO QUE UM METODO PADRAO DE INSTANCIAMENTO PARA QUE ASSIM POSSAMOS UTILIZAR EM SITUAÇOES QUE DEMANDAM CERTA ESPECIFICIDADE NOS VALORES DOS ATRIBUT
		FilmeBuilder builder = new FilmeBuilder();				// DISPOSTOS POR ESSE OBJETO
		builder.filme = new Filme();
		builder.filme.setEstoque(0);
		builder.filme.setNome("Filme 1");
		builder.filme.setPrecoLocacao(4.0);
		return builder;
	}
	
	public FilmeBuilder semEstoque() {
		filme.setEstoque(0);
		return this;
	}
	
	public FilmeBuilder comValor(Double valor) {
		filme.setPrecoLocacao(valor);
		return this;    //	RETORNAMOS O THIS, JUSTAMENTE PQ ESSE METODO ESTA PASSANDO UM VALOR QUE É A PROPRIA INSTANCIA DO OBJETO, E COMO PRECISAVAMOS RETORNAR ELE. BASTAMOS ADICIONAR A PALAVRA THIS, QUE IRA JA FAZER REFERENCIA A INSTANCIA.
	}
	
	public Filme agora() {
		return filme;
	}
}

