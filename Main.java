// Este es un sistema de gestión de productos.
private static final List<Producto> productos = new ArrayList<>();

void main() {
    mostrarMenuPrincipal();
}
// Metodo de las ACCIONES del menu principal
private static void mostrarMenuPrincipal() {
    Scanner scanner = new Scanner(System.in);
    int opcion;

    do {
        printMenu();
        opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
            case 1:
                agregarProducto();
                break;
            case 2:
                listarProductos();
                break;
            case 3:
                buscarProducto();
                break;
            case 4:
                eliminarProducto();
                break;
            case 5:
                break;
            default:
                IO.println("Opción inválida. Inténtelo de nuevo.");
        }

    } while (opcion != 5);
}

// Metodo de lo que se muestra en la CLI.
private static void printMenu() {
    // Ignorar las advertencias de logpoint.
    IO.println("\nSistema de Gestión de Productos");
    IO.println("1. Agregar Producto");
    IO.println("2. Listar Productos");
    IO.println("3. Buscar Producto");
    IO.println("4. Eliminar Producto");
    IO.println("5. Salir");
}

// Metodo para añadir nuevo producto
private static void agregarProducto() {
    Scanner scanner = new Scanner(System.in);

    IO.print("Nombre del Producto: ");
    String nombre = scanner.nextLine();

    IO.print("Precio del Producto: ");
    double precio = scanner.nextDouble();

    IO.print("Cantidad en Stock: ");
    int cantidad = scanner.nextInt();

    Producto nuevoProducto = new Producto(nombre, precio, cantidad);
    productos.add(nuevoProducto);

}

// Metodo para listar productos
private static void listarProductos() {
    if (productos.isEmpty()) {
        IO.println("No hay productos en el sistema.");
    } else {
        IO.println("\nLista de Productos:");
        for (Producto producto : productos) {
            IO.println(producto); // Imprime el Producto usando su toString()
        }
    }
}

// Metodo para buscar producto (debe escribirse literalmente)
private static void buscarProducto() {
    Scanner scanner = new Scanner(System.in);
    IO.print("Ingrese el nombre del producto por buscar: ");
    String nombreBuscar = scanner.nextLine();

    boolean encontrado = false;
    for (Producto producto : productos) {
        if (producto.getNombre().equalsIgnoreCase(nombreBuscar)) { // Usé .equalsIgnoreCase para que la búsqueda no distinga mayúsculas/minúsculas
            encontrado = true;
            IO.println("Producto existente");
            break; // Detiene la búsqueda una vez que se encuentra el producto
        }
    }

    if (!encontrado) {
        IO.println("No se encontró ningún producto con ese nombre.");
    }
}

// Metodo para eliminar producto específico (nombre literal)
private static void eliminarProducto() {
    Scanner scanner = new Scanner(System.in);
    IO.print("Ingrese el nombre del producto a eliminar: ");
    String nombreEliminar = scanner.nextLine();

    boolean eliminado = false;
    for (int i = 0; i < productos.size(); i++) {
        if (productos.get(i).getNombre().equalsIgnoreCase(nombreEliminar)) {
            productos.remove(i);
            eliminado = true;
            break; // Detiene la eliminación una vez que se elimina el producto
        }
    }

    if (!eliminado) {
        IO.println("No se encontró ningún producto con ese nombre para eliminar.");
    }
}

// Clase Producto . Constructor de producto.
static class Producto {
    private final String nombre;
    private final double precio;
    private final int cantidad;

    public Producto(String nombre, double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    // Instanciación de los objetos.
    @Override
    public String toString() { // Sobreescribe toString para imprimir la información del producto de forma legible
        String s = "Producto{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                '}';
        return s;
    }
}
