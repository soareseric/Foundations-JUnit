package br.com.ericsoares.services;

import static br.com.ericsoares.utils.DataUtils.isMesmaData;
import static br.com.ericsoares.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.com.ericsoares.entities.Filme;
import br.com.ericsoares.entities.Locacao;
import br.com.ericsoares.entities.Usuario;

public class LocacaoServiceTest {

	// OBS : UTILIZANDO-SE DO IMPORT STATIC SEU CODIGO GANHA MELHOR LEGIBIDADE  ------ ATALHO CTRL + SHIFT + M ( AP�S A VIRGULA )
	// OBS : AL�M DO M�TODO UTILIZADO, NO CASO USANDO UMA RULE, PODEMOS SEPARAR NOSSO �NICO, EM TESTES MENORES, PARA TESTAR CADA FUNCIONALIDADE DA CLASSE QUE ESTAREMOS TESTANDO

	@Rule // A annotation @Rule IR� DIZER QUE UMA REGRA SER� ESPECIFICADA. REGRAS AS QUAIS S�O DE UMA API DO JUNIT PARA LIDAR COM ALGUMAS PECUALIDADES ( PODEMOS CRIAR NOSSAS PROPRIAS TB)
	public ErrorCollector err = new ErrorCollector(); // UTILIZANDO ESSA REGRA, IREMOS FAZER COM QUE, MESMO N�O DIVINDO NOSSO TESTE, EM MAIS DE UM PARA TESTAR SEPARADAMENTE CADA M�TODO, AINDA SIM IREMOS CONSEGUIR VISUALIZAR CADA ERRO QUE NOSSO �NICO TESTE POSSUI
	
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
	
	// PRIMEIRA : DE FORMA OBJETIVA, E LIMPA. IR� APENAS VERIFICAR EXCE��O COMO ESPERADA, CASO ELA N�O SEJA EXECUTADA IR� MOSTRAR UMA FALHA
	// VALE RESSALTAR QUE O INTUITO DESSES TESTES � VERIFICAR SE A EXCE��O PASSADA NA CLASS, EST� DE FATO SENDO UGTILIZADA E FUNCIONANDO CORRETAMENTE
	
	@Test(expected=Exception.class)
	public void testLocacao_filmeSemEstoque() throws Exception {

		// ETAPA 1 : CENARIO
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// ETAPA 2 : A��O
		service.alugarFilme(usuario, filme);
	}
	
	// SEGUNDA : N�O UTILIZANDO O ' THROWS ' MAIS. IREMOS UTILIZAR UM ' TRY CATCH ' PARA REALIZARMOS A ANALISE DO FUNCIOMENTO DESSA EXCE��O.
	// VALE RESSALTAR QUE NESSA FORMA PODEMOS AT� MESMO VERIFICAR SE A MENSAGEM LAN�ADA PELA EXCE��O EST� DE ACORDO COM A INFORMADA
	// OBS : PARA N�O GERARMOS UM FALSO POSITIVO, UTILIZAMOS DO Assert.fail ( * ), PARA QUE CASO A EXCE��O N�O SEJA LAN�ADA, ELE AINDA SIM MOSTRAR UM ERRO
	// ISSO NECESSITA SER FEITO, POIS, JUNIT CONSIDERA QUE CASO SEU TESTE CHEGUE AT� O FIM, ELE ESTAR� CORRETO. OU SEJA, NESSA MANEIRA QUE ESTAMOS UTILIZANDO, CASO O VALOR SEJA DIFERENTE DE 0, FAZENDO COM QUE N�O CAIA NA EXCE��O, ELE AINDA SIM FALAR�
	// QUE EST� CORRETO, O QUE NA VERDADE N�O EST� ( ISSO SERIA O FALSO POSITIVO )
	
	@Test
	public void testLocacao_filmeSemEstoque2() {

		// ETAPA 1 : CENARIO
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// ETAPA 2 : A��O
		try {
			service.alugarFilme(usuario, filme);
			fail("Deveria ocorrer uma exce��o"); // ( * )
		} catch (Exception e) {
			assertThat(e.getMessage(), is("O filme n�o possui estoque")); // VERIFICANDO SE MENSAGEM LAN�ADA � A ESPERADA
			e.printStackTrace();
		}
	}
	
	// TERCEIRA : NESSA FORMA UTILIZAREMOS MAIS UMA RULE, PARA VERIFICAMOS SE A EXCE��O QUE LAN�AMOS ESTA FUNCIONANDO CORRETAMENTE
	
	@Test
	public void testLocacao_filmeSemEstoque3() throws Exception {

		// ETAPA 1 : CENARIO
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);
		
		// OBS : � NECESSARIO QUE SEJA COLOCADO ESSA PARTE ANTES DA ' A��O '
		expection.expect(Exception.class); // VERIFICANDO EXCEPTION ESPERADA
		expection.expectMessage("O filme n�o possui estoque"); // VERIFICANDO MENSAGEM DA EXCEPTION
		
		// ETAPA 2 : A��O
		service.alugarFilme(usuario, filme);
	
		
	}

}
