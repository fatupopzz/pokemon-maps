import React, { useContext, useMemo, useState } from 'react';
import { PokemonContext } from '../context/PokemonContext';
import PokemonList from './PokemonList';
import SearchBar from './SearchBar';
import { searchPokemonsByName, searchPokemonsByAbility } from '../services/PokemonService';

/**
 * Componente para mostrar la colección de Pokémon del usuario
 */
const UserCollection = () => {
  const { userCollection, allPokemons } = useContext(PokemonContext);
  const [searchTerm, setSearchTerm] = useState('');
  const [abilityFilter, setAbilityFilter] = useState('');
  
  // Obtener los objetos Pokémon que tiene el usuario
  const userPokemons = useMemo(() => {
    if (!userCollection.length) return [];
    
    return userCollection
      .map(name => allPokemons[name])
      .filter(Boolean); // Filtrar nulos por si acaso
  }, [userCollection, allPokemons]);
  
  // Filtrar por nombre y habilidad
  const filteredPokemons = useMemo(() => {
    let result = userPokemons;
    
    if (searchTerm) {
      result = searchPokemonsByName(
        result.reduce((obj, pokemon) => {
          obj[pokemon.name] = pokemon;
          return obj;
        }, {}), 
        searchTerm
      );
    }
    
    if (abilityFilter) {
      result = searchPokemonsByAbility(
        result.reduce((obj, pokemon) => {
          obj[pokemon.name] = pokemon;
          return obj;
        }, {}), 
        abilityFilter
      );
    }
    
    return result;
  }, [userPokemons, searchTerm, abilityFilter]);
  
  // Si no hay pokémon en la colección
  if (!userCollection.length) {
    return (
      <div className="bg-white bg-opacity-80 rounded-xl p-8 shadow-lg text-center">
        <h2 className="text-xl font-semibold text-gray-700 mb-4">Tu colección está vacía</h2>
        <p className="text-gray-500 mb-6">
          Explora la lista de Pokémon y agrega algunos a tu colección.
        </p>
        <a 
          href="/"
          className="inline-block px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors"
        >
          Explorar Pokémon
        </a>
      </div>
    );
  }
  
  return (
    <div>
      <SearchBar 
        onSearch={setSearchTerm}
        onFilterByAbility={setAbilityFilter}
      />
      
      <PokemonList pokemons={filteredPokemons} />
    </div>
  );
};

export default UserCollection;