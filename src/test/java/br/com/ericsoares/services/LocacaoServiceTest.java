package br.com.ericsoares.services;

import static br.com.ericsoares.builders.FilmeBuilder.umFilme;
import static br.com.ericsoares.builders.FilmeBuilder.umFilmeSemEstoque;
import static br.com.ericsoares.builders.LocacaoBuilder.umLocacao;
import static br.com.ericsoares.builders.UsuarioBuilder.umUsuario;
import static br.com.ericsoares.matchers.MatchersProprios.caiNumaSegunda;
import static br.com.ericsoares.matchers.MatchersProprios.ehHoje;
import static br.com.ericsoares.matchers.MatchersProprios.ehHojeComDiferencaDias;
import static br.com.ericsoares.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.ericsoares.builders.LocacaoBuilder;
import br.com.ericsoares.dao.LocacaoDAO;
import br.com.ericsoares.entities.Filme;
import br.com.ericsoares.entities.Locacao;
import br.com.ericsoares.entities.Usuario;
import br.com.ericsoares.exceptions.FilmeSemEstoqueException;
import br.com.ericsoares.exceptions.LocadoraException;
import br.com.ericsoares.utils.DataUtils;


public class LocacaoServiceTest {

	// OBS : UTILIZANDO-SE DO IMPORT STATIC SEU CODIGO GANHA MELHOR LEGIBIDADE
	// ------ ATALHO CTRL + SHIFT + M ( AP�S A VIRGULA )
	// OBS : AL�M DO M�TODO UTILIZADO, NO CASO USANDO UMA RULE, PODEMOS SEPARAR
	// NOSSO �NICO, EM TESTES MENORES, PARA TESTAR CADA FUNCIONALIDADE DA CLASSE QUE
	// ESTAREMOS TESTANDO

	private LocacaoService service; // Instanciando de forma global o LocacaoService

