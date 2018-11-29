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
	// ------ ATALHO CTRL + SHIFT + M ( AP�S A VIRGULA )
	// OBS : AL�M DO M�TODO UTILIZADO, NO CASO USANDO UMA RULE, PODEMOS SEPARAR
	// NOSSO �NICO, EM TESTES MENORES, PARA TESTAR CADA FUNCIONALIDADE DA CLASSE QUE
	// ESTAREMOS TESTANDO

	@Rule // A annotation @Rule IR� DIZER QUE UMA REGRA SER� ESPECIFICADA. REGRAS AS QUAIS S�O DE UMA API DO JUNIT PARA LIDAR COM ALGUMAS PECUALIDADES ( PODEMOS CRIAR NOSSAS PROPRIAS TB)
	public ErrorCollector err = new ErrorCollector(); // UTILIZANDO ESSA REGRA, IREMOS FAZER COM QUE, MESMO N�O DIVINDO NOSSO TESTE, EM MAIS DE UM PARA TESTAR SEPARADAMENTE CADA  M�TODO, AINDA SIM IREMOS CONSEGUIR VISUALIZAR CADA ERRO QUE NOSSO �NICO TESTE POSSUI

	@Rule
	public ExpectedException expection = ExpectedException.none();

	@Test
	public void testLocacao() throws Exception {

		// ETAPA 1 : CENARIO
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// ETAPA 2 : A��O
		Locacao locacao = service.alugarFilme(usuario, filme);

		// ETAPA 3 : VERIFICA��O
		err.checkThat(locacao.getValor(), is(equalTo(5.0))); // O Assert.assertThat � UM ASSERT GENERICO, QUE IR� RECEBER PRIMEIRO � O VALOR ATUAL E O SEGUNDO � O VALOR ESPERADO
		err.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		err.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));

	}

	// FORMAS DE TRATAR EXCE��ES, 3 MANEIRAS :

	// PRIMEIRA : DE FORMA OBJETIVA, E LIMPA. IR� APENAS VERIFICAR EXCE��O COMO
	// ESPERADA, CASO ELA N�O SEJA EXECUTADA IR� MOSTRAR UMA FALHA
	// VALE RESSALTAR QUE O INTUITO DESSES TESTES � VERIFICAR SE A EXCE��O PASSADA
	// NA CLASS, EST� DE FATO SENDO UGTILIZADA E FUNCIONANDO CORRETAMENTE

	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception {

		// ETAPA 1 : CENARIO
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// ETAPA 2 : A��O
		service.alugarFilme(usuario, filme);
	}

	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		// ETAPA 1 : CENARIO
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 2", 1, 4.0);

		// ETAPA 2 : A��O
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usu�rio vazio"));
		}
	}

	@Test
	public void testLocacao_FilmeVazio() throws LocadoraException, FilmeSemEstoqueException {
		// ETAPA 1 : CENARIO
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");

		expection.expect(LocadoraException.class);
		expection.expectMessage("Filme vazio");
		
		// ETAPA 2 : A��O
		service.alugarFilme(usuario, null);
		
		

	}
}
