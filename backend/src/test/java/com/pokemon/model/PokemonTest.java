package com.pokemon.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Pruebas para la clase Pokemon
 * Autor: Fatima Navarro
 */
public class PokemonTest {
    
    @Test
    public void testCrearPokemon() {
        // Crear un Pokémon para la prueba
        Pokemon pokemon = new Pokemon(
            "Pikachu", 
            25, 
            "Electric", 
            "", 
            "Mouse Pokémon", 
            0.4f, 
            6.0f, 
            "Static", 
            1, 
            false
        );
        
        // Verificar que los valores se almacenan correctamente
        assertEquals("Pikachu", pokemon.getName());
        assertEquals(25, pokemon.getPokedexNumber());
        assertEquals("Electric", pokemon.getType1());
        assertEquals("", pokemon.getType2());
        assertEquals("Mouse Pokémon", pokemon.getClassification());
        assertEquals(0.4f, pokemon.getHeight());
        assertEquals(6.0f, pokemon.getWeight());
        assertEquals("Static", pokemon.getAbilities());
        assertEquals(1, pokemon.getGeneration());
        assertFalse(pokemon.isLegendary());
    }
    
    @Test
    public void testToJson() {
        // Crear un Pokémon para la prueba
        Pokemon pokemon = new Pokemon(
            "Mewtwo", 
            150, 
            "Psychic", 
            "", 
            "Genetic Pokémon", 
            2.0f, 
            122.0f, 
            "Pressure", 
            1, 
            true
        );
        
        // Verificar que el método toJson genera un JSON válido
        String json = pokemon.toJson();
        
        // Verificar que el JSON contiene las propiedades esperadas
        assertTrue(json.contains("\"name\":\"Mewtwo\""));
        assertTrue(json.contains("\"pokedexNumber\":150"));
        assertTrue(json.contains("\"type1\":\"Psychic\""));
        assertTrue(json.contains("\"legendary\":true"));
    }
    
    @Test
    public void testPokemonWithSpecialCharacters() {
        // Crear un Pokémon con caracteres especiales para probar el escape de caracteres
        Pokemon pokemon = new Pokemon(
            "Mr. Mime", 
            122, 
            "Psychic", 
            "Fairy", 
            "Barrier Pokémon", 
            1.3f, 
            54.5f, 
            "Soundproof, Filter", 
            1, 
            false
        );
        
        // Verificar que no hay problemas con los caracteres especiales
        assertEquals("Mr. Mime", pokemon.getName());
        assertEquals("Barrier Pokémon", pokemon.getClassification());
        assertEquals("Soundproof, Filter", pokemon.getAbilities());
        
        // Verificar que el JSON se genera correctamente
        String json = pokemon.toJson();
        assertTrue(json.contains("\"name\":\"Mr. Mime\""));
        assertTrue(json.contains("\"abilities\":\"Soundproof, Filter\""));
    }
}