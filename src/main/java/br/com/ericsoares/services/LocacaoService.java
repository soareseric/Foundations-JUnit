package br.com.ericsoares.services;

import static br.com.ericsoares.utils.DataUtils.adicionarDias;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import br.com.ericsoares.entities.Filme;
import br.com.ericsoares.entities.Locacao;
import br.com.ericsoares.entities.Usuario;
import br.com.ericsoares.utils.DataUtils;

public class LocacaoService {

	public Locacao alugarFilme(Usuario usuario, Filme filme) throws Exception {
		if(filme.getEstoque() == 0) {
			throw new Exception("O filme n„o possui estoque");
		}

		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);

		// Salvando a locacao...
		// TODO adicionar m√©todo para salvar

		return locacao;
	}
}

	