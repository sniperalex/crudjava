package com.crud.veiculos;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String DB_FILE = "veiculos.db";

    public static void main(String[] args) {
        try (VehicleDAO dao = new VehicleDAO(DB_FILE)) {
            ensureSeedData(dao);
            runConsole(dao);
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void ensureSeedData(VehicleDAO dao) throws Exception {
        List<Vehicle> all = dao.listAll();
        if (all.size() >= 5) return;
        System.out.println("Inserindo registros iniciais...");
        dao.insert(new Vehicle("Toyota", "Corolla", "AAA-1111", 2018));
        dao.insert(new Vehicle("Honda", "Civic", "BBB-2222", 2019));
        dao.insert(new Vehicle("Ford", "Ka", "CCC-3333", 2016));
        dao.insert(new Vehicle("Volkswagen", "Gol", "DDD-4444", 2015));
        dao.insert(new Vehicle("Chevrolet", "Onix", "EEE-5555", 2020));
    }

    private static void runConsole(VehicleDAO dao) throws Exception {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
            System.out.println("\n--- CRUD Veículos ---");
            System.out.println("1) Listar todos");
            System.out.println("2) Inserir veículo");
            System.out.println("3) Buscar por placa");
            System.out.println("4) Apagar por id");
            System.out.println("0) Sair");
            System.out.print("Escolha: ");
            String opt = sc.nextLine().trim();
            if (opt.equals("0")) break;
            switch (opt) {
                case "1":
                    List<Vehicle> list = dao.listAll();
                    if (list.isEmpty()) System.out.println("Nenhum veículo cadastrado.");
                    else list.forEach(System.out::println);
                    break;
                case "2":
                    System.out.print("Marca: ");
                    String marca = sc.nextLine().trim();
                    System.out.print("Modelo: ");
                    String modelo = sc.nextLine().trim();
                    System.out.print("Placa: ");
                    String placa = sc.nextLine().trim();
                    System.out.print("Ano: ");
                    int ano = Integer.parseInt(sc.nextLine().trim());
                    Vehicle v = dao.insert(new Vehicle(marca, modelo, placa, ano));
                    System.out.println("Inserido: " + v);
                    break;
                case "3":
                    System.out.print("Placa a buscar: ");
                    String p = sc.nextLine().trim();
                    Vehicle found = dao.findByPlaca(p);
                    if (found == null) System.out.println("Nenhum veículo com essa placa.");
                    else System.out.println(found);
                    break;
                case "4":
                    System.out.print("ID a apagar: ");
                    int id = Integer.parseInt(sc.nextLine().trim());
                    boolean ok = dao.deleteById(id);
                    System.out.println(ok ? "Apagado com sucesso." : "ID não encontrado.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
            }
        }
        System.out.println("Encerrando...");
    }
}
