public class ClienteEmpresa extends Cliente {

    private String razonSocial;
    private String cuit;
    private String rubro;
    private String representanteLegal;

    public ClienteEmpresa(int id, String nombre, String apellido, String documento,
                           String direccion, String telefono, String email, String fechaAlta,
                           String razonSocial, String cuit, String rubro, String representanteLegal) {
        super(id, nombre, apellido, documento, direccion, telefono, email, fechaAlta);
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.rubro = rubro;
        this.representanteLegal = representanteLegal;
    }

    public String getRazonSocial() { return razonSocial; }
    public String getCuit() { return cuit; }
    public String getRubro() { return rubro; }
    public String getRepresentanteLegal() { return representanteLegal; }

    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public void setCuit(String cuit) { this.cuit = cuit; }
    public void setRubro(String rubro) { this.rubro = rubro; }
    public void setRepresentanteLegal(String representanteLegal) { this.representanteLegal = representanteLegal; }

    @Override
    public void mostrarDatos() {
        System.out.println("=== Cliente Empresa ===");
        mostrarDatosComunes();
        System.out.println("Razon social: " + razonSocial);
        System.out.println("CUIT: " + cuit);
        System.out.println("Rubro: " + rubro);
        System.out.println("Representante legal: " + representanteLegal);
    }
}
