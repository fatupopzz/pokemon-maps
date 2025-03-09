import Papa from 'papaparse';

/**
 * Esta función carga los datos de Pokémon desde el CSV
 * @param {string} mapType - Tipo de mapa (HashMap, TreeMap, LinkedHashMap)
 * @returns {Promise<Object>} - Un objeto con los datos de Pokémon
 */
export const fetchAllPokemons = async (mapType) => {
  try {
    // En un caso real, haríamos una llamada a la API Java que hemos creado
    // Aquí simularemos cargar el CSV directamente (en producción se manejaría desde el backend)
    const csvUrl = process.env.PUBLIC_URL + '/data/pokemon_data_pokeapi.csv';
    
    const response = await fetch(csvUrl);
    if (!response.ok) {
      throw new Error(`No se pudo cargar el archivo: ${response.statusText}`);
    }
    
    const csvText = await response.text();
    
    // Convertir el CSV a un objeto según el tipo de Map
    return parseCSVToMap(csvText, mapType);
  } catch (error) {
    console.error('Error en fetchAllPokemons:', error);
    throw error;
  }
};

/**
 * Convierte los datos CSV a un objeto según el tipo de Map seleccionado
 * @param {string} csvText - Texto CSV
 * @param {string} mapType - Tipo de Map (HashMap, TreeMap, LinkedHashMap)
 * @returns {Object} - Map de Pokémon
 */
const parseCSVToMap = (csvText, mapType) => {
  return new Promise((resolve, reject) => {
    Papa.parse(csvText, {
      header: true,
      dynamicTyping: true,
      skipEmptyLines: true,
      complete: (results) => {
        try {
          // Transformar los datos según el tipo de Map
          const pokemonMap = createPokemonMap(results.data, mapType);
          resolve(pokemonMap);
        } catch (error) {
          reject(error);
        }
      },
      error: (error) => {
        reject(error);
      }
    });
  });
};

/**
 * Crea un Map de Pokémon según el tipo especificado
 * @param {Array} pokemonArray - Array de datos de Pokémon
 * @param {string} mapType - Tipo de Map a crear
 * @returns {Object} - Map de Pokémon
 */
const createPokemonMap = (pokemonArray, mapType) => {
  let pokemonMap = {};
  
  switch(mapType) {
    case "HashMap":
      // Un objeto normal en JavaScript (no garantiza orden)
      pokemonMap = pokemonArray.reduce((map, pokemon) => {
        map[pokemon.Name] = transformPokemonData(pokemon);
        return map;
      }, {});
      break;
      
    case "TreeMap":
      // Simulamos TreeMap ordenando las claves alfabéticamente
      const sorted = [...pokemonArray].sort((a, b) => 
        a.Name.localeCompare(b.Name)
      );
      
      pokemonMap = sorted.reduce((map, pokemon) => {
        map[pokemon.Name] = transformPokemonData(pokemon);
        return map;
      }, {});
      break;
      
    case "LinkedHashMap":
      // Simulamos LinkedHashMap manteniendo el orden de inserción
      pokemonMap = pokemonArray.reduce((map, pokemon) => {
        map[pokemon.Name] = transformPokemonData(pokemon);
        return map;
      }, {});
      break;
      
    default:
      throw new Error(`Tipo de mapa no válido: ${mapType}`);
  }
  
  return pokemonMap;
};

/**
 * Transforma los datos crudos del Pokémon al formato deseado
 * @param {Object} rawPokemon - Datos crudos del Pokémon desde el CSV
 * @returns {Object} - Datos del Pokémon transformados
 */
const transformPokemonData = (rawPokemon) => {
  return {
    name: rawPokemon.Name,
    type1: rawPokemon.Type1,
    type2: rawPokemon.Type2 || "",
    total: rawPokemon.Total,
    hp: rawPokemon.HP,
    attack: rawPokemon.Attack,
    defense: rawPokemon.Defense,
    spAtk: rawPokemon["Sp. Atk"],
    spDef: rawPokemon["Sp. Def"],
    speed: rawPokemon.Speed,
    generation: rawPokemon.Generation,
    legendary: rawPokemon.Legendary === "True" || rawPokemon.Legendary === true,
    ability: rawPokemon.Ability || "Unknown"
  };
};

/**
 * Filtra los Pokémon por nombre
 * @param {Object} pokemons - Map de Pokémon
 * @param {string} searchTerm - Término de búsqueda
 * @returns {Array} - Array de Pokémon filtrados
 */
export const searchPokemonsByName = (pokemons, searchTerm) => {
  if (!searchTerm) return Object.values(pokemons);
  
  return Object.values(pokemons).filter(pokemon => 
    pokemon.name.toLowerCase().includes(searchTerm.toLowerCase())
  );
};

/**
 * Filtra los Pokémon por habilidad
 * @param {Object} pokemons - Map de Pokémon
 * @param {string} ability - Habilidad a buscar
 * @returns {Array} - Array de Pokémon filtrados
 */
export const searchPokemonsByAbility = (pokemons, ability) => {
  if (!ability) return Object.values(pokemons);
  
  return Object.values(pokemons).filter(pokemon => 
    pokemon.ability.toLowerCase().includes(ability.toLowerCase())
  );
};

/**
 * Ordena los Pokémon por tipo
 * @param {Array} pokemons - Array de Pokémon
 * @returns {Object} - Pokémon agrupados por tipo
 */
export const getPokemonsByType = (pokemons) => {
  return pokemons.reduce((groups, pokemon) => {
    const type = pokemon.type1;
    if (!groups[type]) {
      groups[type] = [];
    }
    groups[type].push(pokemon);
    return groups;
  }, {});
};