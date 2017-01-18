import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ListaDiscos {

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
		List<Disco> discos = gson.fromJson(reader, type);
		reader.close();

		//agrega mas discos, los almacena en una lista temporal y despues los guarda en el archivo JSON
		List<Disco> discoTemp = discos;

		Scanner scanner = new Scanner(System.in);

		System.out.println("Quieres guardar mas discos? [s/n]");
		String line = scanner.nextLine();
		String titulo;
		String autor;

		while (!line.equals("s") || !line.equals("n")) {

			if (line.equals("s")) {
				System.out.println("Introduce título del disco: ");
				titulo = scanner.nextLine();

				if (titulo.equals("quit")) {
					scanner.close();
					return;
				}

				while (!titulo.equals("quit")) {
					System.out.println("Introduce autor del disco: ");
					autor = scanner.nextLine();
					discoTemp.add(new Disco(titulo, autor));

					System.out.println("Introduce título del disco: ");
					titulo = scanner.nextLine();

					if (titulo.equals("quit")) {
						Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
						String json = gson2.toJson(discoTemp);
						FileWriter writer = new FileWriter("Discos.json");

						writer.write(json);
						writer.close();
						scanner.close();
						return;
					}
				}
			}else if (line.equals("n")) {
				System.out.println("De acuerdo, a continuacion se imprimira la lista de discos");
				for (Disco disco : discos) {
					System.out.println("'" + disco.titulo() + "'" + " de " + disco.autor());
				}
				scanner.close();
				return;
			}
			System.out.println("Comando erróneo, por favor introduce s o n");
			System.out.println("Quieres guardar mas discos? [s/n]");
			line = scanner.nextLine();
		}
		scanner.close();
	}
}
