package br.com.ericsoares.dao;

import java.util.List;

import br.com.ericsoares.entities.Locacao;

public interface LocacaoDAO {

	public void salvar(Locacao locacao);

	public List<Locacao> obterLocacoesPendentes();
}
