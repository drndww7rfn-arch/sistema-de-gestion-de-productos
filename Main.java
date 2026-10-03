// Este es un sistema de gestión de productos

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/** Super clase para todos los productos */
class Producto {
    private static int nextId = 1;          // ID auto incremental
    private final int id;
    private String nombre;
    private double precio;

    public Producto(String nombre, double precio) {
        this.id = nextId++;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }

    /** Subclases agregan su propia informacion */
    public String detalles() {
        return String.format("ID: %d | %s | $%.2f", id, nombre, precio);
    }

    @Override
    public String toString() {
        return detalles();
    }
}

/** Producto electrónico */
class Electronico extends Producto {
    private String marca;
    private String modelo;

    public Electronico(String nombre, double precio, String marca, String modelo) {
        super(nombre, precio);
        this.marca = marca;
        this.modelo = modelo;
    }

    @Override
    public String detalles() {
        return super.detalles() + String.format(" | Electrónico | Marca: %s | Modelo: %s",
                marca, modelo);
    }
}

/** Producto libro */
class Libro extends Producto {
    private String autor;
    private String isbn;

    public Libro(String nombre, double precio, String autor, String isbn) {
        super(nombre, precio);
        this.autor = autor;
        this.isbn = isbn;
    }

    @Override
    public String detalles() {
        return super.detalles() + String.format(" | Libro | Autor: %s | ISBN: %s",
                autor, isbn);
    }
}

/** Producto ropa */
class Ropa extends Producto {
    private String talla;
    private String material;

    public Ropa(String nombre, double precio, String talla, String material) {
        super(nombre, precio);
        this.talla = talla;
        this.material = material;
    }

    @Override
    public String detalles() {
        return super.detalles() + String.format(" | Ropa | Talla: %s | Material: %s",
                talla, material);
    }
}

/** Clase principal con menú CLI */
public class Main {
    private static final List<Producto> inventario = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean seguir = true;
        while (seguir) {
            imprimirMenu();
            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    borrarProducto();
                    break;
                case 3:
                    listarProductos();
                    break;
                case 4:
                    System.out.println("¡Hasta luego!");
                    seguir = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void imprimirMenu() {
        System.out.println("\n=== Sistema de Gestión de Productos ===");
        System.out.println("1. Agregar producto");
        System.out.println("2. Borrar producto");
        System.out.println("3. Listar productos");
        System.out.println("4. Salir");
    }

    /** Lectura segura de entero */
    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.print("Entrada inválida. " + mensaje);
            sc.next(); // descarta token inválido
        }
        int val = sc.nextInt();
        sc.nextLine(); // consumir fin‑de‑línea
        return val;
    }

    /** Lectura segura de double */
    private static double leerDouble(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextDouble()) {
            System.out.print("Entrada inválida. " + mensaje);
            sc.next();
        }
        double val = sc.nextDouble();
        sc.nextLine();
        return val;
    }

    /** Agregar un producto según tipo */
    private static void agregarProducto() {
        System.out.println("\n--- Agregar Producto ---");
        System.out.println("Tipos de producto:");
        System.out.println("1. Electrónico");
        System.out.println("2. Libro");
        System.out.println("3. Ropa");
        int tipo = leerEntero("Seleccione el tipo: ");

        String nombre = leerCadena("Nombre: ");
        double precio = leerDouble("Precio: ");

        switch (tipo) {
            case 1 -> {
                String marca = leerCadena("Marca: ");
                String modelo = leerCadena("Modelo: ");
                inventario.add(new Electronico(nombre, precio, marca, modelo));
                System.out.println("Electrónico agregado.");
            }
            case 2 -> {
                String autor = leerCadena("Autor: ");
                String isbn = leerCadena("ISBN: ");
                inventario.add(new Libro(nombre, precio, autor, isbn));
                System.out.println("Libro agregado.");
            }
            case 3 -> {
                String talla = leerCadena("Talla: ");
                String material = leerCadena("Material: ");
                inventario.add(new Ropa(nombre, precio, talla, material));
                System.out.println("Ropa agregada.");
            }
            default -> System.out.println("Tipo no válido.");
        }
    }

    /** Borrar producto por ID */
    private static void borrarProducto() {
        System.out.println("\n--- Borrar Producto ---");
        if (inventario.isEmpty()) {
            System.out.println("No hay productos para borrar.");
            return;
        }
        listarProductos();
        int id = leerEntero("Ingrese el ID del producto a eliminar: ");
        Iterator<Producto> it = inventario.iterator();
        boolean encontrado = false;
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                encontrado = true;
                System.out.println("Producto eliminado.");
                break;
            }
        }
        if (!encontrado) {
            System.out.println("ID no encontrado.");
        }
    }

    /** Mostrar todos los productos */
    private static void listarProductos() {
        System.out.println("\n--- Lista de Productos ---");
        if (inventario.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        for (Producto p : inventario) {
            System.out.println(p);
        }
    }

    /** Lectura de texto (no vacío) */
    private static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        String line = sc.nextLine().trim();
        while (line.isEmpty()) {
            System.out.print("Entrada no puede estar vacía. " + mensaje);
            line = sc.nextLine().trim();
        }
        return line;
    }
}
