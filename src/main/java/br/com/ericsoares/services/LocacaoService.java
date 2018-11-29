package br.com.ericsoares.services;

import static br.com.ericsoares.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import br.com.ericsoares.entities.Filme;
import br.com.ericsoares.entities.Locacao;
import br.com.ericsoares.entities.Usuario;
import br.com.ericsoares.exceptions.FilmeSemEstoqueException;
import br.com.ericsoares.exceptions.LocadoraException;

public class LocacaoService {

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException   {
		
		if(usuario == null) {
			throw new LocadoraException("Usu·rio vazio");
		}
		
		
		if(filmes == null || filmes.isEmpty()) {	
			throw new LocadoraException("Filme vazio");
		}
		
		for(Filme filme: filmes) {
		if(filme.getEstoque() == 0) {
			throw new FilmeSemEstoqueException();
		}
		}

		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Double valorTotal = 0d;
		for(Filme filme: filmes) {
			valorTotal += filme.getPrecoLocacao();
		}
		locacao.setValor(valorTotal);
		
		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);

		// Salvando a locacao...
		// TODO adicionar m√©todo para salvar

		return locacao;
	}
}

	