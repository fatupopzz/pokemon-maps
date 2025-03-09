# Pokémon Explorer

Este proyecto implementa un explorador de datos de Pokémon utilizando el concepto de Maps del Java Collection Framework adaptado a un entorno web con React.

![image](https://github.com/user-attachments/assets/f60632a5-91cf-4089-9f15-0af6c3b3a65d)

## Descripción del Proyecto

El objetivo principal de este proyecto es demostrar la implementación y uso de diferentes tipos de Maps (HashMap, TreeMap y LinkedHashMap) para almacenar y manipular datos de Pokémon. La aplicación permite visualizar, buscar y filtrar información de Pokémon de manera eficiente.

### Relación con la Asignación Original

Este proyecto cumple con los requisitos de la Hoja de Trabajo No. 6 de Algoritmos y Estructura de Datos (CC2016), que solicita:

1. **Implementación de Maps**: Se implementaron las tres variantes de Map solicitadas:
   - **HashMap**: Acceso rápido O(1), sin orden específico
   - **TreeMap**: Elementos ordenados por nombre O(log n)
   - **LinkedHashMap**: Mantiene el orden de inserción O(1)

2. **Patrón Factory**: Utilizado para seleccionar la implementación de Map en tiempo de ejecución.

3. **Operaciones solicitadas**:
   - Agregar un Pokémon a la colección del usuario
   - Mostrar datos de un Pokémon específico
   - Mostrar la colección del usuario ordenada por tipo
   - Mostrar todos los Pokémon ordenados por tipo
   - Buscar Pokémon por habilidad

4. **Ampliación web**: Se extendió el proyecto creando una interfaz web moderna que permite visualizar los datos de manera intuitiva.

## Estructura del Proyecto

```
pokemon-explorer/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   └── java/com/pokemon/
│   │   │       ├── model/
│   │   │       ├── service/
│   │   │       └── util/
│   │   └── test/
│   └── data/
│       └── pokemon_data_pokeapi.csv
├── frontend/
│   ├── public/
│   │   └── data/
│   │       └── pokemon_data_pokeapi.csv
│   └── src/
│       ├── components/
│       ├── pages/
│       ├── services/
│       ├── context/
│       ├── styles/
│       └── assets/
└── README.md
```

## Tecnologías Utilizadas

- **Backend**: Java (Collection Framework)
- **Frontend**: React, React Router, TailwindCSS
- **Datos**: Archivo CSV de Pokémon
- **Despliegue**: GitHub Pages

## Implementación de Maps

### HashMap
- **Características**: Acceso rápido O(1) en promedio para operaciones básicas, sin orden específico
- **Uso en el proyecto**: Utilizado como opción por defecto para almacenar los datos de Pokémon
- **Ventajas**: Mayor eficiencia para búsquedas por nombre

### TreeMap
- **Características**: Mantiene los elementos ordenados por clave (nombre del Pokémon) con operaciones en O(log n)
- **Uso en el proyecto**: Ofrece una vista ordenada alfabéticamente de los Pokémon
- **Ventajas**: Facilita la visualización ordenada y las búsquedas por rango

### LinkedHashMap
- **Características**: Mantiene el orden de inserción con operaciones rápidas O(1) en promedio
- **Uso en el proyecto**: Preserva el orden en que los Pokémon fueron leídos del archivo CSV
- **Ventajas**: Ideal para mantener un orden predecible en la interfaz

## Patrón Factory

Se implementó el patrón Factory para permitir la selección de diferentes implementaciones de Map en tiempo de ejecución:

```java
public class PokemonMapFactory {
    public static MapaPokemons crearMapa(int tipo) {
        switch(tipo) {
            case 1: return new HashMapPokemon();
            case 2: return new TreeMapPokemon();
            case 3: return new LinkedHashMapPokemon();
            default: throw new IllegalArgumentException("Tipo de mapa no válido");
        }
    }
}
```

## Cálculo de Complejidad

Para la operación #4 (mostrar todos los Pokémon ordenados por tipo):

- **Con HashMap**: O(n log n) debido a la necesidad de ordenar los elementos
- **Con TreeMap**: O(n log n) para ordenar por tipo (diferente a la clave original)
- **Con LinkedHashMap**: O(n log n) también requiere ordenamiento adicional

La operación más eficiente es la búsqueda por nombre, que es O(1) con HashMap, O(log n) con TreeMap y O(1) con LinkedHashMap.

## Demo en vivo

[En githubpages ](https://fatupopzz.github.io/pokemon-maps)

## Instalación y Ejecución Local

### Prerequisitos
- Java 11 o superior
- Node.js (v14+)
- npm o yarn

### Backend
```bash
cd backend
mvn clean package
java -jar target/pokemon-explorer-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Frontend
```bash
cd frontend
npm install
npm start
```

## Pruebas Unitarias

Se implementaron pruebas unitarias para verificar el correcto funcionamiento de las operaciones principales:

1. **Prueba de agregación de Pokémon a la colección**
2. **Prueba de búsqueda de Pokémon por nombre**




## Licencia

Este proyecto está bajo la Licencia MIT.
