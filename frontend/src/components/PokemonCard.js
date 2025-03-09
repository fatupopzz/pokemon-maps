import React from 'react';
import { Link } from 'react-router-dom';

/**
 * Componente para mostrar una tarjeta de Pokémon con estilo Frutiger Aero
 */
const PokemonCard = ({ pokemon }) => {
  // Mapa de colores para cada tipo de Pokémon
  const typeColors = {
    "Fire": "bg-red-100 text-red-800",
    "Water": "bg-blue-100 text-blue-800",
    "Grass": "bg-green-100 text-green-800",
    "Electric": "bg-yellow-100 text-yellow-800",
    "Psychic": "bg-purple-100 text-purple-800",
    "Fighting": "bg-orange-100 text-orange-800",
    "Normal": "bg-gray-100 text-gray-800",
    "Ice": "bg-cyan-100 text-cyan-800",
    "Poison": "bg-violet-100 text-violet-800",
    "Ground": "bg-amber-100 text-amber-800",
    "Flying": "bg-sky-100 text-sky-800",
    "Bug": "bg-lime-100 text-lime-800",
    "Rock": "bg-stone-100 text-stone-800",
    "Ghost": "bg-indigo-100 text-indigo-800",
    "Dragon": "bg-teal-100 text-teal-800",
    "Dark": "bg-neutral-100 text-neutral-800",
    "Steel": "bg-zinc-100 text-zinc-800",
    "Fairy": "bg-pink-100 text-pink-800"
  };
  
  return (
    <Link to={`/pokemon/${pokemon.name}`} className="block">
      <div className="relative bg-blue-50 rounded-xl p-4 shadow-md backdrop-blur-md overflow-hidden group cursor-pointer hover:shadow-lg transition-shadow h-full">
        {/* Efectos de fondo con estilo Frutiger Aero */}
        <div className="absolute -top-20 -right-20 w-40 h-40 bg-blue-200 rounded-full opacity-30 blur-xl" />
        <div className="absolute -bottom-10 -left-10 w-32 h-32 bg-purple-200 rounded-full opacity-30 blur-xl" />
        
        <div className="relative z-10">
          <h2 className="text-lg font-bold text-blue-900">{pokemon.name}</h2>
          <div className="flex space-x-2 my-2">
            <span className={`px-2 py-1 rounded-full text-xs ${typeColors[pokemon.type1] || "bg-gray-100 text-gray-800"}`}>
              {pokemon.type1}
            </span>
            {pokemon.type2 && pokemon.type2 !== "" && (
              <span className={`px-2 py-1 rounded-full text-xs ${typeColors[pokemon.type2] || "bg-gray-100 text-gray-800"}`}>
                {pokemon.type2}
              </span>
            )}
          </div>
          
          <div className="mt-2">
            <div className="grid grid-cols-3 gap-1 text-xs text-gray-600">
              <div>HP: {pokemon.hp}</div>
              <div>Atq: {pokemon.attack}</div>
              <div>Def: {pokemon.defense}</div>
            </div>
            <div className="mt-2 text-sm">
              <span className="font-medium">Habilidad:</span> {pokemon.ability}
            </div>
          </div>
          
          {pokemon.legendary && (
            <div className="absolute top-3 right-3">
              <span className="text-yellow-500">★</span>
            </div>
          )}
        </div>
        
        {/* Efecto de hover */}
        <div className="absolute inset-0 bg-gradient-to-br from-blue-200 to-purple-200 opacity-0 group-hover:opacity-10 transition-opacity duration-300" />
      </div>
    </Link>
  );
};

export default PokemonCard;