public class Main {
    public static void main(String[] args) {

        ClientePersonaFisica cliente1 = new ClientePersonaFisica(
                1, "Juan", "Perez", "DNI 30123456",
                "Av. Corrientes 1234", "011-4444-5555", "juan.perez@gmail.com", "15/03/2020",
                "30123456", "22/07/1985", "Contador", 150000
        );

        ClienteEmpresa cliente2 = new ClienteEmpresa(
                2, "TechSolutions", "S.A.", "CUIT 30-71234567-9",
                "Av. del Libertador 5000", "011-5555-7777", "info@techsolutions.com", "01/06/2019",
                "TechSolutions S.A.", "30-71234567-9", "Tecnologia", "Maria Lopez"
        );

        ClientePremium cliente3 = new ClientePremium(
                3, "Carlos", "Gomez", "DNI 25987654",
                "Av. Santa Fe 800", "011-6666-8888", "carlos.gomez@gmail.com", "10/01/2018",
                5000000, "Laura Martinez", "Tarjeta Black, Sala VIP, Descuento en seguros"
        );

        cliente1.mostrarDatos();
        System.out.println();
        cliente2.mostrarDatos();
        System.out.println();
        cliente3.mostrarDatos();
    }
}