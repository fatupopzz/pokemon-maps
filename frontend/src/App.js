import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import CollectionPage from './pages/CollectionPage';
import DetailsPage from './pages/DetailsPage';
import NavBar from './components/NavBar';
import { PokemonContext } from './context/PokemonContext';
import { fetchAllPokemons } from './services/PokemonService';
import './styles/main.css';

function App() {
  // Estado para almacenar el tipo de Map seleccionado
  const [mapType, setMapType] = useState('HashMap');
  // Estado para todos los pokémon cargados
  const [allPokemons, setAllPokemons] = useState({});
  // Estado para la colección del usuario
  const [userCollection, setUserCollection] = useState([]);
  // Estado para controlar la carga
  const [loading, setLoading] = useState(true);
  // Estado para errores
  const [error, setError] = useState(null);

  // Efecto para cargar los datos de pokémon al iniciar o cambiar el tipo de Map
  useEffect(() => {
    const loadPokemonData = async () => {
      setLoading(true);
      try {
        const data = await fetchAllPokemons(mapType);
        setAllPokemons(data);
        setError(null);
      } catch (err) {
        console.error('Error loading Pokemon data:', err);
        setError('No se pudieron cargar los datos de Pokémon. Por favor, intenta de nuevo más tarde.');
      } finally {
        setLoading(false);
      }
    };

    loadPokemonData();
  }, [mapType]);

  // Función para agregar un pokémon a la colección del usuario
  const addToCollection = (pokemonName) => {
    if (!allPokemons[pokemonName]) {
      setError(`Error: El Pokémon "${pokemonName}" no existe en los datos.`);
      return false;
    }
    
    if (userCollection.includes(pokemonName)) {
      setError(`El Pokémon ${pokemonName} ya está en tu colección.`);
      return false;
    }
    
    setUserCollection(prev => [...prev, pokemonName]);
    setError(null);
    return true;
  };

  // Función para cambiar el tipo de Map
  const changeMapType = (type) => {
    setMapType(type);
  };

  // Valores del contexto para compartir en toda la aplicación
  const contextValue = {
    mapType,
    changeMapType,
    allPokemons,
    userCollection,
    addToCollection,
    loading,
    error,
    setError
  };

  return (
    <PokemonContext.Provider value={contextValue}>
      {/* Usamos basename={process.env.PUBLIC_URL} para que funcione con GitHub Pages */}
      <Router basename={process.env.PUBLIC_URL}>
        <div className="min-h-screen bg-gradient-to-br from-blue-50 to-purple-50">
          <NavBar />
          
          <main className="container mx-auto p-4">
            {error && (
              <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4 relative">
                {error}
                <button 
                  className="absolute top-0 right-0 p-2 font-bold"
                  onClick={() => setError(null)}
                >
                  ×
                </button>
              </div>
            )}
            
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/collection" element={<CollectionPage />} />
              <Route path="/pokemon/:name" element={<DetailsPage />} />
            </Routes>
          </main>
          
          <footer className="bg-blue-900 text-white p-4 text-center mt-auto">
            <p>Pokémon Explorer - Utilizando {mapType} para almacenar los datos</p>
          </footer>
        </div>
      </Router>
    </PokemonContext.Provider>
  );
}

export default App;