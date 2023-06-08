import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ModelLoader {
    public static Model loadModel(String filePath) {
        Model model = null;
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            model = (Model) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            System.out.println("Log: Le modèle a été chargé avec succès à partir du fichier : " + filePath);
        } catch (Exception e) {
            System.out.println("Log: Une erreur s'est produite lors du chargement du modèle : " + e.getMessage());
            e.printStackTrace();
        }
        return model;
    }
}
