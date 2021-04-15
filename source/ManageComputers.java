import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import domain.BaseComputer;
import domain.DesktopComputer;
import domain.LaptopComputer;

public class ManageComputers {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static ArrayList<ManageComputers> mc = new ArrayList<ManageComputers>();

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

		int numChoice = 0;

		do {
			System.out.println("====MENU====");
			System.out.println("1. Load");
			System.out.println("2. Save");
			System.out.println("3. List");
			System.out.println("4. Add");
			System.out.println("5. Delete");
			System.out.println("6. Edit");
			System.out.println("0. Exit");

			System.out.print("Enter selection: ");
			numChoice = Integer.parseInt(br.readLine());

			System.out.println();

			if (numChoice < 0 || numChoice > 6) {
				System.out.println("Invalid Input!");
				continue;
			}

			switch (numChoice) {
			case 1:
				loadComputer();
				break;
			case 2:
				saveComputer(mc);
				break;
			case 3:
				listComputer(mc);
				break;
			case 4:
				addComputer(mc);
				break;
			case 5:
				deleteComputer(mc);
				break;
			case 6:
				editComputer(mc);
				break;
			case 0:
				exitProgram(mc);
				break;
			}

		} while (numChoice != 0);

	}

	private static ArrayList<ManageComputers> loadComputer() throws IOException, ClassNotFoundException {

		ArrayList<ManageComputers> mc = new ArrayList<ManageComputers>();

		File f = new File("../assign1Data/");
		File[] files = f.listFiles();

		for (File file : files) {
			String filename = file.getName();

			Object o = deserializeObject(filename);

			mc.add((ManageComputers) o);
		}

		return mc;
	}

	private static Object deserializeObject(String filename)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		Object o = null;

		FileInputStream fis = new FileInputStream("../assign1Data/" + filename);
		ObjectInputStream in = new ObjectInputStream(fis);
		o = in.readObject();
		in.close();
		fis.close();

		return o;
	}

	private static void saveComputer(ArrayList<ManageComputers> mc) throws FileNotFoundException, IOException {
		File f = new File("../assign1Data/");
		File[] files = f.listFiles();

		for (File file : files) {
			file.delete();
		}

		for (int i = 0; i < mc.size(); i++) {
			serializeObject(mc);
		}

	}

	private static void serializeObject(ArrayList<ManageComputers> mc) throws FileNotFoundException, IOException {

		for (int i = 0; i < mc.size(); i++) {
			FileOutputStream fout = new FileOutputStream("../assign1Data/" + (i + 1) + ".txt");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(mc);
			oos.close();
		}
	}

	private static void listComputer(ArrayList<ManageComputers> mc) {

		if (mc.size() == 0) {
			return;
		}

		for (int i = 0; i < mc.size(); i++) {
			System.out.println("--------------");
			System.out.println("COMPUTER #" + (i + 1));

			if (mc.contains("intel") || mc.contains("amd") || mc.contains("nvida")) {
				System.out.println("Type : DesktopComputer");
				System.out.println(mc.toString());
			} else {
				System.out.println("Type : LaptopComputer");
				System.out.println(mc.toString());
			}
		}
	}

	private static void addComputer(ArrayList<ManageComputers> mc) throws IOException {

		int numChoice;
		String cpuType = null;
		int ramSize = 0;
		int diskSize = 0;
		String gpuType;
		int screenSize;
		BaseComputer b = BaseComputer.getInstance(null, 0, 0);

		System.out.println("ADD NEW COMPUTER");
		System.out.println("1. Add new LaptopComputer");
		System.out.println("2. Add new DesktopComputer");
		System.out.println("3. Back to main menu");

		numChoice = Integer.parseInt(br.readLine());

		switch (numChoice) {

		case 1:
			System.out.println("Enter CPU type (i5/i7) :");
			b.setCpuType(br.readLine());
			System.out.println("Enter RAM size (8/16) :");
			b.setRamSize(Integer.parseInt(br.readLine()));
			System.out.println("Enter disk size (250/500) :");
			b.setDiskSize(Integer.parseInt(br.readLine()));
			System.out.println("Enter screen size (13/15) :");
			screenSize = Integer.parseInt(br.readLine());

			LaptopComputer laptop = new LaptopComputer(b.getInstance(cpuType, ramSize, diskSize), screenSize);

			//mc.add(laptop);

			break;

		case 2:
			System.out.println("Enter CPU type (i5/i7) :");
			cpuType = br.readLine();
			System.out.println("Enter RAM size (8/16) :");
			ramSize = Integer.parseInt(br.readLine());
			System.out.println("Enter disk size (250/500) :");
			diskSize = Integer.parseInt(br.readLine());
			System.out.println("Enter GPU (intel/amd/nvidia) :");
			gpuType = br.readLine();

			DesktopComputer desktop = new DesktopComputer(b.getInstance(cpuType, ramSize, diskSize), gpuType);

			//mc.add(desktop);

			break;

		case 3:
			break;
		}
	}

	private static void deleteComputer(ArrayList<ManageComputers> mc) throws FileNotFoundException, IOException {
		int count;
		System.out.print("Enter number of computer to delete: ");
		count = Integer.parseInt(br.readLine());

		if (count <= 0) {
			throw new IllegalArgumentException("***ERROR : Invalid computer number entered ***");
		}

		for (int i = 0; i < mc.size(); i++) {
			if (count == i + 1) {
				mc.remove(i);
				break;
			} else {
				throw new IllegalArgumentException("***ERROR : Invalid computer number entered ***");
			}
		}
	}

	private static void editComputer(ArrayList<ManageComputers> mc) throws FileNotFoundException, IOException {
		int count;
		int numChoice;
		String cpuType;
		int ramSize;
		int diskSize;
		String gpuType;
		int screenSize;
		BaseComputer b = null;
		System.out.println("EDIT COMPUTER");
		System.out.println("Enter number of computer to edit: ");
		count = Integer.parseInt(br.readLine());

		for (int i = 0; i < mc.size(); i++) {
			if (count == i + 1) {

				if (mc.contains("intel") || mc.contains("amd") || mc.contains("nvida")) {
					System.out.println("Enter CPU type (i5/i7) :");
					cpuType = br.readLine();
					System.out.println("Enter RAM size (8/16) :");
					ramSize = Integer.parseInt(br.readLine());
					System.out.println("Enter disk size (250/500) :");
					diskSize = Integer.parseInt(br.readLine());
					System.out.println("Enter GPU (intel/amd/nvidia) :");
					gpuType = br.readLine();
					DesktopComputer desktop = new DesktopComputer(b.getInstance(cpuType, ramSize, diskSize), gpuType);

					//mc.add(desktop);

					break;
				} else {
					System.out.println("Enter CPU type (i5/i7) :");
					cpuType = br.readLine();
					System.out.println("Enter RAM size (8/16) :");
					ramSize = Integer.parseInt(br.readLine());
					System.out.println("Enter disk size (250/500) :");
					diskSize = Integer.parseInt(br.readLine());
					System.out.println("Enter screen size (13/15) :");
					screenSize = Integer.parseInt(br.readLine());

					LaptopComputer laptop = new LaptopComputer(b.getInstance(cpuType, ramSize, diskSize), screenSize);

					//mc.add(laptop);


					break;
				}
			} else {
				throw new IllegalArgumentException("***ERROR : Invalid computer number entered ***");
			}
		}
	}

	private static void exitProgram(ArrayList<ManageComputers> mc) throws IOException {
		return;
	}
}

