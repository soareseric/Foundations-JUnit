package br.com.ericsoares.services;

import br.com.ericsoares.entities.Usuario;

public interface EmailService {

	public void notificarAtraso(Usuario usuario);
}
