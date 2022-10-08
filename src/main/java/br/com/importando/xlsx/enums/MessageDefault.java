package br.com.importando.xlsx.enums;

public enum MessageDefault {
    MINUTINHO(" como está??" +
            "\n" + "Tem um minutinho?"),
    MINUTINHO2(" tudo bem ??" + "\n" +
            "Me chamo Jessica Gurgel e sou correspondente bancária, poderia me dar um minuto de sua atenção?"),
    CARTAO(" tudo bem? " + "\n" +
            "Banco BMG informa, Seu CARTÃO DE CRÉDITO, Sem taxa de adesão, Sem Anuidade e a custo ZERO Foi Aprovado! E está pronto Para ser enviado.\n" +
            "Digite 1 Para saber o limite."),
    LIMITE_EMPRESTIMO(" tudo bem com você? ️\n" +
            "BANCO BMG informa, seu beneficio BPC/LOAS foi liberado para contratação de empréstimos.\n" +
            "Valores liberados chegam até R$ 17.900,00.\n" +
            "Aproveite e não fique fora dessa!!!\n" +
            "Digite 1, para falar com um de nossos atendentes e saber mais."),
    OPORTUNIDADE_EMPRESTIMO(", deixa eu te contar uma novidade!!! Agora com a medida provisória liberada no seu benefício, você pode contratar um valor bem significativo e com uma parcelinhas que cabem no seu bolso.\n" +
            "\n" +
            "E o melhor, com o prazo feito de acordo com a sua disponibilidade financeira!\n" +
            "\n" +
            "Posso encaminhar uma simulação sem compromisso para você analisar os valores que você tem a sua disposição?"),
    RESPOSTA("Obrigada pelo seu retorno!\n" +
            "Estou te encaminhando a um de nossos atendentes."),
    DIGITE2("\n Digite 2 se você não é ");
    private final String message;

    private MessageDefault(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}