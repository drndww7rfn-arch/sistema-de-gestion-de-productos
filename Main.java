import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/* ----------------------------- */
/*        CLASE BASE             */
/* ----------------------------- */
class Producto {
    private static int nextId = 1;
    private final int id;
    private String nombre;
    private double precio;

    public Producto(String nombre, double precio) {
        this.id = nextId++;
        this.nombre = nombre;
        this.precio = precio;
    }

    /* Overloading del constructor (precio por defecto) */
    public Producto(String nombre) {
        this(nombre, 0.0);
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }

    /* Método que será sobrescrito (runtime polymorphism) */
    public String detalles() {
        return String.format("ID: %d | %s | $%.2f", id, nombre, precio);
    }

    @Override
    public String toString() {
        return detalles();
    }
}

/* ----------------------------- */
/*      SUBCLASE ELECTRÓNICO      */
/* ----------------------------- */
class Electronico extends Producto {
    private String marca;
    private String modelo;

    /* Overloading de constructores */
    public Electronico(String nombre, double precio, String marca, String modelo) {
        super(nombre, precio);
        this.marca = marca;
        this.modelo = modelo;
    }

    public Electronico(String nombre, String marca, String modelo) {
        this(nombre, 0.0, marca, modelo);
    }

    @Override
    public String detalles() {
        return super.detalles() +
                String.format(" | Electrónico | Marca: %s | Modelo: %s", marca, modelo);
    }
}

/* ----------------------------- */
/*          SUBCLASE LIBRO       */
/* ----------------------------- */
class Libro extends Producto {
    private String autor;
    private String isbn;

    /* Overloading: con o sin ISBN */
    public Libro(String nombre, double precio, String autor, String isbn) {
        super(nombre, precio);
        this.autor = autor;
        this.isbn = isbn;
    }

    public Libro(String nombre, double precio, String autor) {
        this(nombre, precio, autor, "N/A"); // ISBN por defecto
    }

    @Override
    public String detalles() {
        return super.detalles() +
                String.format(" | Libro | Autor: %s | ISBN: %s", autor, isbn);
    }
}

/* ----------------------------- */
/*          SUBCLASE ROPA        */
/* ----------------------------- */
class Ropa extends Producto {
    private String talla;
    private String material;

    /* Overloading de constructores */
    public Ropa(String nombre, double precio, String talla, String material) {
        super(nombre, precio);
        this.talla = talla;
        this.material = material;
    }

    public Ropa(String nombre, double precio, String talla) {
        this(nombre, precio, talla, "Indeterminado");
    }

    @Override
    public String detalles() {
        return super.detalles() +
                String.format(" | Ropa | Talla: %s | Material: %s", talla, material);
    }
}

/* ----------------------------- */
/*        CLASE MAIN            */
/* ----------------------------- */
public class Main {
    private static final List<Producto> inventario = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean seguir = true;
        while (seguir) {
            imprimirMenu();
            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> agregarProducto();
                case 2 -> borrarProducto();
                case 3 -> listarProductos();
                case 4 -> {
                    System.out.println("¡Hasta luego!");
                    seguir = false;
                }
                default -> System.out.println("Opción no válida. Intente de nuevo.");
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

    /* ---------- MÉTODOS DE ENTRADA (OVERLOADING POR TIPO) ---------- */
    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.print("Entrada inválida. " + mensaje);
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }

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

    private static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        String line = sc.nextLine().trim();
        while (line.isEmpty()) {
            System.out.print("Entrada no puede estar vacía. " + mensaje);
            line = sc.nextLine().trim();
        }
        return line;
    }

    /* Versión que permite cadena vacía (para ISBN o material opcional) */
    private static String leerCadenaOpcional(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();          // puede ser ""
    }

    /* ----------------------------- */
    /*   AGREGAR PRODUCTO (OVERLOAD) */
    /* ----------------------------- */
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
                if (precio == 0) {
                    inventario.add(new Electronico(nombre, marca, modelo));
                } else {
                    inventario.add(new Electronico(nombre, precio, marca, modelo));
                }
                System.out.println("Electrónico agregado.");
            }
            case 2 -> {
                String autor = leerCadena("Autor: ");
                String isbn = leerCadenaOpcional("ISBN (Enter para omitir): ");
                if (isbn.isEmpty()) {
                    inventario.add(new Libro(nombre, precio, autor));
                } else {
                    inventario.add(new Libro(nombre, precio, autor, isbn));
                }
                System.out.println("Libro agregado.");
            }
            case 3 -> {
                String talla = leerCadena("Talla: ");
                String material = leerCadenaOpcional("Material (Enter para omitir): ");
                if (material.isEmpty()) {
                    inventario.add(new Ropa(nombre, precio, talla));
                } else {
                    inventario.add(new Ropa(nombre, precio, talla, material));
                }
                System.out.println("Ropa agregada.");
            }
            default -> System.out.println("Tipo no válido.");
        }
    }

    /* ----------------------------- */
    /*   BORRAR PRODUCTO             */
    /* ----------------------------- */
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

    /* ----------------------------- */
    /*   LISTAR PRODUCTOS            */
    /* ----------------------------- */
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
}
