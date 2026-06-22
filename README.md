# Hamburgueria - 23 Padroes de Projeto em Java

Projeto academico desenvolvido em Java que implementa os 23 padroes de projeto GoF aplicados ao dominio de uma hamburgueria. Cada pacote do projeto representa um conceito do negocio e contem a implementacao de um padrao especifico, sem expor o nome do padrao nas classes.

O projeto cobre os tres grupos de padroes: criacionais, estruturais e comportamentais. Alguns padroes foram integrados entre si, como a Facade que coordena Command, Strategy e Singleton ao mesmo tempo, ou o Observer que avanca o State do pedido e notifica os clientes automaticamente.

Os testes foram escritos com JUnit 5 e estao separados em arquivos por contexto de negocio, com 184 metodos de teste no total cobrindo todos os 23 padroes.

## Estrutura de Pacotes

```
src/main/java/com/hamburgueria/
    ciclo_pedido/           State
    notificacoes/           Observer
    configuracoes/          Singleton
    servicos/               Factory Method
    fabricacao_combos/      Abstract Factory
    relatorios/             Bridge
    calculo_preco/          Strategy
    montagem_lanche/        Builder
    aprovacao_desconto/     Chain of Responsibility
    preparo_cozinha/        Template Method
    comunicacao_interna/    Mediator
    operacoes_balcao/       Facade
    cardapio/               Composite
    carrinho_compras/       Memento
    auditoria_cardapio/     Visitor
    personalizacao_lanche/  Decorator
    navegacao_cardapio/     Iterator
    templates_pedido/       Prototype
    ingredientes/           Flyweight
    fila_cozinha/           Command
    pagamento_entrega/      Adapter
    acesso_cardapio/        Proxy
    calculadora_precos/     Interpreter
```

## Integracoes entre Padroes

| De | Para | Como |
|---|---|---|
| Observer | State | Mesa avanca o estado do pedido e notifica observers automaticamente |
| Chain | Singleton | Aprovadores leem limites de desconto de Configuracoes.getInstance() |
| Facade | Command | PedidoFacade encadeia operacoes via FilaCozinha |
| Facade | Strategy | PedidoFacade calcula totais com Calculadora e Operacao |
| Facade | Singleton | PedidoFacade le taxa de entrega e tempo de preparo do Singleton |
| Facade | Adapter | PedidoFacade resolve forma de pagamento via iPagamento |
| Proxy | Singleton | CardapioProxy valida Configuracoes antes de permitir escrita |
| Iterator | Composite | CardapioHamburgueria.carregarDoComposite() percorre a arvore do Composite |
| Builder | Decorator | buildDecorado() retorna IngredienteDecorator encadeado com os extras escolhidos |
| Template Method | Factory Method | PreparoLanche usa ServicoFactory para obter o servico de entrega |
| Mediator | Observer | Central implementa Observable e notifica observers no broadcast |
| Bridge | Singleton | Funcionario le o nome do estabelecimento de Configuracoes |
| Visitor | Iterator | VisitanteRelatorio usa CardapioHamburgueria para iterar ao auditar |
| Memento | Command | HistoricoCarrinho registra e desfaz Comandos coordenadamente |
| Interpreter | Strategy | CalculadoraCardapio usa Calculadora para aplicar desconto e taxa |
| Abstract Factory | Prototype | Fabricas expõem criarTemplateCombo() retornando PedidoTemplate clonavel |
| Flyweight | Composite | montarCardapioComposite() constroi arvore Combo com ingredientes do cache |

## Testes

| Arquivo | Testes |
|---|---|
| CicloDeVidaDoPedidoTest | 9 |
| NotificacoesDeMesaTest | 7 |
| ConfiguracoesDaLanchoneteTest | 7 |
| CriacaoDeServicosTest | 7 |
| FabricacaoDeCombosTest | 8 |
| EmissaoDeRelatoriosTest | 8 |
| CalculoDePrecosTest | 8 |
| MontagemDeLancheTest | 8 |
| AprovacaoDeDescontosTest | 9 |
| PreparoComunicacaoOperacoesCardapioTest | 20 |
| HistoricoAuditoriaPersonalizacaoTest | 23 |
| NavegacaoClonageIngredientesTest | 20 |
| FilaPagamentoAcessoCalculadoraTest | 48 |
