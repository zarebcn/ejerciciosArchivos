import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class listaDiscos {

	public static void main(String[] args) throws IOException {

		
		//comprueba si existe un archivo que contenga una lista de discos y si no existe lo crea
		File archivo = new File("Discos.json");

		if(archivo.exists()){
			System.out.println("Lista de discos encontrada");
			System.out.println();
			  
		}else{
			System.out.println("No se ha encontrado lista de discos");
			System.out.println("Creando una nueva...");
			System.out.println();
			archivo.createNewFile();
		}

		
		//lee el archivo JSON y lo mete en una lista
		FileReader reader = new FileReader("Discos.json");
		Gson gson = new Gson();
		Type type = new TypeToken<List<Disco>>(){}.getType();
		List<Disco> Discos2 = gson.fromJson(reader, type);
		reader.close();
		
		//agrega mas discos, los almacena en una lista temporal y despues los guarda en el archivo JSON
		List<Disco> discoTemp = Discos2;
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Quieres guardar mas discos? [s/n]");
		String c = scanner.nextLine();
		String a;
		String b;
		
		if (c.equals("s")) {
			System.out.println("Introduce el título del disco: ");
			a = scanner.nextLine();
			
			if (a.equals("quit")) {
				return;
			}
			
			while (!a.equals("quit")) {
				System.out.println("Introduce autor del disco: ");
				b = scanner.nextLine();
				System.out.flush();
				discoTemp.add(new Disco(a, b));
				
				System.out.println("Introduce el título del disco: ");
				a = scanner.nextLine();
				
				if (a.equals("quit")) {
					Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
					String json = gson2.toJson(discoTemp);
					FileWriter writer = new FileWriter("Discos.json");
			
					writer.write(json);
					writer.close();
					return;
				}
			}
		}
		
		if (c.equals("n")) {
			System.out.println("De acuerdo, a continuación se imprimira la lista de discos y se cerrará el programa");
			System.out.println();
			for (Disco disco : Discos2) {
				System.out.println("'" + disco.titulo() + "'" + " de " + disco.autor());
			
			}
		}
	}

}
