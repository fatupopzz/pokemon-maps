package com.pokemon.model;

/**
 * Clase que representa a un Pokémon con sus atributos según el CSV pokemon_data_pokeapi.csv.
 */
public class Pokemon {
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
     * Constructor para crear un nuevo Pokémon
     */
    public Pokemon(String name, int pokedexNumber, String type1, String type2, 
                 String classification, float height, float weight, 
                 String abilities, int generation, boolean legendary) {
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

    // Getters
    public String getName() {
        return name;
    }
    
    public int getPokedexNumber() {
        return pokedexNumber;
    }
    
    public String getType1() {
        return type1;
    }
    
    public String getType2() {
        return type2;
    }
    
    public String getClassification() {
        return classification;
    }
    
    public float getHeight() {
        return height;
    }
    
    public float getWeight() {
        return weight;
    }
    
    public String getAbilities() {
        return abilities;
    }
    
    public int getGeneration() {
        return generation;
    }
    
    public boolean isLegendary() {
        return legendary;
    }
    
    /**
     * Convierte el Pokémon a formato JSON para la API
     */
    public String toJson() {
        return "{"
            + "\"name\":\"" + name + "\","
            + "\"pokedexNumber\":" + pokedexNumber + ","
            + "\"type1\":\"" + type1 + "\","
            + "\"type2\":\"" + type2 + "\","
            + "\"classification\":\"" + escapeJson(classification) + "\","
            + "\"height\":" + height + ","
            + "\"weight\":" + weight + ","
            + "\"abilities\":\"" + escapeJson(abilities) + "\","
            + "\"generation\":" + generation + ","
            + "\"legendary\":" + legendary 
            + "}";
    }
    
    /**
     * Método toString para mostrar la información del Pokémon
     */
    @Override
    public String toString() {
        return "Pokemon: " + name +
               "\nNúmero Pokédex: " + pokedexNumber +
               "\nTipo Principal: " + type1 +
               (type2 != null && !type2.isEmpty() ? "\nTipo Secundario: " + type2 : "") +
               "\nClasificación: " + classification +
               "\nAltura: " + height + " m" +
               "\nPeso: " + weight + " kg" +
               "\nHabilidades: " + abilities +
               "\nGeneración: " + generation +
               "\nLegendario: " + (legendary ? "Sí" : "No");
    }
    
    /**
     * Escapa caracteres especiales para JSON
     */
    private String escapeJson(String text) {
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