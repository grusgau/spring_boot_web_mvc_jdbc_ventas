package org.iesvdm.controlador;

import java.util.List;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

@Controller
//Se puede fijar ruta base de las peticiones de este controlador.
//Los mappings de los métodos tendrían este valor /clientes como
//prefijo.
//@RequestMapping("/clientes")
public class ClienteController {
	
	private ClienteService clienteService;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	//@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	//equivalente a la siguiente anotación
	@GetMapping("/clientes") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
	public String listar(Model model) {
		
		List<Cliente> listaClientes =  clienteService.listAll();
		model.addAttribute("listaClientes", listaClientes);
				
		return "clientes";
		
	}
	
	@GetMapping("/clientes/{id}")
	public String detalle(Model model, @PathVariable Integer id ) {
		
		Cliente cliente = clienteService.one(id);
		model.addAttribute("cliente", cliente);
		
		return "detalle-cliente";
		
	}
	
	@GetMapping("/fabricantes/crear")
	public String crear(Model model) {
		
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		
		return "crear-fabricante";
		
	}
	
	@PostMapping("/clientes/crear")
	public RedirectView submitCrear(@ModelAttribute("cliente") Cliente cliente) {
		
		clienteService.newFabricante(cliente);
				
		return new RedirectView("/clientes") ;
		
	}
	
	@GetMapping("/fabricantes/editar/{id}")
	public String editar(Model model, @PathVariable Integer id) {
		
		Fabricante fabricante = fabricanteService.one(id);
		model.addAttribute("fabricante", fabricante);
		
		return "editar-fabricante";
		
	}
	
	
	@PostMapping("/fabricantes/editar/{id}")
	public RedirectView submitEditar(@ModelAttribute("fabricante") Fabricante fabricante) {
		
		fabricanteService.replaceFabricante(fabricante);		
		
		return new RedirectView("/fabricantes");
	}
	
	@PostMapping("/fabricantes/borrar/{id}")
	public RedirectView submitBorrar(@PathVariable Integer id) {
		
		fabricanteService.deleteFabricante(id);
		
		return new RedirectView("/fabricantes");
	}
	
	
	
	

}
