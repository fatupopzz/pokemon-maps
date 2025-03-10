package com.pokemon.model;

/**
 * Clase que representa a un Pokémon con sus atributos.
 * Autor: Fatima Navarro - 24044
 */
public class Pokemon {
    //--------
    // Atributos principales de un Pokémon basados en el CSV
    // Se mantienen privados para seguir el principio de encapsulamiento
    //--------
    private String name;
    private int pokedexNumber;
    private String type1;
    private String type2;
    private String classification;
    private float height;
    private float weight;
    private String abilities;
    private int generation;
    private boolean legendary;

    /**
     * Constructor completo para crear un Pokémon con todos sus atributos.
     */
    public Pokemon(String name, int pokedexNumber, String type1, String type2, 
                 String classification, float height, float weight, 
                 String abilities, int generation, boolean legendary) {
        //--------
        // Inicialización de todos los atributos mediante constructor
        // No se realizan validaciones complejas para mantener la simplicidad
        //--------
        this.name = name;
        this.pokedexNumber = pokedexNumber;
        this.type1 = type1;
        this.type2 = type2;
        this.classification = classification;
        this.height = height;
        this.weight = weight;
        this.abilities = abilities;
        this.generation = generation;
        this.legendary = legendary;
    }

    // Getters para acceder a los atributos privados
    public String getName() { return name; }
    public int getPokedexNumber() { return pokedexNumber; }
    public String getType1() { return type1; }
    public String getType2() { return type2; }
    public String getClassification() { return classification; }
    public float getHeight() { return height; }
    public float getWeight() { return weight; }
    public String getAbilities() { return abilities; }
    public int getGeneration() { return generation; }
    public boolean isLegendary() { return legendary; }
    
    /**
     * Convierte el Pokémon a formato JSON para API o exportación.
     */
    public String toJson() {
        //--------
        // Creación manual de JSON en lugar de usar bibliotecas externas
        // Se aplica escape a caracteres especiales para garantizar validez
        //--------
        return "{"
            + "\"name\":\"" + name + "\","
            + "\"pokedexNumber\":" + pokedexNumber + ","
            + "\"type1\":\"" + type1 + "\","
            + "\"type2\":\"" + (type2 != null ? type2 : "") + "\","
            + "\"classification\":\"" + escapeJson(classification) + "\","
            + "\"height\":" + height + ","
            + "\"weight\":" + weight + ","
            + "\"abilities\":\"" + escapeJson(abilities) + "\","
            + "\"generation\":" + generation + ","
            + "\"legendary\":" + legendary 
            + "}";
    }
    
    /**
     * Método toString para mostrar información del Pokémon de forma amigable.
     */
    @Override
    public String toString() {
        //--------
        // Representación visual mejorada con formato de tarjeta
        // Muestra la información de manera estructurada y legible
        //--------
        StringBuilder sb = new StringBuilder();
        sb.append("┌──────────────────────────────────┐\n");
        sb.append(String.format("│ %-32s │\n", "POKÉMON #" + pokedexNumber));
        sb.append(String.format("│ %-32s │\n", name.toUpperCase()));
        sb.append("├──────────────────────────────────┤\n");
        sb.append(String.format("│ Tipo: %-26s │\n", type1 + (type2 != null && !type2.isEmpty() ? "/" + type2 : "")));
        sb.append(String.format("│ Clasificación: %-18s │\n", classification));
        sb.append(String.format("│ Altura: %-25s │\n", height + " m"));
        sb.append(String.format("│ Peso: %-26s │\n", weight + " kg"));
        sb.append(String.format("│ Habilidades: %-20s │\n", abilities));
        sb.append(String.format("│ Generación: %-21s │\n", generation));
        sb.append(String.format("│ Legendario: %-21s │\n", legendary ? "Sí" : "No"));
        sb.append("└──────────────────────────────────┘");
        return sb.toString();
    }
    
    /**
     * Método para visualización simplificada en listados.
     */
    public String toShortString() {
        //--------
        // Versión compacta para listados donde no se requiere
        // mostrar toda la información detallada
        //--------
        return String.format("#%03d %-15s | %-10s | Gen %d", 
                            pokedexNumber, 
                            name, 
                            type1 + (type2 != null && !type2.isEmpty() ? "/" + type2 : ""),
                            generation);
    }
    
    /**
     * Escapa caracteres especiales para JSON.
     */
    private String escapeJson(String text) {
        //--------
        // Método auxiliar que garantiza que las cadenas sean
        // compatibles con el formato JSON escapando caracteres especiales
        //--------
        if (text == null) {
            return "";
        }
        
        return text.replace("\"", "\\\"")
                  .replace("\\", "\\\\")
                  .replace("/", "\\/")
                  .replace("\b", "\\b")
                  .replace("\f", "\\f")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}