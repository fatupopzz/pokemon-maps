package com.pokemon.service;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pokemon.model.Pokemon;

/**
 * Pruebas para la colección del usuario
 * Autor: [Tu Nombre]
 */
public class ColeccionUsuarioTest {
    
    private MapaPokemons mapaPokemons;
    private ColeccionUsuario coleccionUsuario;
    
    @BeforeEach
    public void setUp() {
        // Crear un mapa con algunos Pokémon
        mapaPokemons = new HashMapPokemon();
        
        // Agregar varios Pokémon al mapa para las pruebas
        mapaPokemons.agregarPokemon(new Pokemon(
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
        ));
        
        mapaPokemons.agregarPokemon(new Pokemon(
            "Charizard", 
            6, 
            "Fire", 
            "Flying", 
            "Flame Pokémon", 
            1.7f, 
            90.5f, 
            "Blaze", 
            1, 
            false
        ));
        
        mapaPokemons.agregarPokemon(new Pokemon(
            "Bulbasaur", 
            1, 
            "Grass", 
            "Poison", 
            "Seed Pokémon", 
            0.7f, 
            6.9f, 
            "Overgrow", 
            1, 
            false
        ));
        
        mapaPokemons.agregarPokemon(new Pokemon(
            "Gyarados", 
            130, 
            "Water", 
            "Flying", 
            "Atrocious Pokémon", 
            6.5f, 
            235.0f, 
            "Intimidate", 
            1, 
            false
        ));
        
        mapaPokemons.agregarPokemon(new Pokemon(
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
        ));
        
        mapaPokemons.agregarPokemon(new Pokemon(
            "Eevee", 
            133, 
            "Normal", 
            "", 
            "Evolution Pokémon", 
            0.3f, 
            6.5f, 
            "Adaptability", 
            1, 
            false
        ));
        
        // Crear la colección del usuario
        coleccionUsuario = new ColeccionUsuario(mapaPokemons);
    }
    
    @Test
    public void testColeccionInicialmenteVacia() {
        // Verificar que inicialmente la colección está vacía
        assertEquals(0, coleccionUsuario.cantidadPokemon());
        assertTrue(coleccionUsuario.obtenerNombresPokemon().isEmpty());
        assertTrue(coleccionUsuario.obtenerPokemons().isEmpty());
    }
    
    @Test
    public void testAgregarPokemon() {
        // Verificar que inicialmente la colección está vacía
        assertEquals(0, coleccionUsuario.cantidadPokemon());
        
        // Agregar un Pokémon a la colección
        boolean resultado = coleccionUsuario.agregarPokemon("Pikachu");
        
        // Verificar que se agregó correctamente
        assertTrue(resultado);
        assertEquals(1, coleccionUsuario.cantidadPokemon());
        
        // Intentar agregar el mismo Pokémon de nuevo (no debería permitirlo)
        resultado = coleccionUsuario.agregarPokemon("Pikachu");
        assertFalse(resultado);
        assertEquals(1, coleccionUsuario.cantidadPokemon());
        
        // Intentar agregar un Pokémon que no existe
        resultado = coleccionUsuario.agregarPokemon("Alakazam");
        assertFalse(resultado);
        assertEquals(1, coleccionUsuario.cantidadPokemon());
        
        // Agregar más Pokémon válidos
        assertTrue(coleccionUsuario.agregarPokemon("Charizard"));
        assertTrue(coleccionUsuario.agregarPokemon("Bulbasaur"));
        assertEquals(3, coleccionUsuario.cantidadPokemon());
    }
    
    @Test
    public void testAgregarPokemonCaseSensitive() {
        // Probar que la búsqueda es sensible a mayúsculas y minúsculas
        
        // Agregar "Pikachu" debería funcionar
        assertTrue(coleccionUsuario.agregarPokemon("Pikachu"));
        
        // Intentar agregar "pikachu" (en minúsculas) no debería funcionar
        // porque no existe en el mapa con ese nombre exacto
        assertFalse(coleccionUsuario.agregarPokemon("pikachu"));
        
        // La colección solo debería tener un Pokémon
        assertEquals(1, coleccionUsuario.cantidadPokemon());
    }
    
    @Test
    public void testObtenerNombresPokemon() {
        // Agregar algunos Pokémon a la colección
        coleccionUsuario.agregarPokemon("Pikachu");
        coleccionUsuario.agregarPokemon("Charizard");
        coleccionUsuario.agregarPokemon("Mewtwo");
        
        // Obtener los nombres
        Set<String> nombres = coleccionUsuario.obtenerNombresPokemon();
        
        // Verificar que contiene los nombres correctos
        assertEquals(3, nombres.size());
        assertTrue(nombres.contains("Pikachu"));
        assertTrue(nombres.contains("Charizard"));
        assertTrue(nombres.contains("Mewtwo"));
        assertFalse(nombres.contains("Bulbasaur"));
    }
    
