package com.pokemon.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que representa una respuesta API para Pokémon.
 * Se utiliza para enviar respuestas formateadas a las solicitudes del frontend.
 */
public class PokemonResponse {
    private boolean success;
    private String message;
    private Object data;
    private Map<String, Object> metadata;
    
    /**
     * Constructor por defecto
     */
    public PokemonResponse() {
        this.success = true;
        this.message = "";
        this.data = null;
        this.metadata = new HashMap<>();
    }
    
    /**
     * Constructor con mensaje de éxito
     */
    public PokemonResponse(String message) {
        this.success = true;
        this.message = message;
        this.data = null;
        this.metadata = new HashMap<>();
    }
    
    /**
     * Constructor con mensaje y datos
     */
    public PokemonResponse(String message, Object data) {
        this.success = true;
        this.message = message;
        this.data = data;
        this.metadata = new HashMap<>();
    }
    
    /**
     * Constructor completo
     */
    public PokemonResponse(boolean success, String message, Object data, Map<String, Object> metadata) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.metadata = metadata != null ? metadata : new HashMap<>();
    }
    
    /**
     * Crea una respuesta de éxito
     */
    public static PokemonResponse success(String message) {
        return new PokemonResponse(true, message, null, null);
    }
    
    /**
     * Crea una respuesta de éxito con datos
     */
    public static PokemonResponse success(String message, Object data) {
        return new PokemonResponse(true, message, data, null);
    }
    
    /**
     * Crea una respuesta de éxito con datos y metadatos
     */
    public static PokemonResponse success(String message, Object data, Map<String, Object> metadata) {
        return new PokemonResponse(true, message, data, metadata);
    }
    
    /**
     * Crea una respuesta de error
     */
    public static PokemonResponse error(String message) {
        return new PokemonResponse(false, message, null, null);
    }
    
    /**
     * Crea una respuesta de error con detalles
     */
    public static PokemonResponse error(String message, Map<String, Object> details) {
        return new PokemonResponse(false, message, null, details);
    }
    
    /**
     * Convierte un mapa de Pokémon a una lista para la respuesta API
     */
    public static List<Map<String, Object>> convertPokemonMapToList(Map<String, Pokemon> pokemonMap) {
        List<Map<String, Object>> pokemonList = new ArrayList<>();
        
        for (Pokemon pokemon : pokemonMap.values()) {
            Map<String, Object> pokemonData = new HashMap<>();
            pokemonData.put("name", pokemon.getName());
            pokemonData.put("pokedexNumber", pokemon.getPokedexNumber());
            pokemonData.put("type1", pokemon.getType1());
            pokemonData.put("type2", pokemon.getType2());
            pokemonData.put("classification", pokemon.getClassification());
            pokemonData.put("height", pokemon.getHeight());
            pokemonData.put("weight", pokemon.getWeight());
            pokemonData.put("abilities", pokemon.getAbilities());
            pokemonData.put("generation", pokemon.getGeneration());
            pokemonData.put("legendary", pokemon.isLegendary());
            
            pokemonList.add(pokemonData);
        }
        
        return pokemonList;
    }
    
    /**
     * Convierte un Pokémon a un mapa para la respuesta API
     */
    public static Map<String, Object> convertPokemonToMap(Pokemon pokemon) {
        Map<String, Object> pokemonData = new HashMap<>();
        pokemonData.put("name", pokemon.getName());
        pokemonData.put("pokedexNumber", pokemon.getPokedexNumber());
        pokemonData.put("type1", pokemon.getType1());
        pokemonData.put("type2", pokemon.getType2());
        pokemonData.put("classification", pokemon.getClassification());
        pokemonData.put("height", pokemon.getHeight());
        pokemonData.put("weight", pokemon.getWeight());
        pokemonData.put("abilities", pokemon.getAbilities());
        pokemonData.put("generation", pokemon.getGeneration());
        pokemonData.put("legendary", pokemon.isLegendary());
        
        return pokemonData;
    }
    
    /**
     * Convierte esta respuesta a formato JSON
     */
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"success\": ").append(success).append(",");
        sb.append("\"message\": \"").append(escapeJson(message)).append("\",");
        
        // Serializar datos
        sb.append("\"data\": ");
        if (data == null) {
            sb.append("null");
        } else if (data instanceof List) {
            serializeList(sb, (List<?>) data);
        } else if (data instanceof Map) {
            serializeMap(sb, (Map<?, ?>) data);
        } else if (data instanceof Pokemon) {
            sb.append(((Pokemon) data).toJson());
        } else {
            sb.append("\"").append(escapeJson(data.toString())).append("\"");
        }
        
        // Serializar metadatos
        sb.append(",");
        sb.append("\"metadata\": ");
        serializeMap(sb, metadata);
        
        sb.append("}");
        return sb.toString();
    }
    
    // Getters y setters
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
    
    public void addMetadata(String key, Object value) {
        this.metadata.put(key, value);
    }
    
    // Métodos auxiliares para serialización
    private void serializeList(StringBuilder sb, List<?> list) {
        sb.append("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            Object item = list.get(i);
            if (item == null) {
                sb.append("null");
            } else if (item instanceof Number) {
                sb.append(item);
            } else if (item instanceof Boolean) {
                sb.append(item);
            } else if (item instanceof Map) {
                serializeMap(sb, (Map<?, ?>) item);
            } else if (item instanceof List) {
                serializeList(sb, (List<?>) item);
            } else if (item instanceof Pokemon) {
                sb.append(((Pokemon) item).toJson());
            } else {
                sb.append("\"").append(escapeJson(item.toString())).append("\"");
            }
        }
        sb.append("]");
    }
    
    private void serializeMap(StringBuilder sb, Map<?, ?> map) {
        sb.append("{");
        boolean first = true;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!first) {
                sb.append(",");
            }
            first = false;
            
            sb.append("\"").append(escapeJson(entry.getKey().toString())).append("\":");
            
            Object value = entry.getValue();
            if (value == null) {
                sb.append("null");
            } else if (value instanceof Number) {
                sb.append(value);
            } else if (value instanceof Boolean) {
                sb.append(value);
            } else if (value instanceof Map) {
                serializeMap(sb, (Map<?, ?>) value);
            } else if (value instanceof List) {
                serializeList(sb, (List<?>) value);
            } else if (value instanceof Pokemon) {
                sb.append(((Pokemon) value).toJson());
            } else {
                sb.append("\"").append(escapeJson(value.toString())).append("\"");
            }
        }
        sb.append("}");
    }
    
    private String escapeJson(String text) {
        if (text == null) {
            return "";
        }
        
        StringBuilder escaped = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            switch (c) {
                case '"':
                    escaped.append("\\\"");
                    break;
                case '\\':
                    escaped.append("\\\\");
                    break;
                case '/':
                    escaped.append("\\/");
                    break;
                case '\b':
                    escaped.append("\\b");
                    break;
                case '\f':
                    escaped.append("\\f");
                    break;
                case '\n':
                    escaped.append("\\n");
                    break;
                case '\r':
                    escaped.append("\\r");
                    break;
                case '\t':
                    escaped.append("\\t");
                    break;
                default:
                    escaped.append(c);
            }
        }
        
        return escaped.toString();
    }
}