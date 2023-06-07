package Plants;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<PlantNames, Integer> plantesAchete = new HashMap<>();

    public void ajouterPlante(PlantNames plante) {
        int nombreActuel = plantesAchete.getOrDefault(plante, 0);
        plantesAchete.put(plante, nombreActuel + 1);
    }

    public int getNombrePlantes(PlantNames plante) {
        return plantesAchete.getOrDefault(plante, 0);
    }

    public void removePlante(PlantNames plante) {
        int nombreActuel = plantesAchete.getOrDefault(plante, 0);
        if (nombreActuel > 0) {
            plantesAchete.put(plante, nombreActuel - 1);
        }
    }

    public Map<PlantNames, Integer> getPlantesAchete() {
        return plantesAchete;
    }
}
