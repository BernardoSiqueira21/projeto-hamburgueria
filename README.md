# Hamburgueria - 23 Padroes de Projeto em Java



## Diagrama de Classes

```mermaid
classDiagram
    direction TB

%% ════════════════════════════════════════════════════════════
%% 01 · STATE
%% ════════════════════════════════════════════════════════════
    namespace STATE {
        class PedidoContext {
            -PedidoEstado estado
            +confirmar()
            +preparar()
            +entregar()
            +cancelar()
            +devolver()
            +setEstado(PedidoEstado)
            +getEstado() PedidoEstado
        }
        class PedidoEstado {
            <<abstract>>
            +confirmar(PedidoContext)*
            +preparar(PedidoContext)*
            +entregar(PedidoContext)*
            +cancelar(PedidoContext)*
            +devolver(PedidoContext)*
            +getNome() String*
        }
        class PedidoEstadoPendente   { +getNome() String }
        class PedidoEstadoConfirmado { +getNome() String }
        class PedidoEstadoEmPreparo  { +getNome() String }
        class PedidoEstadoEntregue   { +getNome() String }
        class PedidoEstadoCancelado  { +getNome() String }
        class PedidoEstadoDevolvido  { +getNome() String }
    }

    PedidoContext    o-- PedidoEstado
    PedidoEstado     <|-- PedidoEstadoPendente
    PedidoEstado     <|-- PedidoEstadoConfirmado
    PedidoEstado     <|-- PedidoEstadoEmPreparo
    PedidoEstado     <|-- PedidoEstadoEntregue
    PedidoEstado     <|-- PedidoEstadoCancelado
    PedidoEstado     <|-- PedidoEstadoDevolvido

%% ════════════════════════════════════════════════════════════
%% 02 · OBSERVER
%% ════════════════════════════════════════════════════════════
    namespace OBSERVER {
        class Observer {
            <<interface>>
            +atualizar(String, Object)
        }
        class Observable {
            <<interface>>
            +adicionarObserver(Observer)
            +removerObserver(Observer)
            +notificarObservers(String, Object)
        }
        class Mesa {
            -int numero
            -String statusPedido
            -List~Observer~ observers
            -PedidoContext pedidoAtual
            +setPedidoAtual(PedidoContext)
            +avancarEstadoPedido()
            +setStatusPedido(String)
            +getStatusPedido() String
            +getPedidoAtual() PedidoContext
        }
        class ClienteObserver {
            -String nome
            +atualizar(String, Object)
            +getNome() String
        }
    }

    Observable   <|.. Mesa
    Observer     <|.. ClienteObserver
    Mesa         o-- Observer
    Mesa         --> PedidoContext : avança estado e notifica

%% ════════════════════════════════════════════════════════════
%% 03 · SINGLETON
%% ════════════════════════════════════════════════════════════
    namespace SINGLETON {
        class Configuracoes {
            -static Configuracoes instance
            -String nomeEstabelecimento
            -double taxaEntrega
            -int tempoMedioPreparo
            -boolean aceitaPix
            -boolean aceitaCartao
            -double limiteDescontoAtendente
            -double limiteDescontoSupervisor
            -double limiteDescontoGerente
            +static getInstance() Configuracoes
            +getTaxaEntrega() double
            +setTaxaEntrega(double)
            +getTempoMedioPreparo() int
            +getLimiteDescontoAtendente() double
            +setLimiteDescontoAtendente(double)
            +getLimiteDescontoSupervisor() double
            +setLimiteDescontoSupervisor(double)
            +getLimiteDescontoGerente() double
            +setLimiteDescontoGerente(double)
            +getNomeEstabelecimento() String
            +setNomeEstabelecimento(String)
        }
    }

    Configuracoes --> Configuracoes : instance

%% ════════════════════════════════════════════════════════════
%% 04 · FACTORY METHOD
%% ════════════════════════════════════════════════════════════
    namespace FACTORY_METHOD {
        class IServico {
            <<interface>>
            +executar()
            +getNome() String
        }
        class ServicoFactory {
            +static obterServico(String) IServico
        }
        class ServicoRealizarPedido    { +executar(); +getNome() String }
        class ServicoEmitirNota        { +executar(); +getNome() String }
        class ServicoCancelarPedido    { +executar(); +getNome() String }
        class ServicoEntregarPedido    { +executar(); +getNome() String }
        class ServicoProcessarPagamento{ +executar(); +getNome() String }
    }

    IServico <|.. ServicoRealizarPedido
    IServico <|.. ServicoEmitirNota
    IServico <|.. ServicoCancelarPedido
    IServico <|.. ServicoEntregarPedido
    IServico <|.. ServicoProcessarPagamento
    ServicoFactory ..> IServico

%% ════════════════════════════════════════════════════════════
%% 05 · ABSTRACT FACTORY
%% ════════════════════════════════════════════════════════════
    namespace ABSTRACT_FACTORY {
        class FabricaAbstrata {
            <<interface>>
            +criarLanche() Lanche
            +criarAcompanhamento() Acompanhamento
        }
        class Lanche         { <<interface>>; +descrever() String; +getPreco() double }
        class Acompanhamento { <<interface>>; +descrever() String; +getPreco() double }
        class FabricaClassico        { +criarLanche(); +criarAcompanhamento() }
        class FabricaGourmet         { +criarLanche(); +criarAcompanhamento() }
        class FabricaVegano          { +criarLanche(); +criarAcompanhamento() }
        class LancheClassico         { +descrever() String; +getPreco() double }
        class LancheGourmet          { +descrever() String; +getPreco() double }
        class LancheVegano           { +descrever() String; +getPreco() double }
        class AcompanhamentoClassico { +descrever() String; +getPreco() double }
        class AcompanhamentoGourmet  { +descrever() String; +getPreco() double }
        class AcompanhamentoVegano   { +descrever() String; +getPreco() double }
        class FabricaProvider        { +static getFabrica(String) FabricaAbstrata }
    }

    FabricaAbstrata  <|.. FabricaClassico
    FabricaAbstrata  <|.. FabricaGourmet
    FabricaAbstrata  <|.. FabricaVegano
    Lanche           <|.. LancheClassico
    Lanche           <|.. LancheGourmet
    Lanche           <|.. LancheVegano
    Acompanhamento   <|.. AcompanhamentoClassico
    Acompanhamento   <|.. AcompanhamentoGourmet
    Acompanhamento   <|.. AcompanhamentoVegano
    FabricaProvider  ..> FabricaAbstrata

%% ════════════════════════════════════════════════════════════
%% 06 · BRIDGE
%% ════════════════════════════════════════════════════════════
    namespace BRIDGE {
        class TipoRelatorio {
            <<interface>>
            +gerar() String
            +getTipo() String
        }
        class Funcionario {
            <<abstract>>
            #TipoRelatorio relatorio
            +emitirRelatorio()*
            +getCargo() String*
            +setRelatorio(TipoRelatorio)
        }
        class FuncionarioGerente    { +emitirRelatorio(); +getCargo() String }
        class FuncionarioCaixa      { +emitirRelatorio(); +getCargo() String }
        class FuncionarioCozinheiro { +emitirRelatorio(); +getCargo() String }
        class FuncionarioAtendente  { +emitirRelatorio(); +getCargo() String }
        class RelatorioVendas     { +gerar() String; +getTipo() String }
        class RelatorioEstoque    { +gerar() String; +getTipo() String }
        class RelatorioFinanceiro { +gerar() String; +getTipo() String }
        class RelatorioPedidos    { +gerar() String; +getTipo() String }
        class RelatorioDesempenho { +gerar() String; +getTipo() String }
    }

    Funcionario     o-- TipoRelatorio
    Funcionario     <|-- FuncionarioGerente
    Funcionario     <|-- FuncionarioCaixa
    Funcionario     <|-- FuncionarioCozinheiro
    Funcionario     <|-- FuncionarioAtendente
    TipoRelatorio   <|.. RelatorioVendas
    TipoRelatorio   <|.. RelatorioEstoque
    TipoRelatorio   <|.. RelatorioFinanceiro
    TipoRelatorio   <|.. RelatorioPedidos
    TipoRelatorio   <|.. RelatorioDesempenho

%% ════════════════════════════════════════════════════════════
%% 07 · STRATEGY
%% ════════════════════════════════════════════════════════════
    namespace STRATEGY {
        class Operacao {
            <<interface>>
            +executar(double) double
            +getDescricao() String
        }
        class Calculadora {
            -Operacao operacao
            +calcular(double) double
            +setOperacao(Operacao)
        }
        class OperacaoSemDesconto { +executar(double) double }
        class OperacaoDesconto    { -double percentual; +executar(double) double }
        class OperacaoAcrescimo   { -double percentual; +executar(double) double }
        class OperacaoTaxaEntrega { -double taxaFixa; +executar(double) double }
        class OperacaoCupom       { -String codigo; -double valor; +executar(double) double }
        class OperacaoFidelidade  { -int pontos; +executar(double) double }
    }

    Calculadora  o-- Operacao
    Operacao     <|.. OperacaoSemDesconto
    Operacao     <|.. OperacaoDesconto
    Operacao     <|.. OperacaoAcrescimo
    Operacao     <|.. OperacaoTaxaEntrega
    Operacao     <|.. OperacaoCupom
    Operacao     <|.. OperacaoFidelidade

%% ════════════════════════════════════════════════════════════
%% 08 · BUILDER
%% ════════════════════════════════════════════════════════════
    namespace BUILDER {
        class LanchePersonalizado {
            -String nome
            -String tipoPao
            -String tipoCarne
            -boolean queijo
            -boolean bacon
            -boolean alface
            -boolean tomate
            -boolean pickles
            -String molho
            -double preco
            +getNome() String
            +getPreco() double
            +temQueijo() boolean
            +temBacon() boolean
            +getMolho() String
        }
        class LancheBuilder {
            +iniciar(String,String,String) LancheBuilder
            +adicionarQueijo() LancheBuilder
            +adicionarBacon() LancheBuilder
            +adicionarAlface() LancheBuilder
            +adicionarTomate() LancheBuilder
            +adicionarPickles() LancheBuilder
            +adicionarMolho(String) LancheBuilder
            +definirPrecoBase(double) LancheBuilder
            +build() LanchePersonalizado
        }
    }

    LancheBuilder ..> LanchePersonalizado

%% ════════════════════════════════════════════════════════════
%% 09 · CHAIN OF RESPONSIBILITY
%% ════════════════════════════════════════════════════════════
    namespace CHAIN_OF_RESPONSIBILITY {
        class Aprovador {
            <<abstract>>
            #Aprovador proximo
            +setProximo(Aprovador)
            +processar(SolicitacaoDesconto)*
            #passarParaProximo(SolicitacaoDesconto)
        }
        class SolicitacaoDesconto {
            -int id
            -String cliente
            -double valorPedido
            -double percentualDesconto
            -String motivo
            +getCliente() String
            +getPercentualDesconto() double
            +getValorPedido() double
        }
        class Atendente  { +processar(SolicitacaoDesconto) }
        class Supervisor { +processar(SolicitacaoDesconto) }
        class Gerente    { +processar(SolicitacaoDesconto) }
        class Dono       { +processar(SolicitacaoDesconto) }
    }

    Aprovador  o-- Aprovador : proximo
    Aprovador  <|-- Atendente
    Aprovador  <|-- Supervisor
    Aprovador  <|-- Gerente
    Aprovador  <|-- Dono
    Aprovador  ..> SolicitacaoDesconto
    Atendente  --> Configuracoes : getLimiteDescontoAtendente()
    Supervisor --> Configuracoes : getLimiteDescontoSupervisor()
    Gerente    --> Configuracoes : getLimiteDescontoGerente()

%% ════════════════════════════════════════════════════════════
%% 10 · TEMPLATE METHOD
%% ════════════════════════════════════════════════════════════
    namespace TEMPLATE_METHOD {
        class PreparoLanche {
            <<abstract>>
            +preparar()
            #selecionarIngredientes()*
            #grelharCarne()*
            #montarLanche()*
            #getNome() String*
            #adicionarMolho() boolean
            #aplicarMolho()
        }
        class PreparoSmashBurguer { #selecionarIngredientes(); #grelharCarne(); #montarLanche() }
        class PreparoXBurguer     { #selecionarIngredientes(); #grelharCarne(); #montarLanche() }
        class PreparoVegano       { #selecionarIngredientes(); #grelharCarne(); #montarLanche(); #adicionarMolho() boolean }
        class PreparoFrango       { #selecionarIngredientes(); #grelharCarne(); #montarLanche() }
    }

    PreparoLanche <|-- PreparoSmashBurguer
    PreparoLanche <|-- PreparoXBurguer
    PreparoLanche <|-- PreparoVegano
    PreparoLanche <|-- PreparoFrango

%% ════════════════════════════════════════════════════════════
%% 11 · MEDIATOR
%% ════════════════════════════════════════════════════════════
    namespace MEDIATOR {
        class Central {
            -Map~String,Pessoa~ pessoas
            -Map~String,Setor~ setores
            +registrarPessoa(Pessoa)
            +registrarSetor(Setor)
            +enviarParaSetor(String,String,String)
            +enviarParaPessoa(String,String,String)
            +broadcast(String,String)
        }
        class Pessoa {
            <<abstract>>
            #Central central
            #String nome
            +enviarMensagem(String,String)*
            +receberMensagem(String,String)*
            +getNome() String
        }
        class SetorMediator {
            <<interface>>
            +receberMensagem(String,String)
            +getNome() String
        }
        class ClienteHamburgueria { +enviarMensagem(); +receberMensagem(); +getNome() String }
        class CozinhaMediator     { +receberMensagem(); +getNome() String }
        class CaixaMediator       { +receberMensagem(); +getNome() String }
        class EntregaMediator     { +receberMensagem(); +getNome() String }
    }

    Central  o-- Pessoa
    Central  o-- SetorMediator
    Pessoa   <|-- ClienteHamburgueria
    SetorMediator <|.. CozinhaMediator
    SetorMediator <|.. CaixaMediator
    SetorMediator <|.. EntregaMediator
    Pessoa   --> Central
    SetorMediator --> Central

%% ════════════════════════════════════════════════════════════
%% 12 · FACADE
%% ════════════════════════════════════════════════════════════
    namespace FACADE {
        class PedidoFacade {
            -SetorEstoque estoque
            -SetorCozinhaFacade cozinhaFacade
            -SetorPagamento pagamento
            -SetorEntregaFacade entregaFacade
            -FilaCozinha filaCozinha
            -Calculadora calculadora
            +realizarPedidoBalcao(String,String,double,String)
            +realizarPedidoDelivery(String,String,double,String,String,String)
            +cancelarPedido(String,double)
            +fecharCaixaDoDia()
            +getFilaCozinha() FilaCozinha
        }
        class SetorEstoque       { +verificarDisponibilidade(String) boolean; +reservarIngrediente(String,int); +emitirAlertaEstoqueBaixo() }
        class SetorCozinhaFacade { +iniciarPreparo(String); +notificarPronto(String); +registrarTempoMedio(int) }
        class SetorPagamento     { +processarPagamento(String,double,String) boolean; +emitirComprovante(String,double) }
        class SetorEntregaFacade { +registrarEndereco(String,String); +despacharEntregador(String); +calcularTaxaEntrega(String) double }
    }

    PedidoFacade *-- SetorEstoque
    PedidoFacade *-- SetorCozinhaFacade
    PedidoFacade *-- SetorPagamento
    PedidoFacade *-- SetorEntregaFacade
    PedidoFacade --> FilaCozinha   : usa COMMAND
    PedidoFacade --> Calculadora   : usa STRATEGY
    PedidoFacade --> Configuracoes : lê taxaEntrega e tempoPreparo

%% ════════════════════════════════════════════════════════════
%% 13 · COMPOSITE
%% ════════════════════════════════════════════════════════════
    namespace COMPOSITE {
        class Conteudo {
            <<abstract>>
            #String nome
            #double preco
            +getNome() String
            +getPreco() double
            +exibir(String)*
            +calcularTotal() double*
            +adicionar(Conteudo)
            +remover(Conteudo)
        }
        class Item {
            +exibir(String)
            +calcularTotal() double
        }
        class Combo {
            -List~Conteudo~ itens
            +adicionar(Conteudo)
            +remover(Conteudo)
            +getItens() List~Conteudo~
            +exibir(String)
            +calcularTotal() double
        }
        class Cardapio {
            -String nome
            -Conteudo raiz
            +exibir()
            +getTotal() double
        }
    }

    Conteudo <|-- Item
    Conteudo <|-- Combo
    Combo    o-- Conteudo
    Cardapio --> Conteudo

%% ════════════════════════════════════════════════════════════
%% 14 · MEMENTO
%% ════════════════════════════════════════════════════════════
    namespace MEMENTO {
        class CarrinhoEstado {
            <<interface>>
            +getStatus() String
            +getItens() List
            +getTotal() double
            +getObservacoes() String
            +getMomento() String
        }
        class CarrinhoSnapshot {
            -String status
            -List~String~ itens
            -double total
            -String observacoes
            +getStatus() String
            +getItens() List
            +getTotal() double
        }
        class Carrinho {
            -String nomeCliente
            -List~String~ itens
            -double total
            -String status
            +adicionarItem(String,double)
            +removerItem(String,double)
            +setObservacoes(String)
            +salvar() CarrinhoSnapshot
            +restaurar(CarrinhoSnapshot)
            +getTotal() double
        }
        class HistoricoCarrinho {
            -Deque~CarrinhoSnapshot~ pilha
            +push(CarrinhoSnapshot)
            +pop() CarrinhoSnapshot
            +tamanho() int
        }
        class CarrinhoEstadoAberto    { +getStatus() String }
        class CarrinhoEstadoFechado   { +getStatus() String }
        class CarrinhoEstadoPago      { +getStatus() String }
        class CarrinhoEstadoEmEntrega { +getStatus() String }
        class CarrinhoEstadoEntregue  { +getStatus() String }
        class CarrinhoEstadoCancelado { +getStatus() String }
    }

    CarrinhoEstado   <|.. CarrinhoSnapshot
    CarrinhoEstado   <|.. CarrinhoEstadoAberto
    CarrinhoEstado   <|.. CarrinhoEstadoFechado
    CarrinhoEstado   <|.. CarrinhoEstadoPago
    CarrinhoEstado   <|.. CarrinhoEstadoEmEntrega
    CarrinhoEstado   <|.. CarrinhoEstadoEntregue
    CarrinhoEstado   <|.. CarrinhoEstadoCancelado
    Carrinho         ..> CarrinhoSnapshot
    HistoricoCarrinho o-- CarrinhoSnapshot

%% ════════════════════════════════════════════════════════════
%% 15 · VISITOR
%% ════════════════════════════════════════════════════════════
    namespace VISITOR {
        class Elemento {
            <<interface>>
            +aceitar(Visitante)
        }
        class Visitante {
            <<interface>>
            +visitar(ItemCardapio)
            +visitar(ComboCardapio)
            +visitar(Promocao)
        }
        class ItemCardapio  { -String nome; -double preco; -double calorias; -boolean disponivel; +aceitar(Visitante) }
        class ComboCardapio { -String nome; -List~ItemCardapio~ itens; -double desconto; +aceitar(Visitante); +getPrecoFinal() double }
        class Promocao      { -String nome; -double percentualDesconto; -LocalDate validade; +aceitar(Visitante); +isAtiva() boolean }
        class VisitanteRelatorio { +visitar(ItemCardapio); +visitar(ComboCardapio); +visitar(Promocao); +exibirResumo() }
        class VisitanteAuditoria { +visitar(ItemCardapio); +visitar(ComboCardapio); +visitar(Promocao); +exibirResumo() }
        class VisitanteDesconto  { -double percentualExtra; +visitar(ItemCardapio); +visitar(ComboCardapio); +visitar(Promocao) }
    }

    Elemento  <|.. ItemCardapio
    Elemento  <|.. ComboCardapio
    Elemento  <|.. Promocao
    Visitante <|.. VisitanteRelatorio
    Visitante <|.. VisitanteAuditoria
    Visitante <|.. VisitanteDesconto
    Elemento  ..> Visitante

%% ════════════════════════════════════════════════════════════
%% 16 · DECORATOR
%% ════════════════════════════════════════════════════════════
    namespace DECORATOR {
        class IngredienteDecorator {
            <<interface>>
            +getDescricao() String
            +getPreco() double
            +getCalorias() double
        }
        class LancheBase {
            -String nome
            -double preco
            -double calorias
            +getDescricao() String
            +getPreco() double
            +getCalorias() double
        }
        class IngredienteDecoradorBase {
            <<abstract>>
            #IngredienteDecorator ingrediente
            +getDescricao() String
            +getPreco() double
            +getCalorias() double
        }
        class ComQueijo        { +getDescricao() String; +getPreco() double; +getCalorias() double }
        class ComBacon         { +getDescricao() String; +getPreco() double; +getCalorias() double }
        class ComAlface        { +getDescricao() String; +getPreco() double; +getCalorias() double }
        class ComTomate        { +getDescricao() String; +getPreco() double; +getCalorias() double }
        class ComPickles       { +getDescricao() String; +getPreco() double; +getCalorias() double }
        class ComOvo           { +getDescricao() String; +getPreco() double; +getCalorias() double }
        class ComCatupiry      { +getDescricao() String; +getPreco() double; +getCalorias() double }
        class ComMolhoEspecial { +getDescricao() String; +getPreco() double; +getCalorias() double }
    }

    IngredienteDecorator     <|.. LancheBase
    IngredienteDecorator     <|.. IngredienteDecoradorBase
    IngredienteDecoradorBase o-- IngredienteDecorator
    IngredienteDecoradorBase <|-- ComQueijo
    IngredienteDecoradorBase <|-- ComBacon
    IngredienteDecoradorBase <|-- ComAlface
    IngredienteDecoradorBase <|-- ComTomate
    IngredienteDecoradorBase <|-- ComPickles
    IngredienteDecoradorBase <|-- ComOvo
    IngredienteDecoradorBase <|-- ComCatupiry
    IngredienteDecoradorBase <|-- ComMolhoEspecial

%% ════════════════════════════════════════════════════════════
%% 17 · ITERATOR
%% ════════════════════════════════════════════════════════════
    namespace ITERATOR {
        class Iteravel {
            <<interface>>
            +criarIterador() Iterador
        }
        class Iterador {
            <<interface>>
            +temProximo() boolean
            +proximo() ItemPedido
            +reiniciar()
        }
        class ItemPedido {
            -String nome
            -String categoria
            -double preco
            -double calorias
            -boolean disponivel
            +getNome() String
            +getCategoria() String
            +getPreco() double
            +isDisponivel() boolean
        }
        class CardapioHamburgueria {
            -List~ItemPedido~ itens
            +adicionarItem(ItemPedido)
            +carregarDoComposite(Conteudo)
            +criarIterador() Iterador
            +criarIteradorPorCategoria(String) Iterador
            +criarIteradorDisponiveis() Iterador
            +criarIteradorPorFaixaPreco(double,double) Iterador
        }
        class IteradorCardapio      { +temProximo() boolean; +proximo() ItemPedido; +reiniciar() }
        class IteradorPorCategoria  { +temProximo() boolean; +proximo() ItemPedido; +totalFiltrado() int }
        class IteradorDisponiveis   { +temProximo() boolean; +proximo() ItemPedido; +totalDisponiveis() int }
        class IteradorPorFaixaPreco { +temProximo() boolean; +proximo() ItemPedido }
    }

    Iteravel  <|.. CardapioHamburgueria
    Iterador  <|.. IteradorCardapio
    Iterador  <|.. IteradorPorCategoria
    Iterador  <|.. IteradorDisponiveis
    Iterador  <|.. IteradorPorFaixaPreco
    CardapioHamburgueria o-- ItemPedido
    Iterador             ..> ItemPedido
    CardapioHamburgueria --> Conteudo : carregarDoComposite

%% ════════════════════════════════════════════════════════════
%% 18 · PROTOTYPE
%% ════════════════════════════════════════════════════════════
    namespace PROTOTYPE {
        class Clonavel {
            <<interface>>
            +clonar() Clonavel
        }
        class EnderecoEntrega {
            -String rua
            -String numero
            -String bairro
            -String cidade
            -String complemento
            +clonar() EnderecoEntrega
            +getRua() String
            +setRua(String)
        }
        class PedidoTemplate {
            -String nomeCliente
            -String tipoPagamento
            -List~String~ itens
            -EnderecoEntrega endereco
            +clonar() PedidoTemplate
            +adicionarItem(String)
            +removerItem(String)
            +getItens() List
            +getEndereco() EnderecoEntrega
        }
        class RegistroPedidos {
            -Map~String,PedidoTemplate~ templates
            +registrar(String,PedidoTemplate)
            +clonarTemplate(String) PedidoTemplate
            +listarTemplates()
        }
    }

    Clonavel        <|.. EnderecoEntrega
    Clonavel        <|.. PedidoTemplate
    PedidoTemplate  o-- EnderecoEntrega
    RegistroPedidos o-- PedidoTemplate

%% ════════════════════════════════════════════════════════════
%% 19 · FLYWEIGHT
%% ════════════════════════════════════════════════════════════
    namespace FLYWEIGHT {
        class Ingrediente {
            -String nome
            -String tipo
            -double custoUnitario
            -double caloriasUnitarias
            -String descricao
            +getNome() String
            +getCustoUnitario() double
            +getCaloriasUnitarias() double
            +aplicarNoPedido(String,int)
        }
        class IngredienteFactory {
            -static Map~String,Ingrediente~ cache
            +static obter(String,String,double,double,String) Ingrediente
            +static obter(String) Ingrediente
            +static totalInstancias() int
            +static limparCache()
        }
        class PedidoFlyweight {
            -String numeroPedido
            -String nomeCliente
            +adicionarIngrediente(Ingrediente,int)
            +exibir()
        }
        class HamburgueriaFlyweight {
            -String nome
            -List~PedidoFlyweight~ pedidos
            +adicionarPedido(PedidoFlyweight)
            +exibirTodosPedidos()
        }
    }

    IngredienteFactory    ..> Ingrediente
    PedidoFlyweight       o-- Ingrediente
    HamburgueriaFlyweight o-- PedidoFlyweight

%% ════════════════════════════════════════════════════════════
%% 20 · COMMAND
%% ════════════════════════════════════════════════════════════
    namespace COMMAND {
        class Comando {
            <<interface>>
            +executar()
            +desfazer()
            +getDescricao() String
        }
        class PedidoCozinha {
            -String numero
            -String nomeCliente
            -String status
            -List~String~ itens
            +abrir()
            +fechar()
            +iniciarPreparo()
            +finalizarPreparo()
            +cancelar()
            +adicionarItem(String)
            +removerItem(String)
            +aplicarDesconto(double)
            +getStatus() String
            +getItens() List
        }
        class FilaCozinha {
            -List~Comando~ filaExecucao
            -Deque~Comando~ pilhaUndo
            +enfileirar(Comando)
            +executar(Comando)
            +executarFila()
            +desfazerUltimo()
            +desfazerTodos()
            +totalNaFila() int
        }
        class AbrirPedidoComando      { +executar(); +desfazer() }
        class FecharPedidoComando     { +executar(); +desfazer() }
        class IniciarPreparoComando   { +executar(); +desfazer() }
        class FinalizarPreparoComando { +executar(); +desfazer() }
        class AdicionarItemComando    { -String item; +executar(); +desfazer() }
        class AplicarDescontoComando  { -double percentual; +executar(); +desfazer() }
        class CancelarPedidoComando   { +executar(); +desfazer() }
    }

    Comando  <|.. AbrirPedidoComando
    Comando  <|.. FecharPedidoComando
    Comando  <|.. IniciarPreparoComando
    Comando  <|.. FinalizarPreparoComando
    Comando  <|.. AdicionarItemComando
    Comando  <|.. AplicarDescontoComando
    Comando  <|.. CancelarPedidoComando
    FilaCozinha             o-- Comando
    AbrirPedidoComando      --> PedidoCozinha
    FecharPedidoComando     --> PedidoCozinha
    IniciarPreparoComando   --> PedidoCozinha
    FinalizarPreparoComando --> PedidoCozinha
    AdicionarItemComando    --> PedidoCozinha
    AplicarDescontoComando  --> PedidoCozinha
    CancelarPedidoComando   --> PedidoCozinha

%% ════════════════════════════════════════════════════════════
%% 21 · ADAPTER
%% ════════════════════════════════════════════════════════════
    namespace ADAPTER {
        class iPagamento {
            <<interface>>
            +processar(String,double) boolean
            +getFormaPagamento() String
            +getComprovante() String
        }
        class iEntrega {
            <<interface>>
            +despachar(String,String) boolean
            +getRastreamento() String
            +getTaxaEntrega(String) double
        }
        class PagamentoPix    { +processar() boolean; +getFormaPagamento() String; +getComprovante() String }
        class PagamentoCartao { -String tipoCartao; +processar() boolean; +getFormaPagamento() String }
        class SistemaPagamentoLegado {
            +iniciarTransacao(String,String,double)
            +confirmarTransacao() String
            +verificarStatus() boolean
            +gerarRecibo(String) String
        }
        class PagamentoLegadoAdapter {
            -SistemaPagamentoLegado sistemaLegado
            +processar(String,double) boolean
            +getFormaPagamento() String
            +getComprovante() String
        }
        class SistemaEntregaExterna {
            +criarDespacho(String,double)
            +gerarRastreamento() String
            +calcularFrete(String) double
        }
        class EntregaExternaAdapter {
            -SistemaEntregaExterna sistemaExterno
            +despachar(String,String) boolean
            +getRastreamento() String
            +getTaxaEntrega(String) double
        }
        class CaixaRegistradora {
            -String numeroCaixa
            +cobrar(String,double,iPagamento)
            +despacharEntrega(String,String,String,iEntrega)
        }
    }

    iPagamento <|.. PagamentoPix
    iPagamento <|.. PagamentoCartao
    iPagamento <|.. PagamentoLegadoAdapter
    PagamentoLegadoAdapter o-- SistemaPagamentoLegado
    iEntrega   <|.. EntregaExternaAdapter
    EntregaExternaAdapter  o-- SistemaEntregaExterna
    CaixaRegistradora ..> iPagamento
    CaixaRegistradora ..> iEntrega

%% ════════════════════════════════════════════════════════════
%% 22 · PROXY
%% ════════════════════════════════════════════════════════════
    namespace PROXY {
        class ICardapio {
            <<interface>>
            +listarItens() List
            +buscarItem(String) String
            +adicionarItem(String,double)
            +removerItem(String)
            +atualizarPreco(String,double)
            +consultarPreco(String) double
        }
        class CardapioReal {
            -Map~String,Double~ itens
            -BancoDados bancoDados
            +listarItens() List
            +buscarItem(String) String
            +adicionarItem(String,double)
            +removerItem(String)
            +consultarPreco(String) double
        }
        class CardapioProxy {
            -CardapioReal cardapioReal
            -FuncionarioAcesso usuarioAtual
            -List~String~ cacheListagem
            -boolean cacheValido
            +setUsuarioAtual(FuncionarioAcesso)
            +listarItens() List
            +buscarItem(String) String
            +adicionarItem(String,double)
            +consultarPreco(String) double
        }
        class BancoDados {
            -Map~String,Double~ itens
            +carregarItens() Map
            +salvarItem(String,double)
            +deletarItem(String)
            +atualizarItem(String,double)
        }
        class FuncionarioAcesso {
            -String nome
            -Perfil perfil
            -String senha
            +autenticar(String) boolean
            +getNome() String
            +getPerfil() Perfil
        }
    }

    ICardapio     <|.. CardapioReal
    ICardapio     <|.. CardapioProxy
    CardapioProxy o-- CardapioReal
    CardapioProxy --> FuncionarioAcesso
    CardapioProxy --> Configuracoes : valida via SINGLETON
    CardapioReal  --> BancoDados

%% ════════════════════════════════════════════════════════════
%% 23 · INTERPRETER
%% ════════════════════════════════════════════════════════════
    namespace INTERPRETER {
        class ExpressaoCardapio {
            <<interface>>
            +interpretar() double
            +representar() String
        }
        class ValorNumerico  { -double valor; -String rotulo; +interpretar() double; +representar() String }
        class Adicao         { -ExpressaoCardapio esquerda; -ExpressaoCardapio direita; +interpretar() double }
        class Subtracao      { -ExpressaoCardapio esquerda; -ExpressaoCardapio direita; +interpretar() double }
        class Multiplicacao  { -ExpressaoCardapio esquerda; -ExpressaoCardapio direita; +interpretar() double }
        class Divisao        { -ExpressaoCardapio esquerda; -ExpressaoCardapio direita; +interpretar() double }
        class Percentual     { -ExpressaoCardapio base; -ExpressaoCardapio porcentagem; +interpretar() double }
        class CalculadoraCardapio {
            +calcular(ExpressaoCardapio) double
            +totalCombo(double,double) ExpressaoCardapio
            +aplicarDesconto(double,double) ExpressaoCardapio
            +dividirConta(double,int) ExpressaoCardapio
            +totalComQuantidade(double,int) ExpressaoCardapio
        }
        class PedidoInterpreter {
            -String numero
            -String cliente
            -List~ExpressaoCardapio~ expressoes
            +adicionarExpressao(ExpressaoCardapio)
            +calcularTotal() double
        }
        class HamburgueriaInterpreter {
            -String nome
            -List~PedidoInterpreter~ pedidos
            +adicionarPedido(PedidoInterpreter)
            +calcularFaturamentoDoDia() double
            +calcularTicketMedio() double
        }
    }

    ExpressaoCardapio <|.. ValorNumerico
    ExpressaoCardapio <|.. Adicao
    ExpressaoCardapio <|.. Subtracao
    ExpressaoCardapio <|.. Multiplicacao
    ExpressaoCardapio <|.. Divisao
    ExpressaoCardapio <|.. Percentual
    Adicao        o-- ExpressaoCardapio
    Subtracao     o-- ExpressaoCardapio
    Multiplicacao o-- ExpressaoCardapio
    Divisao       o-- ExpressaoCardapio
    Percentual    o-- ExpressaoCardapio
    CalculadoraCardapio     ..> ExpressaoCardapio
    PedidoInterpreter       o-- ExpressaoCardapio
    PedidoInterpreter       --> CalculadoraCardapio
    HamburgueriaInterpreter o-- PedidoInterpreter
    HamburgueriaInterpreter --> CalculadoraCardapio

%% ════════════════════════════════════════════════════════════
%% INTEGRAÇÕES ENTRE PADRÕES
%% ════════════════════════════════════════════════════════════
    Mesa         --> PedidoContext    : OBSERVER usa STATE
    PedidoFacade --> FilaCozinha      : FACADE usa COMMAND
    PedidoFacade --> Calculadora      : FACADE usa STRATEGY
    PedidoFacade --> Configuracoes    : FACADE lê SINGLETON
    Atendente    --> Configuracoes    : CHAIN lê SINGLETON
    Supervisor   --> Configuracoes    : CHAIN lê SINGLETON
    Gerente      --> Configuracoes    : CHAIN lê SINGLETON
    CardapioProxy --> Configuracoes   : PROXY valida via SINGLETON
    CardapioHamburgueria --> Conteudo : ITERATOR carrega do COMPOSITE
```


