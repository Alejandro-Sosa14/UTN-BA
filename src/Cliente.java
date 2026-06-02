public abstract class Cliente implements Mostrable {

    private int id;
    private String nombre;
    private String apellido;
    private String documento;
    private String direccion;
    private String telefono;
    private String email;
    private String fechaAlta;

    public Cliente(int id, String nombre, String apellido, String documento,
                   String direccion, String telefono, String email, String fechaAlta) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.fechaAlta = fechaAlta;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getDocumento() { return documento; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public String getFechaAlta() { return fechaAlta; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setDocumento(String documento) { this.documento = documento; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }
    public void setFechaAlta(String fechaAlta) { this.fechaAlta = fechaAlta; }

    public void mostrarDatosComunes() {
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre + " " + apellido);
        System.out.println("Documento: " + documento);
        System.out.println("Direccion: " + direccion);
        System.out.println("Telefono: " + telefono);
        System.out.println("Email: " + email);
        System.out.println("Fecha de alta: " + fechaAlta);
    }

    public abstract void mostrarDatos();
}