	private SPCService spc;
	private LocacaoDAO dao;
	private EmailService email;
	
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
	 dao = Mockito.mock(LocacaoDAO.class);	 
	 service.setLocacaoDAO(dao);
	 spc = Mockito.mock(SPCService.class);
	 service.setSPCService(spc);
	 email = Mockito.mock(EmailService.class);
	 service.setEmailService(email);
	}
	
	// O AFTER SEGUE A MESMA LOGICA DO BEFORE, POR�M PARA UMA PARTE DO CODIGO QUE VC DESEJA FAZER APARECER, HAVER, AP�S TODA SINTAXE DO TEST
	//@After
	

	@Test
	public void deveAlugarFilme() throws Exception {
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY)); // JA NESSE ASSUME IRA FAZER COM QUE SEJA FALSO QUANDO FOR SABADO, E VERDADEIRO NOS DEMAIS DIAS

		// ETAPA 1 : CENARIO
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().comValor(5.0).agora());

		// ETAPA 2 : A��O
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// ETAPA 3 : VERIFICA��O
		err.checkThat(locacao.getValor(), is(equalTo(5.0))); // O Assert.assertThat � UM ASSERT GENERICO, QUE IR� RECEBER PRIMEIRO � O VALOR ATUAL E O SEGUNDO � O VALOR ESPERADO
		
		//err.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		err.checkThat(locacao.getDataLocacao(), ehHoje()); // COM MATCHERS PERSONALIZADOS PODEMOS SIMPLIFICAR, DEIXANDO MAIS LEGIVEL NOSSO CODIGO
		//err.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
		err.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1)); // COM MATCHERS PERSONALIZADOS PODEMOS SIMPLIFICAR, DEIXANDO MAIS LEGIVEL NOSSO CODIGO

	}

	// FORMAS DE TRATAR EXCE��ES, 3 MANEIRAS :

	// PRIMEIRA : DE FORMA OBJETIVA, E LIMPA. IR� APENAS VERIFICAR EXCE��O COMO
	// ESPERADA, CASO ELA N�O SEJA EXECUTADA IR� MOSTRAR UMA FALHA
	// VALE RESSALTAR QUE O INTUITO DESSES TESTES � VERIFICAR SE A EXCE��O PASSADA
	// NA CLASS, EST� DE FATO SENDO UGTILIZADA E FUNCIONANDO CORRETAMENTE

	@Test(expected = FilmeSemEstoqueException.class)
	public void deveAlugarFilmeComEstoque() throws Exception {

		// ETAPA 1 : CENARIO
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilmeSemEstoque().agora());

		// ETAPA 2 : A��O
		service.alugarFilme(usuario, filmes);
	}

	@Test
	public void deveHaverUsuarioParaAlugarFilme() throws FilmeSemEstoqueException {
		// ETAPA 1 : CENARIO
		List<Filme> filmes = Arrays.asList(umFilme().agora());

		// ETAPA 2 : A��O
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usu�rio vazio"));
		}
	}

	@Test
	public void deveAlugarFilmeParaAlugarNaoPodeEstarVazio() throws LocadoraException, FilmeSemEstoqueException {
		// ETAPA 1 : CENARIO
		Usuario usuario = umUsuario().agora();

		expection.expect(LocadoraException.class);
		expection.expectMessage("Filme vazio");
		
		// ETAPA 2 : A��O
		service.alugarFilme(usuario, null);		
	}
	
	@Test
	// @Ignore // UTILIZANDO-SE DESSA ANNOTATION FAREMOS COM QUE ESSE TEST SEJA IGNORADO, ASSIM PODEREMOS MANTER NOSSA BARRA VERDE DE SUCESSO DO TEST
	public void deveDevolverFilmeNaSegundaCasoAlugueNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY)); // Assume, � UM OTIMO METODO PARA SE USAR QUANDO UM DETERMINADO TEST NECESSITA DE UMA
		// CONDI��O ESPECIFICA PARA PODER FUNCIONAR NESSE CASO, PODEMOS UTILIZA-LO PARA FAZER COM QUE ELE CONFIGURE NOSSO TEST PARA FUNCIONAR NA DETERMINADA CONDI��O SOLICITADA
		// NESSE CASO, ELE EST� INDICANDO PARA ESSE M�TODO SE TORNAR VERDADEIRO SOMENTE QUANDO FOR SABADO
		
		// ETAPA 1 : CENARIO
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		
		// ETAPA 2 : A��O
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		// ETAPA 3 : VERIFICA��O
		//assertThat(retorno.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());
	}
		
	
	@Test
	public void naoDeveAlugarFilmeParaNegativoSPC() throws FilmeSemEstoqueException{
		//CENARIO
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		when(spc.passuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);
		
		//ACAO
		try {
			service.alugarFilme(usuario, filmes);
			//VERIFICACAO
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario negativado!"));
		}
		
		
		verify(spc).passuiNegativacao(usuario);
	}
	
	@Test
	public void deveEnviarEmailParaLocacoesAtrasadas() {
		//CENARIO
			Usuario usuario = umUsuario().agora();
			Usuario usuario2 = umUsuario().comNome("Usuario em dia").agora();
			Usuario usuario3 = umUsuario().comNome("Outro usuario").agora();
			List<Locacao> locacoes = Arrays.asList(
					umLocacao().atrasado().comUsuario(usuario).agora(),
					umLocacao().comUsuario(usuario2).agora(),
					umLocacao().atrasado().comUsuario(usuario3).agora(),
					umLocacao().atrasado().comUsuario(usuario3).agora());
			when(dao.obterLocacoesPendentes()).thenReturn(locacoes);
		//ACAO
			service.notificarAtraso();
		//VERIFICAR
			verify(email, times(3)).notificarAtraso(Mockito.any(Usuario.class));
			verify(email).notificarAtraso(usuario);
			verify(email, Mockito.atLeastOnce()).notificarAtraso(usuario3);
			verify(email, never()).notificarAtraso(usuario2);
			verifyNoMoreInteractions(email);
			
	}
	
}
