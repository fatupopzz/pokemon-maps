package com.pokemon.service;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pokemon.model.Pokemon;

/**
 * Pruebas para la clase ColeccionUsuario
 * Autor: Fatima 
 */
public class ColeccionUsuarioTest {
    
    private MapaPokemons mapaReferencia;
    private ColeccionUsuario coleccionUsuario;
    
    @BeforeEach
    public void setUp() {
        // Crear un mapa de referencia con algunos Pokémon
        mapaReferencia = new HashMapPokemon();
        
        // Agregar varios Pokémon al mapa para las pruebas
        mapaReferencia.agregarPokemon(new Pokemon(
            "Pikachu", 
            25, 
            "Electric", 
            "", 
            "Mouse Pokémon", 
            0.4f, 
            6.0f, 
            "Static, Lightning-rod", 
            1, 
            false
        ));
        
        mapaReferencia.agregarPokemon(new Pokemon(
            "Charizard", 
            6, 
            "Fire", 
            "Flying", 
            "Flame Pokémon", 
            1.7f, 
            90.5f, 
            "Blaze, Solar-power", 
            1, 
            false
        ));
        
        mapaReferencia.agregarPokemon(new Pokemon(
            "Bulbasaur", 
            1, 
            "Grass", 
            "Poison", 
            "Seed Pokémon", 
            0.7f, 
            6.9f, 
            "Overgrow, Chlorophyll", 
            1, 
            false
        ));
        
        mapaReferencia.agregarPokemon(new Pokemon(
            "Gyarados", 
            130, 
            "Water", 
            "Flying", 
            "Atrocious Pokémon", 
            6.5f, 
            235.0f, 
            "Intimidate, Moxie", 
            1, 
            false
        ));
        
        mapaReferencia.agregarPokemon(new Pokemon(
            "Mewtwo", 
            150, 
            "Psychic", 
            "", 
            "Genetic Pokémon", 
            2.0f, 
            122.0f, 
            "Pressure, Unnerve", 
            1, 
            true
        ));
        
        mapaReferencia.agregarPokemon(new Pokemon(
            "Eevee", 
            133, 
            "Normal", 
            "", 
            "Evolution Pokémon", 
            0.3f, 
            6.5f, 
            "Run-away, Adaptability, Anticipation", 
            1, 
            false
        ));
        
        mapaReferencia.agregarPokemon(new Pokemon(
            "Gengar", 
            94, 
            "Ghost", 
            "Poison", 
            "Shadow Pokémon", 
            1.5f, 
            40.5f, 
            "Cursed-body", 
            1, 
            false
        ));
        
        mapaReferencia.agregarPokemon(new Pokemon(
            "Dragonite", 
            149, 
            "Dragon", 
            "Flying", 
            "Dragon Pokémon", 
            2.2f, 
            210.0f, 
            "Inner-focus, Multiscale", 
            1, 
            false
        ));
        
        // Crear la colección del usuario con el mapa de referencia
        coleccionUsuario = new ColeccionUsuario(mapaReferencia);
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
    public void testEliminarPokemon() {
        // Agregar algunos Pokémon
        coleccionUsuario.agregarPokemon("Pikachu");
        coleccionUsuario.agregarPokemon("Charizard");
        coleccionUsuario.agregarPokemon("Mewtwo");
        assertEquals(3, coleccionUsuario.cantidadPokemon());
        
        // Eliminar un Pokémon existente
        boolean resultado = coleccionUsuario.eliminarPokemon("Charizard");
        assertTrue(resultado);
        assertEquals(2, coleccionUsuario.cantidadPokemon());
        assertFalse(coleccionUsuario.obtenerNombresPokemon().contains("Charizard"));
        
        // Intentar eliminar un Pokémon que no está en la colección
        resultado = coleccionUsuario.eliminarPokemon("Charizard");
        assertFalse(resultado);
        assertEquals(2, coleccionUsuario.cantidadPokemon());
        
        // Intentar eliminar un Pokémon que no existe en el mapa de referencia
        resultado = coleccionUsuario.eliminarPokemon("Alakazam");
        assertFalse(resultado);
        assertEquals(2, coleccionUsuario.cantidadPokemon());
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
                assertTrue(pokemon.getAbilities().contains("Static"));
            } else if (pokemon.getName().equals("Gyarados")) {
                encontroGyarados = true;
                assertEquals("Water", pokemon.getType1());
                assertEquals("Flying", pokemon.getType2());
                assertTrue(pokemon.getAbilities().contains("Intimidate"));
            } else if (pokemon.getName().equals("Mewtwo")) {
                encontroMewtwo = true;
                assertEquals("Psychic", pokemon.getType1());
                assertTrue(pokemon.isLegendary());
                assertTrue(pokemon.getAbilities().contains("Pressure"));
            }
        }
        
        assertTrue(encontroPikachu);
        assertTrue(encontroGyarados);
        assertTrue(encontroMewtwo);
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
        coleccionUsuario.agregarPokemon("Gengar");     // Ghost/Poison
        coleccionUsuario.agregarPokemon("Dragonite");  // Dragon/Flying
        
        // Obtener ordenados por tipo
        List<Pokemon> ordenados = coleccionUsuario.obtenerPokemonsPorTipo();
        
        // Verificar que están ordenados correctamente por tipo primario
        assertEquals(8, ordenados.size());
        
