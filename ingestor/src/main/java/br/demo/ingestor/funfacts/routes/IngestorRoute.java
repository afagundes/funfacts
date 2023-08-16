package br.demo.ingestor.funfacts.routes;

import br.demo.ingestor.funfacts.model.FunFact;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Define uma rota que consulta a api "https://uselessfacts.jsph.pl/api/v2/facts/random", extrai a mensagem da resposta
 * e persiste no banco de dados na tabela facts.<p>
 *
 * Para detalhes sobre cada componente de uma rota consulte a classe {@link HelloWorldRoute}
 */
@Component
public class IngestorRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // A cada 1 minuto inicia a rota
        from("timer:ingestor?period=1m&fixedRate=true")
                // Chama a api
                .to("https://uselessfacts.jsph.pl/api/v2/facts/random")
                .log("Resposta da API: ${body}")
                // Essa api retorna algo como: {"id":"fe11607fefa640b5835451440c7b0131","text":"The only real person to ever to appear on a pez dispenser was Betsy Ross.","source":"djtech.net","source_url":"http://www.djtech.net/humor/useless_facts.htm","language":"en","permalink":"https://uselessfacts.jsph.pl/api/v2/facts/fe11607fefa640b5835451440c7b0131"}
                // Precisamos extrair o campo "text"
                // A primeira coisa é converter esse json para um objeto java
                // Essa conversão é feito usando a biblioteca Jackson (consulte o pom.xml)
                .unmarshal().json()
                // Exchange é o objeto que mantém todas as informações sobre a rota no momento
                // Aqui vamos pegar o conteúdo do body da rota já convertido para um mapa pelo unmarshal e extrair o campo text
                .process(exchange -> {
                    String text = exchange.getIn().getBody(Map.class).get("text").toString();
                    exchange.getIn().setBody(text);
                })
                .log("Texto extraído da API: ${body}")
                // Agora só precisamos inserir esse texto no banco de dados
                // O process abaixo poderia estar incluído no process de cima, mas deixei aqui pra ficar mais didático
                .process(exchange -> {
                    FunFact funFact = new FunFact();
                    funFact.setFact(exchange.getIn().getBody(String.class));

                    exchange.getIn().setBody(funFact);
                })
                .to("jpa:br.demo.ingestor.funfacts.model.FunFact")
                .log("Ingestor finalizado com sucesso!");
    }

}
