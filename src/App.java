
public class App {
    public static void main(String[] args) throws Exception {
        String BD = "world";
        String USUARIO = "root";
        String PASS = "";
        String HOST = "localhost";

        ConexionMySql c = new ConexionMySql(HOST, BD, USUARIO, PASS);

        if (c.conectar()) {
            System.out.println("conectado");
        }

        System.out.println("Lista de las 10 CIUDADES (mostrar Name) con más población (Population) del mundo.");

        String consulta = "Select name from city order by population desc limit 10";
        c.ejecutarSelect(consulta);

        System.out.println(
                "Lista de los 10 PAÍSES (mostrar Name y Region) con mayor tamaño de área demográfica (SurfaceArea) del mundo.");
        String consultaDos = "select Name, Region from country order by region desc limit 10";
        c.ejecutarSelect(consultaDos);

        System.out.println(
                "Lista de los 10 PAÍSES con menor esperanza de vida. (LifeExpectancy) (Hay algunos registros a NULL, así que indicar que sean diferentes a NULL");
        String consultaTres = "select name from country order by LifeExpectancy asc limit 10 ";
        c.ejecutarSelect(consultaTres);

        System.out.println("Número de PAÍSES por cada continente");
        String consultaCuatro = "select Continent, count(name) from country group by Continent";
        c.ejecutarSelect(consultaCuatro);

        System.out.println("Número de CIUDADES por cada continente");
        String consultaCinco = "SELECT Continent, sum(Capital) FROM country where Capital is NOT null group by continent ;";
        c.ejecutarSelect(consultaCinco);

        System.out.println(
                "Lista de los 10 PAÍSES (mostrar Name y Region) donde el idioma español sea el que más se hable y sea idioma oficial.");
        String consultaSeis = " SELECT Name, Region FROM country where Code = (SELECT CountryCode from countrylanguage where Language = 'Spanish' and IsOfficial = 'T') limit 10;";
        c.ejecutarSelect(consultaSeis);

        System.out
                .println("Insertar la ciudad Villafranca de Córdoba, 5000 habitantes, Distrito Alto Guadalquivir, ESP");
        String consultaSiete = "INSERT INTO `city` (`ID`, `Name`, `CountryCode`, `District`, `Population`) VALUES ('4074', 'Villafranca de Cordoba', 'ESP', 'Alto Guadalquivir', '5000');";
        System.out.println(c.ejecutarInsertUpdateDelete(consultaSiete));

        System.out.println("Actualiza los habitantes de Villafranca de Córdoba 4500");
        String consultaOcho = "UPDATE `city` SET `Population` = '4500' WHERE `city`.`ID` = '4074'; ";
        System.out.println(c.ejecutarInsertUpdateDelete(consultaOcho));

        System.out.println("Borra las ciudades de Francia");
        String consultaNueve = "DELETE FROM city WHERE CountryCode = 'FRA';";
        System.out.println(c.ejecutarInsertUpdateDelete(consultaNueve));

    }
}
