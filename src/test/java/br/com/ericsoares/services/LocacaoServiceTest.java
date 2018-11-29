package br.com.ericsoares.services;

import static br.com.ericsoares.utils.DataUtils.isMesmaData;
import static br.com.ericsoares.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.com.ericsoares.entities.Filme;
import br.com.ericsoares.entities.Locacao;
import br.com.ericsoares.entities.Usuario;

public class LocacaoServiceTest {

	// OBS : UTILIZANDO-SE DO IMPORT STATIC SEU CODIGO GANHA MELHOR LEGIBIDADE  ------ ATALHO CTRL + SHIFT + M ( AP�S A VIRGULA )
	// OBS : AL�M DO M�TODO UTILIZADO, NO CASO USANDO UMA RULE, PODEMOS SEPARAR NOSSO �NICO, EM TESTES MENORES, PARA TESTAR CADA FUNCIONALIDADE DA CLASSE QUE ESTAREMOS TESTANDO
	
	@Rule // A annotation @Rule IR� DIZER QUE UMA REGRA SER� ESPECIFICADA. REGRAS AS QUAIS S�O DE UMA API DO JUNIT PARA LIDAR COM ALGUMAS PECUALIDADES ( PODEMOS CRIAR NOSSAS PROPRIAS TB)
	public ErrorCollector err = new ErrorCollector(); // UTILIZANDO ESSA REGRA, IREMOS FAZER COM QUE, MESMO N�O DIVINDO NOSSO TESTE,
	// EM MAIS DE UM PARA TESTAR SEPARADAMENTE CADA M�TODO, AINDA SIM IREMOS CONSEGUIR VISUALIZAR CADA ERRO QUE NOSSO �NICO TESTE POSSUI

	@Test
	public void testLocacao() {
		
		// ETAPA 1 : CENARIO
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// ETAPA 2 : A��O
		Locacao locacao = service.alugarFilme(usuario, filme);

		// ETAPA 3 : VERIFICA��O
		err.checkThat(locacao.getValor(), is(equalTo(8.0))); // O Assert.assertThat � UM ASSERT GENERICO, QUE IR� RECEBER PRIMEIRO � O VALOR ATUAL E O SEGUNDO � O VALOR ESPERADO
		err.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		err.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(false));
	}

}
