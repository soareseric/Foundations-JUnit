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

	// OBS : UTILIZANDO-SE DO IMPORT STATIC SEU CODIGO GANHA MELHOR LEGIBIDADE  ------ ATALHO CTRL + SHIFT + M ( APÓS A VIRGULA )
	// OBS : ALÉM DO MÉTODO UTILIZADO, NO CASO USANDO UMA RULE, PODEMOS SEPARAR NOSSO ÚNICO, EM TESTES MENORES, PARA TESTAR CADA FUNCIONALIDADE DA CLASSE QUE ESTAREMOS TESTANDO
	
	@Rule // A annotation @Rule IRÁ DIZER QUE UMA REGRA SERÁ ESPECIFICADA. REGRAS AS QUAIS SÃO DE UMA API DO JUNIT PARA LIDAR COM ALGUMAS PECUALIDADES ( PODEMOS CRIAR NOSSAS PROPRIAS TB)
	public ErrorCollector err = new ErrorCollector(); // UTILIZANDO ESSA REGRA, IREMOS FAZER COM QUE, MESMO NÃO DIVINDO NOSSO TESTE,
	// EM MAIS DE UM PARA TESTAR SEPARADAMENTE CADA MÉTODO, AINDA SIM IREMOS CONSEGUIR VISUALIZAR CADA ERRO QUE NOSSO ÚNICO TESTE POSSUI

	@Test
	public void testLocacao() {
		
		// ETAPA 1 : CENARIO
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// ETAPA 2 : AÇÃO
		Locacao locacao = service.alugarFilme(usuario, filme);

		// ETAPA 3 : VERIFICAÇÃO
		err.checkThat(locacao.getValor(), is(equalTo(8.0))); // O Assert.assertThat É UM ASSERT GENERICO, QUE IRÁ RECEBER PRIMEIRO É O VALOR ATUAL E O SEGUNDO É O VALOR ESPERADO
		err.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		err.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(false));
	}

}
