package com.gestion.empleados.controller;

import com.gestion.empleados.model.Empleado;
import com.gestion.empleados.repository.EmpleadoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class EmpleadoController {
    private EmpleadoRepository repository;

    public EmpleadoController(EmpleadoRepository repository) {
        this.repository = repository;
    }

    //Buscar todos los empleados
    @GetMapping("/empleados")
    public List<Empleado> ListaEmpleados (){
        return repository.findAll();
    }


    // Buscar empleado mediante ID
    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> FindId(@PathVariable Long id){
        Optional<Empleado> empleadoOpt = repository.findById(id);
        if (empleadoOpt.isPresent()){
            return ResponseEntity.ok(empleadoOpt.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un empleado
    @PostMapping("/empleados")
    public ResponseEntity<Empleado> CreateEmp(@RequestBody Empleado empleado) {
        ResponseEntity<Empleado> result;
        if (empleado.getId() != null) {
            result = ResponseEntity.badRequest().build();
        } else {
            Empleado emp = repository.save(empleado);
            result = ResponseEntity.ok(emp);
        }
        return result;
    }



}
