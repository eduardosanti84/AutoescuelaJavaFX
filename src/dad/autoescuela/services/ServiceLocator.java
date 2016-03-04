package dad.autoescuela.services;

public class ServiceLocator {

	private static IConexionServices conexionServices;
	private static IUsuarioServices usuarioServices;
	private static IPreguntaServices preguntaServices;
//	private static IResultadoServices resultadoServices;
	
	static{
		conexionServices = new ConexionServices();
		usuarioServices = new UsuarioServices();
		preguntaServices = new PreguntaServices();
//		resultadoServices = new ResultadoServices();
	}

	public static IConexionServices getConexionServices() {
		return conexionServices;
	}
	public static IUsuarioServices getUsuarioServices() {
		return usuarioServices;
	}
	public static IPreguntaServices getPreguntaServices() {
		return preguntaServices;
	}
//	public static IResultadoServices getResultadoServices() {
//		return resultadoServices;
//	}
}