        // El orden debería ser: Dragon, Electric, Fire, Ghost, Grass, Normal, Psychic, Water
        assertEquals("Dragon", ordenados.get(0).getType1());   // Dragonite
        assertEquals("Electric", ordenados.get(1).getType1()); // Pikachu
        assertEquals("Fire", ordenados.get(2).getType1());     // Charizard
        assertEquals("Ghost", ordenados.get(3).getType1());    // Gengar
        assertEquals("Grass", ordenados.get(4).getType1());    // Bulbasaur
        assertEquals("Normal", ordenados.get(5).getType1());   // Eevee
        assertEquals("Psychic", ordenados.get(6).getType1());  // Mewtwo
        assertEquals("Water", ordenados.get(7).getType1());    // Gyarados
    }
    
    @Test
    public void testBuscarPorTipo() {
        // Agregar algunos Pokémon con tipos variados
        coleccionUsuario.agregarPokemon("Charizard");  // Fire/Flying
        coleccionUsuario.agregarPokemon("Bulbasaur");  // Grass/Poison
        coleccionUsuario.agregarPokemon("Gyarados");   // Water/Flying
        coleccionUsuario.agregarPokemon("Gengar");     // Ghost/Poison
        coleccionUsuario.agregarPokemon("Dragonite");  // Dragon/Flying
        
        // Buscar Pokémon por tipo primario
        List<Pokemon> fuego = coleccionUsuario.buscarPorTipo("Fire");
        assertEquals(1, fuego.size());
        assertEquals("Charizard", fuego.get(0).getName());
        
        // Buscar Pokémon por tipo secundario
        List<Pokemon> voladores = coleccionUsuario.buscarPorTipo("Flying");
        assertEquals(3, voladores.size());
        
        // Verificar que encontró todos los voladores (como primario o secundario)
        boolean tieneCharizard = false;
        boolean tieneGyarados = false;
        boolean tieneDragonite = false;
        
        for (Pokemon p : voladores) {
            if (p.getName().equals("Charizard")) tieneCharizard = true;
            if (p.getName().equals("Gyarados")) tieneGyarados = true;
            if (p.getName().equals("Dragonite")) tieneDragonite = true;
        }
        
        assertTrue(tieneCharizard);
        assertTrue(tieneGyarados);
        assertTrue(tieneDragonite);
        
        // Buscar Pokémon por un tipo que no existe en la colección
        List<Pokemon> hielo = coleccionUsuario.buscarPorTipo("Ice");
        assertEquals(0, hielo.size());
    }
    
    @Test
    public void testObtenerLegendarios() {
        // Agregar varios Pokémon, incluyendo algunos legendarios
        coleccionUsuario.agregarPokemon("Pikachu");   // No legendario
        coleccionUsuario.agregarPokemon("Charizard"); // No legendario
        coleccionUsuario.agregarPokemon("Mewtwo");    // Legendario
        
        // Obtener legendarios
        List<Pokemon> legendarios = coleccionUsuario.obtenerLegendarios();
        
        // Verificar que solo devuelve los legendarios
        assertEquals(1, legendarios.size());
        assertEquals("Mewtwo", legendarios.get(0).getName());
        assertTrue(legendarios.get(0).isLegendary());
    }
    
    @Test
    public void testObtenerEstadisticas() {
        // Agregar varios Pokémon de diferentes generaciones
        coleccionUsuario.agregarPokemon("Pikachu");   // Gen 1
        coleccionUsuario.agregarPokemon("Charizard"); // Gen 1
        coleccionUsuario.agregarPokemon("Mewtwo");    // Gen 1, Legendario
        
        // Obtener estadísticas
        String estadisticas = coleccionUsuario.obtenerEstadisticas();
        
        // Verificar que contiene la información básica correcta
        assertTrue(estadisticas.contains("Total de Pokémon: 3"));
        assertTrue(estadisticas.contains("Pokémon legendarios: 1"));
        assertTrue(estadisticas.contains("Gen 1: 3"));
    }
    
    @Test
    public void testColeccionVaciaEstadisticas() {
        // No agregar Pokémon
        String estadisticas = coleccionUsuario.obtenerEstadisticas();
        
        // Verificar que muestra mensaje para colección vacía
        assertEquals("No hay Pokémon en tu colección.", estadisticas);
    }
    
    @Test
    public void testObtenerPokemonsConMapaVacio() {
        // Crear un mapa de referencia vacío
        MapaPokemons mapaVacio = new HashMapPokemon();
        ColeccionUsuario coleccionVacia = new ColeccionUsuario(mapaVacio);
        
        // Intentar agregar un Pokémon que no existe en el mapa
        boolean resultado = coleccionVacia.agregarPokemon("Pikachu");
        assertFalse(resultado);
        
        // Verificar que la colección sigue vacía
        assertEquals(0, coleccionVacia.cantidadPokemon());
        assertTrue(coleccionVacia.obtenerPokemons().isEmpty());
    }
    
    @Test
    public void testCaseSensitive() {
        // Agregar un Pokémon con nombre específico
        coleccionUsuario.agregarPokemon("Pikachu");
        
        // Intentar buscar el mismo Pokémon con diferente caso
        assertFalse(coleccionUsuario.agregarPokemon("pikachu"));
        assertFalse(coleccionUsuario.agregarPokemon("PIKACHU"));
        
        // La colección solo debe tener un Pokémon
        assertEquals(1, coleccionUsuario.cantidadPokemon());
    }
}