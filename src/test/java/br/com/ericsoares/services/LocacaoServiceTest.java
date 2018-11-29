package br.com.ericsoares.services;

import static br.com.ericsoares.utils.DataUtils.isMesmaData;
import static br.com.ericsoares.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
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

	private LocacaoService service; // Instanciando de forma global o LocacaoService
	
	@Rule // A annotation @Rule IR� DIZER QUE UMA REGRA SER� ESPECIFICADA. REGRAS AS QUAIS S�O DE UMA API DO JUNIT PARA LIDAR COM ALGUMAS PECUALIDADES ( PODEMOS CRIAR NOSSAS PROPRIAS TB)
	public ErrorCollector err = new ErrorCollector(); // UTILIZANDO ESSA REGRA, IREMOS FAZER COM QUE, MESMO N�O DIVINDO NOSSO TESTE, EM MAIS DE UM PARA TESTAR SEPARADAMENTE CADA  M�TODO, AINDA SIM IREMOS CONSEGUIR VISUALIZAR CADA ERRO QUE NOSSO �NICO TESTE POSSUI

	@Rule
	public ExpectedException expection = ExpectedException.none();
	
	// OUTRA ANNOTATION MUITO UTILIZADA NO JUNIT, � O BEFORE E O AFTER. NESSA CASO, O BEFORE SERVE PARA SETAR ALGO QUE SE APRESENTA
	// EM TODOS OS TESTES, O QUE O FAZ SE TORNAR MUITO REPETITIVO, POR�M COM O BEFORE, PRECISAMOS APENAS SETAR UMA VEZ, AP�S ISSO, SER�
	// UTILIZADO PARA TODOS QUE PRECISAREM

	@Before 
	
	public void setup() {
	 service = new LocacaoService();
	}
	
	// O AFTER SEGUE A MESMA LOGICA DO BEFORE, POR�M PARA UMA PARTE DO CODIGO QUE VC DESEJA FAZER APARECER, HAVER, AP�S TODA SINTAXE DO TEST
	//@After
	

	@Test
	public void testLocacao() throws Exception {

		// ETAPA 1 : CENARIO
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

		// ETAPA 2 : A��O
		Locacao locacao = service.alugarFilme(usuario, filmes);

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
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		// ETAPA 2 : A��O
		service.alugarFilme(usuario, filmes);
	}

	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		// ETAPA 1 : CENARIO
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0));

		// ETAPA 2 : A��O
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usu�rio vazio"));
		}
	}

	@Test
	public void testLocacao_FilmeVazio() throws LocadoraException, FilmeSemEstoqueException {
		// ETAPA 1 : CENARIO
		Usuario usuario = new Usuario("Usuario 1");

		expection.expect(LocadoraException.class);
		expection.expectMessage("Filme vazio");
		
		// ETAPA 2 : A��O
		service.alugarFilme(usuario, null);
		
		

	}
}
