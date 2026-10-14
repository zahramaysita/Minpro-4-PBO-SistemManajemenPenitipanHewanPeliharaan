
package main;

import controller.HewanController;
import view.HewanView;

import java.util.Scanner;

public class SistemPenitipanHewanPeliharaan {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        HewanView view = new HewanView();

        HewanController controller =
                new HewanController(view, scanner);

        controller.jalankanProgram();

        scanner.close();
    }
}
