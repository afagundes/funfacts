package br.demo.ingestor.funfacts.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * O Apache Camel trabalha com o conceito de rotas. Cada rota é uma thread separada que executa ações.
 * Pra criar uma rota basta extender a classe RouteBuilder e anotar a classe com @Component.<p>
 *
 * Sobre a anotação component: <a href="https://www.baeldung.com/spring-component-annotation">@Component</a>
 */
@Component
public class HelloWorldRoute extends RouteBuilder {

    @Override
    public void configure() {
        // Toda rota tem:
        //
        // Uma entrada "from" (pode ser qualquer coisa, um arquivo, uma query no banco, um kafka, etc)...
        // Aqui a entrada é um timer que executa a cada 10 segundos...
        from("timer:hello-world?period=10s")
                // Um processamento (transformar a entrada que veio do from)
                // Aqui eu só fixei uma mensagem no body da rota
                .setBody(exchange -> "Apache Camel funcionando!")
                // E uma saída, mandar pro banco de dados, postar em um api externa
                // Aqui eu só jogo o body da rota no log
                .log("Rota Hello World: ${body}");
    }

}
