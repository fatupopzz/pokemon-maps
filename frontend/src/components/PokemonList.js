import React from 'react';
import PokemonCard from './PokemonCard';
import { getPokemonsByType } from '../services/PokemonService';

/**
 * Componente para mostrar una lista de Pokémon agrupados por tipo
 */
const PokemonList = ({ pokemons }) => {
  if (!pokemons || pokemons.length === 0) {
    return (
      <div className="text-center py-10 bg-white bg-opacity-70 rounded-xl shadow-md">
        <p className="text-gray-500">No se encontraron Pokémon que coincidan con los criterios.</p>
      </div>
    );
  }
  
  // Agrupar Pokémon por tipo
  const pokemonsByType = getPokemonsByType(pokemons);
  
  // Orden de tipos (puedes personalizarlo)
  const typeOrder = [
    "Normal", "Fire", "Water", "Electric", "Grass", "Ice", "Fighting", "Poison", 
    "Ground", "Flying", "Psychic", "Bug", "Rock", "Ghost", "Dragon", "Dark", 
    "Steel", "Fairy"
  ];
  
  // Ordenar las claves de tipos según el orden establecido
  const orderedTypes = Object.keys(pokemonsByType).sort((a, b) => {
    const indexA = typeOrder.indexOf(a);
    const indexB = typeOrder.indexOf(b);
    
    // Si no está en la lista, ponerlo al final
    if (indexA === -1) return 1;
    if (indexB === -1) return -1;
    
    return indexA - indexB;
  });
  
  return (
    <div className="space-y-8">
      {orderedTypes.map((type) => (
        <div key={type} className="bg-white bg-opacity-70 rounded-xl p-4 shadow-md backdrop-blur-sm">
          <h3 className="text-lg font-semibold text-blue-800 mb-3 border-b border-blue-100 pb-2">
            Tipo: {type} ({pokemonsByType[type].length})
          </h3>
          
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
            {pokemonsByType[type].map((pokemon) => (
              <PokemonCard key={pokemon.name} pokemon={pokemon} />
            ))}
          </div>
        </div>
      ))}
    </div>
  );
};

export default PokemonList;