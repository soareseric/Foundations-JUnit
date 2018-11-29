package br.com.ericsoares.services;

import org.junit.Assert;
import org.junit.Test;

import br.com.ericsoares.entities.Usuario;

public class AssertTest {

	@Test
	public void test() {
		
		// COMANDOS : PARA EXECUTAR O TESTE UTILIZE " CTRL + F11 "
		// OBS : PODE SER COLOCADO UMA STRING NO COMEÇO DO PARAMETRO, PARA UTILIZAR COMO UMA MENSAGEM QUE SERÁ MOSTRADA NO INICIO DO TEST
		// OBS : SEMPRE SE ATENTAR A ORDEM DOS VALORES DO PARAMETRO QUE IRÁ UTILIZAR
		
		Assert.assertTrue(true);	// Assert.assertTrue : verifica se o parametro passado e verdadeiro
		Assert.assertFalse(false); // Assert.assertFalse : verifica se o parametro passado e falso
		
		Assert.assertEquals(1, 1); // Assert.assertEquals : compara os valores explicitados no parametro
		Assert.assertEquals(0.51, 0.51, 0.01); // Assert.assertEquals : com parametros double, é necessario colocar um delta para especificar a quantidade de casas que esta sendo verificada
		Assert.assertEquals(Math.PI, 3.14, 0.01);
		
		int i = 5; // VALOR PRIMITIVO
		Integer i2 = 5; // VALOR OBJETO ( wrappers )
		// Assert.assertEquals(i, i2);  ----- NÃO É POSSIVEL EXECUTAR O TEST COMPARANDO UM VALOR PRIMITIVO COM O VALOR OBJETO DESSA MANEIRA
		Assert.assertEquals(Integer.valueOf(i), i2); // MANEIRA CORRETA 1 : VOCE PASSAR O VALOR PRIMITIVO PARA OBJETO
		Assert.assertEquals(i, i2.intValue()); // MANEIRA CORRETA 2 : VOCE PASSAR O VALOR OBJETO PARA PRIMITIVO;
		
		Assert.assertEquals("bola", "bola"); // COMPARACAO NORMAL FEITO COM ASSERT EQUALS DE UMA STRING
		Assert.assertNotEquals("moto", "carro"); // ESSA SINTAXE, VERIFICA SE OS VALORES SÃO DE FATO DIFERENTES
		// Assert.assertEquals("bola", "Bola");  JA NESSE CASO O Assert.assertEquals IRÁ DIZER QUE ESTÁ ERRADO DEVIDO A LETRA MAISCULA
		Assert.assertTrue("bola".equalsIgnoreCase("Bola")); // PARA COMPRAR SOMENTE O VALOR DO TEXTO, DESCONSIDERANDO LETRAS MAISCULAS, UTILIZA-SE ESSA SINTAXE
		Assert.assertTrue("bola".startsWith("bo")); // JA PARAR COMPARAR SOMENTE O PREFIXO, A SINTAXE UTILIZADA É ESSA

		Usuario u1 = new Usuario("User 1");
		Usuario u2 = new Usuario("User 1");
		Usuario u3 = u2; // O USUARIO 3, APONTA PARA O MESMO PONTO DE REFERENCIA NA MEMORIA
		Usuario u4 = null;
		
		Assert.assertEquals(u1, u2); // ESSE METODO IRIA GERAR UM ERRO, CASO NÃO HAJA O MÉTODO EQUALS NA CLASSE QUE ESTA SENDO INSTANCIADA
		// ISSO OCORRE PORQUE O VALOR A SER COMPARADO ENTRE DOIS OBJETOS, QUANDO NÃO POSSUEM O EQUALS DECLARADO EM SUA CLASSE, É O VALOR DE SUA REFERENCIA QUE A MEMORIA ESTA APONTANDO
		// JA QUANDO POSSUI O EQUALS, ELE IRA COMPARAR O VALOR PROPRIAMENTE DECLARADO, NÃO SEU PONTO DE REFERENCIA QUE ESTA SENDO APONTADO NA MEMORIA
		
		Assert.assertSame(u3, u2); // O Assert.assertSome : FAZ JUSTAMENTE A COMPARAÇÃO DA REFERENCIA NA MEMORIA, OU SEJA, O MESMO PONTO DE INSTANCIAÇÃO
		Assert.assertNotSame(u4, u2); // VERIFICA SE DE FATO OS OBJETOS ESTÃO SENDO INSTANCIADOS EM LUGARES DIFERENTES
		
		Assert.assertTrue(u4 == null); // PARA COMPARAR SE OBJETO É NULL, PODE-SE UTILIZAR ESSA SINTAXE
		Assert.assertNull(u4); // OU ESSA
		
		Assert.assertNotNull(u1); // VERIFICA SE O OBJETO NÃO É NULL
		
		
	}
}
