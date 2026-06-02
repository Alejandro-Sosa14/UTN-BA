public class ClientePersonaFisica extends Cliente {

    private String dni;
    private String fechaNacimiento;
    private String profesion;
    private double ingresosDeclarados;

    public ClientePersonaFisica(int id, String nombre, String apellido, String documento,
                                 String direccion, String telefono, String email, String fechaAlta,
                                 String dni, String fechaNacimiento, String profesion, double ingresosDeclarados) {
        super(id, nombre, apellido, documento, direccion, telefono, email, fechaAlta);
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.profesion = profesion;
        this.ingresosDeclarados = ingresosDeclarados;
    }

    public String getDni() { return dni; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public String getProfesion() { return profesion; }
    public double getIngresosDeclarados() { return ingresosDeclarados; }

    public void setDni(String dni) { this.dni = dni; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setProfesion(String profesion) { this.profesion = profesion; }
    public void setIngresosDeclarados(double ingresosDeclarados) { this.ingresosDeclarados = ingresosDeclarados; }

    @Override
    public void mostrarDatos() {
        System.out.println("=== Cliente Persona Fisica ===");
        mostrarDatosComunes();
        System.out.println("DNI: " + dni);
        System.out.println("Fecha de nacimiento: " + fechaNacimiento);
        System.out.println("Profesion: " + profesion);
        System.out.println("Ingresos declarados: $" + ingresosDeclarados);
    }
}
