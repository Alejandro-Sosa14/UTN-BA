public class ClientePremium extends Cliente {

    private double limiteCreditoEspecial;
    private String ejecutivoCuenta;
    private String beneficiosAdicionales;

    public ClientePremium(int id, String nombre, String apellido, String documento,
                           String direccion, String telefono, String email, String fechaAlta,
                           double limiteCreditoEspecial, String ejecutivoCuenta, String beneficiosAdicionales) {
        super(id, nombre, apellido, documento, direccion, telefono, email, fechaAlta);
        this.limiteCreditoEspecial = limiteCreditoEspecial;
        this.ejecutivoCuenta = ejecutivoCuenta;
        this.beneficiosAdicionales = beneficiosAdicionales;
    }

    public double getLimiteCreditoEspecial() { return limiteCreditoEspecial; }
    public String getEjecutivoCuenta() { return ejecutivoCuenta; }
    public String getBeneficiosAdicionales() { return beneficiosAdicionales; }

    public void setLimiteCreditoEspecial(double limiteCreditoEspecial) { this.limiteCreditoEspecial = limiteCreditoEspecial; }
    public void setEjecutivoCuenta(String ejecutivoCuenta) { this.ejecutivoCuenta = ejecutivoCuenta; }
    public void setBeneficiosAdicionales(String beneficiosAdicionales) { this.beneficiosAdicionales = beneficiosAdicionales; }

    @Override
    public void mostrarDatos() {
        System.out.println("=== Cliente Premium ===");
        mostrarDatosComunes();
        System.out.println("Limite de credito especial: $" + limiteCreditoEspecial);
        System.out.println("Ejecutivo de cuenta: " + ejecutivoCuenta);
        System.out.println("Beneficios adicionales: " + beneficiosAdicionales);
    }
}

