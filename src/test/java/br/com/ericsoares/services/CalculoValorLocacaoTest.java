package br.com.ericsoares.services;

import static br.com.ericsoares.builders.FilmeBuilder.umFilme;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.com.ericsoares.entities.Filme;
import br.com.ericsoares.entities.Locacao;
import br.com.ericsoares.entities.Usuario;
import br.com.ericsoares.exceptions.FilmeSemEstoqueException;
import br.com.ericsoares.exceptions.LocadoraException;

// ESSA ANNOTATION DEFINE O MÉTODO QUE O JUNIT IRÁ ANALIZAR SEUS TESTES
@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

	private LocacaoService service;

	@Parameter
	public List<Filme> filmes;

	@Parameter(value=1) // ESSA ANNOTATION DEFINI O VALOR DO PARAMETRO VALORLOCACAO
	public Double valorLocacao;
	
	@Parameter(value=2) // ESSA ANNOTATION DEFINI O VALOR DO PARAMETRO CENARIO
	public String cenario;

	@Before
	public void setup() {
		service = new LocacaoService();
	}
	
	private static Filme filme1 =  umFilme().agora(); // INSTANCIANDO OS FILMES EM VARIAVEIS
	private static Filme filme2 =  umFilme().agora();
	private static Filme filme3 =  umFilme().agora();
	private static Filme filme4 =  umFilme().agora();
	private static Filme filme5 =  umFilme().agora();
	private static Filme filme6 =  umFilme().agora();
	private static Filme filme7 =  umFilme().agora();
	
	@Parameters(name="{2}") // O PARAMETRO PASSADO NESSA ANNOTATION DEFINI O VALOR A SER UTILIZADO COMO DEFINIÇÃO DE CADA TESTE
	// NESSE CASO, SERÁ PASSADO O VALOR QUE A VARIAVEL CENARIO POSSUIR
	public static Collection<Object[]> getParametros(){ 
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme1, filme2), 8.0, "2 Filmes: Sem desconto"},
			{Arrays.asList(filme1, filme2, filme3), 11.0, "3 Filmes: 25%"},
			{Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 Filmes: 50%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 Filmes: 75%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 Filmes: 100%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.0, "7 Filmes: Sem desconto"},
		}); 
	}

	@Test
	public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
		// ETAPA 1 : CENARIO
		Usuario usuario = new Usuario("Usuario 1");

		// ETAPA 2 : AÇÃO
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// ETAPA 3 : VERIFICAÇÃO
		assertThat(resultado.getValor(), is(valorLocacao));
	}
}
