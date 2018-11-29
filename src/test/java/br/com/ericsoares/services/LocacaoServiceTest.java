package br.com.ericsoares.services;

import static br.com.ericsoares.utils.DataUtils.isMesmaData;
import static br.com.ericsoares.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;

import br.com.ericsoares.entities.Filme;
import br.com.ericsoares.entities.Locacao;
import br.com.ericsoares.entities.Usuario;

public class LocacaoServiceTest {

	@Test
	public void test() {
		
		// OBS : UTILIZANDO-SE DO IMPORT STATIC SEU CODIGO GANHA MELHOR LEGIBIDADE
				// ------ ATALHO CTRL + SHIFT + M ( APÓS A VIRGULA )
		
		
		// ETAPA 1 : CENARIO
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// ETAPA 2 : AÇÃO
		Locacao locacao = service.alugarFilme(usuario, filme);

		// ETAPA 3 : VERIFICAÇÃO
		assertThat(locacao.getValor(), is(equalTo(5.0))); // O Assert.assertThat É UM ASSERT GENERICO, QUE IRÁ RECEBER
															// PRIMEIRO É O VALOR ATUAL E O SEGUNDO É O VALOR ESPERADO
		assertThat(locacao.getValor(), is(not(7.0)));
		assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}

}
