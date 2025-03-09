import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { PokemonContext } from '../context/PokemonContext';

/**
 * Componente para mostrar los detalles de un Pokémon
 * (versión sin estadísticas)
 */
const PokemonDetails = ({ pokemon }) => {
  const navigate = useNavigate();
  const { addToCollection, userCollection } = useContext(PokemonContext);
  
  if (!pokemon) {
    return (
      <div className="text-center py-10 bg-white bg-opacity-70 rounded-xl shadow-md">
        <p className="text-gray-500">No se encontró información para este Pokémon.</p>
        <button 
          className="mt-4 px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
          onClick={() => navigate('/')}
        >
          Volver al inicio
        </button>
      </div>
    );
  }
  
  const alreadyInCollection = userCollection.includes(pokemon.name);
  
  // Tipos para mostrar (con validación adicional)
  const types = [
    pokemon.type1 && { name: pokemon.type1, main: true },
    pokemon.type2 && pokemon.type2 !== "" && { name: pokemon.type2, main: false }
  ].filter(Boolean);
  
  // Mapa de colores para cada tipo de Pokémon
  const typeColors = {
    "Fire": "bg-red-100 text-red-800 border-red-200",
    "Water": "bg-blue-100 text-blue-800 border-blue-200",
    "Grass": "bg-green-100 text-green-800 border-green-200",
    "Electric": "bg-yellow-100 text-yellow-800 border-yellow-200",
    "Psychic": "bg-purple-100 text-purple-800 border-purple-200",
    "Fighting": "bg-orange-100 text-orange-800 border-orange-200",
    "Normal": "bg-gray-100 text-gray-800 border-gray-200",
    "Ice": "bg-cyan-100 text-cyan-800 border-cyan-200",
    "Poison": "bg-violet-100 text-violet-800 border-violet-200",
    "Ground": "bg-amber-100 text-amber-800 border-amber-200",
    "Flying": "bg-sky-100 text-sky-800 border-sky-200",
    "Bug": "bg-lime-100 text-lime-800 border-lime-200",
    "Rock": "bg-stone-100 text-stone-800 border-stone-200",
    "Ghost": "bg-indigo-100 text-indigo-800 border-indigo-200",
    "Dragon": "bg-teal-100 text-teal-800 border-teal-200",
    "Dark": "bg-neutral-100 text-neutral-800 border-neutral-200",
    "Steel": "bg-zinc-100 text-zinc-800 border-zinc-200",
    "Fairy": "bg-pink-100 text-pink-800 border-pink-200"
  };
  
  return (
    <div className="bg-white bg-opacity-80 rounded-xl p-6 shadow-lg backdrop-blur-sm">
      <div className="flex justify-between items-start">
        <div>
          <h1 className="text-2xl font-bold text-blue-900">
            {pokemon.name}
            {pokemon.legendary && <span className="ml-2 text-yellow-500">★</span>}
          </h1>
          
          <div className="flex mt-2 space-x-2">
            {types.map(type => (
              <span 
                key={type.name}
                className={`px-3 py-1 rounded-full text-sm border ${typeColors[type.name] || "bg-gray-100 text-gray-800 border-gray-200"} ${type.main ? 'font-medium' : ''}`}
              >
                {type.name}
              </span>
            ))}
          </div>
        </div>
        
        <button 
          className="px-4 py-2 rounded-lg text-sm font-medium transition-colors"
          onClick={() => navigate(-1)}
        >
          Volver
        </button>
      </div>
      
      <div className="grid grid-cols-1 gap-6 mt-6">
        <div>
          <h2 className="text-lg font-semibold text-blue-800 mb-3">Información básica</h2>
          <div className="bg-blue-50 rounded-lg p-4 shadow-sm">
            <div className="grid grid-cols-2 gap-4">
              <div>
                <p className="text-sm text-gray-500">Generación</p>
                <p className="font-medium">{pokemon.generation}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Total</p>
                <p className="font-medium">{pokemon.total}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Legendario</p>
                <p className="font-medium">{pokemon.legendary ? "Sí" : "No"}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Habilidad</p>
                <p className="font-medium">{pokemon.ability}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      {!alreadyInCollection && (
        <div className="mt-6 flex justify-center">
          <button
            className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors shadow-md"
            onClick={() => addToCollection(pokemon.name)}
          >
            Agregar a mi colección
          </button>
        </div>
      )}
      
      {alreadyInCollection && (
        <div className="mt-6 text-center text-green-600 font-medium">
          Este Pokémon ya está en tu colección
        </div>
      )}
    </div>
  );
};

export default PokemonDetails;