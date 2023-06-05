import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class ModelSaver {
    public static void saveModel(Model model, String filePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(model);
            objectOut.close();
            fileOut.close();
            System.out.println("Le modèle a été enregistré avec succès dans le fichier : " + filePath);
        } catch (Exception e) {
            System.out.println("Une erreur s'est produite lors de l'enregistrement du modèle : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