    @Test
    public void testObtenerPokemons() {
        // Agregar algunos Pokémon a la colección
        coleccionUsuario.agregarPokemon("Pikachu");
        coleccionUsuario.agregarPokemon("Gyarados");
        coleccionUsuario.agregarPokemon("Mewtwo");
        
        // Obtener los objetos Pokémon
        List<Pokemon> pokemons = coleccionUsuario.obtenerPokemons();
        
        // Verificar que se obtienen los Pokémon correctos
        assertEquals(3, pokemons.size());
        
        // Verificar que se obtienen las instancias correctas
        boolean encontroPikachu = false;
        boolean encontroGyarados = false;
        boolean encontroMewtwo = false;
        
        for (Pokemon pokemon : pokemons) {
            if (pokemon.getName().equals("Pikachu")) {
                encontroPikachu = true;
                assertEquals("Electric", pokemon.getType1());
            } else if (pokemon.getName().equals("Gyarados")) {
                encontroGyarados = true;
                assertEquals("Water", pokemon.getType1());
                assertEquals("Flying", pokemon.getType2());
            } else if (pokemon.getName().equals("Mewtwo")) {
                encontroMewtwo = true;
                assertEquals("Psychic", pokemon.getType1());
                assertTrue(pokemon.isLegendary());
            }
        }
        
        assertTrue(encontroPikachu);
        assertTrue(encontroGyarados);
        assertTrue(encontroMewtwo);
    }
    
    @Test
    public void testObtenerPokemonsVacios() {
        // Sin agregar Pokémon, obtenerPokemons() debería devolver una lista vacía
        List<Pokemon> pokemons = coleccionUsuario.obtenerPokemons();
        assertNotNull(pokemons); // La lista no debería ser null
        assertEquals(0, pokemons.size()); // La lista debería estar vacía
    }
    
    @Test
    public void testObtenerPokemonsPorTipo() {
        // Agregar Pokémon de diferentes tipos
        coleccionUsuario.agregarPokemon("Bulbasaur");  // Grass/Poison
        coleccionUsuario.agregarPokemon("Charizard");  // Fire/Flying
        coleccionUsuario.agregarPokemon("Gyarados");   // Water/Flying
        coleccionUsuario.agregarPokemon("Pikachu");    // Electric
        coleccionUsuario.agregarPokemon("Mewtwo");     // Psychic
        coleccionUsuario.agregarPokemon("Eevee");      // Normal
        
        // Obtener ordenados por tipo
        List<Pokemon> ordenados = coleccionUsuario.obtenerPokemonsPorTipo();
        
        // Verificar que están ordenados correctamente por tipo primario
        assertEquals(6, ordenados.size());
        
        // El orden debería ser: Electric, Fire, Grass, Normal, Psychic, Water
        assertEquals("Electric", ordenados.get(0).getType1()); // Pikachu
        assertEquals("Fire", ordenados.get(1).getType1());     // Charizard
        assertEquals("Grass", ordenados.get(2).getType1());    // Bulbasaur
        assertEquals("Normal", ordenados.get(3).getType1());   // Eevee
        assertEquals("Psychic", ordenados.get(4).getType1());  // Mewtwo
        assertEquals("Water", ordenados.get(5).getType1());    // Gyarados
    }
    
    @Test
    public void testObtenerPokemonsPorTipoVacios() {
        // Sin agregar Pokémon, obtenerPokemonsPorTipo() debería devolver una lista vacía
        List<Pokemon> pokemons = coleccionUsuario.obtenerPokemonsPorTipo();
        assertNotNull(pokemons); // La lista no debería ser null
        assertEquals(0, pokemons.size()); // La lista debería estar vacía
    }
    
    @Test
    public void testToJson() {
        // Agregar algunos Pokémon a la colección
        coleccionUsuario.agregarPokemon("Pikachu");
        coleccionUsuario.agregarPokemon("Charizard");
        coleccionUsuario.agregarPokemon("Mewtwo");
        
        // Obtener el JSON
        String json = coleccionUsuario.toJson();
        
        // Verificar que el JSON contiene la información de los Pokémon
        assertTrue(json.contains("\"name\":\"Pikachu\""));
        assertTrue(json.contains("\"name\":\"Charizard\""));
        assertTrue(json.contains("\"name\":\"Mewtwo\""));
        assertTrue(json.contains("\"type1\":\"Electric\""));
        assertTrue(json.contains("\"type1\":\"Fire\""));
        assertTrue(json.contains("\"type1\":\"Psychic\""));
        assertTrue(json.contains("\"legendary\":true")); // Para Mewtwo
        assertTrue(json.startsWith("["));
        assertTrue(json.endsWith("]"));
    }
    
    @Test
    public void testToJsonVacio() {
        // Sin agregar Pokémon, toJson() debería devolver un arreglo vacío
        String json = coleccionUsuario.toJson();
        assertEquals("[]", json);
    }
    
    @Test
    public void testPokemonNoEncontrado() {
        // Agregar un Pokémon válido
        coleccionUsuario.agregarPokemon("Pikachu");
        
        // Modificar el mapa para simular que el Pokémon ya no existe
        // (esto probaría qué pasa si un Pokémon se elimina del mapa general)
        mapaPokemons = new HashMapPokemon(); // Crear un mapa vacío
        coleccionUsuario = new ColeccionUsuario(mapaPokemons);
        
        // Intentar agregar un Pokémon que no existe en el mapa
        boolean resultado = coleccionUsuario.agregarPokemon("Pikachu");
        assertFalse(resultado);
        
        // La colección no debería contener nada
        assertEquals(0, coleccionUsuario.cantidadPokemon());
        
        // Obtener Pokémons debería devolver una lista vacía
        assertEquals(0, coleccionUsuario.obtenerPokemons().size());
    }
}