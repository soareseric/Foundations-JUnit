package br.com.ericsoares.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.ericsoares.services.CalculoValorLocacaoTest;
import br.com.ericsoares.services.LocacaoServiceTest;

// UMA SUITE DE TESTES, BASICAMENTE � UMA CLASSE QUE IRA COMPOR TODAS AS SUAS CLASSES DE TESTES E IRA EXECUTAR TUDO DE UMA VEZ
// OBS: PONTOS NEGATIVOS: 
// EM INTEGRA��O CONTINUA = N�O E MUITO VIAVEL, POIS, O PROGRAM QUE IRA SER UTILIZADO PARA EXECUTAR OS TESTES, IRA EXECUTAR TODAS SUAS CLASSES E O SUITE, OU SEJA, IRA DUPLICAR TODOS SEUS TESTES SEM NECESSIDADE.
// OUTRA MANEIRA = PODEMOS EXECUTAR TODAS CLASSES DE TESTES DE OUTRA FORMA, QUE SERIA STARTANDO O JUNIT PELO PROPRIO PACOTE DE TESTES, ASSIM IRA EXECUTAR TODOS. O PONTO FORTE DE FAZER ASSIM, E QUE TB NAO PRECISAREMOS FICAR LEMBRANDO DE COLOCAR TODAS NOSSAS CLASSES NO SUITE


//@RunWith(Suite.class) // ANNOTATION DA RUN, PARA DIZER DE QUAL FORMA O JUNIT DEVE SE COMPORTAR NESSA CLASSE DE TESTES
@SuiteClasses({ 						// J� ESSA ANNOTATION SER� ONDE VOCE IRA COLOCAR TODAS SUAS CLASSES DE TESTES PARA SEREM RODADAS
	CalculoValorLocacaoTest.class,
	LocacaoServiceTest.class
})

public class SuiteExecucao {
	// Remova se puder	
}
