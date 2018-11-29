package br.com.ericsoares.services;

import static br.com.ericsoares.utils.DataUtils.isMesmaData;
import static br.com.ericsoares.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.com.ericsoares.entities.Filme;
import br.com.ericsoares.entities.Locacao;
import br.com.ericsoares.entities.Usuario;
import br.com.ericsoares.exceptions.FilmeSemEstoqueException;
import br.com.ericsoares.exceptions.LocadoraException;

public class LocacaoServiceTest {

	// OBS : UTILIZANDO-SE DO IMPORT STATIC SEU CODIGO GANHA MELHOR LEGIBIDADE
	// ------ ATALHO CTRL + SHIFT + M ( APÓS A VIRGULA )
	// OBS : ALÉM DO MÉTODO UTILIZADO, NO CASO USANDO UMA RULE, PODEMOS SEPARAR
	// NOSSO ÚNICO, EM TESTES MENORES, PARA TESTAR CADA FUNCIONALIDADE DA CLASSE QUE
	// ESTAREMOS TESTANDO

	@Rule // A annotation @Rule IRÁ DIZER QUE UMA REGRA SERÁ ESPECIFICADA. REGRAS AS QUAIS SÃO DE UMA API DO JUNIT PARA LIDAR COM ALGUMAS PECUALIDADES ( PODEMOS CRIAR NOSSAS PROPRIAS TB)
	public ErrorCollector err = new ErrorCollector(); // UTILIZANDO ESSA REGRA, IREMOS FAZER COM QUE, MESMO NÃO DIVINDO NOSSO TESTE, EM MAIS DE UM PARA TESTAR SEPARADAMENTE CADA  MÉTODO, AINDA SIM IREMOS CONSEGUIR VISUALIZAR CADA ERRO QUE NOSSO ÚNICO TESTE POSSUI

	@Rule
	public ExpectedException expection = ExpectedException.none();

	@Test
	public void testLocacao() throws Exception {

		// ETAPA 1 : CENARIO
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// ETAPA 2 : AÇÃO
		Locacao locacao = service.alugarFilme(usuario, filme);

		// ETAPA 3 : VERIFICAÇÃO
		err.checkThat(locacao.getValor(), is(equalTo(5.0))); // O Assert.assertThat É UM ASSERT GENERICO, QUE IRÁ RECEBER PRIMEIRO É O VALOR ATUAL E O SEGUNDO É O VALOR ESPERADO
		err.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		err.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));

	}

	// FORMAS DE TRATAR EXCEÇÕES, 3 MANEIRAS :

	// PRIMEIRA : DE FORMA OBJETIVA, E LIMPA. IRÁ APENAS VERIFICAR EXCEÇÃO COMO
	// ESPERADA, CASO ELA NÃO SEJA EXECUTADA IRÁ MOSTRAR UMA FALHA
	// VALE RESSALTAR QUE O INTUITO DESSES TESTES É VERIFICAR SE A EXCEÇÃO PASSADA
	// NA CLASS, ESTÁ DE FATO SENDO UGTILIZADA E FUNCIONANDO CORRETAMENTE

	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception {

		// ETAPA 1 : CENARIO
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// ETAPA 2 : AÇÃO
		service.alugarFilme(usuario, filme);
	}

	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		// ETAPA 1 : CENARIO
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 2", 1, 4.0);

		// ETAPA 2 : AÇÃO
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuário vazio"));
		}
	}

	@Test
	public void testLocacao_FilmeVazio() throws LocadoraException, FilmeSemEstoqueException {
		// ETAPA 1 : CENARIO
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");

		expection.expect(LocadoraException.class);
		expection.expectMessage("Filme vazio");
		
		// ETAPA 2 : AÇÃO
		service.alugarFilme(usuario, null);
		
		

	}
}
