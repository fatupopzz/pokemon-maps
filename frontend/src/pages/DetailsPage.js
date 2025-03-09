import React, { useContext } from 'react';
import { useParams, Navigate } from 'react-router-dom';
import { PokemonContext } from '../context/PokemonContext';
import PokemonDetails from '../components/PokemonDetails';

/**
 * Página que muestra los detalles de un Pokémon específico
 */
const DetailsPage = () => {
  const { name } = useParams();
  const { allPokemons, loading } = useContext(PokemonContext);
  
  // Si estamos cargando, mostrar indicador
  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="text-center">
          <div className="w-16 h-16 border-4 border-blue-500 border-t-transparent rounded-full animate-spin mx-auto mb-4"></div>
          <p className="text-lg text-gray-600">Cargando información del Pokémon...</p>
        </div>
      </div>
    );
  }
  
  // Si no hay datos o el Pokémon no existe, redirigir a la página principal
  if (!allPokemons || !allPokemons[name]) {
    return <Navigate to="/" replace />;
  }
  
  return (
    <div>
      <PokemonDetails pokemon={allPokemons[name]} />
    </div>
  );
};

export default DetailsPage;