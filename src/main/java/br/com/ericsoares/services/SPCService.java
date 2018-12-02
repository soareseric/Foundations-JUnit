package br.com.ericsoares.services;

import br.com.ericsoares.entities.Usuario;

public interface SPCService {

	public boolean passuiNegativacao(Usuario usuario) throws Exception;
}
