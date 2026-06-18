package com.hamburgueria.fabricacao_combos;

public class FabricaProvider {

    public static FabricaAbstrata getFabrica(String tipo) {
        return switch (tipo.toUpperCase()) {
            case "CLASSICO" -> new FabricaClassico();
            case "GOURMET"  -> new FabricaGourmet();
            case "VEGANO"   -> new FabricaVegano();
            default -> throw new IllegalArgumentException("Fábrica desconhecida: " + tipo);
        };
    }
}